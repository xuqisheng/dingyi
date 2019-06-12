package com.zhidianfan.pig.yd.moduler.welcrm.controller;

import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.welcrm.bo.BasicBO;
import com.zhidianfan.pig.yd.moduler.welcrm.dto.AppraiseDTO;
import com.zhidianfan.pig.yd.moduler.welcrm.service.CrmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

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

    /**
     * 接收微生活评价
     * @param appraiseDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/save/appraise")
    public ResponseEntity saveAppraiseInfo(AppraiseDTO appraiseDTO) throws ParseException {
        Tip tip = crmService.saveAppraiseInfo(appraiseDTO);
        return ResponseEntity.ok(tip);
    }
}
