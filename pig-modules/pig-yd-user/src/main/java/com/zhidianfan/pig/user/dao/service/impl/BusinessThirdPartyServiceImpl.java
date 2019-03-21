package com.zhidianfan.pig.user.dao.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhidianfan.pig.user.dao.entity.BusinessThirdParty;
import com.zhidianfan.pig.user.dao.mapper.BusinessThirdPartyMapper;
import com.zhidianfan.pig.user.dao.service.IBusinessThirdPartyService;
import org.springframework.stereotype.Service;

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
