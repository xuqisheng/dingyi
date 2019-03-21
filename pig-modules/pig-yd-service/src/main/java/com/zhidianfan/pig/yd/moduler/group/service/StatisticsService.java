package com.zhidianfan.pig.yd.moduler.group.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Brand;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Business;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.SalesmanStatistics;
import com.zhidianfan.pig.yd.moduler.common.service.IBrandService;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessService;
import com.zhidianfan.pig.yd.moduler.common.service.ISalesmanStatisticsService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @User: ljh
 * @Date: 2019-01-27
 * @Time: 20:11
 */
@Service
public class StatisticsService {

    @Resource
    private IBrandService iBrandService;

    @Resource
    private IBusinessService iBusinessService;

    @Resource
    private ISalesmanStatisticsService iSalesmanStatisticsService;

    @Async
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public void salesmanOrder(Integer type,Date startTime){

        ZoneId zoneId = ZoneId.systemDefault();
        Brand condition = new Brand();
        condition.setStatus("1");
        List<Brand> brands = iBrandService.selectList(new EntityWrapper<>(condition));
        List<SalesmanStatistics> install = new ArrayList<>();

        //时间计算
        LocalDateTime startDateTime = LocalDateTime.ofInstant(startTime.toInstant(), zoneId);
        int i=startDateTime.getMonthValue()%3==0?0:1;
        int quarter=startDateTime.getMonthValue()/3+i;

        for (Brand brand : brands) {

            Business condition1 = new Business();
            condition1.setBrandId(brand.getId());
            condition1.setStatus("1");
            List<Business> businesses = iBusinessService.selectList(new EntityWrapper<>(condition1));
            if(businesses.size()==0){
                continue;
            }
            List<Integer> businessIds = businesses.stream().map(Business::getId).collect(Collectors.toList());
            LocalDate startDate=null;
            LocalDate endDate=null;

            //日期整理
            if(type==1){
                startDate=startDateTime.toLocalDate().withMonth(1).withDayOfMonth(1);
                endDate=startDate.plusYears(1);
            }else if(type==2){
                startDate=startDateTime.toLocalDate().withMonth((quarter-1)*3+1).withDayOfMonth(1);
                endDate=startDateTime.toLocalDate().withDayOfMonth(1);
                if(quarter*3+1==13){
                    endDate=endDate.plusYears(1).withMonth(1);
                }else {
                    endDate=endDate.withMonth(quarter*3+1);
                }
            }
            else if(type==3){
                startDate=startDateTime.toLocalDate().withDayOfMonth(1);
                endDate=startDateTime.toLocalDate().plusMonths(1).withDayOfMonth(1);
            }

            List<SalesmanStatistics> salesmanStatistics = iSalesmanStatisticsService.queryAppUserStatistics(businessIds, startDate.toString(), endDate.toString());

            //是否存在
            SalesmanStatistics condition2 = new SalesmanStatistics();
            condition2.setBrandId(brand.getId());
            condition2.setStatisticsType(type);
            condition2.setYear(startDateTime.getYear());
            if(type==2){
                condition2.setStatisticsQuarter(quarter);
            }else if(type==3){
                condition2.setMonth(startDateTime.getMonthValue());
            }

            iSalesmanStatisticsService.delete(new EntityWrapper<>(condition2));
            for (SalesmanStatistics salesmanStatistic : salesmanStatistics) {
                salesmanStatistic.setStatisticsType(type);

                //1年度 2季度 3月度
                salesmanStatistic.setYear(startDateTime.getYear());
                salesmanStatistic.setStatisticsQuarter(quarter);
                salesmanStatistic.setMonth(startDateTime.getMonthValue());
                salesmanStatistic.setBrandId(brand.getId());
            }
            install.addAll(salesmanStatistics);
        }

        if(install.size()==0){
            return ;
        }
        iSalesmanStatisticsService.insertBatch(install);
        return ;

    }

}
