package com.zhidianfan.pig.yd.moduler.common.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessOrderStatisticsDay;
import com.baomidou.mybatisplus.service.IService;
import com.zhidianfan.pig.yd.moduler.resv.dto.BusinessMonthDataDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.ConsumptionFrequencyDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.RepeatVipDTO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hzp
 * @since 2018-12-20
 */
public interface IBusinessOrderStatisticsDayService extends IService<BusinessOrderStatisticsDay> {

    List<BusinessOrderStatisticsDay> getYesterdayBusninessData(Integer businessId);

    Integer countNewVip(Integer businessId, Integer mealTypeId);

    Integer countRepeatVip(Integer businessId, Integer mealTypeId);

    BusinessMonthDataDTO getBusinessMonthdata(Integer businessId, String dataMonth);

    List<ConsumptionFrequencyDTO> getConsumptionFrequency(Integer businessId, String dataMonth);

    RepeatVipDTO getRepeatVipByMonth(Integer businessId, String dataMonth);
}
