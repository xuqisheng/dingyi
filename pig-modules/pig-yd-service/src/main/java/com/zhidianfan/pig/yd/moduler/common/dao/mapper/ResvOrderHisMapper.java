package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderHis;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhidianfan.pig.yd.moduler.resv.dto.ResvOrderDTO;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sherry
 * @since 2018-09-03
 */
public interface ResvOrderHisMapper extends BaseMapper<ResvOrderHis> {

    //TODO 注意一下sql
    void copyResvOrders(ResvOrderDTO resvOrderDTO);

}
