package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessThirdParty;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ThirdParty;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 酒店和第三方平台关系表 Mapper 接口
 * </p>
 *
 * @author ljh
 * @since 2018-09-20
 */
public interface BusinessThirdPartyMapper extends BaseMapper<BusinessThirdParty> {


    List<BusinessThirdParty> findThirdPartyByBusinessId(@Param("businessId") Integer businessId);

}
