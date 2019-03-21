package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessSmsTemplate;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.BusinessSmsTemplateMapper;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessSmsTemplateService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Conan
 * @since 2018-09-10
 */
@Service
public class BusinessSmsTemplateServiceImpl extends ServiceImpl<BusinessSmsTemplateMapper, BusinessSmsTemplate> implements IBusinessSmsTemplateService {

    @Override
    public Boolean insertBusinessTemplate(Integer businessId){return baseMapper.insertBusinessTemplate(businessId);}

}
