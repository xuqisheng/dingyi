package com.zhidianfan.pig.yd.moduler.manage.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.common.util.PageFactory;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.CustomValueConfig;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.CustomValueStatistics;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.HotelMarketingStatistics;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.common.service.ICustomValueConfigService;
import com.zhidianfan.pig.yd.moduler.common.service.ICustomValueStatisticsService;
import com.zhidianfan.pig.yd.moduler.common.service.IHotelMarketingStatisticsService;
import com.zhidianfan.pig.yd.moduler.manage.bo.CustomValueCustomerBo;
import com.zhidianfan.pig.yd.moduler.manage.bo.CustomValueStatisticsBo;
import com.zhidianfan.pig.yd.moduler.manage.bo.HotelMarketingStatisticsBo;
import com.zhidianfan.pig.yd.moduler.manage.dto.CustomValueConfigDTO;
import com.zhidianfan.pig.yd.moduler.manage.service.StatisticsCustomerValueService;
import com.zhidianfan.pig.yd.utils.IgnorePropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @Description: 总后台 统计客户价值
 * @User: ljh
 * @Date: 2019-01-17
 * @Time: 13:59
 */
@Api(description = "总后台 门店统计")
@RestController
@RequestMapping("/manage/statistics/hotel/")
public class StatisticsCustomerValueController {

    @Resource
    private ICustomValueConfigService iCustomValueConfigService;

    @Resource
    private IHotelMarketingStatisticsService iHotelMarketingStatisticsService;

    @Resource
    private ICustomValueStatisticsService iCustomValueStatisticsService;

    @Resource
    private StatisticsCustomerValueService statisticsCustomerValueService;


    @ApiOperation("添加配置")
    @PostMapping("/vip/config")
    public ResponseEntity<Tip> addCustomValueConfig(@Valid @RequestBody CustomValueConfigDTO bean){

        CustomValueConfig customValueConfig = new CustomValueConfig();
        BeanUtils.copyProperties(bean,customValueConfig);
        customValueConfig.setCreateAt(new Date());
        boolean insert = iCustomValueConfigService.insert(customValueConfig);
        if(insert){
            return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
        }else {
            return ResponseEntity.ok(ErrorTip.ERROR_TIP);
        }
    }

    @ApiOperation("修改配置")
    @PostMapping("/vip/config/edit")
    public ResponseEntity<Tip> editCustomValueConfig(@Valid @RequestBody CustomValueConfigDTO bean){
        CustomValueConfig customValueConfig = new CustomValueConfig();
        BeanUtils.copyProperties(bean,customValueConfig);
        boolean b = iCustomValueConfigService.updateAllColumnById(customValueConfig);

        if(b){
            return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
        }else{
            return ResponseEntity.ok(ErrorTip.ERROR_TIP);
        }
    }


    @ApiOperation("删除配置")
    @GetMapping("/vip/config/del")
    public ResponseEntity<Tip> delCustomValueConfig(Integer id){
        boolean b = iCustomValueConfigService.deleteById(id);
        if(b){
            return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
        }else{
            return ResponseEntity.ok(ErrorTip.ERROR_TIP);
        }
    }



    @ApiOperation("门店数据统计")
    @GetMapping("/data")
    public ResponseEntity hotelData(String businessName ){

        HotelMarketingStatisticsBo hotelMarketingStatisticsBo = new HotelMarketingStatisticsBo();
        Page<HotelMarketingStatistics> page = new PageFactory<HotelMarketingStatistics>().defaultPage();
        List<HotelMarketingStatistics> hotelMarketingStatistics = iHotelMarketingStatisticsService.hotelData(page,businessName);
        page.setRecords(hotelMarketingStatistics);
        //最近更新时间
        BeanUtils.copyProperties(page,hotelMarketingStatisticsBo);
        HotelMarketingStatistics one = iHotelMarketingStatisticsService.selectOne(new EntityWrapper<>(new HotelMarketingStatistics()).orderBy("update_at",false));
        if(one!=null){
            hotelMarketingStatisticsBo.setLastStatisticsAt(one.getUpdateAt());
        }
        return ResponseEntity.ok(hotelMarketingStatisticsBo);
    }

    @ApiOperation("门店数据统计按月份")
    @GetMapping("/data/month")
    public ResponseEntity<List<HotelMarketingStatistics>> hotelDataMonth(Integer businessId){

        HotelMarketingStatistics condition = new HotelMarketingStatistics();
        condition.setBusinessId(businessId);
        List<HotelMarketingStatistics> hotelMarketingStatistics = iHotelMarketingStatisticsService.selectList(new EntityWrapper<>(condition));

        return ResponseEntity.ok(hotelMarketingStatistics);
    }


    @ApiOperation("自定义价值客户")
    @GetMapping("/vip/data")
    public ResponseEntity<CustomValueStatisticsBo> vipData(Integer customValueConfigId, Date date){

        CustomValueStatisticsBo customValueStatisticsBo = new CustomValueStatisticsBo();
        CustomValueConfig customValueConfig = iCustomValueConfigService.selectById(customValueConfigId);
        if(customValueConfig==null){
            return ResponseEntity.ok(new CustomValueStatisticsBo());
        }

        Page page = new PageFactory<>().defaultPage();
        CustomValueStatistics condition = new CustomValueStatistics();
        condition.setCustomValueConfigId(customValueConfigId);
        condition.setBusinessId(customValueConfig.getBusinessId());

        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = date.toInstant().atZone(zoneId).toLocalDate();
        condition.setStatisticsYear(localDate.getYear());
        condition.setStatisticsMonth(localDate.getMonthValue());
        Page<CustomValueStatistics> customValueStatistics = iCustomValueStatisticsService.selectPage(page,new EntityWrapper<>(condition));
        BeanUtils.copyProperties(customValueStatistics,customValueStatisticsBo);
        customValueStatisticsBo.setLastStatisticsAt(customValueConfig.getLastStatisticsAt());

        return ResponseEntity.ok(customValueStatisticsBo);
    }


    @ApiOperation("自定义列表")
    @GetMapping("/vip/custom/data")
    public ResponseEntity<List<CustomValueCustomerBo>> vipCustomData(Integer businessId, Date date){

        ArrayList<CustomValueCustomerBo> result = new ArrayList();
        CustomValueConfig condition = new CustomValueConfig();
        condition.setBusinessId(businessId);
        //所有自定义
        List<CustomValueConfig> customValueConfigs = iCustomValueConfigService.selectList(new EntityWrapper<>(condition));
        Iterator<CustomValueConfig> iterator = customValueConfigs.iterator();
        while(iterator.hasNext()){
            CustomValueConfig customValueConfig = iterator.next();
            Date createAt = customValueConfig.getCreateAt();

            CustomValueCustomerBo customValueCustomerBo = new CustomValueCustomerBo();
            BeanUtils.copyProperties(customValueConfig,customValueCustomerBo);
            if( date !=null ) {
                if(createAt.compareTo(date)==1){
                    iterator.remove();
                    continue;
                }
                //对应人数
                CustomValueStatistics conditionNNT = new CustomValueStatistics();
                conditionNNT.setCustomValueConfigId(customValueConfig.getId());
                ZoneId zoneId = ZoneId.systemDefault();
                LocalDate localDate = date.toInstant().atZone(zoneId).toLocalDate();
                conditionNNT.setStatisticsMonth(localDate.getMonth().getValue());
                conditionNNT.setStatisticsYear(localDate.getYear());
                customValueCustomerBo.setVips(iCustomValueStatisticsService.selectCount(new EntityWrapper<>(conditionNNT)));
            }
            result.add(customValueCustomerBo);
        }

        return ResponseEntity.ok(result);
    }


    @ApiOperation("同步")
    @GetMapping("/data/sync")
    public ResponseEntity<Tip> dataSync(Integer year,Integer month,Integer businessId){

        statisticsCustomerValueService.addHotelMarketingStatistics(year, month,businessId);

        return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
    }



    ////////////////定时统计////////////////


    //自定义客户价值数据
    @GetMapping("/vip/data/add")
    public ResponseEntity<Tip> vipDataAdd(Integer year,Integer month){

        statisticsCustomerValueService.addCustomValueStatistics(year, month);

        return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
    }

    //门店统计数据
    @GetMapping("/data/add")
    public ResponseEntity dataAdd(Integer year,Integer month){

        statisticsCustomerValueService.addHotelMarketingStatistics(year, month,null);

        return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
    }









}
