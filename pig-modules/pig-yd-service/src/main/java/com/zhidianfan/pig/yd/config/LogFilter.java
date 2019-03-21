package com.zhidianfan.pig.yd.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;


/**
 * @Author: huzp
 * @Date: 2018/9/28 11:18
 */
public class LogFilter  extends Filter<ILoggingEvent> {

    @Override
    public FilterReply decide(ILoggingEvent event) {

        //当前打印日志的类
        String loggerName = event.getLoggerName();

        //
        if (getPackName(loggerName).equals("com.zhidianfan.pig.yd.moduler.order.controller")
                || getPackName(loggerName).equals("com.zhidianfan.pig.yd.moduler.order.task"))
        {
            return FilterReply.DENY;
        } else{
            return FilterReply.ACCEPT;
        }
    }

    private String getPackName(String className){
        String packName = className.substring(0,className.lastIndexOf("."));
        return packName;
    }
}
