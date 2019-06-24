package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.Table;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.TableAreaImageDO;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.TableImageDO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ljh
 * @since 2018-10-09
 */
public interface TableMapper extends BaseMapper<Table> {

    /**
     * 根据标签查找对应桌位
     *
     * @param tag
     * @return
     */
    List<Table> findByTableTag(@Param("tag") String tag, @Param("businessId") Integer BusinessId);


    void updateTableAreaOpen(@Param("ids") String ids, @Param("tableAreaId") Integer tableAreaId);

    List<Table> selectFreeTable(@Param("businessId") Integer businessId, @Param("resvDate") Date resvDate, @Param("mealTypeId") Integer mealTypeId);


    List<TableImageDO> queryTableImage(@Param("tableAreaId") Integer tableAreaId, @Param("businessId") Integer businessId);

    List<TableAreaImageDO> queryTableAreaImage(@Param("businessId") Integer businessId);

    Integer businessHotMapSwitch(@Param("businessId") Integer businessId);
}
