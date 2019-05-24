package com.zhidianfan.pig.yd.moduler.resv.service;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author sjl
 * 2019-05-24 11:27
 */
public class OtherTest {

    @Test
    public void testLocalDate() {
        // 2019-05-24 11:25:46
        // 2019-05-24 11:25:50
        LocalDateTime start = LocalDateTime.of(2019, 5, 24, 11, 25, 46);
        LocalDateTime end = LocalDateTime.of(2019, 5, 24, 11, 25, 50);
        Duration duration = Duration.between(start, end);
        long seconds = duration.getSeconds();
        int s = (int) seconds;
        System.out.println(seconds);
    }
}
