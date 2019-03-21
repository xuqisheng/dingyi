package com.zhidianfan.pig.push.controller.bo;



import lombok.Data;

import java.util.List;

@Data
public class ScheduleListResult{


    private int total_count;
    private int total_pages;
    private int page;
    private List<ScheduleResult> schedules;


}
