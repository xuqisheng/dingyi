package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.SmsMarketing;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Conan
 * @since 2018-09-10
 */
public interface SmsMarketingMapper extends BaseMapper<SmsMarketing> {

    Integer sendSmsNum(@Param("startDate") Date startDate, @Param("endDate")Date endDate,@Param("businessId") Integer businessId);

}
