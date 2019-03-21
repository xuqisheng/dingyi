package com.zhidianfan.pig.yd.moduler.common.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderHis;
import com.baomidou.mybatisplus.service.IService;
import com.zhidianfan.pig.yd.moduler.resv.dto.ResvOrderDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sherry
 * @since 2018-09-03
 */
public interface IResvOrderHisService extends IService<ResvOrderHis> {

    void copyResvOrders(ResvOrderDTO resvOrderDTO);
}
