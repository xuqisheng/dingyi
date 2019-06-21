package com.zhidianfan.pig.yd.moduler.resv.constants;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author sjl
 * 2019-05-23 09:27
 */
public abstract class CustomerValueConstants {

    public static final int DEFAULT_NON_AGE = -1;
    public static final LocalDateTime DEFAULT_START_TIME = LocalDateTime.of(2000, 1, 1, 0, 0, 0);
    public static final LocalDateTime DEFAULT_END_TIME = DEFAULT_START_TIME;
    public static final LocalDateTime DEFAULT_FIRST_TIME = DEFAULT_START_TIME;
    public static final int DEFAULT_CUSTOMER_AVG = 0;
    public static final int DEFAULT_SPEND_TIME = -1;
    public static final long DEFAULT_USER_ID = 10000;

    /**
     * 0-未开始
     */
    public static final int NON_EXECUTE = 0;
    /**
     * 1-执行中
     */
    public static final int EXECUTING = 1;
    /**
     * 2-执行成功
     */
    public static final int EXECUTE_SUCCESS = 2;
    /**
     * 3-执行异常
     */
    public static final int EXECUTE_EXCEPTION = 3;

    /**
     * 意向客户
     */
    public static final int INTENTION_CUSTOMER = 1;

    /**
     * 活跃客户
     */
    public static final int ACTIVE_CUSTOMER = 2;

    /**
     * 沉睡客户
     */
    public static final int SLEEP_CUSTOMER = 3;

    /**
     * 流失客户
     */
    public static final int LOSS_CUSTOMER = 4;

    public static final int DEFAULT_PAYAMOUNT = 0;
    public static final int DEFAULT_ORDER_COUNT = 1;

//    记录类型，1-消费订单，2-退订订单|1,2 预订订单，3-主客订单，4-宾客订单|3，4 主/宾客订单，5-价值变更，6-营销经理变更
    public static final int RECORD_TYPE_CUSTOMER = 1;
    public static final int RECORD_TYPE_ESC = 2;
    public static final int RECORD_TYPE_MAN = 3;
    public static final int RECORD_TYPE_GUEST = 4;
    public static final int RECORD_TYPE_VALUE_CHANGE = 5;
    public static final int RECORD_TYPE_APP_USER_CHANGE = 6;

    /**
     * 定时任务开始时间
     */
    public static final LocalTime TASK_START_TIME = LocalTime.of(21, 30, 0);
    /**
     * 定时任务结束时间
     */
    public static final LocalTime TASK_END_TIME = LocalTime.of(8, 0, 0);
}
