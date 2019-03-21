package com.zhidianfan.pig.gateway.component.filter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zhidianfan.pig.common.constant.QueueName;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.common.util.UserUtils;
import com.zhidianfan.pig.gateway.component.dto.LogApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class PrintRequestLogFilter extends ZuulFilter {


    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(PrintRequestLogFilter.class);

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;//要打印返回信息，必须得用"post"
    }

    @Override
    public int filterOrder() {
        return 999;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }


    @Override
    public Object run() {

        log.info("网关日志打印过滤器=====start=======");

        try {
            LogApi logApi = new LogApi();
            logApi.setMicroName("pig-gateway");
            logApi.setReqTime(new Date());

            RequestContext ctx = RequestContext.getCurrentContext();
            HttpServletRequest request = ctx.getRequest();
            InputStream in = request.getInputStream();
            String reqBbody = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
            logApi.setReqData(reqBbody);
            // 打印请求方法，路径
            PrintRequestLogFilter.LOGGER
                    .info("request url:\t" + request.getMethod() + "\t" + request.getRequestURL().toString());
            logApi.setUrl(request.getRequestURL().toString());
            Map<String, String[]> map = request.getParameterMap();
            String auth = request.getHeader("Authorization");
            log.info("Header Auth:{}", auth);
            // 打印请求url参数
            if (map != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("request parameters:\t");
                for (Map.Entry<String, String[]> entry : map.entrySet()) {
                    sb.append("[" + entry.getKey() + "=" + printArray(entry.getValue()) + "]");
                }
                PrintRequestLogFilter.LOGGER.info(sb.toString());
                logApi.setReqData(logApi.getReqData() + "\n" + sb.toString());
            }
            // 打印请求json参数
            if (reqBbody != null) {
                PrintRequestLogFilter.LOGGER.info("request body:\t" + reqBbody);
            }

            // 打印response
            InputStream out = ctx.getResponseDataStream();
            String outBody = StreamUtils.copyToString(out, Charset.forName("UTF-8"));
            if (outBody != null) {
                PrintRequestLogFilter.LOGGER.info("response body:\t" + outBody);
            }

            ctx.setResponseBody(outBody);//重要！！！

            logApi.setResTime(new Date());
            logApi.setResData(outBody);

            String clientId = UserUtils.getClientId();
            String userId = UserUtils.getUserName();
            logApi.setClientId(clientId);
            logApi.setUserId(userId);
            log.info("发送全局日志到消息中心：{}\n{}", QueueName.LOG_API, JsonUtils.obj2Json(logApi));
            rabbitTemplate.convertAndSend(QueueName.LOG_API, JsonUtils.obj2Json(logApi));
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("网关日志打印过滤器=====end=======");
        return null;
    }


    String printArray(String[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}
