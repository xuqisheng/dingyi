package com.zhidianfan.pig.user.dao.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhidianfan.pig.user.dao.entity.Table;
import com.zhidianfan.pig.user.dao.mapper.TableMapper;
import com.zhidianfan.pig.user.dao.service.ITableService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ljh
 * @since 2018-10-09
 */
@Service
public class TableServiceImpl extends ServiceImpl<TableMapper, Table> implements ITableService {

    @Override
    public List<Table> findByTableTag(String tag,Long businessId) {
        return this.baseMapper.findByTableTag(tag,businessId);
    }
}
