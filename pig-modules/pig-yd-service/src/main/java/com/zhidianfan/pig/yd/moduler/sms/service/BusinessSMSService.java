package com.zhidianfan.pig.yd.moduler.sms.service;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.moduler.alipay.util.AlipayUtil;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.service.*;
import com.zhidianfan.pig.yd.moduler.sms.bo.business.BusinessSMSInfoBO;
import com.zhidianfan.pig.yd.moduler.sms.dto.business.BusinessRechargeDTO;
import com.zhidianfan.pig.yd.moduler.sms.dto.business.BusinessSMSInfoDTO;
import com.zhidianfan.pig.yd.moduler.sms.util.BeanCopierUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 短信信息逻辑层
 * Created by Administrator on 2017/12/13.
 */
@Service
@Slf4j
public class BusinessSMSService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessSMSService.class);

    @Autowired
    private IBusinessSmsSettingService businessSmsSettingService;
    @Autowired
    private IBusinessSmsRoleService businessSmsRoleService;
    @Autowired
    private IBusinessSmsService businessSMSService;

    @Resource
    private ISmsTypeService iSmsTypeService;

    @Resource
    private IBusinessSmsRechargeLogService iBusinessSmsRechargeLogService;

    /**
     * 消费记录数据接口
     */
    @Autowired
    private IBusinessSmsRechargeLogService businessSmsRechargeLogService;

    /**
     * 酒店短信数量数据接口
     */
    @Autowired
    private IBusinessSmsService businessSmsService;

    /**
     * 查询酒店短信配置
     *
     * @param businessId
     * @param type
     * @param resvOrderTypeId
     * @return
     */
    @Transactional(readOnly = true)
    public BusinessSMSInfoDTO queryBusinessSMSSettings(Long businessId, Integer type, Integer resvOrderTypeId) {

        BusinessSMSInfoDTO businessSMSInfoDTO = new BusinessSMSInfoDTO();
        try {
            BusinessSMSInfoBO businessSMSInfoBO = new BusinessSMSInfoBO();
//            酒店配置信息，与各种type关联
            BusinessSmsSetting businessSMSSettingsResult = businessSmsSettingService.selectOne(new EntityWrapper<BusinessSmsSetting>().eq("business_id",businessId).eq("type",type));
//            短信与订单类型关联信息 resv_order_type=1为普通预订等等 例如这家酒店的订单类别为普通预定的预定订单之后是否发送短信,取消订单之后是发发送短信
            BusinessSmsRole businessSMSRoleResult = businessSmsRoleService.selectOne(new EntityWrapper<BusinessSmsRole>().eq("business_id",businessId).eq("resv_order_type_id",resvOrderTypeId));
//           酒店现有短信信息 现有短信数，阈值等等
            BusinessSms businessSMSResult = businessSMSService.selectOne(new EntityWrapper<BusinessSms>().eq("business_id",businessId));
            if (businessSMSSettingsResult == null){
                LOGGER.error("请求酒店短信设置信息 接收参数错误, 酒店id 或 type 参数无效!");
                businessSMSInfoDTO.setCode(-102);
                businessSMSInfoDTO.setMessage("请求酒店短信设置信息 接收参数错误, 酒店id 或 type 参数无效!");
                return businessSMSInfoDTO;
            } else if (businessSMSRoleResult == null){
                LOGGER.error("请求酒店短信设置信息 接收参数错误, 酒店id 或 resvOrderTypeId 参数无效!");
                businessSMSInfoDTO.setCode(-103);
                businessSMSInfoDTO.setMessage("请求酒店短信设置信息 接收参数错误, 酒店id 或 resvOrderTypeId 参数无效!");
                return businessSMSInfoDTO;
            } else if (businessSMSResult == null){
                LOGGER.error("请求酒店短信设置信息 接收参数错误, 酒店id 参数无效!");
                businessSMSInfoDTO.setCode(-104);
                businessSMSInfoDTO.setMessage("请求酒店短信设置信息 接收参数错误, 酒店id 参数无效!");
                return businessSMSInfoDTO;
            } else {
                BeanCopierUtil.copyProperties(businessSMSSettingsResult, businessSMSInfoBO);
                BeanCopierUtil.copyProperties(businessSMSRoleResult, businessSMSInfoBO);
                BeanCopierUtil.copyProperties(businessSMSResult, businessSMSInfoBO);
                BeanCopierUtil.copyProperties(businessSMSInfoBO, businessSMSInfoDTO);
                return businessSMSInfoDTO;
            }
        } catch (Exception e){
            LOGGER.error("请求酒店短信设置信息 内部错误, 错误信息为 {}", e.getMessage());
            businessSMSInfoDTO.setCode(-100);
            businessSMSInfoDTO.setMessage("请求酒店短信设置信息 内部错误, 错误信息为 {}");
            return businessSMSInfoDTO;
        }
    }

    /**
     * 查询酒店宴会短信配置
     *
     * @param businessId
     * @param type
     * @return
     */
    @Transactional(readOnly = true)
    public BusinessSMSInfoDTO queryBusinessSMSMeetingSettings(Long businessId, Integer type) {

        BusinessSMSInfoDTO businessSMSInfoDTO = new BusinessSMSInfoDTO();
        try {
            BusinessSMSInfoBO businessSMSInfoBO = new BusinessSMSInfoBO();
            BusinessSmsSetting businessSMSSettingsResult = businessSmsSettingService.selectOne(new EntityWrapper<BusinessSmsSetting>()
                    .eq("business_id",businessId)
                    .eq("type",type));
            BusinessSms businessSMSResult = businessSMSService.selectOne(new EntityWrapper<BusinessSms>()
                    .eq("business_id",businessId));
            if (businessSMSSettingsResult == null){
                LOGGER.error("请求 酒店宴会短信设置信息 接收参数错误, 酒店id 或 type 参数无效!");
                businessSMSInfoDTO.setCode(-102);
                businessSMSInfoDTO.setMessage("请求酒店短信设置信息 接收参数错误, 酒店id 或 type 参数无效!");
                return businessSMSInfoDTO;
            } else if (businessSMSResult == null){
                LOGGER.error("请求 酒店宴会短信设置信息 接收参数错误, 酒店id 参数无效!");
                businessSMSInfoDTO.setCode(-104);
                businessSMSInfoDTO.setMessage("请求酒店短信设置信息 接收参数错误, 酒店id 参数无效!");
                return businessSMSInfoDTO;
            } else {
                BeanCopierUtil.copyProperties(businessSMSSettingsResult, businessSMSInfoBO);
                BeanCopierUtil.copyProperties(businessSMSResult, businessSMSInfoBO);
                BeanCopierUtil.copyProperties(businessSMSInfoBO, businessSMSInfoDTO);
                return businessSMSInfoDTO;
            }
        } catch (Exception e){
            LOGGER.error("请求酒店宴会短信设置信息 内部错误, 错误信息为 {}", e.getMessage());
            businessSMSInfoDTO.setCode(-100);
            businessSMSInfoDTO.setMessage("请求酒店短信设置信息 内部错误, 错误信息为 {}");
            return businessSMSInfoDTO;
        }
    }


    /**
     * 短信支付宝支付
     * @param businessRechargeDTO
     * @return
     */
     public String smsRechargeAliPay(HttpServletRequest request,BusinessRechargeDTO businessRechargeDTO){

         //生成支付参数
         SmsType smsType = iSmsTypeService.selectById(businessRechargeDTO.getSmsTypeId());
         if(smsType==null){
             throw new RuntimeException("短信充值类型id错误");
         }
         SimpleDateFormat dateFormater = new SimpleDateFormat("yyMMdd");
         String business = String.format("%04d", businessRechargeDTO.getBusinessId());
         String orderNum = dateFormater.format(new Date()) + business + String.valueOf((int) (Math.random() * 9000 + 1000));
         String description = "短信每条平均" + smsType.getAveragePrice() + "元";
         String requestText = AlipayUtil.setRequestParam(orderNum, smsType.getSmsTypeName(), smsType.getPayamount().toString(), description);

         // 创建订单
         HashMap<String, String> orderInfo = new HashMap<>();
         orderInfo.put("orderNum", orderNum);
         orderInfo.put("name", smsType.getSmsTypeName());
         orderInfo.put("pay", smsType.getPayamount().toString());
         orderInfo.put("description", description);
         orderInfo.put("id", businessRechargeDTO.getBusinessId().toString());
         orderInfo.put("type", businessRechargeDTO.getSmsTypeId().toString());
         iBusinessSmsRechargeLogService.insertRechargeLog(orderInfo);

        return requestText;
     }


    /**
     * 支付宝回调操作
     * @param request
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean aliNotification(HttpServletRequest request) throws Exception {
        Map<String, String> data = AlipayUtil.getNotifyMap(request);

        if ("success".equalsIgnoreCase(data.get("verify_result"))) {
            try {
                if (data.get("trade_status").equals("TRADE_SUCCESS")
                        || data.get("trade_status").equals("TRADE_FINISHED")) {
                    String ordernum = data.get("out_trade_no");
                    //支付成功
                    BusinessSmsRechargeLog condition=new BusinessSmsRechargeLog();
                    condition.setIspay("0");
                    condition.setOrderNo(ordernum);
                    BusinessSmsRechargeLog update = businessSmsRechargeLogService.selectOne(new EntityWrapper<>(condition));
                    if(update==null){
                        log.error("【支付宝回调】订单不存在或已被删除");
                        return false;
                    }
                    update.setIspay("1");
                    update.setAlipayOrderNo(data.get("trade_no"));
                    update.setUpdatedAt(new Date());
                    update.setStatus("2");
                    //更新支付结果
                    businessSmsRechargeLogService.updateById(update);
                    //添加短信数量
                    BusinessSms businessIdSms = new BusinessSms();
                    businessIdSms.setBusinessId(update.getBusinessId());
                    BusinessSms businessSms = businessSmsService.selectOne(new EntityWrapper<>(businessIdSms));
                    if(businessSms==null){
                        log.error("【支付宝回调】已充值成功数量增加失败 ",ordernum);
                        return false;
                    }
                    businessSms.setCurrentSmsNum(update.getSmsNum());
                    businessSms.setUpdatedAt(new Date());
                    businessSmsService.updateById(businessSms);
                    return true;
                }
            } catch (Exception e) {
                log.info("【支付宝回调】错误信息", JSON.toJSONString(data),e.getMessage());
                throw new RuntimeException("【支付宝回调】充值失败！");
            }
        } else {
            log.info("【支付宝回调】错误信息", JSON.toJSONString(data));
        }
        return false;
    }





}
