package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.VipValueRecord;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhidianfan.pig.yd.moduler.resv.bo.VipValueBo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ljh
 * @since 2018-09-21
 */
public interface VipValueRecordMapper extends BaseMapper<VipValueRecord> {


    List<VipValueBo> countByBusinessId(Integer businessId);

}
