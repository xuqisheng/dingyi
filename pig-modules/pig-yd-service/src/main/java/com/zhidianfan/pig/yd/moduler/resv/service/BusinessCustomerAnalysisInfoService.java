package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.google.common.collect.Lists;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.service.IAppUserService;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessCustomerAnalysisDetailService;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessCustomerAnalysisInfoService;
import com.zhidianfan.pig.yd.moduler.common.service.IResvOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
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

    /** todo 待测试
     * 保存具体的名单数据
     * @param resvDate 执行任务跑的数据，yyyy-MM
     */
    public void saveAnalysisDetail(String resvDate) {
        if (!checkParam(resvDate)) {
            return;
        }
        Wrapper<BusinessCustomerAnalysisDetail> wrapper = new EntityWrapper<>();
        wrapper.eq("date", resvDate);

        List<BusinessCustomerAnalysisDetail> detailList = businessCustomerAnalysisDetailMapper.selectList(wrapper);

        List<Integer> vipIdList = detailList.stream()
                .map(BusinessCustomerAnalysisDetail::getVipId)
                .collect(Collectors.toList());
        List<Vip> vipList = vipService.getVipList(vipIdList);
        Map<Integer, Vip> vipMap = vipList.stream()
                .collect(Collectors.toMap(Vip::getId, vip -> vip));

        List<BusinessCustomerAnalysisInfo> customerAnalysisInfoList = detailList.stream()
                .map(detail -> {
                    String date = detail.getDate();
                    Integer businessId = detail.getBusinessId();
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
        businessCustomerAnalysisInfoMapper.insertBatch(customerAnalysisInfoList);
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
