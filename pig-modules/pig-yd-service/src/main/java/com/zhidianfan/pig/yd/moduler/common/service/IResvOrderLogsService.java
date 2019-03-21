package com.zhidianfan.pig.yd.moduler.common.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderLogs;
import com.zhidianfan.pig.yd.moduler.resv.dto.ResvOrderLogsDTO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sherry
 * @since 2018-09-03
 */
public interface IResvOrderLogsService extends IService<ResvOrderLogs> {

    List<ResvOrderLogsDTO> selectOrderLogsByBatchNo(String batchNo);
}
