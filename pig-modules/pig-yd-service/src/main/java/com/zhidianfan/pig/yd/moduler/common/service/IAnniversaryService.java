package com.zhidianfan.pig.yd.moduler.common.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Anniversary;
import com.baomidou.mybatisplus.service.IService;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Vip;
import com.zhidianfan.pig.yd.moduler.resv.bo.CustomerCareBO;
import com.zhidianfan.pig.yd.moduler.resv.dto.CustomerCareDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hzp
 * @since 2019-01-21
 */
public interface IAnniversaryService extends IService<Anniversary> {

    List<Anniversary> getPastAnniversaryVip();

    List<CustomerCareBO> selectCustomerCareInfoByPage(Page<CustomerCareDTO> page, CustomerCareDTO customerCareDTO);
}
