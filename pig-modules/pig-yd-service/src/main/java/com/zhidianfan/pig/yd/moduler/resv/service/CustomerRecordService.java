package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.google.common.collect.Lists;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.service.ICustomerRecordService;
import com.zhidianfan.pig.yd.moduler.common.service.IGuestCustomerVipMappingService;
import com.zhidianfan.pig.yd.moduler.common.service.IMasterCustomerVipMappingService;
import com.zhidianfan.pig.yd.moduler.resv.constants.CustomerValueConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author sjl
 * 2019-05-28 13:33
 */
@Slf4j
@Service
public class CustomerRecordService {

    @Autowired
    private IMasterCustomerVipMappingService iMasterCustomerVipMappingService;

    @Autowired
    private IGuestCustomerVipMappingService iGuestCustomerVipMappingService;


    public List<CustomerRecord> getCustomerRecord(Vip vip, List<ResvOrder> resvOrders, CustomerValueList customerValueList) {
        List<CustomerRecord> recordList = Lists.newArrayList();
        List<CustomerRecord> customerRecords = reserveOrderCustomer(vip, resvOrders);
        List<CustomerRecord> customerRecords1 = reserveOrderESC(vip, resvOrders);
        List<CustomerRecord> customerRecords2 = manOrder(vip, resvOrders);
        List<CustomerRecord> customerRecords3 = guestOrder(vip, resvOrders);
        CustomerRecord valueChangeRecord = valueChange(vip, customerValueList);
        CustomerRecord userChangeRecord = appUserChange(vip, customerValueList);

        recordList.addAll(customerRecords);
        recordList.addAll(customerRecords1);
        recordList.addAll(customerRecords2);
        recordList.addAll(customerRecords3);
        if (valueChangeRecord != null) {
            recordList.add(valueChangeRecord);
        }
        if (userChangeRecord != null) {
            recordList.add(userChangeRecord);
        }

        return recordList;
    }

    /**
     * 预订定单 - 消费订单
     * @param resvOrders 订单列表
     */
    private List<CustomerRecord> reserveOrderCustomer(Vip vip, List<ResvOrder> resvOrders) {
        List<CustomerRecord> recordList = resvOrders.stream()
                .filter(order -> "2".equals(order.getStatus()) || "3".equals(order.getStatus()))
                .map(order -> setRecordOrder(order, CustomerValueConstants.RECORD_TYPE_CUSTOMER))
                .collect(Collectors.toList());

        return recordList;
    }

    private CustomerRecord setRecordOrder(ResvOrder order, int type) {
        CustomerRecord record = new CustomerRecord();
        record.setVipId(order.getVipId());
        record.setLogType(type);
        record.setLogTime(LocalDateTime.now());
        record.setResvOrder(order.getBatchNo());
        Date updatedAt = order.getUpdatedAt();
        LocalDateTime resvDate = null;
        if (updatedAt != null) {
            Instant instant = updatedAt.toInstant();
            resvDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        } else {
            resvDate = CustomerValueConstants.DEFAULT_START_TIME;
        }
        record.setResvDate(resvDate);
        record.setMealTypeId(order.getMealTypeId());
        record.setMealTypeName(order.getMealTypeName().trim());
        String payamount = order.getPayamount();
        if (StringUtils.isNotBlank(payamount)) {
            double v = Double.parseDouble(payamount);
            int amount = (int) (v * 100);
            record.setConsumeAmount(amount);
        } else {
            record.setConsumeAmount(0);
        }
        String resvNum = order.getResvNum();
        int resvNo = 0;
        if (NumberUtils.isCreatable(resvNum)) {
            resvNo = Integer.parseInt(resvNum);
        } else {
            String actualNum = order.getActualNum();
            if (NumberUtils.isCreatable(actualNum)) {
                resvNo = Integer.parseInt(actualNum);
            }
        }
        record.setPersonNo(resvNo);
        record.setTableId(order.getTableId());
        record.setTableName(order.getTableName());
        record.setVipName(order.getVipName());
        record.setVipPhone(order.getVipPhone());
        record.setAppUserName(order.getAppUserName());
        record.setAppUserId(order.getAppUserId());
        record.setAppUserPhone(order.getAppUserPhone());
        record.setOperationLog(StringUtils.EMPTY);
        record.setCreateUserId(CustomerValueConstants.DEFAULT_USER_ID);
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateUserId(CustomerValueConstants.DEFAULT_USER_ID);
        record.setUpdateTime(LocalDateTime.now());
        return record;
    }

    /**
     * 预订定单-退订订单
     * @param resvOrders 预订订单
     */
    private List<CustomerRecord> reserveOrderESC(Vip vip, List<ResvOrder> resvOrders) {
        List<CustomerRecord> recordList = resvOrders.stream()
                .filter(order -> "4".equals(order.getStatus()))
                .map(order -> setRecordOrder(order, CustomerValueConstants.RECORD_TYPE_ESC))
                .collect(Collectors.toList());

        return recordList;
    }

    /**
     * 主客订单
     */
    private List<CustomerRecord> manOrder(Vip vip, List<ResvOrder> resvOrders) {
        if (vip == null) {
            log.error("vip 信息为空");
            return Lists.newArrayList();
        }
        Integer vipId = vip.getId();
        Integer businessId = vip.getBusinessId();
        Wrapper<MasterCustomerVipMapping> wrapper = new EntityWrapper<>();
        wrapper.eq("business_id", businessId);
        wrapper.eq("master_customer_id", vipId);
        MasterCustomerVipMapping masterCustomerVipMapping = iMasterCustomerVipMappingService.selectOne(wrapper);
        if (masterCustomerVipMapping == null) {
            return Lists.newArrayList();
        }
        String batchNo = masterCustomerVipMapping.getBatchNo();
        List<CustomerRecord> collect = resvOrders.stream()
                .filter(order -> order.getBatchNo().equals(batchNo))
                .map(order -> setRecordOrder(order, CustomerValueConstants.RECORD_TYPE_MAN))
                .collect(Collectors.toList());

        // 主客订单列表
        return collect;
    }

    /**
     * 宾客订单
     */
    private List<CustomerRecord> guestOrder(Vip vip, List<ResvOrder> otherResvOrders) {
        // 以宾客的形式，出现在了其他人的订单中
        Integer vipId = vip.getId();
        Wrapper<GuestCustomerVipMapping> wrapper = new EntityWrapper<>();
        wrapper.eq("guest_customer_id", vipId);
        List<GuestCustomerVipMapping> guestCustomerVipMappings = iGuestCustomerVipMappingService.selectList(wrapper);

        List<CustomerRecord> recordList = new ArrayList<>();
        for (GuestCustomerVipMapping guestCustomerVipMapping : guestCustomerVipMappings) {
            Integer guestCustomerId = guestCustomerVipMapping.getGuestCustomerId();
            for (ResvOrder otherResvOrder : otherResvOrders) {
                Integer vipId1 = otherResvOrder.getVipId();
                if (guestCustomerId.equals(vipId1)) {
                    CustomerRecord record = setRecordOrder(otherResvOrder, CustomerValueConstants.RECORD_TYPE_GUEST);
                    recordList.add(record);
                }
            }
        }

        return recordList;
    }

    /**
     * 价值变更，之前的价值变更与现在的价值进行对比
     */
    private CustomerRecord valueChange(Vip vip, CustomerValueList customerValueList) {
        // 1-意向客户，2-活跃客户，3-沉睡客户，4-流失客户
        String customerValue = getCustomerValue(vip);
        Integer firstClassValue = customerValueList.getFirstClassValue();
        String value = getFirstClassValueStr(firstClassValue);

        customerValue = customerValue == null ? "": customerValue;
        value = value == null ? "" : value;

        // 沉睡客户，沉睡用户，只匹配前面两个字是否一样
        String customerValueNameS = "";
        if (StringUtils.isNotBlank(customerValue) && customerValue.length() >= 2) {
            customerValueNameS = customerValue.substring(0, 2);
        }
        String lastValue = "";
        if (StringUtils.isNotBlank(value) && value.length() >= 2) {
            lastValue = value.substring(0, 2);
        }
        if (!customerValueNameS.equals(lastValue)) {
            CustomerRecord record = new CustomerRecord();
            record.setVipId(customerValueList.getVipId());
            record.setLogType(CustomerValueConstants.RECORD_TYPE_VALUE_CHANGE);
            record.setLogTime(LocalDateTime.now());
            record.setResvOrder(StringUtils.EMPTY);
            record.setResvDate(LocalDateTime.now());
            record.setMealTypeId(0);
            record.setMealTypeName("");
            record.setConsumeAmount(0);
            record.setPersonNo(0);
            record.setTableId(0);
            record.setTableName("");
            record.setVipName("");
            record.setVipPhone("");
            record.setAppUserName("");
            record.setAppUserId(0);
//            String value = getFirstClassValueStr(firstClassValue);
            String customerValueName = getCustomerValueName(vip);
            record.setOperationLog("由" + customerValueName + "变更为" + value);
            record.setCreateUserId(CustomerValueConstants.DEFAULT_USER_ID);
            record.setCreateTime(LocalDateTime.now());
            record.setUpdateUserId(CustomerValueConstants.DEFAULT_USER_ID);
            record.setUpdateTime(LocalDateTime.now());
            return record;
        }
        // todo 返回值类型
        return null;
    }

    private String getCustomerValueName(Vip vip) {
        String vipValueName = vip.getVipValueName();
        if (StringUtils.isNotBlank(vipValueName)) {
            return vipValueName;
        }
        log.error("vip 信息为 null,[{}]", vip);
        return StringUtils.EMPTY;
    }

    private String getFirstClassValueStr(Integer firstClassValue) {
        // 1-意向客户，2-活跃客户，3-沉睡客户，4-流失客户
        if (firstClassValue.equals(1)) {
            return "意向客户";
        } else if (firstClassValue.equals(2)) {
            return "活跃客户";
        }else if (firstClassValue.equals(3)) {
            return "沉睡客户";
        }else if (firstClassValue.equals(4)) {
            return "流失客户";
        }
        log.error("客户一级价值错误:[{}]", firstClassValue);
        return StringUtils.EMPTY;

    }

    /**
     * 客户一级价值
     * @param vip 客户信息
     * @return
     */
    private String getCustomerValue(Vip vip) {
        Integer vipValueId = vip.getVipValueId();
        String vipName = vip.getVipName();

        return vipName;
    }

    @Autowired
    private ICustomerRecordService customerRecordMapper;

    @Autowired
    private BusinessCustomerAnalysisInfoService businessCustomerAnalysisInfoService;

    /**
     * 营销经理变更
     */
    private CustomerRecord appUserChange(Vip vip, CustomerValueList customerValueList) {
        Integer appUserId = getAppUserId(vip);
        if (appUserId < 1) {
            return null;
        }

        CustomerRecord customerRecord = getCustomerRecord(vip.getId());
        Integer changeAppUserId = customerRecord.getAppUserId();

        if (!appUserId.equals(changeAppUserId)) {
            AppUser appUser = businessCustomerAnalysisInfoService.getAppUser(appUserId);
            return setCustomerRecord(customerValueList, appUserId, appUser);
        }

        return null;
    }

    private CustomerRecord setCustomerRecord(CustomerValueList customerValueList, Integer appUserId, AppUser appUser) {
        CustomerRecord record = new CustomerRecord();
        record.setVipId(customerValueList.getVipId());
        record.setLogType(CustomerValueConstants.RECORD_TYPE_APP_USER_CHANGE);
        record.setLogTime(LocalDateTime.now());
        record.setResvOrder("");
        record.setResvDate(LocalDateTime.now());
        record.setMealTypeId(0);
        record.setMealTypeName("");
        record.setConsumeAmount(0);
        record.setPersonNo(0);
        record.setTableId(0);
        record.setTableName("");
        record.setVipName("");
        record.setVipPhone("");
        record.setAppUserName(appUser.getAppUserName());
        record.setAppUserId(appUserId);
        record.setOperationLog("");
        record.setCreateUserId(0L);
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateUserId(0L);
        record.setUpdateTime(LocalDateTime.now());
        return record;
    }

    private CustomerRecord getCustomerRecord(Integer vipId) {
        Wrapper<CustomerRecord> wrapper = new EntityWrapper<>();
        wrapper.eq("vip_id", vipId);
        List<CustomerRecord> customerRecords = customerRecordMapper.selectList(wrapper);
        Optional<CustomerRecord> max = customerRecords.stream()
                .max(Comparator.comparing(CustomerRecord::getUpdateTime));
        return max.orElse(new CustomerRecord());
    }

    /**
     * 指定 vipId 获取其当前的 营销经理
     * @param vip vip
     * @return 营销经理对应的 app_user_id
     */
    private Integer getAppUserId(Vip vip) {
        Integer appUserId = vip.getAppUserId();
        Optional<Integer> optional = Optional.ofNullable(appUserId);
        return optional.orElse(-1);
    }


}
