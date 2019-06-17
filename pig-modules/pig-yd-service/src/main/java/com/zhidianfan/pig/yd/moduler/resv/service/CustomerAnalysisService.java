package com.zhidianfan.pig.yd.moduler.resv.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.CustomerAnalysisAppUserMapper;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.CustomerAnalysisMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 客户分析接口
 *
 * @author wangyzget/appUserStatistics
 * @version v 0.1 2019-03-19 15:06 wangyz Exp $
 */
@Service
public class CustomerAnalysisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerAnalysisService.class);

    @Autowired
    private CustomerAnalysisMapper customerAnalysisMapper;

    @Autowired
    private CustomerAnalysisAppUserMapper customerAnalysisAppUserMapper;

    private static BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 10, 10000, TimeUnit.MILLISECONDS, queue);

    /**
     * 执行上个月的客户分析
     *
     * @param nowDate 定时器执行当天时间  比如  2019-03-01 00:00:00
     */
    public void customerAnalysis(@NotNull LocalDateTime nowDate) {
        //上个月的年月
        final String date = nowDate.minusDays(1).getYear() + "-" + (nowDate.minusMonths(1).getMonthValue() > 9 ? nowDate.minusMonths(1).getMonthValue() : "0" + nowDate.minusMonths(1).getMonthValue());
        //上上个月的年月
        final String oldDate = nowDate.minusMonths(2).getYear() + "-" + (nowDate.minusMonths(2).getMonthValue() > 9 ? nowDate.minusMonths(2).getMonthValue() : "0" + nowDate.minusMonths(2).getMonthValue());
        //清理旧数据
        cleanData(date);
        //获取所有酒店id
        final List<Integer> businessIds = customerAnalysisMapper.queryBusinessIds();
        //先插入所有酒店当月的客户分析结果
        for (Integer businessId : businessIds) {
            BusinessCustomerAnalysis customerAnalysis = new BusinessCustomerAnalysis();
            customerAnalysis.setDate(date);
            customerAnalysis.setBusinessId(businessId);
            customerAnalysisMapper.insert(customerAnalysis);
        }

        //执行新增用户客户分析
        threadPool.execute(() -> new NewVipRunnable(date, nowDate, businessIds).run());
        //执行沉睡用户客户分析
        threadPool.execute(() -> new SleepVipRunnable(date, oldDate, nowDate, businessIds).run());
        //执行流失用户客户分析
        threadPool.execute(() -> new FlowVipRunnable(date, oldDate, nowDate, businessIds).run());
        //执行唤醒用户客户分析
        threadPool.execute(() -> new AwakenVipRunnable(date, oldDate, nowDate, businessIds).run());
    }


    /**
     * 清理脏数据
     */
    private void cleanData(String date) {
        customerAnalysisMapper.cleanCustomerAnalysis(date);
    }


    /**
     * 更新客户分析
     *
     * @param count 数量
     * @param type  1:新增用户
     *              2:活跃用户
     *              3:沉睡用户
     *              4:流失用户
     *              5:唤醒用户
     *              6:恶意用户
     *              7:高价值用户
     */
    private void updateCustomerAnalysis(Integer businessId, String date, Integer count, Integer type) {
        BusinessCustomerAnalysis customerAnalysis = new BusinessCustomerAnalysis();
        customerAnalysis.setDate(date);
        customerAnalysis.setBusinessId(businessId);

        switch (type) {
            case 1:
                customerAnalysis.setNewVipCount(count);
                break;
            case 2:
                customerAnalysis.setActiveVipCount(count);
                break;
            case 3:
                customerAnalysis.setSleepVipCount(count);
                break;
            case 4:
                customerAnalysis.setFlowVipCount(count);
                break;
            case 5:
                customerAnalysis.setAwakenVipCount(count);
                break;
            case 6:
                customerAnalysis.setEvilVipCount(count);
                break;
            case 7:
                customerAnalysis.setHighValueVipCount(count);
                break;
        }


        customerAnalysisMapper.updateByBusinessIdAndDate(customerAnalysis);
    }

    /**
     * 检查客户价值配置
     *
     * @param vipValue 客户价值
     * @param type     类型  1:活跃用户  2:沉睡用户  3:流失用户  4:意向用户   5:恶意用户  6:高价值用户
     * @return true  验证通过   false  参数为空或者0
     */
    private Boolean checkVipValue(VipValue vipValue, Integer type) {
        if (vipValue == null)
            return Boolean.FALSE;

        //活跃用户  活跃天数为空或者0  false
        if (Integer.valueOf(1).equals(type)) {
            if (checkDays(vipValue.getActiveDays()))
                return Boolean.TRUE;

            return Boolean.FALSE;
        }

        //沉睡用户  活跃天数和沉睡天数为空或者0  false
        if (Integer.valueOf(2).equals(type)) {
            if (checkDays(vipValue.getActiveDays()) && checkDays(vipValue.getFlowDays()))
                return Boolean.TRUE;

            return Boolean.FALSE;
        }

        //流失用户  流失天数为空或者0  false
        if (Integer.valueOf(3).equals(type)) {
            if (checkDays(vipValue.getFlowDays()))
                return Boolean.TRUE;

            return Boolean.FALSE;
        }

        //恶意用户  退订单数为空或者0  false
        if (Integer.valueOf(5).equals(type)) {
            if (checkDays(vipValue.getUnorderCount()))
                return Boolean.TRUE;

            return Boolean.FALSE;
        }

        //高价值用户  活跃天数  订单数量  订单金额不能为空
        if (Integer.valueOf(6).equals(type)) {
            if (checkDays(vipValue.getHighValueDays()) && checkDays(vipValue.getHighValueCounts()) && vipValue.getHighValueAmount() != null)
                return Boolean.TRUE;

            return Boolean.FALSE;
        }

        return Boolean.FALSE;
    }

    /**
     * 插入数据    如果数据量小于100  直接插入 否则分次插入
     *
     * @param customerAnalysisList 明细
     */
    private void insertDetail(List<BusinessCustomerAnalysisDetail> customerAnalysisList) {
        if (CollectionUtils.isEmpty(customerAnalysisList))
            return;

        if (customerAnalysisList.size() <= 100)
            customerAnalysisMapper.insertBusinessCustomerAnalysisDetail(customerAnalysisList);
        else {
            List<BusinessCustomerAnalysisDetail> list = Lists.newArrayList();
            for (int i = 1; i <= customerAnalysisList.size(); i++) {
                list.add(customerAnalysisList.get(i - 1));
                if (i % 100 == 0) {
                    customerAnalysisMapper.insertBusinessCustomerAnalysisDetail(list);
                    list.clear();
                }
            }
            if (CollectionUtils.isNotEmpty(list))
                customerAnalysisMapper.insertBusinessCustomerAnalysisDetail(list);
        }
    }


    /**
     * 转换数据
     *
     * @param vips 用户
     * @param type 1:活跃用户  2:沉睡用户  3:流失用户  7:唤醒   8:新用户
     * @return 明细列表
     */
    private List<BusinessCustomerAnalysisDetail> getCustomerAnalysisDetail(List<Vip> vips, Integer type, String date, Integer businessId) {
        if (CollectionUtils.isEmpty(vips))
            return Lists.newArrayList();

        List<BusinessCustomerAnalysisDetail> list = Lists.newArrayList();
        for (int i = 0; i < vips.size(); i++) {
            BusinessCustomerAnalysisDetail detail = new BusinessCustomerAnalysisDetail();
            detail.setDate(date);
            detail.setVipValueType(type);
            detail.setVipId(vips.get(i).getId());
            detail.setBusinessId(businessId);
            detail.setVipName(vips.get(i).getVipName());
            detail.setVipPhone(vips.get(i).getVipPhone());
            list.add(detail);
        }

        return list;
    }

    /**
     * 检查是否为空 或者数值为0
     *
     * @param days 检查的时间
     * @return true : 不为空并且不为0  false : 为空或者为0
     */
    private Boolean checkDays(Integer days) {
        return days != null && days > 0;
    }


    ///////////////////////////////////////
    //              线程对象              //
    ///////////////////////////////////////

    /**
     * 新增用户
     */
    @Data
    class NewVipRunnable implements Runnable {

        /**
         * 日期   yyyy-MM
         */
        private String date;

        /**
         * 开始时间 例如:2019-02-01 00:00:00
         */
        private LocalDateTime beginDay;

        /**
         * 结束时间 例如:2019-03-01 00:00:00
         */
        private LocalDateTime endDay;

        /**
         * 酒店id
         */
        private List<Integer> businessIds;

        NewVipRunnable(String date, LocalDateTime nowDate, List<Integer> businessIds) {
            this.date = date;
            this.businessIds = businessIds;
            this.endDay = nowDate;
            this.beginDay = this.endDay.minusMonths(1);
        }

        @Override
        public void run() {
            try {
                StopWatch watch = new StopWatch();
                watch.start();
                for (Integer businessId : businessIds) {

                    List<Vip> vips = customerAnalysisMapper.getNewVip(businessId, beginDay, endDay);

                    updateCustomerAnalysis(businessId, date, vips.size(), 1);
                    List<BusinessCustomerAnalysisDetail> list = getCustomerAnalysisDetail(vips, 8, date, businessId);

                    if (CollectionUtils.isNotEmpty(list))
                        customerAnalysisMapper.insertBusinessCustomerAnalysisDetail(list);

                    //获得所有营销经理相关
                    List<Map<String, Object>> map = customerAnalysisMapper.getAppUserNewVip(businessId, beginDay, endDay);

                    List<CustomerAnalysisAppUser> appUsers = Lists.newArrayList();
                    for (Map<String, Object> m : map) {
                        CustomerAnalysisAppUser user = new CustomerAnalysisAppUser();
                        user.setDate(date);
                        user.setBusinessId(businessId);
                        user.setNewVipNum(MapUtils.getInteger(m, "new_vip_num"));
                        user.setAppUserId(MapUtils.getInteger(m, "app_user_id"));
                        user.setAppUserName(MapUtils.getString(m, "app_user_name"));
                        appUsers.add(user);
                    }
                    customerAnalysisAppUserMapper.insertList(appUsers);
                }
                watch.stop();
                Map<String, Object> param = Maps.newHashMap();
                param.put("taskName", "新增用户");
                param.put("taskTime", watch.getTime());
                customerAnalysisMapper.insertTask(param);
            } catch (Exception e) {
                LOGGER.info("客服分析新增用户数据异常:", e);
            }

        }
    }


    /**
     * 沉睡用户
     */
    @Data
    @AllArgsConstructor
    class SleepVipRunnable implements Runnable {


        /**
         * 日期   yyyy-MM
         */
        private String date;

        /**
         * 上个月的日期
         */
        private String oldDate;

        /**
         * 时间 例如:2019-03-01 00:00:00
         */
        private LocalDateTime nowDate;

        /**
         * 酒店id
         */
        private List<Integer> businessIds;

        @Override
        public void run() {
            try {
                StopWatch watch = new StopWatch();
                watch.start();
                for (Integer businessId : businessIds) {
                    VipValue vipValue = customerAnalysisMapper.queryVipValueByBusinessId(businessId, 2);

                    if (!checkVipValue(vipValue, 2))
                        continue;
                    //获得本月所有沉睡用户
                    LocalDateTime beginDay = nowDate.minusDays(vipValue.getActiveDays());
                    LocalDateTime lastDay = nowDate.minusDays(vipValue.getFlowDays());
                    List<Vip> nowMonthVips = customerAnalysisMapper.getSleepVip(businessId, beginDay, lastDay, nowDate);
                    //获得上个月所有沉睡用户
                    LocalDateTime oldDate = nowDate.minusMonths(1);
                    LocalDateTime oldBeginDay = oldDate.minusDays(vipValue.getActiveDays());
                    LocalDateTime oldLastDay = oldDate.minusDays(vipValue.getFlowDays());
                    List<Vip> oldMonthVips = customerAnalysisMapper.getSleepVip(businessId, oldBeginDay, oldLastDay, nowDate);

                    if (CollectionUtils.isNotEmpty(nowMonthVips)) {
                        if (CollectionUtils.isNotEmpty(oldMonthVips)) {
                            LOGGER.info(JSONObject.toJSONString(nowMonthVips));
                            LOGGER.info(JSONObject.toJSONString(oldMonthVips));
                            //去重
                            for (int i = 0; i < nowMonthVips.size(); i++) {
                                Vip vip = nowMonthVips.get(i);
                                for (Vip oldVip : oldMonthVips) {
                                    if (vip.getId().equals(oldVip.getId())) {
                                        nowMonthVips.remove(vip);
                                        i--;
                                    }
                                }
                            }
                        }
                        List<BusinessCustomerAnalysisDetail> list = getCustomerAnalysisDetail(nowMonthVips, 2, date, businessId);
                        insertDetail(list);
                        updateCustomerAnalysis(businessId, date, list.size(), 3);
                    } else {
                        updateCustomerAnalysis(businessId, date, 0, 3);
                    }

                }

                watch.stop();
                Map<String, Object> param = Maps.newHashMap();
                param.put("taskName", "沉睡用户");
                param.put("taskTime", watch.getTime());
                customerAnalysisMapper.insertTask(param);
            } catch (Exception e) {
                LOGGER.info("客服分析沉睡用户数据异常:", e);
            }
        }
    }


    /**
     * 流失用户
     */
    @Data
    @AllArgsConstructor
    class FlowVipRunnable implements Runnable {

        /**
         * 日期   yyyy-MM
         */
        private String date;

        /**
         * 上个月的时间
         */
        private String oldDate;

        /**
         * 时间 例如:2019-03-01 00:00:00
         */
        private LocalDateTime nowDate;

        /**
         * 酒店id
         */
        private List<Integer> businessIds;

        @Override
        public void run() {
            try {
                StopWatch watch = new StopWatch();
                watch.start();
                for (Integer businessId : businessIds) {
                    VipValue vipValue = customerAnalysisMapper.queryVipValueByBusinessId(businessId, 3);
                    VipValue activeValue = customerAnalysisMapper.queryVipValueByBusinessId(businessId, 1);

                    if (!checkVipValue(vipValue, 3))
                        continue;
                    //获得流失用户的时间范围
                    LocalDateTime beginDay = nowDate.minusDays(vipValue.getFlowDays());
                    LocalDateTime oldBeginTime = beginDay.minusDays(activeValue.getActiveDays());

                    //获得所有流失用户
                    List<Vip> vips = customerAnalysisMapper.getFlowVip(businessId, beginDay, nowDate, oldBeginTime, beginDay);

                    if (CollectionUtils.isNotEmpty(vips)) {
                        List<BusinessCustomerAnalysisDetail> list = getCustomerAnalysisDetail(vips, 3, date, businessId);
                        insertDetail(list);
                        updateCustomerAnalysis(businessId, date, list.size(), 4);
                    } else {
                        updateCustomerAnalysis(businessId, date, 0, 4);
                    }
                }
                watch.stop();
                Map<String, Object> param = Maps.newHashMap();
                param.put("taskName", "流失用户");
                param.put("taskTime", watch.getTime());
                customerAnalysisMapper.insertTask(param);
            } catch (Exception e) {
                LOGGER.info("客服分析流失用户数据异常:", e);
            }

        }
    }


    /**
     * 唤醒用户
     */
    @Data
    @AllArgsConstructor
    class AwakenVipRunnable implements Runnable {

        /**
         * 日期   yyyy-MM
         */
        private String date;

        /**
         * 上个月的时间
         */
        private String oldDate;

        /**
         * 时间 例如:2019-03-01 00:00:00
         */
        private LocalDateTime nowDate;

        /**
         * 酒店id
         */
        private List<Integer> businessIds;

        @Override
        public void run() {
            try {
                StopWatch watch = new StopWatch();
                watch.start();
                for (Integer businessId : businessIds) {
                    VipValue sleepValue = customerAnalysisMapper.queryVipValueByBusinessId(businessId, 2);
                    VipValue flowValue = customerAnalysisMapper.queryVipValueByBusinessId(businessId, 3);
                    if (!checkVipValue(sleepValue, 2))
                        continue;

                    if (!checkVipValue(flowValue, 3))
                        continue;


                    //获得这个月的第一天
                    LocalDateTime beginDay = nowDate.minusMonths(1);
                    //获得所有这个月内下过单的用户
                    List<Vip> list = customerAnalysisMapper.getActiveVip(businessId, beginDay, nowDate);

                    if (CollectionUtils.isNotEmpty(list)) {


                        List<Vip> sleepList = customerAnalysisMapper.getSleepVip(businessId, beginDay.minusDays(sleepValue.getActiveDays()), beginDay.minusDays(sleepValue.getFlowDays()), beginDay);

                        List<Vip> flowList = customerAnalysisMapper.getFlowVip(businessId, beginDay.minusDays(flowValue.getFlowDays()), beginDay, null, null);
                        if (CollectionUtils.isEmpty(sleepList) && CollectionUtils.isEmpty(flowList))
                            return;
                        if (sleepList == null)
                            sleepList = Lists.newArrayList();

                        CollectionUtils.removeAll(sleepList, flowList);
                        List<Vip> vips = Lists.newArrayList();
                        for (Vip vip : list) {
                            for (Vip sleepVip : sleepList) {
                                if (vip.getId().equals(sleepVip.getId()))
                                    vips.add(vip);
                            }
                        }

                        List<BusinessCustomerAnalysisDetail> awakenList = getCustomerAnalysisDetail(vips, 7, date, businessId);
                        insertDetail(awakenList);
                        updateCustomerAnalysis(businessId, date, awakenList.size(), 5);
                        Map<String, Object> param = Maps.newHashMap();
                        param.put("vip", awakenList);
                        param.put("date", date);
                        param.put("begin", beginDay);
                        param.put("end", nowDate);
                        customerAnalysisAppUserMapper.insertAwakenList(param);
                    } else {
                        updateCustomerAnalysis(businessId, date, 0, 5);
                    }
                }
                watch.stop();
                Map<String, Object> param = Maps.newHashMap();
                param.put("taskName", "唤醒用户");
                param.put("taskTime", watch.getTime());
                customerAnalysisMapper.insertTask(param);
            } catch (Exception e) {
                LOGGER.info("客服分析唤醒用户数据异常:", e);
            }

        }
    }
}