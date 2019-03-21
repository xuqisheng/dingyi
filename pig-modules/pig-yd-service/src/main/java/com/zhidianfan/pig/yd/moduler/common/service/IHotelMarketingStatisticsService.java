package com.zhidianfan.pig.yd.moduler.common.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.HotelMarketingStatistics;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 门店营销统计 服务类
 * </p>
 *
 * @author ljh
 * @since 2019-01-17
 */
public interface IHotelMarketingStatisticsService extends IService<HotelMarketingStatistics> {

     List<HotelMarketingStatistics> hotelData(Page page,String businessName);

}
