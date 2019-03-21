package com.zhidianfan.pig.push.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.push.dao.entity.PushMessage;
import com.zhidianfan.pig.push.service.ReportService;
import com.zhidianfan.pig.push.service.base.IPushMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/4
 * @Modified By:
 */
@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private IPushMessageService messageService;
    @Autowired
    private ReportService reportService;
    @PostMapping("check")
    public void checkStatus(){
        List<PushMessage> pushMessages = messageService.selectList(new EntityWrapper<PushMessage>().eq("send_status", 0));
        pushMessages.forEach(pushMessage -> {
            reportService.checkStatus(pushMessage.getPushId());
        });
    }
}
