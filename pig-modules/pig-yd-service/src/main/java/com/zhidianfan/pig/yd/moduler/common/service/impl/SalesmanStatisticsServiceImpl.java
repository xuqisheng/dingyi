package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.SalesmanStatistics;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.SalesmanStatisticsMapper;
import com.zhidianfan.pig.yd.moduler.common.service.ISalesmanStatisticsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ljh
 * @since 2019-01-27
 */
@Service
public class SalesmanStatisticsServiceImpl extends ServiceImpl<SalesmanStatisticsMapper, SalesmanStatistics> implements ISalesmanStatisticsService {

    @Override
    public List<SalesmanStatistics> queryAppUserStatistics(List businessIdsList, String creattime, String endtime) {
        return baseMapper.queryAppUserStatistics(businessIdsList,creattime,endtime);
    }
}
