package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.Brand;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.BrandMapper;
import com.zhidianfan.pig.yd.moduler.common.service.IBrandService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 潘钱勇
 * @since 2018-10-11
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {


    @Override
    public void bindDept(Brand brand) {
        this.baseMapper.bindDept(brand);
    }
}
