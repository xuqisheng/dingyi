package com.zhidianfan.pig.yd.moduler.common.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.SmsMarketing;
import com.baomidou.mybatisplus.service.IService;

import java.util.Date;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Conan
 * @since 2018-09-10
 */
public interface ISmsMarketingService extends IService<SmsMarketing> {

    Integer sendSmsNum(Date startDate, Date endDate,Integer businessId);

}
