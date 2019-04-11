package com.zhidianfan.pig.yd.moduler.resv.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.resv.bo.ResvOrderThirdBO;
import com.zhidianfan.pig.yd.moduler.resv.dto.ThirdQueryDTO;
import com.zhidianfan.pig.yd.moduler.resv.service.ThirdOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 第三方平台订单接口
 *
 * @Author: huzp
 * @Date: 2018/9/20 14:25
 */
@RestController
@RequestMapping("/thirdOrder")
public class ThirdOrderController {


    @Autowired
    ThirdOrderService thirdOrderService;


    /**
     * 查询第三方平台所有订单
     *
     * @param thirdQueryDTO 查询条件，酒店id必须
     * @return 返回第三方平台订单
     */
    @GetMapping(value = "/conditionorder")
    public ResponseEntity getOrderByBussinessId(ThirdQueryDTO thirdQueryDTO) {

        Page<ResvOrderThirdBO> resvOrderThird = thirdOrderService.getConditionThirdOrder(thirdQueryDTO);

        return ResponseEntity.ok(resvOrderThird);
    }

    /**
     * 查询第三方平台集团下所有订单
     *
     * @param thirdQueryDTO 查询条件，酒店id必须
     * @return 返回第三方平台订单
     */
    @GetMapping(value = "/conditionorder/all")
    public ResponseEntity getAllOrderByBussinessId(ThirdQueryDTO thirdQueryDTO) {

        List<ResvOrderThirdBO> resvOrderThird = thirdOrderService.getAllConditionThirdOrder(thirdQueryDTO);

        return ResponseEntity.ok(resvOrderThird);
    }

    /**
     * 拒绝第三方订单
     *
     * @param orderno 第三方订单id
     * @return 操作是否成功
     */
    @PostMapping(value = "/order")
    public ResponseEntity rejectOrder(@RequestParam String orderno) {

        boolean b = thirdOrderService.rejectOrder(orderno);

        Tip tip = (b ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP);

        return ResponseEntity.ok(tip);
    }

    /**
     * 获取一个酒店最新未读的第三方订单信息
     *
     * @param businessId 酒店id
     * @return 第三方订单信息
     */
    @GetMapping(value = "/newestorder")
    public ResponseEntity getNewestOrder(@RequestParam Integer businessId) {

        ResvOrderThirdBO newestOrder = thirdOrderService.getNewestOrder(businessId);

        return ResponseEntity.ok(newestOrder);
    }


    /**
     * 退订消息知道了
     */
    @PostMapping(value = "/unsubscribeorder")
    public ResponseEntity readUnsubscribeMes(@RequestParam String thridNo ) {

        boolean b  = thirdOrderService.readUnsubscribeOrder(thridNo);

        Tip tip = (b ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP);


        return ResponseEntity.ok(tip);
    }


}
