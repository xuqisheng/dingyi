package com.zhidianfan.pig.yd.moduler.common.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvMeetingOrder;
import com.baomidou.mybatisplus.service.IService;
import com.zhidianfan.pig.yd.moduler.resv.dto.ResvMeetingOrderDTO;
import com.zhidianfan.pig.yd.moduler.sms.dto.MessageDTO;
import com.zhidianfan.pig.yd.moduler.sms.dto.meeting.order.ResvMeetingOrderDto;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sherry
 * @since 2018-08-28
 */
public interface IResvMeetingOrderService extends IService<ResvMeetingOrder> {

    void deleteResvMeetingOrders(ResvMeetingOrderDTO resvMeetingOrderDTO);

    ResvMeetingOrderDto queryResvMeetingOrder(MessageDTO messageDTO);

    void updateMeetingOrderStatus2TO3(Integer intervalNum);

    void updateMeetingOrderStatus1TO6(Integer intervalNum);
}
