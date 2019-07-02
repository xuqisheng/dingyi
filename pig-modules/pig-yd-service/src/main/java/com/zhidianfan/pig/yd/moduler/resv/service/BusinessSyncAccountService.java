package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.common.util.PageFactory;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessSyncAccount;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessSyncAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: hzp
 * @Date: 2019-06-10 16:39
 * @Description: 第三方数据同步 service
 */
@Service
@Slf4j
public class BusinessSyncAccountService {

    @Autowired
    IBusinessSyncAccountService iBusinessSyncAccountService;


    public Page<BusinessSyncAccount> pageSelectBybusinessId(Integer businessId) {


        Page<BusinessSyncAccount> page = new PageFactory().defaultPage();

        iBusinessSyncAccountService.selectPage(page,new EntityWrapper<BusinessSyncAccount>()
                .eq("business_id",businessId)
        );


        return page;
    }


    /**
     * 编辑信息
     * @param businessSyncAccount 酒店同步账号
     * @return 操作结果
     */
    public boolean updateBusinessSyncAccountInfo(BusinessSyncAccount businessSyncAccount) {


        return iBusinessSyncAccountService.updateSyncInfo(businessSyncAccount);
    }


    /**
     * 插入数据
     * @param businessSyncAccount 酒店状态
     * @return 测试
     */
    public boolean insert(BusinessSyncAccount businessSyncAccount) {


        return iBusinessSyncAccountService.insert(businessSyncAccount);
    }

    public boolean changeBusinessSyncAccountInfoStatus(Integer id, Integer status) {

        BusinessSyncAccount businessSyncAccount =  new BusinessSyncAccount();
        businessSyncAccount.setStatus(status);

        return  iBusinessSyncAccountService.update(businessSyncAccount,new EntityWrapper<BusinessSyncAccount>()
                .eq("id",id));
    }
}
