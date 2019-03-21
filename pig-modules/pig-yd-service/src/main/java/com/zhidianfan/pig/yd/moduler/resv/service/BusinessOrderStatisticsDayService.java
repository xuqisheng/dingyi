package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessOrderStatisticsDay;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessOrderStatisticsDayService;
import com.zhidianfan.pig.yd.moduler.common.service.IResvOrderAndroidService;
import com.zhidianfan.pig.yd.moduler.resv.dto.BusinessMonthDataDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.ConsumptionFrequencyDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.RepeatVipDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BusinessOrderStatisticsDayService {

    @Autowired
    IResvOrderAndroidService iResvOrderAndroidService;

    @Autowired
    IBusinessOrderStatisticsDayService iBusinessOrderStatisticsDayService;

    /**
     * 统计昨天各个酒店的各个餐别的 结账桌数 ,总金额 就餐人数,新增客户数,复购客户数,散客客户数,酒店启用的桌子数
     */
    @Async
    public void statisticsData() {
        //1. 查询上线酒店id
        List<Integer> businessIds = iResvOrderAndroidService.getYesterdayBusniness();

        //今天
        Date today = new Date();
        //昨天
        Date yesterday = new Date(today.getTime() - 86400000L);

        //遍历每个酒店,统计出数据然后插入表
        for (Integer businessId : businessIds) {

            //查询昨天的某个酒店的各个餐别的 结账桌数 就餐人数  散客订单数 批次号数
            List<BusinessOrderStatisticsDay> businessOrderStatisticsDayList = iBusinessOrderStatisticsDayService.getYesterdayBusninessData(businessId);

            if(0 == businessOrderStatisticsDayList.size() ){
                continue;
            }

            //计算当天每个餐别的新增客户数量
            for (int i = 0; i < businessOrderStatisticsDayList.size(); i++) {
                //获得当前餐别
                Integer mealTypeId = businessOrderStatisticsDayList.get(i).getMealTypeId();

                if(null == mealTypeId){
                    businessOrderStatisticsDayList.get(i).setNewVip(0);
                    businessOrderStatisticsDayList.get(i).setRepeatVip(0);
                    businessOrderStatisticsDayList.get(i).setCalDate(yesterday);
                    businessOrderStatisticsDayList.get(i).setCreatedAt(today);
                    continue;
                }

                //根据当前餐别查询在这个餐别开始之前顾客是否有订单,如果无他为新增客户 ,统计加1
                Integer newVipNum = iBusinessOrderStatisticsDayService.countNewVip(businessId, mealTypeId);
                //获取每个餐别的复购客户
                Integer repeatVip = iBusinessOrderStatisticsDayService.countRepeatVip(businessId, mealTypeId);
                businessOrderStatisticsDayList.get(i).setNewVip(newVipNum);
                businessOrderStatisticsDayList.get(i).setRepeatVip(repeatVip);
                businessOrderStatisticsDayList.get(i).setCalDate(yesterday);
                businessOrderStatisticsDayList.get(i).setCreatedAt(today);
            }

            //插入BusinessOrderStatisticsDayList
            iBusinessOrderStatisticsDayService.insertBatch(businessOrderStatisticsDayList);
        }
    }

    /**
     * 获取某酒店某天数据
     * @param businessId 酒店id
     * @param calDate 日期
     * @return 酒店数据
     */
    public List<BusinessOrderStatisticsDay> getBusinessDaydata(Integer businessId, String calDate) {

        List<BusinessOrderStatisticsDay> businessOrderStatisticsDays = iBusinessOrderStatisticsDayService.selectList(new EntityWrapper<BusinessOrderStatisticsDay>()
                .eq("cal_date", calDate)
                .eq("business_id", businessId));
        return businessOrderStatisticsDays;

    }

    /**
     * 获取最近几个月某酒店数据
     * @param businessId 酒店id
     * @param monthNum 距离现在的月份差值,1代表本月,2代表本月和上月数据以此类推
     * @return 酒店数据
     */
    public List<BusinessMonthDataDTO> getBusinessMonthData(Integer businessId, Integer monthNum) {

        LocalDate now = LocalDate.now();

        List<BusinessMonthDataDTO> businessMonthDataDTOList = new ArrayList<>();
        for (int i = monthNum-1; i >= 0; --i){

            LocalDate localDate = now.minusMonths(i);

            String month = getMonth(localDate.getMonthValue());

            String dataMonth = localDate.getYear() + "-" + month;
            BusinessMonthDataDTO businessMonthDataDTO =   iBusinessOrderStatisticsDayService.getBusinessMonthdata(businessId,dataMonth);
            businessMonthDataDTOList.add(businessMonthDataDTO);
        }

        return businessMonthDataDTOList;
    }

    /**
     *  某酒店该天的订单分布
     * @param businessId 酒店id
     * @param calDate 订单日期
     * @return 分布情况
     */
    public List<Map<String, Integer>> getOrderDistribution(Integer businessId, String calDate) {

        return iResvOrderAndroidService.getOrderDistribution(businessId, calDate);
    }

    /**
     *  消费频次
     * @param businessId 酒店id
     * @param monthNum 相差月数
     * @return 消费频次的客户数量
     */
    public List<ConsumptionFrequencyDTO> getConsumptionFrequency(Integer businessId, Integer monthNum) {

        LocalDate now = LocalDate.now();

        LocalDate localDate = now.minusMonths(monthNum);

        String month = getMonth(localDate.getMonthValue());


        String dataMonth = localDate.getYear() + "-" + month+"-" + localDate.getDayOfMonth();

        List<ConsumptionFrequencyDTO> consumptionFrequencyList =   iBusinessOrderStatisticsDayService.getConsumptionFrequency(businessId,dataMonth);

        return consumptionFrequencyList;
    }

    /**
     * 复购客户
     * @param businessId 酒店id
     * @param monthNum 月份差数
     * @return 几个月的复购客户数量
     */
    public List<RepeatVipDTO> getRepeatVipByMonth(Integer businessId, Integer monthNum) {


        LocalDate now = LocalDate.now();

        List<RepeatVipDTO> repeatVipDTOS = new ArrayList<>();

        for (int i = monthNum-1; i >= 0; --i){
            LocalDate localDate = now.minusMonths(i);

            String month = getMonth(localDate.getMonthValue());

            String dataMonth = localDate.getYear() + "-" + month;
            RepeatVipDTO repeatVipDTO =    iBusinessOrderStatisticsDayService.getRepeatVipByMonth(businessId,dataMonth);
            repeatVipDTOS.add(repeatVipDTO);
        }
        return repeatVipDTOS;
    }


    /**
     * 当月份小于10的时候加0  例如: 1 月变为01月
     * @param monthValue 月份数字
     * @return 月份字符
     */
    private static String  getMonth(int monthValue){
        String month;
        if(monthValue < 10) {
            month = "0" + monthValue;
        }else {
            month = String.valueOf(monthValue);
        }
        return month;
    }

}
