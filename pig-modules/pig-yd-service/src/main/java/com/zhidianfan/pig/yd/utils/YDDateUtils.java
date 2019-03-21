package com.zhidianfan.pig.yd.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * @Description:
 * @User: ljh
 * @Date: 2018-11-21
 * @Time: 09:44
 */
@Slf4j
public class YDDateUtils {

    private static final String DATE_FORMAT_TYPE="yyyy-MM-dd";
    private static final String DATETIME_FORMAT_TYPE="yyyy-MM-dd HH:mm:ss";


    public  static String formatDate(Date date){
        DateFormat sf = new SimpleDateFormat(DATE_FORMAT_TYPE);
        String format = sf.format(date);
        return format;
    }

    public  static String formatDateTime(Date dateTime){

        DateFormat sf = new SimpleDateFormat(DATETIME_FORMAT_TYPE);
        return  sf.format(dateTime);
    }


    public  static Date formatDate(String date){
        DateFormat sf = new SimpleDateFormat(DATE_FORMAT_TYPE);
        Date parse = null;
        try {
            parse = sf.parse(date);
        } catch (ParseException e) {
           log.error("时间格式化错误:"+e.getMessage());
        }
        return parse;
    }

    public  static Date formatDateTime(String dateTime){

        DateFormat sf = new SimpleDateFormat(DATETIME_FORMAT_TYPE);
        Date parse = null;
        try {
            parse = sf.parse(dateTime);
        } catch (ParseException e) {
            log.error("时间格式化错误:"+e.getMessage());
        }
        return  parse;
    }




}
