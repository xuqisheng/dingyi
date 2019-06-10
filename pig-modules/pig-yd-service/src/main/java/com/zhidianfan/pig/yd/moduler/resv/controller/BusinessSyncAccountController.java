package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessSyncAccount;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessService;
import com.zhidianfan.pig.yd.moduler.resv.service.BusinessSyncAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @Author: hzp
 * @Date: 2019-06-10 16:37
 * @Description:  酒店第三方数据同步Controller
 */
@RestController
@RequestMapping("syncaccount")
public class BusinessSyncAccountController {


    @Autowired
    BusinessSyncAccountService businessSyncAccountService;

    /**
     * 分页查询 同步账号信息
     * @param businessId
     * @return
     */
    @GetMapping(value = "/info")
    public ResponseEntity BusinessSyncAccountInfo(Integer businessId) {

        Page<BusinessSyncAccount> businessSyncAccountPage = businessSyncAccountService.pageSelectBybusinessId(businessId);

        return ResponseEntity.ok(businessSyncAccountPage);
    }



}
