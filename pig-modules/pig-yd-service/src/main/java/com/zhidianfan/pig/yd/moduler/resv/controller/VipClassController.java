package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.VipClass;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.resv.service.VipClassService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/28
 * @Modified By:
 */
@RestController
@RequestMapping("/vipclass")
public class VipClassController {

    @Autowired
    private VipClassService vipClassService;




    /**
     * 查询一个酒店启用的客户分类
     * @param businessId 酒店id
     * @return 分类客户list
     */
    @GetMapping(value = "/businessenableclass")
    public ResponseEntity getBusinessEnableClass(@RequestParam Integer businessId) {

        Page<VipClass> vipClasses =  vipClassService.getBusinessEnableClass(businessId);

        return ResponseEntity.ok(vipClasses);
    }

    /**
     * 门店后台新增或者修改class分类
     */
    @ApiOperation(value="门店后台新增或者修改class分类")
    @PostMapping("/businessvipclass")
    public ResponseEntity editBusinessVipClass(@RequestBody VipClass vipClass) {

        Boolean b = false;
        //如果存在该class 则更新操作
        if(vipClass.getId() != null){

            b = vipClassService.editVIPClassInfo(vipClass);

        }else{

            //如果不存在该class 则进行新增操作
            Integer sortId = vipClassService.countSortId(vipClass);
            vipClass.setSortId(sortId);
            vipClass.setCreatedAt(new Date());

            b = vipClassService.insertVIPClassInfo(vipClass);
        }

        Tip tip = (b ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP);

        return ResponseEntity.ok(tip);
    }

    /**
     * 门店后台物理删除,客户分类置空
     */
    @PostMapping("/vipclassinfo")
    public ResponseEntity  delVipclass(@RequestBody VipClass vipClass) {


        //删除该class
        Boolean b = vipClassService.delVipclass(vipClass);

        Tip tip = (b ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP);

        return ResponseEntity.ok(tip);
    }


}
