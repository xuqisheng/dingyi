package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvMeetingOrderHis;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhidianfan.pig.yd.moduler.resv.dto.ResvMeetingOrderDTO;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sherry
 * @since 2018-09-03
 */
public interface ResvMeetingOrderHisMapper extends BaseMapper<ResvMeetingOrderHis> {

    void copyResvMeetingOrders(ResvMeetingOrderDTO resvMeetingOrderDTO);
}
