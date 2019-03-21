package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderRating;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 订单评论表 Mapper 接口
 * </p>
 *
 * @author ljh
 * @since 2018-12-19
 */
public interface ResvOrderRatingMapper extends BaseMapper<ResvOrderRating> {

    ResvOrderRating selectVipLastInfo(@Param("vipId") Integer vipId);
}
