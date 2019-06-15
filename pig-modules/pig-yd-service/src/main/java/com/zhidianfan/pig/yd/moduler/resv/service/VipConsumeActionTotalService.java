package com.zhidianfan.pig.yd.moduler.resv.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrder;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Vip;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.VipConsumeActionTotal;
import com.zhidianfan.pig.yd.moduler.common.service.IVipConsumeActionTotalService;
import com.zhidianfan.pig.yd.moduler.resv.constants.CustomerValueConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.runtime.parser.node.MathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
    private IVipConsumeActionTotalService vipConsumeActionTotalMapper;

    @Autowired
    private CustomerValueListService customerValueListService;

    public Map<Integer,VipConsumeActionTotal> getVipConsumeActionTotal(List<Vip> vips, Map<Integer,List<ResvOrder>> resvOrdersMap) {

        Map<Integer,VipConsumeActionTotal> map = new HashMap<>();
        for (Vip vip:vips){
            List<ResvOrder> resvOrders = resvOrdersMap.get(vip.getId());
            // 消费订单总次数
            VipConsumeActionTotal vipConsumeActionTotal = new VipConsumeActionTotal();
            vipConsumeActionTotal.setVipId(vip.getId());
            // 消费完成总订单数
            vipConsumeActionTotal.setTotalOrderNo(customerValueListService.getCustomerCount(resvOrders));
            // 消费完成总桌数
            vipConsumeActionTotal.setTotalTableNo(getCustomerTableCount(resvOrders));
            // 消费完成总人数
            vipConsumeActionTotal.setTotalPersonNo(getCustomerPersonCount(resvOrders));
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

            map.put(vip.getId(),vipConsumeActionTotal);
        }



        return map;
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
     * 消费总人数
     * @param resvOrders 订单列表
     * @return 所有该客户已完成/入座的订单中的实际人数之和。（如果没有实际人数，使用就餐人数）
     */
    public Integer getCustomerPersonCount(List<ResvOrder> resvOrders) {
        // 所有该客户已完成/入座的订单中的实际人数之和。（如果没有实际人数，使用就餐人数）
        OptionalInt optionalPersonCount = resvOrders.stream()
                .filter(order -> "2".equals(order.getStatus()) || "3".equals(order.getStatus()))
                .mapToInt(order -> {
                    String actualNum = order.getActualNum();
                    int personCount = 0;
                    try {
                        personCount = Integer.parseInt(actualNum);
                    } catch (NumberFormatException ignored) {
                    }
                    if (personCount <= 0) {
                        String resvNum = order.getResvNum();
                        try {
                            personCount = Integer.parseInt(resvNum);
                        } catch (NumberFormatException e) {
                            personCount = 0;
                        }
                    }
                    return personCount;
                })
                .reduce((a, b) -> a + b);

        return optionalPersonCount.orElse(0);
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
     * 消费频次 = 消费总次数/（当前月-首次消费月份）
     *
     * @param resvOrders 订单列表
     * @return 0-无
     */
    private Float getConsumerFrequency(List<ResvOrder> resvOrders) {
        int customerSize = customerValueListService.getCustomerCount(resvOrders);

        Optional<Date> date = getFirstCustomerMonth(resvOrders);
        Optional<Integer> optional = date.map(date1 -> {
            Instant instant = date1.toInstant();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            LocalDate firstDate = localDateTime.toLocalDate();
            long month = firstDate.until(LocalDate.now(), ChronoUnit.MONTHS);
            return (int)month;
        });
        Integer month = optional.orElse(1);
        month = Math.max(month, 1);
        Number divide = MathUtils.divide(customerSize, month);

        BigDecimal bigDecimal = new BigDecimal(divide.doubleValue()).setScale(1, RoundingMode.HALF_UP);
        return bigDecimal.floatValue();
    }

    private Optional<Date> getFirstCustomerMonth(List<ResvOrder> resvOrders) {
        return resvOrders.stream()
                .filter(order -> "3".equals(order.getStatus()))
                .min(Comparator.comparing(ResvOrder::getUpdatedAt))
                .map(ResvOrder::getUpdatedAt);
    }

    /**
     * 首次消费时间, 客户第一笔入座/消费的日期
     *
     * @param resvOrders 订单列表
     * @return 2000-1-1 0：0：0 - 无
     */
    private LocalDateTime getFirstConsumerTime(List<ResvOrder> resvOrders) {
        Optional<Date> min = resvOrders.stream()
                .filter(resvOrder -> "2".equals(resvOrder.getStatus()) || "3".equals(resvOrder.getStatus()))
                .map(ResvOrder::getUpdatedAt)
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder());
        Optional<LocalDateTime> optional = min.map(date -> LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
        return optional.orElse(CustomerValueConstants.DEFAULT_START_TIME);
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
        return customerValueListService.getPersonAvg(resvOrders);
    }

    /**
     * 桌均消费,单位:分
     *
     * @param resvOrders 订单列表
     * @return
     */
    private Integer getConsumerTableAmount(List<ResvOrder> resvOrders) {
        // 总消费金额
        Integer consumerTotalAmount = getConsumerTotalAmount(resvOrders);
        // 桌数
        Integer customerCount = getCustomerTableCount(resvOrders);
        customerCount = Math.max(customerCount, 1);
        Number divide = MathUtils.divide(consumerTotalAmount, customerCount);
        return Math.round(divide.floatValue());
    }

    /**
     * 消费总金额，单位：分
     *
     * @param resvOrders 订单列表
     * @return
     */
    public Integer getConsumerTotalAmount(List<ResvOrder> resvOrders) {
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
                .map(ResvOrder::getResvDate)
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder());
        Optional<LocalDateTime> optional = min.map(date -> LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
        return optional.orElse(CustomerValueConstants.DEFAULT_START_TIME);
    }

    /**
     * 撤单桌数
     * @param vipId vip 表主键
     * @param value 桌数，Integer 类型
     */
    public void updateCancelTableNo(Integer vipId, String value) {
        int cancelTableNo = Integer.parseInt(value);
        VipConsumeActionTotal total = new VipConsumeActionTotal();
        total.setVipId(vipId);
        total.setCancelTableNo(cancelTableNo);
        total.setUpdateTime(LocalDateTime.now());
        vipConsumeActionTotalMapper.updateById(total);
    }

    /**
     * 首次消费时间
     * @param vipId 主键
     * @param value 首次消费时间，yyy-MM-dd HH:mm:ss
     */
    public void updateFirstConsumeTime(Integer vipId, String value) {
        // todo 逻辑修改，客户结账时角发该操作，然后在重新查询订单表
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(value, formatter);
        VipConsumeActionTotal total = new VipConsumeActionTotal();
        total.setVipId(vipId);
        total.setFirstConsumeTime(dateTime);
        total.setUpdateTime(LocalDateTime.now());
        vipConsumeActionTotalMapper.updateById(total);
    }
}
