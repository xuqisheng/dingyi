package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessThirdParty;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ThirdParty;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.BusinessThirdPartyMapper;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessThirdPartyService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 酒店和第三方平台关系表 服务实现类
 * </p>
 *
 * @author ljh
 * @since 2018-09-20
 */
@Service
public class BusinessThirdPartyServiceImpl extends ServiceImpl<BusinessThirdPartyMapper, BusinessThirdParty> implements IBusinessThirdPartyService {

    @Override
    public List<BusinessThirdParty> findThirdPartyByBusinessId(Integer businessId) {

        return this.baseMapper.findThirdPartyByBusinessId(businessId);
    }
}
