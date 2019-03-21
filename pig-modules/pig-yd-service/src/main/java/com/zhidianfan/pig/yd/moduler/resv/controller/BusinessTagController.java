package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.Business;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 酒店备注标签
 *
 * @author LJH
 * @version 1.0
 * @Date 2018/9/26 10:40
 */
@RestController
@RequestMapping("hotelTag")
public class BusinessTagController {

    @Resource
    private IBusinessService iBusinessService;


    /**
     * 酒店备注标签
     *
     * @param businessId
     * @return
     */
    @GetMapping(value = "/all")
    public ResponseEntity all(Integer businessId) {
        Business business = iBusinessService.selectById(businessId);
        if (business == null) {
            throw new RuntimeException("酒店id不存在");
        }
        Map<String, String> tag = new HashMap<>();
        tag.put("tag", business.getTag());

        return ResponseEntity.ok(tag);
    }


    /**
     * 酒店备注标签新增 修改 删除
     *
     * @param tag
     * @return
     */
    @PostMapping(value = "/edit", params = {"businessId", "tag"})
    public ResponseEntity edit(Integer businessId, String tag) {

        Business business = iBusinessService.selectById(businessId);
        if (StringUtils.isBlank(tag)) {
            tag = "";
        }
        business.setTag(tag);
        boolean result = iBusinessService.updateAllColumnById(business);

        return ResponseEntity.ok(result);
    }


}
