package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.Blacklist;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.resv.dto.CustomerDTO;
import com.zhidianfan.pig.yd.moduler.resv.service.BlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: huzp
 * @Date: 2018/9/26 10:40
 */
@RestController
@RequestMapping("/blacklist")
public class BlackListController {

    @Autowired
    BlackListService blackListService;


    /**
     * 确认用户是否是黑名单
     *
     * @param businessId 酒店id
     * @param phone      黑名单客户号码
     * @return 客户是否是黑名单
     */
    @GetMapping("/listinfo")
    public ResponseEntity getList(@RequestParam Integer businessId,
                                  @RequestParam String phone) {
        //查询用户信息 根据酒店id 手机号码
        Blacklist blackCustomer = blackListService.selectInfo(businessId, phone);

        //根据成功或者失败返回提醒
        boolean flag = ((null == blackCustomer) ? false : true);

        return ResponseEntity.ok(flag);
    }

    /**
     * 增加黑名单用户
     *
     * @param customerDTO 用户信息
     * @return 操作成功或者失败
     */
    @PostMapping("/customer")
    public ResponseEntity addBlalckList(@Valid @RequestBody CustomerDTO customerDTO) {

        //返回插入成功或者失败标志
        boolean b = blackListService.addBlackCustomer(customerDTO);

        //根据成功或者失败返回提醒
        Tip tip = (b ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP);

        return ResponseEntity.ok(tip);
    }

    /**
     * 修改用户黑名单状态
     *
     * @return 操作成功或者失败
     */
    @PostMapping("/status")
    public ResponseEntity delBlalckList(@Valid  @RequestBody CustomerDTO customerDTO) {

        boolean b = blackListService.updateBlackCustomer(customerDTO);

        //根据成功或者失败返回提醒
        Tip tip = (b ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP);

        return ResponseEntity.ok(tip);
    }



}
