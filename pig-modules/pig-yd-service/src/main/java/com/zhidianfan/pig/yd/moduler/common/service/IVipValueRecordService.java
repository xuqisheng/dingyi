package com.zhidianfan.pig.yd.moduler.common.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.VipValueRecord;
import com.baomidou.mybatisplus.service.IService;
import com.zhidianfan.pig.yd.moduler.resv.bo.VipValueBo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ljh
 * @since 2018-09-21
 */
public interface IVipValueRecordService extends IService<VipValueRecord> {


    /**
     * 统计酒店各种价值客户数量
     * @param businessId
     * @return
     */
    List<VipValueBo> countByBusiness(Integer businessId);
}
