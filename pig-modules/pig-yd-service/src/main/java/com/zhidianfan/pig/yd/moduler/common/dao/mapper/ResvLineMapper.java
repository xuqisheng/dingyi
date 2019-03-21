package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvLine;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhidianfan.pig.yd.moduler.resv.dto.LineQueryDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.LineTableDTO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ljh
 * @since 2018-09-25
 */
public interface ResvLineMapper extends BaseMapper<ResvLine> {

    List<LineTableDTO> conditionFindVips(Page<LineTableDTO> page, LineQueryDTO lineQueryDTO);

    List<LineTableDTO> excelConditionFindLineOrders(LineQueryDTO lineQueryDTO);
}
