package com.zhidianfan.pig.yd.moduler.config.controller;

import com.alibaba.fastjson.JSONArray;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ConfigArea;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ConfigDict;
import com.zhidianfan.pig.yd.moduler.config.dto.AreaDTO;
import com.zhidianfan.pig.yd.moduler.config.dto.DistDTO;
import com.zhidianfan.pig.yd.moduler.config.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/11/5
 * @Modified By:
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    ConfigService configService;

    @GetMapping("/dict")
    public ResponseEntity getDict(DistDTO distDTO){
        List<ConfigDict> configDicts = configService.getDict(distDTO);
        return ResponseEntity.ok(configDicts);
    }

    /**
     * 获取区域
     * @param areaDTO
     * @return
     */
    @GetMapping("/area")
    public ResponseEntity getArea(AreaDTO areaDTO){
        List<ConfigArea> configAreas = configService.getArea(areaDTO);
        return ResponseEntity.ok(configAreas);
    }

    @GetMapping("/areaList")
    @Cacheable(value = "resv_sys_area_list",key = "'areaList'")
    public JSONArray getAreaList(){
        JSONArray jsonArray = configService.getAreaList();
        return jsonArray;
    }

    @GetMapping("/getOpenid")
    public ResponseEntity getOpenid(@RequestParam String code){
        Map<String, Object> resultMap = configService.getOpenid(code);
        return ResponseEntity.ok(resultMap);
    }
}
