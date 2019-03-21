package com.zhidianfan.pig.zipkin.service;

import com.zhidianfan.pig.common.constant.QueueName;
import com.zhidianfan.pig.common.constant.TaskName;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.zipkin.dto.LogTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/10/26
 * @Modified By:
 */
@Service
public class ZipLogService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String[] tableNames = {"zipkin_spans", "zipkin_dependencies", "zipkin_annotations"};

    /**
     * 对3张zip进行分批批量删除
     */
    @Async
    public void deleteAllZipkinData(String taskId) {
        LogTask logTask = new LogTask();
        logTask.setTaskId(taskId);//任务id
        logTask.setTaskName(TaskName.TASK1);
        int seq = 0;
        logTask.setSeq(seq + "");
        long start = System.currentTimeMillis();
        logTask.setStartTime(new Date(start));

        logTask.setNote("清理zipkin表任务======start");
        logTask.setInsertTime(new Date(start));
        try {
            log.info("清理zipkin表任务======start");
            rabbitTemplate.convertAndSend(QueueName.LOG_TASK, JsonUtils.obj2Json(logTask));

            seq = this.deleteTables(logTask, tableNames, seq);
        } catch (Exception e) {
            long end = System.currentTimeMillis();
            logTask.setEndTime(new Date(end));
            logTask.setSpeed((int) (end - start));

            logTask.setNote(e.getMessage());
            logTask.setInsertTime(new Date());
            logTask.setSeq(++seq + "");
            logTask.setResult(0);
            rabbitTemplate.convertAndSend(QueueName.LOG_TASK, JsonUtils.obj2Json(logTask));
        }

        long end = System.currentTimeMillis();
        logTask.setSeq(++seq + "");
        logTask.setEndTime(new Date(end));
        logTask.setSpeed((int) (end - start));
        logTask.setResult(1);
        logTask.setNote("清理zipkin表任务======end");

        rabbitTemplate.convertAndSend(QueueName.LOG_TASK, JsonUtils.obj2Json(logTask));
        log.info("清理zipkin表任务======end");

    }

    @Async
    public void deleteAllSysLogData(String taskId) {
        LogTask logTask = new LogTask();
        logTask.setTaskId(taskId);//任务id
        logTask.setTaskName(TaskName.TASK3);
        int seq = 0;
        logTask.setSeq(seq + "");
        long start = System.currentTimeMillis();
        logTask.setStartTime(new Date(start));

        logTask.setNote(TaskName.TASK3 + "======start");
        logTask.setInsertTime(new Date(start));
        try {
            log.info(TaskName.TASK3 + "======start");
            rabbitTemplate.convertAndSend(QueueName.LOG_TASK, JsonUtils.obj2Json(logTask));

            String[] tableNames = new String[10];
            for (int i = 0; i < 10; i++) {
                String tableName = "sys_log_" + i;
                tableNames[i] = tableName;
            }
            seq = this.deleteTables(logTask, tableNames, seq);
        } catch (Exception e) {
            long end = System.currentTimeMillis();
            logTask.setEndTime(new Date(end));
            logTask.setSpeed((int) (end - start));

            logTask.setNote(e.getMessage());
            logTask.setInsertTime(new Date());
            logTask.setSeq(++seq + "");
            logTask.setResult(0);
            rabbitTemplate.convertAndSend(QueueName.LOG_TASK, JsonUtils.obj2Json(logTask));
        }

        long end = System.currentTimeMillis();
        logTask.setSeq(++seq + "");
        logTask.setEndTime(new Date(end));
        logTask.setSpeed((int) (end - start));
        logTask.setResult(1);
        logTask.setNote(TaskName.TASK3 + "======end");

        rabbitTemplate.convertAndSend(QueueName.LOG_TASK, JsonUtils.obj2Json(logTask));
        log.info(TaskName.TASK3 + "======end");

    }

    private int deleteTables(LogTask logTask, String[] tableNames, int seq) {
        for (String tableName : tableNames) {
            while (true) {
                //一次删除10000条
                int count = jdbcTemplate.update("DELETE FROM " + tableName + " LIMIT 10000");
                log.info("清理：{} ,{} 条", tableName, count);

                logTask.setNote("清理：" + tableName + "," + count + " 条");
                logTask.setInsertTime(new Date());
                logTask.setSeq(++seq + "");
                rabbitTemplate.convertAndSend(QueueName.LOG_TASK, JsonUtils.obj2Json(logTask));

                if (count < 1000) {

                    logTask.setNote("清理：" + tableName + "," + count + " 条，本次清理此表结束");
                    logTask.setInsertTime(new Date());
                    logTask.setSeq(++seq + "");
                    rabbitTemplate.convertAndSend(QueueName.LOG_TASK, JsonUtils.obj2Json(logTask));
                    break;
                }
            }
        }

        return seq;

    }
}
