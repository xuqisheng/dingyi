package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvMeetingOrder;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.ResvMeetingOrderHisMapper;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.ResvMeetingOrderMapper;
import com.zhidianfan.pig.yd.moduler.common.service.IResvMeetingOrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhidianfan.pig.yd.moduler.resv.dto.ResvMeetingOrderDTO;
import com.zhidianfan.pig.yd.moduler.sms.dto.MessageDTO;
import com.zhidianfan.pig.yd.moduler.sms.dto.meeting.order.ResvMeetingOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sherry
 * @since 2018-08-28
 */
@Service
public class ResvMeetingOrderServiceImpl extends ServiceImpl<ResvMeetingOrderMapper, ResvMeetingOrder> implements IResvMeetingOrderService {


    @Override
    public void deleteResvMeetingOrders(ResvMeetingOrderDTO resvMeetingOrderDTO) {
        this.baseMapper.deleteResvMeetingOrders(resvMeetingOrderDTO);
    }

    @Override
    public ResvMeetingOrderDto queryResvMeetingOrder(MessageDTO messageDTO) {
        return this.baseMapper.queryResvMeetingOrder(messageDTO);
    }

    @Override
    public void updateMeetingOrderStatus2TO3(Integer intervalNum) {
        baseMapper.updateMeetingOrderStatus2TO3(intervalNum);
    }

    @Override
    public void updateMeetingOrderStatus1TO6(Integer intervalNum) {
        baseMapper.updateMeetingOrderStatus1TO6(intervalNum);
    }


}
