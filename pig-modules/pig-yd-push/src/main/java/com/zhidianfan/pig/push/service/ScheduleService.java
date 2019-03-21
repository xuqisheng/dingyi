package com.zhidianfan.pig.push.service;


import com.zhidianfan.pig.common.constant.Tip;
import com.zhidianfan.pig.push.controller.bo.ScheduleListResult;
import com.zhidianfan.pig.push.controller.bo.ScheduleResult;
import com.zhidianfan.pig.push.controller.dto.schedule.ScheduleDTO;

public interface ScheduleService {


    /**
     * 创建定时任务
     * @param scheduleDTO
     * @return
     */
    ScheduleResult createSchedule(ScheduleDTO scheduleDTO);

    /**
     * 获取有效的 Schedule 列表
     * @param page
     * @return
     */
    ScheduleListResult schedulesList(Integer page);


    /**
     * 删除指定的 Schedule 任务
     * @param scheduleId
     * @return
     */
    Tip deleteSchedule(String scheduleId);




}
