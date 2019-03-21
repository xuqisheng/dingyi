package com.zhidianfan.pig.yd.moduler.common.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderTem;
import com.zhidianfan.pig.yd.moduler.order.entity.OrderTem;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sherry
 * @since 2018-08-16
 */
public interface IResvOrderTemService extends IService<ResvOrderTem> {
	void insertTemHHOrders(Integer businessId);

	List<ResvOrderTem> getOrders(Integer businessId);

	List<OrderTem> selectOrderTemInsertData(Integer businessId);
}
