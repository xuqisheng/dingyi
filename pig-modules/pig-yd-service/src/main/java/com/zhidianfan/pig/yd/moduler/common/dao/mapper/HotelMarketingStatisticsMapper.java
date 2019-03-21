package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.HotelMarketingStatistics;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 门店营销统计 Mapper 接口
 * </p>
 *
 * @author ljh
 * @since 2019-01-17
 */
public interface HotelMarketingStatisticsMapper extends BaseMapper<HotelMarketingStatistics> {


    List<HotelMarketingStatistics> selectHotelByBusinessName(Page page,@Param("businessName") String businessName);

}
