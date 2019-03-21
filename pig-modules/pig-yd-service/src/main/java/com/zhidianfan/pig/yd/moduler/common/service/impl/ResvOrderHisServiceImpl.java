package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderHis;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.ResvOrderHisMapper;
import com.zhidianfan.pig.yd.moduler.common.service.IResvOrderHisService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhidianfan.pig.yd.moduler.resv.dto.ResvOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ResvOrderHisServiceImpl extends ServiceImpl<ResvOrderHisMapper, ResvOrderHis> implements IResvOrderHisService {


    @Override
    public void copyResvOrders(ResvOrderDTO resvOrderDTO) {
        this.baseMapper.copyResvOrders(resvOrderDTO);
    }
}
