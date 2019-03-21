package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessMarketingSmsTemplate;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sherry
 * @since 2018-09-25
 */
public interface BusinessMarketingSmsTemplateMapper extends BaseMapper<BusinessMarketingSmsTemplate> {

    void UpdateAddUseNum(@Param("id") Integer id);

}
