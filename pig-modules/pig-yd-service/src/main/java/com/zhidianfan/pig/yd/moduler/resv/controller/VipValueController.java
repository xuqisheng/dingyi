package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.resv.dto.VipValueDTO;
import com.zhidianfan.pig.yd.moduler.resv.service.VipValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author huzp
 * @Description
 * @Date: 2018/11/12 14:46
 * @Modified By:
 */
@RestController
@RequestMapping("/vipvalue")
@Api("客户价值")
public class VipValueController {

    @Autowired
    private VipValueService vipValueService;


    /**
     * 门店后台酒店价值分类具体信息查询
     * @param businessId 酒店id
     * @return 分类客户list
     */
    @GetMapping("/businessvipvalue")
    public ResponseEntity getBusinessVipValue(@RequestParam Integer businessId) {

        VipValueDTO businessEnableClass = vipValueService.getBusinessVipValue(businessId);

        return ResponseEntity.ok(businessEnableClass);
    }


    /**
     * 门店后台修改酒店客户价值分类信息
     * @param vipValueDTO 酒店客戶价值数据
     * @return
     */
    @ApiOperation(value="门店后台修改酒店客户价值分类信息")
    @PostMapping("/businessvipvalue")
    public ResponseEntity updateBusinessVipValue(@RequestBody VipValueDTO vipValueDTO) {

        VipValueDTO data = vipValueService.getBusinessVipValue(vipValueDTO.getBusinessId());

        Boolean b  ;
        if(data==null){
            //如果没有改酒店的数字,则生成默认的
            b = vipValueService.insertVIPValueInfo(vipValueDTO);
        }else{
            //有则更新
            b = vipValueService.editVIPValueInfo(vipValueDTO);
        }

        Tip tip = (b ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP);

        return ResponseEntity.ok(tip);
    }


}
