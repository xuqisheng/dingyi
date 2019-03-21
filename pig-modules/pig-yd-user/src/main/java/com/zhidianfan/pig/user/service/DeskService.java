package com.zhidianfan.pig.user.service;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.user.bo.DeskBo;
import com.zhidianfan.pig.user.dao.entity.Table;
import com.zhidianfan.pig.user.dao.entity.TableArea;
import com.zhidianfan.pig.user.dao.service.ITableAreaService;
import com.zhidianfan.pig.user.dao.service.ITableService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 桌位相关业务
 *
 * @author LJH
 * @version 1.0
 * @Date 2018/9/19 13:38
 */
@Service
public class DeskService {

    @Autowired
    private ITableService iTableService;

    @Resource
    private ITableAreaService iTableAreaService;

    /**
     * 酒店桌位列表
     *
     * @param businessId
     * @param status
     * @return
     */
    public List<DeskBo> deskList(Integer businessId, String status) {

        Table condition = new Table();
        condition.setBusinessId(businessId);
        condition.setStatus(status);
        List<Table> list = iTableService.selectList(new EntityWrapper(condition));

        List<DeskBo> deskBos = new ArrayList<>();
        for (Table table : list) {
            DeskBo deskBo = new DeskBo();
            BeanUtils.copyProperties(table, deskBo);
            TableArea tableArea = iTableAreaService.selectById(table.getTableAreaId());
            if (tableArea != null) {
                deskBo.setTableAreaName(tableArea.getTableAreaName());
            }
            deskBos.add(deskBo);
        }
        return deskBos;
    }


}
