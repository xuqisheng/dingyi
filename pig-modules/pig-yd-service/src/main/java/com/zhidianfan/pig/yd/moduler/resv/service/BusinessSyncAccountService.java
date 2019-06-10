package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.common.util.PageFactory;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessSyncAccount;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessSyncAccountService;
import com.zhidianfan.pig.yd.moduler.resv.dto.VipTableDTO;
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
}
