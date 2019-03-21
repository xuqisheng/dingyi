package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderTem;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.ResvOrderTemMapper;
import com.zhidianfan.pig.yd.moduler.common.service.IResvOrderTemService;
import com.zhidianfan.pig.yd.moduler.order.entity.OrderTem;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sherry
 * @since 2018-08-16
 */
@Service
public class ResvOrderTemServiceImpl extends ServiceImpl<ResvOrderTemMapper, ResvOrderTem> implements IResvOrderTemService {
	@Override
	public void insertTemHHOrders(Integer businessId) {
		baseMapper.insertTemHHOrders(businessId);
}

	@Override
	public List<ResvOrderTem> getOrders(Integer businessId) {
		return baseMapper.getOrders(businessId);
	}

	@Override
	public List<OrderTem> selectOrderTemInsertData(Integer businessId) {
		return baseMapper.selectOrderTemInsertData(businessId);
	}
}
