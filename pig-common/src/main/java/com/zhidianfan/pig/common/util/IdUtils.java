package com.zhidianfan.pig.common.util;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/11/6
 * @Modified By:
 */

public class IdUtils {

    private static final SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);

    public static long getNextId() {
        long id = idWorker.nextId();
        return id;
    }

    public static String getNextId(String type) {
        long id = idWorker.nextId();
        return type + id;

    }

}
