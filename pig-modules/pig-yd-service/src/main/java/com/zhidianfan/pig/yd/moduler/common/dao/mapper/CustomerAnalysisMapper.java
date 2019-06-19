package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 提供客户分析的各类接口
 *
 * @author wangyz
 * @version v 0.1 2019-03-19 11:09 wangyz Exp $
 */
@Mapper
public interface CustomerAnalysisMapper extends BaseMapper<BusinessCustomerAnalysis> {

    /**
     * 返回用户在上个月是活跃的用户
     *
     * @param businessId 酒店id
     * @param date       执行的当月日期
     * @param oldDate    上个月的日期
     * @param vips       活跃用户
     * @param valueType  类型 1:活跃 2:流失
     * @return 符合要求的活跃用户
     */
    List<BusinessCustomerAnalysisDetail> existActiveVip(@Param("businessId") Integer businessId, @Param("date") String date, @Param("oldDate") String oldDate, @Param("vips") List<Vip> vips, @Param("valueType") Integer valueType);


    /**
     * 根据日期来判断上个月是否是活跃用户
     *
     * @param businessId 酒店id
     * @param date       日期
     * @param beginDate  开始日期
     * @param endDate    结束日期
     * @param valueType  类型 1:活跃  2:沉睡
     * @return 符合要求的用户
     */
    List<BusinessCustomerAnalysisDetail> existActiveVipByDay(@Param("businessId") Integer businessId, @Param("date") String date, @Param("beginDate") LocalDateTime beginDate, @Param("endDate") LocalDateTime endDate, @Param("valueType") Integer valueType, @Param("vips") List<Vip> vips);

    /**
     * 返回用户在上个月是沉睡的用户
     *
     * @param businessId 酒店id
     * @param date       执行的当月日期
     * @param oldDate    上个月的日期
     * @param vips       流失用户
     * @return 符合要求的流失用户
     */
    List<BusinessCustomerAnalysisDetail> existFlowVip(@Param("businessId") Integer businessId, @Param("date") String date, @Param("oldDate") String oldDate, @Param("vips") List<Vip> vips);


    /**
     * 根据时间判断上个月是否是沉睡用户
     *
     * @param businessId 酒店id
     * @param date       日期
     * @param vips       用户
     * @param begin      开始
     * @param end        结束
     * @param now        截止时间
     * @return 符合要求的用户
     */
    List<BusinessCustomerAnalysisDetail> existFlowVipByDay(@Param("businessId") Integer businessId, @Param("date") String date, @Param("vips") List<Vip> vips, @Param("begin") LocalDateTime begin, @Param("end") LocalDateTime end, @Param("nowDate") LocalDateTime now);


    /**
     * 返回用户在上个月是沉睡或者流失的用户
     *
     * @param businessId 酒店id
     * @param date       执行的当月日期
     * @param oldDate    上个月的日期
     * @param vips       活跃用户
     * @return 符合要求的用户
     */
    List<BusinessCustomerAnalysisDetail> existAwakenVip(@Param("businessId") Integer businessId, @Param("date") String date, @Param("oldDate") String oldDate, @Param("vips") List<Vip> vips);

    /**
     * 插入酒店客户分析明细
     *
     * @param businessCustomerAnalysisDetail 客户明细对象
     */
    void insertBusinessCustomerAnalysisDetail(@Param("list") List<BusinessCustomerAnalysisDetail> businessCustomerAnalysisDetail);

    /**
     * 获取所有新用户
     *
     * @param businessId 酒店id
     * @param beginDate  起始时间
     * @param endDate    结束时间  传null就默认为当天时间
     * @return 获取
     */
    List<Map<String, Object>> getAppUserNewVip(@Param("businessId") Integer businessId, @Param("begin") LocalDateTime beginDate, @Param("end") LocalDateTime endDate);

    /**
     * 获取所有新用户
     *
     * @param businessId 酒店id
     * @param beginDate  起始时间
     * @param endDate    结束时间  传null就默认为当天时间
     * @return 新用户id
     */
    List<Vip> getNewVip(@Param("businessId") Integer businessId, @Param("begin") LocalDateTime beginDate, @Param("end") LocalDateTime endDate);

    /**
     * 获取所有活跃用户
     *
     * @param businessId 酒店id
     * @param beginDate  酒店配置的活跃开始时间
     * @param endDate    结束时间 传null就默认为当天时间
     * @return 活跃用户id
     */
    List<Vip> getActiveVip(@Param("businessId") Integer businessId, @Param("begin") LocalDateTime beginDate, @Param("end") LocalDateTime endDate);

    /**
     * 查询所有沉睡用户
     *
     * @param businessId 酒店id
     * @param beginDate  开始时间
     * @param endDate    结束时间
     * @return 所有在开始和结束时间范围内下过单  但是没有在开始时间之前下过单的用户
     */
    List<Vip> getSleepVip(@Param("businessId") Integer businessId, @Param("begin") LocalDateTime beginDate, @Param("end") LocalDateTime endDate, @Param("nowDate") LocalDateTime nowDate);


    /**
     * 查询所有流失用户
     *
     * @param businessId  酒店id
     * @param beginDate   开始时间
     * @param activeBegin 活跃的时间开始
     * @param activeEnd   活跃的时间结束
     * @return 所有在开始时间到现在没有下过订单的用户
     */
    List<Vip> getFlowVip(@Param("businessId") Integer businessId, @Param("begin") LocalDateTime beginDate, @Param("nowDate") LocalDateTime nowDate, @Param("activeBegin") LocalDateTime activeBegin, @Param("activeEnd") LocalDateTime activeEnd);


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
    void updateByBusinessIdAndDate(BusinessCustomerAnalysis businessCustomerAnalysis);

    /**
     * 记录执行时间
     *
     * @param param taskName 任务名称  taskTime  任务时间
     */
    void insertTask(Map<String, Object> param);
}