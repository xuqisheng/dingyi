package com.zhidianfan.pig.yd.moduler.resv.task;

import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.resv.service.OrderStatusNewTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: hzp
 * @Date: 2019-04-30 14:24
 * @Description: update_order_status_new 存储过程 修改为 对应的java 逻辑
 */
@RestController
@RequestMapping("/orderstatus")
public class OrderStatusNewTask {


    @Autowired
    private OrderStatusNewTaskService orderStatusNewTaskService;

    @PostMapping("/update")
    public ResponseEntity updateOrderStatusNew(Integer intervalNum) {

        //更新宴会订单
        orderStatusNewTaskService.updateMeetingOrder(intervalNum);


        //更新标准版订单
        orderStatusNewTaskService.updateOrder(intervalNum);

        //更新安卓电话机订单
        orderStatusNewTaskService.updateOrderAndroid(intervalNum);


        return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
    }

}

