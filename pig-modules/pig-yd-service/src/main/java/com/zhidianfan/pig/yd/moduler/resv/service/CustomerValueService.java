package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.ResvOrderMapper;
import com.zhidianfan.pig.yd.moduler.common.service.ICustomerRecordService;
import com.zhidianfan.pig.yd.moduler.common.service.ICustomerValueListService;
import com.zhidianfan.pig.yd.moduler.common.service.IVipConsumeActionLast60Service;
import com.zhidianfan.pig.yd.moduler.common.service.IVipConsumeActionTotalService;
import com.zhidianfan.pig.yd.moduler.resv.constants.CustomerValueConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

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

    @Transactional(rollbackFor = Exception.class)
    public void getCustomerValueBaseInfo() {
        LocalDateTime startTime = LocalDateTime.now();
        CustomerValueTask customerValueTask = customerValueTaskService.getCustomerValueTask();
        // 1. 从任务表中取出酒店 id
        Long hotelId = customerValueTask.getHotelId();
        // 1.1 查询属于该酒店的所有客户
//        List<Vip> vips = vipService.getVipList(hotelId);
        List<Vip> vips = vipService.getVipList(30);

//        Long taskId = customerValueTask.getId();
        long taskId = 1131751120788840450L;
        log.info("任务开始，taskId：{}, 开始时间：{}", taskId, DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(startTime));
        // 任务执行标记,0-未开始,1-执行中,2-执行成功,3-执行异常
        for (Vip vip : vips) {
            try {
                execute(vip);
            } catch (Exception e) {
                customerValueTaskService.updateTaskStatus(taskId, CustomerValueConstants.EXECUTE_EXCEPTION, startTime, LocalDateTime.now(), e.getMessage());
                log.error("任务发生异常，taskId: {}, 异常时间:{}", taskId, DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now()), e);
                throw e;
            }
        }

        LocalDateTime endTime = LocalDateTime.now();
        customerValueTaskService.updateTaskStatus(taskId, CustomerValueConstants.EXECUTE_SUCCESS, startTime, endTime, StringUtils.EMPTY);
        log.info("任务结束，taskId: {}, 结束时间:{}", taskId, DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(endTime));
    }

    /**
     * 执行任务的过程
     * @param vip vip 信息
     */
    public void execute(Vip vip) {
        List<ResvOrder> resvOrders = getResvOrders(vip.getId());
        List<ResvOrder> resvOrdersBy60days = getResvOrdersBy60day(vip.getId());

        CustomerValueList customerValueList = customerValueListService.getCustomerValueList(vip, resvOrders);
        VipConsumeActionTotal vipConsumeActionTotal = vipConsumeActionTotalService.getVipConsumeActionTotal(vip, resvOrders);
        VipConsumeActionLast60 vipConsumeActionLast60 = vipConsumeActionLast60Service.getVipConsumeActionLast60(vip, resvOrdersBy60days);
        List<CustomerRecord> customerRecordList = customerRecordService.getCustomerRecord(vip, resvOrders, customerValueList);

        customerValueListMapper.insertOrUpdate(customerValueList);
        vipConsumeActionTotalMapper.insertOrUpdate(vipConsumeActionTotal);
        vipConsumeActionLast60Mapper.insertOrUpdate(vipConsumeActionLast60);
        customerRecordMapper.insertBatch(customerRecordList);
    }

    /**
     * 最近 60 天产生的消费订单-根据用餐时间
     *
     * @param vipId vip 表的主键
     */
    private List<ResvOrder> getResvOrdersBy60day(Integer vipId) {
        EntityWrapper<ResvOrder> wrapper = new EntityWrapper<>();
        wrapper.eq("vip_id", vipId);
        LocalDate now = LocalDate.now();
        LocalDate start = now.minusDays(60);
        wrapper.ge("updated_at", start);
        wrapper.le("updated_at", now);
        wrapper.in("status", Arrays.asList(2, 3));
        return resvOrderMapper.selectList(wrapper);
    }

    /**
     * vip 用户的所有订单
     * @param vipId vip 主键
     * @return 所有的订单列表
     */
    private List<ResvOrder> getResvOrders(Integer vipId) {
        EntityWrapper<ResvOrder> wrapper = new EntityWrapper<>();
        wrapper.eq("vip_id", vipId);
        return resvOrderMapper.selectList(wrapper);
    }

}
