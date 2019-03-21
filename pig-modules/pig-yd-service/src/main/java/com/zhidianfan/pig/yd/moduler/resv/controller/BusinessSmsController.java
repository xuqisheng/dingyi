package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.SmsInvoice;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.SmsInvoiceRecord;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.common.service.ISmsInvoiceRecordService;
import com.zhidianfan.pig.yd.moduler.common.service.ISmsInvoiceService;
import com.zhidianfan.pig.yd.moduler.common.service.ISmsLogService;
import com.zhidianfan.pig.yd.moduler.common.service.ISmsMarketingService;
import com.zhidianfan.pig.yd.moduler.resv.bo.BusinessConsumeBo;
import com.zhidianfan.pig.yd.moduler.resv.bo.BusinessSmsStatisticsBo;
import com.zhidianfan.pig.yd.moduler.resv.bo.SmsRecordBO;
import com.zhidianfan.pig.yd.moduler.resv.dto.SmsInvoiceDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.SmsInvoiceRecordDTO;
import com.zhidianfan.pig.yd.moduler.resv.service.BusinessInfoService;
import com.zhidianfan.pig.yd.moduler.resv.service.BusinessSmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 酒店短信
 * @User: ljh
 * @Date: 2018-11-08
 * @Time: 15:33
 */
@Api("酒店短信")
@RestController
@RequestMapping("businessSms")
public class BusinessSmsController {


    @Resource
    private BusinessSmsService businessSmsService;

    @Resource
    private BusinessInfoService businessInfoService;

    @Resource
    private ISmsInvoiceService iSmsInvoiceService;

    @Resource
    private ISmsInvoiceRecordService iSmsInvoiceRecordService;

    @Resource
    private ISmsLogService iSmsLogService;

    @Resource
    private ISmsMarketingService iSmsMarketingService;


    /**
     * 统计数据
     * @param businessId
     * @return
     */
    @ApiOperation("统计数据")
    @GetMapping("/statisticsData")
    public ResponseEntity<BusinessSmsStatisticsBo> statisticsData(Integer businessId){

        BusinessSmsStatisticsBo bo = new BusinessSmsStatisticsBo();

        LocalDate now = LocalDate.now();
        ZoneId zone = ZoneId.systemDefault();
        LocalDate monthFirstDay = now.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate firstDay = LocalDate.of(1970, 1, 1);

        //本月消耗通知短信
        List<BusinessConsumeBo> nowMonthNotification =
                businessSmsService.consumeSms(monthFirstDay,monthFirstDay.plusMonths(1), (byte) 1, businessId);
        bo.setNowMonthNotification(nowMonthNotification.get(0).getNum());

        //本月消耗营销短信
        List<BusinessConsumeBo> nowMonthMarketing =
                businessSmsService.consumeSms(monthFirstDay,monthFirstDay.plusMonths(1), (byte) 2, businessId);
        bo.setNowMonthMarketing(nowMonthMarketing.get(0).getNum());


        Date firstDayDate = Date.from(firstDay.atStartOfDay().atZone(zone).toInstant());
        Date lastDayDate = Date.from(now.atStartOfDay().plusMonths(1).atZone(zone).toInstant());
        //累计消耗通知短信
        Integer allNotification = iSmsLogService.sendSmsNum(firstDayDate, lastDayDate, businessId);
        bo.setAllNotification(allNotification);
        //累计消耗营销短信
        Integer allMarketing = iSmsMarketingService.sendSmsNum(firstDayDate, lastDayDate, businessId);
        bo.setAllMarketing(allMarketing);

        //近6月消耗通知短信（包括本月）
        List<BusinessConsumeBo> notifications = businessSmsService.consumeSms(monthFirstDay.minusMonths(5), monthFirstDay.plusMonths(1), (byte) 1, businessId);
        bo.setNotifications(notifications);

        //近6月消耗营销短信（包括本月）
        List<BusinessConsumeBo> marketing = businessSmsService.consumeSms(monthFirstDay.minusMonths(5), monthFirstDay.plusMonths(1), (byte) 2, businessId);
        bo.setMarketing(marketing);

        //充值短信总量
        Map<String,String> generalRechargeRecord =  businessInfoService.getGeneralRechargeRecord(businessId);
        bo.setAllSms(generalRechargeRecord);

        //剩余短信数量
        Integer leftSms = businessSmsService.leftSms(businessId);
        bo.setLeftSms(leftSms);

        return ResponseEntity.ok(bo);

    }

    @ApiOperation("充值记录 分页")
    @GetMapping("/rechargeRecord")
    public ResponseEntity<Page<SmsRecordBO>> rechargeRecord(Integer businessId){

        //分页充值记录
        Page<SmsRecordBO> smsRechargeRecord = businessInfoService.getSmsRechargeRecord(businessId);


        return ResponseEntity.ok(smsRechargeRecord);
    }


    @ApiOperation("开票信息")
    @GetMapping("/invoiceInfo")
    public ResponseEntity<SmsInvoice> invoiceInfo(@Valid SmsInvoiceDTO smsInvoiceDTO){

        SmsInvoice smsInvoice = new SmsInvoice();
        //保存
        if(smsInvoiceDTO.getOperation()== 2){
            BeanUtils.copyProperties(smsInvoiceDTO,smsInvoice);
            if(smsInvoiceDTO.getId()==null){
                iSmsInvoiceService.insert(smsInvoice);
            }else{
                iSmsInvoiceService.updateById(smsInvoice);
            }
        }else if(smsInvoiceDTO.getOperation()==1) {
            //查看
            SmsInvoice condition = new SmsInvoice();
            condition.setBusinessId(smsInvoiceDTO.getBusinessId());
            condition.setStatus(1);
            smsInvoice = iSmsInvoiceService.selectOne(new EntityWrapper<>(condition));
        }
        return ResponseEntity.ok(smsInvoice);
    }


    @ApiOperation("开票")
    @GetMapping("/invoice")
    public ResponseEntity<Tip> invoice(@Valid SmsInvoiceRecordDTO smsInvoiceRecordDTO){
        Boolean invoice = businessSmsService.invoice(smsInvoiceRecordDTO);

        return ResponseEntity.ok(invoice? SuccessTip.SUCCESS_TIP: ErrorTip.ERROR_TIP);
    }



    @ApiOperation("开票记录详情")
    @GetMapping("/invoiceLogDetail")
    public ResponseEntity<SmsInvoiceRecord> invoice(Integer id){

        SmsInvoiceRecord condition = new SmsInvoiceRecord();
        condition.setRechargeLogId(id);
        SmsInvoiceRecord smsInvoiceRecord = iSmsInvoiceRecordService.selectOne(new EntityWrapper<>(condition));
        return ResponseEntity.ok(smsInvoiceRecord);
    }

}
