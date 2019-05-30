package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Business;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.CustomerValueTask;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessService;
import com.zhidianfan.pig.yd.moduler.common.service.ICustomerValueTaskService;
import com.zhidianfan.pig.yd.moduler.resv.constants.CustomerValueConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public CustomerValueTask getCustomerValueTask() {
        EntityWrapper<CustomerValueTask> wrapper = new EntityWrapper<>();
        wrapper.eq("flag", 0);
        List<CustomerValueTask> customerValueTasks = customerValueTaskMapper.selectList(wrapper);
        Optional<CustomerValueTask> optionalCustomerValueTask = customerValueTasks.stream()
                .max(Comparator.comparing(CustomerValueTask::getSort)
                                .thenComparing(CustomerValueTask::getId, Comparator.reverseOrder())
                );
        return optionalCustomerValueTask.orElseThrow(RuntimeException::new);
    }

    /**
     * 更新任务的状态
     *
     * @param flag             任务执行标记,0-未开始,1-执行中,2-执行成功,3-执行异常
     * @param exceptionMessage 异常信息
     */
    public void updateTaskStatus(Long taskId, Integer flag, LocalDateTime startTime, LocalDateTime endTime, String exceptionMessage) {
        // todo 更新任务表状态
        CustomerValueTask task = new CustomerValueTask();
        task.setId(taskId);
        task.setStartTime(startTime);
        task.setEndTime(endTime);
        task.setFlag(flag);
        Duration duration = Duration.between(startTime, endTime);
        long seconds = duration.getSeconds();
        task.setSpendTime((int) seconds);
        task.setFlag(CustomerValueConstants.EXECUTE_SUCCESS);
        task.setRemark(exceptionMessage);
        task.setUpdateTime(LocalDateTime.now());
        task.setId(taskId);

        customerValueTaskMapper.updateById(task);
    }

    /**
     * 每天执行，把酒店全部插入到任务表中，后面数据计算都任务表中取酒店 id,然后根据酒店分别执行任务
     */
    public void addCustomerList() {
        log.info("开始执行 getCustomerList() ，将要执行的任务写入任务批次表中...");
        // todo 如果任务路途退出，需要清除之前跑了一半的脏数据
        try {
            // 查询出所有酒店,写入任务批次表中
            List<Business> businesses = businessMapper.selectList(new EntityWrapper<>());
            List<CustomerValueTask> valueTaskList = businesses.stream()
                    .map(business -> {
                        // 生成任务信息
                        CustomerValueTask task = new CustomerValueTask();
                        task.setTaskBatchNo(getBatchNo(business.getId()));
                        task.setHotelId(Long.valueOf(business.getId()));
                        Integer brandId = business.getBrandId();
                        if (brandId == null) {
                            brandId = -1;
                        }
                        task.setBrandId(brandId);
                        task.setPlanTime(LocalDate.now());
                        task.setCreateTime(LocalDateTime.now());
                        task.setUpdateTime(LocalDateTime.now());
                        task.setStartTime(CustomerValueConstants.DEFAULT_START_TIME);
                        task.setEndTime(CustomerValueConstants.DEFAULT_START_TIME);
                        task.setSpendTime(CustomerValueConstants.DEFAULT_SPEND_TIME);
                        task.setFlag(CustomerValueConstants.NON_EXECUTE);
                        return task;
                    }).collect(Collectors.toList());
            customerValueTaskMapper.insertBatch(valueTaskList);
            log.info("执行 getCustomerList() 完成,写入任务批次表中数据{}...", valueTaskList.size());
        } catch (Exception e) {
            log.error("任务执行发生异常...", e);
        }
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
