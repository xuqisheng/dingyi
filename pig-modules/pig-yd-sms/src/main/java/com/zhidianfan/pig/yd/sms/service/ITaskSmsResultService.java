package com.zhidianfan.pig.yd.sms.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhidianfan.pig.yd.sms.dao.entity.TaskSmsResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hzp
 * @since 2018-11-02
 */
public interface ITaskSmsResultService extends IService<TaskSmsResult> {

    void updateChannelStatus(String beanName);
}
