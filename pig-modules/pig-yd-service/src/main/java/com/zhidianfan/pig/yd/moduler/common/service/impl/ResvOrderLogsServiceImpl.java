package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderLogs;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.ResvOrderLogsMapper;
import com.zhidianfan.pig.yd.moduler.common.service.IResvOrderLogsService;
import com.zhidianfan.pig.yd.moduler.resv.dto.ResvOrderLogsDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sherry
 * @since 2018-09-03
 */
@Service
public class ResvOrderLogsServiceImpl extends ServiceImpl<ResvOrderLogsMapper, ResvOrderLogs> implements IResvOrderLogsService {


    @Override
    public List<ResvOrderLogsDTO> selectOrderLogsByBatchNo(String batchNo) {
        List<ResvOrderLogsDTO> resvOrderLogs = baseMapper.selectOrderLogsByBatchNo(batchNo);
        return resvOrderLogs;
    }

    @Override
    public void insertMeetingOrderStatus2TO3(Integer intervalNum) {
        baseMapper.insertMeetingOrderStatus2TO3(intervalNum);
    }

    @Override
    public void insertMeetingOrderStatus1TO6(Integer intervalNum) {
        baseMapper.insertMeetingOrderStatus1TO6(intervalNum);
    }

    @Override
    public void insertOrderStatus1TO2(Integer intervalNum) {
        baseMapper.insertOrderStatus1TO2(intervalNum);
    }

    @Override
    public void insertOrderStatus2TO3(Integer intervalNum) {
        baseMapper.insertOrderStatus2TO3(intervalNum);
    }

    @Override
    public void insertAndroidOrderStatus1TO2(Integer intervalNum) {
        baseMapper.insertAndroidOrderStatus1TO2(intervalNum);
    }

    @Override
    public void insertAndroidOrderStatus2TO3(Integer intervalNum) {
        baseMapper.insertAndroidOrderStatus2TO3(intervalNum);
    }
}
