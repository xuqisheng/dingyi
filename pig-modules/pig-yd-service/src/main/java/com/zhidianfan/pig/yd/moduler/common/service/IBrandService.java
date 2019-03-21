package com.zhidianfan.pig.yd.moduler.common.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.Brand;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 潘钱勇
 * @since 2018-10-11
 */
public interface IBrandService extends IService<Brand> {

    /**
     * 绑定微服务中国呢的部门id
     * @param brand
     */
    void bindDept(Brand brand);

}
