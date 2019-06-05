package com.zhidianfan.pig.yd.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2019-06-03
 * @Modified By:
 */
public class PasswordCheckUtils {
    private static String code = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890,./";


    public static boolean checkSimplePassword(String pd) {


        //Step 1 判断密码长度
        if (StringUtils.isBlank(pd)) {
            System.out.println("1密码不能为空");
            return false;
        }

        if (pd.length() < 6
                || pd.length() > 16) {
            System.out.println("2密码长度不符合要求");
            return false;
        }

        //Step 2 将传递过来的密码参数打散
        char[] chars = pd.toCharArray();

        //Step 3 将能够设置密码的字符集转化为Set集合
        Set<String> s1 = getS1(code);

        //Step 4 密码字符必须全部在指定范围内
        boolean flag = false;//是否是纯数字
        for (char c : chars) {
            if (!StringUtils.isNumericSpace(String.valueOf(c))) {
                flag = true;//并不是全部都是数字
            }
            if (!s1.contains(String.valueOf(c))) {
                System.out.println("3密码不符合要求");
                return false;
            }
        }

        //Step 5 当密码是纯数字时，不允许升序或降序
        if (!flag) {//密码全部都是数字时，开始执行此校验
            //Step 6 将char[]转化为int[]
            int[] ints = getInts(chars);
            //Step 7 执行纯顺序或降序逻辑判断
            boolean f1 = checkF1(ints);
            if (!f1) {
                System.out.println("4密码不符合要求");
                return false;
            }
        }

        System.out.println("5密码符合要求");
        return true;

    }

    /**
     * @param ints
     * @return false - 密码不符合要求 true - 密码符合要求
     */
    private static boolean checkF1(int[] ints) {

        int count1 = 0;//升序的个数
        int count2 = 0;//降序的个数
        int count3 = 0;//相等的个数
        for (int i = 0; i < ints.length - 1; i++) {
            if (ints[i] < ints[i + 1]) {//当前是升序
                count1++;
            } else if (ints[i] == ints[i + 1]) {//相等
                count3++;
            } else {//当前是降序
                count2++;
            }
        }

        if (count3 == ints.length - 1) {
            System.out.println("6所有数字都相等，不符合密码要求");
            return false;
        }

        if (count1 == 0
                || count2 == 0) {
            System.out.println("7所有数字处于一种顺序，不符合要求");
            return false;
        }

        return true;
    }

    private static int[] getInts(char[] chars) {
        int[] ints = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            ints[i] = Integer.parseInt(String.valueOf(chars[i]));
        }
        return ints;
    }

    private static Set<String> getS1(String code) {
        char[] chars1 = code.toCharArray();
        Set<String> s1 = new LinkedHashSet<>();
        for (char c1 : chars1) {
            s1.add(String.valueOf(c1));
        }
        return s1;
    }

    public static void main(String[] args) {
        boolean b = checkSimplePassword("11223344");
        System.out.println(b);
    }
}

