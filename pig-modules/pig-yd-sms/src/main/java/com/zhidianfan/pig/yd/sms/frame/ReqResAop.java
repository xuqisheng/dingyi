package com.zhidianfan.pig.yd.sms.frame;

import com.zhidianfan.pig.common.constant.QueueName;

import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.common.util.UserUtils;
import com.zhidianfan.pig.yd.sms.dto.LogApi;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


/**
 * @Author huzp
 * @Description
 * @Date Create in 2018/10/31
 * @Modified By:
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
            "||@annotation(org.springframework.web.bind.annotation.DeleteMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void performance() {

    }

    @Around("performance()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

        LogApi baseLog = new LogApi();
        baseLog.setMicroName("pig-yd-sms");

        long start = System.currentTimeMillis();
        baseLog.setReqTime(new Date(start));

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        String requestURI = request.getRequestURI();
        baseLog.setUrl(requestURI);

        Object[] args = proceedingJoinPoint.getArgs();
        Object result ;

        try {
            String reqData = getReqData(args);
            baseLog.setReqData(reqData);
            result = proceedingJoinPoint.proceed();
            String resStr = JsonUtils.obj2Json(result);
            baseLog.setResData(resStr);
            long end = System.currentTimeMillis();
            baseLog.setResTime(new Date(end));
            log.info("\n===============请求===============\n" +
                            "url：{}\n" +
                            "{}\n" +
                            "===============响应===============\n" +
                            "{}\n" +
                            "=============耗时：{} 毫秒============================"
                    , requestURI, reqData, resStr, (end - start));
            log.info("===============END===============");
            String clientId = UserUtils.getClientId();
            String userId = UserUtils.getUserName();
            baseLog.setClientId(clientId);
            baseLog.setUserId(userId);

            rabbitTemplate.convertAndSend(QueueName.LOG_API, JsonUtils.obj2Json(baseLog));

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            baseLog.setResData(e.getMessage());
            baseLog.setResTime(new Date());
            baseLog.setNote("AOP检测到异常");
            rabbitTemplate.convertAndSend(QueueName.LOG_API, JsonUtils.obj2Json(baseLog));
            //这里不抛出异常的话，RestControllerAdvice就拦截不到了，因为此处捕获了
            throw new RuntimeException(e.getMessage());
        }

        return result;
    }

    private String getReqData(Object[] args) {

        StringBuilder stringBuilder = new StringBuilder();

        for (Object arg : args) {
            if (arg instanceof BindingResult
                    || arg instanceof HttpServletRequest
                    || arg instanceof HttpServletResponse)
                continue;

            stringBuilder.append(JsonUtils.obj2Json(arg));
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }


}

