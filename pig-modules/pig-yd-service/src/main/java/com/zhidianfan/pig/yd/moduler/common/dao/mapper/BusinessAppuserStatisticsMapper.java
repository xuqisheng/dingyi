package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessAppuserStatistics;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huzp
 * @since 2019-05-05
 */
public interface BusinessAppuserStatisticsMapper extends BaseMapper<BusinessAppuserStatistics> {

    void clearTodayStatistics(@Param("businessId")int businessId, @Param("lastYearMonth") String lastYearMonth);

    void createTemporaryTable(@Param("businessId")int businessId, @Param("lastYearMonth") String lastYearMonth, @Param("yearMonth") String yearMonth);

    void dropTemporaryTable();

    void insertAppuserStatistics(@Param("businessId")int businessId, @Param("lastYearMonth") String lastYearMonth, @Param("yearMonth") String yearMonth);

    void insertPadStatistics(@Param("businessId")int businessId, @Param("lastYearMonth") String lastYearMonth, @Param("yearMonth") String yearMonth);
}
