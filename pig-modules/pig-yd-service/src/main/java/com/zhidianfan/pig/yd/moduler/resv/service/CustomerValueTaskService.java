package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Business;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.CustomerValueTask;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessService;
import com.zhidianfan.pig.yd.moduler.common.service.ICustomerValueTaskService;
import com.zhidianfan.pig.yd.moduler.resv.constants.CustomerValueConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author sjl
 * 2019-05-27 14:08
 */
@Slf4j
@Service
public class CustomerValueTaskService {

    @Autowired
    private ICustomerValueTaskService customerValueTaskMapper;

    @Autowired
    private IBusinessService businessMapper;

    /**
     * 获取酒店 id
     *
     * @return 酒店 id
     */
    public List<CustomerValueTask> getCustomerValueTask() {
        EntityWrapper<CustomerValueTask> wrapper = new EntityWrapper<>();
        wrapper.eq("flag", 0);

        List<CustomerValueTask> customerValueTasks = customerValueTaskMapper.selectList(wrapper);
        List<CustomerValueTask> valueTasks = customerValueTasks.stream()
                .sorted(Comparator.comparing(CustomerValueTask::getSort)
                        .thenComparing(CustomerValueTask::getId, Comparator.reverseOrder())
                )
                .collect(Collectors.toList());
        return valueTasks;
    }

    /**
     * 按照排序顺序，获取需要跑批的任务
     *
     * @param planTime 批次时间
     * @return
     */
    public List<CustomerValueTask> getCustomerValuesValueTask(LocalDate planTime) {
        EntityWrapper<CustomerValueTask> wrapper = new EntityWrapper<>();
        wrapper.eq("plan_time", planTime);
        wrapper.in("flag", Arrays.asList(0,1,3));
        wrapper.orderBy("sort", false);

        List<CustomerValueTask> customerValueTasks = customerValueTaskMapper.selectList(wrapper);
        return customerValueTasks;
    }

    /**
     * 更新任务的状态
     *
     * @param flag             任务执行标记,0-未开始,1-执行中,2-执行成功,3-执行异常
     * @param exceptionMessage 异常信息
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void updateTaskStatus(Long taskId, Integer flag, LocalDateTime startTime, LocalDateTime endTime, String exceptionMessage) {
        CustomerValueTask task = new CustomerValueTask();
        task.setId(taskId);
        task.setStartTime(startTime);
        task.setEndTime(endTime);
        task.setFlag(flag);

        Duration duration = Duration.between(startTime, endTime);
        long seconds = duration.getSeconds();
        int spendTime = (int) Math.max(seconds, -1);

        task.setSpendTime(spendTime);
        task.setRemark(exceptionMessage);
        task.setUpdateTime(LocalDateTime.now());

        customerValueTaskMapper.updateById(task);
    }

    /**
     * 每天执行，把酒店全部插入到任务表中，后面数据计算都任务表中取酒店 id,然后根据酒店分别执行任务
     */
    public void addCustomerList() {
        log.info("开始执行 getCustomerList() ，将要执行的任务写入任务批次表中...");
        cleanData();
        try {
            // 查询出所有酒店,写入任务批次表中
            List<Business> businesses = getBusinessesAll();
            List<CustomerValueTask> valueTaskList = businesses.stream()
                    .map(business -> {
                        // 生成任务信息
                        CustomerValueTask task = new CustomerValueTask();
                        task.setTaskBatchNo(getBatchNo(business.getId()));
                        task.setHotelId(Long.valueOf(business.getId()));
                        Integer brandId = business.getBrandId();
                        task.setBrandId(brandId == null ? -1 : brandId);
                        task.setPlanTime(LocalDate.now());
                        task.setCreateTime(LocalDateTime.now());
                        task.setUpdateTime(LocalDateTime.now());
                        task.setStartTime(CustomerValueConstants.DEFAULT_START_TIME);
                        task.setEndTime(CustomerValueConstants.DEFAULT_START_TIME);
                        task.setSpendTime(CustomerValueConstants.DEFAULT_SPEND_TIME);
                        task.setFlag(CustomerValueConstants.NON_EXECUTE);
                        return task;
                    })
                    .collect(Collectors.toList());
            customerValueTaskMapper.insertBatch(valueTaskList);
            log.info("执行 getCustomerList() 完成,写入任务批次表中数据[{}]...", valueTaskList.size());
        } catch (Exception e) {
            log.error("任务执行发生异常...", e);
        }
    }

    private List<Business> getBusinessesAll() {
        return businessMapper.selectList(new EntityWrapper<>());
    }

    /**
     * 清除脏数据
     */
    private void cleanData() {
        Wrapper<CustomerValueTask> wrapper = new EntityWrapper<>();
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.of(0, 0, 0);
        LocalTime endTime = LocalTime.of(23, 59, 59);
        LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(date, endTime);
        wrapper.ge("create_time", startDateTime);
        wrapper.lt("create_time", endDateTime);
        customerValueTaskMapper.delete(wrapper);
    }

    /**
     * 生成批次号
     *
     * @param businessID 酒店 id
     * @return 批次号
     */
    private Long getBatchNo(int businessID) {
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String s = format + businessID;
        return Long.valueOf(s);
    }


}
