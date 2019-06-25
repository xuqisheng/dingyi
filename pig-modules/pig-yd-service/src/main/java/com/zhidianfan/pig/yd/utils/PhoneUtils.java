package com.zhidianfan.pig.yd.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author sjl
 * 2019-06-25 09:53
 */
public class PhoneUtils {
    private PhoneUtils() {
    }

    public static String getPhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            return StringUtils.EMPTY;
        }
        String vipPhoneTrim = phone.trim();
        if (!NumberUtils.isCreatable(vipPhoneTrim)) {
            return StringUtils.EMPTY;
        }
        if (vipPhoneTrim.length() > 11) {
            return StringUtils.EMPTY;
        }
        return vipPhoneTrim;
    }
}
