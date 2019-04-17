package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

import com.google.common.collect.Maps;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderAndroid;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Table;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.TableArea;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.TableType;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.common.service.IResvOrderAndroidService;
import com.zhidianfan.pig.yd.moduler.common.service.ITableAreaService;
import com.zhidianfan.pig.yd.moduler.common.service.ITableService;
import com.zhidianfan.pig.yd.moduler.common.service.ITableTypeService;
import com.zhidianfan.pig.yd.moduler.resv.bo.DeskBo;

import com.zhidianfan.pig.yd.moduler.resv.bo.OrderBO;
import com.zhidianfan.pig.yd.moduler.resv.dto.*;
import com.zhidianfan.pig.yd.moduler.resv.enums.OrderStatus;
import com.zhidianfan.pig.yd.utils.IgnorePropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
    private OrderService orderService;

    @Resource
    private IResvOrderAndroidService iResvOrderService;

    @Resource
    private ITableTypeService iTableTypeService;

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
        List<Table> list = iTableService.selectList(new EntityWrapper(condition).orderBy("created_at", true));

        List<DeskBo> deskBos = new ArrayList<>();
        for (Table table : list) {
            DeskBo deskBo = new DeskBo();
            BeanUtils.copyProperties(table, deskBo);
            TableArea tableArea = iTableAreaService.selectById(table.getTableAreaId());
            if (tableArea != null) {
                deskBo.setTableAreaName(tableArea.getTableAreaName());
            }
            TableType tableType = iTableTypeService.selectById(table.getTableTypeId());
            if (tableType != null) {
                deskBo.setTableTypeName(tableType.getName());
            }

            deskBos.add(deskBo);
        }
        return deskBos;
    }


    public Tip tableAdd(TablesDTO tablesDTO) {
        Table table = new Table();

        table.setTableName(tablesDTO.getTableName());
        table.setBusinessId(tablesDTO.getBusinessId());
        table.setStatus("1");
        //判断名字是否有重复
        int i = iTableService.selectCount(new EntityWrapper<>(table));
        if (i > 0) {
            throw new RuntimeException("桌位名称已存在");
        }
        //插入
        BeanUtils.copyProperties(tablesDTO, table);
        if (StringUtils.isBlank(tablesDTO.getAreaCode())) {
            TableArea tableArea = iTableAreaService.selectById(tablesDTO.getTableAreaId());
            if (tableArea != null) {
                //区域名称
                table.setAreaCode(tableArea.getTableAreaName());
            }
        }
        TableType tableType = iTableTypeService.selectById(tablesDTO.getTableTypeId());
        if (tableType != null) {
            table.setMinPeopleNum(tableType.getMinPeopleNum() + "");
            table.setMaxPeopleNum(tableType.getMaxPeopleNum() + "");
        }


        Integer ttype = tablesDTO.getTtype();
        if (ttype != null) {
            table.settType(ttype);
        }
        table.setCreatedAt(new Date());
        //获得最后的sortId
        Table sort = new Table();
        sort.setBusinessId(tablesDTO.getBusinessId());
        Table one = iTableService.selectOne(new EntityWrapper<>(sort).orderBy("sort_id", false));
        if (one != null) {
            table.setSortId(one.getSortId() + 1);
        }
        boolean insert = iTableService.insert(table);

        return insert ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP;
    }


    public Object tableEdit(TablesDTO tablesDTO) {

        //排序
        List<SortIds> idAndSortIds = tablesDTO.getIdAndSortIds();
        if (idAndSortIds != null && idAndSortIds.size() > 0) {
            ArrayList<Table> list = new ArrayList<>();
            for (SortIds idAndSortId : idAndSortIds) {
                Table area = new Table();
                area.setId(idAndSortId.getId());
                area.setSortId(idAndSortId.getSortIds());
                list.add(area);
            }
            boolean b = iTableService.updateBatchById(list);
            return b ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP;
        }

        //更新其他
        Table table = new Table();
        Integer id = tablesDTO.getId();
        if (id == null) {
            throw new RuntimeException("id不能为空");
        }

        //删除
        if (tablesDTO.getDelete() != null && tablesDTO.getDelete()) {

            //判断是否有订单
            List<OrderBO> orderBos = haveOrder(tablesDTO.getBusinessId(), tablesDTO.getId());
            if (orderBos != null) {
                //同一批次值展示一条
                orderBos = orderBos.stream().collect(
                        Collectors.collectingAndThen(
                                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(OrderBO::getBatchNo))), ArrayList::new));
                return orderBos;
            }
            //删除
            boolean delete = iTableService.deleteById(tablesDTO.getId());
            return delete ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP;
        }
        //更新
        if ("0".equals(tablesDTO.getStatus())) {
            List<OrderBO> orderBos = haveOrder(tablesDTO.getBusinessId(), tablesDTO.getId());
            if (orderBos != null) {
                //同一批次值展示一条
                orderBos = orderBos.stream().collect(
                        Collectors.collectingAndThen(
                                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(OrderBO::getBatchNo))), ArrayList::new));
                return orderBos;
            }
        }
        BeanUtils.copyProperties(tablesDTO, table, IgnorePropertiesUtil.getNullPropertyNames(tablesDTO));

        //更新桌型
        Integer tableTypeId = tablesDTO.getTableTypeId();
        if (tableTypeId != null) {
            TableType tableType = iTableTypeService.selectById(tableTypeId);
            if (tableType != null) {
                table.setMinPeopleNum(tableType.getMinPeopleNum() + "");
                table.setMaxPeopleNum(tableType.getMaxPeopleNum() + "");
            }
        }
        //更新区域
        if (tablesDTO.getTableAreaId() != null) {
            Table sort = new Table();
            sort.setTableAreaId(tablesDTO.getTableAreaId());
            sort.setBusinessId(tablesDTO.getBusinessId());
            Table one = iTableService.selectOne(new EntityWrapper<>(sort).orderBy("sort_id", false));
            if (one != null) {
                table.setSortId(one.getSortId() + 1);
            }
        }

        Integer ttype = tablesDTO.getTtype();
        if (ttype != null) {
            table.settType(ttype);
        }
        boolean update = iTableService.updateById(table);

        return update ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP;
    }


    /**
     * 桌位是否有订单 返回整个批次
     *
     * @return
     */
    private List<OrderBO> haveOrder(Integer businessId, Integer tableId) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBusinessId(businessId);
        orderDTO.setTableId(tableId);
        //已预订和已入座
        orderDTO.setStatus(Arrays.asList(1, 2));
        List<ResvOrderAndroid> orderBos = orderService.searchOrders(orderDTO);
        if (orderBos != null && orderBos.size() > 0) {
            ArrayList<OrderBO> batch = new ArrayList<>();
            //所有批次号
            HashSet<String> batchNos = new HashSet<>();
            for (ResvOrderAndroid orderBo : orderBos) {
                batchNos.add(orderBo.getBatchNo());
            }
            for (String batchNo : batchNos) {
                List<String> tableNames = new ArrayList<>();
                ResvOrderAndroid resvOrderId = new ResvOrderAndroid();
                resvOrderId.setBatchNo(batchNo);
                List<ResvOrderAndroid> resvOrder = iResvOrderService.selectList(new EntityWrapper<>(resvOrderId));
                //设置 批次号对应的所有桌位的名称
                for (ResvOrderAndroid orderBo : resvOrder) {
                    tableNames.add(orderBo.getTableName());
                    OrderBO bo = new OrderBO();
                    BeanUtils.copyProperties(orderBo, bo);
                    bo.setTableNames(tableNames);
                    batch.add(bo);
                }
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
        wrapper.orderBy("sort_id", false);
        TableArea one = iTableAreaService.selectOne(wrapper);


        TableArea tableArea = new TableArea();
        BeanUtils.copyProperties(tableAreaDTO, tableArea);
        if (one != null) {
            tableArea.setSortId(one.getSortId() + 1);
        }

        return iTableAreaService.insert(tableArea);
    }


    @Transactional(rollbackFor = Exception.class)
    public boolean areaEdit(TableAreaDTO tableAreaDTO) {

        //排序操作
        List<SortIds> idAndSortIds = tableAreaDTO.getIdAndSortIds();
        if (idAndSortIds != null) {
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
        if (id == null) {
            throw new RuntimeException("id不能为空");
        }
        TableArea byId = iTableAreaService.selectById(id);
        if (byId == null) {
            throw new RuntimeException("桌位区域不存在");
        }

        TableArea tableArea = new TableArea();
        BeanUtils.copyProperties(tableAreaDTO, tableArea);
        Boolean delete = tableAreaDTO.getDelete();
        boolean b;

        //删除
        if (delete != null && delete) {
            //判断是否有桌位
            Table table = new Table();
            table.setTableAreaId(id);
            int i = iTableService.selectCount(new EntityWrapper<>(table));
            if (i > 0) {
                throw new RuntimeException("操作失败，请先删除区域内所有桌位");
            }
            b = iTableAreaService.deleteById(tableAreaDTO.getId());
        } else {
            String status = tableAreaDTO.getStatus();
            //禁用区域
            if ("0".equals(status)) {
                Table condition = new Table();
                condition.setTableAreaId(id);
                condition.setBusinessId(tableAreaDTO.getBusinessId());
                condition.setStatus("1");
                //判断区域下是否有桌位订单
                List<Table> tables = iTableService.selectList(new EntityWrapper<>(condition));
                for (Table table : tables) {
                    List<OrderBO> orderBOS = haveOrder(table.getBusinessId(), table.getId());
                    if (orderBOS != null && orderBOS.size() > 0) {
                        throw new RuntimeException("操作失败，请先完成区域内桌位的订单");
                    }
                }
                //记录停用的桌位
                List<Integer> tableIds = tables.stream().map(Table::getId).collect(Collectors.toList());
                tableArea.setStatusChangeTableIds(StringUtils.join(tableIds, ","));


                //禁用区域下的所有桌位
                Table update = new Table();
                update.setStatus("0");
                iTableService.update(update, new EntityWrapper<>(condition));
            } else if ("1".equals(status)) {
                //启用区域
                Integer recover = tableAreaDTO.getRecover();
                if ("0".equals(recover)) {
                    String statusChangeTableIds = byId.getStatusChangeTableIds();
                    if (StringUtils.isBlank(statusChangeTableIds)) {
                        //上次状态为空 启动全部
                        recover = 1;
                    }
                    //启用上次记录桌位
                    iTableService.updateTableAreaOpen(statusChangeTableIds, id);
                }
                if ("1".equals(recover)) {
                    //启用所有
                    Table condition = new Table();
                    condition.setTableAreaId(tableAreaDTO.getId());
                    condition.setBusinessId(tableAreaDTO.getBusinessId());

                    Table table = new Table();
                    table.setStatus("1");
                    iTableService.update(table, new EntityWrapper<>(condition));
                }

            }

            b = iTableAreaService.updateById(tableArea);
        }

        return b;
    }

    /**
     * 获取空闲桌位状态
     *
     * @param deskOrderDTO
     * @return 桌位状态
     */
    public Map<String, Integer> deskStatus(DeskOrderDTO deskOrderDTO) {

        //入座查询条件
        EntityWrapper<ResvOrderAndroid> resvOrderEntityWrapper = new EntityWrapper<>();
        //预定查询条件
        EntityWrapper<ResvOrderAndroid> resvOrderEntityWrapper2 = new EntityWrapper<>();
        //锁台查询条件
        EntityWrapper<ResvOrderAndroid> resvOrderEntityWrapper3 = new EntityWrapper<>();
        //桌位数查询条件
        EntityWrapper<Table> tableEntityWrapper = new EntityWrapper<>();

        Integer tableAreaId = deskOrderDTO.getTableAreaId();
        if (null != tableAreaId) {
            resvOrderEntityWrapper.eq("table_area_id", deskOrderDTO.getTableAreaId());
            resvOrderEntityWrapper2.eq("table_area_id", deskOrderDTO.getTableAreaId());
            resvOrderEntityWrapper3.eq("table_area_id", deskOrderDTO.getTableAreaId());
            tableEntityWrapper.eq("table_area_id", deskOrderDTO.getTableAreaId());
        }
        resvOrderEntityWrapper.eq("business_id", deskOrderDTO.getBusinessId());
        resvOrderEntityWrapper.eq("resv_date", deskOrderDTO.getResvDate());
        resvOrderEntityWrapper.eq("meal_type_id", deskOrderDTO.getMealTypeId());


        //查询入座的桌位数量
        int seatCount = iResvOrderService.selectCount(resvOrderEntityWrapper.eq("business_id", deskOrderDTO.getBusinessId())
                .eq("resv_date", deskOrderDTO.getResvDate())
                .eq("meal_type_id", deskOrderDTO.getMealTypeId())
                .eq("status", OrderStatus.HAVE_SEAT.code));


        resvOrderEntityWrapper2.eq("business_id", deskOrderDTO.getBusinessId());
        resvOrderEntityWrapper2.eq("resv_date", deskOrderDTO.getResvDate());
        resvOrderEntityWrapper2.eq("meal_type_id", deskOrderDTO.getMealTypeId());

        //查询预定的桌位数量
        int reserveCount = iResvOrderService.selectCount(resvOrderEntityWrapper2.eq("business_id", deskOrderDTO.getBusinessId())
                .eq("resv_date", deskOrderDTO.getResvDate())
                .eq("meal_type_id", deskOrderDTO.getMealTypeId())
                .eq("status", OrderStatus.RESERVE.code));


        //查询锁台总数
        int lockCount = iResvOrderService.selectCount(resvOrderEntityWrapper3.eq("business_id", deskOrderDTO.getBusinessId())
                .eq("resv_date", deskOrderDTO.getResvDate())
                .eq("meal_type_id", deskOrderDTO.getMealTypeId())
                .eq("status", OrderStatus.LOCK.code));


        //桌子总数
        tableEntityWrapper.eq("business_id", deskOrderDTO.getBusinessId());
        tableEntityWrapper.eq("status", 1);

        int allTbaleNum = iTableService.selectCount(tableEntityWrapper);

        int freeTbaleNum = allTbaleNum - reserveCount - seatCount - lockCount;

        HashMap<String, Integer> deskStatusMap = Maps.newHashMap();
        deskStatusMap.put("seatCount", seatCount);
        deskStatusMap.put("reserveCount", reserveCount);
        deskStatusMap.put("lockCount", lockCount);
        deskStatusMap.put("freeTbaleNum", freeTbaleNum);

        return deskStatusMap;
    }


    /**
     * 获取空闲桌子最大容纳数
     * @param businessid 酒店id
     * @param mealtypeid 餐别id
     * @param resvdate   预约日期
     * @return
     */
    public Integer getFreeTableMaxCapacity(Integer businessid, Integer mealtypeid, Date resvdate) {

        List<Table> tables = iTableService.selectFreeTable(businessid, resvdate, mealtypeid);
        if (tables.size() != 0){
            Integer maxNum = 0;
            for (Table table: tables) {
                maxNum =  (Integer.valueOf(table.getMaxPeopleNum()) > maxNum ? Integer.valueOf(table.getMaxPeopleNum()) : maxNum);
            }
            return  maxNum;
        }
        return  0 ;
    }
}
