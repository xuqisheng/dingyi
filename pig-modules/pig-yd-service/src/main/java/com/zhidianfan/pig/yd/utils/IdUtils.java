package com.zhidianfan.pig.yd.utils;

import com.zhidianfan.pig.yd.utils.support.SnowflakeIdWorker;

/**
 * @author danda
 */
public class IdUtils {
    private static SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);

    /**
     * 生成订单标编号
     *
     * @return 订单编号
     */
    public static String makeOrderNo() {

        return String.valueOf(idWorker.nextId());
    }
}
