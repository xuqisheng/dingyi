package com.zhidianfan.pig.log.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.common.constant.QueueName;
import com.zhidianfan.pig.common.constant.SuccessTip;
import com.zhidianfan.pig.common.constant.TaskName;
import com.zhidianfan.pig.common.constant.Tip;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.log.dao.entity.ConfigLogLifeCycle;
import com.zhidianfan.pig.log.dao.entity.LogTask;
import com.zhidianfan.pig.log.dao.service.IConfigLogLifeCycleService;
import com.zhidianfan.pig.log.dao.service.ILogTaskService;
import com.zhidianfan.pig.log.feign.YdServiceLogFeign;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/10/26
 * @Modified By:
 */
@Service
public class LogService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ILogTaskService logTaskService;
    @Autowired
    private IConfigLogLifeCycleService configLogLifeCycleService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private YdServiceLogFeign ydServiceLogFeign;

    /**
     * 检查指定任务是否全部是正常执行完成的
     *
     * @param taskName 任务名
     * @return
     */
    public boolean checkTask(String taskName) {

        //Step 1 找到最近一次任务的taskid
        List<LogTask> logTasks = logTaskService.selectList(new EntityWrapper<LogTask>()
                .eq("task_name", taskName)
                .eq("seq", "0")
                .ge("insert_time", DateUtils.addDays(new Date(), -30))//只判断30天内的数据
                .orderBy("insert_time", false)
                .last("limit 1"));//30天内，开始的所有任务的第一个执行步骤
        if (!CollectionUtils.isEmpty(logTasks)) {
            LogTask logTask = logTasks.get(0);
            //Step 2 找到当时的taskId
            String taskId = logTask.getTaskId();
            //Step 3 找到此taskId对应的最后一步
            List<LogTask> logTasks1 = logTaskService.selectList(new EntityWrapper<LogTask>()
                    .eq("task_name", taskName)
                    .eq("task_id", taskId)
                    .isNotNull("speed")
                    .last("limit 1"));
            if (CollectionUtils.isEmpty(logTasks1)) {
                //TODO 发送消息到监控模块，让管理员处理
                System.out.println("//TODO 发送消息到监控模块，让管理员处理");
                return false;
            }

        }

        return true;
    }

    public int selectCountByTaskId(String taskId) {
        int count = logTaskService.selectCount(new EntityWrapper<LogTask>()
                .eq("task_id", taskId));
        return count;
    }

    /**
     * 根号配置清除表的数据
     *
     * @param taskId
     * @return
     */
    @Async
    public void deleteAllLogByConfig(String taskId) {

        LogTask logTask = new LogTask();
        logTask.setTaskId(taskId);//任务id
        logTask.setTaskName(TaskName.TASK2);
        int seq = 0;
        logTask.setSeq(seq + "");
        long start = System.currentTimeMillis();
        logTask.setStartTime(new Date(start));

        logTask.setNote(TaskName.TASK2 + "======start");
        logTask.setInsertTime(new Date(start));

        log.info(TaskName.TASK2 + "======start");
        rabbitTemplate.convertAndSend(QueueName.LOG_TASK, JsonUtils.obj2Json(logTask));

        List<ConfigLogLifeCycle> list = configLogLifeCycleService.selectList(null);

        try {
            for (ConfigLogLifeCycle configLogLifeCycle : list) {
                if (2 == configLogLifeCycle.getConfigType()) {//保留指定天数
                    while (true) {

                        int count = deleteLog(configLogLifeCycle, 10000);

                        log.info("清理：" + configLogLifeCycle.getTableName() + "," + count + " 条");

                        logTask.setNote("清理：" + configLogLifeCycle.getTableName() + "," + count + " 条");
                        logTask.setInsertTime(new Date());
                        logTask.setSeq(++seq + "");
                        rabbitTemplate.convertAndSend(QueueName.LOG_TASK, JsonUtils.obj2Json(logTask));

                        if (count < 10000) {

                            logTask.setNote("清理：" + configLogLifeCycle.getTableName() + "," + count + " 条，本次清理此表结束");
                            logTask.setInsertTime(new Date());
                            logTask.setSeq(++seq + "");
                            rabbitTemplate.convertAndSend(QueueName.LOG_TASK, JsonUtils.obj2Json(logTask));

                            break;
                        }

                    }

                }
                //TODO 其他方式的清除逻辑

            }

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
        logTask.setNote(TaskName.TASK2 + "======end");

        rabbitTemplate.convertAndSend(QueueName.LOG_TASK, JsonUtils.obj2Json(logTask));
        log.info(TaskName.TASK2 + "======end");

    }

    private int deleteLog(ConfigLogLifeCycle configLogLifeCycle, int i) {
        int deleteCount = 0;
        if ("yiding_log".equalsIgnoreCase(configLogLifeCycle.getDataSourceName())
                || StringUtils.isEmpty(configLogLifeCycle.getDataSourceName())) {
            deleteCount = jdbcTemplate.update("DELETE FROM " + configLogLifeCycle.getTableName() + " " +
                    "WHERE " + configLogLifeCycle.getConfig2() + " < " +
                    "'" + DateFormatUtils.format(DateUtils.addDays(new Date(), (0 - configLogLifeCycle.getConfig1())), "yyyy-MM-dd") + "' " +
                    "limit " + i);
        } else if ("resv_sys".equalsIgnoreCase(configLogLifeCycle.getDataSourceName())) {
            Tip tip = ydServiceLogFeign.deleteByConfig(configLogLifeCycle.getTableName(), configLogLifeCycle.getConfig2(), i);
            if (tip instanceof SuccessTip) {
                deleteCount = Integer.parseInt(tip.getMsg());
            }
        }


        return deleteCount;

    }
}
