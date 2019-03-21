package com.zhidianfan.pig.yd.moduler.common.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.VipValue;
import com.baomidou.mybatisplus.service.IService;
import com.zhidianfan.pig.yd.moduler.resv.dto.VipValueDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ljh
 * @since 2018-10-29
 */
public interface IVipValueService extends IService<VipValue> {


    VipValueDTO getBusinessVipValue(Integer businessId);

    Boolean insertVIPValueInfo(VipValueDTO vipValueDTO);

    Boolean editVIPValueInfo(VipValueDTO vipValueDTO);
}
