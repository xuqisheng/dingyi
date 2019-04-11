package com.zhidianfan.pig.yd.moduler.push.task;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.yd.config.prop.YdPropertites;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.BasePushLog;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.common.service.IBasePushLogService;
import com.zhidianfan.pig.yd.moduler.push.service.ext.PushSupport;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/22
 * @Modified By:
 */
@Component
@Profile("prod")
public class PushTask {

    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private IBasePushLogService pushLogService;


    private Integer[] status = new Integer[]{1, 3, 4};

    @Autowired
    private YdPropertites ydPropertites;

    @Autowired
    private PushSupport pushSupport;


    /**
     * fixedDelay 上一个任务完成 n 毫秒后执行
     * <p>
     * 推送数据到预订台
     */
    @Scheduled(fixedDelay = 5_000)
    public void pushMsg() {
        Date now = new Date();
        //查询出需要推送的设备：推送次数<3的，2小时前至今30秒前，推送非成功状态的数据；30秒前至今的数据，可能还在被正常推送中
        List<BasePushLog> list = pushLogService.selectList(new EntityWrapper<BasePushLog>()
                .lt("pushed_count", Integer.parseInt(ydPropertites.getJg().getMap().get("pad").get("retry")))
                .notIn("push_status", status)//1-推送成功  3-无需推送 4-已被合并推送
                .between("insert_time", DateUtils.addHours(now, -2), DateUtils.addSeconds(now, -2))
                .orderBy("insert_time", false)
        );


        boolean f1 = false;
        List<String> regIds = null;
        for (BasePushLog pushLog : list) {
            log.info("准备合并推送...............合并 {} 笔推送", list.size());
            list.stream()
                    .forEach(basePushLog -> log.info(basePushLog.toString()));

            if (f1) {
                //Step s 置位被整合的批量推送
                pushLog.setPushStatus(4);
                pushLog.setNote("已被合并推送");
                continue;
            }

            //Step  新增一笔批量推送日志

            if (!f1) {
                regIds = createRegIds(list, 1000);
                Map<String, Object> map = new HashMap<>();
                map.put("code", 500);
                map.put("msg", "订单已更新，请刷新当前页面");

                Tip tip = pushSupport.pushMsgByRedIdsWithNode(regIds, JsonUtils.obj2Json(map));

                if (tip.getCode() == 200) {
                    //Step s 置位被整合的批量推送
                    pushLog.setPushStatus(4);
                    pushLog.setNote("已被合并推送");
                    f1 = true;
                }else {
                    pushLog.setPushStatus(2);
                    pushLog.setNote("定时任务推送失败"+tip.getMsg());
                    f1 = true;
                }

                pushLog.setPushedCount(pushLog.getPushedCount() + 1);
                pushLogService.updateById(pushLog);
            }


        }

        if (f1) {
            pushLogService.updateBatchById(list);//批量更新

            //新增一条合并推送的记录
            BasePushLog basePushLog = new BasePushLog();
            basePushLog.setNote(JsonUtils.obj2Json(list));
            basePushLog.setAppKey(list.get(0).getAppKey());
            basePushLog.setMasterSecret(list.get(0).getMasterSecret());
            basePushLog.setRegId(JsonUtils.obj2Json(regIds).replace("\"", "").replace(" ", ""));
            basePushLog.setType(list.get(0).getType());
            basePushLog.setUsername("Admin");//系统用户
            basePushLog.setPushMsg("{\n" +
                    "  \"msg\" : \"酒店订单更新\",\n" +
                    "  \"code\" : 500\n" +
                    "}");
            basePushLog.setPushTime(now);
            basePushLog.setPushStatus(1);
            basePushLog.setInsertTime(now);
            basePushLog.setPushedCount(1);
            pushLogService.insert(basePushLog);
        }

    }

    /**
     * 组合所有的id
     *
     * @param list
     * @return
     */
    private List<String> createRegIds(List<BasePushLog> list, int maxCount) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            String regId1 = list.get(i).getRegId();
            List<String> list2 = Arrays.asList(regId1.substring(1, regId1.length() - 1).split(","));
            for (String s1 : list2) {
                set.add(s1.trim());//排重
                if (set.size() >= maxCount) {
                    break;
                }
            }
        }
        List<String> list1 = new ArrayList<>(set);
        return list1;
    }


}
