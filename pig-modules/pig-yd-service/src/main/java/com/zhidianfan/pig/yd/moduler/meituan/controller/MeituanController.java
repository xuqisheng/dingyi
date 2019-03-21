package com.zhidianfan.pig.yd.moduler.meituan.controller;

import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.meituan.bo.OrderQueryBO;
import com.zhidianfan.pig.yd.moduler.meituan.service.MeituanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author qqx
 * @Description 美团接口供内部调用
 * @Date Create in 2018/9/03
 * @Modified By:
 */
@RestController
@RequestMapping("/meituan")
public class MeituanController {

    @Autowired
    MeituanService meituanService;


    /**
     *
     * 美团桌位同步接口
     * @param businessId
     * @return BasicBO
     */
    @GetMapping(value = "/table/sync")
    public ResponseEntity tableSync(@RequestParam Integer businessId){
        Tip tip = meituanService.tableSync(businessId);
        return ResponseEntity.ok(tip);
    }

    /**
     *
     * 美团桌位删除
     * @param businessId
     * @param tableId
     * @return BasicBO
     */
    @GetMapping(value = "/table/delete")
    public ResponseEntity tableDelete(@RequestParam Integer businessId,
                                      @RequestParam Integer tableId){
        Tip tip = meituanService.tableDelete(businessId,tableId);
        return ResponseEntity.ok(tip);
    }

    /**
     *
     * 美团餐别同步接口
     * @param businessId
     * @return BasicBO
     */
    @GetMapping(value = "/meal/sync")
    public ResponseEntity mealSync(@RequestParam Integer businessId){
        Tip tip = meituanService.mealSync(businessId);
        return ResponseEntity.ok(tip);
    }

    /**
     *
     * 美团订单更新接口
     * @param businessId
     * @param batchNo
     * @param resvType
     * @return BasicBO
     */
    @GetMapping(value = "/order/update")
    public ResponseEntity orderUpdate(@RequestParam Integer businessId,
                                      @RequestParam String batchNo,
                                      @RequestParam Integer resvType,
                                      @RequestParam String orderSerializedId) throws Exception{
        Tip tip = meituanService.orderUpdate(businessId,batchNo,resvType,orderSerializedId);
        return ResponseEntity.ok(tip);
    }

    /**
     *
     * 美团订单查询接口
     * @param businessId
     * @param batchNo
     * @return BasicBO
     */
    @GetMapping(value = "/order/query")
    public ResponseEntity orderQuery(@RequestParam Integer businessId,
                                      @RequestParam String batchNo,
                                     @RequestParam String orderSerializedId){
        OrderQueryBO orderQueryBO = meituanService.orderQuery(businessId,batchNo,orderSerializedId);
        return ResponseEntity.ok(orderQueryBO);
    }

    /**
     * 桌位状态修改
     * @param businessId
     * @param resvOrder
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/table/status/update")
    public ResponseEntity tableStatusUpdate(@RequestParam Integer businessId,
                                            @RequestParam String resvOrder) throws Exception{
        Tip tip = meituanService.tableStatusUpdate(businessId,resvOrder);
        return ResponseEntity.ok(tip);
    }

    /**
     * 获取绑定门店url
     * @param businessId
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/store/url")
    public ResponseEntity getAppAuthToken(@RequestParam Integer businessId) throws Exception{
        Map<String,String> result = new HashMap<>();
        String url = meituanService.getAppAuthToken(businessId);
        result.put("url",url);
        return ResponseEntity.ok(result);
    }

    /**
     * 获取解绑门店url
     * @param businessId
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/unstore/url")
    public ResponseEntity getAppUnAuthTokenUrl(@RequestParam Integer businessId) throws Exception{
        Map<String,String> result = new HashMap<>();
        String url = meituanService.getAppUnAuthTokenUrl(businessId);
        result.put("url",url);
        return ResponseEntity.ok(result);
    }

}
