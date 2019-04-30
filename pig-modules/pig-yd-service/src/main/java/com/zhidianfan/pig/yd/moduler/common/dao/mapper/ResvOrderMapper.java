package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.CustomValueConfig;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.CustomValueStatistics;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.HotelMarketingStatistics;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrder;
import com.zhidianfan.pig.yd.moduler.resv.bo.DeskOrderBo;
import com.zhidianfan.pig.yd.moduler.resv.bo.MessageOrderBO;
import com.zhidianfan.pig.yd.moduler.resv.bo.OrderBO;
import com.zhidianfan.pig.yd.moduler.resv.dto.*;
import com.zhidianfan.pig.yd.moduler.resv.qo.AllResvOrderQO;
import com.zhidianfan.pig.yd.moduler.resv.qo.LockTablQO;
import com.zhidianfan.pig.yd.moduler.resv.qo.MessageOrderQO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author sherry
 * @since 2018-08-16
 */
public interface ResvOrderMapper extends BaseMapper<ResvOrder> {



    void updateThirdOrderNo(Integer businessId);


    /**
     * 统计订单 关于人均消费
     * @param customValueConfig
     * @param startDate
     * @param endDate
     * @returnC
     */
    List<CustomValueStatistics> statisticsOrderAboutConsume(@Param("data")CustomValueConfig customValueConfig,@Param("startDate") String startDate,@Param("endDate") String endDate);

    /**
     * 统计订单关于就餐时间
     * @param customValueConfig
     * @param startDate
     * @param endDate
     * @return
     */
    List<CustomValueStatistics> statisticsOrderAboutMeetingDay(@Param("data")CustomValueConfig customValueConfig,@Param("startDate") String startDate,@Param("endDate") String endDate);

    /**
     * 统计订单 关于就餐次数
     * @param customValueConfig
     * @param startDate
     * @param endDate
     * @return
     */
    List<CustomValueStatistics> statisticsOrderAboutExpenseTime(@Param("data")CustomValueConfig customValueConfig,@Param("startDate") String startDate,@Param("endDate") String endDate);


    /**
     * 统计订单 关于消费总金额
     * @param customValueConfig
     * @param startDate
     * @param endDate
     * @return
     */
    List<CustomValueStatistics> statisticsOrderAboutExpenseMoney(@Param("data")CustomValueConfig customValueConfig,@Param("startDate") String startDate,@Param("endDate") String endDate);


    /**
     * 门店数据统计
     * @param startDate
     * @param endDate
     * @return
     */
    List<HotelMarketingStatistics> statisticsHotel(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("businessId") Integer businessId);

    void updateOrderStatus1TO2(@Param("intervalNum")Integer intervalNum);

    void updateOrderStatus1TO4(@Param("intervalNum")Integer intervalNum);

    void updateOrderStatus2TO3(@Param("intervalNum")Integer intervalNum);
}
