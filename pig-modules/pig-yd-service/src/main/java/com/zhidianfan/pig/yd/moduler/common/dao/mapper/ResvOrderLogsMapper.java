package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderLogs;
import com.zhidianfan.pig.yd.moduler.resv.dto.ResvOrderLogsDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sherry
 * @since 2018-09-03
 */
public interface ResvOrderLogsMapper extends BaseMapper<ResvOrderLogs> {

    List<ResvOrderLogsDTO> selectOrderLogsByBatchNo(@Param("batchNo") String batchNo);
}
