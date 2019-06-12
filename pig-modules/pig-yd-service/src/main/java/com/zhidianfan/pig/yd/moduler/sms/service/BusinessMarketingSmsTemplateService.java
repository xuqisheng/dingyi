package com.zhidianfan.pig.yd.moduler.sms.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Business;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessMarketingSmsTemplate;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.SmsMarketing;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessMarketingSmsTemplateService;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessService;
import com.zhidianfan.pig.yd.moduler.common.service.ISmsMarketingService;
import com.zhidianfan.pig.yd.moduler.sms.dto.marketing.BusinessMarketingSmsTemplateDTO;
import com.zhidianfan.pig.yd.moduler.sms.service.rmi.SmsFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author: hzp
 * @Date: 2019-06-12 10:38
 * @Description: 生日营销短信
 */
@Service
@Slf4j
public class BusinessMarketingSmsTemplateService {


    @Autowired
    private IBusinessService businessService;

    @Autowired
    private ISmsMarketingService smsMarketingService;


    @Autowired
    private IBusinessMarketingSmsTemplateService ibusinessMarketingSmsTemplateService;


    @Autowired
    private SmsFeign smsFeign;


    /**
     * 新增短信模板
     *
     * @param businessMarketingSmsTemplateDTO 短信模板dto
     * @return 返回操作结果
     */
    public boolean insertBirthTemplate(BusinessMarketingSmsTemplateDTO businessMarketingSmsTemplateDTO) {

        Integer businessId = businessMarketingSmsTemplateDTO.getBusinessId();
        Business business = businessService.selectById(businessId);

        //模板仅且有一条
        int amount = ibusinessMarketingSmsTemplateService.selectCount(new EntityWrapper<BusinessMarketingSmsTemplate>()
                .eq("business_id", businessId)
                .eq("anniversary_type", 1));
        if (amount != 0) return false;


        businessMarketingSmsTemplateDTO.setTemplateContent("【" + business.getBusinessName() + "】" + businessMarketingSmsTemplateDTO.getTemplateContent());

        //计算短信条数 70 个字一条
        int smsNum = businessMarketingSmsTemplateDTO.getTemplateContent().length() <= 70 ? 1 : (2 + (businessMarketingSmsTemplateDTO.getTemplateContent().length() - 70) / 67);
        businessMarketingSmsTemplateDTO.setNum(smsNum);


        //没有模板的时候在插入,新增生日自动发送短信模板
        BusinessMarketingSmsTemplate businessMarketingSmsTemplate = ibusinessMarketingSmsTemplateService.insertBirthTemplate(businessMarketingSmsTemplateDTO);

        //--------新建送审--------
        submitBirthTemplateReview(businessMarketingSmsTemplate, 1);


        return businessMarketingSmsTemplate.getId() != null;
    }


    /**
     * 更新模板水平
     *
     * @param businessMarketingSmsTemplateDTO 模板dto
     * @return 操作结果
     */
    public boolean editBirthTemplate(BusinessMarketingSmsTemplateDTO businessMarketingSmsTemplateDTO) {

        Integer businessId = businessMarketingSmsTemplateDTO.getBusinessId();
        Business business = businessService.selectById(businessId);


        //计算短信条数 70 个字一条
        businessMarketingSmsTemplateDTO.setTemplateContent("【" + business.getBusinessName() + "】" + businessMarketingSmsTemplateDTO.getTemplateContent());
        int smsNum = businessMarketingSmsTemplateDTO.getTemplateContent().length() <= 70 ? 1 : (2 + (businessMarketingSmsTemplateDTO.getTemplateContent().length() - 70) / 67);
        businessMarketingSmsTemplateDTO.setNum(smsNum);

        BusinessMarketingSmsTemplate businessMarketingSmsTemplate = new BusinessMarketingSmsTemplate();
        BeanUtils.copyProperties(businessMarketingSmsTemplateDTO, businessMarketingSmsTemplate);

        businessMarketingSmsTemplate.setUpdateAt(new Date());

        //1. 先查询出原先
        BusinessMarketingSmsTemplate orginalTemplate = ibusinessMarketingSmsTemplateService.selectOne(new EntityWrapper<BusinessMarketingSmsTemplate>()
                .eq("id", businessMarketingSmsTemplate.getId()));

        //更新
        boolean b = ibusinessMarketingSmsTemplateService.update(businessMarketingSmsTemplate, new EntityWrapper<BusinessMarketingSmsTemplate>()
                .eq("id", businessMarketingSmsTemplate.getId()));


        //--------短信内容有变更在送审--------
        if (!orginalTemplate.getTemplateContent().equals(businessMarketingSmsTemplateDTO.getTemplateContent())) {
            submitBirthTemplateReview(businessMarketingSmsTemplate, 2);
        }

        return b;
    }


    /**
     * //2.提交审核,并且发送短信
     * 提交审核
     *
     * @param businessMarketingSmsTemplate 酒店生日短信模板
     * @param status                       状态  1 是新建模板 2 是更新模板
     */
    private void submitBirthTemplateReview(BusinessMarketingSmsTemplate businessMarketingSmsTemplate, Integer status) {


        //变量短信拼接送审
        String templateVariable = businessMarketingSmsTemplate.getTemplateVariable();
        List<String> sort = Arrays.asList(templateVariable.split(","));
        Object[] param = new String[sort.size()];
        for (int i = 0; i < sort.size(); i++) {
            if ("firstName".equals(sort.get(i))) {
                param[i] = "某某";
            } else if ("vipSex".equals(sort.get(i))) {
                param[i] = "先生/女士";
            }
        }

        String templateContent = businessMarketingSmsTemplate.getTemplateContent();
        templateContent = templateContent.replace("{$var}", "%s").replace("\\n", " ");
        String content = String.format(templateContent, param);


        //查看是否有原先的送审信息有原先的则更新

        if (status.equals(1)) {

            SmsMarketing smsMarketing = new SmsMarketing();
            smsMarketing.setContent(content);
            smsMarketing.setVariable(businessMarketingSmsTemplate.getTemplateVariable());
            smsMarketing.setBusinessId(businessMarketingSmsTemplate.getBusinessId());
            smsMarketing.setStatus("1");
            smsMarketing.setCreatedAt(new Date());
            smsMarketing.setAuditingAt(new Date());
            smsMarketing.setNum(0);
            smsMarketing.setTemplateId(businessMarketingSmsTemplate.getId());
            smsMarketing.setSmsNum(businessMarketingSmsTemplate.getNum());

            smsMarketingService.insert(smsMarketing);
        } else if (status.equals(2)) {
            //如果是编辑,查询原先的那条相关的记录
            SmsMarketing smsMarketing = smsMarketingService.selectOne(new EntityWrapper<SmsMarketing>()
                    .eq("template_id", businessMarketingSmsTemplate.getId()));

            smsMarketing.setVariable(businessMarketingSmsTemplate.getTemplateVariable());
            smsMarketing.setStatus("1");
            smsMarketing.setAuditingAt(new Date());
            smsMarketing.setSmsNum(businessMarketingSmsTemplate.getNum());
            smsMarketing.setUpdatedAt(new Date());

            smsMarketingService.updateById(smsMarketing);
        }


        //发送短信
        log.info("发送短信");
        sendReviewMessage(businessMarketingSmsTemplate.getBusinessId());
    }

    /**
     * 发送通知短信
     */
    public void sendReviewMessage(Integer businessId) {

        Business business = businessService.selectById(businessId);


        //审核发送给刘健
        String phone = "13065883920";
        String msg = String.format("叮叮，%s酒店有生日短信待审核，请及时处理。登录查看详情 manager.zhidianfan.com", business.getBusinessName());
        smsFeign.sendNormalMsg(phone, msg);

    }


}
