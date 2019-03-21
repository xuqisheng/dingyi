package com.zhidianfan.pig.yd.moduler.welcrm.controller;

import com.zhidianfan.pig.yd.moduler.welcrm.bo.BasicBO;
import com.zhidianfan.pig.yd.moduler.welcrm.service.CrmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author qqx
 * @Description 微生活crm接口
 * @Date Create in 2018/10/9
 * @Modified By:
 */
@RestController
@RequestMapping("/crm")
public class CrmController {

    @Autowired
    CrmService crmService;

    /**
     *
     * 会员信息接口
     * @param
     * @return
     */
    @GetMapping(value = "/user/info")
    public ResponseEntity getUserInfo(@RequestParam String phone,
                                      @RequestParam Integer businessId) throws Exception{
        BasicBO basicBO = crmService.getUserInfo(phone,businessId);
        return ResponseEntity.ok(basicBO);
    }

    @GetMapping(value = "/user/set")
    public ResponseEntity setUserInfo(@RequestParam String phone,
                                      @RequestParam Integer businessId,
                                      @RequestParam Integer sex,
                                      @RequestParam String userName) throws Exception{
        BasicBO basicBO = crmService.setUserInfo(phone,businessId,sex,userName);
        return ResponseEntity.ok(basicBO);
    }
}
