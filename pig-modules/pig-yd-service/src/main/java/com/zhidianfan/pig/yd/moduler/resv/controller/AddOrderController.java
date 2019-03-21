package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Maps;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderAndroid;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderThird;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.common.service.IResvOrderAndroidService;
import com.zhidianfan.pig.yd.moduler.meituan.service.MeituanService;
import com.zhidianfan.pig.yd.moduler.resv.dto.AddOrderDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.CheckoutBillDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.EditStatusDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.ExchangeTableDTO;
import com.zhidianfan.pig.yd.moduler.resv.enums.OrderStatus;
import com.zhidianfan.pig.yd.moduler.resv.service.AddOrderService;
import com.zhidianfan.pig.yd.moduler.resv.service.ResvLineService;
import com.zhidianfan.pig.yd.moduler.resv.service.SyncPushService;
import com.zhidianfan.pig.yd.moduler.resv.service.ThirdOrderService;
import com.zhidianfan.pig.yd.moduler.sms.bo.message.MessageResultBO;
import com.zhidianfan.pig.yd.utils.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 订单新增
 *
 * @Author: huzp
 * @Date: 2018/9/20 14:05
 */
@RestController
@RequestMapping("/neworder")
@Slf4j
public class AddOrderController {

    /**
     * 新增订单service
     */
    @Autowired
    private AddOrderService addOrderService;

    /**
     * 第三方平台service
     */
    @Autowired
    private ThirdOrderService thirdOrderService;

    /**
     * 排队service
     */
    @Autowired
    private ResvLineService resvLineService;


    @Autowired
    private MeituanService meituanService;

    @Autowired
    private SyncPushService syncPushService;

    @Autowired
    private IResvOrderAndroidService iResvOrderAndroidService;


    /**
     * 普通订单预定
     *
     * @return 返回成功或者失败信息
     */
    @PostMapping("/ordinaryresv")
    public ResponseEntity newOrdinaryOrder(@RequestBody AddOrderDTO addOrderDTO) {


        //判断是否是锁台
        String status = addOrderDTO.getStatus();
        boolean editFalg;
        Tip tip;

        //锁台无需判断客户
        if (!status.equals(OrderStatus.LOCK.code)) {
            //1.判断有无这个客户 客户只有4个参数  1. 姓氏 2. 手机号 3.性别 4.酒店id
            editFalg = addOrderService.editVip(addOrderDTO);
            //如果客户信息报错直接返回错误
            if (!editFalg) {
                tip = new ErrorTip();
                ((ErrorTip) tip).setMsg("更新客户信息出错");
                return ResponseEntity.ok(tip);
            }
        }

        //2.1根据传来的信息生成批次号
        String batchNo = "pc" + IdUtils.makeOrderNo();
        addOrderDTO.setBatchNo(batchNo);
        //2.2 将订单号 批次号 以及订单信息插入订单表
        tip = addOrderService.inserOrder(addOrderDTO);

        if (tip instanceof ErrorTip) {
            return ResponseEntity.ok(tip);
        }



        //同步安卓电话机和小程序的订单状态
        if (StringUtils.isNotBlank(addOrderDTO.getDeviceType())){
            syncPushService.SyncOrderStatus(addOrderDTO.getDeviceType(), addOrderDTO.getBusinessId());
        }


        //发送普通预定短信短信,订单为预订单且 issendMsg 为1
        if (status.equals(OrderStatus.RESERVE.code) && (addOrderDTO.getIssendmsg() == 1)) {
            try {
                MessageResultBO messageResultBO = addOrderService.sendResvMessage("order", batchNo);
                if (null != messageResultBO) {
                    return processResult(messageResultBO, tip);
                }
            }catch (Exception e){
                e.printStackTrace();
                SuccessTip successTip = new SuccessTip();
                successTip.setCode(200);
                successTip.setMsg("预订成功,但短信发送失败");
                return  ResponseEntity.ok(successTip);
            }
        }

        return ResponseEntity.ok(tip);
    }

    /**
     * 加桌 同批次生成新订单,发送短信
     *
     * @return 返回加桌结果
     */
    @PostMapping("/tableresv")
    public ResponseEntity newTbaleOrder(@RequestBody AddOrderDTO addOrderDTO) {

        Tip tip ;

        //1.判断有无这个客户
        boolean editFalg = addOrderService.editVip(addOrderDTO);

        //如果客户信息报错直接返回错误
        if (!editFalg) {
            tip = new ErrorTip();
            ((ErrorTip) tip).setMsg("加桌,更新客户信息出错");
            return ResponseEntity.ok(tip);
        }

        //2.1根据传来的订单号和批次号 生成 以及订单信息插入订单表
        tip = addOrderService.inserOrder(addOrderDTO);
        if (tip instanceof ErrorTip) {
            return ResponseEntity.ok(tip);
        }


        if (StringUtils.isNotBlank(addOrderDTO.getDeviceType())){
            syncPushService.SyncOrderStatus(addOrderDTO.getDeviceType(), addOrderDTO.getBusinessId());
        }

        //发送加桌短信
        if (addOrderDTO.getStatus().equals(OrderStatus.RESERVE.code)) {
            try {
                MessageResultBO messageResultBO = addOrderService.sendResvMessage("add_table", addOrderDTO.getBatchNo());
                if (null != messageResultBO) {
                    return processResult(messageResultBO, tip);
                }
            }catch (Exception e){
                e.printStackTrace();
                SuccessTip successTip = new SuccessTip();
                successTip.setCode(200);
                successTip.setMsg("加桌成功,但短信发送失败");
                return  ResponseEntity.ok(successTip);
            }
        }


        return ResponseEntity.ok(tip);
    }

    /**
     * 排队转入座 生成订单号
     *
     * @return 返回排队结果
     */
    @PostMapping("/lineresv")
    public ResponseEntity newlineOrder(@RequestBody AddOrderDTO addOrderDTO) {

        Tip tip = SuccessTip.SUCCESS_TIP;

        //1.判断有无这个客户
        boolean editFalg = addOrderService.editVip(addOrderDTO);

        //如果客户信息报错直接返回错误
        if (!editFalg) {
            tip = new ErrorTip();
            ((ErrorTip) tip).setMsg("更新客户信息出错");
            return ResponseEntity.ok(tip);
        }


        //2.1根据传来的信息生成批次号号
        addOrderDTO.setBatchNo("pc" + IdUtils.makeOrderNo());

        //3.插入订单 ， 回写排队表批次号 修改排队状态
        boolean b = resvLineService.updateLine(addOrderDTO);
        if (!b) {
            tip = new ErrorTip();
            ((ErrorTip) tip).setMsg("桌位被占用或更新排队号出错");
        }

        if (StringUtils.isNotBlank(addOrderDTO.getDeviceType())){
            syncPushService.SyncOrderStatus(addOrderDTO.getDeviceType(), addOrderDTO.getBusinessId());
        }

        return ResponseEntity.ok(tip);
    }


    /**
     * 第三方平台订单预定
     *
     * @return 返回美团预定结果
     */
    @PostMapping("/thirdresv")
    public ResponseEntity newThirdOrder(@RequestBody AddOrderDTO addOrderDTO) {

        //根据传来的信息生成批次号
        String batchNo = "pc" + IdUtils.makeOrderNo();
        addOrderDTO.setBatchNo(batchNo);
        // 插入 订单以及回改第三方表中的订单号
        Tip tip = thirdOrderService.updateOrderNo(addOrderDTO);

        if (StringUtils.isNotBlank(addOrderDTO.getDeviceType())){
            syncPushService.SyncOrderStatus(addOrderDTO.getDeviceType(), addOrderDTO.getBusinessId());
        }

        //发送普通预定短信短信,转订单成功 而且 issendMsg 为1
        if (tip instanceof SuccessTip && addOrderDTO.getIssendmsg() == 1) {
            try {
                MessageResultBO messageResultBO = addOrderService.sendResvMessage("order", addOrderDTO.getBatchNo());
                if (null != messageResultBO) {
                    return processResult(messageResultBO, tip);
                }
            }catch (Exception e){
                e.printStackTrace();
                SuccessTip successTip = new SuccessTip();
                successTip.setCode(200);
                successTip.setMsg("第三方订单成功,但短信发送失败");
                return  ResponseEntity.ok(successTip);
            }
        }

        return ResponseEntity.ok(tip);
    }

    /**
     * 天泰第三方接单
     * @param addOrderDTO 订单信息
     * @return 接单结果
     */
    @PostMapping("/pcthirdresv")
    public ResponseEntity newPcThirdOrder(@RequestBody AddOrderDTO addOrderDTO) {

        //根据传来的信息生成批次号
        String batchNo = "pc" + IdUtils.makeOrderNo();
        addOrderDTO.setBatchNo(batchNo);
        // 插入 订单以及回改第三方表中的订单号
        Tip tip = thirdOrderService.pcUpdateOrderNo(addOrderDTO);


        //发送普通预定短信短信,转订单成功 而且 issendMsg 为1
        if (tip instanceof SuccessTip && addOrderDTO.getIssendmsg() == 1) {
            try {
                MessageResultBO messageResultBO = addOrderService.pcSendResvMessage("order", addOrderDTO.getBatchNo());
                if (null != messageResultBO) {
                    return processResult(messageResultBO, tip);
                }
            }catch (Exception e){
                e.printStackTrace();
                SuccessTip successTip = new SuccessTip();
                successTip.setCode(200);
                successTip.setMsg("天泰预定成功,但短信发送失败");
                return  ResponseEntity.ok(successTip);
            }
        }

        return ResponseEntity.ok(tip);
    }


    /**
     * 提前美团接单
     *
     * @param businessid 酒店id
     * @param thirdno    第三方订单号
     * @return 返回接单结果
     */
    @GetMapping("/mtorder")
    public ResponseEntity updateMTOrder(@RequestParam("businessid") Integer businessid, @RequestParam("thirdno") String thirdno) {

        Tip tip = meituanService.earlyOrderUpdate(businessid, thirdno);
        return ResponseEntity.ok(tip);
    }


    /**
     * 第三方订单信息
     *
     * @param thirdno 第三方订单号
     * @return 第三方订单
     */
    @GetMapping("/thirdorderstatus")
    public ResponseEntity getThirdOrderStatus(@RequestParam("thirdno") String thirdno) {

        ResvOrderThird resvOrderThird = thirdOrderService.selecltOneBythirdNo(thirdno);

        return ResponseEntity.ok(resvOrderThird);
    }

    /**
     * 锁台解锁
     */
    @PostMapping("/unlocktable")
    public ResponseEntity unlockTable(@RequestBody AddOrderDTO addOrderDTO) {


        boolean unlock = addOrderService.unlock(addOrderDTO);

        Tip tip = (unlock ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP);

        if (StringUtils.isNotBlank(addOrderDTO.getDeviceType())){
            syncPushService.SyncOrderStatus(addOrderDTO.getDeviceType(), addOrderDTO.getBusinessId());
        }

        return ResponseEntity.ok(tip);
    }


    /**
     * 修改订单状态,退订则发送短信
     *
     * @param editStatusDTO 订单信息
     * @return 返回操作结果
     */
    @PostMapping("/resvstatus")
    public ResponseEntity editResvStatus(@RequestBody EditStatusDTO editStatusDTO) {

        //获取订单号 list
        List<String> resvOrders = new ArrayList<>();
        List<CheckoutBillDTO> checkoutBills = editStatusDTO.getCheckoutBills();
        for (CheckoutBillDTO checkoutBill : checkoutBills) {
            resvOrders.add(checkoutBill.getResvOrder());
        }

        boolean editResvStatus = addOrderService.editResvStatus(resvOrders, editStatusDTO);

        Tip tip = (editResvStatus ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP);


        //需要查询business_sms_template表的status 状态
        Integer templateStatus = addOrderService.getTemplateStatus(editStatusDTO.getBusinessId(), 5);


        ResvOrderAndroid resvOrderAndroid = iResvOrderAndroidService.selectOne(new EntityWrapper<ResvOrderAndroid>().eq("resv_order",resvOrders.get(0)));


        //互相推送消息
        if (StringUtils.isNotBlank(editStatusDTO.getDeviceType())){
            syncPushService.SyncOrderStatus(editStatusDTO.getDeviceType(), editStatusDTO.getBusinessId());
        }

        if (resvOrderAndroid == null){
            return ResponseEntity.ok(tip);
        }

        //如果是退订，且门店后台配置为每次发送
        if (editStatusDTO.getStatus().equals(OrderStatus.DEBOOK.code) && templateStatus == 1 && StringUtils.isNotBlank(resvOrderAndroid.getVipPhone())) {
            try {
                MessageResultBO messageResultBO = addOrderService.sendCancelMsgAll(resvOrders);
                if (null != messageResultBO) {
                    return processResult(messageResultBO, tip);
                }
            }catch (Exception e){
                e.printStackTrace();
                SuccessTip successTip = new SuccessTip();
                successTip.setCode(200);
                successTip.setMsg("状态修改成功,但短信发送失败");
                return  ResponseEntity.ok(successTip);
            }
        }


        return ResponseEntity.ok(tip);
    }

    /**
     * 确认订单是否会来
     *
     * @param editStatusDTO 订单信息
     * @return 操作结果
     */
    @PostMapping("/resvconfirm")
    public ResponseEntity editResvConfirm(@RequestBody EditStatusDTO editStatusDTO) {

        boolean editResvStatus = addOrderService.editResConfirm(editStatusDTO.getCheckoutBills(), editStatusDTO.getConfirm());

        Tip tip = (editResvStatus ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP);

        if (StringUtils.isNotBlank(editStatusDTO.getDeviceType())){
            syncPushService.SyncOrderStatus(editStatusDTO.getDeviceType(), editStatusDTO.getBusinessId());
        }

        return ResponseEntity.ok(tip);
    }


    /**
     * 酒店订单换桌
     *
     * @param exchangeTableDTO 换桌需要参数
     * @return 操作结果
     */
    @PostMapping("/orderexchangetable")
    public ResponseEntity updateTable(@Valid ExchangeTableDTO exchangeTableDTO) {
        Tip tip = addOrderService.updateTable(exchangeTableDTO);


        if (StringUtils.isNotBlank(exchangeTableDTO.getDeviceType())){
            syncPushService.SyncOrderStatus(exchangeTableDTO.getDeviceType(), exchangeTableDTO.getBusinessId());
        }

        //换桌 应该查询配置表
        Integer templateStatus = addOrderService.getTemplateStatus(exchangeTableDTO.getBusinessId(), 4);


        //换桌发送短信条件换桌操作成功, 门店后台配置为每次发送
        if (tip instanceof SuccessTip && templateStatus == 1) {
            try {
                MessageResultBO messageResultBO = addOrderService.sendChangeTableMsg(exchangeTableDTO);
                if (null != messageResultBO) {
                    return processResult(messageResultBO, tip);
                }
            }catch (Exception e){
                e.printStackTrace();
                SuccessTip successTip = new SuccessTip();
                successTip.setCode(200);
                successTip.setMsg("酒店换桌成功,但短信发送失败");
                return  ResponseEntity.ok(successTip);
            }
        }


        return ResponseEntity.ok(tip);
    }


    /**
     * 发送订单提醒短信
     *
     * @param batchNo 订单批次号
     * @return 返回处理结果
     */
    @GetMapping("/remindresvordermsg")
    public ResponseEntity sendRemindResvOrderMsg(@RequestParam("batchNo") String batchNo) {

        MessageResultBO messageResultBO;
        try {
             messageResultBO = addOrderService.sendResvMessage("order", batchNo);
        }catch (Exception e){
            e.printStackTrace();
            ErrorTip errorTip = new ErrorTip();
            errorTip.setCode(200);
            errorTip.setMsg("发送订单提醒短信,发送失败");
            return ResponseEntity.ok(errorTip);
        }

        Tip tip = (null != messageResultBO ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP);

        return ResponseEntity.ok(tip);
    }


    protected static ResponseEntity processResult(MessageResultBO messageResultBO, Tip tip) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("code", tip.getCode());
        map.put("msg", tip.getMsg());
        map.put("errormsg", messageResultBO.getErrorMsg());
        return ResponseEntity.ok(map);
    }


}
