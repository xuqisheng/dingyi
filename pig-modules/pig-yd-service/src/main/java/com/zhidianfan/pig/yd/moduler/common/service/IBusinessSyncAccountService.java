package com.zhidianfan.pig.yd.moduler.common.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessSyncAccount;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sherry
 * @since 2018-10-22
 */
public interface IBusinessSyncAccountService extends IService<BusinessSyncAccount> {

    boolean updateSyncInfo(BusinessSyncAccount businessSyncAccount);
}
