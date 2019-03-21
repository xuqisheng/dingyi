package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.Brand;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 潘钱勇
 * @since 2018-10-11
 */
public interface BrandMapper extends BaseMapper<Brand> {

    void bindDept(Brand brand);
}
