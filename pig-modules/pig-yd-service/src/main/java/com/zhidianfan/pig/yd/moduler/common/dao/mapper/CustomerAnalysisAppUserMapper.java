package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.CustomerAnalysisAppUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author wangyz
 * @version v 0.1 2019-04-16 16:12 wangyz Exp $
 */
@Mapper
public interface CustomerAnalysisAppUserMapper extends BaseMapper<CustomerAnalysisAppUser> {

    /**
     * 插入酒店所有的数据
     *
     * @param list
     * @return
     */
    void insertList(List<CustomerAnalysisAppUser> list);

    /**
     * 插入酒店数据
     *
     * @param param
     */
    void insertAwakenList(Map<String, Object> param);
}