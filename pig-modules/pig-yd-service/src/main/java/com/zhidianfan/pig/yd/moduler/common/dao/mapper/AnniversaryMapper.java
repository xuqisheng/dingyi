package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.Anniversary;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2019-01-21
 */
public interface AnniversaryMapper extends BaseMapper<Anniversary> {

    List<Anniversary> getPastAnniversaryVip();
}
