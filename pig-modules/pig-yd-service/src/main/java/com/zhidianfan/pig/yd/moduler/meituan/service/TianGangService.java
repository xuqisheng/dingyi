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
import com.zhidianfan.pig.yd.moduler.meituan.constant.TianGangMethod;
import com.zhidianfan.pig.yd.moduler.meituan.dto.TGOrderCanCelDTO;
import com.zhidianfan.pig.yd.moduler.meituan.dto.TGOrderCreateDTO;
import com.zhidianfan.pig.yd.moduler.meituan.dto.TGOrderSubmitDTO;
import com.zhidianfan.pig.yd.moduler.meituan.dto.TGOrderUpdateDTO;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

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

    @Autowired
    private RestTemplate restTemplate;

    private String accessToken = "";

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

    /**
     * 获取天港token
     */
    public boolean getToken(){

        boolean getToken = false;

        accessToken = "";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Basic YWxsLW1hbi1zYWxlOnNlY3JldA==");
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("username", TianGangMethod.TIANGANG_USERNAME);
        param.add("password", TianGangMethod.TIANGANG_PASSWORD);
        param.add("grant_type", "password");
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(param, headers);

        String url = TianGangMethod.GET_TOKEN_URL;

        try {

            ResponseEntity<String> entity = restTemplate.postForEntity(url, httpEntity, String.class);
            JSONObject jsonObject = JSONObject.parseObject(entity.getBody(), JSONObject.class);

            if (entity.getStatusCodeValue() == 200) {
                accessToken =  String.valueOf(jsonObject.get("access_token"));
                getToken = true;
            }

        }catch (Exception e){

            getToken = false;

        }

        return getToken;
    }

    /**
     * 健康检查
     */
    public boolean ApiHealthCheck(){

        boolean isHealth = false;

        String url = TianGangMethod.HEALTH_URL + "?access_token=" + accessToken;

        try {

            ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);

            if (entity.getStatusCodeValue() != 200) {
                isHealth = getToken();
            }else {
                isHealth = true;
            }

        }catch (Exception e){
            isHealth = getToken();
        }

        return isHealth;
    }


    /**
     * 新建订单--天港
     * @param tgOrderCreateDTO
     */
    public void createTianGangOrder(TGOrderCreateDTO tgOrderCreateDTO){

        //连接是否健康
        if(ApiHealthCheck()){

            Map<String, Object> params = JsonUtils.object2Map(tgOrderCreateDTO);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity httpEntity = new HttpEntity(params,headers);

            String url = TianGangMethod.CREATE_ORDER_URL + "?access_token=" + accessToken;

            try {

                ResponseEntity<String> entity = restTemplate.postForEntity(url, httpEntity, String.class);

            }catch (Exception e){

                e.printStackTrace();

            }

        }

    }

    /**
     * 修改订单--天港
     * @param tgOrderUpdateDTO
     */
    public void updateTianGangOrder(TGOrderUpdateDTO tgOrderUpdateDTO){

        //连接是否健康
        if(ApiHealthCheck()){

            Map<String, Object> params = JsonUtils.object2Map(tgOrderUpdateDTO);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity httpEntity = new HttpEntity(params,headers);

            String url = TianGangMethod.UPDATE_ORDER_URL + "?access_token=" + accessToken;

            try {

                ResponseEntity<String> entity = restTemplate.postForEntity(url, httpEntity, String.class);

            }catch (Exception e){

                e.printStackTrace();

            }

        }

    }


    /**
     * 取消订单--天港
     * @param tgOrderCanCelDTO
     */
    public void cancelTianGangOrder(TGOrderCanCelDTO tgOrderCanCelDTO){

        //连接是否健康
        if(ApiHealthCheck()){

            Map<String, Object> params = JsonUtils.object2Map(tgOrderCanCelDTO);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity httpEntity = new HttpEntity(params,headers);

            String url = TianGangMethod.CANCEL_ORDER_URL + "?access_token=" + accessToken;

            try {

                ResponseEntity<String> entity = restTemplate.postForEntity(url, httpEntity, String.class);

            }catch (Exception e){

                e.printStackTrace();

            }

        }

    }

    /**
     * 结算订单--天港
     * @param tgOrderSubmitDTO
     */
    public void submitTianGangOrder(TGOrderSubmitDTO tgOrderSubmitDTO){

        //连接是否健康
        if(ApiHealthCheck()){

            Map<String, Object> params = JsonUtils.object2Map(tgOrderSubmitDTO);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity httpEntity = new HttpEntity(params,headers);

            String url = TianGangMethod.SUBMIT_ORDER_URL + "?access_token=" + accessToken;

            try {

                ResponseEntity<String> entity = restTemplate.postForEntity(url, httpEntity, String.class);

            }catch (Exception e){

                e.printStackTrace();

            }

        }

    }

}
