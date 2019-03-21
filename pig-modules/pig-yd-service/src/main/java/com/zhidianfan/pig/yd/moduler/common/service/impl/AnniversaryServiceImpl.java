package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.Anniversary;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.AnniversaryMapper;
import com.zhidianfan.pig.yd.moduler.common.service.IAnniversaryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
}
