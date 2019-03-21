package com.zhidianfan.pig.yd.moduler.push.task;

import com.zhidianfan.pig.yd.moduler.common.service.IBasePushLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 推送日志到历史表
 *
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/22
 * @Modified By:
 */
@Component
@Slf4j
public class PushToHisTask {

    @Autowired
    private IBasePushLogService iBasePushLogService;


    /**
     * fixedDelay 上一个任务完成 n 毫秒后执行
     * <p>
     * 将昨天的数据迁往历史表
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void transToHis() {
        log.info("执行推送日志迁移任务====start");
        //删除数据
        iBasePushLogService.deleteHisData();
        //数据迁移到历史表
        iBasePushLogService.toHis(DateUtils.addDays(new Date(), -1));
        //删除数据
        iBasePushLogService.deleteHisData();
        log.info("执行推送日志迁移任务====end");

    }
}
