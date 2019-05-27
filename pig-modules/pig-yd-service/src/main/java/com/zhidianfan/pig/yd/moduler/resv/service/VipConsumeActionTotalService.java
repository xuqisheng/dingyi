package com.zhidianfan.pig.yd.moduler.resv.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrder;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Vip;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.VipConsumeActionTotal;
import com.zhidianfan.pig.yd.moduler.resv.constants.CustomerValueConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * @author sjl
 * 2019-05-27 13:21
 */
@Slf4j
@Service
public class VipConsumeActionTotalService {

    @Autowired
    private VipService vipService;

    @Autowired
    private CustomerValueListService customerValueListService;

    public VipConsumeActionTotal getVipConsumeActionTotal(Vip vip, List<ResvOrder> resvOrders) {
        // todo 计算总体消费行为，并插入
        // 消费订单总次数
        VipConsumeActionTotal vipConsumeActionTotal = new VipConsumeActionTotal();
        vipConsumeActionTotal.setVipId(vip.getId());
        // 消费完成总订单数
        vipConsumeActionTotal.setTotalOrderNo(customerValueListService.getCustomerCount(resvOrders));
        // 消费完成总桌数
        vipConsumeActionTotal.setTotalTableNo(getCustomerTableCount(resvOrders));
        // 消费完成总人数
        vipConsumeActionTotal.setTotalPersonNo(getConsumerTotalCount(resvOrders));
        // 撤单桌数
        vipConsumeActionTotal.setCancelTableNo(getCancelOrderTable(resvOrders));
        // 消费总金额，单位：分
        vipConsumeActionTotal.setTotalConsumeAvg(getConsumerTotalAmount(resvOrders));
        // 桌均消费,单位:分
        vipConsumeActionTotal.setTableConsumeAvg(getConsumerTableAmount(resvOrders));
        // 人均消费,单位:分
        vipConsumeActionTotal.setPersonConsumeAvg(getConsumerPersonAmount(resvOrders));
        // 首次消费时间
        vipConsumeActionTotal.setFirstConsumeTime(getFirstConsumerTime(resvOrders));
        // 消费频次
        vipConsumeActionTotal.setConsumeFrequency(getConsumerFrequency(resvOrders));
        // 最近就餐时间
        vipConsumeActionTotal.setLastConsumeTime(getLastEatTime(resvOrders));
        vipConsumeActionTotal.setCreateUserId(CustomerValueConstants.DEFAULT_USER_ID);
        vipConsumeActionTotal.setCreateTime(LocalDateTime.now());
        vipConsumeActionTotal.setUpdateUserId(CustomerValueConstants.DEFAULT_USER_ID);
        vipConsumeActionTotal.setUpdateTime(LocalDateTime.now());

        return vipConsumeActionTotal;
    }

    /**
     * 获取消费完成总的桌数
     *
     * @param resvOrders 订单列表
     * @return 0-无
     */
    private Integer getCustomerTableCount(List<ResvOrder> resvOrders) {
        // 所有该客户已完成/入座的订单数量
        long count = resvOrders.stream()
                .filter(order -> "2".equals(order.getStatus()) || "3".equals(order.getStatus()))
                .count();
        return (int) count;
    }

    /**
     * 消费完成的总次数
     *
     * @param resvOrders 订单列表
     * @return 0-无
     */
    private Integer getConsumerTotalCount(List<ResvOrder> resvOrders) {
        // 按批次号，累加该客户的订单（只要一个批次号内有一个桌位入座/完成即可）。
        return resvOrders.stream()
                .filter(order -> "2".equals(order.getStatus()) || "3".equals(order.getStatus()))
                .collect(groupingBy(ResvOrder::getBatchNo))
                .size();
    }

    /**
     * 撤单桌数
     *
     * @param resvOrders 订单列表
     * @return 0-无
     */
    private Integer getCancelOrderTable(List<ResvOrder> resvOrders) {
        // 该客户所有退订的订单数量
        List<ResvOrder> cancelOrderList = resvOrders.stream()
                .filter(order -> "4".equals(order.getStatus()))
                .collect(toList());
        return cancelOrderList.size();
    }
    /**
     * 消费频次
     *
     * @param resvOrders 订单列表
     * @return 0-无
     */
    private Integer getConsumerFrequency(List<ResvOrder> resvOrders) {
        // 消费总次数/（当前月-首次消费月份）
        int customerSize = resvOrders.size();
        Optional<Date> date = resvOrders.stream()
                .filter(order -> "3".equals(order.getStatus()))
                .min((o1, o2) -> o1.getUpdatedAt().before(o2.getUpdatedAt()) ? 1 : -1)
                .map(ResvOrder::getUpdatedAt);
        Optional<Integer> optional = date.map(date1 -> {
            Instant instant = date1.toInstant();
            LocalDate firstDate = LocalDate.from(instant);
            Period period = Period.between(LocalDate.now(), firstDate);
            return period.getMonths();
        });
        Integer month = optional.orElse(1);
        // todo 这里涉及到精度的问题
        return customerSize / month;
    }

    /**
     * 首次消费时间
     *
     * @param resvOrders 订单列表
     * @return 2000-1-1 0：0：0 - 无
     */
    private LocalDateTime getFirstConsumerTime(List<ResvOrder> resvOrders) {
        int customerSize = resvOrders.size();
        Optional<Date> date = resvOrders.stream()
                .filter(order -> "3".equals(order.getStatus()))
                .min((o1, o2) -> o1.getUpdatedAt().before(o2.getUpdatedAt()) ? 1 : -1)
                .map(ResvOrder::getUpdatedAt);
        Optional<LocalDateTime> localDate = date.map(date1 -> {
            Instant instant = date1.toInstant();
            return LocalDateTime.from(instant);
        });
        return localDate.orElse(CustomerValueConstants.DEFAULT_FIRST_TIME);
    }

    /**
     * todo 计算逻辑再确认
     * 人均消费,单位:分
     *
     * @param resvOrders 订单列表
     * @return 0-无
     */
    private Integer getConsumerPersonAmount(List<ResvOrder> resvOrders) {
        // 总消费金额/对应批次号的订单实际人数之和。（如果订单没有实际人数，用预订人数代替）
        // 有实际金额的订单
        Stream<ResvOrder> resvOrderStream = resvOrders.stream()
                .filter(order -> {
                    String payamount = order.getPayamount();
                    if (StringUtils.isNotBlank(payamount)) {
                        return NumberUtils.isCreatable(payamount);
                    }
                    return false;
                });

        List<Integer> collect = resvOrderStream.map(order -> {
            int personCount = 1;
            String actualNum = order.getActualNum();
            if (StringUtils.isNotBlank(actualNum)) {
                try {
                    Integer.parseInt(actualNum);
                } catch (NumberFormatException e) {
                    personCount = 1;
                }
            } else {
                String resvNum = order.getResvNum();
                if (StringUtils.isNotBlank(resvNum)) {
                    try {
                        Integer.parseInt(resvNum);
                    } catch (NumberFormatException e) {
                        personCount = 1;
                    }
                }
            }
            return personCount;
        }).collect(toList());
        int orderCount = collect.size();

        long count = resvOrderStream
                .mapToInt(resvOrder -> {
                    String actualNum = resvOrder.getActualNum();
                    int actualNo = 1;
                    return eatPersonNo(resvOrder, actualNo, actualNum);
                })
                .count();
        // todo 有潜在 bug

        return 0;
    }

    private int eatPersonNo(ResvOrder order, int actualNo, String actualNum) {
        if (StringUtils.isNotBlank(actualNum)) {
            try {
                actualNo = Integer.parseInt(actualNum);
            } catch (NumberFormatException e) {
                String resvNum = order.getResvNum();
                if (StringUtils.isNotBlank(resvNum)) {
                    try {
                        actualNo = Integer.parseInt(resvNum);
                    } catch (NumberFormatException e1) {
                        actualNo = 1;
                    }
                }
            }
        }
        return actualNo;
    }

    /**
     * 桌均消费,单位:分
     *
     * @param resvOrders 订单列表
     * @return
     */
    private Integer getConsumerTableAmount(List<ResvOrder> resvOrders) {
        // 将该客户所有订单的消费金额累加。
        int sum = resvOrders.stream()
                .filter(order -> "3".equals(order.getStatus()))
                .map(ResvOrder::getPayamount)
                .mapToInt(payAmount -> {
                    if (payAmount.length() < 11) {
                        return Integer.parseInt(payAmount);
                    } else {
                        return 0;
                    }
                })
                .sum();

        return sum;
    }

    /**
     * 消费总金额，单位：分
     *
     * @param resvOrders 订单列表
     * @return
     */
    private Integer getConsumerTotalAmount(List<ResvOrder> resvOrders) {
        // 将该客户所有订单的消费金额累加
        int sum = resvOrders.stream()
                .filter(resvOrder -> "3".equals(resvOrder.getStatus()))
                .map(ResvOrder::getPayamount)
                .mapToInt(payAmount -> {
                    if (StringUtils.isBlank(payAmount)) {
                        return CustomerValueConstants.DEFAULT_PAYAMOUNT;
                    }
                    //暂时不处理不正常的数据
                    try {
                        double v = Double.parseDouble(payAmount);
                        return (int) (v * 100);
                    } catch (NumberFormatException e) {
                        log.error("订单金额转换异常-", e);
                        return CustomerValueConstants.DEFAULT_PAYAMOUNT;
                    }
                }).sum();
        return sum;
    }


    /**
     * 最近一次就餐时间
     * @param resvOrders 订单列表
     * @return 2000-1-1 0:0:0 - 无
     */
    private LocalDateTime getLastEatTime(List<ResvOrder> resvOrders) {
        // 最近一笔已入座/完成的订单距离系统的时间
        Optional<Date> min = resvOrders.stream()
                .filter(resvOrder -> "2".equals(resvOrder.getStatus()) || "3".equals(resvOrder.getStatus()))
                .map(ResvOrder::getUpdatedAt)
                .min((o1, o2) -> o1.before(o2) ? 1 : -1);
        Optional<LocalDateTime> optional = min.map(date -> LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
        return optional.orElse(CustomerValueConstants.DEFAULT_START_TIME);
    }

}
