package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderRating;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.ResvOrderRatingMapper;
import com.zhidianfan.pig.yd.moduler.common.service.IResvOrderRatingService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单评论表 服务实现类
 * </p>
 *
 * @author ljh
 * @since 2018-12-19
 */
@Service
public class ResvOrderRatingServiceImpl extends ServiceImpl<ResvOrderRatingMapper, ResvOrderRating> implements IResvOrderRatingService {

    @Override
    public ResvOrderRating selectVipLastInfo(Integer vipId) {
        return baseMapper.selectVipLastInfo(vipId);
    }
}
