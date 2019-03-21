package com.zhidianfan.pig.yd.moduler.common.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvLine;
import com.zhidianfan.pig.yd.moduler.resv.dto.LineQueryDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.LineTableDTO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ljh
 * @since 2018-09-25
 */
public interface IResvLineService extends IService<ResvLine> {

    void conditionFindVips(Page<LineTableDTO> page, LineQueryDTO lineQueryDTO);

    List<LineTableDTO> excelConditionFindLineOrders(LineQueryDTO lineQueryDTO);
}
