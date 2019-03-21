package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.common.service.*;
import com.zhidianfan.pig.yd.moduler.resv.bo.OrderBO;
import com.zhidianfan.pig.yd.moduler.resv.dto.AddTableDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.AllResvOrderDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.ExchangeTableDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.OrderConfirmDTO;
import com.zhidianfan.pig.yd.moduler.resv.enums.OrderOperationEnum;
import com.zhidianfan.pig.yd.moduler.resv.qo.AllResvOrderQO;
import com.zhidianfan.pig.yd.utils.IdUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ResvService {
	private Logger log = LoggerFactory.getLogger(getClass());
	/**
	 * 桌位数据接口
	 */
	@Autowired
	private ITableService tableService;
	/**
	 * 桌位区域数据接口
	 */
	@Autowired
	private ITableAreaService tableAreaService;
	/**
	 * 订单数据接口
	 */
	@Autowired
	private IResvOrderAndroidService iResvOrderAndroidService;
	/**
	 * 通知数据接口
	 */
	@Autowired
	private IResvNoticeService resvNoticeService;
	/**
	 * 订单日志数据接口
	 */
	@Autowired
	private IResvOrderLogsService resvOrderLogsService;
	/**
	 * 订单状态映射数据接口
	 */
	@Autowired
	private IResvStatusMappingService resvStatusMappingService;
	/**
	 * 酒店数据接口
	 */
	@Autowired
	private IBusinessService businessService;

	/**
	 * 酒店桌位换桌
	 *
	 * @param params
	 * @return
	 */
	public Tip updateTable(ExchangeTableDTO params) {
		ResvOrderAndroid resvOrder = iResvOrderAndroidService.selectOne(new EntityWrapper<ResvOrderAndroid>().eq("resv_order", params.getResvOrder()));
		Table table = tableService.selectOne(new EntityWrapper<Table>().eq("id", params.getTableId()).eq("business_id", params.getBusinessId()));
		//桌位不存在
		if (null == table) {
			log.info("桌位不存在");
			return new ErrorTip(404, "桌位不存在");
		}
		TableArea tableArea = tableAreaService.selectOne(new EntityWrapper<TableArea>().eq("id", table.getTableAreaId()).eq("business_id", params.getBusinessId()));
		//区域不存在
		if (null == tableArea) {
			log.info("桌位区域不存在");
			return new ErrorTip(404, "桌位区域不存在");
		}
		Wrapper<ResvOrderAndroid> checkOrderWrapper = new EntityWrapper<ResvOrderAndroid>()
				.eq("table_id", params.getTableId())
				.eq("resv_date", params.getResvDate())
				.eq("meal_type_id", params.getMealTypeId());
		if (params.getMealTypeIdA() == null || params.getMealTypeIdA() == 0) {
			checkOrderWrapper.isNull("meal_type_id_a");
		} else {
			checkOrderWrapper.eq("meal_type_id_a", params.getMealTypeIdA());
		}

		if (params.getMealTypeIdB() == null || params.getMealTypeIdB() == 0) {
			checkOrderWrapper.isNull("meal_type_id_b");
		} else {
			checkOrderWrapper.eq("meal_type_id_b", params.getMealTypeIdB());
		}

		ResvOrderAndroid checkOrder = iResvOrderAndroidService.selectOne(checkOrderWrapper);
		if (checkOrder != null) {
			log.info("桌位被占用");
			return new ErrorTip(404, "桌位被占用");
		}
		//旧的桌位名称桌位区域名称
		params.setOldTableName(resvOrder.getTableName());
		params.setOldTableAreaName(resvOrder.getTableAreaName());
		//更新订单桌位桌位区域信息
		resvOrder.setOpenIsSync(0);
		resvOrder.setTableId(table.getId());
		resvOrder.setTableName(table.getTableName());
		resvOrder.setMaxPeopleNum(table.getMaxPeopleNum());
		resvOrder.setTableAreaId(table.getTableAreaId());
		resvOrder.setTableAreaName(tableArea.getTableAreaName());
		resvOrder.setTableName(table.getTableName());
		resvOrder.setResvDate(params.getResvDate());
		resvOrder.setMealTypeId(params.getMealTypeId());
		resvOrder.setMealTypeName(params.getMealTypeName());
		resvOrder.setMealTypeIdA((params.getMealTypeIdA() == null || params.getMealTypeIdA() == 0) ? null : params.getMealTypeIdA());
		resvOrder.setMealTypeIdB((params.getMealTypeIdB() == null || params.getMealTypeIdB() == 0) ? null : params.getMealTypeIdB());
		resvOrder.setIschangetable(1);
		boolean update = iResvOrderAndroidService.updateById(resvOrder);
		if (update) {
			String log = "原桌位" + params.getOldTableName() + "换桌成功";
			insertResvOrderLog(resvOrder, log);
			//不是预订台通知
			if (resvOrder.getAppUserId() != null && resvOrder.getAppUserId() != 0) {
				insertResvNotice(resvOrder, OrderOperationEnum.EXCHANGE_TABLE);
			}
			return new SuccessTip();
		} else {
			return new ErrorTip(404, "加桌失败，请重试！");
		}
	}

	/**
	 * 确认订单
	 *
	 * @param params 确认订单参数
	 * @return
	 */
	public Tip updateConfirm(OrderConfirmDTO params) {
		ResvOrderAndroid order = iResvOrderAndroidService.selectOne(new EntityWrapper<ResvOrderAndroid>().eq("resv_order", params.getResvOrder()));
		order.setConfirm(params.getConfirm());
		boolean update = iResvOrderAndroidService.updateById(order);
		params.setOldTableName(order.getTableName());
		params.setOldTableAreaName(order.getTableAreaName());
		return update ? new SuccessTip() : new ErrorTip(404, "加桌失败，请重试！");
	}

	/**
	 * 加桌
	 *
	 * @param params 加桌参数
	 * @return
	 */
	public Tip addTable(AddTableDTO params) {
		String newResvOrder = IdUtils.makeOrderNo();
		ResvOrderAndroid order = iResvOrderAndroidService.selectOne(new EntityWrapper<ResvOrderAndroid>().eq("resv_order", params.getResvOrder()));
		Table table = tableService.selectById(params.getTableId());
		TableArea tableArea = tableAreaService.selectById(table.getTableAreaId());
		order.setId(null);
		order.setTableAreaId(table.getTableAreaId());
		order.setTableAreaName(tableArea.getTableAreaName());
		order.setTableId(table.getId());
		order.setTableName(table.getTableName());
		order.setMaxPeopleNum(table.getMaxPeopleNum());
		order.setResvOrder(newResvOrder);
		boolean insert = iResvOrderAndroidService.insert(order);

		if (insert) {
			String log = "加桌成功-resv_server";
			insertResvOrderLog(order, log);
			if (order.getAppUserId() != null && order.getAppUserId() != 0) {
				insertResvNotice(order, OrderOperationEnum.ADD_TABLE);
			}

			return new SuccessTip();
		} else {
			return new ErrorTip(404, "加桌失败，请重试！");
		}
	}

	/**
	 * 根据条件获取所有订单
	 *
	 * @param params
	 * @return
	 */
	public List<OrderBO> getAllResvOrders(AllResvOrderDTO params) {
		String tableAreaId = params.getTableAreaId();
		AllResvOrderQO allResvOrderQO = new AllResvOrderQO();
		if (StringUtils.isNotBlank(tableAreaId)) {
			if (StringUtils.isNotBlank(tableAreaId)) {
				List<String> ids = Arrays.asList(tableAreaId.split(","));
				allResvOrderQO.setTableAreaId(ids);
			} else {
				allResvOrderQO.setTableAreaId(Arrays.asList(tableAreaId));
			}
		}

		Business business = businessService.selectById(params.getBusinessId());
		allResvOrderQO.setIsKbc(business.getIsKbc());
		allResvOrderQO.setBusinessId(params.getBusinessId());
		allResvOrderQO.setResvDate(params.getResvDate());
		allResvOrderQO.setMealTypeIdA(params.getMealTypeIdA());
		allResvOrderQO.setMealTypeIdA(params.getMealTypeIdB());
		allResvOrderQO.setResvOrderStatus(params.getOrderStatus());

		return iResvOrderAndroidService.getAllResvOrders(allResvOrderQO);
	}

	/**
	 * 新增订单日志
	 *
	 * @param order 订单
	 * @param log   日志
	 */
	private void insertResvOrderLog(ResvOrderAndroid order, String log) {
		ResvStatusMapping resvStatusMapping = resvStatusMappingService.selectById(order.getStatus());
		//加桌日志
		ResvOrderLogs resvOrderLogs = new ResvOrderLogs();
		resvOrderLogs.setResvOrder(order.getResvOrder());
		resvOrderLogs.setStatus(String.valueOf(resvStatusMapping.getStatusId()));
		resvOrderLogs.setStatusName(resvStatusMapping.getStatusName());
		resvOrderLogs.setLogs(log.substring(0, Math.min(2000, log.length())));
		resvOrderLogs.setCreatedAt(new Date());
		resvOrderLogs.setDeviceUserId(order.getDeviceUserId());
		resvOrderLogsService.insert(resvOrderLogs);
	}

	/**
	 * 消息记录
	 *
	 * @param order     订单
	 * @param operation 操作类型
	 */
	private void insertResvNotice(ResvOrderAndroid order, OrderOperationEnum operation) {
		String notice = order.getDeviceUserName() + "编辑了您的普通预订订单(客户：" + order.getVipName() + ")，内容：" + operation.getName() + "，请留意。";
		ResvNotice resvNotice = new ResvNotice();
		resvNotice.setBusinessId(order.getBusinessId());
		resvNotice.setAppUserId(order.getAppUserId());
		resvNotice.setNotice(notice);
		resvNotice.setOpreation(operation.getName());
		resvNotice.setResvOrder(order.getResvOrder());
		resvNotice.setResvType(1);
		resvNotice.setCreatedAt(new Date());
		resvNotice.setUpdatedAt(new Date());
		resvNoticeService.insert(resvNotice);
	}
}
