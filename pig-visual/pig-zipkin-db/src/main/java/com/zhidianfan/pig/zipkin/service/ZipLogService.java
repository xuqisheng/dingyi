package com.zhidianfan.pig.zipkin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


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

        for (int i = 0; i < tableNames.length; i++) {
            String sql = " truncate table " + tableNames[i];
            log.info("执行sql {} 开始", sql);
            jdbcTemplate.execute(sql);
            log.info("执行sql {} 结束", sql);
        }
    }

    @Async
    public void deleteAllSysLogData(String taskId) {

        for (int i = 0; i < 10; i++) {
            String tableName = "sys_log_" + i;
            String sql = " truncate table " + tableName;
            log.info("执行sql {} 开始", sql);
            jdbcTemplate.execute(sql);
            log.info("执行sql {} 结束", sql);
        }


    }

}
