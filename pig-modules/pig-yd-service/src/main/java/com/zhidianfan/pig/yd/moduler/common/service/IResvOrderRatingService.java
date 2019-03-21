package com.zhidianfan.pig.yd.moduler.common.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderRating;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 订单评论表 服务类
 * </p>
 *
 * @author ljh
 * @since 2018-12-19
 */
public interface IResvOrderRatingService extends IService<ResvOrderRating> {

    ResvOrderRating selectVipLastInfo(Integer vipId);
}
