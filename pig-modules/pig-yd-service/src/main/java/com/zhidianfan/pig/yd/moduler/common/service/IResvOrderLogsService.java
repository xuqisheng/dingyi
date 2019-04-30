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

    void insertMeetingOrderStatus2TO3(Integer intervalNum);


    void insertMeetingOrderStatus1TO6(Integer intervalNum);

    void insertOrderStatus1TO2(Integer intervalNum);

    void insertOrderStatus2TO3(Integer intervalNum);

    void insertAndroidOrderStatus1TO2(Integer intervalNum);

    void insertAndroidOrderStatus2TO3(Integer intervalNum);
}
