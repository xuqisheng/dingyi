package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.service.*;
import com.zhidianfan.pig.yd.moduler.resv.dto.VipOrderDTO;
import com.zhidianfan.pig.yd.moduler.resv.thread.VipStatisticsThread;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author Bazinga
 * @Description
 * @Date Create in 2018/11/15
 * @Modified By:
 */
@Slf4j
@Service
public class VipStatisticsService {
    @Autowired
    private IVipService vipService;

    @Autowired
    private IResvOrderAndroidService resvOrderAndroidService;

    @Autowired
    private IVipStatisticsService vipStatisticsService;

    @Autowired
    private IBusinessService businessService;

    @Autowired
    private IResvOrderService resvOrderService;

    /**
     * vip统计线程池
     */
    private static final ExecutorService executorService = Executors.newFixedThreadPool(20);

    /**
     * 统计结束的时间
     */
    private static final LocalTime singleDatetime = LocalTime.of(7, 0);


    /**
     * 获取指定用户的统计信息
     *
     * @param vipId
     * @return
     */
    public VipStatistics getVipStatistics(String vipId) {
        VipStatistics vipStatistics = vipStatisticsService.selectOne(new EntityWrapper<VipStatistics>()
                .eq("vip_id", vipId));
        vipStatistics = vipStatistics == null ? new VipStatistics() : vipStatistics;
        return vipStatistics;
    }


    /**
     * 统计所有酒店
     */
    @Async
    public void vip() {
        int currentPage = 1;
        int pageSize = 1000;
        int num = 0;
        while (true) {
            Page<Vip> vipPage = vipService.selectPage(new Page<>(currentPage, pageSize));
            num += vipPage.getRecords().size();
            log.info("查询出来数量:" + vipPage.getRecords().size());
            log.info("当前总数:" + (num + vipPage.getRecords().size()));
            log.info("----------------------------------------------------------------------------------------------------------------");
            vipPage.getRecords().forEach(vip -> {
                log.info("vip_id:" + vip.getId());
                try {
                    vip(vip.getId());
                } catch (Exception e) {
                    log.error("统计 vipId:{},异常信息:{}", vip.getId(), e.getMessage());
                }
            });

            if (!vipPage.hasNext()) {
                break;
            }
            currentPage++;
        }
    }

    /**
     * 同一个酒店
     *
     * @param id
     */
    @Async
    public void vip(Integer id) {
        Vip vip = vipService.selectById(id);
        if (vip == null) {
            log.error("用户 id 不存在:{}", id);
            return;
        }
        List<ResvOrder> orders = resvOrderService.selectList(new EntityWrapper<ResvOrder>().eq("vip_id", id));
        VipStatistics statistics = vipStatisticsService.selectOne(new EntityWrapper<VipStatistics>().eq("vip_id", id));
        if (statistics == null) {
            statistics = new VipStatistics();
            statistics.setVipId(id);
        }
        statistics.setBusinessId(vip.getBusinessId());
        statistics.setResvBatchCount(0);
        statistics.setResvOrderCount(0);
        statistics.setResvPeopleNum(0);
        statistics.setMealBatchCount(0);
        statistics.setMealOrderCount(0);
        statistics.setMealPeopleNum(0);
        VipStatistics finalStatistics = statistics;
        Set<String> batchSet = Sets.newHashSet();
        orders.forEach(order -> {
            if (!batchSet.contains(order.getBatchNo())) {
                finalStatistics.setResvBatchCount(finalStatistics.getResvBatchCount() + 1);
            }
            finalStatistics.setResvOrderCount(finalStatistics.getResvOrderCount() + 1);
            if (StringUtils.isNotEmpty(order.getResvNum())) {
                log.info("ResvPeopleNum:{},ResvNum:{}", finalStatistics.getResvPeopleNum(), order.getResvNum());
                Integer resvNum = 1;
                try {
                    resvNum = Integer.valueOf(order.getResvNum());
                } catch (NumberFormatException e) {
                    log.error("订单:{},预订人数异常:{}", order.getResvOrder(), order.getResvNum());
                }
                finalStatistics.setResvPeopleNum(finalStatistics.getResvPeopleNum() + resvNum);
            }
            if (StringUtils.equalsAny(order.getStatus(), "2", "3")) {
                if (!batchSet.contains(order.getBatchNo())) {
                    finalStatistics.setMealBatchCount(finalStatistics.getMealBatchCount() + 1);
                }
                finalStatistics.setMealOrderCount(finalStatistics.getMealOrderCount() + 1);
                if (StringUtils.isNotEmpty(order.getActualNum())) {
                    finalStatistics.setMealPeopleNum(finalStatistics.getMealPeopleNum() + Integer.valueOf(order.getActualNum()));
                }
                Date lastMealDate = finalStatistics.getLastMealDate();
                if (lastMealDate == null || lastMealDate.before(order.getResvDate())) {
                    finalStatistics.setLastMealDate(order.getResvDate());
                }
            }

            batchSet.add(order.getBatchNo());

        });
        boolean result = vipStatisticsService.insertOrUpdate(statistics);
        log.info("用户统计结果:{},{}", id, result);
    }

    /**
     * 统计一个价酒店 vip 信息
     *
     * @param id
     */
    @Async
    public void business(Integer id) {
        List<Vip> vips = vipService.selectList(new EntityWrapper<Vip>().eq("business_id", id));
        vips.parallelStream().forEach(vip -> {
            vip(vip.getId());
        });
    }

    /**
     * 统计所有酒店vip 信息
     */
    @Async
    public void business() {
        int currentPage = 1;
        int pageSize = 1000;
        while (true) {
            Page<Business> businessPage = businessService.selectPage(new Page<>(currentPage, pageSize));
            businessPage.getRecords().parallelStream().forEach(business -> {
                log.info("businessId:" + business.getId());
                try {
                    business(business.getId());
                } catch (Exception e) {
                    log.error("统计 businessId:{},异常信息:{}", business.getId(), e.getMessage());
                }
            });

            if (!businessPage.hasNext()) {
                break;
            }
            currentPage++;
        }
    }

    /**
     * 统计所有酒店的vip信息
     */
    @Async
    public void statisticsAllBusinessVip() {
        int businessCurrentPage = 1;
        int businessPageSize = 1000;


        while (true) {
            //翻页查询酒店
            Page<Business> businessPage = businessService.selectPage(new Page<>(businessCurrentPage, businessPageSize));
            //遍历酒店
            businessPage.getRecords().forEach(this::businessVipStatistics);

            //当前页是最后页退出循环
            if (!businessPage.hasNext()) {
                break;
            }
            //当前页面+1
            businessCurrentPage++;
        }
    }


    /**
     * 酒店vip统计
     *
     * @param business
     */
    private void businessVipStatistics(Business business) {
        int vipCurrentPage = 1;
        int vipPageSize = 100;

        while (true) {
            //翻页查询酒店vip
            Page<Vip> vipPage = vipService.selectPage(
                    new Page<>(vipCurrentPage, vipPageSize),
                    new EntityWrapper<Vip>().eq("business_id", business.getId())
            );

            List<Vip> vips = vipPage.getRecords();
            List<Integer> vipIds = Lists.newArrayList();
            vips.forEach(vip -> vipIds.add(vip.getId()));

            LocalTime now = LocalTime.now();
            if (now.isBefore(singleDatetime)) {
                executorService.execute(() -> new VipStatisticsThread(this, business, vipIds).run());
            } else {
                vipStatistics(business, vipIds);
            }

            //vip查询最后页退出循环
            if (!vipPage.hasNext()) {
                break;
            }
            //当前页面+1
            vipCurrentPage++;
        }

    }

    /**
     * vip批量统计
     *
     * @param business
     * @param vipIds
     */
    public void vipStatistics(Business business, Collection<Integer> vipIds) {
        //开启线程执行vip订单信息统计
        int orderCurrentPage = 1;
        int orderPageSize = 2000;
        Map<Integer, VipStatistics> vipStatisticsMap = Maps.newHashMap();
        List<VipStatistics> vipStatisticsList = vipStatisticsService.selectList(new EntityWrapper<VipStatistics>().eq("business_id", business.getId()).in("vip_id", vipIds));
        vipStatisticsList.forEach(vipStatistics -> vipStatisticsMap.put(vipStatistics.getVipId(), vipStatistics));
        Set<String> batchSet = Sets.newHashSet();

        while (true) {
            Page<ResvOrder> orderPage = resvOrderService.selectPage(
                    new Page<>(orderCurrentPage, orderPageSize),
                    new EntityWrapper<ResvOrder>()
                            .eq("business_id", business.getId())
                            .in("vip_id", vipIds));
            orderPage.getRecords().forEach(order -> {
                VipStatistics vipStatistics = vipStatisticsMap.get(order.getVipId());
                //vip统计不存在初始化数据
                if (vipStatistics == null) {
                    vipStatistics = new VipStatistics();
                    vipStatistics.setBusinessId(business.getId());
                    vipStatistics.setVipId(order.getVipId());
                    vipStatistics.setResvBatchCount(0);
                    vipStatistics.setResvOrderCount(0);
                    vipStatistics.setResvPeopleNum(0);
                    vipStatistics.setMealBatchCount(0);
                    vipStatistics.setMealOrderCount(0);
                    vipStatistics.setMealPeopleNum(0);
                    vipStatisticsMap.put(order.getVipId(), vipStatistics);
                }
                //批次号是否已经存在，一个批次号只统计一次
                if (!batchSet.contains(order.getBatchNo())) {
                    vipStatistics.setResvBatchCount(vipStatistics.getResvBatchCount() + 1);
                }
                //预定次数
                vipStatistics.setResvOrderCount(vipStatistics.getResvOrderCount() + 1);
                if (StringUtils.isNotEmpty(order.getResvNum())) {
                    Integer num;
                    //预定人数转换成int，如果转换失败当做1人处理
                    try {
                        num = Integer.valueOf(order.getResvNum());
                    } catch (NumberFormatException e) {
                        log.error("订单号:{},NumberFormatException:{}", order.getResvOrder(), e.getMessage());
                        num = 1;
                    }
                    vipStatistics.setResvPeopleNum(vipStatistics.getResvPeopleNum() + num);
                }
                //预定入座或者已完成
                if (StringUtils.equalsAny(order.getStatus(), "2", "3")) {
                    //相同批次号只做一次处理
                    if (!batchSet.contains(order.getBatchNo())) {
                        vipStatistics.setMealBatchCount(vipStatistics.getMealBatchCount() + 1);
                    }
                    //消费次数
                    vipStatistics.setMealOrderCount(vipStatistics.getMealOrderCount() + 1);
                    //实际预订人数
                    if (StringUtils.isNotEmpty(order.getActualNum())) {
                        Integer actualNum;
                        try {
                            actualNum = Integer.valueOf(order.getActualNum());
                        } catch (NumberFormatException e) {
                            log.error("订单号:{},NumberFormatException:{}", order.getResvOrder(), e.getMessage());
                            actualNum = 1;
                        }
                        vipStatistics.setMealPeopleNum(vipStatistics.getMealPeopleNum() + actualNum);
                    }
                    //最近就餐时间
                    Date lastMealDate = vipStatistics.getLastMealDate();
                    if (lastMealDate == null || lastMealDate.before(order.getResvDate())) {
                        vipStatistics.setLastMealDate(order.getResvDate());
                    }
                }
                //添加批次号
                batchSet.add(order.getBatchNo());
            });

            //当前订单是否没有下一页
            if (!orderPage.hasNext()) {
                break;
            }
            //翻页
            orderCurrentPage++;
        }

        orderCurrentPage = 1;
        while (true) {
            Page<ResvOrderAndroid> orderAndroidPage = resvOrderAndroidService.selectPage(
                    new Page<>(orderCurrentPage, orderPageSize),
                    new EntityWrapper<ResvOrderAndroid>()
                            .eq("business_id", business.getId())
                            .in("vip_id", vipIds));
            orderAndroidPage.getRecords().forEach(order -> {
                VipStatistics vipStatistics = vipStatisticsMap.get(order.getVipId());
                //vip统计不存在初始化数据
                if (vipStatistics == null) {
                    vipStatistics = new VipStatistics();
                    vipStatistics.setBusinessId(business.getId());
                    vipStatistics.setVipId(order.getVipId());
                    vipStatistics.setResvBatchCount(0);
                    vipStatistics.setResvOrderCount(0);
                    vipStatistics.setResvPeopleNum(0);
                    vipStatistics.setMealBatchCount(0);
                    vipStatistics.setMealOrderCount(0);
                    vipStatistics.setMealPeopleNum(0);
                    vipStatisticsMap.put(order.getVipId(), vipStatistics);
                }
                //批次号是否已经存在，一个批次号只统计一次
                if (!batchSet.contains(order.getBatchNo())) {
                    vipStatistics.setResvBatchCount(vipStatistics.getResvBatchCount() + 1);
                }
                //预定次数
                vipStatistics.setResvOrderCount(vipStatistics.getResvOrderCount() + 1);
                if (StringUtils.isNotEmpty(order.getResvNum())) {
                    Integer num;
                    //预定人数转换成int，如果转换失败当做1人处理
                    try {
                        num = Integer.valueOf(order.getResvNum());
                    } catch (NumberFormatException e) {
                        log.error("订单号:{},NumberFormatException:{}", order.getResvOrder(), e.getMessage());
                        num = 1;
                    }
                    vipStatistics.setResvPeopleNum(vipStatistics.getResvPeopleNum() + num);
                }
                //预定入座或者已完成
                if (StringUtils.equalsAny(order.getStatus(), "2", "3")) {
                    //相同批次号只做一次处理
                    if (!batchSet.contains(order.getBatchNo())) {
                        vipStatistics.setMealBatchCount(vipStatistics.getMealBatchCount() + 1);
                    }
                    //消费次数
                    vipStatistics.setMealOrderCount(vipStatistics.getMealOrderCount() + 1);
                    //实际预订人数
                    if (StringUtils.isNotEmpty(order.getActualNum())) {
                        Integer actualNum;
                        try {
                            actualNum = Integer.valueOf(order.getActualNum());
                        } catch (NumberFormatException e) {
                            log.error("订单号:{},NumberFormatException:{}", order.getResvOrder(), e.getMessage());
                            actualNum = 1;
                        }
                        vipStatistics.setMealPeopleNum(vipStatistics.getMealPeopleNum() + actualNum);
                    }
                    //最近就餐时间
                    Date lastMealDate = vipStatistics.getLastMealDate();
                    if (lastMealDate == null || lastMealDate.before(order.getResvDate())) {
                        vipStatistics.setLastMealDate(order.getResvDate());
                    }
                }
                //添加批次号
                batchSet.add(order.getBatchNo());
            });

            //当前订单是否没有下一页
            if (!orderAndroidPage.hasNext()) {
                break;
            }
            //翻页
            orderCurrentPage++;
        }

        //新增或者更新vip统计
        if(!MapUtils.isEmpty(vipStatisticsMap)){
            vipStatisticsService.insertOrUpdateBatch(Lists.newArrayList(vipStatisticsMap.values()));
        }

    }

    @Async
    public void vipOrderStatistics(VipOrderDTO vipOrderDTO) {
        log.info("=================开始执行vip统计===============");
        while (true) {
            int businessCurrentPage = 1;
            int businessPageSize = 1000;
            Page<Business> businessPage = businessService.selectPage(new Page<>(businessCurrentPage, businessPageSize));
            businessPage.getRecords().forEach(business -> {
                log.info("vipOrderStatistics酒店id:{}",business.getId());
                int currentPage = 1;
                int pageSize = 1000;

                Map<Integer, VipStatistics> vipStatisticsMap = Maps.newHashMap();
                Set<String> batchSet = Sets.newHashSet();


                EntityWrapper<ResvOrder> resvOrderEntityWrapper = new EntityWrapper<>();
                EntityWrapper<ResvOrderAndroid> androidEntityWrapper = new EntityWrapper<>();
                //vip id
                if (vipOrderDTO.getVipId() != null) {
                    resvOrderEntityWrapper.eq("vip_id", vipOrderDTO.getVipId());
                    androidEntityWrapper.eq("vip_id", vipOrderDTO.getVipId());
                }
                //business id
                if (vipOrderDTO.getBusinessId() != null) {
                    resvOrderEntityWrapper.eq("business_id", vipOrderDTO.getBusinessId());
                    androidEntityWrapper.eq("business_id", vipOrderDTO.getBusinessId());
                }
                //查询订单时间
                LocalDate updatedAt = vipOrderDTO.getBeginDate() == null ? LocalDate.now().minusDays(1) : vipOrderDTO.getBeginDate();
                resvOrderEntityWrapper.gt("updated_at", updatedAt);
                androidEntityWrapper.gt("updated_at", updatedAt);

                LocalDate now = LocalDate.now();
                resvOrderEntityWrapper.lt("updated_at", now);
                androidEntityWrapper.lt("updated_at", now);

                resvOrderEntityWrapper.eq("business_id", business.getId());
                androidEntityWrapper.eq("business_id", business.getId());

                //统计resv_order订单
                while (true) {
                    Page<ResvOrder> orderPage = resvOrderService.selectPage(
                            new Page<>(currentPage, pageSize),
                            resvOrderEntityWrapper
                    );

                    orderPage.getRecords().forEach(order -> {
                        Integer vipId = order.getVipId();
                        if (vipId == null || vipId == 0) {
                            return;
                        }
                        VipStatistics vipStatistics = vipStatisticsMap.get(vipId);
                        if (vipStatistics == null) {
                            Integer businessId = order.getBusinessId();
                            vipStatistics = vipStatisticsService.selectOne(
                                    new EntityWrapper<VipStatistics>()
                                            .eq("business_id", businessId)
                                            .eq("vip_id", vipId)
                            );
                            if (vipStatistics == null) {
                                vipStatistics = new VipStatistics();
                                vipStatistics.setVipId(vipId);
                                vipStatistics.setBusinessId(businessId);
                                vipStatistics.setResvBatchCount(0);
                                vipStatistics.setResvOrderCount(0);
                                vipStatistics.setResvPeopleNum(0);
                                vipStatistics.setMealBatchCount(0);
                                vipStatistics.setMealOrderCount(0);
                                vipStatistics.setMealPeopleNum(0);
                                vipStatistics.setAmount(0);
                                vipStatistics.setCreateTime(new Date());
                            }
                            if(vipStatistics.getCreateTime() == null){
                                vipStatistics.setCreateTime(new Date());
                            }else{
                                vipStatistics.setUpdateTime(new Date());
                            }
                            vipStatisticsMap.put(vipId, vipStatistics);
                        }
                        handlerResvOrder(batchSet, order, vipStatistics);
                        //新增或者更新
                        vipStatisticsService.insertOrUpdateBatch(Lists.newArrayList(vipStatisticsMap.values()));

                    });

                    if (!orderPage.hasNext()) {
                        break;
                    }

                    currentPage++;

                }

                currentPage = 1;
                //统计resv_order_android订单
                while (true) {
                    Page<ResvOrderAndroid> orderPage = resvOrderAndroidService.selectPage(
                            new Page<>(currentPage, pageSize),
                            androidEntityWrapper
                    );

                    orderPage.getRecords().forEach(order -> {
                        Integer vipId = order.getVipId();
                        if (vipId == null || vipId == 0) {
                            return;
                        }
                        VipStatistics vipStatistics = vipStatisticsMap.get(vipId);
                        if (vipStatistics == null) {
                            Integer businessId = order.getBusinessId();
                            vipStatistics = vipStatisticsService.selectOne(
                                    new EntityWrapper<VipStatistics>()
                                            .eq("business_id", businessId)
                                            .eq("vip_id", vipId)
                            );
                            if (vipStatistics == null) {
                                vipStatistics = new VipStatistics();
                                vipStatistics.setVipId(vipId);
                                vipStatistics.setBusinessId(businessId);
                                vipStatistics.setResvBatchCount(0);
                                vipStatistics.setResvOrderCount(0);
                                vipStatistics.setResvPeopleNum(0);
                                vipStatistics.setMealBatchCount(0);
                                vipStatistics.setMealOrderCount(0);
                                vipStatistics.setMealPeopleNum(0);
                                vipStatistics.setAmount(0);
                                vipStatistics.setCreateTime(new Date());
                            }
                            if(vipStatistics.getCreateTime() == null){
                                vipStatistics.setCreateTime(new Date());
                            }else{
                                vipStatistics.setUpdateTime(new Date());
                            }
                            vipStatisticsMap.put(vipId, vipStatistics);
                        }
                        handlerResvOrderAndroid(batchSet, order, vipStatistics);
                        //新增或者更新
                        vipStatisticsService.insertOrUpdateBatch(Lists.newArrayList(vipStatisticsMap.values()));

                    });

                    if (!orderPage.hasNext()) {
                        break;
                    }

                    currentPage++;

                }
            });

            if (!businessPage.hasNext()) {
                break;
            }
            businessCurrentPage++;
        }
        log.info("=================开始执行vip统计===============");
    }

    private void handlerResvOrder(Set<String> batchSet, ResvOrder order, VipStatistics vipStatistics) {
        //批次号是否已经存在，一个批次号只统计一次
        if (!batchSet.contains(order.getBatchNo())) {
            vipStatistics.setResvBatchCount(vipStatistics.getResvBatchCount() + 1);
        }
        //预定次数
        vipStatistics.setResvOrderCount(vipStatistics.getResvOrderCount() + 1);
        if (StringUtils.isNotEmpty(order.getResvNum())) {
            Integer num;
            //预定人数转换成int，如果转换失败当做1人处理
            try {
                num = Integer.valueOf(order.getResvNum());
            } catch (NumberFormatException e) {
                log.error("订单号:{},NumberFormatException:{}", order.getResvOrder(), e.getMessage());
                num = 1;
            }
            vipStatistics.setResvPeopleNum(vipStatistics.getResvPeopleNum() + num);
        }
        //预定入座或者已完成
        if (StringUtils.equalsAny(order.getStatus(), "2", "3")) {
            //相同批次号只做一次处理
            if (!batchSet.contains(order.getBatchNo())) {
                vipStatistics.setMealBatchCount(vipStatistics.getMealBatchCount() + 1);
            }
            //消费次数
            vipStatistics.setMealOrderCount(vipStatistics.getMealOrderCount() + 1);
            //实际预订人数
            if (StringUtils.isNotEmpty(order.getActualNum())) {
                Integer actualNum;
                try {
                    actualNum = Integer.valueOf(order.getActualNum());
                } catch (NumberFormatException e) {
                    log.error("订单号:{},NumberFormatException:{}", order.getResvOrder(), e.getMessage());
                    actualNum = 1;
                }
                vipStatistics.setMealPeopleNum(vipStatistics.getMealPeopleNum() + actualNum);
            }
            //最近就餐时间
            Date lastMealDate = vipStatistics.getLastMealDate();
            if (lastMealDate == null || lastMealDate.before(order.getResvDate())) {
                vipStatistics.setLastMealDate(order.getResvDate());
            }

            vipStatistics.setUpdateTime(new Date());
        }
        //添加批次号
        batchSet.add(order.getBatchNo());
    }

    private void handlerResvOrderAndroid(Set<String> batchSet, ResvOrderAndroid order, VipStatistics vipStatistics) {
        //批次号是否已经存在，一个批次号只统计一次
        if (!batchSet.contains(order.getBatchNo())) {
            vipStatistics.setResvBatchCount(vipStatistics.getResvBatchCount() + 1);
        }
        //预定次数
        vipStatistics.setResvOrderCount(vipStatistics.getResvOrderCount() + 1);
        if (StringUtils.isNotEmpty(order.getResvNum())) {
            Integer num;
            //预定人数转换成int，如果转换失败当做1人处理
            try {
                num = Integer.valueOf(order.getResvNum());
            } catch (NumberFormatException e) {
                log.error("订单号:{},NumberFormatException:{}", order.getResvOrder(), e.getMessage());
                num = 1;
            }
            vipStatistics.setResvPeopleNum(vipStatistics.getResvPeopleNum() + num);
        }
        //预定入座或者已完成
        if (StringUtils.equalsAny(order.getStatus(), "2", "3")) {
            //相同批次号只做一次处理
            if (!batchSet.contains(order.getBatchNo())) {
                vipStatistics.setMealBatchCount(vipStatistics.getMealBatchCount() + 1);
            }
            //消费次数
            vipStatistics.setMealOrderCount(vipStatistics.getMealOrderCount() + 1);
            //实际预订人数
            if (StringUtils.isNotEmpty(order.getActualNum())) {
                Integer actualNum;
                try {
                    actualNum = Integer.valueOf(order.getActualNum());
                } catch (NumberFormatException e) {
                    log.error("订单号:{},NumberFormatException:{}", order.getResvOrder(), e.getMessage());
                    actualNum = 1;
                }
                vipStatistics.setMealPeopleNum(vipStatistics.getMealPeopleNum() + actualNum);
            }
            //最近就餐时间
            Date lastMealDate = vipStatistics.getLastMealDate();
            if (lastMealDate == null || lastMealDate.before(order.getResvDate())) {
                vipStatistics.setLastMealDate(order.getResvDate());
            }

            vipStatistics.setUpdateTime(new Date());

        }
        //添加批次号
        batchSet.add(order.getBatchNo());
    }


}
