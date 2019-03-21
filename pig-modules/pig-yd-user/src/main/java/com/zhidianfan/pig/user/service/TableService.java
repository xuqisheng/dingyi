package com.zhidianfan.pig.user.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

import com.zhidianfan.pig.user.bo.DeskBo;
import com.zhidianfan.pig.user.bo.OrderBo;
import com.zhidianfan.pig.user.dao.entity.Table;
import com.zhidianfan.pig.user.dao.entity.TableArea;
import com.zhidianfan.pig.user.dao.service.ITableAreaService;
import com.zhidianfan.pig.user.dao.service.ITableService;
import com.zhidianfan.pig.user.dto.*;
import com.zhidianfan.pig.user.feign.OrderFeign;
import com.zhidianfan.pig.user.utils.IgnorePropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * 桌位相关业务
 *
 * @author LJH
 * @version 1.0
 * @Date 2018/9/19 13:38
 */
@Service
public class TableService {

    @Resource
    private ITableService iTableService;

    @Resource
    private ITableAreaService iTableAreaService;

    @Resource
    private OrderFeign orderFeign;

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


    public Tip tableAdd(TablesDTO tablesDTO){
        Table table = new Table();

        table.setTableName(tablesDTO.getTableName());
        table.setBusinessId(tablesDTO.getBusinessId());
        table.setStatus("1");
        //判断名字是否有重复
        int i = iTableService.selectCount(new EntityWrapper<>(table));
        if(i>0){
            throw new RuntimeException("桌位名称已存在");
        }
        //插入
        BeanUtils.copyProperties(tablesDTO,table);
        if(StringUtils.isBlank(tablesDTO.getAreaCode())){
            TableArea tableArea = iTableAreaService.selectById(tablesDTO.getTableAreaId());
            if(tableArea!=null){
                //区域名称
                table.setAreaCode(tableArea.getTableAreaName());
            }
        }

        boolean insert = iTableService.insert(table);

        return insert? SuccessTip.SUCCESS_TIP: ErrorTip.ERROR_TIP;
    }


    public Object tableEdit(TablesDTO tablesDTO){

        //排序
        List<SortIds> idAndSortIds = tablesDTO.getIdAndSortIds();
        if(idAndSortIds!=null&&idAndSortIds.size()>0) {
            ArrayList<Table> list = new ArrayList<>();
            for (SortIds idAndSortId : idAndSortIds) {
                Table area = new Table();
                area.setId(idAndSortId.getId());
                area.setSortId(idAndSortId.getSortIds());
                list.add(area);
            }
            boolean b = iTableService.updateBatchById(list);
            return b? SuccessTip.SUCCESS_TIP: ErrorTip.ERROR_TIP;
        }

        //更新其他
        Table table = new Table();
        Integer id = tablesDTO.getId();
        if(id==null){
            throw new RuntimeException("id不能为空");
        }

        //删除
        if(tablesDTO.getDelete()!=null&&tablesDTO.getDelete()){

            //判断是否有订单
            List<OrderBo> orderBos = haveOrder(tablesDTO);
            if (orderBos != null) {
                return orderBos;
            }
            //删除
            boolean delete = iTableService.deleteById(tablesDTO.getId());
            return delete? SuccessTip.SUCCESS_TIP: ErrorTip.ERROR_TIP;
        }
        //更新
        if("0".equals(tablesDTO.getStatus())){
            List<OrderBo> orderBos = haveOrder(tablesDTO);
            if(orderBos!=null){
                return orderBos;
            }
        }
        BeanUtils.copyProperties(tablesDTO,table, IgnorePropertiesUtil.getNullPropertyNames(tablesDTO));


        boolean update = iTableService.updateById(table);

        return update? SuccessTip.SUCCESS_TIP: ErrorTip.ERROR_TIP;
    }


    /**
     * 桌位是否有订单 返回整个批次
     * @param tablesDTO
     * @return
     */
    private List<OrderBo> haveOrder(TablesDTO tablesDTO) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBusinessId(tablesDTO.getBusinessId());
        orderDTO.setTableId(tablesDTO.getId());
        //已预订和已入座
        orderDTO.setStatus(Arrays.asList(1,2));
        List<OrderBo> orderBos = orderFeign.searchOrder(orderDTO);
        if(orderBos!=null&&orderBos.size()>0){
            ArrayList<OrderBo> batch = new ArrayList<>();
            //所有批次号
            HashSet<String> batchNos = new HashSet<>();
            for (OrderBo orderBo : orderBos) {
                batchNos.add(orderBo.getBatchNo());
            }
            for (String batchNo : batchNos) {
                List<String> tableNames = new ArrayList<>();
                List<OrderBo> boList = orderFeign.findByBatchNo(batchNo);
                //设置 批次号对应的所有桌位的名称
                for (OrderBo orderBo : boList) {
                    tableNames.add(orderBo.getTableName());
                    orderBo.setTableNames(tableNames);
                }
                batch.addAll(boList);
            }

            return batch;
        }
        return null;
    }


    @Transactional
    public boolean areaAdd(TableAreaDTO tableAreaDTO) {
        TableArea condition = new TableArea();
        condition.setBusinessId(tableAreaDTO.getBusinessId());
        EntityWrapper<TableArea> wrapper = new EntityWrapper<>(condition);
        wrapper.orderBy("sort_id",false);
        TableArea one = iTableAreaService.selectOne(wrapper);


        TableArea tableArea = new TableArea();
        BeanUtils.copyProperties(tableAreaDTO,tableArea);
        if(one!=null){
            tableArea.setSortId(one.getSortId()+1);
        }

        return iTableAreaService.insert(tableArea);
    }


    @Transactional(rollbackFor = Exception.class)
    public boolean areaEdit(TableAreaDTO tableAreaDTO) {

        //排序
        List<SortIds> idAndSortIds = tableAreaDTO.getIdAndSortIds();
        if(idAndSortIds!=null) {
            ArrayList<TableArea> list = new ArrayList<>();
            for (SortIds idAndSortId : idAndSortIds) {
                TableArea area = new TableArea();
                area.setId(idAndSortId.getId());
                area.setSortId(idAndSortId.getSortIds());
                list.add(area);
            }
            boolean b = iTableAreaService.updateBatchById(list);
            return b;
        }

        //编辑
        Integer id = tableAreaDTO.getId();
        if(id==null){
            throw new RuntimeException("id不能为空");
        }
        TableArea tableArea = new TableArea();
        BeanUtils.copyProperties(tableAreaDTO,tableArea);
        Boolean delete = tableAreaDTO.getDelete();
        boolean b;

        //删除
        if(delete!=null&&delete){
            //判断是否有桌位
            Table table = new Table();
            table.setTableAreaId(id);
            int i = iTableService.selectCount(new EntityWrapper<>(table));
            if(i>0){
                throw new RuntimeException("操作失败，请先删除区域内所有桌位");
            }
            b = iTableAreaService.deleteById(tableAreaDTO.getId());
        }else {
            String status = tableAreaDTO.getStatus();
            //禁用区域
            if( "0".equals(status)){
                //禁用区域下的所有桌位
                Table condition = new Table();
                condition.setTableAreaId(id);
                Table update = new Table();
                update.setStatus("0");
                iTableService.update(update,new EntityWrapper<>(condition));
            }
            b = iTableAreaService.updateById(tableArea);
        }

        return b;
    }
}
