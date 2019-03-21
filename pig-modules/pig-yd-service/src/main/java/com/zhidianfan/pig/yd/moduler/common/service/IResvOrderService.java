package com.zhidianfan.pig.yd.moduler.common.service;


import com.baomidou.mybatisplus.service.IService;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.CustomValueConfig;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.CustomValueStatistics;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.HotelMarketingStatistics;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrder;



import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hzp
 * @since 2019-01-15
 */
public interface IResvOrderService extends IService<ResvOrder> {



    /**
     * 统计订单 关于人均消费
     * @param customValueConfig
     * @param startDate
     * @param endDate
     * @returnC
     */
    List<CustomValueStatistics> statisticsOrderAboutConsume(CustomValueConfig customValueConfig, String startDate, String endDate);

    /**
     * 统计订单关于就餐时间
     * @param customValueConfig
     * @param startDate
     * @param endDate
     * @return
     */
    List<CustomValueStatistics> statisticsOrderAboutMeetingDay(CustomValueConfig customValueConfig, String startDate, String endDate);

    /**
     * 统计订单 关于就餐次数
     * @param customValueConfig
     * @param startDate
     * @param endDate
     * @return
     */
    List<CustomValueStatistics> statisticsOrderAboutExpenseTime(CustomValueConfig customValueConfig, String startDate, String endDate);


    /**
     * 统计订单 关于消费总金额
     * @param customValueConfig
     * @param startDate
     * @param endDate
     * @return
     */
    List<CustomValueStatistics> statisticsOrderAboutExpenseMoney(CustomValueConfig customValueConfig,String startDate, String endDate);


    /**
     * 门店数据统计
     * @param startDate
     * @param endDate
     * @return
     */
    List<HotelMarketingStatistics> statisticsHotel(String startDate, String endDate,Integer businessId);


    void updateThirdOrderNo(Integer businessId);
}
