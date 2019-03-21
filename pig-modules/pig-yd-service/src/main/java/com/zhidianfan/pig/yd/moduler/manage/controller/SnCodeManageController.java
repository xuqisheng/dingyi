package com.zhidianfan.pig.yd.moduler.manage.controller;

import com.zhidianfan.pig.common.constant.Tip;
import com.zhidianfan.pig.yd.moduler.manage.dto.PageDTO;
import com.zhidianfan.pig.yd.moduler.manage.dto.SnCodeDTO;
import com.zhidianfan.pig.yd.moduler.manage.service.SnCodeManageService;
import com.zhidianfan.pig.yd.utils.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Part;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/11/21
 * @Modified By:
 */
@RestController
@RequestMapping("/sn")
public class SnCodeManageController {

    @Autowired
    SnCodeManageService snCodeManageService;

    /**
     * SN码列表 分页
     * @param snCodeDTO
     * @return
     */
    @GetMapping("/get/page")
    public ResponseEntity getSnCodePage(SnCodeDTO snCodeDTO){
        PageDTO pageDTO = snCodeManageService.getSnCodePage(snCodeDTO);
        return ResponseEntity.ok(pageDTO);
    }

    /**
     * SN码列表
     * @param snCodeDTO
     * @return
     */
    @GetMapping("/get")
    public ResponseEntity getSnCode(SnCodeDTO snCodeDTO){
        List<Map<String,Object>> list = snCodeManageService.getSnCode(snCodeDTO);
        return ResponseEntity.ok(list);
    }

    /**
     * SN码新增
     * @param snCodeDTO
     * @return
     */
    @PostMapping("/put")
    public ResponseEntity putSnCode(@RequestBody SnCodeDTO snCodeDTO){
        Tip tip = snCodeManageService.putSnCode(snCodeDTO);
        return ResponseEntity.ok(tip);
    }

    /**
     * SN码修改
     * @param snCodeDTO
     * @return
     */
    @PostMapping("/update")
    public ResponseEntity updateSncode(@RequestBody SnCodeDTO snCodeDTO){
        Tip tip = snCodeManageService.updateSncode(snCodeDTO);
        return ResponseEntity.ok(tip);
    }

    /**
     * SN码绑定
     * @param snCodeDTO
     * @return
     */
    @PostMapping("/bind")
    public ResponseEntity bindSncode(@RequestBody SnCodeDTO snCodeDTO){
        Tip tip = snCodeManageService.bindSncode(snCodeDTO);
        return ResponseEntity.ok(tip);
    }

    /**
     * 批量绑定sn码
     * @param snCodeDTO
     * @return
     */
    @PostMapping("/update/list")
    public ResponseEntity updateSncodes(@RequestBody SnCodeDTO snCodeDTO){
        Tip tip = snCodeManageService.updateSncodes(snCodeDTO);
        return ResponseEntity.ok(tip);
    }


    /**
     * SN码删除
     * @param snCodeDTO
     * @return
     */
    @PostMapping("/delete")
    public ResponseEntity deleteSncode(@RequestBody SnCodeDTO snCodeDTO){
        Tip tip = snCodeManageService.deleteSncode(snCodeDTO);
        return ResponseEntity.ok(tip);
    }

    /**
     * 日志列表 分页
     * @param snCodeDTO
     * @return
     */
    @GetMapping("/getLogs/page")
    public ResponseEntity getSnCodeLog(SnCodeDTO snCodeDTO){
        PageDTO pageDTO = snCodeManageService.getSnCodeLog(snCodeDTO);
        return ResponseEntity.ok(pageDTO);
    }

    /**
     * excel批量导入excel
     * @param file
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @PostMapping("/import/excel")
    public ResponseEntity importExcel(@RequestPart Part file) throws InvocationTargetException, IllegalAccessException {
        Tip tip = snCodeManageService.importExcel(file);
        return ResponseEntity.ok(tip);
    }

    @GetMapping("/download")
    public void download(){
        ExcelUtil.ListExport2Excel("sncode",new ArrayList<>());
    }


    @PostMapping("/import/list")
    public ResponseEntity importList(@RequestBody SnCodeDTO snCodeDTO){
        Tip tip = snCodeManageService.importList(snCodeDTO);
        return ResponseEntity.ok(tip);
    }

}
