package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessMarketingSmsTemplate;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.BusinessMarketingSmsTemplateMapper;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessMarketingSmsTemplateService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhidianfan.pig.yd.moduler.sms.dto.marketing.BusinessMarketingSmsTemplateDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sherry
 * @since 2018-09-25
 */
@Service
public class BusinessMarketingSmsTemplateServiceImpl extends ServiceImpl<BusinessMarketingSmsTemplateMapper, BusinessMarketingSmsTemplate> implements IBusinessMarketingSmsTemplateService {

    @Override
    public void addUseNum(Integer id) {
        this.baseMapper.UpdateAddUseNum( id);
    }

    @Override
    public BusinessMarketingSmsTemplate insertBirthTemplate(BusinessMarketingSmsTemplateDTO businessMarketingSmsTemplateDTO) {

        BusinessMarketingSmsTemplate businessMarketingSmsTemplate = new BusinessMarketingSmsTemplate();

        BeanUtils.copyProperties(businessMarketingSmsTemplateDTO, businessMarketingSmsTemplate);

        businessMarketingSmsTemplate.setAnniversaryObj("本人");
        businessMarketingSmsTemplate.setAnniversaryType(1);
        businessMarketingSmsTemplate.setCreateAt(new Date());
        businessMarketingSmsTemplate.setUpdateAt(new Date());

        baseMapper.insert(businessMarketingSmsTemplate);
        return businessMarketingSmsTemplate;
    }
}
