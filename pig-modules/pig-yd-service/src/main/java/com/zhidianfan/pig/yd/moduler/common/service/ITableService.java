package com.zhidianfan.pig.yd.moduler.common.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.Table;
import com.baomidou.mybatisplus.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ljh
 * @since 2018-10-09
 */
public interface ITableService extends IService<Table> {

    /**
     * 根据标签查找对应桌位
     * @param tag
     * @return
     */
    List<Table> findByTableTag(String tag, Integer BusinessId);


    /**
     * 更新指定id和区域的桌位开启
     * @param ids
     * @param tableAreaId
     */
    void updateTableAreaOpen(String ids,Integer tableAreaId);

    /**
     * 查找该酒店该时段的空闲桌位
     * @param businessId 酒店id
     * @param resvDate 预定日期
     * @param mealTypeId 餐别id
     * @return
     */
    List<Table> selectFreeTable(Integer businessId, Date resvDate, Integer mealTypeId);
}
