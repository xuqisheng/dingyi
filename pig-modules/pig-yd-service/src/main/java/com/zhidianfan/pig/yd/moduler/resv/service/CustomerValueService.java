package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.ResvOrderMapper;
import com.zhidianfan.pig.yd.moduler.common.service.ICustomerValueListService;
import com.zhidianfan.pig.yd.moduler.common.service.IVipConsumeActionLast60Service;
import com.zhidianfan.pig.yd.moduler.common.service.IVipConsumeActionTotalService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    @Transactional(rollbackFor = Exception.class)
    public void getCustomerValueBaseInfo() {
        LocalDateTime startTime = LocalDateTime.now();
        CustomerValueTask customerValueTask = customerValueTaskService.getCustomerValueTask();
        // 1. 从任务表中取出酒店 id
        Long hotelId = customerValueTask.getHotelId();
        // 1.1 查询属于该酒店的所有客户
//        List<Vip> vips = vipService.getVipList(hotelId);
        List<Vip> vips = vipService.getVipList(30);

        String exceptionMessage = StringUtils.EMPTY;
        boolean b = false;
        for (Vip vip : vips) {
            try {
                b = execute(vip);
            } catch (Exception e) {
                exceptionMessage = e.toString();
                // todo 事务手动回滚
            }
            if (!b) {
                log.error("写入数据库失败----", vip);
            }
        }
//        Long taskId = customerValueTask.getId();
        long taskId = 1131751120788840450L;
        LocalDateTime endTime = LocalDateTime.now();
        customerValueTaskService.updateTaskStatus(taskId, b, startTime, endTime, exceptionMessage);
    }

    public boolean execute(Vip vip) {
        List<ResvOrder> resvOrders = getResvOrders(vip.getId());
        List<ResvOrder> resvOrdersBy60days = getResvOrdersBy60day(vip.getId());

        CustomerValueList customerValueList = customerValueListService.getCustomerValueList(vip, resvOrders);
        VipConsumeActionTotal vipConsumeActionTotal = vipConsumeActionTotalService.getVipConsumeActionTotal(vip, resvOrders);
        VipConsumeActionLast60 vipConsumeActionLast60 = vipConsumeActionLast60Service.getVipConsumeActionLast60(vip, resvOrdersBy60days);

        customerValueListMapper.insert(customerValueList);
        vipConsumeActionTotalMapper.insert(vipConsumeActionTotal);
        vipConsumeActionLast60Mapper.insert(vipConsumeActionLast60);

        //todo 这里最后是需要修改的
        return true;
    }

    /**
     * 最近 60 天产生的消费订单-根据用餐时间
     *
     * @param vipId vip 表的主键
     */
    private List<ResvOrder> getResvOrdersBy60day(Integer vipId) {
        EntityWrapper<ResvOrder> wrapper = new EntityWrapper<>();
        wrapper.eq("vip_id", vipId);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.minusDays(60);
        wrapper.ge("created_at", start);
        wrapper.le("create_at", now);
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
