package com.zhidianfan.pig.yd.utils;

import io.jsonwebtoken.SignatureException;

import java.security.MessageDigest;

/**
 * Created by chenxin on 2017/9/29.
 */

public class Sha1Util {
    public static String SHA1(String decrypt){
        try {
            //指定sha1算法
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(decrypt.getBytes("utf-8"));
            //获取字节数组
            byte[] messageDigest = digest.digest();
            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new SignatureException("SHA1 calculate error");
        }
    }
}
