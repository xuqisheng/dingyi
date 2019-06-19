package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.Table;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.TableAreaImageDO;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.TableImageDO;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.TableMapper;
import com.zhidianfan.pig.yd.moduler.common.service.ITableService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ljh
 * @since 2018-10-09
 */
@Service
public class TableServiceImpl extends ServiceImpl<TableMapper, Table> implements ITableService {

    @Override
    public List<Table> findByTableTag(String tag, Integer businessId) {
        return this.baseMapper.findByTableTag(tag, businessId);
    }

    @Override
    public void updateTableAreaOpen(String ids, Integer tableAreaId) {

        this.baseMapper.updateTableAreaOpen(ids, tableAreaId);
    }

    @Override
    public List<Table> selectFreeTable(Integer businessId, Date resvDate, Integer mealTypeId) {
        return this.baseMapper.selectFreeTable(businessId, resvDate, mealTypeId);
    }

    @Override
    public List<TableImageDO> queryTableImage(Integer tableAreaId, Integer businessId) {
        return this.baseMapper.queryTableImage(tableAreaId, businessId);
    }

    @Override
    public List<TableAreaImageDO> queryTableAreaImage(Integer businessId) {
        return this.baseMapper.queryTableAreaImage(businessId);
    }


    @Override
    public Integer businessHotMapSwitch(Integer businessId) {
        return this.baseMapper.businessHotMapSwitch(businessId);
    }

}
