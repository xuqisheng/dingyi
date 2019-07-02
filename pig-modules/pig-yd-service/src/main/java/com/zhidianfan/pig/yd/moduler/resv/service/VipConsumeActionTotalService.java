package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.MasterCustomerVipMapping;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrder;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Vip;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.VipConsumeActionTotal;
import com.zhidianfan.pig.yd.moduler.common.service.IVipConsumeActionTotalService;
import com.zhidianfan.pig.yd.moduler.resv.constants.CustomerValueConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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

    @Autowired
    private CustomerRecordService customerRecordService;

    public Map<Integer, VipConsumeActionTotal> getVipConsumeActionTotal(List<Vip> vips, Map<Integer, List<ResvOrder>> resvOrdersMap, List<MasterCustomerVipMapping> masterCustomerVipMappingList) {

        Map<Integer, VipConsumeActionTotal> map = new HashMap<>();
        for (Vip vip : vips) {
            try {
                // 主客订单
                List<ResvOrder> resvOrderList = new ArrayList<>();
                List<ResvOrder> manOrderList = customerRecordService.getManOrderList(vip, masterCustomerVipMappingList, resvOrdersMap);
                List<ResvOrder> resvOrders = resvOrdersMap.get(vip.getId());
                if (CollectionUtils.isNotEmpty(resvOrders)) {
                    resvOrderList.addAll(resvOrders);
                }
                if (CollectionUtils.isNotEmpty(manOrderList)) {
                    boolean manOrder = customerValueListService.isManOrder(vip, masterCustomerVipMappingList);
                    if (manOrder) {
                        resvOrderList.addAll(manOrderList);
                    } else {
                        resvOrderList.removeAll(manOrderList);
                    }
                }
                // 消费订单总次数
                VipConsumeActionTotal vipConsumeActionTotal = new VipConsumeActionTotal();
                vipConsumeActionTotal.setVipId(vip.getId());
                // 消费完成总订单数
                vipConsumeActionTotal.setTotalOrderNo(customerValueListService.getCustomerCount(resvOrderList, vip));
                // 消费完成总桌数
                vipConsumeActionTotal.setTotalTableNo(getCustomerTableCount(resvOrderList));
                // 消费完成总人数
                vipConsumeActionTotal.setTotalPersonNo(getCustomerPersonCount(resvOrderList));
                // 撤单桌数
                vipConsumeActionTotal.setCancelTableNo(getCancelOrderTable(resvOrderList));
                // 消费总金额，单位：分
                vipConsumeActionTotal.setTotalConsumeAvg(getConsumerTotalAmount(resvOrderList));
                // 桌均消费,单位:分
                vipConsumeActionTotal.setTableConsumeAvg(getConsumerTableAmount(resvOrderList));
                // 人均消费,单位:分
                vipConsumeActionTotal.setPersonConsumeAvg(getConsumerPersonAmount(resvOrderList, vip));
                // 首次消费时间
                vipConsumeActionTotal.setFirstConsumeTime(getFirstConsumerTime(resvOrderList));
                // 消费频次
                vipConsumeActionTotal.setConsumeFrequency(getConsumerFrequency(resvOrderList, vip));
                // 最近就餐时间
                vipConsumeActionTotal.setLastConsumeTime(getLastEatTime(resvOrderList));
                vipConsumeActionTotal.setCreateUserId(CustomerValueConstants.DEFAULT_USER_ID);
                vipConsumeActionTotal.setCreateTime(LocalDateTime.now());
                vipConsumeActionTotal.setUpdateUserId(CustomerValueConstants.DEFAULT_USER_ID);
                vipConsumeActionTotal.setUpdateTime(LocalDateTime.now());

                map.put(vip.getId(), vipConsumeActionTotal);
            }catch (Exception e){
                log.error(e.getMessage(),e);
            }

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
        if (CollectionUtils.isEmpty(resvOrders)) {
            return 0;
        }
        // 所有该客户已完成/入座的订单数量
        long count = resvOrders.stream()
                .filter(order -> "2".equals(order.getStatus()) || "3".equals(order.getStatus()))
                .count();
        return (int) count;
    }

    /**
     * 消费总人数
     *
     * @param resvOrders 订单列表
     * @return 所有该客户已完成/入座的订单中的实际人数之和。（如果没有实际人数，使用就餐人数）
     */
    public Integer getCustomerPersonCount(List<ResvOrder> resvOrders) {
        if (CollectionUtils.isEmpty(resvOrders)) {
            return 0;
        }
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
        if (CollectionUtils.isEmpty(resvOrders)) {
            return 0;
        }
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
    private Float getConsumerFrequency(List<ResvOrder> resvOrders, Vip vip) {
        if (CollectionUtils.isEmpty(resvOrders)) {
            return 0.0f;
        }
        int customerSize = customerValueListService.getCustomerCount(resvOrders, vip);

        Optional<Date> date = getFirstCustomerMonth(resvOrders);
        Optional<Integer> optional = date.map(date1 -> {
            Instant instant = date1.toInstant();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            LocalDate firstDate = localDateTime.toLocalDate();
            long month = firstDate.until(LocalDate.now(), ChronoUnit.MONTHS);
            return (int) month;
        });
        Integer month = optional.orElse(1);
        month = Math.max(month, 1);
        Number divide = MathUtils.divide(customerSize, (float)month);
        BigDecimal bigDecimal = new BigDecimal(divide.doubleValue()).setScale(1, RoundingMode.HALF_UP);
        return bigDecimal.floatValue();
    }

    private Optional<Date> getFirstCustomerMonth(List<ResvOrder> resvOrders) {
        return resvOrders.stream()
                .filter(Objects::nonNull)
                .filter(order -> "3".equals(order.getStatus()))
                .filter(order -> Objects.nonNull(order.getUpdatedAt()))
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
        if (CollectionUtils.isEmpty(resvOrders)) {
            return CustomerValueConstants.DEFAULT_START_TIME;
        }
        Optional<Date> min = resvOrders.stream()
                .filter(resvOrder -> "2".equals(resvOrder.getStatus()) || "3".equals(resvOrder.getStatus()))
                .map(ResvOrder::getUpdatedAt)
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder());
        Optional<LocalDateTime> optional = min.map(date -> LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
        return optional.orElse(CustomerValueConstants.DEFAULT_START_TIME);
    }

    /**
     * 人均消费,单位:分
     *
     * @param resvOrders 订单列表
     * @return 0-无
     */
    private Integer getConsumerPersonAmount(List<ResvOrder> resvOrders, Vip vip) {
        if (CollectionUtils.isEmpty(resvOrders)) {
            return 0;
        }
        // 总消费金额/对应批次号的订单实际人数之和。（如果订单没有实际人数，用预订人数代替）
        // 有实际金额的订单
        return customerValueListService.getPersonAvg(resvOrders, vip);
    }

    /**
     * 桌均消费,单位:分
     *
     * @param resvOrders 订单列表
     * @return
     */
    private Integer getConsumerTableAmount(List<ResvOrder> resvOrders) {
        if (CollectionUtils.isEmpty(resvOrders)) {
            return 0;
        }
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
        if (CollectionUtils.isEmpty(resvOrders)) {
            return 0;
        }
        // 将该客户所有订单的消费金额累加
        int sum = resvOrders.stream()
                .filter(resvOrder -> "2".equals(resvOrder.getStatus()) || "3".equals(resvOrder.getStatus()))
                .map(ResvOrder::getPayamount)
                .mapToInt(payAmount -> {
                    if (StringUtils.isBlank(payAmount) || !NumberUtils.isCreatable(payAmount)) {
                        return CustomerValueConstants.DEFAULT_PAYAMOUNT;
                    }
                    try {
                        float v = Float.parseFloat(payAmount);
                        Number multiply = MathUtils.multiply(v, 100);
                        return Math.round(multiply.floatValue());
                    } catch (NumberFormatException e) {
                        log.error("订单金额转换异常,数据内容为：{}", payAmount);
                        return CustomerValueConstants.DEFAULT_PAYAMOUNT;
                    }
                }).sum();
        return sum;
    }


    /**
     * 最近一次就餐时间
     *
     * @param resvOrders 订单列表
     * @return 2000-1-1 0:0:0 - 无
     */
    private LocalDateTime getLastEatTime(List<ResvOrder> resvOrders) {
        if (CollectionUtils.isEmpty(resvOrders)) {
            return CustomerValueConstants.DEFAULT_START_TIME;
        }
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
     *
     * @param vipId vip 表主键
     * @param value 桌数，Integer 类型
     */
    public void updateCancelTableNo(Integer vipId, String value) {
        int cancelTableNo = Integer.parseInt(value);
        Wrapper<VipConsumeActionTotal> wrapper = new EntityWrapper<>();
        wrapper.eq("vip_id", vipId);

        VipConsumeActionTotal vipConsumeActionTotal = vipConsumeActionTotalMapper.selectOne(wrapper);
        Integer oldCancelTableNo = 0;
        if (vipConsumeActionTotal != null) {
            oldCancelTableNo = vipConsumeActionTotal.getCancelTableNo();
        }
        log.debug("原来的撤单桌数为:[{}], 现在的撤单桌数为:[{}]", oldCancelTableNo, cancelTableNo + oldCancelTableNo);
        VipConsumeActionTotal total = new VipConsumeActionTotal();
        total.setTotalOrderNo(0);
        total.setTotalTableNo(0);
        total.setTotalPersonNo(0);
        total.setTotalConsumeAvg(0);
        total.setTableConsumeAvg(0);
        total.setPersonConsumeAvg(0);
        total.setFirstConsumeTime(CustomerValueConstants.DEFAULT_FIRST_TIME);
        total.setConsumeFrequency(0.0F);
        total.setLastConsumeTime(LocalDateTime.now());
        total.setCreateUserId(0L);
        total.setCreateTime(LocalDateTime.now());
        total.setUpdateUserId(0L);

        total.setVipId(vipId);
        total.setCancelTableNo(cancelTableNo + oldCancelTableNo);
        total.setUpdateTime(LocalDateTime.now());

        vipConsumeActionTotalMapper.insertOrUpdate(total);
    }

    /**
     * 首次消费时间
     *
     * @param vipId 主键
     * @param value 首次消费时间，yyy-MM-dd HH:mm:ss
     */
    public void updateFirstConsumeTime(Integer vipId, String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(value, formatter);
        VipConsumeActionTotal total = new VipConsumeActionTotal();
        total.setTotalOrderNo(0);
        total.setTotalTableNo(0);
        total.setTotalPersonNo(0);
        total.setCancelTableNo(0);
        total.setTotalConsumeAvg(0);
        total.setTableConsumeAvg(0);
        total.setPersonConsumeAvg(0);
        total.setConsumeFrequency(0.0F);
        total.setLastConsumeTime(CustomerValueConstants.DEFAULT_END_TIME);
        total.setCreateUserId(100000L);
        total.setCreateTime(LocalDateTime.now());
        total.setUpdateUserId(100000L);

        total.setVipId(vipId);
        total.setFirstConsumeTime(dateTime);
        total.setUpdateTime(LocalDateTime.now());
        vipConsumeActionTotalMapper.insertOrUpdate(total);
    }
}
