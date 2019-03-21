package com.zhidianfan.pig.yd.moduler.sms.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.service.*;
import com.zhidianfan.pig.yd.moduler.sms.bo.message.FailStatusBO;
import com.zhidianfan.pig.yd.moduler.sms.bo.message.MessageResultBO;
import com.zhidianfan.pig.yd.moduler.sms.dto.LineMessageDTO;
import com.zhidianfan.pig.yd.moduler.sms.dto.LoginMessageDTO;
import com.zhidianfan.pig.yd.moduler.sms.dto.MessageDTO;
import com.zhidianfan.pig.yd.moduler.sms.dto.business.BusinessSMSInfoDTO;
import com.zhidianfan.pig.yd.moduler.sms.dto.log.SMSLogDTO;
import com.zhidianfan.pig.yd.moduler.sms.dto.meeting.order.ResvMeetingOrderDto;
import com.zhidianfan.pig.yd.moduler.sms.service.rmi.SmsFeign;
import com.zhidianfan.pig.yd.moduler.sms.service.rmi.dto.ClMsgParam;
import com.zhidianfan.pig.yd.moduler.sms.service.rmi.dto.SmsSendResDTO;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author Conan
 * @Description 短信调用服务
 * @Date: 2018/9/12 0012 下午 4:20
 * @Modified By:
 */

@Service("MessageService")
public class MessageService {

    /**
     * 微服务之间调用pig-yd-base中短信接口
     */
    @Autowired
    private SmsFeign smsFeign;

    /**
     * 短信日志接口业务层
     */
    @Autowired
    private SMSLogService sMSLogService;

    /**
     * 酒店信息数据库操作
     */
    @Autowired
    private IBusinessService businessService;

    /**
     * 安卓电话机订单表
     */
    @Autowired
    private IResvOrderAndroidService iResvOrderAndroidService;
    /**
     * 订单表
     */
    @Autowired
    private IResvOrderService iResvOrderService;


    /**
     * 短信信息逻辑层
     */
    @Autowired
    private BusinessSMSService businessSMSService;

    /**
     * 酒店短信数据库操作
     */
    @Autowired
    private IBusinessSmsService businessSmsService;

    /**
     * 短信模板
     */
    @Autowired
    private MessageTemplateService messageTemplateService;

    /**
     * 宴会订单数据库操作
     */
    @Autowired
    private IResvMeetingOrderService resvMeetingOrderService;

    /**
     * 酒店短信末班数据库操作
     */
    @Autowired
    private IBusinessSmsTemplateService businessSmsTemplateService;


    /**
     * 排队订单信息service
     */
    @Autowired
    private IResvLineService resvLineService;


    private static final Logger LOGGER = Logger.getLogger(MessageService.class);

    /**
     * 普通短信，即pushSms所传type
     */
    private static final Integer ORDINARY = 2;

    /**
     * 营销短信
     */
    private static final Integer MARKETING = 1;

    /**
     * 发送预订单相关操作短信
     *
     * @param messageDTO 短信外部对象
     * @return 发送短信结果
     */
    public MessageResultBO sendMessage(MessageDTO messageDTO) {
        int type = 0;
        //短信类型 1-预订, 2-换桌, 3-退订
        //TemplateType类型  1-预订,2-确认, 3-入座,4-换桌, 5-退订
        if (messageDTO.getTemplateType() <= 3) {
            type = 1;
        } else if (messageDTO.getTemplateType().equals(4)) {
            type = 2;
        } else if (messageDTO.getTemplateType().equals(5)) {
            type = 3;
        }
//        酒店信息查询
        Business business = businessService.selectById(messageDTO.getBusinessId());
        if (business == null) {
            LOGGER.error("酒店信息不存在，请求酒店id为：" + messageDTO.getBusinessId());
            return getResult("-200", "没有该酒店信息！", "FAIL");
        }
//        酒店模板查询
        BusinessSmsTemplate businessSmsTemplate = businessSmsTemplateService.selectOne(new EntityWrapper<BusinessSmsTemplate>()
                .eq("business_id", messageDTO.getBusinessId())
                .eq("template_type", messageDTO.getTemplateType()));
        String content = null;
        if (!Objects.isNull(businessSmsTemplate)) {
            content = businessSmsTemplate.getTemplateContent();
        }
//        String content = businessSMSService.getBusinessSMSTemplate(messageDTO.getBusinessId(), messageDTO.getTemplateType());
//        短信配置信息
        BusinessSMSInfoDTO info = businessSMSService.queryBusinessSMSSettings(messageDTO.getBusinessId(), type, messageDTO.getResvOrderTypeId());

        Object resvOrder ;
        //订单信息
        resvOrder = iResvOrderAndroidService.selectOne(new EntityWrapper<ResvOrderAndroid>()
                .eq("resv_order", messageDTO.getResvOrder()));

        if (resvOrder == null ){
            resvOrder = iResvOrderService.selectOne(new EntityWrapper<ResvOrder>()
                    .eq("resv_order", messageDTO.getResvOrder()));
        }

        if (resvOrder == null) {
            LOGGER.error("订单信息不存在，请求订单号为：" + messageDTO.getResvOrder());
            return getResult("-200", "没有该订单信息！", "FAIL");
        }
        Map<String, Object> param = convertMap(business, info, resvOrder);
        param.put("checkedTables", messageDTO.getCheckedTables());//
        param.put("oldTableName", messageDTO.getOldTableName());//
        param.put("oldTableAreaName", messageDTO.getOldTableAreaName());//
//        配置短信模板
        if (StringUtils.isEmpty(content)) {
            content = messageTemplateService.getMessage(messageDTO.getTemplateType(), param);
        } else {
            try {
                JSONObject jsonObject = JSONObject.parseObject(content);
//                配置自定义模板 sort变量的位置 , signType [易订]标志前置后置
                content = messageTemplateService.getMessage2(jsonObject.getString("messageContent"),
                        jsonObject.getObject("sort", List.class), jsonObject.getInteger("signType"), param);
            } catch (Exception e) {
                LOGGER.error("  " + content);
                LOGGER.error(e.getMessage(), e);
                return null;
            }
        }

        Map<String, Object> resvOrderMap = Maps.newHashMap();
        resvOrderMap.putAll(JSONObject.parseObject(JSONObject.toJSONString(resvOrder)));

        return pushSMS(ORDINARY, content, messageDTO.getBusinessId(), messageDTO.getPhone() + "", business.getBusinessName(), resvOrderMap);
    }


    /**
     * 排队订单催促短信
     *
     * @param lineMessageDTO 排队订单号 客户手机号
     * @return 短信发送结果
     */
    public MessageResultBO sendLineUrgeMessage(LineMessageDTO lineMessageDTO) {

        ResvLine resvLine = resvLineService.selectOne(new EntityWrapper<ResvLine>().eq("line_no", lineMessageDTO.getLineNo()));


        Integer mylineSort = resvLine.getLineSort();
        //查询在当前催促用户之前排队人数
        Integer lineNum = resvLineService.selectCount(new EntityWrapper<ResvLine>()
                .lt("line_sort", mylineSort)
                .eq("status", 0)
                .eq("resv_date", resvLine.getResvDate())
                .eq("meal_type_id", resvLine.getMealTypeId())
                .eq("business_id", resvLine.getBusinessId()));

        //TODO 查询是否已经有配置模板,有的话使用酒店自己配置的模板
        //排队催促短信内容
        //酒店模板查询
        BusinessSmsTemplate businessSmsTemplate = businessSmsTemplateService.selectOne(new EntityWrapper<BusinessSmsTemplate>()
                .eq("business_id", resvLine.getBusinessId())
                .eq("template_type", 8));


        String content;
        //如果模板不为空的话那么采用酒店自己配置的模板
        if (!Objects.isNull(businessSmsTemplate)) {
            //获取酒店自己配置的排队催促模板短信
            content = getLineContentTemplates(businessSmsTemplate, resvLine,lineNum);
        } else {
            //排队催促默认模板
            content = messageTemplateService.getLineUrageMessage(resvLine, lineNum);
        }


        return pushLineMsg(resvLine, content, lineMessageDTO.getPhone());
    }


    /**
     * 生成排队订单短信 当前号,餐别时间
     *
     * @param lineMessageDTO 排队订单号 客户手机号
     * @return 短信发送结果
     */
    public MessageResultBO sendLineMessage(LineMessageDTO lineMessageDTO) {

        ResvLine resvLine = resvLineService.selectOne(new EntityWrapper<ResvLine>().eq("line_no", lineMessageDTO.getLineNo()));


        //酒店模板查询
        BusinessSmsTemplate businessSmsTemplate = businessSmsTemplateService.selectOne(new EntityWrapper<BusinessSmsTemplate>()
                .eq("business_id", resvLine.getBusinessId())
                .eq("template_type", 7));

        String content;
        if (!Objects.isNull(businessSmsTemplate)) {
            //获取酒店自己配置的模板短信
            content = getLineContentTemplates(businessSmsTemplate, resvLine,null);
        } else {
            //排队订单默认模板
            content = messageTemplateService.getLineMessage(resvLine);
        }

        return pushLineMsg(resvLine, content, lineMessageDTO.getPhone());
    }


    /**
     * 获取模板短信内容
     * @param businessSmsTemplate 短信模板
     * @param resvLine 排队信息
     * @param lineNum 当前已经到的序号
     * @return
     */
    private  String getLineContentTemplates(BusinessSmsTemplate businessSmsTemplate,ResvLine resvLine,Integer lineNum){
        String content = businessSmsTemplate.getTemplateContent();
        JSONObject jsonObject = JSONObject.parseObject(content);

        Business business = businessService.selectById(resvLine.getBusinessId());
        //拼接短信模板需要的参数
        Map<String, Object> param = Maps.newHashMap();

        param.putAll(JSONObject.parseObject(JSONObject.toJSONString(business)));
        param.putAll(JSONObject.parseObject(JSONObject.toJSONString(resvLine)));

        if(lineNum !=null) param.put("lineNum",lineNum);

        //根据参数拼接模板短信
        content = messageTemplateService.getMessage2(jsonObject.getString("messageContent"),
                jsonObject.getObject("sort", List.class), jsonObject.getInteger("signType"), param);

        return  content;
    }

    /**
     * 发送登录验证短信
     *
     * @param loginMessageDTO
     * @return
     */
    public MessageResultBO sendLoginMessage(LoginMessageDTO loginMessageDTO) {
        String code = loginMessageDTO.getCode();
        if (loginMessageDTO.getPhone() == null) {
            return getResult("-200", "手机号码为空!", "FAIL");
        }
        if (StringUtils.isEmpty(code)) {
            return getResult("-200", "登录短信验证码生成异常!", "FAIL");
        }
        String content = String.format("【易订】验证码：%s。如非本人操作，请忽略本短信", code + "");
//        if(1==1)return null;
        return pushSMS(ORDINARY, content, loginMessageDTO.getBusinessId(), loginMessageDTO.getPhone().toString(), "", null);
    }

    /**
     * 返回错误信息
     *
     * @param code   错误代码
     * @param text   错误信息
     * @param status 错误状态
     * @return 错误结果
     */
    private MessageResultBO getResult(String code, String text, String status) {
        MessageResultBO resultBO = new MessageResultBO();
        resultBO.setErrorMsg(text);
        resultBO.setCode(code);
        resultBO.setStatus(status);
        return resultBO;
    }

    /**
     * 发送宴会短信
     *
     * @param messageDTO 外部短信类
     * @return 短信发送结果
     */

    public MessageResultBO sendMeetingSMS(MessageDTO messageDTO) {
        Business business = businessService.selectById(messageDTO.getBusinessId());
        if (business == null) {
            LOGGER.error("酒店信息不存在，请求酒店id为：" + messageDTO.getBusinessId());
            return getResult("-200", "没有该酒店信息！", "FAIL");
        }
//        宴会确认短信, 0-不发送, 1-发送
        if (Integer.valueOf("0").equals(business.getMeetingConfirmMessage())){
            return getResult("-200", "酒店配置不发送短信!", "FAIL");
        }

        ResvMeetingOrderDto meetingOrder = resvMeetingOrderService.queryResvMeetingOrder(messageDTO);
        if (meetingOrder == null){
            return getResult("-200", "没有该宴会订单信息!", "FAIL");
        }

        BusinessSMSInfoDTO smsInfo = businessSMSService.queryBusinessSMSMeetingSettings(messageDTO.getBusinessId(), 1);
        Map<String, Object> param = convertMap(business, meetingOrder, smsInfo);
        param.put("checkedTables", messageDTO.getCheckedTables());
        //if(1==1)return null;
        String content = messageTemplateService.getMessage(7, param);

        Map<String, Object> meetingOrderMap = Maps.newHashMap();
        meetingOrderMap.putAll(JSONObject.parseObject(JSONObject.toJSONString(meetingOrder)));


        return pushSMS(ORDINARY, content, messageDTO.getBusinessId(), messageDTO.getPhone() + "", business.getBusinessName(), meetingOrderMap);
    }

    /**
     * 对象转换成map
     */
    private Map<String, Object> convertMap(Object businessDTO, Object businessSMSInfoDTO, Object resvOrder) {
        Map<String, Object> param = Maps.newHashMap();
        if (resvOrder != null)
            param.putAll(JSONObject.parseObject(JSONObject.toJSONString(resvOrder)));
        if (businessDTO != null)
            param.putAll(JSONObject.parseObject(JSONObject.toJSONString(businessDTO)));
        if (businessSMSInfoDTO != null)
            param.putAll(JSONObject.parseObject(JSONObject.toJSONString(businessSMSInfoDTO)));
        return param;
    }


    /**
     * 发送短信方法
     *
     * @param type           1:营销短信 2:普通短信
     * @param messageContent 短信内容
     * @param businessId     酒店id
     * @param phone          需要发送的电话号码
     * @return 短信发送结果
     */
    private MessageResultBO pushSMS(Integer type, String messageContent, Long businessId, String phone, String businessName, Map<String, Object> orderMap) {
//      酒店现有短信信息 现有短信数，阈值等等
        BusinessSms businessSms = businessSmsService.selectOne(new EntityWrapper<BusinessSms>().eq("business_id", businessId));
        if (businessSms == null) {
            LOGGER.error("该酒店不存在或该酒店未开通短信业务，请求酒店id为：" + businessId);
            return getResult("-200", "该酒店不存在或该酒店未开通短信业务！", "FAIL");
        }
        Integer count = businessSms.getCurrentSmsNum();
        int smsNum = messageContent.length() <= 70 ? 1 : (2 + (messageContent.length() - 70) / 67);
        List<String> phonesList = Lists.newArrayList(StringUtils.split(phone, ","));
        smsNum = smsNum * phonesList.size();

        Boolean isRemind = null;
        MessageResultBO resultBO = new MessageResultBO();
        if (count <= 0) {
            isRemind = Boolean.FALSE;
            resultBO.setRemind(isRemind);
            return resultBO;
        }

        Integer minSmsNum = businessSms.getMinSmsNum();
        if (minSmsNum != null && minSmsNum > 0) {
            if (count - smsNum < minSmsNum) {
                isRemind = Boolean.TRUE;
            } else {
                isRemind = Boolean.FALSE;
            }
        }
        LOGGER.info("message for" + phone);
        LOGGER.info("business id:" + businessId + "         sms count:" + count);
        if (count >= smsNum) {

            SmsSendResDTO result;
//            营销短信 1 普通短信 2
            ClMsgParam clMsgParam = new ClMsgParam();
            //是否营销短信
            if (type.equals(MARKETING)) {
//                批量与不批量
                if (phonesList.size() > 1) {
                    clMsgParam.setPhone(phonesList);
                    clMsgParam.setMsg(messageContent);
                    result = smsFeign.sendBatchmarkMsg(clMsgParam);
                } else {
                    result = smsFeign.sendmarkMsg(phone, messageContent);
                }
            } else {
                if (phonesList.size() > 1) {
                    clMsgParam.setPhone(phonesList);
                    clMsgParam.setMsg(messageContent);
                    result = smsFeign.sendBatchNormalMsg(clMsgParam);
                } else {
                    result = smsFeign.sendNormalMsg(phone, messageContent);
                }
            }
//            Map<String, Object> result = object2Map(smsSendResDTO);

            //if(1==1)return null;
//            Map<String, Object> result = ChuangLanUtils.sendMessage(jsonObject);
            //短信发送日志
            SMSLogDTO log = new SMSLogDTO();
            log.setBusinessId(businessId);
            log.setBusinessName(businessName);
            log.setAppUserId(MapUtils.getLong(orderMap, "appUserId"));
            log.setAppUserName(MapUtils.getString(orderMap, "appUserName"));
            log.setContent(messageContent);
            log.setDeviceUserId(MapUtils.getLong(orderMap, "deviceUserId"));
            log.setDeviceUserName(MapUtils.getString(orderMap, "deviceUserName"));
            log.setVipPhone(phone);
            log.setSmsNum(smsNum);
            log.setMsgid(result.getMsgId().toString());

            if (result.getCode().equals("0")) {
                BusinessSms businessSMS = new BusinessSms();
                businessSMS.setBusinessId(businessId.intValue());
                businessSMS.setCurrentSmsNum(count - smsNum);

                businessSmsService.update(businessSMS, new EntityWrapper<BusinessSms>().eq("business_id", businessId));
                resultBO.setCode(result.getCode());
                resultBO.setMsgId(result.getMsgId());
                resultBO.setStatus(result.getStatus());
                resultBO.setTime(result.getTime());
                resultBO.setErrorMsg(result.getErrorMsg());
                resultBO.setText(result.getText());
                resultBO.setRemind(isRemind);
                resultBO.setMinSmsNum(minSmsNum);

                log.setStatus("REQUEST_SUCCESS");
                log.setStatusDesc("短信已发送");
            } else {
                log.setStatus("REQUEST_FAIL");
                log.setStatusDesc(result.getErrorMsg());
                resultBO.setCode(result.getCode());
                resultBO.setErrorMsg("发送信息失败!" + JSONObject.toJSONString(result));
            }
            sMSLogService.insertSMSLog(log);
            return resultBO;
        }
        resultBO.setCode("-200");
        resultBO.setErrorMsg("短信条数不足");
        resultBO.setStatus("FAIL");
        resultBO.setRemind(isRemind);
        resultBO.setMinSmsNum(minSmsNum);
        return resultBO;
    }


    /**
     * 校验排队有关短信发送
     *
     * @param resvLine 排队订单信息
     * @param content  短信内容
     * @param phone    手机号码
     * @return
     */
    public MessageResultBO pushLineMsg(ResvLine resvLine, String content, String phone) {

        Integer businessId = resvLine.getBusinessId();

        //对酒店短信逻辑判断
        BusinessSms businessSms = businessSmsService.selectOne(new EntityWrapper<BusinessSms>().eq("business_id", businessId));
        if (businessSms == null) {
            LOGGER.error("该酒店不存在或该酒店未开通短信业务，请求酒店id为：" + businessId);
            return getResult("-200", "该酒店不存在或该酒店未开通短信业务！", "FAIL");
        }
        Integer count = businessSms.getCurrentSmsNum();
        int smsNum = content.length() <= 70 ? 1 : (2 + (content.length() - 70) / 67);
        List<String> phonesList = Lists.newArrayList(StringUtils.split(phone, ","));
        smsNum = smsNum * phonesList.size();

        Boolean isRemind = null;
        MessageResultBO resultBO = new MessageResultBO();
        //如果短信总数小于0 提示他没短信了
        if (count <= 0) {
            isRemind = Boolean.FALSE;
            resultBO.setRemind(isRemind);
            return resultBO;
        }
        Integer minSmsNum = businessSms.getMinSmsNum();
        if (minSmsNum != null && minSmsNum > 0) {
            if (count - smsNum < minSmsNum) {
                isRemind = Boolean.TRUE;
            } else {
                isRemind = Boolean.FALSE;
            }
        }

        //如果神域短信数量大于这次要发送的数量
        if (count >= smsNum) {
            //通过feign调用发送短信返回结果
            SmsSendResDTO result = smsFeign.sendNormalMsg(phone, content);

            //短信发送日志
            SMSLogDTO log = new SMSLogDTO();
            log.setBusinessId(businessId.longValue());
            log.setBusinessName(resvLine.getBusinessName());
            log.setAppUserId(resvLine.getAppUserId() == null ? null : resvLine.getAppUserId().longValue());
            log.setAppUserName(resvLine.getAppUserName());
            log.setContent(content);
            log.setDeviceUserId(resvLine.getDeviceUserId() == null ? null : resvLine.getDeviceUserId().longValue());
            log.setDeviceUserName(resvLine.getDeviceUserName());
            log.setVipPhone(phone);
            log.setSmsNum(smsNum);
            log.setMsgid(String.valueOf(result.getMsgId()));

            if (result.getCode().equals("0")) {
                BusinessSms businessSMS = new BusinessSms();
                businessSMS.setBusinessId(businessId);
                businessSMS.setCurrentSmsNum(count - smsNum);

                businessSmsService.update(businessSMS, new EntityWrapper<BusinessSms>().eq("business_id", businessId));
                //返回给前端的结果
                resultBO.setCode(result.getCode());
                resultBO.setMsgId(result.getMsgId());
                resultBO.setStatus(result.getStatus());
                resultBO.setTime(result.getTime());
                resultBO.setErrorMsg(result.getErrorMsg());
                resultBO.setText(result.getText());
                resultBO.setRemind(isRemind);
                resultBO.setMinSmsNum(minSmsNum);

                log.setStatus("REQUEST_SUCCESS");
                log.setStatusDesc("短信已发送");
            } else {
                log.setStatus("REQUEST_FAIL");
                log.setStatusDesc(result.getErrorMsg());
                resultBO.setCode(result.getCode());
                resultBO.setErrorMsg("发送信息失败!" + JSONObject.toJSONString(result));
            }
            sMSLogService.insertSMSLog(log);
            return resultBO;
        }
        resultBO.setCode("-200");
        resultBO.setErrorMsg("短信条数不足");
        resultBO.setStatus("FAIL");
        resultBO.setRemind(isRemind);
        resultBO.setMinSmsNum(minSmsNum);

        return resultBO;
    }

    /**
     * 参数校验
     *
     * @param code
     * @param msg
     * @return
     */
    public FailStatusBO failSign(Integer code, String msg) {
        FailStatusBO failStatusBO = new FailStatusBO();
        failStatusBO.setCode(code);
        failStatusBO.setMsg(msg);
        return failStatusBO;
    }


}
