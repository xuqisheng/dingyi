package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author sjl
 * 2019-05-29 10:43
 */

@Slf4j
@Service
public class BusinessCustomerAnalysisInfoService {

//    1:活跃用户  2:沉睡用户  3:流失用户  4:意向用户   5:恶意用户  6:高价值用户  7:唤醒   8:新用户

    @Autowired
    private IBusinessCustomerAnalysisInfoService businessCustomerAnalysisInfoMapper;

    @Autowired
    private VipService vipService;

    @Autowired
    private IBusinessCustomerAnalysisDetailService businessCustomerAnalysisDetailMapper;

    @Autowired
    private IResvOrderService orderService;

    @Autowired
    private IAppUserService appuserMapper;

    @Autowired
    private IBusinessService iBusinessService;

    @Autowired
    private IBusinessCustomerAnalysisService iBusinessCustomerAnalysisService;

    /**
     * 执行入口
     */
    public void execute() {
        final DateTimeFormatter formatterShort = DateTimeFormatter.ofPattern("yyyy-MM");
        final DateTimeFormatter formatterLong = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 0. 查询出所有的酒店
        // 取出所有的酒店 id
        Wrapper<Business> wrapper = new EntityWrapper<>();
        List<Business> businessList = iBusinessService.selectList(wrapper);
        List<Integer> businessIdList = getBusinessIdList(businessList);

        // 1. 批量查询 info 表中的最后一个月的记录
        Map<Integer, BusinessCustomerAnalysisInfo> businessCustomerAnalysisInfoMap = getBusinessCustomerAnalysisInfo(businessIdList);

        // 2. info 表为空，则取全部数据
        List<CompletableFuture> completableFutureList = new ArrayList<>();
        for (Map.Entry<Integer, BusinessCustomerAnalysisInfo> entry: businessCustomerAnalysisInfoMap.entrySet()) {
            Integer businessId = entry.getKey();
            log.info("----------------------客户分析任务开始 businessId:[{}], 开始时间:[{}]----------------------", businessId, DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now()));
            BusinessCustomerAnalysisInfo businessCustomerAnalysisInfo = entry.getValue();
            String date = businessCustomerAnalysisInfo.getDate();
            LocalDate nowDate = LocalDate.now();
            if (StringUtils.isBlank(date)) {
                cleanData(businessId);
                LocalDate oldDate = LocalDate.of(2018, 10, 1);
                int month = (int) oldDate.until(nowDate, ChronoUnit.MONTHS);
                for (int i = 0; i < month; i++) {
                    int finalI = i;
                    CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
                        LocalDate newDate = oldDate.plusMonths(finalI);
                        String resvDate = newDate.format(formatterShort);
                        saveAnalysisDetail(businessId, resvDate);
                        return finalI;
                    });
                    // completableFuture.join(); // 不需要获取执行结果，只需要线程在后台插入数据即可
                }
            } else {
                //2.1 info 表不空，则取最后的日期 + 1 个月
                LocalDate localDate = getLocalDate(date);
                Optional<LocalDate> optionalLocalDate = Optional.ofNullable(localDate);
                optionalLocalDate.ifPresent((date1) -> {
                    int year = date1.getYear();
                    int dayOfMonth = date1.getDayOfMonth();
                    int year1 = nowDate.getYear();
                    int dayOfMonth1 = nowDate.getDayOfMonth();
                    if (year == year1 && dayOfMonth == dayOfMonth1) {
                        return;
                    }
                    long until = date1.until(nowDate, ChronoUnit.MONTHS);
                    for (int i = 0; i < until; i++) {
                        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
                            LocalDate plusDate = localDate.plusMonths(1);
                            String resvDate = plusDate.format(formatterShort);
                            cleanData(businessId, resvDate);
                            saveAnalysisDetail(businessId, resvDate);
                            return 1;
                        });
                        // completableFuture.join();
                    }
                });

            }
            log.info("----------------------客户分析任务完成 businessId:[{}], 结束时间:[{}]----------------------", businessId, DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now()));
        }
    }

    private void cleanData(Integer businessId) {
        cleanData(businessId, null);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cleanData(Integer businessId, String resvDate) {
        Wrapper<BusinessCustomerAnalysisInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("business_id", businessId);
        if (StringUtils.isNotBlank(resvDate)) {
            wrapper.eq("date", resvDate);
        }
        businessCustomerAnalysisInfoMapper.delete(wrapper);
    }

    private LocalDate getLocalDate(String date) {
        try {
            if (StringUtils.isNotBlank(date)) {
                date = date + "-01";
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(date, formatter);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private Map<Integer, LocalDate> getBusinessCustomerAnalysis(List<Integer> businessIdList) {
        if (CollectionUtils.isEmpty(businessIdList)) {
            return Maps.newHashMap();
        }
        Map<Integer, LocalDate> resultMap = new HashMap<>(businessIdList.size());
        Wrapper<BusinessCustomerAnalysis> wrapper = new EntityWrapper<>();
        wrapper.in("business_id", businessIdList);
        List<BusinessCustomerAnalysis> businessCustomerAnalysisList = iBusinessCustomerAnalysisService.selectList(wrapper);

        for (Integer businessId : businessIdList) {
            for (BusinessCustomerAnalysis businessCustomerAnalysis : businessCustomerAnalysisList) {
                Integer analysisBusinessId = businessCustomerAnalysis.getBusinessId();
                if (businessId.equals(analysisBusinessId)) {
                    String date = businessCustomerAnalysis.getDate();
                    if (StringUtils.isNotBlank(date)) {
                        date = date + "-01";
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate localDate = LocalDate.parse(date, formatter);
                        resultMap.put(businessId, localDate);
                    }
                }
            }
            resultMap.put(businessId, LocalDate.MIN);
        }

        return resultMap;
    }

    /**
     * 查询最大时间的一条条记录
     * @param businessIdList
     * @return Map<Integer, BusinessCustomerAnalysisInfo>， k->businessId v->BusinessCustomerAnalysisInfo 最后一条记录
     */
    private Map<Integer, BusinessCustomerAnalysisInfo> getBusinessCustomerAnalysisInfo(List<Integer> businessIdList) {
        if (CollectionUtils.isEmpty(businessIdList)) {
            return Maps.newHashMap();
        }
        Wrapper<BusinessCustomerAnalysisInfo> wrapper = new EntityWrapper<>();
        wrapper.in("business_id", businessIdList);
        wrapper.orderBy("date", false);
        wrapper.last("limit 1");
        Map<Integer, BusinessCustomerAnalysisInfo> resultMap = new HashMap<>(businessIdList.size());
        List<BusinessCustomerAnalysisInfo> businessCustomerAnalysisInfos = businessCustomerAnalysisInfoMapper.selectList(wrapper);
        for (Integer businessId : businessIdList) {
            for (BusinessCustomerAnalysisInfo businessCustomerAnalysisInfo : businessCustomerAnalysisInfos) {
                Integer analysisInfoBusinessId = businessCustomerAnalysisInfo.getBusinessId();
                if (businessId.equals(analysisInfoBusinessId)) {
                    resultMap.put(businessId, businessCustomerAnalysisInfo);
                    break;
                }
            }
            resultMap.put(businessId, new BusinessCustomerAnalysisInfo());
        }

        return resultMap;
    }

    private BusinessCustomerAnalysisInfo getBusinessCustomerAnalysisInfo(Integer businessId) {
        Wrapper<BusinessCustomerAnalysisInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("business_id", businessId);
        wrapper.orderBy("date", false);
        wrapper.last("limit 1");
        BusinessCustomerAnalysisInfo businessCustomerAnalysisInfo = businessCustomerAnalysisInfoMapper.selectOne(wrapper);
        return businessCustomerAnalysisInfo;
    }

    private Map<Integer, BusinessCustomerAnalysisDetail> getMaxdateBusinessCustomerAnalysisDetailMap(Map<Integer, List<BusinessCustomerAnalysisDetail>> businessCustomerAnalysisDetailMap) {
        Map<Integer, BusinessCustomerAnalysisDetail> resultMap = new HashMap<>(businessCustomerAnalysisDetailMap.size());
        for (Map.Entry<Integer, List<BusinessCustomerAnalysisDetail>> detail : businessCustomerAnalysisDetailMap.entrySet()) {
            Integer businessId = detail.getKey();
            List<BusinessCustomerAnalysisDetail> detailList = detail.getValue();
            // 倒序排序，时间最大的在第一个
            detailList.sort(Comparator.comparing((BusinessCustomerAnalysisDetail businessCustomerAnalysisDetail) -> {
                String date = businessCustomerAnalysisDetail.getDate() + "-01";
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(date, formatter);
            }).reversed());
            BusinessCustomerAnalysisDetail businessCustomerAnalysisDetail;
            if (CollectionUtils.isNotEmpty(detailList)) {
                businessCustomerAnalysisDetail = detailList.get(0);
            } else {
                businessCustomerAnalysisDetail = new BusinessCustomerAnalysisDetail();
            }
            resultMap.put(businessId, businessCustomerAnalysisDetail);
        }
        return resultMap;
    }


    private Map<Integer, List<BusinessCustomerAnalysisDetail>> groupByBusinessId(List<Integer> businessIdList,
                                                                           List<BusinessCustomerAnalysisDetail> businessCustomerAnalysisDetailList) {
        Map<Integer, List<BusinessCustomerAnalysisDetail>> resultMap = new HashMap<>(businessIdList.size());
        List<BusinessCustomerAnalysisDetail> list;
        for (Integer businessId : businessIdList) {
            list = new ArrayList<>();
            for (BusinessCustomerAnalysisDetail detail : businessCustomerAnalysisDetailList) {
                Integer detailBusinessId = detail.getBusinessId();
                if (businessId.equals(detailBusinessId)) {
                    list.add(detail);
                }
            }
            resultMap.put(businessId, list);
        }
        return resultMap;
    }

    /**
     * 查询一批 指定businessId 的数据
     */
    private List<BusinessCustomerAnalysisDetail> getBusinessCustomerAnalysisList(Integer businessId) {
        if (businessId == null) {
            return Lists.newArrayList();
        }
        Wrapper<BusinessCustomerAnalysisDetail> wrapper = new EntityWrapper<>();
        wrapper.eq("business_id", businessId);
        return businessCustomerAnalysisDetailMapper.selectList(wrapper);
    }

    /**
     * 筛选 business 中的 id 作为列表
     */
    private List<Integer> getBusinessIdList(List<Business> businessList) {
        if (CollectionUtils.isEmpty(businessList)) {
            return Lists.newArrayList();
        }
        return businessList.stream()
                .map(Business::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * 保存具体的名单数据
     * @param resvDate 执行任务跑的数据，yyyy-MM
     */
    public void saveAnalysisDetail(Integer businessId, String resvDate) {
        if (!checkParam(resvDate)) {
            return;
        }
        Wrapper<BusinessCustomerAnalysisDetail> wrapper = new EntityWrapper<>();
        wrapper.eq("date", resvDate);
        wrapper.eq("business_id", businessId);

        List<BusinessCustomerAnalysisDetail> detailList = businessCustomerAnalysisDetailMapper.selectList(wrapper);

        List<Integer> vipIdList = detailList.stream()
                .map(BusinessCustomerAnalysisDetail::getVipId)
                .collect(Collectors.toList());
        List<Vip> vipList = vipService.getVipList(vipIdList);
        Map<Integer, Vip> vipMap = vipList.stream()
                .collect(Collectors.toMap(Vip::getId, vip -> vip));

        List<BusinessCustomerAnalysisInfo> customerAnalysisInfoList = detailList.stream()
                .parallel()
                .map(detail -> {
                    String date = detail.getDate();
                    Integer vipId = detail.getVipId();
                    String vipName = detail.getVipName();
                    String vipPhone = detail.getVipPhone();
                    Integer vipValueType = detail.getVipValueType();
                    Vip vip = vipMap.get(vipId);
                    String vipSex = getVipSex(vip);
                    List<ResvOrder> resvOrders = getResvOrder(vipId);
                    Optional<Vip> optionalVip = Optional.ofNullable(vip);
                    vip = optionalVip.orElse(new Vip());
                    Integer appUserId = vip.getAppUserId();
                    AppUser appUser = getAppUser(appUserId);
                    String appUserName = getAppUserName(appUser);
                    String appUserPhone = getAppUserPhone(appUser);
                    Integer currentAppUserId = getCurrentAppUserId(vipId, resvOrders);
                    String currentAppUserName = getCurrentAppUserName(vipId, resvOrders);
                    String currentAppUserPhone = getCurrentAppUserPhone(vipId, resvOrders);

                    BusinessCustomerAnalysisInfo info = new BusinessCustomerAnalysisInfo();
                    info.setBusinessId(businessId);
                    info.setVipId(vipId);
                    info.setVipName(vipName);
                    info.setVipSex(vipSex);
                    info.setVipPhone(vipPhone);
                    info.setAppUserId(appUserId);
                    info.setVipValueType(vipValueType);
                    info.setType(vipValueType);
                    info.setAppUserName(appUserName);
                    info.setAppUserPhone(appUserPhone);
                    info.setCurrentAppUserId(currentAppUserId);
                    info.setCurrentAppUser(currentAppUserName);
                    info.setCurrentAppUserPhone(currentAppUserPhone);
                    info.setDate(date);
                    info.setCreateTime(LocalDateTime.now());
                    info.setUpdateTime(LocalDateTime.now());

                    return info;
                })
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(customerAnalysisInfoList)) {
            businessCustomerAnalysisInfoMapper.insertBatch(customerAnalysisInfoList);
        }
    }


    public LocalDate existBusinessAnalysis(Integer businessId) {
        if (businessId == null) {
            return null;
        }
        Wrapper<BusinessCustomerAnalysisInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("business_id", businessId);
        List<BusinessCustomerAnalysisInfo> businessCustomerAnalysisInfoList = businessCustomerAnalysisInfoMapper.selectList(wrapper);
        Optional<LocalDate> optionDate = businessCustomerAnalysisInfoList.stream()
                .map(BusinessCustomerAnalysisInfo::getDate)
                .map(LocalDate::parse)
                .max(Comparator.naturalOrder());
        return optionDate.orElse(null);
    }

    public AppUser getAppUser(Integer appUserId) {
        if (appUserId == null) {
            return new AppUser();
        }
        AppUser appUser = appuserMapper.selectById(appUserId);
        Optional<AppUser> optional = Optional.ofNullable(appUser);
        return optional.orElse(new AppUser());
    }

    public List<AppUser> getAppUserList(List<Integer> appUserIds) {
        if (appUserIds == null) {
            return Lists.newArrayList();
        }
        List<Integer> appUserList = appUserIds.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        Wrapper<AppUser> wrapper = new EntityWrapper<>();
        wrapper.in("id", appUserList.toArray());

        return appuserMapper.selectList(wrapper);
    }

    private boolean checkParam(String resvDate) {
        try {
            DateTimeFormatter.ofPattern("yyyy-MM").parse(resvDate);
        } catch (DateTimeParseException e) {
            log.error("传入的参数错误", e);
            return false;
        }
        return true;
    }

    /**
     * 当前经营经理电话
     * @param vipId
     * @return
     */
    private String getCurrentAppUserPhone(Integer vipId, List<ResvOrder> resvOrders) {
        if (vipId == null) {
            return StringUtils.EMPTY;
        }
        // 最后一次订单的营销经理
        Optional<ResvOrder> min = getLastResvOrder(resvOrders);
        ResvOrder resvOrder = min.orElse(new ResvOrder());
        String vipPhone = resvOrder.getVipPhone();
        return Optional.ofNullable(vipPhone).orElse(StringUtils.EMPTY);
    }

    /**
     * 当前营销经理名称
     * @param vipId
     * @return
     */
    private String getCurrentAppUserName(Integer vipId, List<ResvOrder> resvOrders) {
        if (vipId == null) {
            return StringUtils.EMPTY;
        }
        // 最后一次订单的营销经理
        Optional<ResvOrder> min = getLastResvOrder(resvOrders);
        ResvOrder resvOrder = min.orElse(new ResvOrder());
        String vipName = resvOrder.getVipName();
        return Optional.ofNullable(vipName).orElse(StringUtils.EMPTY);
    }

    /**
     * 当前营销经理 id
     * @param vipId
     * @return
     */
    private Integer getCurrentAppUserId(Integer vipId, List<ResvOrder> resvOrders) {
        if (vipId == null) {
            return -1;
        }
        Optional<ResvOrder> lastResvOrder = getLastResvOrder(resvOrders);
        ResvOrder resvOrder = lastResvOrder.orElse(new ResvOrder());
        Integer appUserId = resvOrder.getAppUserId();
        return Optional.ofNullable(appUserId).orElse(-1);
    }

    /**
     * 营销经理电话
     * @return
     */
    private String getAppUserPhone(AppUser appUser) {
        if (appUser == null) {
            return StringUtils.EMPTY;
        }
        String appUserPhone = appUser.getAppUserPhone();
        return Optional.ofNullable(appUserPhone).orElse(StringUtils.EMPTY);
    }

    /**
     * 营销经理名称
     * @return
     */
    private String getAppUserName(AppUser appUser) {
        String appUserName = appUser.getAppUserName();
        return Optional.ofNullable(appUserName).orElse(StringUtils.EMPTY);
    }

    /**
     * 营销经理 id
     * @param vipId
     * @return
     */
    private Integer getAppUserId(Integer vipId, List<ResvOrder> resvOrders, String resvDate) {
        Optional<ResvOrder> resvOrderOptional = getCurrentMonthLastResvOrder(resvOrders, resvDate);
        ResvOrder resvOrder = resvOrderOptional.orElse(new ResvOrder());
        Integer appUserId = resvOrder.getAppUserId();
        return Optional.ofNullable(appUserId).orElse(-1);
    }

    private String getVipSex(Vip vip) {
        if (vip == null) {
            return StringUtils.EMPTY;
        }
        String vipSex = vip.getVipSex();
        if ("男".equals(vipSex)) {
            return "先生";
        }
        if ("女".equals(vipSex)) {
            return "女士";
        }
        return vipSex;
    }

    /**
     * 取传入月份的订单
     * @param resvOrders 订单列表
     * @param resvData 月份 yyy-MM
     * @return 最后一次消费订单
     */
    private Optional<ResvOrder> getCurrentMonthLastResvOrder(List<ResvOrder> resvOrders, String resvData) {
        return resvOrders.stream()
                .filter(order -> {
                    Date updatedAt = order.getUpdatedAt();
                    if (updatedAt != null) {
                        Instant instant = updatedAt.toInstant();
                        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                        String format = DateTimeFormatter.ofPattern("yyyy-MM").format(localDateTime);
                        return resvData.equals(format);
                    }
                    return false;
                })
                .min((o1, o2) -> o1.getUpdatedAt().before(o2.getUpdatedAt()) ? -1 : 1);
    }

    private List<ResvOrder> getResvOrder(Integer vipId) {
        Wrapper<ResvOrder> wrapper = new EntityWrapper<>();
        wrapper.eq("vip_id", vipId);
        List<ResvOrder> resvOrders = orderService.selectList(wrapper);
        Optional<List<ResvOrder>> optional = Optional.ofNullable(resvOrders);
        return optional.orElse(new ArrayList<>());
    }

    /**
     * 获取最后一笔订单
     * @param resvOrders 订单列表
     * @return 最后一笔订单
     */
    private Optional<ResvOrder> getLastResvOrder(List<ResvOrder> resvOrders) {
        return resvOrders.stream()
                .filter(order -> order != null && order.getUpdatedAt() != null)
                .max(Comparator.comparing(ResvOrder::getUpdatedAt));
    }
}
