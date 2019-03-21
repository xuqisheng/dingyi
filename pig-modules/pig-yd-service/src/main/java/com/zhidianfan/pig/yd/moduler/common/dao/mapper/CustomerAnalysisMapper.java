package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessCustomerAnalysis;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrder;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Vip;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.VipValue;
import com.zhidianfan.pig.yd.moduler.resv.qo.CustomerAnalysisResvOrderQO;
import com.zhidianfan.pig.yd.moduler.resv.qo.CustomerAnalysisVipQO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 提供客户分析的各类接口
 *
 * @author wangyz
 * @version v 0.1 2019-03-19 11:09 wangyz Exp $
 */
@Mapper
public interface CustomerAnalysisMapper extends BaseMapper<BusinessCustomerAnalysis> {


    /**
     * 订单表查询用户id
     *
     * @param customerAnalysisResvOrderQO 订单查询对象
     * @return 订单集合
     */
    List<Integer> queryResvOrderVipIdsList(CustomerAnalysisResvOrderQO customerAnalysisResvOrderQO);

    /**
     * 获取所有新用户
     *
     * @param businessId 酒店id
     * @param beginDate  起始时间
     * @param endDate    结束时间  传null就默认为当天时间
     * @return 新用户id
     */
    List<Integer> getNewVip(@Param("businessId") Integer businessId, @Param("begin") LocalDateTime beginDate, @Param("end") LocalDateTime endDate);

    /**
     * 获取所有活跃用户
     *
     * @param businessId 酒店id
     * @param activeDay  活跃天数
     * @return 活跃用户id
     */
    List<Integer> getActiveVip(@Param("businessId") Integer businessId, @Param("activeDay") Integer activeDay);

    /**
     * 查询用户列表
     *
     * @param customerAnalysisVipQO 用户查询对象
     * @return 客户集合
     */
    List<Vip> queryVipList(CustomerAnalysisVipQO customerAnalysisVipQO);

    /**
     * 查询用户价值配置
     *
     * @param businessId 酒店id
     * @param type       类型  1:活跃用户  2:沉睡用户  3:流失用户  4:意向用户   5:恶意用户  6:高价值用户
     * @return 酒店配置
     */
    VipValue queryVipValueByBusinessId(@Param("businessId") Integer businessId, @Param("type") Integer type);

    /**
     * 查询所有上线酒店id
     *
     * @return 酒店id
     */
    List<Integer> queryBusinessIds();


    /**
     * 清理指定月份的数据
     *
     * @param date yyyy-MM
     */
    void cleanCustomerAnalysis(String date);

    /**
     * 根据酒店id和日期更新客户分析
     *
     * @param businessCustomerAnalysis 客户分析
     * @return 成功或者失败
     */
    Integer updateByBusinessIdAndDate(BusinessCustomerAnalysis businessCustomerAnalysis);
}