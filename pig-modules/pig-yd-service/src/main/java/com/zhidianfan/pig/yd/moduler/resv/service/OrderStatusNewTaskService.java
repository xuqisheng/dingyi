package com.zhidianfan.pig.yd.moduler.resv.service;

import com.zhidianfan.pig.yd.moduler.common.service.IResvMeetingOrderService;
import com.zhidianfan.pig.yd.moduler.common.service.IResvOrderAndroidService;
import com.zhidianfan.pig.yd.moduler.common.service.IResvOrderLogsService;
import com.zhidianfan.pig.yd.moduler.common.service.IResvOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author: hzp
 * @Date: 2019-04-30 15:02
 * @Description:
 */
@Service
@Slf4j
public class OrderStatusNewTaskService {


    @Autowired
    private IResvOrderLogsService iResvOrderLogsService;

    @Autowired
    private IResvMeetingOrderService iResvMeetingOrderService;

    @Autowired
    private IResvOrderAndroidService iResvOrderAndroidService;

    @Autowired
    private IResvOrderService iResvOrderService;


    @Async
    public void updateMeetingOrder(Integer intervalNum) {

        //插入Meeting 2 --> 3的日志
        iResvOrderLogsService.insertMeetingOrderStatus2TO3(intervalNum);
        //更新宴会订单2 --> 3
        iResvMeetingOrderService.updateMeetingOrderStatus2TO3(intervalNum);

        //插入Meeting 1-->6的日志
        iResvOrderLogsService.insertMeetingOrderStatus1TO6(intervalNum);
        //更新宴会订单 1 -->6
        iResvMeetingOrderService.updateMeetingOrderStatus1TO6(intervalNum);


    }

    @Async
    public void updateOrder(Integer intervalNum) {


        //插入Order日志 1-->2
        iResvOrderLogsService.insertOrderStatus1TO2(intervalNum);
        //更新订单 1-->2
        iResvOrderService.updateOrderStatus1TO2(intervalNum);

        //更新订单状态为退订 4
        iResvOrderService.updateOrderStatus1TO4(intervalNum);

        //插入Order日志 2-->3
        iResvOrderLogsService.insertOrderStatus2TO3(intervalNum);
        //更新订单 2-->3
        iResvOrderService.updateOrderStatus2TO3(intervalNum);


    }

    @Async
    public void updateOrderAndroid(Integer intervalNum) {

        //插入安卓电话机订单日志 1-->2
        iResvOrderLogsService.insertAndroidOrderStatus1TO2(intervalNum);
        //更新安卓电话机订单 1-->2
        iResvOrderAndroidService.updateAndroidOrderStatus1TO2(intervalNum);

        //安卓电话机订单状态更新状态 1 -->4
        iResvOrderAndroidService.updateAndroidOrderStatus1TO4(intervalNum);

        //插入安卓电话机订单日志 2-->3
        iResvOrderLogsService.insertAndroidOrderStatus2TO3(intervalNum);
        //更新安卓电话机订单 2-->3
        iResvOrderAndroidService.updateAndroidOrderStatus2TO3(intervalNum);

    }
}
