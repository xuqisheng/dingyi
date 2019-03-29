package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Anniversary;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.AnniversaryMapper;
import com.zhidianfan.pig.yd.moduler.common.service.IAnniversaryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhidianfan.pig.yd.moduler.resv.bo.CustomerCareBO;
import com.zhidianfan.pig.yd.moduler.resv.dto.CustomerCareDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hzp
 * @since 2019-01-21
 */
@Service
public class AnniversaryServiceImpl extends ServiceImpl<AnniversaryMapper, Anniversary> implements IAnniversaryService {

    @Override
    public List<Anniversary> getPastAnniversaryVip() {
        return baseMapper.getPastAnniversaryVip();
    }

    @Override
    public List<CustomerCareBO> selectCustomerCareInfoByPage(Page<CustomerCareDTO> page, CustomerCareDTO customerCareDTO) {
        return baseMapper.selectCustomerCareInfoByPage(page,customerCareDTO);
    }
}
