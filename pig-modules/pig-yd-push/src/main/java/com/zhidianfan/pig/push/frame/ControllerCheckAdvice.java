package com.zhidianfan.pig.push.frame;

import com.zhidianfan.pig.common.constant.ErrorTip;
import com.zhidianfan.pig.common.constant.Tip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 控制器参数异常处理
 *
 * @Author sherry
 * @Description
 * @Date Create in 2018/9/17
 * @Modified By:
 */
@ControllerAdvice
public class ControllerCheckAdvice {


    private Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Tip> checkRequest(BindException e) {


        log.info("参数校验异常");

        BindingResult bindingResult = e.getBindingResult();

        String errorMesssage = "校验失败:";

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMesssage += fieldError.getField() + ":" + fieldError.getDefaultMessage() + ";";
        }

        Tip tip = new ErrorTip(400, errorMesssage.substring(0, errorMesssage.length() - 1));
        return ResponseEntity.badRequest().body(tip);
    }

}
