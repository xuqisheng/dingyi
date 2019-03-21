package com.zhidianfan.pig.yd.moduler.manage.controller;


import com.zhidianfan.pig.common.constant.Tip;
import com.zhidianfan.pig.yd.moduler.manage.dto.BusinessDTO;
import com.zhidianfan.pig.yd.moduler.manage.dto.PageDTO;
import com.zhidianfan.pig.yd.moduler.manage.service.BusinessManageService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/11/2
 * @Modified By:
 */
@Api("酒店")
@RestController
@RequestMapping("/business")
public class BusinessManageController {

    @Autowired
    BusinessManageService businessManageService;

    /**
     * 获取酒店申请列表
     * @param businessDTO
     * @return
     */
    @GetMapping("/get/page")
    public ResponseEntity getBusiness(BusinessDTO businessDTO){
        PageDTO pageDTO = businessManageService.getBusiness(businessDTO);
        return ResponseEntity.ok(pageDTO);
    }

    /**
     * 未审核数量
     * @return
     */
    @GetMapping("/get/count")
    public ResponseEntity getBusinssCount(BusinessDTO businessDTO){
        Map<String,Integer> result = businessManageService.getBusinssCount(businessDTO);
        return ResponseEntity.ok(result);
    }

    /**
     * 审核酒店
     * @param businessDTO
     * @return
     */
    @PostMapping("/update")
    public ResponseEntity updateBusiness(@RequestBody BusinessDTO businessDTO){
        Tip tip = businessManageService.updateBusiness(businessDTO);
        return ResponseEntity.ok(tip);
    }

    /**
     * 新增审核记录
     * @param businessDTO
     * @return
     */
    @PostMapping("/apply")
    public ResponseEntity putApplyAgent(@RequestBody BusinessDTO businessDTO){
        Tip tip = businessManageService.putApplyAgent(businessDTO);
        return ResponseEntity.ok(tip);
    }



}
