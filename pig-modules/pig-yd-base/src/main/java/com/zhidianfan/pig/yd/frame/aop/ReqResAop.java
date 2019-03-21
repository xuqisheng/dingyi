package com.zhidianfan.pig.yd.frame.aop;

import com.zhidianfan.pig.common.constant.QueueName;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.common.util.UserUtils;
import com.zhidianfan.pig.yd.moduler.common.dto.LogApi;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 全局请求响应日志打印
 * Created by nbcoolkid on 2017-11-21.
 */
@Aspect
@Component
@Slf4j
public class ReqResAop {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.GetMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.PostMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.PutMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void performance() {
    }

    @Around("performance()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        LogApi logApi = new LogApi();
        logApi.setMicroName("pig-yd-base");

        long start = System.currentTimeMillis();
        logApi.setReqTime(new Date(start));


        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String url = request.getRequestURI();
        logApi.setUrl(url);

        Object[] objects = proceedingJoinPoint.getArgs();
        Object result = null;
        try {
            String reqStr = getReq(objects);
            logApi.setReqData(reqStr);


            result = proceedingJoinPoint.proceed();// result的值就是被拦截方法的返回值
            String resStr = JsonUtils.obj2Json(result);
            logApi.setResData(resStr);
            long end = System.currentTimeMillis();
            logApi.setResTime(new Date(end));


            log.info("\n===============请求===============\n" +
                            "url：{}\n" +
                            "{}\n" +
                            "===============响应===============\n" +
                            "{}\n" +
                            "=============耗时：{} 毫秒============================"
                    , url, reqStr, resStr, (end - start));
            log.info("===============END===============");

            String clientId = UserUtils.getClientId();
            String userId = UserUtils.getUserName();
            logApi.setClientId(clientId);
            logApi.setUserId(userId);

            rabbitTemplate.convertAndSend(QueueName.LOG_API, JsonUtils.obj2Json(logApi));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            logApi.setResTime(new Date());
            logApi.setResData(e.getMessage());
            logApi.setNote("AOP检测到异常");
            rabbitTemplate.convertAndSend(QueueName.LOG_API, JsonUtils.obj2Json(logApi));

            //这里不抛出异常的话，RestControllerAdvice就拦截不到了，因为此处捕获了
            throw new RuntimeException(e.getMessage());
        }

        return result;

    }

    private String getReq(Object[] objects) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object o : objects) {
            if (o instanceof BindingResult
                    || o instanceof HttpServletRequest
                    || o instanceof HttpServletResponse
                    ) {
                continue;
            }
            stringBuilder.append(JsonUtils.obj2Json(o));
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
