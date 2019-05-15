package com.zhidianfan.pig.yd.moduler.meituan.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.common.service.*;
import com.zhidianfan.pig.yd.moduler.meituan.bo.TianGangOrderBO;
import com.zhidianfan.pig.yd.moduler.meituan.service.rmi.PushFeign;
import com.zhidianfan.pig.yd.moduler.meituan.service.rmi.dto.JgPush;
import com.zhidianfan.pig.yd.utils.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2019-05-15
 * @Modified By:
 */
@Service
@Slf4j
public class TianGangService {

    @Autowired
    private IBusinessService businessService;

    @Autowired
    private IResvOrderThirdService resvOrderThirdService;

    @Autowired
    private IMealTypeService mealTypeService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PushFeign pushFeign;

    @Autowired
    private IResvMeetingKeyService resvMeetingKeyService;

    @Autowired
    private IVipService vipService;

    /**
     * 创建天港订单
     * @param tianGangOrderBO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Tip createOrder(TianGangOrderBO tianGangOrderBO) throws ParseException {

        Tip tip = null;

        //获取酒店信息
        Business business = businessService.selectOne(new EntityWrapper<Business>().eq("branch_code",tianGangOrderBO.getBranchCode()).eq("status","1"));
        if(business == null){
            tip = new ErrorTip(500,"酒店不存在");
            return tip;
        }

        //获取餐别信息
        MealType mealType = mealTypeService.selectOne(new EntityWrapper<MealType>().eq("config_id",tianGangOrderBO.getMealConfigId()).eq("status","1"));
        if(mealType == null){
            tip = new ErrorTip(500,"餐别不存在");
            return tip;
        }

        //新增客户
        Vip vip = getVipInfo(business.getId(),tianGangOrderBO.getVipPhone());
        if(vip == null){
            Vip vip2 = new Vip();
            BeanUtils.copyProperties(tianGangOrderBO,vip2);
            vip2.setBusinessName(business.getBusinessName());
            vip2.setBusinessId(business.getId());
            vipService.insert(vip2);
        }

        //获取新增后的vip
        Vip vip1 = getVipInfo(business.getId(),tianGangOrderBO.getVipPhone());

        //判断是普通订单还是宴会订单
        if(tianGangOrderBO.getOrderType() == 1){
            //生成第三方订单
            ResvOrderThird resvOrderThird = new ResvOrderThird();
            BeanUtils.copyProperties(tianGangOrderBO,resvOrderThird);
            resvOrderThird.setCreatedAt(new Date());
            resvOrderThird.setBusinessId(business.getId());
            resvOrderThird.setMealTypeId(mealType.getId());
            resvOrderThird.setMealTypeName(mealType.getMealTypeName());
            resvOrderThird.setStatus(10);
            resvOrderThird.setVipSex("男".equals(tianGangOrderBO.getVipSex()) ? "先生" : "女士");
            resvOrderThird.setSource("天港钉钉");
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            resvOrderThird.setResvDate(format.parse(tianGangOrderBO.getResvDate()));

            boolean insert = resvOrderThirdService.insert(resvOrderThird);

            if(insert){

                tip = new SuccessTip(200,"预订成功");

                try {

                    //订单推送pad端
                    if (business.getIsPadPush() == 1) {
                        JgPush jgPush = new JgPush();
                        jgPush.setBusinessId(String.valueOf(resvOrderThird.getBusinessId()));
                        jgPush.setMsgSeq(String.valueOf(getNextDateId("MT_ORDER")));
                        jgPush.setType("PAD");
                        jgPush.setUsername("13777575146");
                        String orderMsg = JsonUtils.obj2Json(resvOrderThird).replaceAll("\r|\n", "").replaceAll("\\s*", "");
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("data", orderMsg);
                        jsonObject.put("type", "8");
                        jsonObject.put("orderType", "dd");
                        jgPush.setMsg(jsonObject.toString());

                        pushFeign.pushMsg(jgPush.getType(), jgPush.getUsername(), jgPush.getMsgSeq(), jgPush.getBusinessId(), jgPush.getMsg());
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }else {
                tip = new ErrorTip(500,"预订失败");
            }
        }else {

            //生成宴会线索
            ResvMeetingKey resvMeetingKey = new ResvMeetingKey();
            BeanUtils.copyProperties(tianGangOrderBO,resvMeetingKey);
            resvMeetingKey.setCreatedAt(new Date());
            resvMeetingKey.setStatusName("线索阶段");
            resvMeetingKey.setBusinessId(business.getId());
            resvMeetingKey.setBusinessName(business.getBusinessName());
            resvMeetingKey.setMealTypeId(mealType.getId());
            resvMeetingKey.setMealTypeName(mealType.getMealTypeName());
            resvMeetingKey.setStatus(1);
            resvMeetingKey.setSource(3);
            resvMeetingKey.setVipId(vip1.getId());
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            resvMeetingKey.setResvDate(format.parse(tianGangOrderBO.getResvDate()));
            String keyNo = IdUtils.makeOrderNo();
            resvMeetingKey.setKeyNo("XS"+keyNo);
            resvMeetingKey.setKeyNoBusiness("钉钉");

            boolean insert = resvMeetingKeyService.insert(resvMeetingKey);

            if(insert){

                tip = new SuccessTip(200,"预订成功");

                try {

                    //订单推送pad端
                    if (business.getIsPadPush() == 1) {
                        JgPush jgPush = new JgPush();
                        jgPush.setBusinessId(String.valueOf(resvMeetingKey.getBusinessId()));
                        jgPush.setMsgSeq(String.valueOf(getNextDateId("MT_ORDER")));
                        jgPush.setType("PAD");
                        jgPush.setUsername("13777575146");
                        String orderMsg = JsonUtils.obj2Json(resvMeetingKey).replaceAll("\r|\n", "").replaceAll("\\s*", "");
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("data", orderMsg);
                        jsonObject.put("type", "12");
                        jsonObject.put("orderType", "dd");
                        jgPush.setMsg(jsonObject.toString());

                        pushFeign.pushMsg(jgPush.getType(), jgPush.getUsername(), jgPush.getMsgSeq(), jgPush.getBusinessId(), jgPush.getMsg());
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }else {
                tip = new ErrorTip(500,"预订失败");
            }

        }

        return tip;
    }

    /**
     * 获取vip
     * @param businessId
     * @param vipPhone
     * @return
     */
    public Vip getVipInfo(Integer businessId,String vipPhone){
        return vipService.selectOne(new EntityWrapper<Vip>().eq("business_id",businessId).eq("vip_phone",vipPhone));
    }

    /**
     * 获取序列
     *
     * @param type 类型
     * @return 序列
     */
    private long getNextDateId(String type) {
        String todayStr = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMdd");//历史遗留Key暂不考虑，一天也就一个。
        long l2 = redisTemplate.opsForValue().increment("PUSH:" + type + ":" + todayStr, 1);
        String s1 = StringUtils.leftPad("" + l2, 7, "0");
        return Long.parseLong(todayStr + s1);
    }
}
