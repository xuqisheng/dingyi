package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.ResvOrderMapper;
import com.zhidianfan.pig.yd.moduler.common.service.*;
import com.zhidianfan.pig.yd.moduler.resv.constants.CustomerValueConstants;
import com.zhidianfan.pig.yd.moduler.resv.dto.CustomerValueChangeFieldDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author sjl
 * 2019-05-21 13:57
 */
@Slf4j
@Service
public class CustomerValueService {

    @Autowired
    private ResvOrderMapper resvOrderMapper;

    @Autowired
    private ICustomerValueListService customerValueListMapper;

    @Autowired
    private CustomerValueListService customerValueListService;

    @Autowired
    private IVipConsumeActionTotalService vipConsumeActionTotalMapper;

    @Autowired
    private IVipConsumeActionLast60Service vipConsumeActionLast60Mapper;

    @Autowired
    private VipService vipService;

    @Autowired
    private CustomerValueTaskService customerValueTaskService;

    @Autowired
    private VipConsumeActionTotalService vipConsumeActionTotalService;

    @Autowired
    private VipConsumeActionLast60Service vipConsumeActionLast60Service;

    @Autowired
    private CustomerRecordService customerRecordService;

    @Autowired
    private ICustomerRecordService customerRecordMapper;

    @Autowired
    private INowChangeInfoService nowChangeInfoMapper;

    //    @Async
//    @Transactional(rollbackFor = Exception.class)
//    public void getCustomerValueBaseInfo() {
//        LocalDateTime startTime = LocalDateTime.now();
//        CustomerValueTask customerValueTask = customerValueTaskService.getCustomerValueTask();
//        log.info("获取到的任务编号：{}", customerValueTask.getId());
//        // 1. 从任务表中取出酒店 id
//        Long hotelId = customerValueTask.getHotelId();
//        cleanData(hotelId);
//        // 1.1 查询属于该酒店的所有客户
//        List<Vip> vips = vipService.getVipList(hotelId);
//
//        Long taskId = customerValueTask.getId();
//        log.info("任务开始，taskId：{}, 开始时间：{}", taskId, DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(startTime));
//        // 任务执行标记,0-未开始,1-执行中,2-执行成功,3-执行异常
//        customerValueTaskService.updateTaskStatus(taskId, CustomerValueConstants.EXECUTING, startTime, CustomerValueConstants.DEFAULT_END_TIME, StringUtils.EMPTY);
//
//        AtomicInteger count = new AtomicInteger(0);
//
//        Optional.ofNullable(vips)
//                .ifPresent(vips1 -> {
//                    vips1.parallelStream()
//                            .forEach(vip -> {
//                                try {
//                                    execute(vip);
//                                    log.info("当前执行进度,酒店：{},{}/{}", hotelId, count.addAndGet(1), vips.size());
//                                } catch (Exception e) {
//                                    customerValueTaskService.updateTaskStatus(taskId, CustomerValueConstants.EXECUTE_EXCEPTION, startTime, LocalDateTime.now(), StringUtils.EMPTY);
//                                    log.error("任务发生异常，taskId: {}, 异常时间:{}", taskId, DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now()), e);
//                                }
//                            });
//                });
//
//        LocalDateTime endTime = LocalDateTime.now();
//        customerValueTaskService.updateTaskStatus(taskId, CustomerValueConstants.EXECUTE_SUCCESS, startTime, endTime, StringUtils.EMPTY);
//        log.info("任务结束，taskId: {}, 结束时间:{}", taskId, DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(endTime));
//    }

    public void getCustomerValueBaseInfo2(CustomerValueTask customerValueTask) {
        LocalDateTime startTime = LocalDateTime.now();
        // 1. 从任务表中取出酒店 id
        Long hotelId = customerValueTask.getHotelId();
        cleanData(hotelId);
        // 1.1 查询属于该酒店的所有客户
        List<Vip> vips = vipService.getVipList(hotelId);
        //对vips分组，100个vip一组
        Map<String, List<Vip>> map = getVipsMap(vips, 500);

        Long taskId = customerValueTask.getId();
        // 任务执行标记,0-未开始,1-执行中,2-执行成功,3-执行异常
        customerValueTaskService.updateTaskStatus(taskId, CustomerValueConstants.EXECUTING, startTime, CustomerValueConstants.DEFAULT_END_TIME, StringUtils.EMPTY);

        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "128");
        Optional.ofNullable(map)
                .ifPresent(map1 -> {
                    map1.forEach((k, v) -> {
                        try {
                            execute(v);
                        } catch (Exception e) {
                            log.error(e.getMessage(), e);
                            customerValueTaskService.updateTaskStatus(taskId, CustomerValueConstants.EXECUTE_EXCEPTION, startTime, LocalDateTime.now(), StringUtils.EMPTY);
                            log.error("任务发生异常，taskId: {}, 异常时间:{}", taskId, DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now()), e);
                        }
                    });
                });

        LocalDateTime endTime = LocalDateTime.now();
        customerValueTaskService.updateTaskStatus(taskId, CustomerValueConstants.EXECUTE_SUCCESS, startTime, endTime, StringUtils.EMPTY);
    }

    /**
     * 对客户进行分组
     *
     * @param vips
     * @param i
     * @return
     */
    private Map<String, List<Vip>> getVipsMap(List<Vip> vips, int i) {
        //vips分组，每组i个vip
        //可以分成多少组？即map大小
        Map<String, List<Vip>> map = new HashMap<>(vips.size() / i + 1);
        for (int j = 0; j < vips.size() / i + 1; j++) {
            int startIndex = j * 2;
            int endIndex = (j + 1) * 2;

            if (startIndex >= vips.size()) {
                continue;
            }

            if (endIndex > vips.size()) {
                endIndex = vips.size();
            }

            List<Vip> tmp = vips.subList(startIndex, endIndex);
            map.put("" + j, tmp);

        }
        return map;
    }

    // 清除脏数据
    private void cleanData(Long hotelId) {
        if (hotelId == null) {
            return;
        }
        Wrapper<CustomerValueList> wrapper = new EntityWrapper<>();
        wrapper.eq("hotel_id", hotelId);
        customerValueListMapper.delete(wrapper);
    }

    /**
     * 执行任务的过程
     *
     * @param vip vip 信息
     */
    public void execute(List<Vip> vip) {

        List<Integer> vipIds = vip.stream().map(Vip::getId).collect(Collectors.toList());

        //获取客户的所有订单
//        List<ResvOrder> resvOrders = getResvOrders(vip.getId());
        Map<Integer, List<ResvOrder>> resvOrders = getResvOrders(vipIds);
//        List<ResvOrder> resvOrdersBy60days = getResvOrdersBy60day(vip.getId());
        Map<Integer, List<ResvOrder>> resvOrdersBy60days = getResvOrdersBy60day(vipIds);

        Map<Integer, CustomerValueList> customerValueList = customerValueListService.getCustomerValueList(vip, resvOrders);

        Map<Integer, VipConsumeActionTotal> vipConsumeActionTotal = vipConsumeActionTotalService.getVipConsumeActionTotal(vip, resvOrders);

        Map<Integer, VipConsumeActionLast60> vipConsumeActionLast60 = vipConsumeActionLast60Service.getVipConsumeActionLast60(vip, resvOrdersBy60days);

        Map<Integer, List<CustomerRecord>> customerRecordList = customerRecordService.getCustomerRecord(vip, resvOrders, customerValueList);

        Map<Integer, NowChangeInfo> nowChangeInfo = getProfile(vip);

        Optional.ofNullable(customerValueList)
                .ifPresent(map -> {
                    map.forEach((k, v) -> {
                        customerValueListMapper.insertOrUpdate(v);
                    });
                });

        Optional.ofNullable(vipConsumeActionTotal)
                .ifPresent(map -> {
                    map.forEach((k, v) -> {
                        vipConsumeActionTotalMapper.insertOrUpdate(v);
                    });
                });

        Optional.ofNullable(vipConsumeActionLast60)
                .ifPresent(map -> {
                    map.forEach((k, v) -> {
                        vipConsumeActionLast60Mapper.insertOrUpdate(v);
                    });
                });

        Optional.ofNullable(customerRecordList)
                .ifPresent(map -> {
                    map.forEach((k, v) -> {
                        customerRecordMapper.insertBatch(v);
                    });
                });

        Optional.ofNullable(nowChangeInfo)
                .ifPresent(map -> {
                    map.forEach((k, v) -> {
                        if (v != null) {
                            nowChangeInfoMapper.insertOrUpdate(v);
                            log.info("客户资料完整度 [{}] 插入完成", v);
                        }
                    });
                });

        log.info("插入 vip:[{}] 完成", vipIds);
    }

    /**
     * 客户资料完整度
     *
     * @param vips vip 信息
     * @return NowChangeInfo
     */
    private Map<Integer, NowChangeInfo> getProfile(List<Vip> vips) {

        Map<Integer, NowChangeInfo> map = new HashMap<>();
        for (Vip vip : vips) {
            try {
                if (vip == null) {
                    return null;
                }
                int profile = vipService.getProfile(vip);
                Integer vipId = vip.getId();
                NowChangeInfo nowChangeInfo = new NowChangeInfo();
                nowChangeInfo.setVipId(vipId);
                nowChangeInfo.setValue(profile);
                nowChangeInfo.setType(CustomerValueChangeFieldDTO.PROFILE);
                nowChangeInfo.setChangeTime(LocalDateTime.now());
                nowChangeInfo.setRemark(StringUtils.EMPTY);
                nowChangeInfo.setCreateTime(LocalDateTime.now());
                nowChangeInfo.setUpdateTime(LocalDateTime.now());
                map.put(vipId, nowChangeInfo);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

        }


        return map;
    }

    /**
     * 最近 60 天产生的消费订单-根据用餐时间
     *
     * @param vipId vip 表的主键
     */
    private Map<Integer, List<ResvOrder>> getResvOrdersBy60day(List<Integer> vipId) {
        EntityWrapper<ResvOrder> wrapper = new EntityWrapper<>();
        wrapper.in("vip_id", vipId);
        LocalDate now = LocalDate.now();
        LocalDate start = now.minusDays(60);
        wrapper.ge("updated_at", start);
        wrapper.le("updated_at", now);
        wrapper.in("status", Arrays.asList(2, 3));
        List<ResvOrder> resvOrders = resvOrderMapper.selectList(wrapper);
        Map<Integer, List<ResvOrder>> map = resvOrders.stream()
                .collect(Collectors.groupingBy(ResvOrder::getVipId));
        return map;
    }


    /**
     * vip 用户的所有订单
     *
     * @param vipIds vip 主键
     * @return 所有的订单列表
     */
    private Map<Integer, List<ResvOrder>> getResvOrders(List<Integer> vipIds) {
        EntityWrapper<ResvOrder> wrapper = new EntityWrapper<>();
        wrapper.in("vip_id", vipIds);
        List<ResvOrder> resvOrders = resvOrderMapper.selectList(wrapper);
        Map<Integer, List<ResvOrder>> map = resvOrders.stream()
                .collect(Collectors.groupingBy(ResvOrder::getVipId));
        return map;
    }


}
