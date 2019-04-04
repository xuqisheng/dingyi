package com.zhidianfan.pig.yd.moduler.sms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.zhidianfan.pig.common.util.PageFactory;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.common.service.*;
import com.zhidianfan.pig.yd.moduler.sms.bo.sms.SmsResultBO;
import com.zhidianfan.pig.yd.moduler.sms.dto.SMSDTO;
import com.zhidianfan.pig.yd.moduler.sms.dto.SuccessSms;
import com.zhidianfan.pig.yd.moduler.sms.dto.business.BusinessRechargeDTO;
import com.zhidianfan.pig.yd.moduler.sms.dto.sms.*;
import com.zhidianfan.pig.yd.moduler.sms.service.BusinessSMSService;
import com.zhidianfan.pig.yd.moduler.sms.service.SmsMarketingService;
import com.zhidianfan.pig.yd.moduler.sms.service.SmsService;
import com.zhidianfan.pig.yd.moduler.sms.service.rmi.SmsFeign;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author Conan
 * @Description
 * @Date: 2018/9/13 0013 下午 3:38
 * @Modified By:
 */
@RestController
@RequestMapping("/sms")
public class SmsController {

    Logger logger = LoggerFactory.getLogger(SmsController.class);

    @Autowired
    private SmsService smsService;

    @Autowired
    private ISmsMarketingService smsMarketingService;

    @Autowired
    private IBusinessService businessService;

    @Resource
    private ISmsLogService iSmsLogService;

    @Autowired
    private SmsFeign smsFeign;

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
     * 营销短信模板数据接口
     */
    @Autowired
    private IBusinessMarketingSmsTemplateService businessMarketingSmsTemplateService;

    @Resource
    private ISmsTypeService iSmsTypeService;

    @Resource
    private BusinessSMSService businessSMSService;

    /**
     * 普通短信模板
     */
    @Resource
    private IBusinessSmsTemplateService iBusinessSmsTemplateService;


    @Resource
    private IVipValueService iVipValueService;

    @Autowired
    private SmsMarketingService marketingService;


    /**
     * 发送自定义短信
     *
     * @param smsDTO
     * @param error
     * @return
     */
    @PostMapping(value = "/sendSMS")
    public ResponseEntity sendMessage(@RequestBody SMSDTO smsDTO, BindingResult error) {
        logger.info("接收发送自定义短信请求：" + smsDTO.toString());
        SuccessSms successSms = new SuccessSms();
        SmsResultBO result = smsService.sendSMS(smsDTO);
        Map<String, Object> param = Maps.newHashMap();
        param.put("data", result);
        successSms.setExtDate(param);
        return ResponseEntity.ok(successSms);
    }

    /**
     * 获取酒店充值记录
     *
     * @param businessDTO
     * @return
     */
    @GetMapping("/business/log")
    public ResponseEntity businessLog(@Valid BusinessDTO businessDTO) {
        Page<BusinessSmsRechargeLog> businessSmsRechargeLogPage = businessSmsRechargeLogService.selectPage(
                new PageFactory<BusinessSmsRechargeLog>().defaultPage(),
                new EntityWrapper<BusinessSmsRechargeLog>()
                        .eq("business_id", businessDTO.getBusinessId())
                        .eq("isPay", 1)
                        .orderBy("created_at desc"));

        SuccessSms successSms = new SuccessSms();
        Map<String, Object> param = Maps.newHashMap();
        param.put("data", businessSmsRechargeLogPage.getRecords());
        successSms.setExtDate(param);
        return ResponseEntity.ok(successSms);
    }

    /**
     * 短信剩余数量
     *
     * @return
     */
    @GetMapping("/business")
    public ResponseEntity businessSmsNumber(Integer businessId) {
        BusinessSms businessSms = businessSmsService.selectOne(new EntityWrapper<BusinessSms>()
                .eq("business_id", businessId));
        SuccessSms successSms = new SuccessSms();
        Map params = Maps.newHashMap();
        if (businessSms == null) {
            params.put("data", 0);
        } else {
            params.put("data", businessSms.getCurrentSmsNum());
        }
        successSms.setExtDate(params);
        return ResponseEntity.ok(params);
    }

    /**
     * 创建更新模板
     *
     * @param templateDTO
     * @return
     */
    @PostMapping("/template")
    public ResponseEntity createOrUpdateTemplate(@Valid TemplateDTO templateDTO) {
        BusinessMarketingSmsTemplate businessMarketingSmsTemplate = null;
        boolean flag = true;
        if (templateDTO.getId() != null && templateDTO.getId() != 0) {//模板已经存在
            businessMarketingSmsTemplate = businessMarketingSmsTemplateService.selectOne(
                    new EntityWrapper<BusinessMarketingSmsTemplate>()
                            .eq("id", templateDTO.getId())
                            .eq("business_id", templateDTO.getBusinessId()));
            if (businessMarketingSmsTemplate == null) {//模板未找到
                return ResponseEntity.ok(new ErrorTip(400, "该模板不存在"));
            }
        } else {//模板不存在
            businessMarketingSmsTemplate = new BusinessMarketingSmsTemplate();
            businessMarketingSmsTemplate.setIsEnable(1);
            businessMarketingSmsTemplate.setCreateAt(new Date());
            flag = false;
        }
        businessMarketingSmsTemplate.setBusinessId(templateDTO.getBusinessId());
        businessMarketingSmsTemplate.setUpdateAt(new Date());
        businessMarketingSmsTemplate.setTemplateTitle(templateDTO.getTemplateTitle());
        businessMarketingSmsTemplate.setTemplateContent(templateDTO.getTemplateContent());
        businessMarketingSmsTemplate.setTemplateVariable(templateDTO.getTemplateVariable());
        boolean b = businessMarketingSmsTemplateService.insertOrUpdate(businessMarketingSmsTemplate);
        Tip tip = b ? new SuccessTip(200, flag ? "更新成功" : "添加成功") : new ErrorTip(400, flag ? "更新失败" : "添加失败");
        JSONObject result = JSONObject.parseObject(JSON.toJSONString(tip));
        result.put("id", businessMarketingSmsTemplate.getId());
        return ResponseEntity.ok(result);
    }


    /**
     * 删除模板
     *
     * @param businessId 酒店id
     * @param templateId 模板id
     * @return
     */
    @PostMapping("/template/delete")
    public ResponseEntity deleteTemplate(Integer businessId, Integer templateId) {
        boolean delete = businessMarketingSmsTemplateService.delete(new EntityWrapper<BusinessMarketingSmsTemplate>().eq("business_id", businessId).eq("id", templateId));
        logger.info("删除:" + delete);
        Tip tip = delete ? new SuccessTip(200, "删除成功") : new ErrorTip(400, "删除失败");
        return ResponseEntity.ok(tip);
    }

    /**
     * 通过酒店id获取分页获取营销短信模板
     *
     * @param queryTemplate
     * @return
     */
    @ApiOperation("营销短信模板")
    @GetMapping("/template")
    public ResponseEntity<SuccessSms> queryTemplate(@Valid QueryTemplate queryTemplate) {

        Wrapper<BusinessMarketingSmsTemplate> wrapper = new EntityWrapper<BusinessMarketingSmsTemplate>()
                .eq("business_id", queryTemplate.getBusinessId())
                .eq("is_enable", 1);
        //模板搜索
        String keyword = queryTemplate.getKeyword();
        if (StringUtils.isNotBlank(keyword)) {
            wrapper.like("template_content", keyword);
        }
        Page<BusinessMarketingSmsTemplate> page = businessMarketingSmsTemplateService.selectPage(
                new PageFactory<BusinessMarketingSmsTemplate>().defaultPage(),
                wrapper
        );
        SuccessSms successSms = new SuccessSms();
        Map data = Maps.newHashMap();
        data.put("data", page.getRecords());
        successSms.setExtDate(data);
        return ResponseEntity.ok(successSms);
    }

    /**
     * 获取酒店营销短信模板数量
     *
     * @param businessId
     * @return
     */
    @GetMapping("/template/num")
    public ResponseEntity queryTemplateNum(Integer businessId) {
        int count = businessMarketingSmsTemplateService.selectCount(
                new EntityWrapper<BusinessMarketingSmsTemplate>()
                        .eq("business_id", businessId)
        );
        Map data = Maps.newHashMap();
        data.put("data", count);
        SuccessSms successSms = new SuccessSms();
        successSms.setExtDate(data);
        return ResponseEntity.ok(successSms);
    }

    /**
     * 翻页获取营销短信
     *
     * @param marketingDTO
     * @return
     */
    @ApiOperation("营销短信发送记录")
    @GetMapping("/marketing")
    public ResponseEntity<SuccessSms> marketingSmsList(@Valid MarketingDTO marketingDTO) {

        Wrapper<SmsMarketing> wrapper = new EntityWrapper<SmsMarketing>().eq("business_id", marketingDTO.getBusinessId());
        String status = marketingDTO.getStatus();
        if (StringUtils.isNotBlank(status)) {
            wrapper.eq("status", status);
        }
        Page<SmsMarketing> page = smsMarketingService.selectPage(
                new PageFactory<SmsMarketing>().defaultPage(), wrapper.orderBy("created_at", false)
        );
        Map<String, Object> data = Maps.newHashMap();
        data.put("data", page.getRecords());
        SuccessSms successSms = new SuccessSms();
        successSms.setExtDate(data);
        return ResponseEntity.ok(successSms);
    }


    @ApiOperation("营销短信发送记录分页")
    @GetMapping("/marketingPage")
    public ResponseEntity<Page<SmsMarketing>> marketingSmsPage(@Valid MarketingDTO marketingDTO) {

        Wrapper<SmsMarketing> wrapper = new EntityWrapper<SmsMarketing>().eq("business_id", marketingDTO.getBusinessId());
        String status = marketingDTO.getStatus();
        if (StringUtils.isNotBlank(status)) {
            wrapper.eq("status", status);
        }
        Page<SmsMarketing> page = smsMarketingService.selectPage(
                new PageFactory<SmsMarketing>().defaultPage(), wrapper.orderBy("created_at", false)
        );

        return ResponseEntity.ok(page);
    }


    @ApiOperation("通知短信发送记录")
    @PostMapping("/notification")
    public ResponseEntity<Page<SmsLog>> notification(@RequestBody NotificationDTO marketingDTO) {

        SmsLog condition = new SmsLog();
        String vipPhone = marketingDTO.getVipPhone();
        if (StringUtils.isNotBlank(vipPhone)) {
            marketingDTO.setVipPhone(null);
        }

        BeanUtils.copyProperties(marketingDTO, condition);

        Page<SmsLog> smsLogPage = new PageFactory<SmsLog>().defaultPage();

        Wrapper<SmsLog> wrapper = new EntityWrapper<>(condition);
        Date endSendTime = marketingDTO.getEndSendTime();
        Date startSendTime = marketingDTO.getStartSendTime();
        if (endSendTime != null) {
            wrapper.lt("created_at", endSendTime);
        }

        if (startSendTime != null) {
            wrapper.gt("created_at", startSendTime);
        }

        if (StringUtils.isNotBlank(vipPhone)) {
            wrapper.like("vip_phone", vipPhone);
        }

        Page<SmsLog> page = iSmsLogService.selectPage(smsLogPage, wrapper.orderBy("created_at", false));

        return ResponseEntity.ok(page);
    }

    /**
     * 营销短信发送
     *
     * @param marketingSendDTO
     * @return
     */
    @PostMapping("/marketing/send")
    @Transactional
    public ResponseEntity sendMarketingSms(@Valid MarketingSendDTO marketingSendDTO) {
        BusinessMarketingSmsTemplate businessMarketingSmsTemplate = businessMarketingSmsTemplateService.selectOne(
                new EntityWrapper<BusinessMarketingSmsTemplate>().eq("business_id", marketingSendDTO.getBusinessId())
                        .eq("id", marketingSendDTO.getTemplateId())
                        .eq("is_enable", 1)
        );
        if (businessMarketingSmsTemplate == null && StringUtils.isEmpty(marketingSendDTO.getContent())) {
            return ResponseEntity.ok(new ErrorTip(400, "短信模板不存在或短信内容为空"));
        }
        SmsMarketing smsMarketing = new SmsMarketing();
        BeanUtils.copyProperties(marketingSendDTO, smsMarketing);
        smsMarketing.setClient(1);


        //查询价值名称
        String vipValueIds = marketingSendDTO.getVipValueId();
        if (vipValueIds != null) {
            String[] vipvalueId = vipValueIds.split(",");
            List<String> ids = Arrays.asList(vipvalueId);
            List<VipValue> vipValues = iVipValueService.selectBatchIds(ids);
            String vipValueNames = vipValues.stream().map(VipValue::getVipValueName).collect(Collectors.joining(","));
            smsMarketing.setVipValueName(vipValueNames);
        }

        Business business = businessService.selectById(marketingSendDTO.getBusinessId());
        if (businessMarketingSmsTemplate == null) {
            smsMarketing.setContent("【" + business.getBusinessName() + "】" + marketingSendDTO.getContent() + "退订回复TD！");
        } else {
            smsMarketing.setContent("【" + business.getBusinessName() + "】" + businessMarketingSmsTemplate.getTemplateContent() + "退订回复TD！");
            smsMarketing.setVariable(businessMarketingSmsTemplate.getTemplateVariable());
        }
        smsMarketing.setStatus("1");
        smsMarketing.setCreatedAt(new Date());
        smsMarketing.setUpdatedAt(new Date());
        if (marketingSendDTO.getTimer() != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm");
            Date timer = null;
            try {
                timer = simpleDateFormat.parse(marketingSendDTO.getTimer());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            smsMarketing.setTimer(timer);
        }

        marketingService.calcTargetPhone(smsMarketing);
        BusinessSms businessSms = businessSmsService.selectOne(new EntityWrapper<BusinessSms>().eq("business_id", smsMarketing.getBusinessId()));
        if(businessSms.getCurrentSmsNum()<smsMarketing.getSmsNum()){
            return ResponseEntity.ok(ErrorTip.ERROR_TIP);
        }
        businessSms.setCurrentSmsNum(businessSms.getCurrentSmsNum()-smsMarketing.getSmsNum());
        businessSmsService.updateById(businessSms);
        boolean result = smsMarketingService.insert(smsMarketing);
        if(!result){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.ok(ErrorTip.ERROR_TIP);
        }

        //模板使用数+1
        businessMarketingSmsTemplateService.addUseNum(marketingSendDTO.getTemplateId());


        Tip tip = result ? new SuccessTip(200, "success") : new ErrorTip(400, "failed");

        //审核发送给刘健
        String phone = "13065883920";
        String msg = String.format("叮叮，%s酒店有营销短信待审核，请及时处理。登录查看详情   manager.zhidianfan.com" , business.getBusinessName()) ;
        smsFeign.sendNormalMsg(phone,msg);

        return ResponseEntity.ok(tip);
    }

    /**
     * 获取酒店审核中的短息数量
     *
     * @param businessId 酒店id
     * @return
     */
    @GetMapping("/examining")
    public ResponseEntity examiningNum(Integer businessId) {
        int num = smsMarketingService.selectCount(new EntityWrapper<SmsMarketing>().eq("business_id", businessId).eq("status", 1));
        SuccessSms successSms = new SuccessSms();
        Map<String, Object> data = Maps.newHashMap();
        data.put("num", num);
        successSms.setExtDate(data);
        return ResponseEntity.ok(successSms);
    }


    @ApiOperation("充值")
    @PostMapping("/recharge")
    public ResponseEntity recharge(HttpServletRequest request, BusinessRechargeDTO businessRechargeDTO) {

        String pay = businessSMSService.smsRechargeAliPay(request, businessRechargeDTO);
        return ResponseEntity.ok(pay);
    }

    @ApiOperation("支付宝回调方法")
    @RequestMapping(value = "/alipay_notify")
    public void alipay_notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean result = businessSMSService.aliNotification(request);
        PrintWriter out = response.getWriter();
        out.print(result ? "success" : "failure");
        out.flush();
        out.close();
    }


    @ApiOperation("充值信息")
    @GetMapping("/rechargeInfo")
    public ResponseEntity<List<SmsType>> rechargeInfo() {
        SmsType smsType = new SmsType();
        smsType.setStatus("1");
        List<SmsType> smsTypes = iSmsTypeService.selectList(new EntityWrapper<>(smsType));
        return ResponseEntity.ok(smsTypes);
    }

    @ApiOperation("通知短信模板")
    @GetMapping(value = "/notificationTemplate", params = {"businessId"})
    public ResponseEntity<List<BusinessSmsTemplate>> notificationTemplate(Integer businessId) {
        BusinessSmsTemplate condition = new BusinessSmsTemplate();
        condition.setBusinessId(businessId);
        List<BusinessSmsTemplate> businessSmsTemplates = iBusinessSmsTemplateService.selectList(new EntityWrapper<>(condition));
        return ResponseEntity.ok(businessSmsTemplates);
    }


    @ApiOperation("通知短信模板编辑和新增")
    @PostMapping(value = "/notificationTemplateEdit")
    public ResponseEntity notificationTemplateEdit(NotificationTemplateDTO notificationTemplateDTO) {
        Integer id = notificationTemplateDTO.getId();
        BusinessSmsTemplate newData = new BusinessSmsTemplate();
        boolean result;
        BeanUtils.copyProperties(notificationTemplateDTO, newData);
        if (id != null) {
            result = iBusinessSmsTemplateService.updateById(newData);
        } else {
            result = iBusinessSmsTemplateService.insert(newData);
        }
        return ResponseEntity.ok(result ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP);
    }

}
