package com.zhidianfan.pig.user.dao.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhidianfan.pig.user.dao.entity.Table;

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
    List<Table> findByTableTag(String tag,Long BusinessId);

}
