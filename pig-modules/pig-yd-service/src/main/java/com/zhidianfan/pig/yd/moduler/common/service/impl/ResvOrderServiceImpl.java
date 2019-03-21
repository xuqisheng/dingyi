package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.CustomValueConfig;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.CustomValueStatistics;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.HotelMarketingStatistics;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrder;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.ResvOrderMapper;
import com.zhidianfan.pig.yd.moduler.common.service.IResvOrderService;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sherry
 * @since 2018-08-16
 */
@Service
public class ResvOrderServiceImpl extends ServiceImpl<ResvOrderMapper, ResvOrder> implements IResvOrderService {


    @Override
    public void updateThirdOrderNo(Integer businessId) {
		baseMapper.updateThirdOrderNo(businessId);
    }

    @Override
    public List<CustomValueStatistics> statisticsOrderAboutConsume(CustomValueConfig customValueConfig, String startDate, String endDate) {
        return baseMapper.statisticsOrderAboutConsume(customValueConfig,startDate,endDate);
    }

    @Override
    public List<CustomValueStatistics> statisticsOrderAboutMeetingDay(CustomValueConfig customValueConfig, String startDate, String endDate) {
        return baseMapper.statisticsOrderAboutMeetingDay(customValueConfig,startDate,endDate);
    }

    @Override
    public List<CustomValueStatistics> statisticsOrderAboutExpenseTime(CustomValueConfig customValueConfig, String startDate, String endDate) {
        return baseMapper.statisticsOrderAboutExpenseTime(customValueConfig,startDate,endDate);
    }

    @Override
    public List<CustomValueStatistics> statisticsOrderAboutExpenseMoney(CustomValueConfig customValueConfig, String startDate, String endDate) {
        return baseMapper.statisticsOrderAboutExpenseMoney(customValueConfig,startDate,endDate);
    }

    @Override
    public List<HotelMarketingStatistics> statisticsHotel(String startDate, String endDate,Integer businessId) {
        return baseMapper.statisticsHotel(startDate,endDate,businessId);
    }

}
