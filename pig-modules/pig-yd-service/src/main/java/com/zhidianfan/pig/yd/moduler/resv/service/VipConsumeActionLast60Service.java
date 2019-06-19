package com.zhidianfan.pig.yd.moduler.resv.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.MasterCustomerVipMapping;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrder;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Vip;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.VipConsumeActionLast60;
import com.zhidianfan.pig.yd.moduler.resv.constants.CustomerValueConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.velocity.runtime.parser.node.MathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

/**
 * @author sjl
 * 2019-05-27 13:21
 */
@Slf4j
@Service
public class VipConsumeActionLast60Service {

    @Autowired
    private CustomerRecordService customerRecordService;

    @Autowired
    private CustomerValueListService customerValueListService;

    public Map<Integer,VipConsumeActionLast60> getVipConsumeActionLast60(List<Vip> vips, Map<Integer,List<ResvOrder>> resvOrdersBy60daysMap, List<MasterCustomerVipMapping> masterCustomerVipMappingList) {

        Map<Integer,VipConsumeActionLast60> map = new HashMap<>();

        for (Vip vip:vips){

            try {
                List<ResvOrder> resvOrderList = new ArrayList<>();
                List<ResvOrder> resvOrdersBy60days = resvOrdersBy60daysMap.get(vip.getId());
                // 主客订单
                List<ResvOrder> manOrderList = customerRecordService.getManOrderList(vip, masterCustomerVipMappingList, resvOrdersBy60daysMap);

                if (CollectionUtils.isNotEmpty(resvOrdersBy60days)) {
                    resvOrderList.addAll(resvOrdersBy60days);
                }
                if (CollectionUtils.isNotEmpty(manOrderList)) {
                    boolean manOrder = customerValueListService.isManOrder(vip, masterCustomerVipMappingList);
                    if (manOrder) {
                        resvOrderList.addAll(manOrderList);
                    } else {
                        resvOrderList.removeAll(manOrderList);
                    }
                }
                if (resvOrdersBy60days != null) {
                    resvOrdersBy60days.addAll(manOrderList);
                }

                VipConsumeActionLast60 vipConsumeActionLast60 = new VipConsumeActionLast60();

                vipConsumeActionLast60.setVipId(vip.getId());
                // 消费完成总订单数
                vipConsumeActionLast60.setTotalOrderNo(getTotalOrderNo60(resvOrderList));
                // 消费完成总桌数
                vipConsumeActionLast60.setTotalTableNo(getTotalTableNo60(resvOrderList));
                // 消费完成总人数
                vipConsumeActionLast60.setTotalPersonNo(getTotalPersonNo60(resvOrderList));
                // 撤单桌数
                vipConsumeActionLast60.setCancelTableNo(getCancelTableNo60(resvOrderList));
                // 消费总金额,单位:分
                vipConsumeActionLast60.setTotalConsumeAmount(getTotalConsumeAmount60(resvOrderList));
                // 桌均消费,单位:分
                vipConsumeActionLast60.setTableConsumeAvg(getTableConsumeAvg60(resvOrderList));
                // 人均消费,单位:分
                vipConsumeActionLast60.setPersonConsumeAvg(getPersonConsumeAvg(resvOrderList));
                // 消费频次
                vipConsumeActionLast60.setConsumeFrequency(getConsumeFrequency60(resvOrderList));
                vipConsumeActionLast60.setCreateUserId(CustomerValueConstants.DEFAULT_USER_ID);
                vipConsumeActionLast60.setCreateTime(LocalDateTime.now());
                vipConsumeActionLast60.setUpdateUserId(CustomerValueConstants.DEFAULT_USER_ID);
                vipConsumeActionLast60.setUpdateTime(LocalDateTime.now());


                map.put(vip.getId(),vipConsumeActionLast60);
            }catch (Exception e){
                log.error(e.getMessage(),e);
            }

        }



        return map;
    }

    /**
     * 60天内消费完成总订单次数数
     *
     * @param resvOrdersBy60days 订单列表
     * @return 0-无
     */
    private Integer getTotalOrderNo60(List<ResvOrder> resvOrdersBy60days) {
        // 按批次号，累加该客户的订单（只要一个批次号内有一个桌位入座/完成即可）
        if (CollectionUtils.isEmpty(resvOrdersBy60days)){
            return 0;
        }
        int size = resvOrdersBy60days.stream()
                .filter(order -> "2".equals(order.getStatus()) || "3".equals(order.getStatus()))
                .collect(groupingBy(ResvOrder::getBatchNo))
                .size();

        return size;
    }

    /**
     * 60天内消费完成总桌数
     *
     * @param resvOrdersBy60day 订单列表
     * @return 0-无
     */
    private Integer getTotalTableNo60(List<ResvOrder> resvOrdersBy60day) {
        if (CollectionUtils.isEmpty(resvOrdersBy60day)){
            return 0;
        }
        // 所有该客户已完成/入座的订单数量
        long count = resvOrdersBy60day.stream()
                .filter(order -> "2".equals(order.getStatus()) || "3".equals(order.getStatus()))
                .count();
        return (int) count;
    }

    /**
     * 60天内消费完成总人数
     *
     * @param resvOrdersBy60day 60天内产生的订单列表
     * @return 0-无
     */
    public Integer getTotalPersonNo60(List<ResvOrder> resvOrdersBy60day) {

        if (CollectionUtils.isEmpty(resvOrdersBy60day)){
            return 0;
        }

        // 所有该客户已完成/入座的订单中的实际人数之和。（如果没有实际人数，使用就餐人数）
        int sum = resvOrdersBy60day.stream()
                // 订单状态:已经入座，已完成
                .filter(order -> "2".equals(order.getStatus()) || "3".equals(order.getStatus()))
                .mapToInt(order -> {
                    String actualNum = order.getActualNum();
                    int personCount = 0;
                    try {
                        personCount = Integer.parseInt(actualNum);
                    } catch (NumberFormatException e) {
                        log.warn("用餐人数类型由 String -> int 转换失败,取预订人数, orderId:{}", order.getId());
                    }
                    if (personCount <= 0) {
                        String resvNum = order.getResvNum();
                        try {
                            personCount = Integer.parseInt(resvNum);
                        } catch (NumberFormatException e) {
                            log.warn("预订人数类型由 String -> int 转换失败，取 0， orderId:[{}]", order.getId());
                            personCount = 0;
                        }
                    }
                    return personCount;
                })
                .sum();
        return sum;
    }

    /**
     * 60天内消次数 = 60天内消费总次数/ 2
     *
     * @param resvOrdersBy60day 订单列表
     * @return
     */
    private Integer getConsumeFrequency60(List<ResvOrder> resvOrdersBy60day) {
        if (CollectionUtils.isEmpty(resvOrdersBy60day)){
            return 0;
        }
        // 消费总次数
        long count = resvOrdersBy60day.stream()
                .filter(order -> "2".equals(order.getStatus()) || "3".equals(order.getStatus()))
                .count();
        int customerCount = (int) count;
        log.info("60天内消费次数：[{}]", count);
        return customerCount;
    }


    /**
     * 60天内人均消费,单位:分
     *
     * @param resvOrdersBy60day 订单列表
     * @return
     */
    private Integer getPersonConsumeAvg(List<ResvOrder> resvOrdersBy60day) {
        if (CollectionUtils.isEmpty(resvOrdersBy60day)){
            return 0;
        }
        // 总消费金额/对应批次号的订单实际人数之和。（如果订单没有实际人数，用预订人数代替）
        // 总消费金额
        int sum = getTotalConsumeAmount60(resvOrdersBy60day);
        //  订单实际人数之和
        int personCount = resvOrdersBy60day.stream()
                .filter(order -> "2".equals(order.getStatus()) || "3".equals(order.getStatus()))
                .filter(order -> StringUtils.isNotBlank(order.getResvNum()) || StringUtils.isNotBlank(order.getActualNum()))
                .mapToInt(order -> {
                    String actualNum = order.getActualNum();
                    try {
                        return Integer.parseInt(actualNum);
                    } catch (NumberFormatException e) {
                        String resvNum = order.getResvNum();
                        try {
                            return Integer.parseInt(resvNum);
                        } catch (NumberFormatException ex) {
                            return CustomerValueConstants.DEFAULT_ORDER_COUNT;
                        }
                    }
                }).sum();
        int count = Math.max(personCount, 1);
        Number divide = MathUtils.divide(sum, count);
        return Math.round(divide.floatValue());
    }


    /**
     * 60天内桌均消费,单位:分
     *
     * @param resvOrdersBy60day 订单列表
     * @return
     */
    private Integer getTableConsumeAvg60(List<ResvOrder> resvOrdersBy60day) {
        if (CollectionUtils.isEmpty(resvOrdersBy60day)){
            return 0;
        }
        // 总消费金额/对应批次号的订单之和。（仅指已入座/完成的订单）
        int consumerSum = getTotalConsumeAmount60(resvOrdersBy60day);
        Integer totalTableNo = getTotalTableNo60(resvOrdersBy60day);
        long countOrder =  Math.max(totalTableNo, 1);
        Number divide = MathUtils.divide(consumerSum, countOrder);
        return Math.round(divide.floatValue());
    }

    /**
     * 60天内消费总金额,单位:分
     *
     * @param resvOrdersBy60day 订单列表
     * @return
     */
    private Integer getTotalConsumeAmount60(List<ResvOrder> resvOrdersBy60day) {
        if (CollectionUtils.isEmpty(resvOrdersBy60day)){
            return 0;
        }
        return resvOrdersBy60day.stream()
                .filter(order -> "2".equals(order.getStatus()) || "3".equals(order.getStatus()))
                .map(ResvOrder::getPayamount)
                .mapToInt(payAmount -> {
                    if (StringUtils.isBlank(payAmount)) {
                        return CustomerValueConstants.DEFAULT_PAYAMOUNT;
                    }
                    try {
                        float f = Float.parseFloat(payAmount);
                        return Math.round(f * 100);
                    } catch (NumberFormatException e) {
                        log.error("转换失败 支付金额 ：[{}]- 支付金额数据格式错误", payAmount);
                        return CustomerValueConstants.DEFAULT_PAYAMOUNT;
                    }
                })
                .sum();
    }

    /**
     * 60天内撤单桌数
     *
     * @param resvOrdersBy60day 订单列表
     * @return
     */
    private Integer getCancelTableNo60(List<ResvOrder> resvOrdersBy60day) {
        if (CollectionUtils.isEmpty(resvOrdersBy60day)){
            return 0;
        }
        long count = resvOrdersBy60day.stream()
                .filter(order -> "4".equals(order.getStatus()))
                .count();
        return (int) count;
    }

}
