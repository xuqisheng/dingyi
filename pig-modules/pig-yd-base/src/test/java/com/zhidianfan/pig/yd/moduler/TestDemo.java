package com.zhidianfan.pig.yd.moduler;

import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.BasePushLog;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/24
 * @Modified By:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestDemo {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test2() {
        List<BasePushLog> list = new ArrayList<>();
        BasePushLog basePushLog = new BasePushLog();
        basePushLog.setNote("哇哈哈1");
        list.add(basePushLog);
        for (BasePushLog basePushLog1 : list) {
            if (basePushLog1.getNote().equals("哇哈哈1")) {
                basePushLog1.setNote("哇哈哈2");
                break;
            }
            System.out.println("执行到此");
        }
        System.out.println(list);
    }

    @Test
    public void test1() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("msg", "酒店订单更新");
        System.out.println(JsonUtils.obj2Json(map));
    }

    @Test
    public void test3() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            long l1 = getNextDateId("TT");
            System.out.println(l1);
            TimeUnit.SECONDS.sleep(1);
        }
    }

    private long getNextDateId(String type) {
        String todayStr = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMdd");//历史遗留Key暂不考虑，一天也就一个。
        long l2 = redisTemplate.opsForValue().increment("PUSH:" + type + ":" + todayStr, 1);
        String s1 = StringUtils.leftPad("" + l2, 7, "0");
        return Long.parseLong(todayStr + s1);
    }

}
