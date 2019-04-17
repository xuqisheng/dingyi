package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderAndroid;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.SmsMarketing;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Vip;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.resv.bo.VipMealInfoBo;
import com.zhidianfan.pig.yd.moduler.resv.bo.VipValueCountBo;
import com.zhidianfan.pig.yd.moduler.resv.dto.*;
import com.zhidianfan.pig.yd.moduler.resv.enums.OrderStatus;
import com.zhidianfan.pig.yd.moduler.resv.service.VipService;
import com.zhidianfan.pig.yd.moduler.sms.service.SmsMarketingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Part;
import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


/**
 * @Author: huzp
 * @Date: 2018/9/20 13:34
 */
@RestController
@RequestMapping("/vip")
@Slf4j
@Api("客户")
public class VipController {

    @Autowired
    private VipService vipService;

    @Autowired
    private MessageSource messageSource;

    @Resource
    private SmsMarketingService smsMarketingService;



    /**
     * 粗略条件查询一个酒店客户
     *
     * @param vipConditionCountDTO businessId 酒店id必须 ,keyword 可有可无
     * @return
     */
    @ApiOperation("粗略条件查询一个酒店客户")
    @GetMapping("/businessvip")
    public ResponseEntity allBusinessVip(@Valid VipConditionCountDTO vipConditionCountDTO) {

        Page<VipTableDTO> allVip = vipService.getConditionVip(vipConditionCountDTO);

        return ResponseEntity.ok(allVip);
    }


    /**
     * 根据id更新Vip信息
     *
     * @return 成功失败标志
     */
    @PostMapping(value = "/infoEdit")
    public ResponseEntity updateVip(@RequestBody Vip vip) {

        //更新Vip信息
        boolean b = vipService.updateVipInfo(vip);

        //成功错误标志
        Tip tip = (b ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP);

        return ResponseEntity.ok(tip);
    }

    /**
     * 查询具体客户信息
     *
     * @param businessId 酒店id
     * @param phone      手机号码
     * @return 客户信息
     */
    @ApiOperation(value="查询具体客户信息")
    @GetMapping(value = "/vipInfo")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType="query", name = "businessId", value = "酒店id", required = true, dataType = "Integer"),
//            @ApiImplicitParam(paramType="query", name = "phone", value = "手机号码", required = true, dataType = "String")
//    })
    public ResponseEntity getVipInfo(@RequestParam Integer businessId,
                                     @RequestParam String phone) {

        VipInfoDTO vipInfo = vipService.getVipInfo(businessId, phone);
        //Vip客户信息获取查询异常判断
        if (null == vipInfo) {
            ErrorTip tip = new ErrorTip();
            //国际化
            Locale locale = LocaleContextHolder.getLocale();
            String msg = messageSource.getMessage("vipaf", null, locale);
            tip.setMsg(msg);
            return ResponseEntity.ok(tip);
        }
        return ResponseEntity.ok(vipInfo);
    }


    /**
     * 价值类别客户统计
     *
     * @param businessId
     * @return
     */
    @ApiOperation("价值类别客户统计")
    @GetMapping("valueCount")
    public ResponseEntity valueCount(Integer businessId) {

        VipValueCountBo vipValueRecords = vipService.valueCount(businessId);

        return ResponseEntity.ok(vipValueRecords);
    }


    /**
     * 指定条件的客户数量
     *
     * @param vipConditionCountDTO
     * @return
     */
    @GetMapping("conditionCount")
    public ResponseEntity conditionCount(@Valid VipConditionCountDTO vipConditionCountDTO) {

         //Integer integer = vipService.conditionCount(vipConditionCountDTO);
        SmsMarketing smsMarketing = new SmsMarketing();
        BeanUtils.copyProperties(vipConditionCountDTO,smsMarketing);
        int integer = smsMarketingService.calcTargetPhone(smsMarketing);

        Map<String, Integer> res = new HashMap<>();
        res.put("count", integer);
        return ResponseEntity.ok(res);
    }

    /**
     * 指定条件的客户数量（可包含营销经理）
     *
     * @param vipConditionCountDTO
     * @return
     */
    @GetMapping("conditionCountByAppUser")
    public ResponseEntity conditionCountByAppUser(@Valid VipConditionCountDTO vipConditionCountDTO) {

        //Integer integer = vipService.conditionCount(vipConditionCountDTO);
        SmsMarkingDTO smsMarkingDTO = new SmsMarkingDTO();
        BeanUtils.copyProperties(vipConditionCountDTO,smsMarkingDTO);
//        int integer = smsMarketingService.calcTargetPhone(smsMarketing);
        Map map = smsMarketingService.calcTargetPhoneByAppUser(smsMarkingDTO);

//        Map<String, Integer> res = new HashMap<>();
//        res.put("count", integer);
        return ResponseEntity.ok(map);
    }
    /**
     * 客户正在进行的订单查询
     *
     * @param orderConditionDTO
     * @return
     */
    @GetMapping("reserveOrders")
    public ResponseEntity reserveOrders(@Valid OrderConditionDTO orderConditionDTO) {
        //正在进行
        orderConditionDTO.setStatus(OrderStatus.RESERVE.code);

        List<ResvOrderAndroid> orders = vipService.orders(orderConditionDTO);
        return ResponseEntity.ok(orders);
    }


    /**
     * 客户基础信息模糊查询出客户list
     */
    @GetMapping(value = "/fuzzyviplist")
    public ResponseEntity fuzzyQueryVipList(@RequestParam Integer businessId,
                                            @RequestParam String phone) {

        Page<Vip> vipList = vipService.fuzzyQueryVipList(businessId, phone);

        return ResponseEntity.ok(vipList);
    }

    /**
     * 获得用户列表(带统计信息)
     * @param businessId
     * @return
     */
    @GetMapping(value = "/statisticsViplist")
    public ResponseEntity statisticsViplist(@RequestParam Integer businessId, @RequestParam(required = false) String queryVal) {

        Page<StatisticsVipDTO> vipList = vipService.statisticsViplist(businessId, queryVal);

        return ResponseEntity.ok(vipList);
    }


    /**
     * 门店后台客戶新增或者更新
     *
     * @param vip
     * @return
     */
    @ApiOperation(value="新版门店后台客戶新增或者更新", notes="客户不存在则新增,否则更新")
    @PostMapping(value = "/vipinfo")
    public ResponseEntity addVip(@RequestBody Vip vip) {

        //更新或者插入Vip信息
        boolean b = vipService.updateOrInsertVip(vip);

        //成功错误标志
        Tip tip;

        //成功则查询该客户信息
        if (b){
            Vip basicVipInfo = vipService.getBasicVipInfo(vip.getBusinessId(), vip.getVipPhone());
            SuccessTip successTip = new SuccessTip();
            successTip.setCode(200);
            successTip.setMsg(basicVipInfo.toString());
            tip = successTip;
        }else {
            tip = ErrorTip.ERROR_TIP;
        }

        return ResponseEntity.ok(tip);
    }

    /**
     * 门店后台精细条件查询酒店客户
     */
        @ApiOperation(value="新版门店后台条件查询酒店客户", notes="根据 条件查询客户")
    @PostMapping(value = "/condition")
    public ResponseEntity conditionFindVips(@RequestBody VipInfoDTO vipInfoDTO) {

        //门店后台查询用户
        Page<VipTableDTO> vips = vipService.conditionFindVips(vipInfoDTO);


        return ResponseEntity.ok(vips);
    }

    /**
     * 门店后台客户信息导出excel
     */
    @ApiOperation(value="门店后台客户信息导出excel")
    @GetMapping("/downloadexcel/vip")
    public void downloadVipExcel(@Valid VipInfoDTO vipInfoDTO) {

        //查询出所有符合条件的排队订单数据
        List<VipTableDTO> records = vipService.excelConditionFindVips(vipInfoDTO);

        //下载excel
        vipService.downloadexcel(records);

    }

    /**
     * 门店后台客户信息导出excel模板
     */
    @ApiOperation(value="门店后台客户信息导出excel模板")
    @GetMapping("/downloadexcel/vipdemo")
    public void downloadVipExcelDemo() {

        //下载模板
        vipService.downLoadExcelDemo();

    }


    /**
     * 导入excel
     * @param file 文件
     * @param id   酒店id
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @ApiOperation(value="门店后台客户信息根据excel导入客户信息")
    @PostMapping("/importexcel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "excel", required = true, dataType = "File"),
            @ApiImplicitParam(name = "id", value = "酒店id", required = true, dataType = "Integer")
    })
    public ResponseEntity importExcelV2(@RequestPart Part file,@RequestParam("id") Integer id) throws InvocationTargetException, IllegalAccessException {

        Tip tip = vipService.importExcel(file, id);


        return ResponseEntity.ok().body(tip);

    }


    @ApiOperation(value="就近60天就餐信息")
    @GetMapping("/meal/info/near60")
    public ResponseEntity<VipMealInfoBo> mealInfoNear60(Integer vipId,Integer businessId) {

        VipMealInfoBo vipMealInfoBo = vipService.mealInfoNear(vipId, businessId, 60);
        return ResponseEntity.ok(vipMealInfoBo);
    }



}
