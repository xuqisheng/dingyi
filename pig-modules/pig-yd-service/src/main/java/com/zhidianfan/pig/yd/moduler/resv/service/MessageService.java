package com.zhidianfan.pig.yd.moduler.resv.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.zhidianfan.pig.yd.moduler.common.service.IResvOrderAndroidService;
import com.zhidianfan.pig.yd.moduler.resv.bo.MessageOrderBO;
import com.zhidianfan.pig.yd.moduler.resv.bo.SmsBO;
import com.zhidianfan.pig.yd.moduler.resv.constants.MessageConstants;
import com.zhidianfan.pig.yd.moduler.resv.qo.CheckedTableQO;
import com.zhidianfan.pig.yd.moduler.resv.qo.MessageOrderQO;
import com.zhidianfan.pig.yd.moduler.resv.qo.SmsQO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MessageService {
	private static final Logger LOGGER = Logger.getLogger(MessageService.class);
	/**
	 * 远程调用
	 */
	@Autowired
	private RestTemplate restTemplate;
	/**
	 * 订单数据接口
	 */
	@Autowired
	private IResvOrderAndroidService iResvOrderAndroidService;


//	/**
//	 * 发送换桌短信
//	 * @param exchangeTableDTO 换桌参数
//	 * @return
//	 */
//	public MessageResultBO sendChangeTableMsg(ExchangeTableDTO exchangeTableDTO) {
//
//		MessageOrderQO messageOrderQO = new MessageOrderQO();
//		messageOrderQO.setSmsType("change");
//		messageOrderQO.setType(3);
//		messageOrderQO.setResvOrder(exchangeTableDTO.getResvOrder());
//		MessageOrderBO messageOrder = resvOrderService.getMessageOrder(messageOrderQO);
//		/* 编辑短信 end... */
//		try {
//
//
//			if (messageOrder.getIsSend() == 1) {
//
//
//				//新版短信发送逻辑
//				MessageDTO messageDTO = new MessageDTO();
//
//				messageDTO.setBusinessId(Long.valueOf(messageOrder.getBusinessId()));
//				messageDTO.setTemplateType(4);
//				messageDTO.setPhone(messageOrder.getVipPhone());
//				messageDTO.setResvOrder(messageOrder.getResvOrder());
//				messageDTO.setResvOrderTypeId(1);
//				//原先桌位
//				messageDTO.setOldTableName(exchangeTableDTO.getOldTableName());
//				messageDTO.setOldTableAreaName(exchangeTableDTO.getOldTableAreaName());
//
//				//换桌桌位
//				List<Map<String, Object>>  checkedTables = Lists.newArrayList();
//				Map<String, Object> checkedTableMap = Maps.newHashMap();
//				checkedTableMap.put("tableName", messageOrder.getTableAreaName());
//				checkedTableMap.put("tableAreaName", messageOrder.getTableAreaName());
//				checkedTables.add(checkedTableMap);
//				messageDTO.setCheckedTables(checkedTables);
//
//
//				MessageResultBO messageResultBO = messageService.sendMessage(messageDTO);
//
//
//				return messageResultBO;          //换桌
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}



//	/**
//	 * 发送确认短信
//	 * @param params 订单确认参数
//	 * @return
//	 */
//	public SmsBO sendConfirmMsg(OrderConfirmDTO params) {
//		MessageOrderQO messageOrderQO = new MessageOrderQO();
//		messageOrderQO.setSmsType("confirm");
//		messageOrderQO.setType(1);
//		messageOrderQO.setResvOrder(params.getResvOrder());
//		MessageOrderBO messageOrder = resvOrderService.getMessageOrder(messageOrderQO);
//		try {
//			if (messageOrder.getIsSend() == 1) {
//				SmsQO smsQO = new SmsQO();
//				smsQO.setBusinessId(messageOrder.getBusinessId());
//				smsQO.setTemplateType(2);
//				smsQO.setPhone(messageOrder.getVipPhone());
//				smsQO.setResvOrderTypeId(1);
//				smsQO.setResvOrder(messageOrder.getResvOrder());
//				List<CheckedTableQO> checkedTables = Lists.newArrayList();
//				CheckedTableQO checkedTableQO = new CheckedTableQO();
//				checkedTableQO.setTableName(params.getOldTableName());
//				checkedTableQO.setTableAreaName(params.getOldTableAreaName());
//				checkedTables.add(checkedTableQO);
//				smsQO.setCheckedTables(checkedTables);
//				return postSMS(smsQO);          //确认
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	/**
	 * 发送加桌短信
	 * @param resvOrder 订单号
	 * @return
	 */
	public SmsBO sendAddTableResvMsg(String resvOrder) {

		MessageOrderQO messageOrderQO = new MessageOrderQO();
		messageOrderQO.setSmsType("order");
		messageOrderQO.setType(1);
		messageOrderQO.setResvOrder(resvOrder);
		MessageOrderBO messageOrder = iResvOrderAndroidService.getMessageOrder(messageOrderQO);
		try {
			if (messageOrder.getIsSend() == 1) {
				SmsQO smsQO = new SmsQO();
				smsQO.setBusinessId(messageOrder.getBusinessId());
				smsQO.setTemplateType(1);
				smsQO.setPhone(messageOrder.getVipPhone());
				smsQO.setResvOrderTypeId(1);
				smsQO.setResvOrder(messageOrder.getResvOrder());
				List<CheckedTableQO> checkedTables = Lists.newArrayList();
				CheckedTableQO checkedTableQO = new CheckedTableQO();
				checkedTableQO.setTableName(messageOrder.getTableAreaName());
				checkedTableQO.setTableAreaName(messageOrder.getTableAreaName());
				checkedTables.add(checkedTableQO);
				smsQO.setCheckedTables(checkedTables);

				return postSMS(smsQO);          //加桌
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发送短信
	 * @param param 发送短信参数
	 * @return
	 */
	private SmsBO postSMS(SmsQO param) {
		return postSMS(param, MessageConstants.MESSAGE_URL);         //封装重构
	}

	/**
	 * 发送短信
	 * @param param 发送的参数
	 * @param url 发送url
	 * @return
	 */
	private SmsBO postSMS(SmsQO param, String url) {
		try {
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add("Content-Type", "application/json;charset=UTF-8");
			HttpEntity<SmsQO> httpEntity = new HttpEntity<SmsQO>(param, httpHeaders);
			ResponseEntity<String> entity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
			JSONObject json = JSONObject.parseObject(entity.getBody());
			JSONObject extDate = (json.getJSONObject("extDate")).getJSONObject("data");
			String remind = extDate.getString("remind");
			String minSmsNum = extDate.getString("minSmsNum");
			SmsBO smsBO = new SmsBO();
			if (null != remind) {
				smsBO.setRemind(remind);
			}
			if (null != minSmsNum) {
				smsBO.setMinSmsNum(minSmsNum);
			}
			return smsBO;
		} catch (Exception e) {
			LOGGER.info("请求短信接口异常：", e);
		}
		return null;
	}




}
