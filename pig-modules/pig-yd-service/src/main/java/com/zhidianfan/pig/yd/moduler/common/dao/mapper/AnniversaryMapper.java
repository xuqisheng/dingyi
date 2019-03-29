package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Anniversary;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhidianfan.pig.yd.moduler.resv.bo.CustomerCareBO;
import com.zhidianfan.pig.yd.moduler.resv.dto.CustomerCareDTO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2019-01-21
 */
public interface AnniversaryMapper extends BaseMapper<Anniversary> {

    List<Anniversary> getPastAnniversaryVip();

    List<CustomerCareBO> selectCustomerCareInfoByPage(Page<CustomerCareDTO> page, CustomerCareDTO customerCareDTO);
}
