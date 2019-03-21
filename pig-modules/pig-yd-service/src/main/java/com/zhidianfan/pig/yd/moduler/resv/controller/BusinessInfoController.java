package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Business;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessThirdParty;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.MealType;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.TableArea;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.resv.bo.DeskBo;
import com.zhidianfan.pig.yd.moduler.resv.bo.SmsRecordBO;
import com.zhidianfan.pig.yd.moduler.resv.dto.HomePageDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.ThirdPartyInfoEditDTO;
import com.zhidianfan.pig.yd.moduler.resv.service.BusinessInfoService;
import com.zhidianfan.pig.yd.moduler.resv.service.DeskService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

/**
 * @Author: huzp
 * @Date: 2018/9/19 16:20
 */
@RestController
@RequestMapping("/hotel")
public class BusinessInfoController {

    @Autowired
    private BusinessInfoService businessInfoService;

    @Autowired
    private MessageSource messageSource;

    @Resource
    private DeskService deskService;


    /**
     * 获取酒店信息
     *
     * @param id 酒店id
     * @return 返回酒店信息
     */
    @GetMapping(value = "/businessInfo", params = "id")
    public ResponseEntity getBusinessInfo(Integer id, Integer tableAreaStatus) {


        //查询酒店距离过期天数
        Map  expirationInfo = businessInfoService.getDistanceExpiredDays(id);


        //获取酒店相关的信息
        Business businessinfo = businessInfoService.getBusinessInfo(id);
        List<MealType> mealtype = businessInfoService.getMealTypeInfo(id);
        List<TableArea> areainfo = businessInfoService.getTableAreaInfo(id, tableAreaStatus);


        //酒店查询异常判断
        if (null == businessinfo) {
            ErrorTip tip = new ErrorTip();
            tip.setCode(500);
            //国际化
            Locale locale = LocaleContextHolder.getLocale();
            String msg = messageSource.getMessage("hiaf", null, locale);
            tip.setMsg(msg);
            return ResponseEntity.ok(tip);
        }
        //酒店桌位标签
        Set<String> labels = new HashSet<>();
        List<DeskBo> tables = deskService.deskList(id, null);
        tables.forEach((table) -> {
            String relatedTable = table.getRelatedTable();
            if (StringUtils.isNotBlank(relatedTable)) {
                //标签根据英文逗号隔开
                labels.addAll(Arrays.asList(relatedTable.split(",")));
            }
        });


        Map<String, Object> result = Maps.newHashMap();
        result.put("businessinfo", businessinfo);
        result.put("mealtype", mealtype);
        result.put("areainfo", areainfo);
        result.put("deskLabels", labels);
        result.put("expirationInfo", expirationInfo);

        return ResponseEntity.ok(result);
    }


    /**
     * 酒店关联的第三方平台信息
     *
     * @param businessId
     * @return
     */
    @GetMapping(value = "thirdPartyInfo", params = "businessId")
    public ResponseEntity getThirdPartyInfo(Integer businessId) {
        List<BusinessThirdParty> businessThirdParty = businessInfoService.getThirdPartyInfo(businessId);
        return ResponseEntity.ok(businessThirdParty);
    }


    /**
     * 酒店关联的第三方平台信息新增删除
     *
     * @param thirdPartyInfoEditDTO
     * @return
     */
    @PostMapping(value = "thirdPartyInfoEdit")
    public ResponseEntity thirdPartyInfoEdit(@Valid ThirdPartyInfoEditDTO thirdPartyInfoEditDTO) {
        if (thirdPartyInfoEditDTO.getId() != null) {
            businessInfoService.deleteThirdPartyById(thirdPartyInfoEditDTO.getId());
            return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
        }
        if (StringUtils.isNotBlank(thirdPartyInfoEditDTO.getName())) {
            BusinessThirdParty businessThirdParty = new BusinessThirdParty();
            businessThirdParty.setBusinessName(thirdPartyInfoEditDTO.getBusinessName());
            businessThirdParty.setBusinessId(thirdPartyInfoEditDTO.getBusinessId());
            businessThirdParty.setThirdPartyName(thirdPartyInfoEditDTO.getName());
            businessInfoService.insertThirdParty(businessThirdParty);
        }
        return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
    }


    /**
     * 修改酒店默认人数
     *
     * @param business 酒店id 与酒店默认人数
     * @return 操作成功与否
     */
    @PostMapping(value = "/defmealsnum")
    public ResponseEntity changeDefMealsNum(@RequestBody Business business) {

        boolean b = businessInfoService.changeDefMealsNum(business);

        Tip tip = (b ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP);

        return ResponseEntity.ok(tip);
    }

    /**
     * 修改第三方订单免审状态
     *
     * @param business 酒店id 与按钮状态
     * @return 成功操作与否
     */
    @PostMapping(value = "/orderexemptionverifycheck")
    public ResponseEntity changeOrderExemptionVerifyCheck(@RequestBody Business business) {

        boolean b = businessInfoService.changeOrderExemptionVerifyCheck(business);

        Tip tip = (b ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP);

        return ResponseEntity.ok(tip);
    }


    /**
     * 酒店短信充值记录
     *
     * @param businessId
     * @return
     */
    @GetMapping(value = "smsrechargerecord", params = "businessId")
    public ResponseEntity smsRechargeRecord(Integer businessId) {

        //分页充值记录
        Page<SmsRecordBO> smsRechargeRecord = businessInfoService.getSmsRechargeRecord(businessId);

        //获取总充值记录
        Map<String, String> generalRechargeRecord = businessInfoService.getGeneralRechargeRecord(businessId);

        Map<String, Object> result = Maps.newHashMap();
        result.put("smsRechargeRecord", smsRechargeRecord);
        result.put("generalRechargeRecord", generalRechargeRecord);

        return ResponseEntity.ok(result);
    }


    /**
     * 酒店首页
     *
     * @param
     * @return
     */
    @ApiOperation(value = "门店后台酒店首页")
    @GetMapping(value = "/businesshomepage")
    @ApiImplicitParam(paramType = "query", name = "businessId", value = "酒店id", dataType = "Integer")
    public ResponseEntity getBusinessInfo(Integer businessId) {

        //获取总人数,营业预定数,总金额
        HomePageDTO businessHomepageInfo = businessInfoService.getBusinessHomepageInfo(businessId);

        return ResponseEntity.ok(businessHomepageInfo);
    }

    /**
     * 获取过期酒店
     */
    @ApiOperation(value = "过期酒店数据")
    @GetMapping(value = "/expiredhotel")
    public ResponseEntity getExpiredHotel(){

       List<Business> expiredHotel = businessInfoService.getExpiredHotel();

       return ResponseEntity.ok(expiredHotel);
    }

}
