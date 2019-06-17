package com.zhidianfan.pig.yd.moduler.resv.service;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.xiaoleilu.hutool.date.DateUtil;
import com.zhidianfan.pig.common.util.PageFactory;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.common.service.*;
import com.zhidianfan.pig.yd.moduler.resv.bo.*;
import com.zhidianfan.pig.yd.moduler.resv.dto.*;
import com.zhidianfan.pig.yd.moduler.resv.enums.OrderStatus;
import com.zhidianfan.pig.yd.moduler.resv.qo.LockTablQO;
import com.zhidianfan.pig.yd.utils.ExcelUtil;
import com.zhidianfan.pig.yd.utils.IdUtils;
import com.zhidianfan.pig.yd.utils.YDDateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @Author panqianyong
 * @Description 订单删除
 * @Date Create in 2018/9/5
 * @Modified By:
 */
@Service
public class OrderService {


    /**
     * 预订单接口
     */

    @Autowired
    private IResvOrderAndroidService resvOrderService;
    @Autowired
    private IResvOrderHisService resvOrderHisService;

    /**
     * 宴会预订单接口
     */
    @Autowired
    private IResvMeetingOrderService resvMeetingOrderService;
    @Autowired
    private IResvMeetingOrderHisService resvMeetingOrderHsService;

    @Resource
    private ITableService iTableService;


    @Resource
    private MessageSource messageSource;

    @Resource
    private IVipService iVipService;



    @Autowired
    private IResvOrderLogsService iResvOrderLogsService;


    @Resource
    private IResvOrderRatingService iResvOrderRatingService;

    /**
     * 删除普通预订订单
     *
     * @param resvOrderDTO
     * @return
     */
    public boolean deleteOrders(ResvOrderDTO resvOrderDTO) {
        resvOrderDTO.setResvDateEndLimit(DateUtil.lastMonth());
        resvOrderHisService.copyResvOrders(resvOrderDTO);
        resvOrderService.deleteResvOrders(resvOrderDTO);
        return true;
    }

    /**
     * 删除宴会预订订单
     *
     * @param resvMeetingOrderDTO
     * @return
     */
    public boolean deleteMeetingOrders(ResvMeetingOrderDTO resvMeetingOrderDTO) {
        resvMeetingOrderDTO.setResvDateEndLimit(DateUtil.lastMonth());
        resvMeetingOrderHsService.copyResvMeetingOrders(resvMeetingOrderDTO);
        resvMeetingOrderService.deleteResvMeetingOrders(resvMeetingOrderDTO);
        return true;
    }


    /**
     * 订单搜索 桌位和预定相关
     * @param deskOrderDTO
     * @return
     */
    public List<DeskOrderBo> orderSearch(DeskOrderDTO deskOrderDTO){
        //单个桌位订单一天不会超过9999
        List<DeskOrderBo> resvOrders = resvOrderService.findDeskOrders(new Page(0,9999),deskOrderDTO);
        return resvOrders;
    }

    /**
     * 酒店历史订单
     * @param deskOrderDTO
     * @return
     */
    public Page<DeskOrderBo> historyOrders (DeskOrderDTO deskOrderDTO){

        Page<DeskOrderBo> page = new PageFactory().defaultPage();

        if(deskOrderDTO.getBusinessId()==null){
            return page;
        }

        deskOrderDTO.setNow(new Date());
        List<DeskOrderBo> resvOrders = resvOrderService.findDeskOrders(page,deskOrderDTO);
        //所有批次
        Set<String> batchNos = resvOrders.stream().map(DeskOrderBo::getBatchNo).collect(Collectors.toSet());
        //批次对应订单
        for (String batchNo : batchNos) {
            List<String> tables = new ArrayList<>();
            for (DeskOrderBo resvOrder : resvOrders) {
                if(batchNo.equals(resvOrder.getBatchNo())){
                    tables.add(resvOrder.getTableName());
                    resvOrder.setTables(tables);
                }
            }
        }
        //去重
        resvOrders=
                resvOrders.stream().collect(
                        Collectors.collectingAndThen(
                                Collectors.toCollection(
                                        ()->new TreeSet<>(
                                                Comparator.comparing(DeskOrderBo::getBatchNo))),ArrayList::new));
        page.setRecords(resvOrders);
        return  page;
    }





    /**
     * 超时订单查询
     * @param deskOrderDTO
     * @return
     */
    public List<DeskOrderBo> timeoutOrderSearch(DeskOrderDTO deskOrderDTO){

        List<DeskOrderBo> list = new ArrayList<>();

        //查找指定订单
        ResvOrderAndroid resvOrderCondition = new ResvOrderAndroid();
        BeanUtils.copyProperties(deskOrderDTO,resvOrderCondition);
        List<ResvOrderAndroid> resvOrders = resvOrderService.selectList(new EntityWrapper<>(resvOrderCondition));

        //判断是否超时
        resvOrders.forEach((resvOrder)->{
            String destTime = resvOrder.getDestTime();
            if(StringUtils.isBlank(destTime)){
                //没有就餐时间
                return;
            }
            Date resvDate = YDDateUtils.formatDateTime(YDDateUtils.formatDate(resvOrder.getResvDate())+" "+destTime+":00");
            //桌位超时时间
            Table table = iTableService.selectById(resvOrder.getTableId());
            if(table==null){
                return;
            }

            LocalDateTime nowTime = LocalDateTime.now();
            //Date timeoutDate = DateUtils.addMinutes(resvDate, table.getReservedTime());
            LocalDateTime timeout = LocalDateTime.ofInstant(resvDate.toInstant(), ZoneId.systemDefault());
            if(nowTime.isAfter(timeout)){
                //超时订单
                DeskOrderBo deskOrderBo = new DeskOrderBo();
                BeanUtils.copyProperties(resvOrder,deskOrderBo);
                Vip vip = iVipService.selectById(resvOrder.getVipId());
                if(vip!=null) {
                    deskOrderBo.setVipValueName(vip.getVipValueName());
                    deskOrderBo.setAllergen(vip.getAllergen());
                }

                //严重超时固定为30分钟
                Integer timeoutTime =30;
                if (timeoutTime != null && timeoutTime != 0) {
                    Date timeoutDate1 = DateUtils.addMinutes(resvDate, timeoutTime);
                    LocalDateTime timeout1 = LocalDateTime.ofInstant(timeoutDate1.toInstant(), ZoneId.systemDefault());
                    if (nowTime.isAfter(timeout1)) {
                        deskOrderBo.setTimeoutLevel(1);
                    }
                }
                list.add(deskOrderBo);
            }
        });

        //计算批次桌位和人数
        List<String> tables = new ArrayList<>();
            list.forEach((timeoutOrder)->{
                Integer headcount=0;
                ResvOrderAndroid batchNo = new ResvOrderAndroid();
                batchNo.setBatchNo(timeoutOrder.getBatchNo());
                List<ResvOrderAndroid> batchOrder = resvOrderService.selectList(new EntityWrapper<>(batchNo));
                for (ResvOrderAndroid resvOrder : batchOrder) {
                    tables.add(resvOrder.getTableName());
                    if( StringUtils.isNotBlank(resvOrder.getResvNum()))
                        headcount+=Integer.valueOf(resvOrder.getResvNum());
                }
                timeoutOrder.setHeadcount(headcount);
                timeoutOrder.setTables(tables);
        });

        return list;
    }





    /**
     * 预定订单修改
     * @param reserveOrderEditDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public Tip editReserve(ReserveOrderEditDTO reserveOrderEditDTO) {

        //退订
        Boolean debook = reserveOrderEditDTO.getDebook();
        if(debook!=null&&debook){
            ResvOrderAndroid status = new ResvOrderAndroid();
            status.setStatus(OrderStatus.DEBOOK.code);

            ResvOrderAndroid batchNo = new ResvOrderAndroid();
            batchNo.setBatchNo(reserveOrderEditDTO.getBatchNo());
            boolean update = resvOrderService.update(status, new EntityWrapper<>(batchNo));
            return update?SuccessTip.SUCCESS_TIP:ErrorTip.ERROR_TIP;
        }

        //新的桌位
        List<OrderTableBo> orderTable = reserveOrderEditDTO.getOrderTables();

        ResvOrderAndroid resvOrderCode = new ResvOrderAndroid();
        resvOrderCode.setBatchNo(reserveOrderEditDTO.getBatchNo());
        List<ResvOrderAndroid> resvOrders = resvOrderService.selectList(new EntityWrapper<>(resvOrderCode));
        if(CollectionUtils.isEmpty(resvOrders)){
            Locale locale = LocaleContextHolder.getLocale();
            return new ErrorTip(400,messageSource.getMessage("orderMiss",null,locale));
        }

        List<Integer> tableIds=new ArrayList<>();
        for (OrderTableBo orderTableBo : orderTable) {
            tableIds.add(orderTableBo.getTableId());
        }
        for (ResvOrderAndroid order : resvOrders) {

            //变更的桌位
            if(!tableIds.contains(order.getTableId())){
                //减桌操作(取消订单)
                order.setStatus(OrderStatus.DEBOOK.code);
            }else {
                //减去原始桌位
                tableIds.remove((Object) order.getTableId());
            }

            //更新订单
            ResvOrderAndroid update = new ResvOrderAndroid();
            BeanUtils.copyProperties(reserveOrderEditDTO,update);
            update.setId(order.getId());
            resvOrderService.updateById(update);
        }

        //加桌
        List<ResvOrderAndroid> newOrders = Lists.newArrayList();

        if(tableIds.size()>0){
            for (Integer tableId : tableIds) {
                ResvOrderAndroid newTable = new ResvOrderAndroid();
                BeanUtils.copyProperties(resvOrders.get(0),newTable);
                newTable.setBatchNo(reserveOrderEditDTO.getBatchNo());
                newTable.setTableId(tableId);
                newTable.setResvOrder(IdUtils.makeOrderNo());
                for (OrderTableBo orderTableBo : orderTable) {
                    if(orderTableBo.getTableId().equals(tableId)){
                        newTable.setResvNum(String.valueOf(orderTableBo.getResvNum()));
                    }
                }
                newOrders.add(newTable);
            }
            resvOrderService.insertBatch(newOrders);
        }
        return SuccessTip.SUCCESS_TIP;
    }



    public Page<ResvTableOrder> conditionQueryResvOrder(ResvOrderQueryDTO resvOrderQueryDTO) {

        Page<ResvTableOrder> page = new PageFactory().defaultPage();


        resvOrderService.conditionQueryResvOrder(page,resvOrderQueryDTO);

        return page;
    }

    /**
     * 锁台记录分页查询
     * @param lockTablQO
     * @return
     */
    public Page<LockTablDTO> conditionQueryLockRecord(LockTablQO lockTablQO) {

        Page<LockTablDTO> page = new PageFactory().defaultPage();

        resvOrderService.conditionQueryLockRecord(page,lockTablQO);

        return page;
    }

    /**
     * 查询excel 预订单数据
     * @param resvOrderQueryDTO
     * @return
     */
    public List<ResvTableOrder> excelConditionFindResvOrders(ResvOrderQueryDTO resvOrderQueryDTO) {

        List<ResvTableOrder> resvTableOrders =resvOrderService.excelConditionFindResvOrders(resvOrderQueryDTO);

        return resvTableOrders;
    }

    /**
     * 导出预订单excel
     * @param records
     */
    public void downloadexcel(List<ResvTableOrder> records) {

        //初始化 全局变量
        String sign = "resvorder";

        ExcelUtil.ListExport2Excel(sign, records);

    }

    /**
     * 查询订单操作记录
     * @param batchNo
     * @return
     */
    public List<ResvOrderLogsDTO> getOrderLogsByBatchNo(String batchNo) {

        List<ResvOrderLogsDTO> resvOrderLogs= iResvOrderLogsService.selectOrderLogsByBatchNo(batchNo);

        return resvOrderLogs;
    }

    /**
     * 订单搜索
     * @param resvOrderDTO
     * @return
     */
    public List<ResvOrderAndroid> searchOrders(OrderDTO resvOrderDTO) {
        ResvOrderAndroid resvOrderId = new ResvOrderAndroid();
        BeanUtils.copyProperties(resvOrderDTO,resvOrderId);
        EntityWrapper<ResvOrderAndroid> en = new EntityWrapper<>(resvOrderId);
        List<Integer> status = resvOrderDTO.getStatus();
        if(status!=null&&status.size()>0){
            en.in("status",status);
        }
        Date geResvDate = resvOrderDTO.getGeResvDate();
        if(geResvDate!=null){
            en.ge("resv_date",geResvDate);
        }

        return resvOrderService.selectList(en);
    }

    /**
     * 订单评价
     * @param bean
     * @return
     */
    public boolean orderRating(OrderRatingDTO bean) {
        ResvOrderRating resvOrderRating = new ResvOrderRating();
        BeanUtils.copyProperties(bean,resvOrderRating);
       return iResvOrderRatingService.insert(resvOrderRating);
    }


    public List<BatchOrderBo> orderSearchStatus(DeskOrderDTO bean) {

        List<BatchOrderBo> batchOrderBos = new ArrayList<>();
        List<DeskOrderBo> deskOrders = resvOrderService.findOrders(bean);

        //已结账散客
        BatchOrderBo notVipStatus3 = new BatchOrderBo();
        Double payAmount3=0.00;
        Integer tableNum3=0;
        //未结账散客
        BatchOrderBo notVipStatus2 = new BatchOrderBo();
        Integer tableNum2=0;
        //所有批次号
        Set<String> batchNos = deskOrders.stream().map(DeskOrderBo::getBatchNo).collect(Collectors.toSet());
        for (String batchNo : batchNos) {
            BatchOrderBo batchOrderBo = new BatchOrderBo();
            List<DeskOrderBo> batchOrder = deskOrders.stream().filter(item -> item.getBatchNo().equals(batchNo)).collect(Collectors.toList());
            //复制基础信息
            BeanUtils.copyProperties(batchOrder.get(0),batchOrderBo);


            //订单桌位信息
            ArrayList<OrderTablesStatusBo> tablesStatusBos = new ArrayList<>();
            Integer resvNum=0;
            for (DeskOrderBo deskOrderBo : batchOrder) {
                if(StringUtils.isNotBlank(deskOrderBo.getResvNum()))
                    resvNum+=Integer.valueOf(deskOrderBo.getResvNum());
                OrderTablesStatusBo tablesStatusBo = new OrderTablesStatusBo();
                BeanUtils.copyProperties(deskOrderBo,tablesStatusBo);
                tablesStatusBos.add(tablesStatusBo);

                //判断是否散客
                if(deskOrderBo.getVipId()==null){
                    if("3".equals(deskOrderBo.getStatus())){
                        String payamount = batchOrderBo.getPayamount();
                        if(StringUtils.isNotBlank(payamount))
                            payAmount3+=Double.valueOf(payamount);
                        tableNum3++;
                    }
                    if("2".equals(batchOrderBo.getStatus())){
                        tableNum2++;
                    }
                }
            }
            batchOrderBo.setResvNum(resvNum+"");
            batchOrderBo.setOrders(tablesStatusBos);
            batchOrderBos.add(batchOrderBo);
        }

        notVipStatus3.setPayamount(payAmount3+"");
        notVipStatus3.setTableId(tableNum3);
        notVipStatus3.setStatus("3");

        notVipStatus2.setTableId(tableNum2);
        notVipStatus2.setStatus("2");

        batchOrderBos.add(notVipStatus3);
        batchOrderBos.add(notVipStatus2);

        return batchOrderBos;
    }

    /**
     * 筛选条件
     *
     */
    public JSONObject performanceStatistics(PerformanceDTO performanceDTO) {


        // 1. 查询各种第三方订单统计
        List<PerformanceBO> thirdPerformanceBOs  = resvOrderService.selectPerformanceStatisticsWithThird(performanceDTO);


        // 2. 查询电话机统计
        List<PerformanceBO> androidPerformanceBOs = resvOrderService.selectPerformanceStatisticsWithAndroidPhone(performanceDTO);



        // 3. 查询小程序业绩统计
        List<PerformanceBO> appPerformanceBOs  = resvOrderService.selectPerformanceStatisticsWithSmallApp(performanceDTO);

        JSONObject result = new JSONObject();

        result.put("third",thirdPerformanceBOs);
        result.put("android",androidPerformanceBOs);
        result.put("app",appPerformanceBOs);

        return  result;
    }
}

