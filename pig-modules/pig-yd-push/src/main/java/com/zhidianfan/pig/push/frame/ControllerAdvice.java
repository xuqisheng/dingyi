package com.zhidianfan.pig.push.frame;

import com.zhidianfan.pig.common.constant.ErrorTip;
import com.zhidianfan.pig.common.constant.Tip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 控制器参数异常处理
 *
 * @Author sherry
 * @Description
 * @Date Create in 2018/9/17
 * @Modified By:
 */
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {


    private Logger log = LoggerFactory.getLogger(getClass());



    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Tip> runtimeException(RuntimeException e) {

        String message = e.getMessage();

        log.warn("runtimeException"+message);
        Tip tip = new ErrorTip(400, message);
        return ResponseEntity.badRequest().body(tip);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Tip> exception(Exception e) {

        String message = e.getMessage();
        String errorMesssage = "推送模块繁忙:";
        log.error("推送模块繁忙"+message);
        Tip tip = new ErrorTip(500, errorMesssage+message);
        return ResponseEntity.badRequest().body(tip);
    }


}
