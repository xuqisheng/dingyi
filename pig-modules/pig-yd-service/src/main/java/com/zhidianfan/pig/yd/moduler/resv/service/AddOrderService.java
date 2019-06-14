package com.zhidianfan.pig.yd.moduler.resv.service;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.common.service.*;
import com.zhidianfan.pig.yd.moduler.resv.bo.MessageOrderBO;
import com.zhidianfan.pig.yd.moduler.resv.dto.*;
import com.zhidianfan.pig.yd.moduler.resv.enums.OrderStatus;
import com.zhidianfan.pig.yd.moduler.resv.qo.MessageOrderQO;
import com.zhidianfan.pig.yd.moduler.sms.bo.message.MessageResultBO;
import com.zhidianfan.pig.yd.moduler.sms.dto.MessageDTO;
import com.zhidianfan.pig.yd.moduler.sms.service.MessageService;
import com.zhidianfan.pig.yd.utils.IdUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @Author: huzp
 * @Date: 2018/9/20 14:06
 */
@Service
public class AddOrderService {


    @Autowired
    private IResvOrderAndroidService iResvOrderService;

    @Autowired
    private IResvOrderService resvOrderService;


    @Autowired
    private VipService vipService;

    //sms模块下的 MessageService
    @Autowired
    private MessageService messageService;

    /**
     * 桌位数据接口
     */
    @Autowired
    private ITableService tableService;
    /**
     * 桌位区域数据接口
     */
    @Autowired
    private ITableAreaService tableAreaService;

    /**
     * 订单日志记录
     */
    @Autowired
    private IResvOrderLogsService iResvOrderLogsService;

    /**
     * 第三方订单
     */
    @Autowired
    private IResvOrderThirdService iResvOrderThirdService;

    /**
     * 订单状态对应表
     */
    @Autowired
    private IResvStatusMappingService iResvStatusMappingService;


    /**
     * 普通短信模板
     */
    @Resource
    private IBusinessSmsTemplateService iBusinessSmsTemplateService;

    /**
     * 生成新订单
     *
     * @param addOrderDTO
     * @return
     */
    @Transactional
    public Tip inserOrder(AddOrderDTO addOrderDTO) {

        ResvOrderAndroid resvOrder = new ResvOrderAndroid();

        //订单日志记录
        ResvOrderLogs resvOrderLogs = new ResvOrderLogs();


        BeanUtils.copyProperties(addOrderDTO, resvOrder);

        //获取桌位信息
        TableDTO[] tableDTO = addOrderDTO.getTableDTO();

        //生成创建日期
        Date date = new Date();

        //占用桌位
        StringBuilder sb = new StringBuilder();


        //遍历查询桌位是否被占用
        for (TableDTO dto : tableDTO) {

            //先查询桌位是否被使用
            List<ResvOrderAndroid> resvOrders = iResvOrderService.selectList(new EntityWrapper<ResvOrderAndroid>()
                    .eq("meal_type_id", resvOrder.getMealTypeId())
                    .eq("resv_date", resvOrder.getResvDate())
                    .eq("table_area_id", dto.getTableAreaId())
                    .eq("table_id", dto.getTableId())
                    .andNew("status='1'").or("status='2'").or("status='5'")
                    .andNew().eq("business_id", resvOrder.getBusinessId()));
            if (resvOrders.size() != 0) {

                for (ResvOrderAndroid resvOrder1 : resvOrders) {
                    sb.append(resvOrder1.getTableName() + ",");
                }
            }
        }

        if (sb.length() > 0) {
            sb.delete(sb.length() - 1, sb.length());
            sb.append(" 桌位被占用");
            ErrorTip errorTip = new ErrorTip();
            errorTip.setCode(500);
            errorTip.setMsg(sb.toString());
            return errorTip;
        }


        //查询status相关的状态描述
        ResvStatusMapping resvStatus = iResvStatusMappingService.selectOne(new EntityWrapper<ResvStatusMapping>()
                .eq("status_id", resvOrder.getStatus()));

        //根据deviceType 判断是小程序还是电话机的制定日志
        String deviceType = (addOrderDTO.getDeviceType().equals("1") ? "安卓电话机" : "小程序");

        String androidUserName = resvOrder.getAndroidUserName();
        String desc = (androidUserName == null ? "" : "(" + androidUserName + ")");

        String log;
        switch (resvStatus.getStatusId()) {
            case 1:
                log = "订单预订成功-" + deviceType + desc;
                break;
            case 2:
                log = "订单入座成功-" + deviceType + desc;
                break;
            case 5:
                log = "锁台成功-" + deviceType + desc;
                break;
            default:
                log = "订单预订成功-" + deviceType + desc;
                break;
        }

        //循环插入订单
        for (TableDTO dto : tableDTO) {

            BeanUtils.copyProperties(dto, resvOrder);

            //设置订单号
            String orderNo = IdUtils.makeOrderNo();
            resvOrder.setResvOrder(orderNo);
            resvOrder.setCreatedAt(date);
            //以桌位生成订单
            iResvOrderService.insert(resvOrder);


            //将订单操作日志插入订单日志表
            resvOrderLogs.setResvOrder(orderNo);
            resvOrderLogs.setAndroidUserId(resvOrder.getAndroidUserId());
            resvOrderLogs.setCreatedAt(date);
            resvOrderLogs.setStatus(resvStatus.getStatusId().toString());
            resvOrderLogs.setStatusName(resvStatus.getStatusName());
            resvOrderLogs.setLogs(log);
            iResvOrderLogsService.insert(resvOrderLogs);
        }

        return SuccessTip.SUCCESS_TIP;
    }


    /**
     * 天泰pc端生成订单
     *
     * @param addOrderDTO
     * @return
     */
    @Transactional
    public Tip pcInserOrder(AddOrderDTO addOrderDTO) {

        ResvOrder resvOrder = new ResvOrder();

        BeanUtils.copyProperties(addOrderDTO, resvOrder);

        //获取桌位信息
        TableDTO[] tableDTO = addOrderDTO.getTableDTO();

        //生成创建日期
        Date date = new Date();

        //占用桌位
        StringBuilder sb = new StringBuilder();


        //遍历查询桌位是否被占用
        for (TableDTO dto : tableDTO) {

            //先查询桌位是否被使用
            List<ResvOrder> resvOrders = resvOrderService.selectList(new EntityWrapper<ResvOrder>()
                    .eq("meal_type_id", resvOrder.getMealTypeId())
                    .eq("resv_date", resvOrder.getResvDate())
                    .eq("table_area_id", dto.getTableAreaId())
                    .eq("table_id", dto.getTableId())
                    .andNew("status='1'").or("status='2'").or("status='5'")
                    .andNew().eq("business_id", resvOrder.getBusinessId()));
            if (resvOrders.size() != 0) {
                for (ResvOrder resvOrder1 : resvOrders) {
                    sb.append(resvOrder1.getTableName() + ",");
                }
            }
        }

        if (sb.length() > 0) {
            //去除逗号
            sb.delete(sb.length() - 1, sb.length());
            sb.append(" 桌位被占用");
            ErrorTip errorTip = new ErrorTip();
            errorTip.setCode(500);
            errorTip.setMsg(sb.toString());
            return errorTip;
        }


        //循环插入订单
        for (TableDTO dto : tableDTO) {
            BeanUtils.copyProperties(dto, resvOrder);
            //设置订单号
            String orderNo = IdUtils.makeOrderNo();
            resvOrder.setResvOrder(orderNo);
            resvOrder.setCreatedAt(date);
            //以桌位生成pc端订单
            resvOrderService.insert(resvOrder);
        }

        return SuccessTip.SUCCESS_TIP;
    }


    /**
     * 编辑客户信息
     *
     * @param addOrderDTO
     * @return
     */
    public boolean editVip(AddOrderDTO addOrderDTO) {

        //如果没有手机号直接认定为散客
        if (null == addOrderDTO.getVipPhone() || "".equals(addOrderDTO.getVipPhone()))
            return true;

        Vip vip = new Vip();
        BeanUtils.copyProperties(addOrderDTO, vip);

        //去除订单中的tag与remark字段
        vip.setTag(null);
        vip.setRemark(null);

        boolean editFalg = vipService.updateOrInsertVip(vip);

        //获取vip基础信息
        Vip basicVipInfo = vipService.getBasicVipInfo(vip.getBusinessId(), vip.getVipPhone());

        //传输类更新最新的vip基础信息
        addOrderDTO.setVipId(basicVipInfo.getId());
        addOrderDTO.setVipCompany(basicVipInfo.getVipCompany());
        addOrderDTO.setVipName(basicVipInfo.getVipName());
        addOrderDTO.setVipSex(basicVipInfo.getVipSex());

        return editFalg;
    }


    /**
     * 锁台解锁
     *
     * @param addOrderDTO
     */
    public boolean unlock(AddOrderDTO addOrderDTO) {

        ResvOrderAndroid resvOrder = new ResvOrderAndroid();

        //订单日志记录
        ResvOrderLogs resvOrderLogs = new ResvOrderLogs();


        BeanUtils.copyProperties(addOrderDTO, resvOrder);

        //获取桌位信息
        TableDTO[] tableDTO = addOrderDTO.getTableDTO();

        boolean b = false;
        Date date = new Date();


        for (TableDTO dto : tableDTO) {

            ResvOrderAndroid lockOrder = iResvOrderService.selectOne(new EntityWrapper<ResvOrderAndroid>()
                    .eq("meal_type_id", resvOrder.getMealTypeId())
                    .eq("resv_date", resvOrder.getResvDate())
                    .eq("table_area_id", dto.getTableAreaId())
                    .eq("table_id", dto.getTableId())
                    .eq("status", OrderStatus.LOCK.code)
                    .eq("business_id", resvOrder.getBusinessId()));
            if (null == lockOrder) {
                return false;
            }

            //更新锁台状态为解锁
            lockOrder.setStatus(OrderStatus.UNLOCK.code);
            b = iResvOrderService.updateById(lockOrder);

            //插入总订单日志表
            Integer androidUserId = resvOrder.getAndroidUserId();
            String androidUserName = resvOrder.getAndroidUserName();

            resvOrderLogs.setResvOrder(lockOrder.getResvOrder());
            resvOrderLogs.setAndroidUserId(androidUserId);
            resvOrderLogs.setCreatedAt(date);
            resvOrderLogs.setStatus(OrderStatus.UNLOCK.code);
            resvOrderLogs.setStatusName(OrderStatus.UNLOCK.label);

            String desc = (androidUserId == null ? "" : "(" + androidUserName + ")");
            resvOrderLogs.setLogs("解锁成功-安卓电话机" + desc);
            iResvOrderLogsService.insert(resvOrderLogs);


        }
        return b;
    }

    /**
     * 修改订单状态
     *
     * @param resvOrders    订单号
     * @param editStatusDTO 订单属性
     * @return
     */
    public boolean editResvStatus(List<String> resvOrders, EditStatusDTO editStatusDTO) {

        boolean b;

        List<CheckoutBillDTO> checkoutBills1 = editStatusDTO.getCheckoutBills();
        if ("3".equals(editStatusDTO.getStatus())) {
            //更新订单状态 结账
            List<CheckoutBillDTO> checkoutBills = checkoutBills1;
            //计算浮动金额
            Integer payamount = Integer.valueOf(editStatusDTO.getPayamount());
            int size = checkoutBills.size();

            if (size == 1) {
                //一桌情况
                checkoutBills.get(0).setEverypay(payamount);
            } else {
                //多桌情况
                int i = payamount / size;
                for (int j = 0; j < size; j++) {

                    if (j != (size - 1)) {
                        checkoutBills.get(j).setEverypay(i);
                    } else {
                        //最后一桌
                        checkoutBills.get(j).setEverypay(payamount - i * (size - 1));
                    }
                }
            }

            //批量更新
            for (CheckoutBillDTO checkoutBill : checkoutBills) {
                iResvOrderService.checkoutBills(checkoutBill, editStatusDTO.getAndroidUserId(), editStatusDTO.getAndroidUserName());
            }

            b = true;

        } else {
            //更新订单状态 入座  退订
            b = iResvOrderService.updateStatusByOrder(resvOrders, editStatusDTO);

            //订单更新入座时候需要判断是否为微信订单生成的第三方订单
            if (editStatusDTO.getStatus().equals("2") && b) {
                for (CheckoutBillDTO checkoutBillDTO : checkoutBills1) {
                    String thirdOrderNo = checkoutBillDTO.getThirdOrderNo();
                    if (StringUtils.isEmpty(thirdOrderNo))
                        continue;

                    ResvOrderThird resvOrderThird = iResvOrderThirdService.selectOne(new EntityWrapper<ResvOrderThird>()
                            .eq("third_order_no", thirdOrderNo));
                    //如果是易订公众号接口
                    if (resvOrderThird.getSource().equals("易订公众号")) {
                        // 设置resvOrderThird 的状态为到店
                        resvOrderThird.setStatus(50);
                        iResvOrderThirdService.updateById(resvOrderThird);
                    }
                }
            }

        }


        //修改订单状态插入日志表
        //订单日志记录
        ResvOrderLogs resvOrderLogs = new ResvOrderLogs();
        Date date = new Date();

        ResvStatusMapping resvStatus = iResvStatusMappingService.selectOne(new EntityWrapper<ResvStatusMapping>()
                .eq("status_id", editStatusDTO.getStatus()));

        //插入订单操作日志
        for (String resvOrderNo : resvOrders) {

            //设置订单号
            resvOrderLogs.setResvOrder(resvOrderNo);

            Integer androidUserId = editStatusDTO.getAndroidUserId();
            String androidUserName = editStatusDTO.getAndroidUserName();

            resvOrderLogs.setAndroidUserId(androidUserId);
            resvOrderLogs.setCreatedAt(date);
            resvOrderLogs.setStatus(resvStatus.getStatusId().toString());
            resvOrderLogs.setStatusName(resvStatus.getStatusName());


            String desc = (androidUserId == null ? "" : "(" + androidUserName + ")");


            String log;
            switch (resvStatus.getStatusId()) {
                case 2:
                    log = "变更订单状态为入座-安卓电话机 " + desc;
                    break;
                case 3:
                    log = "变更订单状态为结账-安卓电话机 " + desc;
                    break;
                case 4:
                    log = "变更订单状态为退订-安卓电话机 " + desc;
                    break;
                default:
                    log = "变更订单状态-安卓电话机 " + desc;
                    break;
            }
            resvOrderLogs.setLogs(log);
            iResvOrderLogsService.insert(resvOrderLogs);

        }


        return b;
    }

    /**
     * 确认订单一定会来
     *
     * @param checkoutBillDTOS 获取订单list
     * @param confirm          是否会来 1 确定会来
     * @return
     */
    public boolean editResConfirm(List<CheckoutBillDTO> checkoutBillDTOS, String confirm) {

        List resvOrders = new ArrayList();

        for (CheckoutBillDTO checkoutBill : checkoutBillDTOS) {
            resvOrders.add(checkoutBill.getResvOrder());
        }

        return iResvOrderService.updateConfirmByOrder(resvOrders, confirm);
    }


    /**
     * 普通预定手动发送短信
     *
     * @param smsType 预定类型
     * @param batchNo 订单批次号
     */
    public MessageResultBO sendResvMessage(String smsType, String batchNo) {

        //查询出一个批次号 预定状态的订单
        List<ResvOrderAndroid> resvOrders = iResvOrderService.selectList(new EntityWrapper<ResvOrderAndroid>()
                .eq("batch_no", batchNo)
                .eq("status", OrderStatus.RESERVE.code));


        MessageDTO messageDTO = new MessageDTO();
        //设置酒店ID , 客户号码 ,订单类型, 模板类型
        messageDTO.setBusinessId(resvOrders.get(0).getBusinessId().longValue());
        messageDTO.setPhone(resvOrders.get(0).getVipPhone());
        messageDTO.setResvOrderTypeId(1);
        //加桌视为跟预定一个模板
        if (smsType.equals("add_table")) {
            messageDTO.setTemplateType(6);
        } else {
            messageDTO.setTemplateType(1);
        }

        //拼接桌位
        List<Map<String, Object>> checkedTables = Lists.newArrayList();

        for (int i = 0; i < resvOrders.size(); i++) {
            ResvOrderAndroid resvOrder = iResvOrderService.selectOne(new EntityWrapper<ResvOrderAndroid>()
                    .eq("resv_order", resvOrders.get(i).getResvOrder()));
            Map<String, Object> map = Maps.newHashMap();
            map.put("tableName", resvOrder.getTableName());
            map.put("tableAreaName", resvOrder.getTableAreaName());
            checkedTables.add(map);
        }

        messageDTO.setCheckedTables(checkedTables);

        //只需要一批订单中的一个订单号
        messageDTO.setResvOrder(resvOrders.get(0).getResvOrder());


        MessageResultBO messageResultBO = messageService.sendMessage(messageDTO);

        return messageResultBO;          //普通预定或者加桌

    }


    /**
     * 天泰普通预定手动发送短信
     *
     * @param smsType 预定类型
     * @param batchNo 订单批次号
     */
    public MessageResultBO pcSendResvMessage(String smsType, String batchNo) {

        //查询出一个批次号 预定状态的订单
        List<ResvOrder> resvOrders = resvOrderService.selectList(new EntityWrapper<ResvOrder>()
                .eq("batch_no", batchNo)
                .eq("status", OrderStatus.RESERVE.code));


        MessageDTO messageDTO = new MessageDTO();
        //设置酒店ID , 客户号码 ,订单类型, 模板类型
        messageDTO.setBusinessId(resvOrders.get(0).getBusinessId().longValue());
        messageDTO.setPhone(resvOrders.get(0).getVipPhone());
        messageDTO.setResvOrderTypeId(1);
        //加桌视为跟预定一个模板
        if (smsType.equals("add_table")) {
            messageDTO.setTemplateType(6);
        } else {
            messageDTO.setTemplateType(1);
        }

        //拼接桌位
        List<Map<String, Object>> checkedTables = Lists.newArrayList();

        for (int i = 0; i < resvOrders.size(); i++) {
            ResvOrder resvOrder = resvOrderService.selectOne(new EntityWrapper<ResvOrder>()
                    .eq("resv_order", resvOrders.get(i).getResvOrder()));
            Map<String, Object> map = Maps.newHashMap();
            map.put("tableName", resvOrder.getTableName());
            map.put("tableAreaName", resvOrder.getTableAreaName());
            checkedTables.add(map);
        }

        messageDTO.setCheckedTables(checkedTables);
        //只需要一批订单中的一个订单号
        messageDTO.setResvOrder(resvOrders.get(0).getResvOrder());
        MessageResultBO messageResultBO = messageService.sendMessage(messageDTO);

        return messageResultBO;          //普通预定或者加桌

    }


    /**
     * 酒店桌位换桌
     *
     * @param params
     * @return
     */
    public Tip updateTable(ExchangeTableDTO params) {
        ResvOrderAndroid resvOrder = iResvOrderService.selectOne(new EntityWrapper<ResvOrderAndroid>().eq("resv_order", params.getResvOrder()));
        Table table = tableService.selectOne(new EntityWrapper<Table>().eq("id", params.getTableId()).eq("business_id", params.getBusinessId()));
        //桌位不存在
        if (null == table) {
            return new ErrorTip(404, "桌位不存在");
        }
        TableArea tableArea = tableAreaService.selectOne(new EntityWrapper<TableArea>().eq("id", table.getTableAreaId()).eq("business_id", params.getBusinessId()));
        //区域不存在
        if (null == tableArea) {
            return new ErrorTip(404, "桌位区域不存在");
        }
        Wrapper<ResvOrderAndroid> checkOrderWrapper = new EntityWrapper<ResvOrderAndroid>()
                .eq("table_id", params.getTableId())
                .eq("resv_date", params.getResvDate())
                .eq("meal_type_id", params.getMealTypeId())
                .andNew("status='1'").or("status='2'").or("status='5'");
        if (params.getMealTypeIdA() == null || params.getMealTypeIdA() == 0) {
            checkOrderWrapper.isNull("meal_type_id_a");
        } else {
            checkOrderWrapper.eq("meal_type_id_a", params.getMealTypeIdA());
        }

        if (params.getMealTypeIdB() == null || params.getMealTypeIdB() == 0) {
            checkOrderWrapper.isNull("meal_type_id_b");
        } else {
            checkOrderWrapper.eq("meal_type_id_b", params.getMealTypeIdB());
        }

        ResvOrderAndroid checkOrder = iResvOrderService.selectOne(checkOrderWrapper);
        if (checkOrder != null) {
            return new ErrorTip(404, checkOrder.getTableName() + " 桌位被占用");
        }
        //旧的桌位名称桌位区域名称
        params.setOldTableName(resvOrder.getTableName());
        params.setOldTableAreaName(resvOrder.getTableAreaName());
        //更新订单桌位桌位区域信息
        resvOrder.setOpenIsSync(0);
        resvOrder.setTableId(table.getId());
        resvOrder.setTableName(table.getTableName());
        resvOrder.setMaxPeopleNum(table.getMaxPeopleNum());
        resvOrder.setTableAreaId(table.getTableAreaId());
        resvOrder.setTableAreaName(tableArea.getTableAreaName());
        resvOrder.setTableName(table.getTableName());
        resvOrder.setResvDate(params.getResvDate());
        resvOrder.setMealTypeId(params.getMealTypeId());
        resvOrder.setMealTypeName(params.getMealTypeName());
        resvOrder.setMealTypeIdA((params.getMealTypeIdA() == null || params.getMealTypeIdA() == 0) ? null : params.getMealTypeIdA());
        resvOrder.setMealTypeIdB((params.getMealTypeIdB() == null || params.getMealTypeIdB() == 0) ? null : params.getMealTypeIdB());
        resvOrder.setIschangetable(1);
        boolean update = iResvOrderService.updateById(resvOrder);
        if (update) {
            //换桌成功插入日志表
            //订单日志记录
            ResvOrderLogs resvOrderLogs = new ResvOrderLogs();

            Integer androidUserId = resvOrder.getAndroidUserId();
            String androidUserName = resvOrder.getAndroidUserName();
            resvOrderLogs.setResvOrder(resvOrder.getResvOrder());
            resvOrderLogs.setAndroidUserId(androidUserId);
            resvOrderLogs.setCreatedAt(new Date());
            resvOrderLogs.setStatus(OrderStatus.RESERVE.code);
            resvOrderLogs.setStatusName(OrderStatus.RESERVE.label);

            String desc = (androidUserId == null ? "" : "(" + androidUserName + ")");
            resvOrderLogs.setLogs("换桌:从 " + params.getOldTableName() + " 换至 " + table.getTableName() + "-安卓电话机" + desc);
            iResvOrderLogsService.insert(resvOrderLogs);

            return new SuccessTip();
        } else {
            return new ErrorTip(404, "换桌失败，请重试！");
        }
    }


    /**
     * 发送换桌短信
     *
     * @param exchangeTableDTO 换桌参数
     * @return
     */
    public MessageResultBO sendChangeTableMsg(ExchangeTableDTO exchangeTableDTO) {

        MessageOrderQO messageOrderQO = new MessageOrderQO();
        messageOrderQO.setSmsType("change");
        // 获取短信配置
        messageOrderQO.setType(2);
        messageOrderQO.setResvOrder(exchangeTableDTO.getResvOrder());

        MessageOrderBO messageOrder = iResvOrderService.getMessageOrder(messageOrderQO);
        /* 编辑短信 end... */

        //短信发送逻辑
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setBusinessId(Long.valueOf(messageOrder.getBusinessId()));
        messageDTO.setTemplateType(4);
        messageDTO.setPhone(messageOrder.getVipPhone());
        messageDTO.setResvOrder(exchangeTableDTO.getResvOrder());
        messageDTO.setResvOrderTypeId(1);
        //原先桌位
        messageDTO.setOldTableName(exchangeTableDTO.getOldTableName());
        messageDTO.setOldTableAreaName(exchangeTableDTO.getOldTableAreaName());

        //换桌桌位
//            List<Map<String, Object>> checkedTables = Lists.newArrayList();
//            Map<String, Object> checkedTableMap = Maps.newHashMap();
//            checkedTableMap.put("tableName", messageOrder.getTableAreaName());
//            checkedTableMap.put("tableAreaName", messageOrder.getTableAreaName());
//            checkedTables.add(checkedTableMap);
//            messageDTO.setCheckedTables(checkedTables);

        MessageResultBO messageResultBO = messageService.sendMessage(messageDTO);

        return messageResultBO;          //换桌

    }

    /**
     * 发送退订短信
     *
     * @param addOrderDTOS 订单信息
     */
    public MessageResultBO sendCancelMsgAll(List<String> addOrderDTOS) {


        MessageOrderQO messageOrderQO = new MessageOrderQO();
        messageOrderQO.setSmsType("cancel");
        // 获取短信配置
        messageOrderQO.setType(3);

        messageOrderQO.setResvOrder(addOrderDTOS.get(0));
        MessageOrderBO messageOrder = iResvOrderService.getMessageOrder(messageOrderQO);


        MessageDTO messageDTO = new MessageDTO();

        //设置酒店ID , 客户号码 ,订单类型, 模板类型
        messageDTO.setBusinessId(Long.valueOf(messageOrder.getBusinessId()));
        messageDTO.setPhone(messageOrder.getVipPhone());
        //为普通预定单
        messageDTO.setResvOrderTypeId(1);
        //类型为退订
        messageDTO.setTemplateType(5);
        //只传第一个订单id
        messageDTO.setResvOrder(addOrderDTOS.get(0));

        //拼接桌位
        List<Map<String, Object>> checkedTables = Lists.newArrayList();

        for (int i = 0; i < addOrderDTOS.size(); i++) {

            ResvOrderAndroid resvOrder = iResvOrderService.selectOne(new EntityWrapper<ResvOrderAndroid>()
                    .eq("resv_order", addOrderDTOS.get(i)));
            Map<String, Object> map = Maps.newHashMap();
            map.put("tableName", resvOrder.getTableName());
            map.put("tableAreaName", resvOrder.getTableAreaName());
            checkedTables.add(map);
        }

        messageDTO.setCheckedTables(checkedTables);

        MessageResultBO messageResultBO = messageService.sendMessage(messageDTO); //退订

        return messageResultBO;          //退订


    }

    public Integer getTemplateStatus(Integer businessId, Integer templateType) {

        BusinessSmsTemplate businessSmsTemplate = iBusinessSmsTemplateService.selectOne(new EntityWrapper<BusinessSmsTemplate>()
                .eq("business_id", businessId)
                .eq("template_type", templateType));

        return businessSmsTemplate.getStatus();
    }


}
