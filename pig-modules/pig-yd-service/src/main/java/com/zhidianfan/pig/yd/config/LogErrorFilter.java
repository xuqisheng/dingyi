package com.zhidianfan.pig.yd.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;


/**
 * @Author: huzp
 * @Date: 2018/10/9 15:18
 */
public class LogErrorFilter extends Filter<ILoggingEvent> {

    @Override
    public FilterReply decide(ILoggingEvent event) {

        String loggerName = event.getLoggerName();

        //判断日志级别在error或者error以上
        if (!(event.getLevel().levelInt >= 40000)
                || getPackName(loggerName).equals("com.zhidianfan.pig.yd.moduler.order.controller")
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
