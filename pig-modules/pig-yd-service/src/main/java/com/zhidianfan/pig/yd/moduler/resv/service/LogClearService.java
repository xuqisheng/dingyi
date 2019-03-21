package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.LogOpenReqRes;
import com.zhidianfan.pig.yd.moduler.common.dao.service.ILogOpenReqResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2019-03-11
 * @Modified By:
 */
@Service
public class LogClearService {
    /**
     * log_open_req_res数据操作层
     */
    @Autowired
    private ILogOpenReqResService reqResService;

    @Async
    public void clearReqRes(Integer before) {
        reqResService.delete(
                new EntityWrapper<LogOpenReqRes>()
                        .lt("created_at", LocalDate.now().minusDays(before))
        );
    }
}
