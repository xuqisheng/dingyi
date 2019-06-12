package com.zhidianfan.pig.yd.moduler.sms.task;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.zhidianfan.pig.yd.moduler.common.constant.TaskName;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvTask;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.SmsMarketing;
import com.zhidianfan.pig.yd.moduler.common.dao.service.IResvTaskService;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.service.ISmsMarketingService;
import com.zhidianfan.pig.yd.moduler.sms.service.SmsMarketingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018-12-08
 * @Modified By:
 */
@Slf4j
@RestController
public class SendAndroidSmsTask {
    @Autowired
    private SmsMarketingService smsMarketingService;

    @Autowired
    private ISmsMarketingService marketingService;

    @Autowired
    private IResvTaskService resvTaskService;

    /**
     * 是否有任务在执行
     * true 正在执行任务
     * false 没有在执行的任务
     */
    private boolean taskStatus = false;


    /**
     * 单条短信发送
     *
     * @param id
     * @param businessId
     * @return
     */
    @PostMapping("/marketing/sms/send")
    public ResponseEntity sendMarketingSms(String taskId, Integer id, Integer businessId) {

        return ResponseEntity.ok(smsMarketingService.sendMarketingSms(id, businessId));
    }

    /**
     * 批量发送安卓电话机短信
     *
     * @return
     */
    @PostMapping(value = "/marketing/sms/send/batch")
    public ResponseEntity batchSendMarketingSms(String taskId) {
        log.info("=============begin batchSendMarketingSms=================");
        if(taskStatus){
            log.info("正在执行短信发送");
            ErrorTip errorTip = new ErrorTip();
            errorTip.setMsg("正在执行短发发送");
            return ResponseEntity.ok(errorTip);
        }
        try {
            taskStatus = true;
            if(StringUtils.isEmpty(taskId)){
                log.info("taskId为空");
                return ResponseEntity.badRequest().body(new ErrorTip(400,"taskId为空"));
            }
            ResvTask resvTask = resvTaskService.selectOne(new EntityWrapper<ResvTask>().eq("task_id", taskId));
            if(resvTask != null){
                log.info("重复的 taskId");
                return ResponseEntity.badRequest().body(new ErrorTip(400,"重复的 taskId"));
            }
            //检查是否有营销短信正在发送
            resvTask = resvTaskService.selectOne(new EntityWrapper<ResvTask>().eq("task_name", TaskName.SEND_MARKETING_SMS).orderBy("start_time", false).last("limit 1"));
            log.info("检查短信");

            if(resvTask == null || resvTask.getEndTime() != null){
                log.info("发送短信");
                Date startTime = new Date();
                ResvTask task = new ResvTask();
                task.setId(IdWorker.getId());
                task.setInsertTime(startTime);
                task.setTaskId(taskId);
                task.setTaskName(TaskName.SEND_MARKETING_SMS);
                task.setSeq("0");
                task.setStartTime(startTime);
                task.setNote("发送营销短信");

                Wrapper<SmsMarketing> wrapper = new EntityWrapper<SmsMarketing>()
                        .eq("status", 3)
                        .eq("client", 1)
                        .le("timer", new Date());
                List<SmsMarketing> smsMarketings = marketingService.selectList(wrapper);
                smsMarketings.forEach(smsMarketing -> smsMarketingService.sendMarketingSms(smsMarketing));
                task.setEndTime(new Date());
                resvTaskService.insertOrUpdate(task);
            }
        } finally {
            taskStatus = false;
        }

        return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
    }

    @PostMapping(value = "/marketing/sms/send/batch/v2")
    public ResponseEntity batchSendMarketingSmsV2(@RequestParam("id") Integer id){
        Wrapper<SmsMarketing> wrapper = new EntityWrapper<SmsMarketing>()
                .eq("id",id)
                .eq("status", 3)
                .eq("client", 1);
        SmsMarketing smsMarketing = marketingService.selectOne(wrapper);
        SuccessTip successTip = smsMarketingService.sendMarketingSmsV2(smsMarketing);
        return ResponseEntity.ok(successTip);
    }
}
