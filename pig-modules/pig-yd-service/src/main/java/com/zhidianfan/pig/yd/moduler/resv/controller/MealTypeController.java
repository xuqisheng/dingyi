package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.google.common.collect.Maps;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.MealType;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.meituan.service.MeituanService;
import com.zhidianfan.pig.yd.moduler.resv.dto.MealTypeDTO;
import com.zhidianfan.pig.yd.moduler.resv.service.MealTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: huzp
 * @Date: 2018/11/29 16:17
 * @DESCRIPTION
 */
@Api(value = "餐别")
@RestController
@RequestMapping("/mealtype")
public class MealTypeController {

    @Autowired
    MealTypeService mealTypeService;

    @Autowired
    private MeituanService meituanService;


    /**
     *
     * @param id 酒店id
     * @return
     */
    @ApiOperation(value="酒店餐别查询", notes="根据酒店id查询订单")
    @ApiImplicitParam(paramType="query", name = "id", value = "酒店id", dataType = "String")
    @GetMapping(value = "/info")
    public ResponseEntity getMealType(@RequestParam("id")String id) {

        List<MealType> newMealTypeList = mealTypeService.getNewMealTypeByBusinessId(id);
//        List<MealType> oldmealTypeList = mealTypeService.selectOldMealTypeByBusinessId(id);

        Map<String, List<MealType>> result = Maps.newHashMap();
        result.put("newMealTypeList",newMealTypeList);
//        result.put("oldmealTypeList",oldmealTypeList);

        return ResponseEntity.ok(result);
    }

    /**
     * 修改餐别信息
     * @return
     */
    @ApiOperation(value="酒店餐别编辑")
    @PostMapping(value = "/info")
    public ResponseEntity editMealTypeInfo(@RequestBody List<MealTypeDTO> mealTypeDTO) {

        Object result = mealTypeService.editMealTypeInfo(mealTypeDTO);

        return ResponseEntity.ok(result);
    }


    /**
     * 美团同步接口
     * @param businessId
     * @return
     */
    @ApiOperation(value="同步第三方餐时")
    @GetMapping(value = "/sync")
    public ResponseEntity syncMealType(@RequestParam("businessid") Integer businessId){

        Tip tip = meituanService.newMealSync(businessId);

        return ResponseEntity.ok(tip);
    }

}
