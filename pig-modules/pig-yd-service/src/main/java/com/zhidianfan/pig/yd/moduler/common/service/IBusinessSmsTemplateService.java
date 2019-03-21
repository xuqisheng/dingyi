package com.zhidianfan.pig.yd.moduler.common.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessSmsTemplate;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Conan
 * @since 2018-09-10
 */
public interface IBusinessSmsTemplateService extends IService<BusinessSmsTemplate> {

    Boolean insertBusinessTemplate(Integer businessId);

}
