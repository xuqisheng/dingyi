package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.VipValueRecord;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.VipValueRecordMapper;
import com.zhidianfan.pig.yd.moduler.common.service.IVipValueRecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhidianfan.pig.yd.moduler.resv.bo.VipValueBo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ljh
 * @since 2018-09-21
 */
@Service
public class VipValueRecordServiceImpl extends ServiceImpl<VipValueRecordMapper, VipValueRecord> implements IVipValueRecordService {

    @Override
    public List<VipValueBo> countByBusiness(Integer businessId) {
        return this.baseMapper.countByBusinessId(businessId);
    }
}
