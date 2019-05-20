package com.zhidianfan.pig.yd.moduler.meituan.controller;

import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.meituan.bo.TianGangOrderBO;
import com.zhidianfan.pig.yd.moduler.meituan.service.TianGangService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.text.ParseException;

/**
 * @Author qqx
 * @Description 易订接收天港钉钉订单
 * @Date Create in 2019-05-15
 * @Modified By:
 */
@RestController
@RequestMapping("/dd")
@Slf4j
public class TianGangController {

    @Autowired
    TianGangService tianGangService;

    /**
     * 接收天港订单 在易订系统创建第三方订单
     * @param tianGangOrderBO
     * @return
     */
    @PostMapping(value = "/create/order")
    public ResponseEntity createTianGangOrder(@Valid TianGangOrderBO tianGangOrderBO) throws ParseException {

        Tip tip = tianGangService.createOrder(tianGangOrderBO);

        return ResponseEntity.ok(tip);
    }



}
