package com.zhidianfan.pig.yd.sms.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhidianfan.pig.yd.sms.dao.entity.TaskSmsResult;
import com.zhidianfan.pig.yd.sms.dao.mapper.TaskSmsResultMapper;
import com.zhidianfan.pig.yd.sms.service.ITaskSmsResultService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2018-11-02
 */
@Service
public class TaskSmsResultServiceImpl extends ServiceImpl<TaskSmsResultMapper, TaskSmsResult> implements ITaskSmsResultService {


    public void updateChannelStatus(String beanName) {

        //将结果刷入结果表
        TaskSmsResult taskSmsResult = new TaskSmsResult();
        taskSmsResult.setUpdateTime(new Date());
        taskSmsResult.setRemark("计算选择最优通道");
        taskSmsResult.setOperatorStatus(1);

        baseMapper.update(taskSmsResult, new EntityWrapper<TaskSmsResult>()
                .eq("bean_name", beanName));

        //设置其他通道为operator_status 为0
        TaskSmsResult otherTaskSmsResult = new TaskSmsResult();

        otherTaskSmsResult.setOperatorStatus(0);
        baseMapper.update(otherTaskSmsResult, new EntityWrapper<TaskSmsResult>()
                .ne("bean_name", beanName));

    }
}
