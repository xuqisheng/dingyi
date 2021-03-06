package com.zhidianfan.pig.yd.moduler.resv.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.common.util.PageFactory;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessService;
import com.zhidianfan.pig.yd.moduler.common.service.IResvOrderAndroidService;
import com.zhidianfan.pig.yd.moduler.common.service.IResvOrderService;
import com.zhidianfan.pig.yd.moduler.common.service.IResvOrderThirdService;
import com.zhidianfan.pig.yd.moduler.meituan.bo.OrderQueryBO;
import com.zhidianfan.pig.yd.moduler.meituan.service.MeituanService;
import com.zhidianfan.pig.yd.moduler.resv.bo.ResvOrderThirdBO;
import com.zhidianfan.pig.yd.moduler.resv.dto.AddOrderDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.ThirdQueryDTO;
import com.zhidianfan.pig.yd.moduler.wechat.util.OrderTemplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.zhidianfan.pig.yd.moduler.meituan.service.YdService.wechatPushMes;


/**
 * @Author: huzp
 * @Date: 2018/9/20 14:37
 */
@Service
@Transactional
@Slf4j
public class ThirdOrderService {

    @Autowired
    private IResvOrderThirdService iResvOrderThirdService;

    @Autowired
    private AddOrderService addOrderService;

    @Autowired
    private MeituanService meituanService;

    @Autowired
    private VipService vipService;

    @Autowired
    private IResvOrderAndroidService iResvOrderService;

    @Autowired
    private IResvOrderService resvOrderService;

    @Autowired
    private IBusinessService iBusinessService;


    /**
     * 获取第三方平台未处理订单信息
     *
     * @param thirdQueryDTO 查询条件，酒店id必须
     * @return 返回第三方平台未处理订单信息
     */
    public Page<ResvOrderThirdBO> getConditionThirdOrder(ThirdQueryDTO thirdQueryDTO) {

        Page<ResvOrderThirdBO> page = new PageFactory().defaultPage();


        Page<ResvOrderThirdBO> resvOrderThirds = iResvOrderThirdService.getThirdOrder(page, thirdQueryDTO);

        return resvOrderThirds;
    }

    /**
     * 获取第三方平台未处理订单信息
     *
     * @param thirdQueryDTO 查询条件，酒店id必须
     * @return 返回第三方平台未处理订单信息
     */
    public Page<ResvOrderThirdBO> getConditionWeChatThirdOrder(ThirdQueryDTO thirdQueryDTO) {

        Page<ResvOrderThirdBO> page = new PageFactory().defaultPage();


        Page<ResvOrderThirdBO> resvOrderThirds = iResvOrderThirdService.getWeChatThirdOrder(page, thirdQueryDTO);

        return resvOrderThirds;
    }

    /**
     * 获取第三方平台未处理订单信息
     *
     * @param thirdQueryDTO 查询条件，酒店id必须
     * @return 返回第三方平台未处理订单信息
     */
    public List<ResvOrderThirdBO> getAllConditionThirdOrder(ThirdQueryDTO thirdQueryDTO) {


        List<ResvOrderThirdBO> resvOrderThirds = iResvOrderThirdService.getAllThirdOrder(thirdQueryDTO);

        return resvOrderThirds;
    }


    /**
     * 插入订单以及回改第三方表中的信息
     *
     * @param addOrderDTO 订单信息
     * @return 操作结果
     */
    public Tip updateOrderNo(AddOrderDTO addOrderDTO) {


        ResvOrderThird resvOrderThird = new ResvOrderThird();
        //设置为已读
        resvOrderThird.setFlag(1);
        //更新已读状态
        iResvOrderThirdService.update(resvOrderThird, new EntityWrapper<ResvOrderThird>().
                eq("third_order_no", addOrderDTO.getThirdOrderNo()));

        //只有安卓电话机端 有易订公众号
        ResvOrderThird resvOrderThird1 = iResvOrderThirdService.selectOne(new EntityWrapper<ResvOrderThird>()
                .eq("third_order_no", addOrderDTO.getThirdOrderNo()));
        if (!resvOrderThird1.getStatus().equals(10) && !resvOrderThird1.getStatus().equals(45)) {
            ErrorTip errorTip = new ErrorTip();
            errorTip.setMsg("客户已经取消");
            return errorTip;
        }

        Tip tip = addOrderService.inserOrder(addOrderDTO);

        //如果插入失败直接返回桌位被占用
        if (tip instanceof ErrorTip) {
            return tip;
        }

        //如果为美团的话,则进行美团操作
        if (addOrderDTO.getThirdPartyId() == 2) {

            Tip getOrderTip = meiTuanGetOrder(addOrderDTO, 1, resvOrderThird);
            //如果美团接单失败 则返回接单失败
            if (getOrderTip instanceof ErrorTip) {
                return getOrderTip;
            }

        }

        //易订公众号接单
        if (addOrderDTO.getThirdPartyId() == 4) {

            //修改订单状态,为接单
            //已经接受
            resvOrderThird1.setResult(1);
            resvOrderThird1.setStatus(40);
            resvOrderThird1.setBatchNo(addOrderDTO.getBatchNo());
            iResvOrderThirdService.update(resvOrderThird1, new EntityWrapper<ResvOrderThird>().
                    eq("third_order_no", addOrderDTO.getThirdOrderNo()));

            //查询或者新增该客户然后更新订单表信息
            Vip vip = new Vip();
            BeanUtils.copyProperties(addOrderDTO, vip);
            //去除订单信息中的tag 与 remark
            vip.setTag(null);
            vip.setRemark(null);
            vipService.updateOrInsertVip(vip);

            //更新订单表中的信息
            Vip basicVipInfo = vipService.getBasicVipInfo(addOrderDTO.getBusinessId(), vip.getVipPhone());
            ResvOrderAndroid resvOrder = new ResvOrderAndroid();
            resvOrder.setVipPhone(basicVipInfo.getVipPhone());
            resvOrder.setVipId(basicVipInfo.getId());
            resvOrder.setVipName(basicVipInfo.getVipName());
            resvOrder.setVipSex(basicVipInfo.getVipSex());
            resvOrder.setVipCompany(basicVipInfo.getVipCompany());
            iResvOrderService.update(resvOrder, new EntityWrapper<ResvOrderAndroid>()
                    .eq("third_order_no", addOrderDTO.getThirdOrderNo()));

            log.info("商家接单成功");


        }


        return SuccessTip.SUCCESS_TIP;
    }


    /**
     * Pc 端 插入订单以及回改第三方表中的信息
     *
     * @param addOrderDTO
     * @return 操作结果
     */
    public Tip pcUpdateOrderNo(AddOrderDTO addOrderDTO) {

        ResvOrderThird resvOrderThird = new ResvOrderThird();
        //设置为已读
        resvOrderThird.setFlag(1);
        //更新已读状态
        iResvOrderThirdService.update(resvOrderThird, new EntityWrapper<ResvOrderThird>().
                eq("third_order_no", addOrderDTO.getThirdOrderNo()));

        Tip tip;
        tip = addOrderService.pcInserOrder(addOrderDTO);

        //如果插入失败直接返回桌位被占用
        if (tip instanceof ErrorTip) {
            return tip;
        }

        //如果为美团的话,则进行美团操作
        if (addOrderDTO.getThirdPartyId() == 2) {
            //如果为美团的话,则进行美团操作
            if (addOrderDTO.getThirdPartyId() == 2) {

                Tip getOrderTip = meiTuanGetOrder(addOrderDTO, 2, resvOrderThird);
                //如果美团接单失败 则返回接单失败
                if (getOrderTip instanceof ErrorTip) {
                    return getOrderTip;
                }

            }
        }

        return SuccessTip.SUCCESS_TIP;
    }


    /**
     * 修改第三方订单result 为 2 拒绝
     *
     * @param orderno 第三方订单id
     * @return 操作是否成功
     */
    public boolean rejectOrder(String orderno) {

        ResvOrderThird resvOrderThird = new ResvOrderThird();
        //设置为已读
        resvOrderThird.setFlag(1);
        resvOrderThird.setResult(2);
        resvOrderThird.setUpdatedAt(new Date());

        boolean third_order_no = iResvOrderThirdService.update(resvOrderThird, new EntityWrapper<ResvOrderThird>()
                .eq("third_order_no", orderno));

        return third_order_no;
    }

    /**
     * 查询一个酒店未读的最新第三方订单
     *
     * @param businessId 酒店id
     * @return 订单信息
     */
    public ResvOrderThirdBO getNewestOrder(Integer businessId) {

        ResvOrderThirdBO newestOrder = iResvOrderThirdService.getNewestOrder(businessId);

        return newestOrder;
    }

    /**
     *
     * @param thridNo 退订订单id
     * @return  操作结果
     */
    public boolean readUnsubscribeOrder(String thridNo) {

        ResvOrderThird resvOrderThird = new ResvOrderThird();
        //设置已读
        resvOrderThird.setFlag(1);

        boolean b = iResvOrderThirdService.update(resvOrderThird, new EntityWrapper<ResvOrderThird>().eq("third_order_no", thridNo));

        return b;
    }

    /**
     * @param thirdno 第三方订单号
     * @return 第三方订单
     */
    public ResvOrderThird selecltOneBythirdNo(String thirdno) {

        ResvOrderThird resvOrderThird = iResvOrderThirdService.selectOne(new EntityWrapper<ResvOrderThird>().eq("third_order_no", thirdno));

        return resvOrderThird;
    }


    /**
     * 美团订单接单
     *
     * @param addOrderDTO 新增订单的信息
     * @param type 安卓电话机类型或者标准版类型订单
     * @param resvOrderThird 第三方订单信息
     * @return 接单结果
     */
    public Tip meiTuanGetOrder(AddOrderDTO addOrderDTO, Integer type, ResvOrderThird resvOrderThird) {
        //pc端 去美团更新桌位信息
        Tip meituanTip = meituanService.orderUpdate(addOrderDTO.getBusinessId(), addOrderDTO.getBatchNo(),
                addOrderDTO.getResvOrderType(), addOrderDTO.getThirdOrderNo());

        //接单成功
        if (meituanTip.getCode() == 200) {
            //回调获取手机号
            OrderQueryBO orderQueryBO = meituanService.orderQuery(addOrderDTO.getBusinessId(), addOrderDTO.getBatchNo(), addOrderDTO.getThirdOrderNo());
            JSONObject data = (JSONObject) orderQueryBO.getData();
            String phone = data.get("phone").toString();
            resvOrderThird.setVipPhone(phone);

            //建立或者更新客户信息
            Vip vip = new Vip();
            BeanUtils.copyProperties(addOrderDTO, vip);
            //去除订单信息中的tag 与 remark
            vip.setTag(null);
            vip.setRemark(null);

            vip.setVipPhone(phone);
            vipService.updateOrInsertVip(vip);

            //更新订单表中的信息
            Vip basicVipInfo = vipService.getBasicVipInfo(addOrderDTO.getBusinessId(), phone);

            if (type == 1) {
                ResvOrderAndroid resvOrder = new ResvOrderAndroid();
                resvOrder.setVipPhone(basicVipInfo.getVipPhone());
                resvOrder.setVipId(basicVipInfo.getId());
                resvOrder.setVipName(basicVipInfo.getVipName());
                resvOrder.setVipSex(basicVipInfo.getVipSex());
                resvOrder.setVipCompany(basicVipInfo.getVipCompany());
                iResvOrderService.update(resvOrder, new EntityWrapper<ResvOrderAndroid>()
                        .eq("third_order_no", addOrderDTO.getThirdOrderNo()));

            } else if (type == 2) {

                ResvOrder resvOrder2 = new ResvOrder();
                resvOrder2.setVipPhone(basicVipInfo.getVipPhone());
                resvOrder2.setVipId(basicVipInfo.getId());
                resvOrder2.setVipName(basicVipInfo.getVipName());
                resvOrder2.setVipSex(basicVipInfo.getVipSex());
                resvOrder2.setVipCompany(basicVipInfo.getVipCompany());
                resvOrderService.update(resvOrder2, new EntityWrapper<ResvOrder>()
                        .eq("third_order_no", addOrderDTO.getThirdOrderNo()));

            }

            //更新第三方订单表信息 手机号
            iResvOrderThirdService.update(resvOrderThird, new EntityWrapper<ResvOrderThird>().
                    eq("third_order_no", addOrderDTO.getThirdOrderNo()));
        } else {

            // 如果接单失败,删除已有订单

            if (type == 1) {
                iResvOrderService.delete(new EntityWrapper<ResvOrderAndroid>().eq("batch_no", addOrderDTO.getBatchNo()));
            } else if (type == 2) {
                resvOrderService.delete(new EntityWrapper<ResvOrder>().eq("batch_no", addOrderDTO.getBatchNo()));

            }

            ErrorTip errorTip = new ErrorTip();
            errorTip.setMsg("客户已取消");
            return errorTip;
        }

        return SuccessTip.SUCCESS_TIP;
    }

    /**
     * 拒绝yd 公众号订单
     *
     * @param orderno 第三方订单号
     * @return 操作结果
     */
    public Tip rejectPublicAccountOrder(String orderno) {

        //只有安卓电话机端 有易订公众号
        ResvOrderThird resvOrderThird1 = iResvOrderThirdService.selectOne(new EntityWrapper<ResvOrderThird>()
                .eq("third_order_no", orderno));
        if (resvOrderThird1.getStatus() != 10) {
            ErrorTip errorTip = new ErrorTip();
            errorTip.setMsg("客户已经取消");
            return errorTip;
        }

        //如果客户没有取消, 商户取消订单更新 字段
        ResvOrderThird resvOrderThird = new ResvOrderThird();
        //设置为已读
        resvOrderThird.setFlag(1);
        //拒绝
        resvOrderThird.setResult(2);
        resvOrderThird.setUpdatedAt(new Date());
        //设置为商家拒单
        resvOrderThird.setStatus(30);

        iResvOrderThirdService.update(resvOrderThird, new EntityWrapper<ResvOrderThird>()
                .eq("third_order_no", orderno));

        Business business = iBusinessService.selectById(resvOrderThird1.getBusinessId());
        ResvOrderAndroid resvOrderAndroid = new ResvOrderAndroid();
        resvOrderAndroid.setBusinessName(business.getBusinessName());
        resvOrderAndroid.setTableName("");


        log.info("商家接单成功,推送商家拒单给微信客户.");
        wechatPushMes(resvOrderAndroid, resvOrderThird1, OrderTemplate.ORDER_RESV_HOTEL_CANCEL, business);


        return SuccessTip.SUCCESS_TIP;
    }

    /**
     * 将微信公众号订单状态修改为45
     *
     * @param orderno 订单状态号
     * @return 返回操作成功结果
     */
    public Boolean paStatusTo45(String orderno) {


        ResvOrderThird resvOrderThird = iResvOrderThirdService.selectOne(new EntityWrapper<ResvOrderThird>().
                eq("third_order_no", orderno));

        if(resvOrderThird.getStatus().equals(45) ){
            log.info("订单已经是已经接单为入座状态了!");
            return true;
        }

        resvOrderThird.setStatus(45);
        iResvOrderThirdService.updateById(resvOrderThird);

        Business business = iBusinessService.selectOne(new EntityWrapper<Business>().eq("id", resvOrderThird.getBusinessId()));

        //微信推送
        log.info("商家接单成功,推送接单成功给微信客户.");

        ResvOrderAndroid resvOrderAndroid = new ResvOrderAndroid();
        resvOrderAndroid.setTableName("");
        resvOrderAndroid.setTableAreaName("");

        wechatPushMes(resvOrderAndroid, resvOrderThird, OrderTemplate.ORDER_RESV_SUCCESS, business);

        return true;
    }
}
