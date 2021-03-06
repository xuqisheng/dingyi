package com.zhidianfan.pig.yd.moduler.order.task;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.service.*;
import com.zhidianfan.pig.yd.moduler.order.bo.*;
import com.zhidianfan.pig.yd.moduler.order.entity.OrderTem;
import com.zhidianfan.pig.yd.moduler.order.service.XopService;
import com.zhidianfan.pig.yd.moduler.resv.enums.OrderStatus;
import com.zhidianfan.pig.yd.utils.IdUtils;
import lombok.Data;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author mikrotik
 */
@Component
@ConditionalOnProperty(name = "yd.xms.task", havingValue = "true")
@Slf4j
public class OrderTask {
    /**
     * 西软接口信息数据接口
     */
    @Autowired
    private IXmsBusinessService xmsBusinessService;
    /**
     * 第三方订单数据接口
     */
    @Autowired
    private IResvOrderTemService resvOrderTemService;
    /**
     * 西软业务逻辑类
     */
    @Autowired
    private XopService xopService;

    @Autowired
    private IResvOrderService resvOrderService;

    @Autowired
    private IResvOrderLogsService resvOrderLogsService;

    @Autowired
    private IResvMeetingOrderService resvMeetingOrderService;

    @Autowired
    private ITableAreaService tableAreaService;

    @Autowired
    private ITableService tableService;

    @Autowired
    private IMealTypeService mealTypeService;

    @Autowired
    private IBusinessService businessService;

    @Autowired
    private IBillSyncService billSyncService;

    @Autowired
    private IBillMxSyncService billMxSyncService;

    /**
     * 创建更新普通订单
     */
    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    public void createOrUpdate() {
        log.info("======createOrUpdate start========");
        int currentPage = 1;
        int pageSize = 1000;
        while (true) {
            Page<XmsBusiness> page = new Page<>(currentPage, pageSize);
            Page<XmsBusiness> xmsBusinessPage = xmsBusinessService.selectPage(page);
            for (XmsBusiness xmsBusiness : xmsBusinessPage.getRecords()) {
                Integer businessId = xmsBusiness.getBusinessId();
                log.info("当前:{}", businessId);
                List<ResvOrderTem> orders = resvOrderTemService.selectList(new EntityWrapper<ResvOrderTem>().eq("business_id", businessId).ge("resv_date", DateFormatUtils.format(new Date(), "yyyy-MM-dd")).eq("xms_update_status", 0));
                if (CollectionUtils.isEmpty(orders)) {
                    continue;
                }
                orders.forEach(order -> {
                    if ((order.getXmsUpdateStatus() == 1 && !StringUtils.isEmpty(order.getThirdOrderNo())) || "4".equals(order.getStatus()) || StringUtils.isEmpty(order.getVipPhone())) {
                        return;
                    }
                    log.info("订单酒店id:{}", order.getBusinessId());
                    xopService.updateOrSaveOrder(order);
                });
            }

            if (!xmsBusinessPage.hasNext()) {
                break;
            }
            currentPage++;
        }
        log.info("======createOrUpdate end========");
    }

    /**
     * 创建更新宴会订单
     */
    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    public void createOrUpdateMeeting() {
        log.info("======createOrUpdate start========");
        int currentPage = 1;
        int pageSize = 1000;
        while (true) {
            Page<XmsBusiness> page = new Page<>(currentPage, pageSize);
            Page<XmsBusiness> xmsBusinessPage = xmsBusinessService.selectPage(page);
            for (XmsBusiness xmsBusiness : xmsBusinessPage.getRecords()) {
                Integer businessId = xmsBusiness.getBusinessId();
                log.info("当前:{}", businessId);
                List<ResvMeetingOrder> orders = resvMeetingOrderService.selectList(
                        new EntityWrapper<ResvMeetingOrder>()
                                .eq("business_id", businessId)
                                .ge("resv_date", DateFormatUtils.format(new Date(), "yyyy-MM-dd"))
                );
                if (CollectionUtils.isEmpty(orders)) {
                    continue;
                }
                orders.forEach(order -> {

                    if ((order.getXmsUpdateStatus() == 1 && !StringUtils.isEmpty(order.getThirdOrderNo())) || "4".equals(order.getStatus()) || StringUtils.isEmpty(order.getVipPhone())) {
                        return;
                    }
                    log.info("订单酒店id:{}", order.getBusinessId());
                    xopService.updateOrSaveMettingOrder(order);
                });
            }
            if (!xmsBusinessPage.hasNext()) {
                break;
            }
            currentPage++;
        }
        log.info("======createOrUpdate end========");
    }


    /**
     * 更新第三方订单表
     */
    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    public void updateOrderTem() {
        log.info("======updateOrderTem start========");
        int currentPage = 1;
        int pageSize = 1000;

        List<String> strings = Arrays.asList("1", "2", "4", "6");
        List<OrderTem> rms = new ArrayList<>();


        while (true) {
            Page<XmsBusiness> page = new Page<>(currentPage, pageSize);
            Page<XmsBusiness> xmsBusinessPage = xmsBusinessService.selectPage(page);
            ArrayList<ResvOrderTem> list = Lists.newArrayList();
            for (XmsBusiness xmsBusiness : xmsBusinessPage.getRecords()) {
                if (list.size() != 0) {
                    list.clear();
                }
                //Step 1 查询订单
                List<OrderTem> orderTems = resvOrderTemService.selectOrderTemInsertData(xmsBusiness.getBusinessId());

                //优化sql 去除sql 中IsTirdparty不为null status 不在1,2,4,6 中的数据
                for (OrderTem orderTem : orderTems) {
                    String status = orderTem.getStatus();
                    if (!strings.contains(status) || orderTem.getIsTirdparty() != null){
                        rms.add(orderTem);
                    }
                }
                orderTems.removeAll(rms);
                rms.clear();

                boolean change ;
                for (OrderTem orderTem : orderTems) {
                    //Step 2 检查是否需要更新
                    ResvOrderTem resvOrderTem = resvOrderTemService.selectOne(new EntityWrapper<ResvOrderTem>().eq("resv_order", orderTem.getResvOrder()));
                    if (resvOrderTem == null || !resvOrderTem.getStatus().equals(orderTem.getStatus()) || !resvOrderTem.getTableId().equals(orderTem.getTableId())) {
                        change = true;
                    } else {
                        change = false;
                    }
                    //Step 3 根据S2决定是否更新
                    if (resvOrderTem == null) {
                        resvOrderTem = new ResvOrderTem();
                        resvOrderTem.settCreatedAt(new Date());
                        resvOrderTem.setUpdateStatus(0);
                        resvOrderTem.setXmsUpdateStatus(0);
                    } else if (!StringUtils.isEmpty(resvOrderTem.getThirdOrderNo())) {
                        orderTem.setThirdOrderNo(resvOrderTem.getThirdOrderNo());
                    }
                    BeanUtils.copyProperties(orderTem, resvOrderTem);
                    resvOrderTem.settUpdatedAt(new Date());
                    if (orderTem.getIsChangeTable() == null) {
                        resvOrderTem.setStatus(resvOrderTem.getStatus());
                    } else {
                        resvOrderTem.setStatus("1");
                    }
                    if (change) {
                        resvOrderTem.setUpdateStatus(0);
                        resvOrderTem.setXmsUpdateStatus(0);
                    }
                    //Step 4 对剩余的订单执行insert操作
                    resvOrderTemService.insertOrUpdate(resvOrderTem);
                }

            }
            if (!xmsBusinessPage.hasNext()) {
                break;
            }
            currentPage++;
        }
        log.info("======updateOrderTem end========");
    }

    /**
     * 取消普通订单
     */
    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    public void cancel() {
        log.info("======cancel start========");
        int currentPage = 1;
        int pageSize = 1000;
        while (true) {
            Page<XmsBusiness> page = new Page<>(currentPage, pageSize);
            Page<XmsBusiness> xmsBusinessPage = xmsBusinessService.selectPage(page);
            for (XmsBusiness xmsBusiness : xmsBusinessPage.getRecords()) {
                Integer businessId = xmsBusiness.getBusinessId();
                XmsBusiness business = xmsBusinessService.selectOne(new EntityWrapper<XmsBusiness>().eq("business_id", businessId));
                if (business == null) {
                    continue;
                }
                List<ResvOrderTem> orders = resvOrderTemService.selectList(new EntityWrapper<ResvOrderTem>().eq("business_id", businessId).ge("resv_date", DateFormatUtils.format(new Date(), "yyyy-MM-dd")).eq("xms_update_status", 0));
                if (CollectionUtils.isEmpty(orders)) {
                    continue;
                }
                orders.forEach(order -> {

                    if ("4".equals(order.getStatus()) && order.getThirdOrderNo() != null) {
                        xopService.cancelOrder(order);
                    }

                });
            }

            if (!xmsBusinessPage.hasNext()) {
                break;
            }
            currentPage++;
        }
        log.info("======cancel end========");
    }


    /**
     * 取消宴会订单
     */
    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    public void cancelMeeting() {
        log.info("======cancelMeeting start========");
        int currentPage = 1;
        int pageSize = 1000;
        while (true) {
            Page<XmsBusiness> page = new Page<>(currentPage, pageSize);
            Page<XmsBusiness> xmsBusinessPage = xmsBusinessService.selectPage(page);
            for (XmsBusiness xmsBusiness : xmsBusinessPage.getRecords()) {
                Integer businessId = xmsBusiness.getBusinessId();
                XmsBusiness business = xmsBusinessService.selectOne(new EntityWrapper<XmsBusiness>().eq("business_id", businessId));
                if (business == null) {
                    continue;
                }
                List<ResvMeetingOrder> orders = resvMeetingOrderService.selectList(new EntityWrapper<ResvMeetingOrder>().eq("business_id", businessId).ge("resv_date", DateFormatUtils.format(new Date(), "yyyy-MM-dd")));
                if (CollectionUtils.isEmpty(orders)) {
                    continue;
                }
                orders.forEach(order -> {

                    if ("4".equals(order.getStatus()) && order.getThirdOrderNo() != null) {
                        xopService.cancelMeetingOrder(order);
                    }

                });
            }

            if (!xmsBusinessPage.hasNext()) {
                break;
            }
            currentPage++;
        }
        log.info("======cancelMeeting end========");
    }

    /**
     * 检查普通订单信息
     */
    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    public void checkOrder() {
        log.info("======checkOrder start========");
        int currentPage = 1;
        int pageSize = 1000;
        while (true) {
            Page<XmsBusiness> page = new Page<>(currentPage, pageSize);
            Page<XmsBusiness> xmsBusinessPage = xmsBusinessService.selectPage(page);
            for (XmsBusiness xmsBusiness : xmsBusinessPage.getRecords()) {
                Integer businessId = xmsBusiness.getBusinessId();
                resvOrderService.updateThirdOrderNo(businessId);
                List<ResvOrder> orders = resvOrderService.selectList(new EntityWrapper<ResvOrder>().eq("business_id", businessId).ge("resv_date", DateFormatUtils.format(new Date(), DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.getPattern())).ne("third_order_no", ""));
                if (!CollectionUtils.isEmpty(orders)) {
                    orders.forEach(order -> {
                        if (order.getThirdOrderNo() == null) {
                            return;
                        }
                        PosReserverBO posReserverBO = xopService.posReserver(businessId, order.getThirdOrderNo());
                        if (posReserverBO == null || !posReserverBO.isSuccess() || posReserverBO.getResults().size() == 0) {
                            return;
                        }
                        String tableCode = posReserverBO.getResults().get(0).get("tableno");
                        Table table = tableService.selectOne(new EntityWrapper<Table>().eq("business_id", businessId).eq("table_code", tableCode));
                        //宴会桌位执行下个循环
                        if (StringUtils.equals(table.getTableType(), "1")) {
                            return;
                        }
                        if (table == null) {
                            return;
                        }
                        String changed = posReserverBO.getResults().get(0).get("changed");
                        try {
                            Date changeDate = DateUtils.parseDate(changed, "yyyy-MM-dd HH:mm:ss");
                            Date updatedAt = order.getUpdatedAt();
                            if (updatedAt.before(changeDate)) {
                                order.setTableId(table.getId());
                                order.setTableName(table.getTableName());
                                TableArea tableArea = tableAreaService.selectById(table.getTableAreaId());
                                order.setTableAreaName(tableArea.getTableAreaName());
                                order.setTableAreaId(tableArea.getId());

                            }
                        } catch (ParseException e) {
                            log.error("日期转换失败:{}", e.getMessage());
                        }
                        boolean checkTable = xopService.checkTable(businessId, tableCode);
                        LocalTime time = LocalTime.now();
                        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        String orderDate = DateFormatUtils.format(order.getResvDate(), "yyyy-MM-dd");
                        MealType mealType = mealTypeService.selectOne(
                                new EntityWrapper<MealType>()
                                        .eq("business_id", order.getBusinessId())
                                        .le("resv_start_time", time)
                                        .ge("resv_end_time", time)
                        );
                        if (mealType == null) {
                            return;
                        }

                        //非当天当前餐次可能把退订重新状态改成预定的
                        ResvOrder resvOrder = resvOrderService.selectById(order.getId());
                        if (
                                !StringUtils.equals(resvOrder.getStatus(), "4") &&
                                        !StringUtils.equals(order.getStatus(), "4")
                                        && StringUtils.equals(currentDate, orderDate)
                                        && order.getMealTypeId().equals(mealType.getId())
                        ) {
                            if (checkTable) {
                                order.setStatus("1");
                            } else {
                                log.info("=================checkOrder 订单修改为入座 begin===================");
                                log.info("checkOrder:{}", order);
                                order.setStatus("2");
                                order.setRemark("checkOrder西软入座");
                                order.setUpdatedAt(new Date());
                                log.info("mealType:{}", mealType);
                                log.info("checkOrder:{}", order);
                                log.info("=================checkOrder 订单修改为入座 end===================");
                                //记录入座日志
                                Wrapper<ResvOrderLogs> wrapper = new EntityWrapper<ResvOrderLogs>()
                                        .eq("resv_order", order.getResvOrder()).orderBy("create_at", false);
                                ResvOrderLogs resvOrderLogs = resvOrderLogsService.selectOne(wrapper);
                                if (!StringUtils.equals(resvOrderLogs.getStatus(), "2")) {
                                    ResvOrderLogs logs = new ResvOrderLogs();
                                    logs.setCreatedAt(new Date());
                                    logs.setResvOrder(order.getResvOrder());
                                    logs.setStatusName("已经座");
                                    logs.setLogs("变更预定状态-resv,由西软入座");
                                }
                            }
                        } else if (!StringUtils.equals(order.getStatus(), "4") && !StringUtils.equals(resvOrder.getStatus(), "4")) {
                            order.setStatus("1");
                        }
                        resvOrderService.updateById(order);
                    });
                }
            }

            if (!xmsBusinessPage.hasNext()) {
                break;
            }
            currentPage++;
        }
        log.info("======checkOrder end========");
    }

    /**
     * 宴会订单检查
     */
    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    public void checkMeetingOrder() {
        log.info("======checkMeetingOrder start========");
        int currentPage = 1;
        int pageSize = 1000;
        while (true) {
            Page<XmsBusiness> page = new Page<>(currentPage, pageSize);
            Page<XmsBusiness> xmsBusinessPage = xmsBusinessService.selectPage(page);
            for (XmsBusiness xmsBusiness : xmsBusinessPage.getRecords()) {
                Integer businessId = xmsBusiness.getBusinessId();
                resvOrderService.updateThirdOrderNo(businessId);
                List<ResvMeetingOrder> orders = resvMeetingOrderService.selectList(new EntityWrapper<ResvMeetingOrder>().eq("business_id", businessId).ge("resv_date", DateFormatUtils.format(new Date(), DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.getPattern())).ne("third_order_no", ""));
                if (!CollectionUtils.isEmpty(orders)) {
                    orders.forEach(order -> {
                        if (order.getThirdOrderNo() == null) {
                            return;
                        }
                        PosReserverBO posReserverBO = xopService.posReserver(businessId, order.getThirdOrderNo());
                        if (posReserverBO == null || !posReserverBO.isSuccess() || posReserverBO.getResults().size() == 0) {
                            return;
                        }
                        String tableCode = posReserverBO.getResults().get(0).get("tableno");
                        Table table = tableService.selectOne(new EntityWrapper<Table>().eq("business_id", businessId).eq("table_code", tableCode));
                        if (table == null || !StringUtils.equals(table.getTableType(), "1")) {
                            return;
                        }
                        String changed = posReserverBO.getResults().get(0).get("changed");
                        try {
                            Date changeDate = DateUtils.parseDate(changed, "yyyy-MM-dd HH:mm:ss");
                            Date updatedAt = order.getUpdatedAt();
                            if (updatedAt.before(changeDate)) {
                                order.setTableId(table.getId());
                                order.setTableName(table.getTableName());
                                TableArea tableArea = tableAreaService.selectById(table.getTableAreaId());
                                order.setTableAreaName(tableArea.getTableAreaName());
                                order.setTableAreaId(tableArea.getId());

                            }
                        } catch (ParseException e) {
                            log.error("日期转换失败:{}", e.getMessage());
                        }
                        String now = DateFormatUtils.format(new Date(), "yyyy-Mm-dd");
                        String resvDate = DateFormatUtils.format(order.getResvDate(), "yyyy-Mm-dd");
                        if(now.equals(resvDate)){
                            boolean checkTable = xopService.checkTable(businessId, tableCode);
                            if (checkTable) {
                                order.setStatus("1");
                            } else {
                                order.setStatus("2");
                            }
                            order.setRemark("西软：checkMeetingOrder");
                            ResvMeetingOrder meetingOrder = resvMeetingOrderService.selectById(order.getId());
                            if(StringUtils.equalsAny(meetingOrder.getStatus(),"1","2")){
                                resvMeetingOrderService.updateById(order);
                            }
                        }
                    });
                }
            }

            if (!xmsBusinessPage.hasNext()) {
                break;
            }
            currentPage++;
        }
        log.info("======checkMeetingOrder end========");
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    public void checkTableStatus() {
        log.info("======checkTableStatus start========");
        int currentPage = 1;
        int pageSize = 1000;
        while (true) {
            Page<XmsBusiness> page = new Page<>(currentPage, pageSize);
            Page<XmsBusiness> xmsBusinessPage = xmsBusinessService.selectPage(page);
            for (XmsBusiness xmsBusiness : xmsBusinessPage.getRecords()) {
                Integer businessId = xmsBusiness.getBusinessId();
                List<Table> tables = tableService.selectList(new EntityWrapper<Table>().eq("business_id", businessId));

                if (CollectionUtils.isEmpty(tables)) {
                    continue;
                }

                tables.forEach(table -> {
                    String tableCode = table.getTableCode();
                    if (StringUtils.isEmpty(tableCode)) {
                        log.info("没有 tableCode:{},{}", table.getBusinessId(), table.getId());
                        return;
                    }
                    boolean checkTable;
                    try {
                        checkTable = xopService.checkTable(businessId, tableCode);
                    } catch (Exception e) {
                        log.error("验证桌位异常:{}", e.getMessage());
                        return;
                    }
                    String status = "1";
                    if (!checkTable) {
                        status = "2";
                    }
                    TableMenusBO tableMenusBO = xopService.tableMenus(businessId, tableCode);
                    if (!tableMenusBO.isSuccess() || tableMenusBO.getResults().size() == 0) {
                        return;
                    }
                    Map<String, String> result = tableMenusBO.getResults().get(0);
                    String thirdOrderNo = result.get("resno");
                    if (StringUtils.isBlank(thirdOrderNo)) {
                        return;
                    }

                    ResvOrderTem resvOrderTem = resvOrderTemService.selectOne(new EntityWrapper<ResvOrderTem>().eq("business_id", businessId).eq("third_order_no", thirdOrderNo));
                    ResvOrder resvOrder = null;
                    if (resvOrderTem == null) {
                        resvOrder = resvOrderService.selectOne(new EntityWrapper<ResvOrder>().eq("business_id", businessId).eq("third_order_no", thirdOrderNo));
                    } else {
                        resvOrder = resvOrderService.selectOne(new EntityWrapper<ResvOrder>().eq("business_id", businessId).eq("resv_order", resvOrderTem.getResvOrder()));
                    }
                    ResvMeetingOrder resvMeetingOrder = null;
                    if (resvOrder == null) {
                        resvMeetingOrder = resvMeetingOrderService.selectOne(new EntityWrapper<ResvMeetingOrder>().eq("business_id", businessId).eq("third_order_no", thirdOrderNo));
                    }
                    if (
                            (resvOrder != null && !StringUtils.equalsAny(resvOrder.getStatus(), "2")) ||
                                    (resvMeetingOrder != null && !StringUtils.equalsAny(resvMeetingOrder.getStatus(), "2"))
                    ) {
                        return;
                    }
                    String menuDate = result.get("menudate");
//                    Date parse = null;
//                    try {
//                        parse = DateUtils.parseDate(menuDate, "yyyy-mm-dd HH:mm:ss");
//                    } catch (ParseException e) {
//                        log.error(e.getMessage(), e);
//                        return;
//                    }
//                    String time = DateFormatUtils.format(parse, "HH:ss");
                    LocalTime time = LocalTime.now();
                    MealType mealType = mealTypeService.selectOne(new EntityWrapper<MealType>().eq("business_id", businessId).ge("resv_start_time", time).le("resv_end_time", time));
                    String currentDate = DateFormatUtils.format(new Date(), "yyyy-mm-dd");
                    if (resvOrder == null) {
                        resvOrder = new ResvOrder();
                        String orderNo = IdUtils.makeOrderNo();
                        resvOrder.setBatchNo("pc" + orderNo);
                        resvOrder.setResvOrder(orderNo);
                        resvOrder.setBusinessId(businessId);
                        Business bs = businessService.selectById(businessId);
                        resvOrder.setBusinessName(bs.getBusinessName());
                        resvOrder.setResvDate(new Date());
                        String destTime = DateFormatUtils.format(new Date(), "HH:mm:ss");
                        resvOrder.setDestTime(destTime);
                        resvOrder.setStatus(status);
                        resvOrder.setTableName(table.getTableName());
                        resvOrder.setTableId(table.getId());
                        TableArea tableArea = tableAreaService.selectById(table.getTableAreaId());
                        resvOrder.setTableAreaName(tableArea.getTableAreaName());
                        resvOrder.setTableAreaId(tableArea.getId());
                        resvOrder.setCreatedAt(new Date());
                        resvOrder.setUpdatedAt(new Date());
                        resvOrder.setDeviceUserName("西软");
                        resvOrder.setMealTypeId(mealType.getId());
                        resvOrder.setMealTypeName(mealType.getMealTypeName());
                        resvOrder.setThirdOrderNo(thirdOrderNo);
                        resvOrderService.insert(resvOrder);
                    } else if (resvOrder != null) {
                        String orderDate = DateFormatUtils.format(resvOrder.getResvDate(), "yyyy-mm-dd");
                        if (!StringUtils.equals(currentDate, orderDate)) {
                            return;
                        }
                        ResvOrder order = new ResvOrder();
                        order.setId(resvOrder.getId());
                        if (
                                !StringUtils.equals(order.getStatus(), "4")
                                        && StringUtils.equals(currentDate, orderDate)
                                        && order.getMealTypeId().equals(mealType.getId())
                        ) {
                            if (checkTable) {
                                order.setStatus("1");
                            } else {
                                log.info("=================checkTableStatus 订单修改为入座 begin===================");
                                log.info("checkTableStatus:{}", order);
                                order.setStatus("2");
                                order.setRemark("checkTableStatus西软入座");
                                order.setUpdatedAt(new Date());
                                log.info("mealType:{}", mealType);
                                log.info("checkTableStatus:{}", order);
                                log.info("=================checkTableStatus 订单修改为入座 end===================");
                            }
                        } else if (!StringUtils.equals(order.getStatus(), "4")) {
                            order.setStatus("1");
                        }
                        resvOrderService.updateById(order);
//                        resvOrderService.insertOrUpdate(resvOrder);
                    } else if (resvMeetingOrder != null) {
                        String orderDate = DateFormatUtils.format(resvOrder.getResvDate(), "yyyy-mm-dd");
                        if (!StringUtils.equals(currentDate, orderDate)) {
                            return;
                        }
                        resvMeetingOrder.setStatus(status);
                        ResvMeetingOrder meetingOrder = new ResvMeetingOrder();
                        if (
                                !StringUtils.equals(meetingOrder.getStatus(), "4")
                                        && StringUtils.equals(currentDate, orderDate)
                                        && meetingOrder.getMealTypeId().equals(mealType.getId())
                        ) {
                            if (checkTable) {
                                meetingOrder.setStatus("1");
                            } else {
                                meetingOrder.setStatus("2");
                            }
                        } else if (!StringUtils.equals(meetingOrder.getStatus(), "4")) {
                            meetingOrder.setStatus("1");
                        }
                        meetingOrder.setId(resvMeetingOrder.getId());
                        meetingOrder.setRemark("西软：checkTableStatus");
                        resvMeetingOrderService.updateById(meetingOrder);
                    }
                });
            }

            if (!xmsBusinessPage.hasNext()) {
                break;
            }
            currentPage++;
        }
        log.info("======checkTableStatus end========");
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    public void orderMenuMaster() {
        log.info("======orderMenuMaster start========");
        int currentPage = 1;
        int pageSize = 1000;
        Page<XmsBusiness> page = new Page<>(currentPage, pageSize);
        Page<XmsBusiness> xmsBusinessPage = xmsBusinessService.selectPage(page);
        for (XmsBusiness xmsBusiness : xmsBusinessPage.getRecords()) {
            Integer businessId = xmsBusiness.getBusinessId();
            Date date = new Date();
            String format = DateFormatUtils.format(date, "HH:mm");
            MealType mealType = mealTypeService.selectOne(new EntityWrapper<MealType>().lt("resv_start_time", format).gt("resv_start_time", format));
            if (mealType == null) {
                return;
            }
            List<ResvOrderTem> orders = resvOrderTemService.selectList(
                    new EntityWrapper<ResvOrderTem>()
                            .eq("business_id", businessId)
                            .eq("resv_date", DateFormatUtils.format(new Date(), "yyyy-MM-dd"))
                            .eq("meal_type_id", mealType.getId())
                            .eq("xms_update_status", 0)
            );
            orders.forEach(order -> {
                if (!StringUtils.equalsAnyIgnoreCase(order.getStatus(), "1", "2") || !StringUtils.isNotEmpty(order.getThirdOrderNo()) || !StringUtils.isNotEmpty(order.getMenuOrder())) {
                    return;
                }
                Table table = tableService.selectById(order.getTableId());
                if (table == null || StringUtils.isEmpty(table.getTableCode())) {
                    return;
                }
                TableMenuBO menuOrder = xopService.getMenuOrder(businessId, table.getTableCode());
                if (menuOrder.isSuccess()) {
                    order.setMenuOrder(menuOrder.getResults().get(0).get("menu"));
                    resvOrderTemService.updateById(order);
                }
            });
        }
    }


    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    public void meetingOrderMenuMaster() {
        log.info("======meetingOrderMenuMaster start========");
        int currentPage = 1;
        int pageSize = 1000;
        Page<XmsBusiness> page = new Page<>(currentPage, pageSize);
        Page<XmsBusiness> xmsBusinessPage = xmsBusinessService.selectPage(page);
        for (XmsBusiness xmsBusiness : xmsBusinessPage.getRecords()) {
            Integer businessId = xmsBusiness.getBusinessId();
            Date date = new Date();
            String format = DateFormatUtils.format(date, "HH:mm");
            MealType mealType = mealTypeService.selectOne(new EntityWrapper<MealType>().lt("resv_start_time", format).gt("resv_start_time", format));
            if (mealType == null) {
                return;
            }
            List<ResvMeetingOrder> orders = resvMeetingOrderService.selectList(
                    new EntityWrapper<ResvMeetingOrder>()
                            .eq("business_id", businessId)
                            .eq("resv_date", DateFormatUtils.format(new Date(), "yyyy-MM-dd"))
                            .eq("meal_type_id", mealType.getId())
                            .eq("xms_update_status", 0)
            );
            orders.forEach(order -> {
                if (!StringUtils.equalsAnyIgnoreCase(order.getStatus(), "1", "2") || !StringUtils.isNotEmpty(order.getThirdOrderNo()) || !StringUtils.isNotEmpty(order.getMenuOrder())) {
                    return;
                }
                Table table = tableService.selectById(order.getTableId());
                if (table == null || StringUtils.isEmpty(table.getTableCode())) {
                    return;
                }
                TableMenuBO menuOrder = xopService.getMenuOrder(businessId, table.getTableCode());
                if (menuOrder.isSuccess()) {
                    order.setMenuOrder(menuOrder.getResults().get(0).get("menu"));
                    order.setRemark("西软：meetingOrderMenuMaster");
                    resvMeetingOrderService.updateById(order);
                }
            });
        }
    }

    /**
     * 订单结账
     */
    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    public void orderOver() {
        log.info("======orderOver start========");
        int currentPage = 1;
        int pageSize = 1000;
        Page<XmsBusiness> page = new Page<>(currentPage, pageSize);
        Page<XmsBusiness> xmsBusinessPage = xmsBusinessService.selectPage(page);
        for (XmsBusiness xmsBusiness : xmsBusinessPage.getRecords()) {
            Integer businessId = xmsBusiness.getBusinessId();
            Date date = new Date();
            String format = DateFormatUtils.format(date, "HH:mm");
            MealType mealType = mealTypeService.selectOne(new EntityWrapper<MealType>().lt("resv_start_time", format).gt("resv_start_time", format));
            if (mealType == null) {
                return;
            }
            List<ResvOrderTem> orders = resvOrderTemService.selectList(
                    new EntityWrapper<ResvOrderTem>()
                            .eq("business_id", businessId)
                            .eq("resv_date", DateFormatUtils.format(new Date(), "yyyy-MM-dd"))
                            .eq("meal_type_id", mealType.getId())
            );
            HashMap<String, ResvOrderTem> map = Maps.newHashMap();
            orders.forEach(order -> {
                if (!StringUtils.equalsAnyIgnoreCase(order.getStatus(), "1", "2") || !StringUtils.isNotEmpty(order.getThirdOrderNo()) || !StringUtils.isNotEmpty(order.getMenuOrder())) {
                    return;
                }
                map.put(order.getMenuOrder(), order);
            });

            Set<String> menuOrders = map.keySet();
            OrderStatusBO orderStatus = xopService.getOrderStatus(businessId, menuOrders);
            if (orderStatus.isSuccess()) {
                orderStatus.getResults().forEach(result -> {
                    String sta = result.get("sta");
                    if (StringUtils.equals(sta, "3")) {
                        String menu = result.get("menu");
                        ResvOrderTem resvOrderTem = map.get(menu);
                        if (resvOrderTem == null) {
                            return;
                        }

                        ResvOrder resvOrder = resvOrderService.selectOne(new EntityWrapper<ResvOrder>().eq("resv_order", resvOrderTem.getResvOrder()));
                        resvOrder.setStatus("3");
                        String amount = result.get("amount");
                        resvOrder.setPayamount(amount);
                        resvOrderService.updateById(resvOrder);
                        OrderDishBO orderDish = xopService.getOrderDish(businessId, menu);
                        BillSync billSync = new BillSync();
                        billSync.setAreaCode(resvOrderTem.getAreaCode());
                        billSync.setBbbc(resvOrderTem.getMealTypeName());
                        billSync.setBbrq(new Date());
                        billSync.setBusinessId(resvOrderTem.getBusinessId());
                        billSync.setBusinessName(resvOrderTem.getBusinessName());
                        billSync.setCreatedAt(new Date());
                        billSync.setSjje(amount);
                        billSync.setTableCode(resvOrderTem.getTableCode());
                        billSync.setActualNum(resvOrderTem.getResvNum());
                        billSync.setZdbh(menu);
                        billSyncService.insert(billSync);
                        if (orderDish.isSuccess()) {
                            List<OrderDishBO.Result> results = orderDish.getResults();
                            List<Map<String, String>> dishes = results.get(0).getDishes();
                            dishes.forEach(dish -> {
                                BillMxSync billMxSync = new BillMxSync();
                                billMxSync.setBusinessId(resvOrderTem.getBusinessId());
                                billMxSync.setBusinessName(resvOrderTem.getBusinessName());
                                billMxSync.setZdbh(result.get("menu"));
                                billMxSync.setCmbh(dish.get("code"));
                                billMxSync.setCmmc(dish.get("descript"));
                                billMxSync.setSjje(Double.valueOf(dish.get("amount")));
                                billMxSync.setCmsl(Integer.valueOf(dish.get("number")));
                                billMxSyncService.insert(billMxSync);
                            });
                        }
                    }
                });
            }
        }
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    public void meetingOrderOver() {
        log.info("======orderOver start========");
        int currentPage = 1;
        int pageSize = 1000;
        Page<XmsBusiness> page = new Page<>(currentPage, pageSize);
        Page<XmsBusiness> xmsBusinessPage = xmsBusinessService.selectPage(page);
        for (XmsBusiness xmsBusiness : xmsBusinessPage.getRecords()) {
            Integer businessId = xmsBusiness.getBusinessId();
            Date date = new Date();
            String format = DateFormatUtils.format(date, "HH:mm");
            MealType mealType = mealTypeService.selectOne(new EntityWrapper<MealType>().lt("resv_start_time", format).gt("resv_start_time", format));
            if (mealType == null) {
                return;
            }
            List<ResvMeetingOrder> orders = resvMeetingOrderService.selectList(
                    new EntityWrapper<ResvMeetingOrder>()
                            .eq("business_id", businessId)
                            .eq("resv_date", DateFormatUtils.format(new Date(), "yyyy-MM-dd"))
                            .eq("meal_type_id", mealType.getId())
            );
            HashMap<String, ResvMeetingOrder> map = Maps.newHashMap();
            orders.forEach(order -> {
                if (!StringUtils.equalsAnyIgnoreCase(order.getStatus(), "1", "2") || !StringUtils.isNotEmpty(order.getThirdOrderNo()) || !StringUtils.isNotEmpty(order.getMenuOrder())) {
                    return;
                }
                map.put(order.getMenuOrder(), order);
            });

            Set<String> menuOrders = map.keySet();
            OrderStatusBO orderStatus = xopService.getOrderStatus(businessId, menuOrders);
            if (orderStatus.isSuccess()) {
                orderStatus.getResults().forEach(result -> {
                    String sta = result.get("sta");
                    if (StringUtils.equals(sta, "3")) {
                        String menu = result.get("menu");
                        ResvMeetingOrder resvMeetingOrder = map.get(menu);
                        if (resvMeetingOrder == null) {
                            return;
                        }


                        resvMeetingOrder.setStatus("3");
                        String amount = result.get("amount");
                        resvMeetingOrder.setPayAmount(amount);
                        resvMeetingOrder.setRemark("西软：meetingOrderOver");
                        resvMeetingOrderService.updateById(resvMeetingOrder);
                        OrderDishBO orderDish = xopService.getOrderDish(businessId, menu);
                        BillSync billSync = new BillSync();
                        TableArea tableArea = tableAreaService.selectById(resvMeetingOrder.getTableAreaId());
                        if (tableArea != null) {
                            billSync.setAreaCode(tableArea.getAreaCode());
                        }
                        billSync.setBbbc(resvMeetingOrder.getMealTypeName());
                        billSync.setBbrq(new Date());
                        billSync.setBusinessId(resvMeetingOrder.getBusinessId());
                        billSync.setBusinessName(resvMeetingOrder.getBusinessName());
                        billSync.setCreatedAt(new Date());
                        billSync.setSjje(amount);
//                        billSync.setTableCode(resvMeetingOrder.getTableCode());
//                        billSync.setActualNum(resvOrderTem.getResvNum());
                        billSync.setZdbh(menu);
                        billSyncService.insert(billSync);
                        if (orderDish.isSuccess()) {
                            List<OrderDishBO.Result> results = orderDish.getResults();
                            List<Map<String, String>> dishes = results.get(0).getDishes();
                            dishes.forEach(dish -> {
                                BillMxSync billMxSync = new BillMxSync();
                                billMxSync.setBusinessId(resvMeetingOrder.getBusinessId());
                                billMxSync.setBusinessName(resvMeetingOrder.getBusinessName());
                                billMxSync.setZdbh(result.get("menu"));
                                billMxSync.setCmbh(dish.get("code"));
                                billMxSync.setCmmc(dish.get("descript"));
                                billMxSync.setSjje(Double.valueOf(dish.get("amount")));
                                billMxSync.setCmsl(Integer.valueOf(dish.get("number")));
                                billMxSyncService.insert(billMxSync);
                            });
                        }
                    }
                });
            }
        }
    }
}
