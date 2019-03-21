package com.zhidianfan.pig.yd.moduler.common.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.SalesmanStatistics;
import com.baomidou.mybatisplus.service.IService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ljh
 * @since 2019-01-27
 */
public interface ISalesmanStatisticsService extends IService<SalesmanStatistics> {

    List<SalesmanStatistics> queryAppUserStatistics(List businessIdsList, String creattime, String endtime);

}
