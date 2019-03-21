package com.zhidianfan.pig.log.task;

import com.zhidianfan.pig.common.constant.TaskName;
import com.zhidianfan.pig.log.dto.CommonRes;
import com.zhidianfan.pig.log.feign.ZipLogFeign;
import com.zhidianfan.pig.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志清理
 *
 * @Author sherry
 * @Description
 * @Date Create in 2018/10/26
 * @Modified By:
 */
@RestController
public class LogController {

    @Autowired
    private ZipLogFeign zipLogFeign;

    @Autowired
    private LogService logService;

    /**
     * 情况所有zip日志表，在pig库中，通过feign调用
     *
     * @param taskId 任务id，由xxl-job在调用前生成
     * @return
     */
    @PostMapping("/ziplog/delete/all")
    public ResponseEntity deleteAllZipkinData(@RequestParam String taskId) {
        //判断taskId是否重复
        int count = logService.selectCountByTaskId(taskId);
        if (count > 0) {
            return ResponseEntity.ok(new CommonRes(500, "taskId重复：" + taskId));
        }
        //判断此类任务当前是否还有在执行的，如果有，则不执行，避免产生拥堵
        boolean flag = logService.checkTask(TaskName.TASK1);
        if (flag) {
            CommonRes commonRes = zipLogFeign.deleteAllZipkinData(taskId);
            return ResponseEntity.ok(commonRes);
        } else {
            return ResponseEntity.ok(new CommonRes(500, "此任务存在异常，本次不执行，请处理完成后再执行"));
        }
    }

    /**
     * 根据配置情况执行日志清理工作
     *
     * @param taskId
     * @return
     */
    @PostMapping("/alllog/delete")
    public ResponseEntity deleteAllLogByConfig(@RequestParam String taskId) {
        //判断taskId是否重复
        int count = logService.selectCountByTaskId(taskId);
        if (count > 0) {
            return ResponseEntity.ok(new CommonRes(500, "taskId重复：" + taskId));
        }
        //判断此类任务当前是否还有在执行的，如果有，则不执行，避免产生拥堵
        boolean flag = logService.checkTask(TaskName.TASK2);
        if (flag) {
            logService.deleteAllLogByConfig(taskId);
            return ResponseEntity.ok(CommonRes.SUCCESS1);
        } else {
            return ResponseEntity.ok(new CommonRes(500, "此任务存在异常，本次不执行，请处理完成后再执行"));
        }
    }


    /**
     * 清空SYS_LOG_(0~9)的数据
     *
     * @param taskId
     * @return
     */
    @PostMapping("/syslog/delete/all")
    public ResponseEntity deleteAllSysLogData(@RequestParam String taskId) {
        //判断taskId是否重复
        int count = logService.selectCountByTaskId(taskId);
        if (count > 0) {
            return ResponseEntity.ok(new CommonRes(500, "taskId重复：" + taskId));
        }
        //判断此类任务当前是否还有在执行的，如果有，则不执行，避免产生拥堵
        boolean flag = logService.checkTask(TaskName.TASK3);
        if (flag) {
            CommonRes commonRes = zipLogFeign.deleteAllSysLogData(taskId);
            return ResponseEntity.ok(commonRes);
        } else {
            return ResponseEntity.ok(new CommonRes(500, "此任务存在异常，本次不执行，请处理完成后再执行"));
        }
    }
}
