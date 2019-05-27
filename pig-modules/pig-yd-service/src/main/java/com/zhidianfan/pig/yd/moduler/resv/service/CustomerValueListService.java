package com.zhidianfan.pig.yd.moduler.resv.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.service.IAppUserService;
import com.zhidianfan.pig.yd.moduler.resv.constants.CustomerValueConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * @author sjl
 * 2019-05-27 13:20
 */
@Slf4j
@Service
public class CustomerValueListService {

    @Autowired
    private VipService vipService;

    @Autowired
    private FirstValueConfigService firstValueConfigService;

    @Autowired
    private LossValueConfigService lossValueConfigService;

    @Autowired
    private IAppUserService appUserMapper;

    /**
     * 获取客户价值列表实体对象
     * @param vip vip 对象
     * @param resvOrders 订单列表
     * @return CustomerValueList
     */
    public CustomerValueList getCustomerValueList(Vip vip, List<ResvOrder> resvOrders) {
        Integer businessId = vip.getBusinessId();
        // 消费次数
        int customerCount = getCustomerCount(resvOrders);
        // 总消费金额
        double customerAmount = getCustomerAmount(resvOrders);
        int customerAmountInt = (int) (customerAmount * 100);
        // 人均消费金额
        int personAvg = getPersonAvg(resvOrders);
        // 最近就餐时间
        LocalDateTime lastEatTime = getLastEatTime(resvOrders);
        // 一级价值，意向客户、活跃客户、沉睡客户、流失客户
        int firstValue = getFirstValue(resvOrders, businessId);
        // 细分价值
        String lossValue = getLossValue(resvOrders, businessId);
        // 自定义分类
        // todo 使用之前的分类
        String customerClass = getCustomerClass(resvOrders, businessId);
        Integer appUserId = vip.getAppUserId();
        Optional<Integer> optionalAppUserId = Optional.ofNullable(appUserId);
        String appUserName = getAppUserName(appUserId);

        // todo 列表页面
        CustomerValueList customerValueList = new CustomerValueList();
        customerValueList.setVipId(vip.getId());
        customerValueList.setVipName(getVipName(vip));
        customerValueList.setVipSex(getVipSex(vip));
        customerValueList.setVipPhone(getVipPhone(vip));
        customerValueList.setVipAge(vipService.getAge(vip));
        customerValueList.setVipCompany(getVipCompany(vip));
        customerValueList.setAppUserId(optionalAppUserId.orElse(-1));
        customerValueList.setAppUserName(appUserName);
        customerValueList.setHotelId(businessId);
        customerValueList.setCustomerCount(customerCount);
        customerValueList.setCustomerAmountTotal(customerAmountInt);
        customerValueList.setCustomerAmountAvg(personAvg);
        customerValueList.setLastEatTime(lastEatTime);
        customerValueList.setFirstClassValue(firstValue);
        customerValueList.setSubValue(lossValue);
        customerValueList.setCustomerClass(customerClass);
        customerValueList.setCreateTime(LocalDateTime.now());
        customerValueList.setUpdateTime(LocalDateTime.now());

        return customerValueList;
    }

    /**
     * 获取手机号
     * @param vip vip 信息
     * @return "" - 无
     */
    private String getVipPhone(Vip vip) {
        String vipPhone = vip.getVipPhone();
        if (StringUtils.isNotBlank(vipPhone)) {
            return StringUtils.EMPTY;
        }
        return vipPhone;
    }

    /**
     * 获取用户名称
     * @param vip vip 信息
     * @return "" - 无
     */
    private String getVipName(Vip vip) {
        String vipName = vip.getVipName();
        if (StringUtils.isBlank(vipName)) {
            return StringUtils.EMPTY;
        }
        return vipName;
    }

    /**
     * 获取 vip 性别，男 - 先生，女 - 女士
     * @param vip vip 信息
     * @return "" - 无性别
     */
    private String getVipSex(Vip vip) {
        String vipSex = vip.getVipSex();
        if (StringUtils.isBlank(vipSex)) {
            return StringUtils.EMPTY;
        }
        if ("男".equals(vipSex)) {
            return   "先生";
        }
        if ("女".equals(vipSex)) {
            return   "女士";
        }
        return vipSex;
    }

    private String getVipCompany(Vip vip) {
        String vipCompany = vip.getVipCompany();
        if (vipCompany == null) {
            vipCompany = StringUtils.EMPTY;
        }
        return vipCompany;
    }

    /**
     * 消费次数
     * @param resvOrders 订单列表
     * @return 0-无
     */
    public int getCustomerCount(List<ResvOrder> resvOrders) {
        List<ResvOrder> collect = resvOrders.stream()
                .collect(collectingAndThen(toCollection(
                        () -> new TreeSet<>(comparing(ResvOrder::getBatchNo))), ArrayList::new)
                );
        return collect.size();
    }


    /**
     * 消费总金额
     *
     * @param resvOrders 订单列表
     * @return 消费总金额，单位:分
     */
    private int getCustomerAmount(List<ResvOrder> resvOrders) {
        String payStatus = "3";
        return resvOrders.stream()
                .filter((order) -> order.getStatus().equals(payStatus))
                .map(ResvOrder::getPayamount)
                .filter(payAmount -> StringUtils.isNotBlank(payAmount) && payAmount.length() < 11)
                .mapToInt(payAmount -> {
                    Optional<String> optionalPayAmount = Optional.of(payAmount);
                    return Integer.valueOf(optionalPayAmount.orElse("0")) * 100;
                })
                .sum();
    }

    /**
     * 人均消费金额
     *
     * @param resvOrders 用户id
     * @return 人均消费金额，单元:分
     */
    private int getPersonAvg(List<ResvOrder> resvOrders) {
        OptionalDouble optAverage = resvOrders.stream()
                .filter(order -> "3".equals(order.getStatus()))
                .map(ResvOrder::getPayamount)
                .mapToDouble(payAmount -> {
                    Optional<String> optionalPayAmount = Optional.ofNullable(payAmount);
                    return Integer.valueOf(optionalPayAmount.orElse("0"));
                })
                .average();
        double v = optAverage.orElse(CustomerValueConstants.DEFAULT_CUSTOMER_AVG);
        return (int) v * 100;
    }


    /**
     *  最后一次就餐时间
     * @param resvOrders 订单列表
     * @return 2000-1-1 0:0:0 表示坎
     */
    private LocalDateTime getLastEatTime(List<ResvOrder> resvOrders) {
        Optional<LocalDateTime> optionalLocalDateTime = resvOrders.stream()
                .map(ResvOrder::getCreatedAt)
                .max((o1, o2) -> {
                    Instant one = o1.toInstant();
                    Instant two = o2.toInstant();
                    boolean after = one.isAfter(two);
                    return after ? 1 : -1;
                })
                .map(date -> {
                    Instant instant = date.toInstant();
                    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                });
        return optionalLocalDateTime.orElse(CustomerValueConstants.DEFAULT_START_TIME);
    }

    /**
     * 用户一级价值
     *
     * @param resvOrders   订单列表
     * @param hotelId 酒店 id
     * @return 0-未配置,1-意向客户，2-活跃客户，3-沉睡客户，4-流失客户
     */
    private int getFirstValue(List<ResvOrder> resvOrders, Integer hotelId) {
        FirstValueConfig config = firstValueConfigService.getConfig(hotelId);
        if (config == null) {
            return 0;
        }
        Integer startValue = config.getStartValue();
        Integer endValue = config.getEndValue();
        if (resvOrders.isEmpty()) {
            //todo 意向客户
            return CustomerValueConstants.INTENTION_CUSTOMER;
        }
        List<LocalDateTime> activeCustomerList = resvOrders.stream()
                .map(ResvOrder::getResvDate)
                .map(date -> {
                    Instant instant = date.toInstant();
                    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                })
                .filter(localDateTime -> {
                    Period period = Period.between(localDateTime.toLocalDate(), LocalDate.now());
                    // todo 此处查数据库配置获取
                    return period.getDays() <= startValue;
                })
                .collect(Collectors.toList());
        if (!activeCustomerList.isEmpty()) {
            // todo 30 天内有消费行为的，为活跃客户
            return CustomerValueConstants.ACTIVE_CUSTOMER;
        }

        List<LocalDateTime> sleepCustomerList = resvOrders.stream()
                .map(ResvOrder::getResvDate)
                .map(date -> {
                    Instant instant = date.toInstant();
                    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                })
                .filter(localDateTime -> {
                    Period period = Period.between(localDateTime.toLocalDate(), LocalDate.now());
                    return period.getDays() > startValue && period.getDays() <= endValue;
                })
                .collect(Collectors.toList());
        if (!sleepCustomerList.isEmpty()) {
            // todo 沉睡客户
            return CustomerValueConstants.SLEEP_CUSTOMER;
        }

        // todo 流失客户
        return CustomerValueConstants.LOSS_CUSTOMER;
    }


    /**
     * 细分价值
     *
     * @param resvOrders   用户id
     * @param hotelId 酒店id
     * @return 1-4 的取值
     */
    private String getLossValue(List<ResvOrder> resvOrders, Integer hotelId) {
        List<LossValueConfig> lossValueConfigList = lossValueConfigService.getLossValueConfig(hotelId);
        lossValueConfigList.sort(((o1, o2) -> o1.getSort() > o2.getSort() ? 1 : -1));

        double personAvg = getPersonAvg(resvOrders);
        double customerAmount = getCustomerAmount(resvOrders);
        int customerCount = getCustomerCount(resvOrders);
        for (LossValueConfig lossValueConfig : lossValueConfigList) {
            String valueName = lossValueConfig.getValueName();
            Integer customerPersonAvgStart = lossValueConfig.getCustomerPersonAvgStart();
            Integer customerPersonAvgEnd = lossValueConfig.getCustomerPersonAvgEnd();
            Integer customerTotalStart = lossValueConfig.getCustomerTotalStart();
            Integer customerTotalEnd = lossValueConfig.getCustomerTotalEnd();
            Integer customerCountStart = lossValueConfig.getCustomerCountStart();
            Integer customerCountEnd = lossValueConfig.getCustomerCountEnd();

            if (personAvg >= customerPersonAvgStart && personAvg <= customerPersonAvgEnd
                    && customerAmount >= customerTotalStart && customerAmount <= customerTotalEnd
                    && customerCount >= customerCountStart && customerCount <= customerCountEnd) {
                return valueName;
            }
        }
        return StringUtils.EMPTY;
    }


    /**
     * 获取营销经理名称
     *
     * @param appUserId 营销经理 id
     * @return 营销经理名称
     */
    private String getAppUserName(Integer appUserId) {
        AppUser appUser = appUserMapper.selectById(appUserId);
        if (appUser == null) {
            return StringUtils.EMPTY;
        }
        if (appUserId == null || appUserId == -1) {
            return StringUtils.EMPTY;
        }
        return appUser.getAppUserName();
    }

    /**
     * 自定义类别
     * @param resvOrders 订单列表
     * @param businessId 酒店列表
     * @return 自定义类别的名称
     */
    private String getCustomerClass(List<ResvOrder> resvOrders, Integer businessId) {
        return StringUtils.EMPTY;
    }
}
