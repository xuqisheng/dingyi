package com.zhidianfan.pig.yd.moduler.resv.task;

import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.resv.service.LogClearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2019-03-11
 * @Modified By:
 */
@RestController
@RequestMapping("/log/clear")
public class LogClearTask {
    @Autowired
    private LogClearService logClearService;

    @GetMapping("/req/res")
    public ResponseEntity clearReqRes(Integer before) {

        logClearService.clearReqRes(before);
        return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
    }
}
