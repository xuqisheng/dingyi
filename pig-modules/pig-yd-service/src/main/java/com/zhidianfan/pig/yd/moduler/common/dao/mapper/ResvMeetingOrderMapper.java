package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvMeetingOrder;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhidianfan.pig.yd.moduler.resv.dto.ResvMeetingOrderDTO;
import com.zhidianfan.pig.yd.moduler.sms.dto.MessageDTO;
import com.zhidianfan.pig.yd.moduler.sms.dto.meeting.order.ResvMeetingOrderDto;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sherry
 * @since 2018-08-28
 */

public interface ResvMeetingOrderMapper extends BaseMapper<ResvMeetingOrder> {

    void deleteResvMeetingOrders(ResvMeetingOrderDTO resvMeetingOrderDTO);

    ResvMeetingOrderDto queryResvMeetingOrder(MessageDTO messageDTO);
}
