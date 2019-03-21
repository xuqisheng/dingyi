/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.zhidianfan.pig.common.util;

import com.zhidianfan.pig.common.constant.CommonConstant;
import com.zhidianfan.pig.common.constant.SuccessTip;
import com.zhidianfan.pig.common.constant.Tip;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.Optional;

/**
 * @author lengleng
 * @date 2017/12/22
 */
public class UserUtilsTest {
    @Test
    public void getToken() throws Exception {
        String authorization = null;
        System.out.println(StringUtils.substringAfter(authorization, CommonConstant.TOKEN_SPLIT));
    }

    @Test
    public void optionalTest() {
        Optional<String> optional = Optional.ofNullable("");
        System.out.println(optional.isPresent());
    }

    @Test
    public void test1(){
        Tip tip = new SuccessTip(200,"成功");
        String s1 = JsonUtils.obj2Json(tip);
        System.out.println(s1);
        Tip tip1 = JsonUtils.jsonStr2Obj(s1,SuccessTip.class);
        System.out.println(tip1);
    }

}