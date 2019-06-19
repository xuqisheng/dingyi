package com.zhidianfan.pig.yd.moduler.resv.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Allergen;
import com.zhidianfan.pig.yd.moduler.common.service.IAllergenService;
import com.zhidianfan.pig.yd.moduler.resv.dto.ResvOrderQueryDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.ResvTableOrder;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huzp
 * @since 2019-06-06
 */
@Controller
@RequestMapping("/allergen")
public class AllergenController {

    @Autowired
    IAllergenService allergenService;




    @ApiOperation(value="过敏来源查询", notes="根据查询条件查询来源")
    @GetMapping("/list")
    public ResponseEntity conditionQueryResvOrder(){

        List<Allergen> allergens = allergenService.selectList(new EntityWrapper<Allergen>().eq("status", 1));
        return  ResponseEntity.ok(allergens);
    }





}

