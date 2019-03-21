package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvLine;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.ResvLineMapper;
import com.zhidianfan.pig.yd.moduler.common.service.IResvLineService;
import com.zhidianfan.pig.yd.moduler.resv.dto.LineQueryDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.LineTableDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ljh
 * @since 2018-09-25
 */
@Service
public class ResvLineServiceImpl extends ServiceImpl<ResvLineMapper, ResvLine> implements IResvLineService {

    @Override
    public void conditionFindVips(Page<LineTableDTO> page, LineQueryDTO lineQueryDTO) {

        List<LineTableDTO> resvLineOrders = this.baseMapper.conditionFindVips(page, lineQueryDTO);
        page.setRecords(resvLineOrders);
    }

    @Override
    public List<LineTableDTO> excelConditionFindLineOrders(LineQueryDTO lineQueryDTO) {
        return this.baseMapper.excelConditionFindLineOrders(lineQueryDTO);
    }
}
