package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvMeetingOrderHis;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.ResvMeetingOrderHisMapper;
import com.zhidianfan.pig.yd.moduler.common.service.IResvMeetingOrderHisService;
import com.zhidianfan.pig.yd.moduler.resv.dto.ResvMeetingOrderDTO;
import org.springframework.stereotype.Service;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sherry
 * @since 2018-09-03
 */
@Service
public class ResvMeetingOrderHisServiceImpl extends ServiceImpl<ResvMeetingOrderHisMapper, ResvMeetingOrderHis> implements IResvMeetingOrderHisService {


    @Override
    public void copyResvMeetingOrders(ResvMeetingOrderDTO resvMeetingOrderDTO) {
        this.baseMapper.copyResvMeetingOrders(resvMeetingOrderDTO);
    }
}
