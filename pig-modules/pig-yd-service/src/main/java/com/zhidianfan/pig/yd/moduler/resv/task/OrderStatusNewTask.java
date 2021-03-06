package com.zhidianfan.pig.yd.moduler.resv.task;

import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.resv.service.OrderStatusNewTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: hzp
 * @Date: 2019-04-30 14:24
 * @Description: 更新订单状态 update_order_status_new 去除存储过程
 */
@RestController
@RequestMapping("/orderstatus")
@Slf4j
public class OrderStatusNewTask {


    @Autowired
    private OrderStatusNewTaskService orderStatusNewTaskService;

    @PostMapping("/update")
    public ResponseEntity updateOrderStatusNew(Integer intervalNum) {

        long l = System.currentTimeMillis();
        log.info("----任务开始-----" + l + "" );

        //更新宴会订单
        orderStatusNewTaskService.updateMeetingOrder(intervalNum);

        //更新标准版订单
        orderStatusNewTaskService.updateOrder(intervalNum);

        //更新安卓电话机订单
        orderStatusNewTaskService.updateOrderAndroid(intervalNum);

        long currentTimeMillis = System.currentTimeMillis();
        log.info("-----任务结束,总耗时----" + (currentTimeMillis - l) + "" );

        return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
    }

}

