package com.zhidianfan.pig.yd.moduler.resv.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.service.IAppUserService;
import com.zhidianfan.pig.yd.moduler.resv.constants.CustomerValueConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.runtime.parser.node.MathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private ConfigHotelService configHotelService;

    @Autowired
    private LossValueConfigService lossValueConfigService;

    @Autowired
    private IAppUserService appUserMapper;

    @Autowired
    private VipConsumeActionTotalService vipConsumeActionTotalService;

    /**
     * 获取客户价值列表实体对象
     *
     * @param vips          vip 对象
     * @param resvOrdersMap 订单列表
     * @return CustomerValueList
     */
    public Map<Integer, CustomerValueList> getCustomerValueList(List<Vip> vips, Map<Integer, List<ResvOrder>> resvOrdersMap) {
        Map<Integer, CustomerValueList> map = new HashMap<>();
        for (Vip vip : vips) {
            List<ResvOrder> resvOrders = resvOrdersMap.get(vip.getId());
            Integer businessId = vip.getBusinessId();
            // 消费次数
            int customerCount = getCustomerCount(resvOrders);
            // 总消费金额
            double customerAmount = getCustomerAmount(resvOrders);
            int customerAmountInt = (int) Math.round(customerAmount * 100);
            // 人均消费金额
            int personAvg = getPersonAvg(resvOrders);
            // 最近就餐时间
            LocalDateTime lastEatTime = getLastEatTime(resvOrders);
            // 一级价值，意向客户、活跃客户、沉睡客户、流失客户
            int firstValue = getFirstValue(resvOrders, businessId);
            // 细分价值
            Long lossValue = getLossValue(resvOrders, businessId);
            // 自定义分类
            String customerClass = vip.getVipClassName();
            customerClass = Optional.ofNullable(customerClass).orElse(StringUtils.EMPTY);

            Integer appUserId = vip.getAppUserId();
            Optional<Integer> optionalAppUserId = Optional.ofNullable(appUserId);
            String appUserName = getAppUserName(appUserId);

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

            map.put(vip.getId(), customerValueList);
        }


        return map;
    }

    /**
     * 获取手机号
     *
     * @param vip vip 信息
     * @return "" - 无
     */
    private String getVipPhone(Vip vip) {
        String vipPhone = vip.getVipPhone();
        if (StringUtils.isBlank(vipPhone)) {
            return StringUtils.EMPTY;
        }
        return vipPhone;
    }

    /**
     * 获取用户名称
     *
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
     *
     * @param vip vip 信息
     * @return "" - 无性别
     */
    private String getVipSex(Vip vip) {
        String vipSex = vip.getVipSex();
        if (StringUtils.isBlank(vipSex)) {
            return StringUtils.EMPTY;
        }
        if ("男".equals(vipSex)) {
            return "先生";
        }
        if ("女".equals(vipSex)) {
            return "女士";
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
     *
     * @param resvOrders 订单列表
     * @return 0-无
     */
    public int getCustomerCount(List<ResvOrder> resvOrders) {
        if (CollectionUtils.isEmpty(resvOrders)) {
            return 0;
        }
        List<ResvOrder> collect = resvOrders.stream()
                .filter(order -> "2".equals(order.getStatus()) || "3".equals(order.getStatus()))
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
        if (CollectionUtils.isEmpty(resvOrders)){
            return 0;
        }
        return resvOrders.stream()
                .filter((order) -> "3".equals(order.getStatus()))
                .map(ResvOrder::getPayamount)
                .filter(payAmount -> StringUtils.isNotBlank(payAmount) && payAmount.length() < 11)
                .mapToInt(payAmount -> {
                    Optional<String> optionalPayAmount = Optional.of(payAmount);
                    double v = Double.valueOf(optionalPayAmount.orElse("0")) * 100;
                    return (int) v;
                })
                .sum();
    }

    /**
     * 人均消费金额
     *
     * @param resvOrders 用户id
     * @return 人均消费金额，单元:分
     */
    public int getPersonAvg(List<ResvOrder> resvOrders) {
        if (CollectionUtils.isEmpty(resvOrders)){
            return 0;
        }
        //消费总金额
        Integer consumerTotalAmount = vipConsumeActionTotalService.getConsumerTotalAmount(resvOrders);
        // 消费人数
        Integer customerPersonCount = vipConsumeActionTotalService.getCustomerPersonCount(resvOrders);
        customerPersonCount = Math.max(customerPersonCount, 1);
        Number personCountNumber = MathUtils.divide(consumerTotalAmount, customerPersonCount);
        return Math.round(personCountNumber.floatValue());
    }


    /**
     * 最后一次就餐时间
     *
     * @param resvOrders 订单列表
     * @return 2000-1-1 0:0:0 表示坎
     */
    private LocalDateTime getLastEatTime(List<ResvOrder> resvOrders) {
        if (CollectionUtils.isEmpty(resvOrders)){
            return LocalDateTime.of(2000,1,1,0,0,0);
        }
        // 最近一笔已入座/完成的订单
        Optional<LocalDateTime> optionalLocalDateTime = resvOrders.stream()
                .filter(order -> "2".equals(order.getStatus()) || "3".equals(order.getStatus()))
                .map(ResvOrder::getResvDate)
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .map(date -> {
                    Instant instant = date.toInstant();
                    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                });
        return optionalLocalDateTime.orElse(CustomerValueConstants.DEFAULT_START_TIME);
    }

    /**
     * 沉睡与唤醒之间
     */
    private static final String ACTIVE_SLEEP_BETWEEN_K = "active_sleep_between";
    /**
     * 沉睡与流失之间
     */
    private static final String SLEEP_LOSS_BETWEEN_K = "sleep_loss_between";
    /**
     * 细分价值中的时间周期
     */
    private static final String VALUE_CATEGORY_CYCLE_K = "value_category_cycle";
    /**
     * 黑名单
     */
    private static final String BLACK_LIST_ORDER_NUM_K = "black_list_order_num";


    /**
     * 用户一级价值
     *
     * @param resvOrders 订单列表
     * @param hotelId    酒店 id
     * @return 0-未配置,1-意向客户，2-活跃客户，3-沉睡客户，4-流失客户
     */
    private int getFirstValue(List<ResvOrder> resvOrders, Integer hotelId) {
        /* 意向客户 未消费客户
         * 活跃客户 30天内，有消费的客户
         * 沉睡客户 30天内未消费，且在30-60天内有消费行为
         * 流失客户 60天内未消费，但有历史消费行为
         */

        // 获取每个酒店自己的配置
        Map<String, String> hotelConfig = configHotelService.getConfig(hotelId);
//        FirstValueConfig config = (FirstValueConfig) hotelConfig;
        if (hotelConfig == null) {
            log.error("hotelId:{}, 查询不到一级价值的配置-----", hotelId);
            return -1;
        }
        String activeSleepBetween = hotelConfig.get(ACTIVE_SLEEP_BETWEEN_K);
        String sleepLossBetween = hotelConfig.get(SLEEP_LOSS_BETWEEN_K);
        int activeSleep;
        int sleepLoss;
        try {
            activeSleep = Integer.parseInt(activeSleepBetween);
            sleepLoss = Integer.parseInt(sleepLossBetween);
        } catch (NumberFormatException e) {
            log.error("-------配置出错，请检查配置 businessId:[{}]-------------", hotelId);
            return -1;
        }
        if (CollectionUtils.isEmpty(resvOrders)) {
            //意向客户 未消费客户
            return CustomerValueConstants.INTENTION_CUSTOMER;
        }
        List<LocalDateTime> activeCustomerList = getPayTimeStream(resvOrders)
                .filter(localDateTime -> {
                    Duration duration = Duration.between(localDateTime, LocalDateTime.now());
                    return duration.toDays() <= activeSleep;
                })
                .collect(Collectors.toList());
        if (!activeCustomerList.isEmpty()) {
            return CustomerValueConstants.ACTIVE_CUSTOMER;
        }

        List<LocalDateTime> sleepCustomerList = getPayTimeStream(resvOrders)
                .filter(localDateTime -> {
                    Duration duration = Duration.between(localDateTime, LocalDateTime.now());
                    return duration.toDays() > activeSleep && duration.toDays() <= sleepLoss;
                })
                .collect(Collectors.toList());
        if (!sleepCustomerList.isEmpty()) {
            return CustomerValueConstants.SLEEP_CUSTOMER;
        }

        return CustomerValueConstants.LOSS_CUSTOMER;
    }

    private Stream<LocalDateTime> getPayTimeStream(List<ResvOrder> resvOrders) {
        return resvOrders.stream()
                .filter(order -> "2".equals(order.getStatus()) || "3".equals(order.getStatus()))
                .map(ResvOrder::getUpdatedAt)
                .filter(Objects::nonNull)
                .map(date -> {
                    Instant instant = date.toInstant();
                    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                });
    }


    /**
     * 细分价值
     *
     * @param resvOrders 用户id
     * @param hotelId    酒店id
     * @return 1-4 的取值
     */
    private Long getLossValue(List<ResvOrder> resvOrders, Integer hotelId) {
        List<LossValueConfig> lossValueConfigList = lossValueConfigService.getLossValueConfig(hotelId);
        // 排序小的在前面
        lossValueConfigList.sort(Comparator.comparing(LossValueConfig::getSort));

        // 单位：分
        int personAvg = getPersonAvg(resvOrders);
        double customerAmount = getCustomerAmount(resvOrders);
        int customerCount = getCustomerCount(resvOrders);
        for (LossValueConfig lossValueConfig : lossValueConfigList) {
            String valueName = lossValueConfig.getValueName();
            Long id = lossValueConfig.getId();
            // 单位:分
            Integer customerPersonAvgStart = lossValueConfig.getCustomerPersonAvgStart();
            Integer customerPersonAvgEnd = lossValueConfig.getCustomerPersonAvgEnd();

            Integer customerTotalStart = lossValueConfig.getCustomerTotalStart();
            Integer customerTotalEnd = lossValueConfig.getCustomerTotalEnd();

            Integer customerCountStart = lossValueConfig.getCustomerCountStart();
            Integer customerCountEnd = lossValueConfig.getCustomerCountEnd();

            // 人均消费
            boolean customerPersonAvConfig = getCustomerPersonAvgConfig(customerPersonAvgStart, customerPersonAvgEnd, personAvg);
            // 消费总金额
            boolean customerTotalConfig = getCustomerTotalConfig(customerTotalStart, customerTotalEnd, customerAmount);
            // 消费次数
            boolean customerCountConfig = getCustomerCountConfig(customerCountStart, customerCountEnd, customerCount);

            if (customerPersonAvConfig && customerTotalConfig && customerCountConfig) {
                return id;
            }
        }
        return -1L;
    }

    private boolean getCustomerPersonAvgConfig(Integer customerPersonAvgStart, Integer customerPersonAvgEnd, int personAvg) {
        int flag = 0;
        if (customerPersonAvgStart > 0) {
            flag = 1;
        }
        if (customerPersonAvgEnd > 0) {
            flag += 2;
        }

        if (flag == 0) {
            // 两个都没有值
            return true;
        } else if (flag == 1) {
            // 配置了起始值，没有配置结束值
            if (personAvg >= customerPersonAvgStart) {
                return true;
            }
        } else if (flag == 2) {
            // 配置了结束值，没有配置起始值
            if (personAvg <= customerPersonAvgEnd) {
                return true;
            }
        } else {
            // 两个都有配置值
            if (personAvg >= customerPersonAvgStart && personAvg <= customerPersonAvgEnd) {
                return true;
            }
        }
        return false;
    }

    private boolean getCustomerTotalConfig(Integer customerTotalStart, Integer customerTotalEnd, double customerAmount) {
        int flag = 0;
        if (customerTotalStart > 0) {
            flag = 1;
        }
        if (customerTotalEnd > 0) {
            flag += 2;
        }

        if (flag == 0) {
            // 两个都没有值
            return true;
        } else if (flag == 1) {
            // 配置了起始值，没有配置结束值
            if (customerAmount >= customerTotalStart) {
                return true;
            }
        } else if (flag == 2) {
            // 配置了结束值，没有配置起始值
            if (customerAmount <= customerTotalEnd) {
                return true;
            }
        } else {
            // 两个都没有配置值
            if (customerAmount >= customerTotalStart && customerAmount <= customerTotalEnd) {
                return true;
            }
        }
        return false;
    }

    private boolean getCustomerCountConfig(Integer customerCountStart, Integer customerCountEnd, int customerCount) {
        int flag = 0;
        if (customerCountStart > 0) {
            flag = 1;
        }
        if (customerCountEnd > 0) {
            flag += 2;
        }

        if (flag == 0) {
            // 两个都没有配置值
            return true;
        } else if (flag == 1) {
            // 配置了起始值，没有配置结束值
            if (customerCount >= customerCountStart) {
                return true;
            }
        } else if (flag == 2) {
            // 配置了结束值，没有配置起始值
            if (customerCount <= customerCountEnd) {
                return true;
            }
        } else {

            // 两个都有值
            if (customerCount >= customerCountStart && customerCount <= customerCountEnd) {
                return true;
            }
        }
        return false;
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
     *
     * @param resvOrders 订单列表
     * @param businessId 酒店列表
     * @return 自定义类别的名称
     */
    private String getCustomerClass(List<ResvOrder> resvOrders, Integer businessId) {
        return StringUtils.EMPTY;
    }
}
