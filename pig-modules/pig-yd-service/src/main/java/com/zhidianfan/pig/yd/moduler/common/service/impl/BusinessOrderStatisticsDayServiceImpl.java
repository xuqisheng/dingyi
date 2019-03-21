package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessOrderStatisticsDay;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.BusinessOrderStatisticsDayMapper;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessOrderStatisticsDayService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhidianfan.pig.yd.moduler.resv.dto.BusinessMonthDataDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.ConsumptionFrequencyDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.RepeatVipDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hzp
 * @since 2018-12-20
 */
@Service
public class BusinessOrderStatisticsDayServiceImpl extends ServiceImpl<BusinessOrderStatisticsDayMapper, BusinessOrderStatisticsDay> implements IBusinessOrderStatisticsDayService {

    @Override
    public List<BusinessOrderStatisticsDay> getYesterdayBusninessData(Integer businessId) {
        return baseMapper.getYesterdayBusninessData(businessId);
    }

    @Override
    public Integer countNewVip(Integer businessId, Integer mealTypeId) {
        return baseMapper.countNewVip(businessId,mealTypeId);
    }

    @Override
    public Integer countRepeatVip(Integer businessId, Integer mealTypeId) {
        return baseMapper.countRepeatVip(businessId,mealTypeId);
    }

    @Override
    public BusinessMonthDataDTO getBusinessMonthdata(Integer businessId, String dataMonth) {
        return baseMapper.getBusinessMonthdata( businessId,  dataMonth);
    }

    @Override
    public List<ConsumptionFrequencyDTO> getConsumptionFrequency(Integer businessId, String dataMonth) {
        return baseMapper.getConsumptionFrequency(businessId,dataMonth);
    }

    @Override
    public RepeatVipDTO getRepeatVipByMonth(Integer businessId, String dataMonth) {
        return baseMapper.getRepeatVipByMonth(businessId,dataMonth);
    }
}
