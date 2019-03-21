package com.zhidianfan.pig.yd.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/9/28
 * @Modified By:
 */

public class PasswordUtilsTest {

    @Test
    public void encode() {
    }

    @Test
    public void matches() {
        String password = "123456";
        boolean b3 = PasswordUtils.matches(password,"$2a$10$8/hcFElhCjTnAYQC0YcsN.H.JLLDJslJAsxnkTh1P0lC/fLhP8dFy");
        System.out.println(b3);
        System.out.println(PasswordUtils.encode(password));
    }
}