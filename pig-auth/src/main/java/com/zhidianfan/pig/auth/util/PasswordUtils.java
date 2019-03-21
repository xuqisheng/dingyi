package com.zhidianfan.pig.auth.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/28
 * @Modified By:
 */

public class PasswordUtils {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    /**
     * 加密
     * @param pass
     * @return
     */
    public static String encode(String pass){
        return ENCODER.encode(pass);
    }

    public static boolean matches(String password,String password1){
        return ENCODER.matches(password,password1);
    }
}
