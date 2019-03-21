package com.zhidianfan.pig.zipkin.web;

import com.zhidianfan.pig.zipkin.dto.CommonRes;
import com.zhidianfan.pig.zipkin.service.ZipLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/10/26
 * @Modified By:
 */
@RestController
public class ZipLogController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ZipLogService zipLogService;
    /**
     * 删除zipkin_annotations、zipkin_dependencies、zipkin_spans，三张表的全部数据
     * @return
     */
    @PostMapping("/ziplog/delete/all")
    public ResponseEntity deleteAllZipkinData(@RequestParam String taskId){

        zipLogService.deleteAllZipkinData(taskId);

        return ResponseEntity.ok(CommonRes.SUCCESS1);
    }

    /**
     * 清空SYS_LOG_(0~9)的数据
     * @return
     */
    @PostMapping("/syslog/delete/all")
    public ResponseEntity deleteAllSysLogData(@RequestParam String taskId){

        zipLogService.deleteAllSysLogData(taskId);

        return ResponseEntity.ok(CommonRes.SUCCESS1);
    }

}
