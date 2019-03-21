package com.zhidianfan.pig.user.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.user.dao.entity.BusinessTableType;
import com.zhidianfan.pig.user.dao.entity.Table;
import com.zhidianfan.pig.user.dao.entity.TableType;
import com.zhidianfan.pig.user.dao.service.IBusinessTableTypeService;
import com.zhidianfan.pig.user.dao.service.ITableService;
import com.zhidianfan.pig.user.dao.service.ITableTypeService;
import com.zhidianfan.pig.user.dto.BusinessTableTypeDTO;
import com.zhidianfan.pig.user.dto.OrderDTO;
import com.zhidianfan.pig.user.feign.OrderFeign;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;


/**
 * @Description:
 * @User: ljh
 * @Date: 2018-11-07
 * @Time: 11:19
 */
@Service
public class BusinessTableTypeService {


    @Resource
    private ITableTypeService iTableTypeService;
    @Resource
    private ITableService iTableService;


    public boolean typeAdd(BusinessTableTypeDTO businessTableTypeDTO) {


        TableType businessTableType = new TableType();

        String tableTypeName = businessTableTypeDTO.getName();
        if(StringUtils.isBlank(tableTypeName)){
            throw new RuntimeException("桌型名称不能为空");
        }
        businessTableType.setName(tableTypeName.trim());
        int i = iTableTypeService.selectCount(new EntityWrapper<>(businessTableType));
        if(i>0){
            throw new RuntimeException("桌型名称已存在");
        }

        BeanUtils.copyProperties(businessTableTypeDTO, businessTableType);
        boolean insert = iTableTypeService.insert(businessTableType);
        return insert;
    }


    public boolean typeEdit(BusinessTableTypeDTO businessTableTypeDTO) {
        TableType businessTableType = new TableType();
        Integer id = businessTableTypeDTO.getId();
        if(id==null){
           throw new RuntimeException("id不能为空");
       }
        Boolean delete = businessTableTypeDTO.getDelete();
        if(delete!=null&&delete){
            //判断是不是有桌位在使用
            Table table = new Table();
            table.setStatus("1");
            table.setTableTypeId(id);
            int i = iTableService.selectCount(new EntityWrapper<>(table));
            if(i>0){
                throw new RuntimeException("有桌位正在使用此桌型请先删除桌位");
            }
            iTableTypeService.deleteById(id);
       }
        BeanUtils.copyProperties(businessTableTypeDTO, businessTableType);
        boolean b = iTableTypeService.updateById(businessTableType);
        return b;
    }





}
