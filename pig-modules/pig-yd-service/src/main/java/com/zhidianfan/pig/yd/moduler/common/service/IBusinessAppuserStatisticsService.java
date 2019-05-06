package com.zhidianfan.pig.yd.moduler.common.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessAppuserStatistics;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huzp
 * @since 2019-05-05
 */
public interface IBusinessAppuserStatisticsService extends IService<BusinessAppuserStatistics> {

    void clearTodayStatistics(int businessId, String lastYearMonth);

    void createTemporaryTable(int businessId, String lastYearMonth, String yearMonth);

    void dropTemporaryTable();

    void insertAppuserStatistics(int businessId, String lastYearMonth, String yearMonth);

    void insertPadStatistics(int businessId, String lastYearMonth, String yearMonth);
}
