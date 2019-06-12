package com.zhidianfan.pig.yd.moduler.common.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessMarketingSmsTemplate;
import com.baomidou.mybatisplus.service.IService;
import com.zhidianfan.pig.yd.moduler.sms.dto.marketing.BusinessMarketingSmsTemplateDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sherry
 * @since 2018-09-25
 */
public interface IBusinessMarketingSmsTemplateService extends IService<BusinessMarketingSmsTemplate> {


    void addUseNum(Integer id);

    BusinessMarketingSmsTemplate insertBirthTemplate(BusinessMarketingSmsTemplateDTO businessMarketingSmsTemplateDTO);

}
