package com.zhidianfan.pig.yd.moduler.common.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvMeetingOrderHis;
import com.baomidou.mybatisplus.service.IService;
import com.zhidianfan.pig.yd.moduler.resv.dto.ResvMeetingOrderDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sherry
 * @since 2018-09-03
 */
public interface IResvMeetingOrderHisService extends IService<ResvMeetingOrderHis> {

    void copyResvMeetingOrders(ResvMeetingOrderDTO resvMeetingOrderDTO);
}
