package com.zhidianfan.pig.yd.moduler.manage.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.service.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description:
 * @User: ljh
 * @Date: 2019-01-17
 * @Time: 15:06
 */
@Service
public class StatisticsCustomerValueService {

    @Resource
    private IHotelMarketingStatisticsService iHotelMarketingStatisticsService;

    @Resource
    private ICustomValueConfigService iCustomValueConfigService;

    @Resource
    private ICustomValueStatisticsService iCustomValueStatisticsService;

    @Resource
    private IResvOrderService iResvOrderService;

    @Resource
    private IVipService iVipService;

    @Resource
    private IBusinessService iBusinessService;


    @Async
    public void addHotelMarketingStatistics (Integer year,Integer month,Integer businessId){
        LocalDate date = LocalDate.of(year, month, 1);
        LocalDate lastDate=date.plusMonths(1);

        List<HotelMarketingStatistics> hotelMarketingStatistics = iResvOrderService.statisticsHotel(date.toString(), lastDate.toString(),businessId);
        for (HotelMarketingStatistics hotelMarketingStatistic : hotelMarketingStatistics) {
            ResvOrder resvOrder = new ResvOrder();
            resvOrder.setBusinessId(hotelMarketingStatistic.getBusinessId());
            Business business = iBusinessService.selectById(hotelMarketingStatistic.getBusinessId());
            if(business!=null){
                hotelMarketingStatistic.setBusinessName(business.getBusinessName());
            }

            hotelMarketingStatistic.setStatisticsMonth(month);
            hotelMarketingStatistic.setStatisticsYear(year);

            //判断是否已经统计过
            HotelMarketingStatistics condition = new HotelMarketingStatistics();
            condition.setBusinessId(hotelMarketingStatistic.getBusinessId());
            condition.setStatisticsYear(year);
            condition.setStatisticsMonth(month);
            HotelMarketingStatistics one = iHotelMarketingStatisticsService.selectOne(new EntityWrapper<>(condition));
            if(one!=null){
                hotelMarketingStatistic.setId(one.getId());
                hotelMarketingStatistic.setUpdateTime(one.getUpdateTime()==null?1:one.getUpdateTime()+1);
                hotelMarketingStatistic.setUpdateAt(new Date());
            }else {
                hotelMarketingStatistic.setUpdateTime(1);
                hotelMarketingStatistic.setUpdateAt(new Date());
                hotelMarketingStatistic.setCreateAt(new Date());
            }
        }
        if(hotelMarketingStatistics.size()!=0){
            iHotelMarketingStatisticsService.insertOrUpdateBatch(hotelMarketingStatistics);
        }
    }

    @Async
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public void addCustomValueStatistics (Integer year,Integer month){

        List<CustomValueStatistics> installData=new ArrayList<>();
        Date now = new Date();
        //查找数据
        LocalDate date = LocalDate.of(year, month, 1);
        LocalDate lastDate=date.plusMonths(1);
        List<CustomValueConfig> customValueConfigs = iCustomValueConfigService.selectList(new EntityWrapper<>());
        for (CustomValueConfig customValueConfig : customValueConfigs) {

            Integer consumeStart = customValueConfig.getConsumeStart();
            Integer consumeEnd = customValueConfig.getConsumeEnd();
            Integer expenseMoneyStart = customValueConfig.getExpenseMoneyStart();
            Integer expenseMoneyEnd = customValueConfig.getExpenseMoneyEnd();
            Integer expenseTimeStart = customValueConfig.getExpenseTimeStart();
            Integer expenseTimeEnd = customValueConfig.getExpenseTimeEnd();
            Integer meatDayStart = customValueConfig.getMeatDayStart();
            Integer meatDayEnd = customValueConfig.getMeatDayEnd();


            List<CustomValueStatistics> consume=null;
            List<CustomValueStatistics> expenseMoney=null;
            List<CustomValueStatistics> expenseTime=null;
            List<CustomValueStatistics> meatDay=null;
            //满足所有条件的vipId
            List<Integer> vipIds=new ArrayList<>();

            //人均消费
            if(consumeStart!=null||consumeEnd!=null){
                  consume = iResvOrderService.statisticsOrderAboutConsume(customValueConfig, date.toString(), lastDate.toString());
                  if(consume.size()>0)
                    vipIds=consume.stream().map(CustomValueStatistics::getVipId).collect(Collectors.toList());

            }
            //总消费金额
            if(expenseMoneyStart!=null||expenseMoneyEnd!=null){
                expenseMoney = iResvOrderService.statisticsOrderAboutExpenseMoney(customValueConfig, date.toString(), lastDate.toString());
                vipIds=processingCondition(expenseMoney, vipIds);

            }
            //消费次数
            if(expenseTimeStart!=null||expenseTimeEnd!=null){
                  expenseTime = iResvOrderService.statisticsOrderAboutExpenseTime(customValueConfig, date.toString(), lastDate.toString());
                vipIds=processingCondition(expenseTime, vipIds);

            }
            //就餐时间范围
            if(meatDayStart!=null||meatDayEnd!=null){
                 LocalDate meatDayE=date;
                 LocalDate meatDayS=lastDate;
                if(meatDayStart!=null){
                    meatDayE=date.minusDays(meatDayStart);
                }
                if(meatDayEnd!=null){
                    meatDayS=date.minusDays(meatDayEnd);
                }

                 meatDay = iResvOrderService.statisticsOrderAboutMeetingDay(customValueConfig, meatDayS.toString(), meatDayE.toString());
                vipIds=processingCondition(meatDay, vipIds);

            }

            //满足所有条件

            for (Integer vipId : vipIds) {
                CustomValueStatistics customValueStatistics = new CustomValueStatistics();
                if(consume!=null&&consume.size()>0){
                    customValueStatistics.setConsume(getCustomValueStatisticsField(consume, vipId).getConsume());
                }
                if(expenseMoney!=null&&expenseMoney.size()>0){
                    customValueStatistics.setExpenseMoney(getCustomValueStatisticsField(expenseMoney, vipId).getExpenseMoney());
                }
                if(expenseTime!=null&&expenseTime.size()>0){
                    customValueStatistics.setExpenseTime(getCustomValueStatisticsField(expenseTime, vipId).getExpenseTime());
                }
                if(meatDay!=null&&meatDay.size()>0){
                    customValueStatistics.setLastMeatDay(getCustomValueStatisticsField(meatDay,vipId).getLastMeatDay());
                }


                Vip vip = iVipService.selectById(vipId);
                customValueStatistics.setVipId(vipId);
                if(vip!=null) {
                    customValueStatistics.setVipName(vip.getVipName());
                    customValueStatistics.setVipPhone(vip.getVipPhone());
                }

                customValueStatistics.setBusinessId(customValueConfig.getBusinessId());

                customValueStatistics.setCustomValueConfigId(customValueConfig.getId());
                customValueStatistics.setStatisticsYear(year);
                customValueStatistics.setStatisticsMonth(month);

                //判断是否已经统计过
                CustomValueStatistics condition = new CustomValueStatistics();
                condition.setCustomValueConfigId(customValueConfig.getId());
                condition.setStatisticsMonth(month);
                condition.setStatisticsYear(year);
                CustomValueStatistics one = iCustomValueStatisticsService.selectOne(new EntityWrapper<>(condition));
                if(one!=null){
                    customValueStatistics.setId(one.getId());
                }else{
                    customValueStatistics.setCreateAt(now);
                }
                customValueStatistics.setUpdateAt(now);
                installData.add(customValueStatistics);
                customValueConfig.setLastStatisticsAt(now);
            }
        }
        if(installData.size()==0){
            return ;
        }
        iCustomValueConfigService.updateBatchById(customValueConfigs);
        iCustomValueStatisticsService.insertOrUpdateBatch(installData);
    }

    private CustomValueStatistics getCustomValueStatisticsField(List<CustomValueStatistics> data, Integer vipId) {
        Optional<CustomValueStatistics> one = data.stream().filter(item -> item.getVipId().equals(vipId)).findFirst();
        if(one.isPresent()){
            return one.get();
        }
        return new CustomValueStatistics();
    }

    private  List<Integer> processingCondition(List<CustomValueStatistics> data, List<Integer> vipIds) {
        if(data.size()>0){
            if(vipIds.size()>0){
                Iterator<Integer> iterator = vipIds.iterator();
                if(iterator.hasNext()){
                    if(!data.contains(iterator.next())){
                        iterator.remove();
                    }
                }
            }else {
                vipIds=data.stream().map(CustomValueStatistics::getVipId).collect(Collectors.toList());
            }
        }
        return vipIds;
    }


}
