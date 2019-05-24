package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.ResvOrderMapper;
import com.zhidianfan.pig.yd.moduler.common.service.*;
import com.zhidianfan.pig.yd.moduler.resv.constants.CustomerValueConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * @author sjl
 * 2019-05-21 13:57
 */
@Slf4j
@Service
public class CustomerValueBaseInfoService {

    @Autowired
    private IVipService vipMapper;

    @Autowired
    private ResvOrderMapper resvOrderMapper;

    @Autowired
    private IBusinessService businessMapper;

    @Autowired
    private ICustomerValueTaskService customerValueTaskMapper;

    @Autowired
    private FirstValueConfigService firstValueConfigService;

    @Autowired
    private LossValueConfigService lossValueConfigService;

    @Autowired
    private IAppUserService appUserMapper;

    @Autowired
    private ICustomerValueListService customerValueListMapper;

    @Autowired
    private IVipConsumeActionTotalService vipConsumeActionTotalMapper;

    @Autowired
    private IVipConsumeActionLast60Service vipConsumeActionLast60Mapper;

    /**
     * 每天执行，把酒店全部插入到任务表中，后面数据计算都任务表中取酒店 id,然后根据酒店分别执行任务
     */
    public void addCustomerList() {
        log.info("开始执行 getCustomerList() ，将要执行的任务写入任务批次表中...");
        try {
            // 查询出所有酒店,写入任务批次表中
            List<Business> businesses = businessMapper.selectList(new EntityWrapper<>());
            List<CustomerValueTask> valueTaskList = businesses.stream()
                    .map(business -> {
                        // 生成任务信息
                        CustomerValueTask task = new CustomerValueTask();
                        task.setTaskBatchNo(getBatchNo(business.getId()));
                        task.setHotelId(Long.valueOf(business.getId()));
                        Integer brandId = business.getBrandId();
                        if (brandId == null) {
                            brandId = -1;
                        }
                        task.setBrandId(brandId);
                        task.setPlanTime(LocalDate.now());
                        task.setCreateTime(LocalDateTime.now());
                        task.setUpdateTime(LocalDateTime.now());
                        task.setStartTime(CustomerValueConstants.DEFAULT_START_TIME);
                        task.setEndTime(CustomerValueConstants.DEFAULT_START_TIME);
                        task.setSpendTime(CustomerValueConstants.DEFAULT_SPEND_TIME);
                        task.setFlag(CustomerValueConstants.NON_EXECUTE);
                        return task;
                    }).collect(Collectors.toList());
            customerValueTaskMapper.insertBatch(valueTaskList);
            log.info("执行 getCustomerList() 完成,写入任务批次表中数据{}...", valueTaskList.size());
        } catch (Exception e) {
            log.error("任务执行发生异常...", e);
        }
    }

    /**
     * 生成批次号
     *
     * @param businessID 酒店 id
     * @return 批次号
     */
    private Long getBatchNo(int businessID) {
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String s = format + businessID;
        return Long.valueOf(s);
    }

    public void getCustomerValueBaseInfo() {
        LocalDateTime startTime = LocalDateTime.now();
        // 1. 取出酒店 id
        CustomerValueTask customerValueTask = getCustomerValueTask();
        // 1.1 查询属于该酒店的所有客户
        Long hotelId = customerValueTask.getHotelId();
//        List<Vip> vips = getVipList(hotelId);
        List<Vip> vips = getVipList(30);
        String exceptionMessage = StringUtils.EMPTY;
        boolean b = false;
        for (Vip vip : vips) {
            try {
                b = execute(vip);
            } catch (Exception e) {
                exceptionMessage = e.toString();
            }
            if (!b) {
                log.error("写入数据库失败----", vip);
            }
        }
        Long taskId = customerValueTask.getId();
//        long taskId = 1131751120788840450L;
        LocalDateTime endTime = LocalDateTime.now();
        updateTaskStatus(taskId, b, startTime, endTime, exceptionMessage);

    }

    private boolean execute(Vip vip) {
        Integer businessId = vip.getBusinessId();
        Integer vipId = vip.getId();
        String vipName = vip.getVipName();
        String vipSex = vip.getVipSex();
        String vipPhone = vip.getVipPhone();
        // 年龄
        int vipAge = getAge(vip);
        String vipCompany = vip.getVipCompany();
        if (vipCompany == null) {
            vipCompany = StringUtils.EMPTY;
        }
        // 消费次数
        int customerCount = getCustomerCount(vipId);
        // 总消费金额
        double customerAmount = getCustomerAmount(vipId);
        int customerAmountInt = (int)customerAmount * 100;
        // 人均消费金额
        int personAvg = getPersonAvg(vipId);
        // 最近就餐时间
        LocalDateTime lastEatTime = getLastEatTime(vipId);
        // 一级价值，意向客户、活跃客户、沉睡客户、流失客户
        int firstValue = getFirstValue(vipId, businessId);
        // 细分价值
        String lossValue = getLossValue(vipId, businessId);
        // 自定义分类
        // todo 使用之前的分类
        String customerClass = "";
        Integer appUserId = vip.getAppUserId();
        Optional<Integer> optionalAppUserId = Optional.ofNullable(appUserId);
        String appUserName = getAppUserName(appUserId);

        // todo 列表页面
        CustomerValueList customerValueList = new CustomerValueList();
        customerValueList.setVipId(vip.getId());
        customerValueList.setVipName(vipName);
        customerValueList.setVipSex(vipSex);
        customerValueList.setVipPhone(vipPhone);
        customerValueList.setVipAge(vipAge);
        customerValueList.setVipCompany(vipCompany);
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

        // todo 计算总体消费行为，并插入
        // 消费订单总次数
        VipConsumeActionTotal vipConsumeActionTotal = new VipConsumeActionTotal();
        vipConsumeActionTotal.setVipId(vipId);
        // 消费完成总订单数
        vipConsumeActionTotal.setTotalOrderNo(customerCount);
        // 消费完成总桌数
        vipConsumeActionTotal.setTotalTableNo(getCustomerTableCount(vipId));
        // 消费完成总人数
        vipConsumeActionTotal.setTotalPersonNo(getConsumerTotalCount(vipId));
        // 撤单订单数
        vipConsumeActionTotal.setCancelOrderNo(getCancelOrderCount(vipId));
        // 撤单桌数
        vipConsumeActionTotal.setCancelTableNo(getCancelOrderTable(vipId));
        // 撤单人数
        vipConsumeActionTotal.setCancelPersonNo(getCancelOrderPerson(vipId));
        // 消费总金额，单位：分
        vipConsumeActionTotal.setTotalConsumeAvg(getConsumerTotalAmount(vipId));
        // 桌均消费,单位:分
        vipConsumeActionTotal.setTableConsumeAvg(getConsumerTableAmount(vipId));
        // 人均消费,单位:分
        vipConsumeActionTotal.setPersonConsumeAvg(getConsumerPersonAmount(vipId));
        // 首次消费时间
        vipConsumeActionTotal.setFirstConsumeTime(getFirstConsumerTime(vipId));
        // 消费频次
        vipConsumeActionTotal.setConsumeFrequency(getConsumerFrequency(vipId));
        // 最近就餐时间
        vipConsumeActionTotal.setLastConsumeTime(lastEatTime);
        vipConsumeActionTotal.setCreateUserId(CustomerValueConstants.DEFAULT_USER_ID);
        vipConsumeActionTotal.setCreateTime(LocalDateTime.now());
        vipConsumeActionTotal.setUpdateUserId(CustomerValueConstants.DEFAULT_USER_ID);
        vipConsumeActionTotal.setUpdateTime(LocalDateTime.now());


        // todo 计算 60 天消费行为，并插入
        VipConsumeActionLast60 vipConsumeActionLast60 = new VipConsumeActionLast60();

        vipConsumeActionLast60.setVipId(vipId);
        // 消费完成总订单数
        vipConsumeActionLast60.setTotalOrderNo(getTotalOrderNo60(vipId));
        // 消费完成总桌数
        vipConsumeActionLast60.setTotalTableNo(getTotalTableNo60(vipId));
        // 消费完成总人数
        vipConsumeActionLast60.setTotalPersonNo(getTotalPersonNo60(vipId));
        // 撤销订单数
        vipConsumeActionLast60.setCancelOrderNo(getCancelOrderNo60(vipId));
        // 撤单桌数
        vipConsumeActionLast60.setCancelTableNo(getCancelTableNo60(vipId));
        // 撤单人数
        vipConsumeActionLast60.setCancelPersonNo(getCancelPersonNo60(vipId));
        // 消费总金额,单位:分
        vipConsumeActionLast60.setTotalConsumeAmount(getTotalConsumeAmount60(vipId));
        // 桌均消费,单位:分
        vipConsumeActionLast60.setTableConsumeAvg(getTableConsumeAvg60(vipId));
        // 人均消费,单位:分
        vipConsumeActionLast60.setPersonConsumeAvg(getPersonConsumeAvg(vipId));
        // 消费频次
        vipConsumeActionLast60.setConsumeFrequency(getConsumeFrequency60(vipId));
        vipConsumeActionLast60.setCreateUserId(CustomerValueConstants.DEFAULT_USER_ID);
        vipConsumeActionLast60.setCreateTime(LocalDateTime.now());
        vipConsumeActionLast60.setUpdateUserId(CustomerValueConstants.DEFAULT_USER_ID);
        vipConsumeActionLast60.setUpdateTime(LocalDateTime.now());

        customerValueListMapper.insert(customerValueList);
        vipConsumeActionTotalMapper.insert(vipConsumeActionTotal);
        vipConsumeActionLast60Mapper.insert(vipConsumeActionLast60);

         //todo 这里最后是需要修改的
         return true;
    }

    /**
     * 60天内消费频次
     * @param vipId
     * @return
     */
    private Integer getConsumeFrequency60(Integer vipId) {
        return 0;
    }

    /**
     * 60天内人均消费,单位:分
     * @param vipId
     * @return
     */
    private Integer getPersonConsumeAvg(Integer vipId) {
        return 0;
    }

    /**
     * 60天内桌均消费,单位:分
     * @param vipId
     * @return
     */
    private Integer getTableConsumeAvg60(Integer vipId) {
        return 0;
    }

    /**
     * 60天内消费总金额,单位:分
     * @param vipId
     * @return
     */
    private Integer getTotalConsumeAmount60(Integer vipId) {
        return 0;
    }

    /**
     * 60天内撤单人数
     * @param vipId
     * @return
     */
    private Integer getCancelPersonNo60(Integer vipId) {
        return 0;
    }

    /**
     * 60天内撤单桌数
     * @param vipId
     * @return
     */
    private Integer getCancelTableNo60(Integer vipId) {
        return 0;
    }

    /**
     * 60天内撤销订单数
     * @param vipId
     * @return
     */
    private Integer getCancelOrderNo60(Integer vipId) {
        return 0;
    }

    /**
     * 60天内消费完成总人数
     * @param vipId
     * @return
     */
    private Integer getTotalPersonNo60(Integer vipId) {
        return 0;
    }

    /**
     * 60天内消费完成总桌数
     * @param vipId
     * @return
     */
    private Integer getTotalTableNo60(Integer vipId) {
        return 0;
    }

    /**
     * 60天内消费完成总订单数
     * @param vipId
     * @return
     */
    private Integer getTotalOrderNo60(Integer vipId) {
        return 0;
    }

    /**
     * 消费频次
     * @param vipId
     * @return
     */
    private Integer getConsumerFrequency(Integer vipId) {
        return 0;
    }

    /**
     * 首次消费时间
     * @param vipId
     * @return
     */
    private LocalDateTime getFirstConsumerTime(Integer vipId) {
        return CustomerValueConstants.DEFAULT_START_TIME;
    }

    /**
     * 人均消费,单位:分
     * @param vipId
     * @return
     */
    private Integer getConsumerPersonAmount(Integer vipId) {
        return null;
    }

    /**
     * 桌均消费,单位:分
     * @param vipId
     * @return
     */
    private Integer getConsumerTableAmount(Integer vipId) {
        return 0;
    }

    /**
     * 消费总金额，单位：分
     * @param vipId
     * @return
     */
    private Integer getConsumerTotalAmount(Integer vipId) {
        return 0;
    }

    /**
     * 撤单人数
     * @param vipId
     * @return
     */
    private Integer getCancelOrderPerson(Integer vipId) {
        return 0;
    }

    /**
     * 撤单桌数
     * @param vipId
     * @return
     */
    private Integer getCancelOrderTable(Integer vipId) {
        return 0;
    }

    /**
     * 撤单数量
     * @param vipId
     * @return
     */
    private Integer getCancelOrderCount(Integer vipId) {
        return 0;
    }

    /**
     * 消费完成的总次数
     * @param vipId
     * @return 0-无
     */
    private Integer getConsumerTotalCount(Integer vipId) {
        return 0;
    }

    /**
     * 获取消费完成总的桌数
     * @param vipId id
     * @return 0-无
     */
    private Integer getCustomerTableCount(Integer vipId) {
        return 0;
    }

    /**
     * 更新任务的状态
     * @param b true-成功,false-失败
     * @param exceptionMessage 异常信息
     */
    private void updateTaskStatus(Long taskId, boolean b, LocalDateTime startTime, LocalDateTime endTime, String exceptionMessage) {
        // todo 更新任务表状态
        CustomerValueTask task = new CustomerValueTask();
        task.setId(taskId);
        task.setStartTime(startTime);
        task.setEndTime(endTime);
        Duration duration = Duration.between(startTime, endTime);
        long seconds = duration.getSeconds();
        task.setSpendTime((int)seconds);
        task.setFlag(CustomerValueConstants.EXECUTE_SUCCESS);
        task.setRemark(exceptionMessage);
        task.setUpdateTime(LocalDateTime.now());
        task.setId(taskId);

        customerValueTaskMapper.updateById(task);
    }

    private List<Vip> getVipList(long hotelId) {
        Wrapper<Vip> wrapper = new EntityWrapper<>();
        wrapper.eq("business_id", hotelId);
        List<Vip> vips = vipMapper.selectList(wrapper);
        return vips;
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
        return appUser.getAppUserName();
    }

    /**
     * 细分价值
     *
     * @param vipId   用户id
     * @param hotelId 酒店id
     * @return 1-4 的取值
     */
    private String getLossValue(Integer vipId, Integer hotelId) {
        List<LossValueConfig> lossValueConfigList = lossValueConfigService.getLossValueConfig(hotelId);
        lossValueConfigList.sort(((o1, o2) -> o1.getSort() > o2.getSort() ? 1 : -1));

        double personAvg = getPersonAvg(vipId);
        double customerAmount = getCustomerAmount(vipId);
        int customerCount = getCustomerCount(vipId);
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
     * 用户一级价值
     *
     * @param vipId   用户 id
     * @param hotelId 酒店 id
     * @return 0-未配置,1-意向客户，2-活跃客户，3-沉睡客户，4-流失客户
     */
    public int getFirstValue(Integer vipId, Integer hotelId) {
        FirstValueConfig config = firstValueConfigService.getConfig(hotelId);
        if (config == null) {
            return 0;
        }
        Integer startValue = config.getStartValue();
        Integer endValue = config.getEndValue();
        List<ResvOrder> resvOrders = getResvOrders(vipId);
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

    public LocalDateTime getLastEatTime(Integer vipId) {
        List<ResvOrder> resvOrders = getResvOrders(vipId);
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

    public int getCustomerCount(Integer vipId) {
        if (vipId == null) {
            log.error("getCustomerCount()...vipId 为空");
            return -1;
        }
        List<ResvOrder> resvOrders = getResvOrders(vipId);

        List<ResvOrder> collect = resvOrders.stream()
                .collect(collectingAndThen(toCollection(
                        () -> new TreeSet<>(comparing(ResvOrder::getBatchNo))), ArrayList::new)
                );
        return collect.size();
    }

    /**
     * 消费总金额
     *
     * @param vipId 用户id
     * @return 消费总金额，单位:分
     */
    public int getCustomerAmount(Integer vipId) {
        String payStatus = "3";
        List<ResvOrder> resvOrders = getResvOrders(vipId);
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
     * @param vipId 用户id
     * @return 人均消费金额，单元:分
     */
    public int getPersonAvg(Integer vipId) {
        List<ResvOrder> resvOrders = getResvOrders(vipId);
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
//        return Integer.valueOf(v * 100 + "");
    }

    private List<ResvOrder> getResvOrders(Integer vipId) {
        EntityWrapper<ResvOrder> wrapper = new EntityWrapper<>();
        wrapper.eq("vip_id", vipId);
        wrapper.in("status", Arrays.asList(2, 3));
        return resvOrderMapper.selectList(wrapper);
    }

    /**
     * 获取年龄
     *
     * @param vip vip 信息
     * @return 不显示的年龄为 -1
     */
    private int getAge(Vip vip) {
        Integer hideBirthdayYear = vip.getHideBirthdayYear();
        if (hideBirthdayYear == null || hideBirthdayYear == 1) {
            return CustomerValueConstants.DEFAULT_NON_AGE;
        }
        String vipBirthday = vip.getVipBirthday();
        if (StringUtils.isBlank(vipBirthday)) {
            return CustomerValueConstants.DEFAULT_NON_AGE;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyy-MM-dd");
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(vipBirthday, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            log.error("格式转换失败", e);
            return CustomerValueConstants.DEFAULT_NON_AGE;
        } catch (RuntimeException e) {
            log.error("其他运行时的异常", e);
            return CustomerValueConstants.DEFAULT_NON_AGE;
        }
        LocalDate now = LocalDate.now();
        Period between = Period.between(now, localDate);
        return between.getYears();
    }

    /**
     * 获取酒店 id
     *
     * @return 酒店 id
     */
    private CustomerValueTask getCustomerValueTask() {
        List<CustomerValueTask> customerValueTasks = customerValueTaskMapper.selectList(new EntityWrapper<>());
        Optional<CustomerValueTask> optionalCustomerValueTask = customerValueTasks.stream()
                .max(((o1, o2) -> o1.getSort() > o2.getSort() ? 1 : -1));
        return optionalCustomerValueTask.orElseThrow(RuntimeException::new);
    }

    private int getSittingDownOrderCount(String vipId) {
        Wrapper<ResvOrder> wrapper = new EntityWrapper<>();
        wrapper.eq("vip_id", vipId);
        Integer count = resvOrderMapper.selectCount(wrapper);
        return Optional.ofNullable(count).orElse(0);
    }

    // 计算总体消费行为
    public void customerActionTotal(Integer vipId) {
        Wrapper<CustomerValueList> wrapper = new EntityWrapper<>();
        wrapper.eq("vip_id", vipId);
        CustomerValueList customerValueList = customerValueListMapper.selectOne(wrapper);

        // 消费订单：次
        Integer customerCount = customerValueList.getCustomerCount();
        // 消费订单：桌
        Integer customerTable = customerValueList.getCustomerTable();
        // 消费订单：人
        Integer customerPerson = customerValueList.getCustomerPerson();
        // 总金额，单位：分
        Integer customerAmountTotal = customerValueList.getCustomerAmountTotal();
        // 桌均消费
        Integer customerAmountTable = customerValueList.getCustomerAmountTable();
        // 人均消费
        Integer customerAmountAvg = customerValueList.getCustomerAmountAvg();
        // 消费频次

    }

}
