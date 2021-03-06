package com.zhidianfan.pig.user.controller;



import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.user.bo.DeskBo;
import com.zhidianfan.pig.user.dao.entity.TableArea;
import com.zhidianfan.pig.user.dao.entity.TableType;

import com.zhidianfan.pig.user.dao.service.ITableAreaService;

import com.zhidianfan.pig.user.dao.service.ITableTypeService;
import com.zhidianfan.pig.user.dto.*;

import com.zhidianfan.pig.user.service.BusinessTableTypeService;
import com.zhidianfan.pig.user.service.TableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

/**
 * @Description:桌位
 * @User: ljh
 * @Date: 2018-11-06
 * @Time: 17:01
 */
@Api("桌位")
@RestController
@RequestMapping("/table")
public class TableController {

    @Resource
    private TableService tableService;

    @Resource
    private BusinessTableTypeService businessTableTypeService;

    @Resource
    private ITableAreaService iTableAreaService;

    @Resource
    private ITableTypeService iTableTypeService;




    /**
     * 所有桌位
     * @param businessId 酒店id
     * @param status
     * @return
     */
    @ApiOperation("所有桌位")
    @GetMapping(value = "/list",params = {"businessId"})
    public ResponseEntity<List<DeskBo>> list(Integer businessId, String status){

        List<DeskBo> tables = tableService.deskList(businessId, status);
        return ResponseEntity.ok(tables);
    }

    @ApiOperation("添加")
    @PostMapping(value = "/add")
    public ResponseEntity<Tip> add(@Valid TablesDTO tablesDTO){

        Tip tip = tableService.tableAdd(tablesDTO);
        return ResponseEntity.ok(tip);
    }


    @ApiOperation("编辑（包括删除）")
    @PostMapping(value = "/edit")
    public ResponseEntity<Object> edit(@Valid @RequestBody TablesDTO tablesDTO){

        Object tip = tableService.tableEdit(tablesDTO);
        return ResponseEntity.ok(tip);
    }

    @ApiOperation("新增桌型")
    @PostMapping(value = "/type/add")
    public  ResponseEntity<Tip> typeAdd(@Valid BusinessTableTypeDTO businessTableTypeDTO){

        boolean insert = businessTableTypeService.typeAdd(businessTableTypeDTO);
        return ResponseEntity.ok(insert? SuccessTip.SUCCESS_TIP:ErrorTip.ERROR_TIP);
    }

    @ApiOperation("编辑桌型（包括删除）")
    @PostMapping(value = "/type/edit")
    public  ResponseEntity<Tip> typeEdit(@Valid  BusinessTableTypeDTO businessTableTypeDTO){

        boolean insert = businessTableTypeService.typeEdit(businessTableTypeDTO);
        return ResponseEntity.ok(insert? SuccessTip.SUCCESS_TIP:ErrorTip.ERROR_TIP);
    }

    @ApiOperation("所有桌型")
    @GetMapping(value = "/type/all")
    public  ResponseEntity<List<TableType>> typeAdd(Integer businessId){
        TableType condition = new TableType();
        condition.setBusinessId(businessId);
        List<TableType> tableTypes = iTableTypeService.selectList(new EntityWrapper<>(condition));
        return ResponseEntity.ok(tableTypes);
    }



    @ApiOperation("所有区域")
    @GetMapping(value = "/area/all")
    public  ResponseEntity<List<TableArea>> areaAdd(Integer businessId,Integer status){
        TableArea condition = new TableArea();
        condition.setBusinessId(businessId);
        if(status!=null){
            condition.setStatus(status+"");
        }
        List<TableArea> tableTypes = iTableAreaService.selectList(new EntityWrapper<>(condition));
        return ResponseEntity.ok(tableTypes);
    }

    @ApiOperation("新增区域")
    @PostMapping(value = "/area/add")
    public  ResponseEntity<Tip> areaAdd(@Valid TableAreaDTO tableAreaDTO){

        boolean insert = tableService.areaAdd(tableAreaDTO);
        return ResponseEntity.ok(insert? SuccessTip.SUCCESS_TIP:ErrorTip.ERROR_TIP);
    }


    @ApiOperation("编辑区域（包括删除）")
    @PostMapping(value = "/area/edit")
    public  ResponseEntity<Tip> areaEdit(@Valid @RequestBody TableAreaDTO tableAreaDTO){

        boolean b = tableService.areaEdit(tableAreaDTO);
        return ResponseEntity.ok(b? SuccessTip.SUCCESS_TIP:ErrorTip.ERROR_TIP);


    }

}
