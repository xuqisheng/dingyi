package com.zhidianfan.pig.yd.moduler.resv.service;

import com.google.common.collect.Lists;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.CustomerRecord;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.CustomerValueList;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrder;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Vip;
import com.zhidianfan.pig.yd.moduler.common.service.ICustomerRecordService;
import com.zhidianfan.pig.yd.moduler.common.service.ICustomerValueListService;
import com.zhidianfan.pig.yd.moduler.resv.constants.CustomerValueConstants;
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
@Service
public class CustomerRecordService {

    @Autowired
    private ICustomerRecordService customerRecordMapper;

    @Autowired
    private ICustomerValueListService customerValueListMapper;

    public void saveRecord(Vip vip, List<ResvOrder> resvOrders, CustomerValueList customerValueList) {
        List<CustomerRecord> insertRecordList = Lists.newArrayList();
        List<CustomerRecord> customerRecords = reserveOrderCustomer(vip, resvOrders);
        List<CustomerRecord> customerRecords1 = reserveOrderESC(vip, resvOrders);
        List<CustomerRecord> customerRecords2 = manOrder(resvOrders);
        List<CustomerRecord> customerRecords3 = guestOrder(vip, resvOrders);
        //todo 用户价值列表
        CustomerRecord valueChangeRecord = valueChange(customerValueList);
        CustomerRecord userChangeRecord = appUserChange(customerValueList);

        insertRecordList.addAll(customerRecords);
        insertRecordList.addAll(customerRecords1);
        insertRecordList.addAll(customerRecords2);
        insertRecordList.addAll(customerRecords3);
        insertRecordList.add(valueChangeRecord);
        insertRecordList.add(userChangeRecord);

        customerRecordMapper.insertBatch(insertRecordList);
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
        record.setOperationLog("");
        record.setCreateUserId(0L);
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateUserId(0L);
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
                    Integer vipId = order.getGuestVip();
                    Integer manVipId = order.getManVipId();
                    if (manVipId == null) {
                        return false;
                    }
                    return !vipId.equals(manVipId);
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
    private CustomerRecord valueChange(CustomerValueList customerValueList) {
//        Integer firstClassValue = customerValueList.getFirstClassValue();
        Integer firstClassValue = 1;
        Integer customerValue = getCustomerValue(customerValueList.getVipId());
        if (!firstClassValue.equals(customerValue)) {
            CustomerRecord record = new CustomerRecord();
            record.setVipId(customerValueList.getVipId());
            record.setLogType(CustomerValueConstants.RECORD_TYPE_VALUE_CHANGE);
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
        // todo 返回值类型
        return null;
    }

    /**
     * 客户一级价值
     * @param vipId vipid
     * @return
     */
    private Integer getCustomerValue(Integer vipId) {
        CustomerValueList customerValueList = customerValueListMapper.selectById(vipId);
        CustomerValueList optValue = Optional.ofNullable(customerValueList).orElseGet(CustomerValueList::new);
        return optValue.getFirstClassValue();
    }

    /**
     * 营销经理变更
     */
    private CustomerRecord appUserChange(CustomerValueList customerValueList) {
//        Integer appUserId = customerValueList.getAppUserId();
        Integer appUserId = 1;
        Integer changeAppUserId = getAppUserId(customerValueList.getVipId());
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
     * @param vipId vipid
     * @return 营销经理对应的 app_user_id
     */
    private Integer getAppUserId(Integer vipId) {
        // customer_value_list
        CustomerValueList customerValueList = customerValueListMapper.selectById(vipId);
        CustomerValueList optValue = Optional.ofNullable(customerValueList).orElseGet(CustomerValueList::new);
        return optValue.getAppUserId();
    }


}
