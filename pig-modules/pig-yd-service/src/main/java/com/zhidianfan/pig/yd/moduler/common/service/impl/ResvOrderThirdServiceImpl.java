package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderThird;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.ResvOrderThirdMapper;
import com.zhidianfan.pig.yd.moduler.common.service.IResvOrderThirdService;
import com.zhidianfan.pig.yd.moduler.resv.bo.ResvOrderThirdBO;
import com.zhidianfan.pig.yd.moduler.resv.dto.ThirdQueryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sherry
 * @since 2018-09-20
 */
@Service
public class ResvOrderThirdServiceImpl extends ServiceImpl<ResvOrderThirdMapper, ResvOrderThird> implements IResvOrderThirdService {


    @Override
    public ResvOrderThirdBO getNewestOrder(Integer businessId) {
        ResvOrderThirdBO newestOrder = baseMapper.getNewestOrder(businessId);
        return newestOrder;
    }

    @Override
    public Page<ResvOrderThirdBO> getThirdOrder(Page<ResvOrderThirdBO> page, ThirdQueryDTO thirdQueryDTO) {

        List<ResvOrderThirdBO> thirdOrderList  = baseMapper.getThirdOrder(page,thirdQueryDTO);
        return page.setRecords(thirdOrderList);
    }

    @Override
    public Page<ResvOrderThirdBO> getWeChatThirdOrder(Page<ResvOrderThirdBO> page, ThirdQueryDTO thirdQueryDTO) {

        List<ResvOrderThirdBO> thirdOrderList  = baseMapper.getWeChatThirdOrder(page,thirdQueryDTO);
        return page.setRecords(thirdOrderList);
    }

    @Override
    public List<ResvOrderThirdBO> getAllThirdOrder(ThirdQueryDTO thirdQueryDTO) {

        List<ResvOrderThirdBO> thirdOrderList  = baseMapper.getAllThirdOrder(thirdQueryDTO);
        return thirdOrderList;
    }


}
