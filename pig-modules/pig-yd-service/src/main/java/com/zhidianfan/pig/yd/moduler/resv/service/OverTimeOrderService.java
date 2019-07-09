package com.zhidianfan.pig.yd.moduler.resv.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Business;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderAndroid;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessService;
import com.zhidianfan.pig.yd.moduler.common.service.IResvOrderService;
import com.zhidianfan.pig.yd.moduler.meituan.service.rmi.PushFeign;
import com.zhidianfan.pig.yd.moduler.meituan.service.rmi.dto.JgPush;
import com.zhidianfan.pig.yd.moduler.resv.thread.OverTimeOrderThread;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author: hzp
 * @Date: 2019-05-24 10:09
 * @Description: 超时订单定时任务service
 */
@Service
@Slf4j
@ConditionalOnProperty(name = "yd.task", havingValue = "true")
public class OverTimeOrderService {

    @Autowired
    private IBusinessService iBusinessService;

    @Autowired
    private IResvOrderService iResvOrderService;


    @Autowired
    private PushFeign pushFeign;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Scheduled(fixedDelay = 60*5*1_000)
    public void statisticsOverTimeOrder() {

        log.info("statisticsOverTimeOrder------任务开始");

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        try {
            int businessCurrentPage = 1;
            int businessPageSize = 100;

            Wrapper<Business> statusWrapper = new EntityWrapper<Business>().eq("status", 1);


            while (true) {
                //翻页查询酒店
                Page<Business> businessPage = iBusinessService.selectPage(
                        new Page<>(businessCurrentPage, businessPageSize),
                        statusWrapper);

                // 线程池更新订单状态,并且推送
                Future<?> submit = executorService.submit(new OverTimeOrderThread(this, businessPage.getRecords()));
                // new OverTimeOrderThread(this, businessPage.getRecords()).run());
                submit.get();
                //business查询最后页退出循环
                if (!businessPage.hasNext()) {
                    break;
                }
                //当前页数+1
                businessCurrentPage++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {


            //关闭线程池
            executorService.shutdown();
        }


    }

    /**
     * 统计超时订单状态,并且推送
     *
     * @param businessList 酒店id list
     */

    public void countOverTimeOrderAndPushMessage(List<Business> businessList) {

        log.info("统计超时订单数量,并且推送: countOverTimeOrderAndPushMessage-----------task start");

        for (Business business : businessList) {
            try {

                LocalDate now = LocalDate.now();

                // 1.筛选出订单
                Integer resvOrderNum = iResvOrderService.selectOverTimeOrder(business.getId(), now.toString(), "1");

                //如果超时订单数量不为0 ,则推送该酒店的信息
                if (resvOrderNum != null && resvOrderNum != 0) {


//                    for (ResvOrder resvOrder : resvOrderList) {
//                        //超时订单状态码为 8
//                        resvOrder.setStatus("8");
//                        resvOrder.setUpdatedAt(date);
//                    }
//                    //1.1. 更新
//                    iResvOrderService.updateBatchById(resvOrderList);
//                    //1.2. 订单变为超时更新日志
//                    ResvOrderLogs resvOrderLogs = new ResvOrderLogs();
//                    //记录日志
//                    for (ResvOrder resvOrder : resvOrderList) {
//                        resvOrderLogs.setResvOrder(resvOrder.getResvOrder()); //订单号
//                        resvOrderLogs.setStatus("8");
//                        resvOrderLogs.setStatusName("订单超时");
//                        resvOrderLogs.setLogs("变更预定状态");
//                        resvOrderLogs.setCreatedAt(date);
//                        iResvOrderLogsService.insert(resvOrderLogs);
//                    }

                    //2. 推送
                    pushMessage(business.getId());


                }

            } catch (Exception e) {
                log.error("推送:" + business.getId() + "的超时信息");
                log.error(e.toString());
            }

            log.debug(business.getId() + " 更新完成");
        }
    }

    /**
     * 推送消息 (type 为9 作为超时订单推送类型)
     *
     * @param id 酒店id
     */
    private void pushMessage(Integer id) {


        //推送数据
        JgPush jgPush = new JgPush();
        jgPush.setBusinessId(id.toString());
        jgPush.setMsgSeq(String.valueOf(getNextDateId()));
        jgPush.setType("PAD");
        jgPush.setUsername("13777575146");

        JSONObject jsonObject = new JSONObject();

        ResvOrderAndroid resvOrderAndroid = new ResvOrderAndroid();
        //数据为空
        String orderMsg = JsonUtils.obj2Json(resvOrderAndroid);
        //数据为空
        jsonObject.put("data", orderMsg);
        //type 为9 作为超时订单推送类型
        jsonObject.put("type", "9");
        jgPush.setMsg(jsonObject.toString());

        log.info("推送酒店" + id);
        pushFeign.pushMsg(jgPush.getType(), jgPush.getUsername(), jgPush.getMsgSeq(), jgPush.getBusinessId(), jgPush.getMsg());

    }


    /**
     * 生成key
     *
     * @return 返回一个为long的key
     */
    private long getNextDateId() {
        String todayStr = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMdd");//历史遗留Key暂不考虑，一天也就一个。

        long l2 = redisTemplate.opsForValue().increment("PUSH:" + "OVERTIME_ORDER" + ":" + todayStr, 1);
        String s1 = StringUtils.leftPad("" + l2, 7, "0");
        return Long.parseLong(todayStr + s1);
    }
}
