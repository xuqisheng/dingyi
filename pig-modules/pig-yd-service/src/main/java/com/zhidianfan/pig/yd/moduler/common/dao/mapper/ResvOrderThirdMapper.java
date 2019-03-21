package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderThird;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhidianfan.pig.yd.moduler.resv.bo.ResvOrderThirdBO;
import com.zhidianfan.pig.yd.moduler.resv.dto.ThirdQueryDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sherry
 * @since 2018-09-20
 */
public interface ResvOrderThirdMapper extends BaseMapper<ResvOrderThird> {

    ResvOrderThirdBO getNewestOrder(@Param("businessId")Integer businessId);

    List<ResvOrderThirdBO> getThirdOrder(Page<ResvOrderThirdBO> page, ThirdQueryDTO tq);
}
