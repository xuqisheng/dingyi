package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessAppuserStatistics;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.BusinessAppuserStatisticsMapper;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessAppuserStatisticsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huzp
 * @since 2019-05-05
 */
@Service
public class BusinessAppuserStatisticsServiceImpl extends ServiceImpl<BusinessAppuserStatisticsMapper, BusinessAppuserStatistics> implements IBusinessAppuserStatisticsService {

    @Override
    public void clearTodayStatistics(int businessId, String lastYearMonth) {

        baseMapper.clearTodayStatistics(businessId,lastYearMonth);
    }

    @Override
    public void createTemporaryTable(int businessId, String lastYearMonth, String yearMonth) {

        baseMapper.createTemporaryTable(businessId,lastYearMonth,yearMonth);

    }

    @Override
    public void dropTemporaryTable() {

        baseMapper.dropTemporaryTable();
    }

    @Override
    public void insertAppuserStatistics(int businessId, String lastYearMonth, String yearMonth) {

        baseMapper.insertAppuserStatistics(businessId,lastYearMonth,yearMonth);
    }

    @Override
    public void insertPadStatistics(int businessId, String lastYearMonth, String yearMonth) {

        baseMapper.insertPadStatistics(businessId,lastYearMonth,yearMonth);
    }
}
