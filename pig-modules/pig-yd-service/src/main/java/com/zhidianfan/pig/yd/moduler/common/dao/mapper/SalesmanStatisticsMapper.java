package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.SalesmanStatistics;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ljh
 * @since 2019-01-27
 */
public interface SalesmanStatisticsMapper extends BaseMapper<SalesmanStatistics> {

    List<SalesmanStatistics> queryAppUserStatistics(@Param("businessIdsList") List businessIdsList, @Param("creattime") String  creattime,@Param("endtime") String endtime);

}
