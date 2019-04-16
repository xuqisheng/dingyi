package com.zhidianfan.pig.yd.moduler.common.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderThird;
import com.zhidianfan.pig.yd.moduler.resv.bo.ResvOrderThirdBO;
import com.zhidianfan.pig.yd.moduler.resv.dto.ThirdQueryDTO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sherry
 * @since 2018-09-20
 */
public interface IResvOrderThirdService extends IService<ResvOrderThird> {

    ResvOrderThirdBO getNewestOrder(Integer businessId);


    Page<ResvOrderThirdBO> getThirdOrder(Page<ResvOrderThirdBO> page, ThirdQueryDTO thirdQueryDTO);

    List<ResvOrderThirdBO> getAllThirdOrder(ThirdQueryDTO thirdQueryDTO);

    Page<ResvOrderThirdBO> getWeChatThirdOrder(Page<ResvOrderThirdBO> page, ThirdQueryDTO thirdQueryDTO);
}
