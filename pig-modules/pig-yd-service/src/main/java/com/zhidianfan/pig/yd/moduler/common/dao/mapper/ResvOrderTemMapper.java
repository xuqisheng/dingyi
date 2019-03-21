package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderTem;
import com.zhidianfan.pig.yd.moduler.order.entity.OrderTem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sherry
 * @since 2018-08-16
 */
public interface ResvOrderTemMapper extends BaseMapper<ResvOrderTem> {
	/**
	 *
	 * @param businessId
	 */
	void insertTemHHOrders(@Param("businessId") Integer businessId);

	/**
	 *
	 * @param businessId
	 * @return
	 */
	List<ResvOrderTem> getOrders(@Param("businessId") Integer businessId);

	/**
	 * 查询第三方订单需要插入的数据
	 * @param businessId
	 * @return
	 */
	List<OrderTem> selectOrderTemInsertData(Integer businessId);
}
