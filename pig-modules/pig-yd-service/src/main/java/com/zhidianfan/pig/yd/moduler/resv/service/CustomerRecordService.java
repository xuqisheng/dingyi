package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.service.ICustomerRecordService;
import com.zhidianfan.pig.yd.moduler.common.service.ICustomerValueListService;
import com.zhidianfan.pig.yd.moduler.common.service.IGuestCustomerVipMappingService;
import com.zhidianfan.pig.yd.moduler.common.service.IMasterCustomerVipMappingService;
import com.zhidianfan.pig.yd.moduler.resv.constants.CustomerValueConstants;
import com.zhidianfan.pig.yd.utils.PhoneUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.velocity.runtime.parser.node.MathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
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

    @Autowired
    private BusinessCustomerAnalysisInfoService businessCustomerAnalysisInfoService;

    @Autowired
    private ICustomerRecordService customerRecordMapper;


    public Map<Integer, List<CustomerRecord>> getCustomerRecord(List<Vip> vips, Map<Integer, List<ResvOrder>> resvOrdersMap, List<MasterCustomerVipMapping> masterCustomerVipMappings,
                                                                List<GuestCustomerVipMapping> guestCustomerVipMappings, ConfigTaskExec configTaskExec, CustomerValueTask customerValueTask) {

        cleanData(vips, configTaskExec);
        Map<Integer, List<CustomerRecord>> map = new HashMap<>();
        List<Integer> appUserIdList = getAppUser(vips);
        List<AppUser> appUserList = businessCustomerAnalysisInfoService.getAppUserList(appUserIdList);
        List<Integer> vipIdList = getVipIdList(vips);
        Map<Integer, List<CustomerRecord>> nowChangeOrderInfo = getNowChangeOrderInfo(vips);
        Map<Integer, CustomerRecord> nowChangeAppUser = getNowChangeAppUser(vips);
        Map<Integer, CustomerRecord> oldCustomerRecord = getOldCustomerRecord(vips);


        for (Vip vip : vips) {
            // log.info("vip 数量：[{}]", vips.size());
            try {
                List<CustomerRecord> recordList = Lists.newArrayList();

                LocalDateTime lastChangeTime = getLastChangeTime(nowChangeOrderInfo, vip);
                // CustomerRecord customerRecordAppUser = getCustomerRecord(vip, nowChangeAppUser.get(vip.getId()));
                CustomerRecord customerRecordAppUser = nowChangeAppUser.get(vip.getId());

                List<CustomerRecord> customerRecords = reserveOrderCustomer(vip, resvOrdersMap.get(vip.getId()), lastChangeTime);
                List<CustomerRecord> customerRecords1 = reserveOrderESC(vip, resvOrdersMap.get(vip.getId()), lastChangeTime);
                List<CustomerRecord> customerRecords2 = manOrder2(vip, masterCustomerVipMappings, resvOrdersMap, lastChangeTime);
                List<CustomerRecord> customerRecords3 = guestOrder2(vip, guestCustomerVipMappings, resvOrdersMap, lastChangeTime);
                CustomerRecord valueChangeRecord = valueChange(vip, oldCustomerRecord.get(vip.getId()), configTaskExec, customerValueTask);
                CustomerRecord userChangeRecord = appUserChange2(vip, customerRecordAppUser, appUserList, customerValueTask);

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

                map.put(vip.getId(), recordList);
            }catch (Exception e){
                log.error(e.getMessage(),e);
            }

        }

         return map;
    }

    public Map<Integer, CustomerRecord> getOldCustomerRecord(List<Vip> vips) {
        List<Integer> vipIdList = getVipIdList(vips);
        Wrapper<CustomerRecord> wrapper = new EntityWrapper<>();
        wrapper.eq("vip_id", vipIdList);
        wrapper.eq("log_type", 5);
        List<CustomerRecord> customerRecordList = customerRecordMapper.selectList(wrapper);
        Map<Integer, List<CustomerRecord>> customerRecordMap = customerRecordList.stream()
                .collect(Collectors.groupingBy(CustomerRecord::getVipId));

        Map<Integer, CustomerRecord> resultMap = new HashMap<>();
        for (Map.Entry<Integer, List<CustomerRecord>> entry : customerRecordMap.entrySet()) {
            Integer key = entry.getKey();
            List<CustomerRecord> value = entry.getValue();
            Optional<CustomerRecord> maxCustomerRecord = value.stream()
                    .filter(Objects::nonNull)
                    .filter(customerRecord -> Objects.nonNull(customerRecord.getUpdateTime()))
                    .max(Comparator.comparing(CustomerRecord::getUpdateTime));

            resultMap.put(key, maxCustomerRecord.orElse(new CustomerRecord()));
        }

        return resultMap;
    }

    private LocalDateTime getLastChangeTime(Map<Integer, List<CustomerRecord>> nowChangeOrderInfo, Vip vip) {
        if (nowChangeOrderInfo != null) {
            if (vip == null) {
                return null;
            }
            Integer id = vip.getId();
            if (id == null) {
                return null;
            }
            List<CustomerRecord> recordList = nowChangeOrderInfo.get(id);
            if (CollectionUtils.isEmpty(recordList)) {
                return null;
            }
            Optional<CustomerRecord> max = recordList.stream()
                    .max(Comparator.comparing(CustomerRecord::getUpdateTime));
            CustomerRecord customerRecord = max.orElse(new CustomerRecord());
            LocalDateTime updateTime = customerRecord.getUpdateTime();
            if (updateTime == null) {
                return null;
            }
            return LocalDateTime.of(updateTime.toLocalDate(), LocalTime.MAX);
        }
        return null;
    }

    /**
     *
     * @param vips vip 列表
     * @return
     */
    private Map<Integer, List<CustomerRecord>> getNowChangeOrderInfo(List<Vip> vips) {
        List<Integer> vipIdList = getVipIdList(vips);
        Wrapper<CustomerRecord> wrapper = new EntityWrapper<>();
        wrapper.in("vip_id", vipIdList.toArray());
        wrapper.in("log_type", Arrays.asList(1, 2, 3, 4));

        List<CustomerRecord> recordList = customerRecordMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(recordList)) {
            return Maps.newHashMap();
        }
        return recordList.stream()
                .collect(Collectors.groupingBy(CustomerRecord::getVipId));
    }

    /**
     *
     * @param vips vip 列表
     * @return
     */
    private Map<Integer, CustomerRecord> getNowChangeAppUser(List<Vip> vips) {
        List<Integer> vipIdList = getVipIdList(vips);
        Wrapper<CustomerRecord> wrapper = new EntityWrapper<>();
        wrapper.in("vip_id", vipIdList.toArray());
        wrapper.eq("log_type", 6);

        List<CustomerRecord> recordList = customerRecordMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(recordList)) {
            return Maps.newHashMap();
        }

        Map<Integer, List<CustomerRecord>> customerRecordMap = recordList.stream()
                .collect(Collectors.groupingBy(CustomerRecord::getVipId));
        Map<Integer, CustomerRecord> resultMap = new HashMap<>();
        for (Map.Entry<Integer, List<CustomerRecord>> entry : customerRecordMap.entrySet()) {
            Integer key = entry.getKey();
            List<CustomerRecord> value = entry.getValue();
            Optional<CustomerRecord> maxCustomerRecord = value.stream()
                    .filter(Objects::nonNull)
                    .filter(customerRecord -> Objects.nonNull(customerRecord.getUpdateTime()))
                    .max(Comparator.comparing(CustomerRecord::getUpdateTime));
            resultMap.put(key, maxCustomerRecord.orElse(new CustomerRecord()));
        }

        return resultMap;
    }

    private List<Integer> getVipIdList(List<Vip> vips) {
        List<Integer> vipIdList = vips.stream()
                .filter(Objects::nonNull)
                .map(Vip::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return vipIdList;
    }

    private List<Integer> getAppUser(List<Vip> vips) {
        return vips.stream()
                .filter(Objects::nonNull)
                .map(Vip::getId)
                .collect(Collectors.toList());
    }

    /**
     * 清除指定 vip 的定时任务跑的当天的数据
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cleanData(List<Vip> vipList, ConfigTaskExec configTaskExec) {
        LocalTime taskStartTime = configTaskExec.getStartTime();
        LocalTime taskEndTime = configTaskExec.getEndTime();
        if (CollectionUtils.isEmpty(vipList)) {
            return;
        }
        List<Integer> filterVipList = vipList.stream()
                .map(Vip::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Wrapper<CustomerRecord> wrapper = new EntityWrapper<>();
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        LocalDateTime startDateTime;
        LocalDateTime endDateTime;
        // 没有超过 00 点，结束时间 + 1 天，超过了，开始时间 -1 天
        if (nowTime.isAfter(taskStartTime) && nowTime.isBefore(LocalTime.MAX)) {
            startDateTime = LocalDateTime.of(nowDate, taskStartTime);
            endDateTime = LocalDateTime.of(nowDate.plusDays(1), taskEndTime);
        } else {
            startDateTime = LocalDateTime.of(nowDate.minusDays(1), taskStartTime);
            endDateTime = LocalDateTime.of(nowDate, taskEndTime);
        }
        wrapper.in("vip_id", filterVipList);
        wrapper.ge("create_time", startDateTime);
        wrapper.le("create_time", endDateTime);
        customerRecordMapper.delete(wrapper);
    }


    /**
     * 预订定单 - 消费订单
     *
     * @param resvOrders 订单列表
     */
    private List<CustomerRecord> reserveOrderCustomer(Vip vip, List<ResvOrder> resvOrders, LocalDateTime changeDate) {
        if (CollectionUtils.isEmpty(resvOrders)){
            return new ArrayList<>();
        }

        List<CustomerRecord> recordList = resvOrders.stream()
                .filter(order -> isLastOrder(changeDate, order))
                .filter(order -> "2".equals(order.getStatus()) || "3".equals(order.getStatus()))
                .map(order -> setCustomerRecord(order, vip, CustomerValueConstants.RECORD_TYPE_CUSTOMER))
                .collect(Collectors.toList());
        // log.info("vipId:[{}],消费订单数量：[{}]", vip.getId(), resvOrders.size());
        return recordList;
    }

    private boolean isLastOrder(LocalDateTime changeDate, ResvOrder order) {
        if (changeDate == null) {
            return true;
        } else {
            if (order == null) {
                return false;
            }
            Date updatedAt = order.getUpdatedAt();
            if (updatedAt == null) {
                return false;
            }
            LocalDateTime localDateTime = getLocalDateTime(updatedAt);
            return localDateTime.isAfter(changeDate);
        }
    }

    private LocalDateTime getLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        Instant instant = date.toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }


    private CustomerRecord setMasterOrGuestRecordOrder(Integer vipId, ResvOrder order, int type) {
        CustomerRecord record = new CustomerRecord();
        record.setVipId(vipId);
        Date updatedAt = order.getUpdatedAt();
        record.setLogType(type);
        Date resvDate = order.getResvDate();
        LocalDateTime resvDateLocalDateTime = getLocalDateTime(resvDate);
        record.setLogTime(resvDateLocalDateTime);
        record.setResvOrder(order.getResvOrder());
        record.setResvDate(resvDateLocalDateTime);
        record.setMealTypeId(order.getMealTypeId());
        record.setMealTypeName(order.getMealTypeName());
        Integer payAmount = getPayAmount(order);
        record.setConsumeAmount(payAmount);
        int resvNo = getResvNo(order);
        record.setPersonNo(resvNo);
        record.setTableId(order.getTableId());
        record.setTableName(order.getTableName());
        record.setVipName(order.getVipName());
        String vipPhone = order.getVipPhone();
        record.setVipPhone(PhoneUtils.getPhone(vipPhone));
        record.setAppUserName(order.getAppUserName());
        record.setAppUserId(order.getAppUserId());
        String appUserPhone = order.getAppUserPhone();
        record.setAppUserPhone(PhoneUtils.getPhone(appUserPhone));
        record.setOperationLog(StringUtils.EMPTY);
        record.setCreateUserId(0L);
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateUserId(0L);
        record.setUpdateTime(LocalDateTime.now());
        return record;
    }

//    private CustomerRecord getCustomerRecord(ResvOrder order, int type, CustomerRecord record) {
//        record.setLogType(type);
//        if (type == CustomerValueConstants.RECORD_TYPE_CUSTOMER || type == CustomerValueConstants.RECORD_TYPE_ESC) {
//            LocalDateTime logTime = getLocalDateTime(order.getUpdatedAt());
//            record.setLogTime(logTime);
//        } else if (type == CustomerValueConstants.RECORD_TYPE_MAN) {
//
//        }
//        record.setResvOrder(order.getBatchNo());
//        Date updatedAt = order.getUpdatedAt();
//        LocalDateTime resvDate;
//        if (updatedAt != null) {
//            Instant instant = updatedAt.toInstant();
//            resvDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
//        } else {
//            resvDate = CustomerValueConstants.DEFAULT_START_TIME;
//        }
//        record.setResvDate(resvDate);
//        record.setMealTypeId(order.getMealTypeId());
//        record.setMealTypeName(order.getMealTypeName().trim());
//        String payamount = order.getPayamount();
//        if (StringUtils.isNotBlank(payamount)) {
//            double v = Double.parseDouble(payamount);
//            int amount = (int) (v * 100);
//            record.setConsumeAmount(amount);
//        } else {
//            record.setConsumeAmount(0);
//        }
//        String resvNum = order.getResvNum();
//        int resvNo = 0;
//        if (NumberUtils.isCreatable(resvNum)) {
//            resvNo = Integer.parseInt(resvNum);
//        } else {
//            String actualNum = order.getActualNum();
//            if (NumberUtils.isCreatable(actualNum)) {
//                resvNo = Integer.parseInt(actualNum);
//            }
//        }
//        record.setPersonNo(resvNo);
//        record.setTableId(order.getTableId());
//        record.setTableName(order.getTableName());
//        record.setVipName(order.getVipName());
//        String vipPhone = order.getVipPhone();
//        if (NumberUtils.isCreatable(vipPhone) && vipPhone.length() == 11) {
//            record.setVipPhone(vipPhone);
//        } else {
//            log.error("手机号异常:[{}],订单号 [{}]", vipPhone, order.getId());
//        }
//        record.setAppUserName(order.getAppUserName());
//        record.setAppUserId(order.getAppUserId());
//        record.setAppUserPhone(order.getAppUserPhone());
//        record.setOperationLog(StringUtils.EMPTY);
//        record.setCreateUserId(CustomerValueConstants.DEFAULT_USER_ID);
//        record.setCreateTime(LocalDateTime.now());
//        record.setUpdateUserId(CustomerValueConstants.DEFAULT_USER_ID);
//        record.setUpdateTime(LocalDateTime.now());
//        return record;
//    }

    /**
     * 预订定单-退订订单
     *
     * @param resvOrders 预订订单
     */
    private List<CustomerRecord> reserveOrderESC(Vip vip, List<ResvOrder> resvOrders, LocalDateTime nowChange) {
        if (CollectionUtils.isEmpty(resvOrders)){
            return new ArrayList<>();
        }
        List<CustomerRecord> recordList = resvOrders.stream()
                .filter(order -> isLastOrder(nowChange, order))
                .filter(order -> "4".equals(order.getStatus()))
                .map(order -> setCustomerRecordESC(order, vip, CustomerValueConstants.RECORD_TYPE_ESC))
                .collect(Collectors.toList());

        return recordList;
    }

    /**
     * 主客订单
     */
    private List<CustomerRecord> manOrder(Vip vip, List<ResvOrder> resvOrders) {
        if (vip == null || vip.getId() == null) {
            log.error("vip 信息为空");
            return Lists.newArrayList();
        }
        if (CollectionUtils.isEmpty(resvOrders)) {
            log.info("订单信息不存在:{}", vip.getId());
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
                .map(order -> setMasterOrGuestRecordOrder(masterCustomerVipMapping.getId(), order, CustomerValueConstants.RECORD_TYPE_MAN))
                .collect(Collectors.toList());

        // 主客订单列表
        return collect;
    }
    /**
     * 主客订单
     */
    public List<CustomerRecord> manOrder2(Vip vip, List<MasterCustomerVipMapping> masterCustomerVipMappingList, Map<Integer, List<ResvOrder>> resvOrdersMap, LocalDateTime nowChangeTime) {
        boolean checkParam = checkParam(vip, masterCustomerVipMappingList, resvOrdersMap);
        if (!checkParam) {
            return Lists.newArrayList();
        }

        List<ResvOrder> resvOrderList = resvOrdersMap.get(vip.getId());
        List<ResvOrder> orderList = resvOrderList.stream()
                .filter(order -> isLastOrder(nowChangeTime, order))
                .collect(Collectors.toList());

        List<CustomerRecord> recordList = new ArrayList<>();
        for (ResvOrder resvOrder : orderList) {
            for (MasterCustomerVipMapping vipMapping : masterCustomerVipMappingList) {
                Integer masterCustomerId = vipMapping.getMasterCustomerId();
                String batchNo = vipMapping.getBatchNo();
                if (resvOrder.getBatchNo().equals(batchNo)) {
                    CustomerRecord record = setMasterOrGuestRecordOrder(masterCustomerId, resvOrder, CustomerValueConstants.RECORD_TYPE_MAN);
                    recordList.add(record);
                }
            }
        }

        return recordList;
    }

    /**
     * 参数检查
     * @param vip
     * @param masterCustomerVipMappingList
     * @param resvOrdersMap
     * @return false 参数有错误，true 参数正确
     */
    private boolean checkParam(Vip vip, List<MasterCustomerVipMapping> masterCustomerVipMappingList, Map<Integer, List<ResvOrder>> resvOrdersMap) {
        if (vip == null) {
            return false;
        }
        if (CollectionUtils.isEmpty(masterCustomerVipMappingList)) {
            return false;
        }
        if (MapUtils.isEmpty(resvOrdersMap)) {
            return false;
        }

        Integer id = vip.getId();
        if (id == null) {
            return false;
        }

        List<ResvOrder> resvOrderList = resvOrdersMap.get(id);
        if (CollectionUtils.isEmpty(resvOrderList)) {
            return false;
        }

        return true;
    }

    /**
     * 主客订单
     */
    public List<ResvOrder> getManOrderList(Vip vip, List<MasterCustomerVipMapping> masterCustomerVipMappingList, Map<Integer, List<ResvOrder>> resvOrdersMap) {
        if (vip == null) {
            return Lists.newArrayList();
        }
        if (CollectionUtils.isEmpty(masterCustomerVipMappingList)) {
            return Lists.newArrayList();
        }
        if (MapUtils.isEmpty(resvOrdersMap)) {
            return Lists.newArrayList();
        }

        Integer id = vip.getId();
        if (id == null) {
            return Lists.newArrayList();
        }

//        List<ResvOrder> resvOrderList = resvOrdersMap.get(id);
//        if (CollectionUtils.isEmpty(resvOrderList)) {
//            return Lists.newArrayList();
//        }
        List<ResvOrder> recordList = new ArrayList<>();
        resvOrdersMap.forEach((k, v) -> {
            for (ResvOrder resvOrder : v) {
                for (MasterCustomerVipMapping vipMapping : masterCustomerVipMappingList) {
                    Integer masterCustomerId = vipMapping.getMasterCustomerId();
                    String batchNo = vipMapping.getBatchNo();
                    if (resvOrder.getBatchNo().equals(batchNo)) {
                        // 将这条订单变更为主客所属的订单
                        // resvOrder.setVipId(masterCustomerId);
                        recordList.add(resvOrder);
                    }
                }
            }
        });


        return recordList;
    }

    public List<MasterCustomerVipMapping> manOrderList(List<Vip> vipList) {
        if (CollectionUtils.isEmpty(vipList)) {
            return Lists.newArrayList();
        }

        Object[] vipIds = vipList.stream()
                .filter(Objects::nonNull)
                .map(Vip::getId)
                .toArray();

        Wrapper<MasterCustomerVipMapping> wrapper = new EntityWrapper<>();
        wrapper.in("master_customer_id", vipIds);
        List<MasterCustomerVipMapping> vipMappings = iMasterCustomerVipMappingService.selectList(wrapper);
        if (CollectionUtils.isEmpty(vipMappings)) {
            return Lists.newArrayList();
        }
        return vipMappings;
    }

    public List<GuestCustomerVipMapping> guestOrderList(List<Vip> vipList) {
        if (CollectionUtils.isEmpty(vipList)) {
            return Lists.newArrayList();
        }

        Object[] vipIds = vipList.stream()
                .filter(Objects::nonNull)
                .map(Vip::getId)
                .toArray();

        Wrapper<GuestCustomerVipMapping> wrapper = new EntityWrapper<>();
        wrapper.in("guest_customer_id", vipIds);
        List<GuestCustomerVipMapping> vipMappings = iGuestCustomerVipMappingService.selectList(wrapper);
        if (CollectionUtils.isEmpty(vipMappings)) {
            return Lists.newArrayList();
        }
        return vipMappings;
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
                    CustomerRecord record = setMasterOrGuestRecordOrder(guestCustomerId, otherResvOrder, CustomerValueConstants.RECORD_TYPE_GUEST);
                    recordList.add(record);
                }
            }
        }

        return recordList;
    }
    /**
     * 宾客订单
     */
    private List<CustomerRecord> guestOrder2(Vip vip, List<GuestCustomerVipMapping> guestCustomerVipMappingList,  Map<Integer, List<ResvOrder>> resvOrdersMap,
                                             LocalDateTime nowChangeTime) {
        if (vip == null) {
            return Lists.newArrayList();
        }
        if (CollectionUtils.isEmpty(guestCustomerVipMappingList)) {
            return Lists.newArrayList();
        }
        if (MapUtils.isEmpty(resvOrdersMap)) {
            return Lists.newArrayList();
        }

        Integer id = vip.getId();
        if (id == null) {
            return Lists.newArrayList();
        }


        List<ResvOrder> resvOrderList = resvOrdersMap.get(id);
        if (CollectionUtils.isEmpty(resvOrderList)) {
            return Lists.newArrayList();
        }
        List<ResvOrder> orderList = resvOrderList.stream()
                .filter(order -> isLastOrder(nowChangeTime, order))
                .collect(Collectors.toList());

        List<CustomerRecord> recordList = new ArrayList<>();
        for (ResvOrder resvOrder : orderList) {
            for (GuestCustomerVipMapping vipMapping : guestCustomerVipMappingList) {
                if (vipMapping == null) {
                    return Lists.newArrayList();
                }
                Integer guestCustomerId = vipMapping.getGuestCustomerId();
                if (guestCustomerId == null) {
                    return Lists.newArrayList();
                }
                String batchNo = vipMapping.getBatchNo();
                if (resvOrder.getBatchNo().equals(batchNo)) {
                    CustomerRecord record = setMasterOrGuestRecordOrder(guestCustomerId, resvOrder, CustomerValueConstants.RECORD_TYPE_GUEST);
                    recordList.add(record);
                }
            }
        }

        // 主客订单列表
        return recordList;
    }

    /**
     * 价值变更，之前的价值变更与现在的价值进行对比
     */
    private CustomerRecord valueChange(Vip vip, CustomerRecord customerRecord, ConfigTaskExec configTaskExec, CustomerValueTask customerValueTask) {
        LocalTime taskStartTime = configTaskExec.getStartTime();
        // 1-意向客户，2-活跃客户，3-沉睡客户，4-流失客户
        // 1活跃用户 2沉睡用户 3流失用户 4意向用户 5恶意用户 6高价值用户
        String customerValue = getCustomerValue(vip);
        Integer firstClassValue;
        if (customerRecord != null) {
            firstClassValue = customerRecord.getNewFirstValue();
        } else {
            firstClassValue = 1;
        }
        String value = getFirstClassValueStr(firstClassValue);

        customerValue = customerValue == null ? "" : customerValue;
        value = value == null ? "" : value;

        // 沉睡客户，沉睡用户，只匹配前面两个字是否一样
        String customerValueNameS = customerValue;
        if (StringUtils.isNotBlank(customerValue) && customerValue.length() >= 2) {
            customerValueNameS = customerValue.substring(0, 2);
        }
        String lastValue = value;
        if (StringUtils.isNotBlank(value) && value.length() >= 2) {
            lastValue = value.substring(0, 2);
        }
        if (customerRecord != null && !customerValueNameS.equals(lastValue)) {
            CustomerRecord record = new CustomerRecord();
            record.setVipId(customerRecord.getVipId());
            record.setLogType(CustomerValueConstants.RECORD_TYPE_VALUE_CHANGE);
            // 取计划开始时间-1做为价值变更时间
            LocalDate planTime = customerValueTask.getPlanTime();
            LocalDateTime logTime = LocalDateTime.of(planTime.minusDays(1), LocalTime.MIN);
            record.setLogTime(logTime);
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
            String customerValueName = getCustomerValueName(vip);
            record.setOperationLog("由" + customerValueName + "变更为" + value);
            record.setCreateUserId(CustomerValueConstants.DEFAULT_USER_ID);
            record.setCreateTime(LocalDateTime.now());
            record.setUpdateUserId(CustomerValueConstants.DEFAULT_USER_ID);
            record.setUpdateTime(LocalDateTime.now());
            return record;
        }
        return null;
    }

    private String getCustomerValueName(Vip vip) {
        String vipValueName = vip.getVipValueName();
        if (StringUtils.isNotBlank(vipValueName)) {
            return vipValueName;
        }
        log.error("vipValueName 信息为 null, Vip ID 为：{} ", vip.getId());
        return StringUtils.EMPTY;
    }

    private String getFirstClassValueStr(Integer firstClassValue) {
        // 1-意向客户，2-活跃客户，3-沉睡客户，4-流失客户
        if (firstClassValue.equals(1)) {
            return "意向客户";
        } else if (firstClassValue.equals(2)) {
            return "活跃客户";
        } else if (firstClassValue.equals(3)) {
            return "沉睡客户";
        } else if (firstClassValue.equals(4)) {
            return "流失客户";
        }
        log.error("客户一级价值错误:[{}]", firstClassValue);
        return StringUtils.EMPTY;

    }

    /**
     * 客户一级价值
     *
     * @param vip 客户信息
     * @return
     */
    private String getCustomerValue(Vip vip) {
        String vipName = vip.getVipValueName();
        Optional<String> optionalVipName = Optional.ofNullable(vipName);
        return optionalVipName.orElse(StringUtils.EMPTY);
    }


    /**
     * 营销经理变更
     */
//    private CustomerRecord appUserChange(Vip vip, CustomerValueList customerValueList) {
//        Integer appUserId = getAppUserId(vip);
//        if (appUserId < 1) {
//            return null;
//        }
//
//        CustomerRecord customerRecord = getCustomerRecord(vip.getId());
//        Integer changeAppUserId = customerRecord.getAppUserId();
//
//        if (!appUserId.equals(changeAppUserId)) {
//            AppUser appUser = businessCustomerAnalysisInfoService.getAppUser(appUserId);
//            return setCustomerRecord(customerValueList, appUserId, appUser);
//        }
//
//        return null;
//    }

    /**
     * 营销经理变更
     * @param vip 当前 vip
     * @param customerRecord 最后一条客户记录，其中记录了最后一次经销经理
     * @param appUserList 所有的营销经理列表
     * @return CustomerRecord
     */
    private CustomerRecord appUserChange2(Vip vip, CustomerRecord customerRecord, List<AppUser> appUserList, CustomerValueTask customerValueTask) {
        if (vip == null) {
            return null;
        }
        if (vip.getId() == null) {
            return null;
        }
        Integer appUserId = getAppUserId(vip);
        if (appUserId < 1) {
            return null;
        }
        if (customerRecord == null) {
            return null;
        }
        Integer changeAppUserId = customerRecord.getAppUserId();
        for (AppUser appUser : appUserList) {
            if (!appUserId.equals(changeAppUserId)) {
                return setAppUserCustomerRecord(vip, appUserId, appUser, customerValueTask);
            }
        }


        return null;
    }

    /**
     * 设置营销经理变更的对象实体
     * @param vip
     * @param appUserId
     * @param appUser
     * @return CustomerRecord
     */
    private CustomerRecord setAppUserCustomerRecord(Vip vip, Integer appUserId, AppUser appUser, CustomerValueTask customerValueTask) {
        CustomerRecord customerRecord = new CustomerRecord();
        customerRecord.setVipId(vip.getId());
        customerRecord.setLogType(CustomerValueConstants.RECORD_TYPE_APP_USER_CHANGE);
        LocalDate planTime = customerValueTask.getPlanTime();
        LocalTime min = LocalTime.MIN;
        customerRecord.setLogTime(LocalDateTime.of(planTime.minusDays(1), min));
        customerRecord.setResvOrder(StringUtils.EMPTY);
        customerRecord.setResvDate(LocalDateTime.now());
        customerRecord.setMealTypeId(0);
        customerRecord.setMealTypeName(StringUtils.EMPTY);
        customerRecord.setConsumeAmount(0);
        customerRecord.setPersonNo(0);
        customerRecord.setTableId(0);
        customerRecord.setTableName(StringUtils.EMPTY);
        customerRecord.setVipName(StringUtils.EMPTY);
        customerRecord.setVipPhone(StringUtils.EMPTY);
        String appUserName = appUser.getAppUserName();
        Optional<String> optionalAppUser = Optional.ofNullable(appUserName);
        appUserName = optionalAppUser.orElse(StringUtils.EMPTY);
        customerRecord.setAppUserName(appUserName);
        customerRecord.setAppUserId(appUserId);
        String appUserPhone = appUser.getAppUserPhone();
        customerRecord.setAppUserPhone(Optional.ofNullable(appUserPhone).orElse(StringUtils.EMPTY));
        customerRecord.setOperationLog(StringUtils.EMPTY);
        customerRecord.setCreateUserId(0L);
        customerRecord.setCreateTime(LocalDateTime.now());
        customerRecord.setUpdateUserId(0L);
        customerRecord.setUpdateTime(LocalDateTime.now());

        return customerRecord;
    }

    // 设置消费订单 Record
    private CustomerRecord setCustomerRecord(ResvOrder resvOrder, Vip vip, int type) {
        CustomerRecord record = new CustomerRecord();
        record.setVipId(vip.getId());
        record.setLogType(type);
        Date resvDate = resvOrder.getResvDate();
        LocalDateTime resvLocalDateTime = getLocalDateTime(resvDate);
        record.setLogTime(resvLocalDateTime);
        record.setResvOrder(resvOrder.getResvOrder());
        // LocalDateTime resvDate = getLocalDateTime(resvDate);
        record.setResvDate(resvLocalDateTime);
        record.setMealTypeId(resvOrder.getMealTypeId());
        record.setMealTypeName(resvOrder.getMealTypeName());
        Integer payAmount = getPayAmount(resvOrder);
        record.setConsumeAmount(payAmount);
        int resvNo = getResvNo(resvOrder);
        record.setPersonNo(resvNo);
        record.setTableId(resvOrder.getTableId());
        record.setTableName(resvOrder.getTableName());
        record.setVipName(resvOrder.getVipName());
        String vipPhone = resvOrder.getVipPhone();
        record.setVipPhone(PhoneUtils.getPhone(vipPhone));
        record.setAppUserName(resvOrder.getAppUserName());
        record.setAppUserId(resvOrder.getAppUserId());
        record.setOperationLog(StringUtils.EMPTY);
        record.setCreateUserId(0L);
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateUserId(0L);
        record.setUpdateTime(LocalDateTime.now());
        return record;
    }

    /**
     * 退订订单
     * @param resvOrder
     * @param vip
     * @param type
     * @return
     */
    private CustomerRecord setCustomerRecordESC(ResvOrder resvOrder, Vip vip, int type) {
        CustomerRecord record = new CustomerRecord();
        record.setVipId(vip.getId());
        record.setLogType(type);
        Date resvDate = resvOrder.getResvDate();
        Date updatedAt = resvOrder.getUpdatedAt();
        LocalDateTime updateLocalDateTime = getLocalDateTime(updatedAt);
        LocalDateTime resvLocalDateTime = getLocalDateTime(resvDate);
        record.setLogTime(updateLocalDateTime);
        record.setResvOrder(resvOrder.getResvOrder());
        record.setResvDate(resvLocalDateTime);
        record.setMealTypeId(resvOrder.getMealTypeId());
        record.setMealTypeName(resvOrder.getMealTypeName());
        Integer payAmount = getPayAmount(resvOrder);
        record.setConsumeAmount(payAmount);
        int resvNo = getResvNo(resvOrder);
        record.setPersonNo(resvNo);
        record.setTableId(resvOrder.getTableId());
        record.setTableName(resvOrder.getTableName());
        record.setVipName(resvOrder.getVipName());
        String vipPhone = resvOrder.getVipPhone();
        record.setVipPhone(PhoneUtils.getPhone(vipPhone));
        record.setAppUserName(resvOrder.getAppUserName());
        record.setAppUserId(resvOrder.getAppUserId());
        record.setOperationLog(StringUtils.EMPTY);
        record.setCreateUserId(0L);
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateUserId(0L);
        record.setUpdateTime(LocalDateTime.now());
        return record;
    }

    private int getResvNo(ResvOrder resvOrder) {
        String resvNum = resvOrder.getResvNum();
        int resvNo = 0;
        if (NumberUtils.isCreatable(resvNum)) {
            try {
                resvNo = Integer.parseInt(resvNum);
            } catch (NumberFormatException e) {
                log.error("订单id:[{}] 用餐人数数据异常：[{}]", resvOrder.getId(), resvNum);
            }
        }
        if (resvNo == 0){
            String actualNum = resvOrder.getActualNum();
            if (NumberUtils.isCreatable(actualNum)) {
                try {
                    resvNo = Integer.parseInt(actualNum);
                } catch (NumberFormatException ignored) {
                    log.error("订单id:[{}] 预订人数数据异常：[{}]", resvOrder.getId(), actualNum);
                }
            }
        }
        return resvNo;
    }

    private Integer getPayAmount(ResvOrder resvOrder) {
        String payamount = resvOrder.getPayamount();
        if (StringUtils.isNotBlank(payamount) && NumberUtils.isCreatable(payamount)) {
            double v = Double.parseDouble(payamount);
            Number amount = MathUtils.multiply(v, 100);
            return Math.round(amount.floatValue());
        } else {
            return 0;
        }
    }

    private CustomerRecord getCustomerRecord(Integer vipId) {
        Wrapper<CustomerRecord> wrapper = new EntityWrapper<>();
        wrapper.eq("vip_id", vipId);
        List<CustomerRecord> customerRecords = customerRecordMapper.selectList(wrapper);
        Optional<CustomerRecord> max = customerRecords.stream()
                .max(Comparator.comparing(CustomerRecord::getUpdateTime));
        return max.orElse(new CustomerRecord());
    }

    private CustomerRecord getCustomerRecord(Vip vip, List<CustomerRecord> customerRecordList) {
        if (vip == null) {
            return null;
        }
        if (CollectionUtils.isEmpty(customerRecordList)) {
            return null;
        }
        Integer vipAppuserId = getAppUserId(vip);
        Optional<CustomerRecord> optionalCustomerRecord = customerRecordList.stream()
                .filter(appUser -> Objects.nonNull(appUser.getId()))
                .max(Comparator.comparing(CustomerRecord::getUpdateTime));
        return optionalCustomerRecord.orElse(new CustomerRecord());
    }

    /**
     * 指定 vipId 获取其当前的 营销经理
     *
     * @param vip vip
     * @return 营销经理对应的 app_user_id
     */
    private Integer getAppUserId(Vip vip) {
        Integer appUserId = vip.getAppUserId();
        Optional<Integer> optional = Optional.ofNullable(appUserId);
        return optional.orElse(-1);
    }


}
