package com.zhidianfan.pig.yd.moduler.resv.service;

import com.google.common.collect.Lists;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.CustomerRecord;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.CustomerValueList;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrder;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Vip;
import com.zhidianfan.pig.yd.moduler.common.service.ICustomerRecordService;
import com.zhidianfan.pig.yd.moduler.common.service.ICustomerValueListService;
import com.zhidianfan.pig.yd.moduler.resv.constants.CustomerValueConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author sjl
 * 2019-05-28 13:33
 */
@Slf4j
@Service
public class CustomerRecordService {

    @Autowired
    private ICustomerValueListService customerValueListMapper;

    public List<CustomerRecord> getCustomerRecord(Vip vip, List<ResvOrder> resvOrders, CustomerValueList customerValueList) {
        List<CustomerRecord> recordList = Lists.newArrayList();
        List<CustomerRecord> customerRecords = reserveOrderCustomer(vip, resvOrders);
        List<CustomerRecord> customerRecords1 = reserveOrderESC(vip, resvOrders);
        List<CustomerRecord> customerRecords2 = manOrder(resvOrders);
        List<CustomerRecord> customerRecords3 = guestOrder(vip, resvOrders);
        CustomerRecord valueChangeRecord = valueChange(vip, customerValueList);
        CustomerRecord userChangeRecord = appUserChange(vip, customerValueList);

        recordList.addAll(customerRecords);
        recordList.addAll(customerRecords1);
        recordList.addAll(customerRecords2);
        recordList.addAll(customerRecords3);
        recordList.add(valueChangeRecord);
        recordList.add(userChangeRecord);

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
        record.setMealTypeName(order.getMealTypeName());
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
    private List<CustomerRecord> manOrder(List<ResvOrder> resvOrders) {
        // 买单的人，但不是预订的人
        List<CustomerRecord> recordList = resvOrders.stream()
                .filter(order -> {
                    Integer vipId = order.getVipId();
                    if (vipId == null) {
                        return false;
                    }
                    Integer manVipId = order.getManVipId();
                    if (manVipId == null) {
                        return false;
                    }
                    return !vipId.equals(manVipId);
                })
                .map(order -> setRecordOrder(order, CustomerValueConstants.RECORD_TYPE_MAN))
                .collect(Collectors.toList());
        // 主客订单列表
        return recordList;
    }

    /**
     * 宾客订单
     */
    private List<CustomerRecord> guestOrder(Vip vip, List<ResvOrder> otherResvOrders) {
        // 以宾客的形式，出现在了其他人的订单中
        Integer id = vip.getId();
        List<CustomerRecord> orderList = otherResvOrders.stream()
                .filter(order -> {
                    Integer guestVip = order.getGuestVip();
                    return guestVip != null;
                })
                .filter(order -> {
                    Integer guestVip = order.getGuestVip();
                    return id.equals(guestVip);
                })
                .map(order -> setRecordOrder(order, CustomerValueConstants.RECORD_TYPE_GUEST))
                .collect(Collectors.toList());
        // 宾客列表
        return orderList;
    }

    /**
     * 价值变更，之前的价值变更与现在的价值进行对比
     */
    private CustomerRecord valueChange(Vip vip, CustomerValueList customerValueList) {
        // 1-意向客户，2-活跃客户，3-沉睡客户，4-流失客户
        Integer firstClassValue = customerValueList.getFirstClassValue();
//        Integer firstClassValue = 1;
        Integer customerValue = getCustomerValue(vip);
        if (!firstClassValue.equals(customerValue)) {
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
            String value = getFirstClassValueStr(firstClassValue);
            String customerValueName = getCustomerValueName(vip);
            record.setOperationLog("由" + value + "变更为" + customerValueName);
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
    private Integer getCustomerValue(Vip vip) {
        Integer vipValueId = vip.getVipValueId();
        String vipName = vip.getVipName();

        return vipValueId;
    }

    /**
     * 营销经理变更
     */
    private CustomerRecord appUserChange(Vip vip, CustomerValueList customerValueList) {
//        Integer appUserId = customerValueList.getAppUserId();
        Integer appUserId = 1;
        Integer changeAppUserId = getAppUserId(vip);
        if (!appUserId.equals(changeAppUserId)) {
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
            record.setAppUserName("");
            record.setAppUserId(0);
            record.setOperationLog("");
            record.setCreateUserId(0L);
            record.setCreateTime(LocalDateTime.now());
            record.setUpdateUserId(0L);
            record.setUpdateTime(LocalDateTime.now());
            return record;
        }

        return null;
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
