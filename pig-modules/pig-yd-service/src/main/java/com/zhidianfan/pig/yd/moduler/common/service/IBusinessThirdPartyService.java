package com.zhidianfan.pig.yd.moduler.common.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessThirdParty;
import com.baomidou.mybatisplus.service.IService;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ThirdParty;

import java.util.List;

/**
 * <p>
 * 酒店和第三方平台关系表 服务类
 * </p>
 *
 * @author ljh
 * @since 2018-09-20
 */
public interface IBusinessThirdPartyService extends IService<BusinessThirdParty> {

    /**
     * 查找客户关联第三方平台(状态是有效的，当前日期在合同日期内)
     * @param businessId
     * @return
     */
    List<BusinessThirdParty> findThirdPartyByBusinessId(Integer businessId);

}
