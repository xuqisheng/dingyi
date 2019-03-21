package com.zhidianfan.pig.yd.moduler.resv.service;

import com.zhidianfan.pig.yd.moduler.common.service.IVipValueService;
import com.zhidianfan.pig.yd.moduler.resv.dto.VipValueDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VipValueService {

    @Autowired
    private IVipValueService iVipValueService;

    /**
     * 查询一个酒店启用的客户价值
     * @param businessId 酒店id
     * @return 酒店客户价值
     */
    public VipValueDTO  getBusinessVipValue(Integer businessId) {

        //状态1 为启用0 为停用
        VipValueDTO vipValue  = iVipValueService.getBusinessVipValue(businessId);

        return vipValue;
    }

    /**
     * 插入酒店默认客户价值
     * @param vipValueDTO 客户价值dto
     * @return 操作成功与否标志
     */
    public Boolean insertVIPValueInfo(VipValueDTO vipValueDTO) {
        Boolean b = iVipValueService.insertVIPValueInfo(vipValueDTO);
        return b;
    }

    /**
     * 更新酒店客户价值
     * @param vipValueDTO 客户价值dto
     * @return 操作成功与否标志
     */
    public Boolean editVIPValueInfo(VipValueDTO vipValueDTO) {
        Boolean b = iVipValueService.editVIPValueInfo(vipValueDTO);
        return  b;
    }
}
