package com.zhidianfan.pig.push.controller;

import com.zhidianfan.pig.common.constant.Tip;
import com.zhidianfan.pig.push.controller.bo.ScheduleListResult;
import com.zhidianfan.pig.push.controller.bo.ScheduleResult;
import com.zhidianfan.pig.push.controller.dto.schedule.ScheduleDTO;
import com.zhidianfan.pig.push.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Description: 定时推送任务
 * User: ljh
 * Date: 2018-11-04
 * Time: 19:53
 */
@RestController
@RequestMapping("schedule")
public class ScheduleController {

    @Resource
    private ScheduleService scheduleService;

    @PostMapping("add")
    public ResponseEntity add(@RequestBody  ScheduleDTO scheduleDTO){
        ScheduleResult schedule = scheduleService.createSchedule(scheduleDTO);
        return ResponseEntity.ok(schedule);
    };

    @GetMapping("list")
    public ResponseEntity list(Integer page){
        ScheduleListResult scheduleListResult = scheduleService.schedulesList(page);
         return ResponseEntity.ok(scheduleListResult);
    };

    @GetMapping("delete")
    public ResponseEntity add(String scheduleId){
        Tip tip = scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.ok(tip);
    };
}
