package com.zhidianfan.pig.yd.moduler.resv.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Business;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrder;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderLogs;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessService;
import com.zhidianfan.pig.yd.moduler.common.service.IResvOrderLogsService;
import com.zhidianfan.pig.yd.moduler.common.service.IResvOrderService;
import com.zhidianfan.pig.yd.moduler.meituan.service.rmi.PushFeign;
import com.zhidianfan.pig.yd.moduler.meituan.service.rmi.dto.JgPush;
import com.zhidianfan.pig.yd.moduler.resv.thread.OverTimeOrderThread;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: hzp
 * @Date: 2019-05-24 10:09
 * @Description: 超时订单定时任务service
 */
@Service
@Slf4j
public class OverTimeOrderService {

    @Autowired
    private IBusinessService iBusinessService;

    @Autowired
    private IResvOrderService iResvOrderService;

    @Autowired
    private IResvOrderLogsService iResvOrderLogsService;

    @Autowired
    private PushFeign pushFeign;

    @Autowired
    private RedisTemplate redisTemplate;

//    @Async
    public void statisticsOverTimeOrder() {

        log.info("任务开始");

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        try {
            int businessCurrentPage = 1;
            int businessPageSize = 100;


            while(true) {
                //翻页查询酒店
                Page<Business> businessPage = iBusinessService.selectPage(
                        new Page<>(businessCurrentPage, businessPageSize),
                        new EntityWrapper<Business>().eq("status", 1)
                );

                // 线程池更新订单状态,并且推送
                executorService.execute(() -> new OverTimeOrderThread(this, businessPage.getRecords()).run());

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
     *
     * 更新超时订单状态,并且推送
     *
     * @param businessList 酒店id list
     */
    public void updateOverTimeOrderAndPushMessage(List<Business> businessList) {

        log.info("updateOverTimeOrderAndPushMessage-----------task start");

        for (Business business : businessList) {
            try {

                LocalDate now = LocalDate.now();
                Date date = new Date();

                // 1.筛选出订单
                List<ResvOrder> resvOrderList = iResvOrderService.selectOverTimeOrder(business.getId(), now.toString(), "1");

                //如果有需要更新的超时订单
                if (!resvOrderList.isEmpty()){


                    for (ResvOrder resvOrder : resvOrderList) {
                        //超时订单状态码为 8
                        //todo 数据库订单状态mapping表
                        resvOrder.setStatus("8");
                    }
                    //1.1. 更新
                    iResvOrderService.updateBatchById(resvOrderList);
                    //1.2. 订单变为超时更新日志
                    ResvOrderLogs resvOrderLogs = new ResvOrderLogs();
                    //记录日志
                    for (ResvOrder resvOrder : resvOrderList) {
                        resvOrderLogs.setResvOrder(resvOrder.getResvOrder()); //订单号
                        resvOrderLogs.setStatus("8");
                        resvOrderLogs.setStatusName("订单超时");
                        resvOrderLogs.setLogs("变更预定状态");
                        resvOrderLogs.setCreatedAt(date);
                        iResvOrderLogsService.insert(resvOrderLogs);
                    }

                    //2. 推送
                    pushMessage(business.getId());


                }

            }catch (Exception e){
                log.error("更新酒店id为:"+ business.getId()+ "的超时订单出错");
                log.error(e.toString());
            }

            log.debug(business.getId()+ " 更新完成");
        }
    }

    /**
     * 推送消息 (超时订单状态为8)
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
        //数据为空
        jsonObject.put("data", "");
        //type 为9 作为超时订单推送类型
        jsonObject.put("type", "9");
        jgPush.setMsg(jsonObject.toString());

        log.info("推送酒店" + id);
        pushFeign.pushMsg(jgPush.getType(), jgPush.getUsername(), jgPush.getMsgSeq(), jgPush.getBusinessId(), jgPush.getMsg());

    }


    /**
     * 生成key
     * @return 返回一个为long的key
     */
    private long getNextDateId() {
        String todayStr = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMdd");//历史遗留Key暂不考虑，一天也就一个。

        long l2 = redisTemplate.opsForValue().increment("PUSH:" + "OVERTIME_ORDER" + ":" + todayStr, 1);
        String s1 = StringUtils.leftPad(""+l2, 7, "0");
        return Long.parseLong(todayStr + s1);
    }
}
