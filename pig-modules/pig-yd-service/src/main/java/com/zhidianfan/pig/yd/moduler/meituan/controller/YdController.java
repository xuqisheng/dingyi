package com.zhidianfan.pig.yd.moduler.meituan.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.meituan.bo.BasicBO;
import com.zhidianfan.pig.yd.moduler.meituan.bo.BusinessBo;
import com.zhidianfan.pig.yd.moduler.meituan.bo.OrderBO;
import com.zhidianfan.pig.yd.moduler.meituan.constant.MeituanMethod;
import com.zhidianfan.pig.yd.moduler.meituan.dto.*;
import com.zhidianfan.pig.yd.moduler.meituan.service.YdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author qqx
 * @Description 易订接口供美团调用
 * @Date Create in 2018/9/06
 * @Modified By:
 */
@RestController
@RequestMapping("/yd")
@Slf4j
public class YdController {

    @Autowired
    YdService ydService;


    /**
     *
     * 易订桌位查询接口
     * @param tableDTO
     * @return BasicBO
     */
    @PostMapping(value = "/table/QueryTables")
    public ResponseEntity tableSync(@Valid TableDTO tableDTO, BindingResult error){
        BasicBO basicBO;
        if (error.hasErrors()){
            basicBO = ydService.failSign(10004,"参数异常,请检查请求中是否缺少必传参数");
            return ResponseEntity.ok(basicBO);
        }
        if(!tableDTO.getDeveloperId().equals(MeituanMethod.DEVELOPERID)){
            basicBO = ydService.failSign(10001,"开发者id参数错误");
            return ResponseEntity.ok(basicBO);
        }

        basicBO = ydService.tablesQuery(tableDTO);
        return ResponseEntity.ok(basicBO);
    }

    /**
     *
     * 易订已订桌位查询接口
     * @param tablesOccDTO
     * @return BasicBO
     */
    @PostMapping(value = "/table/QueryTablesOccupied")
    public ResponseEntity tableOccupiedQuery(@Valid TablesOccDTO tablesOccDTO, BindingResult error) throws Exception{
        BasicBO basicBO;
        if (error.hasErrors()){
            basicBO = ydService.failSign(10004,"参数异常,请检查请求中是否缺少必传参数");
            return ResponseEntity.ok(basicBO);
        }
        if(!tablesOccDTO.getDeveloperId().equals(MeituanMethod.DEVELOPERID)){
            basicBO = ydService.failSign(10001,"开发者id参数错误");
            return ResponseEntity.ok(basicBO);
        }

        basicBO = ydService.tableOccupiedQuery(tablesOccDTO);
        return ResponseEntity.ok(basicBO);
    }

    /**
     *
     * 易订门店时段查询
     * @param mealDTO
     * @return BasicBO
     */
    @PostMapping(value = "/config/QueryPeriods")
    public ResponseEntity mealQuery(@Valid MealDTO mealDTO, BindingResult error){
        BasicBO basicBO;
        if (error.hasErrors()){
            basicBO = ydService.failSign(10004,"参数异常,请检查请求中是否缺少必传参数");
            return ResponseEntity.ok(basicBO);
        }
        if(!mealDTO.getDeveloperId().equals(MeituanMethod.DEVELOPERID)){
            basicBO = ydService.failSign(10001,"开发者id参数错误");
            return ResponseEntity.ok(basicBO);
        }

        basicBO = ydService.mealQuery(mealDTO);
        return ResponseEntity.ok(basicBO);
    }

    /**
     *
     * 易订接收美团订单
     * @param orderDTO
     * @return BasicBO
     */
    @PostMapping(value = "/order/create")
    public ResponseEntity orderCreate(@Valid OrderDTO orderDTO, BindingResult error) throws Exception {
        BasicBO basicBO;
        if (error.hasErrors()){
            basicBO = ydService.failSign(10004,"参数异常,请检查请求中是否缺少必传参数");
            return ResponseEntity.ok(basicBO);
        }
        if(!orderDTO.getDeveloperId().equals(MeituanMethod.DEVELOPERID)){
            basicBO = ydService.failSign(10001,"开发者id参数错误");
            return ResponseEntity.ok(basicBO);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("developerId", orderDTO.getDeveloperId());
        params.put("ePoiId", orderDTO.getEPoiId());
        params.put("sign", orderDTO.getSign());
        basicBO = ydService.orderCreate(orderDTO);
        return ResponseEntity.ok(basicBO);
    }


    /**
     * 易订接收美团订单修改
     * @param orderDTO
     * @param error
     * @return
     * @throws ParseException
     */
    @PostMapping(value = "/order/change")
    public ResponseEntity orderChange(@Valid OrderDTO orderDTO, BindingResult error) throws Exception {
        BasicBO basicBO;
        if (error.hasErrors()){
            basicBO = ydService.failSign(10004,"参数异常,请检查请求中是否缺少必传参数");
            return ResponseEntity.ok(basicBO);
        }
        if(!orderDTO.getDeveloperId().equals(MeituanMethod.DEVELOPERID)){
            basicBO = ydService.failSign(10001,"开发者id参数错误");
            return ResponseEntity.ok(basicBO);
        }

//        if(ydService.verifySignature(params)){
            OrderBO  orderBO = ydService.orderChange(orderDTO);
            return ResponseEntity.ok(orderBO);
//        }else {
//            basicBO = ydService.failSign(10002,"签名验证失败");
//            return ResponseEntity.ok(basicBO);
//        }
    }

    @PostMapping(value = "/kb/order")
    public ResponseEntity kbOrderCreate(@RequestBody KbOrderDTO kbOrderDTO){
        Tip tip = ydService.kbOrderCreate(kbOrderDTO);
        return ResponseEntity.ok(tip);
    }

    /**
     * apptoken回调接口
     * @param callBackDTO
     * @return
     */
    @PostMapping(value = "/callback/token")
    public ResponseEntity calllBackToken(CallBackDTO callBackDTO){
        JSONObject json = new JSONObject();
        log.warn("apptoken:{}",callBackDTO.getAppAuthToken());
        if(ydService.callBackToken(callBackDTO)){
            json.put("data","success");
        }else {
            json.put("data","false");
        }
        return ResponseEntity.ok(json);
    }

    /**
     * apptoken解绑回调接口
     * @param callBackDTO
     * @return
     */
    @PostMapping(value = "/callback/untoken")
    public ResponseEntity calllBackUnToken(CallBackDTO callBackDTO){
        JSONObject json = new JSONObject();
        log.warn("apptoken:解绑");
        if(ydService.callBackUnToken(callBackDTO)){
            json.put("data","success");
        }else {
            json.put("data","false");
        }
        return ResponseEntity.ok(json);
    }

    /**
     * 心跳检测
     * @return
     */
    @PostMapping(value = "/alive")
    public ResponseEntity alive(){
        return ResponseEntity.ok("");
    }

    /**
     * 根据口碑门店id获取酒店信息
     * @param shopId
     * @param merchantPid
     * @return
     */
    @GetMapping("/business/info")
    public ResponseEntity getBusinessInfo(@RequestParam String shopId,
                                          @RequestParam String merchantPid){
        BusinessBo businessBo = ydService.getBusinessInfo(shopId,merchantPid);
        return ResponseEntity.ok(businessBo);
    }

}
