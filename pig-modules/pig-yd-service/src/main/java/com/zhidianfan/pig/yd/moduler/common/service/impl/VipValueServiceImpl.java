package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.VipValue;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.VipValueMapper;
import com.zhidianfan.pig.yd.moduler.common.service.IVipValueService;
import com.zhidianfan.pig.yd.moduler.resv.dto.VipValueDTO;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ljh
 * @since 2018-10-29
 */
@Service
public class VipValueServiceImpl extends ServiceImpl<VipValueMapper, VipValue> implements IVipValueService {


    @Override
    public VipValueDTO getBusinessVipValue(Integer businessId) {
        return baseMapper.getBusinessVipValue(businessId);
    }

    @Override
    public Boolean insertVIPValueInfo(VipValueDTO vipValueDTO) {
        return baseMapper.insertVIPValueInfo(vipValueDTO);
    }

    @Override
    public Boolean editVIPValueInfo(VipValueDTO vipValueDTO) {
        //更新活跃用户
        baseMapper.editVIPValueInfo1(vipValueDTO);
        //更新沉睡用户
        baseMapper.editVIPValueInfo2(vipValueDTO);
        //更新流失用户
        baseMapper.editVIPValueInfo3(vipValueDTO);
        //更新恶意用户
        baseMapper.editVIPValueInfo5(vipValueDTO);
        //更新高价值用户
        baseMapper.editVIPValueInfo6(vipValueDTO);
        return true;
    }
}
