package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.VipValue;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhidianfan.pig.yd.moduler.resv.dto.VipValueDTO;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ljh
 * @since 2018-10-29
 */
public interface VipValueMapper extends BaseMapper<VipValue> {

    VipValueDTO getBusinessVipValue(Integer businessId);

    Boolean insertVIPValueInfo(VipValueDTO vipValueDTO);

    void editVIPValueInfo1(VipValueDTO vipValueDTO);

    void editVIPValueInfo2(VipValueDTO vipValueDTO);

    void editVIPValueInfo3(VipValueDTO vipValueDTO);

    void editVIPValueInfo5(VipValueDTO vipValueDTO);

    void editVIPValueInfo6(VipValueDTO vipValueDTO);
}
