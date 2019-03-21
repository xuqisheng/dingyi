package com.zhidianfan.pig.user.dao.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhidianfan.pig.user.dao.entity.Table;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ljh
 * @since 2018-10-09
 */
public interface TableMapper extends BaseMapper<Table> {


    List<Table> findByTableTag(@Param("tag") String tag, @Param("businessId") Long businessId);
}
