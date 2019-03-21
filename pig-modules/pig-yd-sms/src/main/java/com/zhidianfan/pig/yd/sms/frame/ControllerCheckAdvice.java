package com.zhidianfan.pig.yd.sms.frame;


import com.zhidianfan.pig.yd.sms.dto.ErrorTip;
import com.zhidianfan.pig.yd.sms.dto.Tip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @Author huzp
 * @Description
 * @Date Create in 2018/10/31
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
