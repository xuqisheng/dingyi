package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.zhidianfan.pig.yd.moduler.resv.dto.AllResvOrderDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.ResvOrderDTO;
import com.zhidianfan.pig.yd.moduler.resv.service.MessageService;
import com.zhidianfan.pig.yd.moduler.resv.service.ResvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/31
 * @Modified By:
 */
@RestController
@RequestMapping("/resv")
public class ResvController {

    private Logger log = LoggerFactory.getLogger(getClass());
    /**
     * 订单业务逻辑
     */
    @Autowired
    private ResvService resvService;
    /**
     * 短信逻辑
     */
    @Autowired
    private MessageService messageService;


    /**
     * 某酒店某天某餐别所有订单，含桌位，桌位订单没分离
     *
     * @param resvOrder
     * @return
     */
    @GetMapping("/resv_orders")
    public ResponseEntity getAllResvOrders(@RequestParam ResvOrderDTO resvOrder) {
        return ResponseEntity.ok(null);
    }
//
//    /**
//     * 酒店订单换桌
//     *
//     * @param exchangeTableDTO 换桌需要参数
//     * @return
//     */
//    @PostMapping("/order/exchange/table")
//    public ResponseEntity updateTable(@Valid ExchangeTableDTO exchangeTableDTO) {
//        Tip tip = resvService.updateTable(exchangeTableDTO);
//        if (tip instanceof SuccessTip) {
//            MessageResultBO messageResultBO = messageService.sendChangeTableMsg(exchangeTableDTO);
//            if (null != messageResultBO) {
//                return processResult(messageResultBO, tip);
//            }
//        }
//        return ResponseEntity.ok(tip);
//    }

//    /**
//     * 订单确认
//     *
//     * @param orderConfirmDTO
//     * @return
//     */
//    @PostMapping("/order/confirm")
//    public ResponseEntity updateConfirm(@Valid OrderConfirmDTO orderConfirmDTO) {
//        Tip tip = resvService.updateConfirm(orderConfirmDTO);
//        if (tip instanceof SuccessTip) {
//            SmsBO smsBO = messageService.sendConfirmMsg(orderConfirmDTO);
//            if (null != smsBO) {
//                return processResult(smsBO, tip);
//            }
//        }
//        return ResponseEntity.ok(tip);
//    }

//    /**
//     * 加桌
//     *
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("/order/table/add")
//    public ResponseEntity addTable(@Valid AddTableDTO addTableDTO) {
//        Tip tip = resvService.addTable(addTableDTO);
//        if (tip instanceof SuccessTip) {
//            SmsBO smsBO = messageService.sendAddTableResvMsg(addTableDTO.getResvOrder());
//            if (null != smsBO) {
//                return processResult(smsBO, tip);
//            }
//        }
//        return ResponseEntity.ok(tip);
//    }

    /**
     * 根据条件查询订单
     *
     * @param allResvOrderDTO
     * @return
     */
    @GetMapping("/orders")
    public ResponseEntity getAllResvOrders(@Valid AllResvOrderDTO allResvOrderDTO) {
        return ResponseEntity.ok(resvService.getAllResvOrders(allResvOrderDTO));
    }

//    private ResponseEntity processResult(MessageResultBO messageResultBO, Tip tip) {
//        Map<String, Object> map = Maps.newHashMap();
//        map.put("code", tip.getCode());
//        map.put("msg", tip.getMsg());
//        map.put("sms", messageResultBO);
//        return ResponseEntity.ok(map);
//    }

}
