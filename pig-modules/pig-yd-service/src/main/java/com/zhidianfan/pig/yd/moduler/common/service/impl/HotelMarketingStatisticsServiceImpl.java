package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.HotelMarketingStatistics;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.HotelMarketingStatisticsMapper;
import com.zhidianfan.pig.yd.moduler.common.service.IHotelMarketingStatisticsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 门店营销统计 服务实现类
 * </p>
 *
 * @author ljh
 * @since 2019-01-17
 */
@Service
public class HotelMarketingStatisticsServiceImpl extends ServiceImpl<HotelMarketingStatisticsMapper, HotelMarketingStatistics> implements IHotelMarketingStatisticsService {

    @Override
    public List<HotelMarketingStatistics> hotelData(Page page,String businessName) {
        return baseMapper.selectHotelByBusinessName(page,businessName);
    }
}
