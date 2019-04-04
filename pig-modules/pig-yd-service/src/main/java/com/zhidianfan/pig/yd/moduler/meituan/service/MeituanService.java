package com.zhidianfan.pig.yd.moduler.meituan.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.common.service.*;
import com.zhidianfan.pig.yd.moduler.meituan.bo.OrderQueryBO;
import com.zhidianfan.pig.yd.moduler.meituan.constant.MeituanMethod;
import com.zhidianfan.pig.yd.moduler.meituan.dto.BasicDTO;
import com.zhidianfan.pig.yd.moduler.meituan.service.rmi.PushFeign;
import com.zhidianfan.pig.yd.moduler.meituan.service.rmi.dto.JgPush;
import com.zhidianfan.pig.yd.moduler.sms.service.rmi.SmsFeign;
import com.zhidianfan.pig.yd.moduler.sms.service.rmi.dto.SmsSendResDTO;
import com.zhidianfan.pig.yd.utils.SignUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;


/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/9/03
 * @Modified By:
 */
@Service
public class MeituanService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ITableService tableService;

    @Autowired
    private IMealTypeService mealTypeService;

    @Autowired
    private IResvOrderService iResvOrderService;
    @Autowired
    private IResvOrderAndroidService iResvOrderAndroidService;

    @Autowired
    private IBusinessService businessService;

    @Autowired
    private IResvOrderThirdService resvOrderThirdService;

    @Autowired
    private PushFeign pushFeign;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SmsFeign smsFeign;


    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 美团桌位同步接口
     *
     * @param businessId
     * @return BasicBO
     */
    public Tip tableSync(Integer businessId) {
        Tip tip = null;
        Business business = businessService.selectOne(new EntityWrapper<Business>().eq("id", businessId));
        if ("1".equals(String.valueOf(business.getIsMeituan()))) {
            List<Table> tables = tableService.selectList(new EntityWrapper<Table>().eq("business_id", businessId).eq("status", 1));
            JSONArray tableArray = new JSONArray();
            for (Table table : tables) {
                JSONObject tableObject = new JSONObject();
                tableObject.put("tableId", table.getId().toString());
                tableObject.put("tableName", table.getTableName());
                tableObject.put("tableType", table.gettType() == 0 ? 1 : 0);
                tableObject.put("isOnlineSale", 0);
                tableObject.put("maxPeople", Integer.parseInt(table.getMaxPeopleNum()));
                tableArray.add(tableObject);
            }
            BasicDTO basicDTO = new BasicDTO();
            basicDTO.setAppAuthToken(business.getAppToken());
            basicDTO.setUrl(MeituanMethod.TABLE_SERVER_URL);
            if (tables.size() > 0) {
                basicDTO.setData(tableArray);
            } else {
                basicDTO.setData(null);
            }
            tip = restTemplatePost(basicDTO);
        } else {
            tip = new SuccessTip(4001, "改酒店未开通新美大接口");
        }
        return tip;
    }

    /**
     * 美团桌位删除
     *
     * @param businessId
     * @param tableId
     * @return BasicBO
     */
    public Tip tableDelete(Integer businessId, Integer tableId) {
        Tip tip = null;
        Business business = businessService.selectOne(new EntityWrapper<Business>().eq("id", businessId));
        if ("1".equals(String.valueOf(business.getIsMeituan()))) {
            Map<String, Object> params = new HashMap<>();
            params.put("appAuthToken", business.getAppToken());
            params.put("charset", MeituanMethod.CHARSET);
            params.put("timestamp", String.valueOf(Instant.now().getEpochSecond()));
            params.put("tableId", tableId.toString());
            String sign = createMeituanSgin(params);
            BasicDTO basicDTO = new BasicDTO();
            basicDTO.setAppAuthToken(business.getAppToken());
            basicDTO.setUrl(MeituanMethod.TABLE_DELETE_SERVER_URL + "?" + "appAuthToken=" + params.get("appAuthToken") + "&charset=" + params.get("charset") + "&timestamp=" + params.get("timestamp") + "&sign=" + sign + "&tableId=" + params.get("tableId"));
            tip = restTemplateGet(basicDTO);
        } else {
            tip = new SuccessTip(4001, "改酒店未开通新美大接口");
        }
        return tip;
    }

    /**
     * 美团餐别同步接口
     *
     * @param businessId
     * @return BasicBO
     */
    public Tip mealSync(Integer businessId) {
        Tip tip = null;
        Business business = businessService.selectOne(new EntityWrapper<Business>().eq("id", businessId));
        if ("1".equals(String.valueOf(business.getIsMeituan()))) {
            List<MealType> meals = mealTypeService.selectList(new EntityWrapper<MealType>().eq("business_id", businessId).eq("status", "1"));
            List<Map<String, Integer>> list = new ArrayList();
            for (MealType meal : meals) {
                Map<String, Integer> params = new HashMap<>();
                params.put("type", meal.getConfigId());
                params.put("startHour", Integer.parseInt(meal.getResvStartTime().substring(0, 2).replace("0", "")));
                params.put("startMinute", Integer.parseInt(meal.getResvStartTime().substring(3, 5).replace("00", "0")));
                params.put("endHour", Integer.parseInt(meal.getResvEndTime().substring(0, 2).replace("0", "")));
                params.put("endMinute", Integer.parseInt(meal.getResvEndTime().substring(3, 5).replace("00", "0")));
                list.add(params);
            }
            JSONArray data = new JSONArray();
            JSONObject object = new JSONObject();
            object.put("weeks", "[1,2,3,4,5,6,7]");
            object.put("diningTime", list);
            data.add(object);
            BasicDTO basicDTO = new BasicDTO();
            basicDTO.setAppAuthToken(business.getAppToken());
            basicDTO.setUrl(MeituanMethod.MEAL_SERVER_URL);
            basicDTO.setData(data);
            tip = restTemplatePost(basicDTO);
        } else {
            tip = new SuccessTip(4001, "该酒店未开通新美大接口");
        }
        return tip;
    }

    /**
     * 新门店后台美团餐别同步接口
     *
     * @param businessId
     * @return BasicBO
     */
    public Tip newMealSync(Integer businessId) {
        Tip tip;
        Business business = businessService.selectOne(new EntityWrapper<Business>().eq("id", businessId));
        if ("1".equals(String.valueOf(business.getIsMeituan()))) {

            List<MealType> meals = mealTypeService.selectList(new EntityWrapper<MealType>()
                    .eq("business_id", businessId)
                    .eq("status", "1")
                    .eq("isnew", "1"));

            List<Map<String, Integer>> list = new ArrayList();

            for (MealType meal : meals) {
                Map<String, Integer> params = new HashMap<>();
                params.put("type", meal.getConfigId());

                if (meal.getConfigId() == 13) {

                    int startHour = Integer.parseInt(meal.getResvStartTime().substring(0, 2));
                    int startMinute = Integer.parseInt(meal.getResvStartTime().substring(3, 5));
                    //如果夜宵开始时间到了第二天六点之前
                    if (6 > startHour) {
                        startHour = 23;
                        startMinute = 30;
                    }
                    params.put("startHour", startHour);
                    params.put("startMinute", startMinute);

                    int endHour = Integer.parseInt(meal.getResvEndTime().substring(0, 2));
                    int endMinute = Integer.parseInt(meal.getResvEndTime().substring(3, 5));
                    //如果到了第二天六点之前
                    if (6 > endHour) {
                        endHour = 23;
                        endMinute = 59;
                    }
                    params.put("endHour", endHour);
                    params.put("endMinute", endMinute);

                } else {
                    params.put("startHour", Integer.parseInt(meal.getResvStartTime().substring(0, 2)));
                    params.put("startMinute", Integer.parseInt(meal.getResvStartTime().substring(3, 5)));


                    params.put("endHour", Integer.parseInt(meal.getResvEndTime().substring(0, 2)));
                    params.put("endMinute", Integer.parseInt(meal.getResvEndTime().substring(3, 5)));

                }
                list.add(params);
            }
            JSONArray data = new JSONArray();
            JSONObject object = new JSONObject();
            object.put("weeks", "[1,2,3,4,5,6,7]");
            object.put("diningTime", list);
            data.add(object);
            BasicDTO basicDTO = new BasicDTO();
            basicDTO.setAppAuthToken(business.getAppToken());
            basicDTO.setUrl(MeituanMethod.MEAL_SERVER_URL);
            basicDTO.setData(data);
            tip = restTemplatePost(basicDTO);
        } else {
            tip = new SuccessTip(4001, "该酒店未开通新美大接口");
        }
        return tip;
    }


    /**
     * 美团桌位状态变更
     *
     * @param businessId
     * @param resvOrder
     * @return
     */
    public Tip tableStatusUpdate(Integer businessId, String resvOrder) throws Exception {
        Tip tip;
        Business business = businessService.selectOne(new EntityWrapper<Business>().eq("id", businessId));
        //todo 针对安卓电话机进行了修改
        ResvOrderAndroid resvOrder1 = new ResvOrderAndroid();

        ResvOrderAndroid resvOrderAndroid = iResvOrderAndroidService.selectOne(new EntityWrapper<ResvOrderAndroid>().eq("resv_order", resvOrder)
                .eq("business_id", businessId));
        if (resvOrderAndroid == null) {
            ResvOrder resvOrder2 = iResvOrderService.selectOne(new EntityWrapper<ResvOrder>().eq("resv_order", resvOrder)
                    .eq("business_id", businessId));
            BeanUtils.copyProperties(resvOrder2, resvOrder1);
        } else {
            resvOrder1 = resvOrderAndroid;
        }

        MealType mealType = mealTypeService.selectOne(new EntityWrapper<MealType>().eq("id", resvOrder1.getMealTypeId()));
        JSONArray data = new JSONArray();
        JSONObject object = new JSONObject();
        object.put("tableId", resvOrder1.getTableAreaId());
        object.put("orderTime", Integer.valueOf(String.valueOf(resvOrder1.getResvDate().getTime() / 1000)));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startTime = sdf.format(resvOrder1.getResvDate()) + " " + mealType.getResvStartTime() + ":00";
        Date date1 = format.parse(startTime);
        String endTime = sdf.format(resvOrder1.getResvDate()) + " " + mealType.getResvEndTime() + ":00";
        Date date2 = format.parse(endTime);
        object.put("startTime", Integer.valueOf(String.valueOf(date1.getTime() / 1000)));
        object.put("endTime", Integer.valueOf(String.valueOf(date2.getTime() / 1000)));
        if ("1".equals(resvOrder1.getStatus()) || "2".equals(resvOrder1.getStatus()) || "5".equals(resvOrder1.getStatus())) {
            object.put("status", 0);
        } else {
            object.put("status", 1);
        }
        object.put("timePeriod", mealType.getConfigId());
        data.add(object);
        BasicDTO basicDTO = new BasicDTO();
        basicDTO.setAppAuthToken(business.getAppToken());
        basicDTO.setUrl(MeituanMethod.TABLE_UPDATE_SERVER_URL);
        basicDTO.setData(data);
        tip = restTemplatePost(basicDTO);
        return tip;
    }

    /**
     * 美团订单更新接口
     *
     * @param businessId
     * @param batchNo
     * @param resvType
     * @return BasicBO
     */
    public Tip orderUpdate(Integer businessId, String batchNo, Integer resvType, String orderSerializedId) {
        try {
            Tip tip = new SuccessTip();
            Business business = businessService.selectOne(new EntityWrapper<Business>().eq("id", businessId));

            List<ResvOrderAndroid> orders = iResvOrderAndroidService.selectList(new EntityWrapper<ResvOrderAndroid>().eq("business_id", businessId).eq("batch_no", batchNo));
            //如果在安卓电话机订单表查询不到,那么查询订单表
            if (orders.size() == 0) {
                List<ResvOrder> resvOrders = iResvOrderService.selectList(new EntityWrapper<ResvOrder>().eq("business_id", businessId).eq("batch_no", batchNo));
                for (ResvOrder resvOrder : resvOrders) {
                    ResvOrderAndroid resvOrderAndroid = new ResvOrderAndroid();
                    BeanUtils.copyProperties(resvOrder, resvOrderAndroid);
                    orders.add(resvOrderAndroid);
                }
            }


            BasicDTO basicDTO = new BasicDTO();
            basicDTO.setAppAuthToken(business.getAppToken());
            basicDTO.setUrl(MeituanMethod.ORDER_UPDATE_SERVER_URL);
            JSONObject data = new JSONObject();
            data.put("orderSerializedId", orderSerializedId);
            //1为接单 2为拒单 3为用户到店 4为用户未到店
            ResvOrderThird resvOrderThird = new ResvOrderThird();
            if ("1".equals(String.valueOf(resvType))) {
                String tableId = "1";
                String tableName = "1";
                Integer startTime1 = 0;
                Integer endTime1 = 0;
                if (orders.size() > 0) {
                    tableId = "";
                    tableName = "";
                    MealType mealType = mealTypeService.selectOne(new EntityWrapper<MealType>().eq("id", orders.get(0).getMealTypeId()));
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String startTime = sdf.format(orders.get(0).getResvDate()) + " " + mealType.getResvStartTime() + ":00";
                    Date date1 = format.parse(startTime);
                    String endTime = sdf.format(orders.get(0).getResvDate()) + " " + mealType.getResvEndTime() + ":00";
                    Date date2 = format.parse(endTime);
                    for (ResvOrderAndroid order : orders) {
                        tableId += order.getTableId() + ",";
                        tableName += order.getTableName() + ",";
                    }
                    tableId = tableId.length() == 0 ? "" : tableId.substring(0, tableId.length() - 1);
                    tableName = tableName.length() == 0 ? "" : tableName.substring(0, tableName.length() - 1);
                    startTime1 = Integer.valueOf(String.valueOf(date1.getTime() / 1000));
                    endTime1 = Integer.valueOf(String.valueOf(date2.getTime() / 1000));
                }
                resvOrderThird.setStatus(40);
                resvOrderThird.setResult(1);
                resvOrderThird.setBatchNo(batchNo);
                resvOrderThird.setFlag(1);
                data.put("operationType", 1);
                data.put("tableId", tableId);
                data.put("tableName", tableName);
                data.put("startTime", startTime1);
                data.put("endTime", endTime1);
            } else if ("2".equals(String.valueOf(resvType))) {
                resvOrderThird.setStatus(30);
                resvOrderThird.setResult(2);
                resvOrderThird.setFlag(1);
                data.put("operationType", 2);
                if ("KB".equals(orderSerializedId.substring(0, 2))) {
                    log.info("口碑预订拒单发送短信");
                    ResvOrderThird resvOrderThird1 = resvOrderThirdService.selectOne(new EntityWrapper<ResvOrderThird>().eq("third_order_no",orderSerializedId));
                    if(resvOrderThird1 != null){
                        Business business1 = businessService.selectById(resvOrderThird1.getBusinessId());
                        if(business1 != null){
                            String msg = "非常抱歉，您预订的" + business1.getBusinessName() + resvOrderThird1.getResvDate() + "已订满。期待您的下次预订。";
                            log.info("短信发送内容:{},{}",resvOrderThird1.getVipPhone(),msg);
                            SmsSendResDTO smsSendResDTO = smsFeign.sendNormalMsg(resvOrderThird1.getVipPhone(), msg);
                            log.info("短信发送结果:{}",smsSendResDTO.toString());
                        }
                    }
                }
            } else if ("3".equals(String.valueOf(resvType))) {
                resvOrderThird.setStatus(50);
                resvOrderThird.setResult(3);
                resvOrderThird.setFlag(1);
                data.put("operationType", 3);
                data.put("consume", "");
            } else if ("4".equals(String.valueOf(resvType))) {
                resvOrderThird.setStatus(60);
                resvOrderThird.setResult(4);
                resvOrderThird.setFlag(1);
                data.put("operationType", 4);
            }
            basicDTO.setData(data);
            if (!"KB".equals(orderSerializedId.substring(0, 2))) {
                tip = restTemplatePost(basicDTO);
            }
            if (tip.getCode() == 200) {
                boolean updateStatus = resvOrderThirdService.update(resvOrderThird, new EntityWrapper<ResvOrderThird>().eq("third_order_no", orderSerializedId));
                if (updateStatus) {
                    tip = new SuccessTip(200, "成功");
                } else {
                    tip = new SuccessTip(4001, "失败");
                }
            }
            JgPush jgPush = new JgPush();
            jgPush.setBusinessId(String.valueOf(businessId));
            jgPush.setMsgSeq(String.valueOf(getNextDateId("MT_ORDER")));
            jgPush.setType("PAD");
            jgPush.setUsername("13777575146");
//            String orderMsg1 = JSONUtils.toJSONString(object2Map(resvOrderThird));
            String orderMsg = JsonUtils.obj2Json(resvOrderThird).replaceAll("\r|\n", "").replaceAll("\\s*", "");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", orderMsg);
            jsonObject.put("type", "8");
            jgPush.setMsg(jsonObject.toString());
            try {
                pushFeign.pushMsg(jgPush.getType(), jgPush.getUsername(), jgPush.getMsgSeq(), jgPush.getBusinessId(), jgPush.getMsg());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return tip;
        } catch (Exception e) {
            return ErrorTip.ERROR_TIP;
        }
    }


    /**
     * 美团订单查询接口
     *
     * @param businessId
     * @param batchNo
     * @return BasicBO
     */
    public OrderQueryBO orderQuery(Integer businessId, String batchNo, String orderSerializedId) {
        Tip tip = null;
        Business business = businessService.selectOne(new EntityWrapper<Business>().eq("id", businessId));
        Map<String, Object> params = new HashMap<>();
        params.put("appAuthToken", business.getAppToken());
        params.put("charset", MeituanMethod.CHARSET);
        params.put("timestamp", String.valueOf(Instant.now().getEpochSecond()));
        params.put("orderSerializedId", orderSerializedId);
        String sign = createMeituanSgin(params);
        BasicDTO basicDTO = new BasicDTO();
        basicDTO.setUrl(MeituanMethod.ORDER_QUERY_SERVER_URL + "?" + "appAuthToken=" + params.get("appAuthToken") + "&charset=" + params.get("charset") + "&timestamp=" + params.get("timestamp") + "&sign=" + sign + "&orderSerializedId=" + params.get("orderSerializedId"));
        tip = restTemplateGet(basicDTO);
        JSONObject jsonObject = JSONObject.parseObject(tip.getMsg(), JSONObject.class);
        OrderQueryBO orderQueryBO = new OrderQueryBO();
        if (jsonObject != null) {
            orderQueryBO.setCode(200);
            orderQueryBO.setData(jsonObject);
            orderQueryBO.setError(null);
        } else {
            orderQueryBO.setCode(30001);
            orderQueryBO.setData(null);
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("message", "订单不存在");
            orderQueryBO.setError(jsonObject1);
        }
        return orderQueryBO;
    }


    /**
     * 美团订单未选座提前接单
     *
     * @param businessId
     * @return BasicBO
     */
    public Tip earlyOrderUpdate(Integer businessId , String orderSerializedId) {

        try {
            Tip tip = new SuccessTip();

            Business business = businessService.selectOne(new EntityWrapper<Business>().eq("id", businessId));

            BasicDTO basicDTO = new BasicDTO();
            basicDTO.setAppAuthToken(business.getAppToken());
            basicDTO.setUrl(MeituanMethod.ORDER_UPDATE_SERVER_URL);
            JSONObject data = new JSONObject();
            data.put("orderSerializedId", orderSerializedId);
            String tableId = "1";
            String tableName = "预定桌";
            Long startTime1 = System.currentTimeMillis()/1000;
            //结束时间加上三小时
            Long endTime1 = (System.currentTimeMillis()/1000+10800);
            data.put("operationType", 1);
            data.put("tableId", tableId);
            data.put("tableName", tableName);
            data.put("startTime", startTime1);
            data.put("endTime", endTime1);
            basicDTO.setData(data);
            if (!"KB".equals(orderSerializedId.substring(0, 2))) {
                tip = restTemplatePost(basicDTO);
            }
            ResvOrderThird resvOrderThird = new ResvOrderThird();
            resvOrderThird.setStatus(45);
            resvOrderThirdService.update(resvOrderThird,new EntityWrapper<ResvOrderThird>().eq("third_order_no",orderSerializedId));


            return tip;
        } catch (Exception e) {
            return ErrorTip.ERROR_TIP;
        }
    }

    /**
     * 获取appAuthToken
     *
     * @return BasicBO
     */
    public String getAppAuthToken(Integer businessId) {
        Map<String, Object> params = new HashMap<>();
        params.put("developerId", MeituanMethod.DEVELOPERID);
        params.put("businessId", 7);
        params.put("timestamp", String.valueOf(Instant.now().toEpochMilli()));
        params.put("ePoiId", businessId);
//        params.put("ePoiName", "测试门店");
        String sign = createMeituanSgin(params);
        BasicDTO basicDTO = new BasicDTO();
        basicDTO.setUrl(MeituanMethod.APP_AUTH_TOKEN + "?" + "developerId=" + params.get("developerId") + "&businessId=" + params.get("businessId") + "&ePoiId=" + params.get("ePoiId") + "&timestamp=" + params.get("timestamp") + "&sign=" + sign);
        log.warn(basicDTO.getUrl());
        return basicDTO.getUrl();
    }

    /**
     * 获取解绑url
     *
     * @return BasicBO
     */
    public String getAppUnAuthTokenUrl(Integer businessId) {
        Business business = businessService.selectById(businessId);
        if (business != null) {
            BasicDTO basicDTO = new BasicDTO();
            basicDTO.setUrl(MeituanMethod.UN_APP_AUTH_TOKEN + "?" + "signKey=" + MeituanMethod.SIGNKEY + "&businessId=7" + "&appAuthToken=" + business.getAppToken());
            log.warn(basicDTO.getUrl());
            return basicDTO.getUrl();
        } else {
            return "";
        }
    }

    /**
     * restTemplate post
     *
     * @param basicDTO
     * @return ResponseEntity<String>
     */
    public Tip restTemplatePost(BasicDTO basicDTO) {
        Tip tip = null;
        LinkedMultiValueMap map = new LinkedMultiValueMap();
        map.set("appAuthToken", basicDTO.getAppAuthToken());
        map.set("charset", MeituanMethod.CHARSET);
        map.set("timestamp", String.valueOf(Instant.now().getEpochSecond()));
        map.set("data", basicDTO.getData().toJSONString());
        Map<String, Object> params = new HashMap<>();
        params.put("appAuthToken", basicDTO.getAppAuthToken());
        params.put("charset", MeituanMethod.CHARSET);
        params.put("timestamp", String.valueOf(Instant.now().getEpochSecond()));
        params.put("data", basicDTO.getData().toJSONString());
        map.set("sign", createMeituanSgin(params));
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType(MeituanMethod.CONTENTTYPE);
        headers.setContentType(type);
        HttpEntity<LinkedMultiValueMap> httpEntity = new HttpEntity<>(map, headers);
        try {
            ResponseEntity<String> entity = restTemplate.postForEntity(basicDTO.getUrl(), httpEntity, String.class);
            JSONObject jsonObject = JSONObject.parseObject(entity.getBody(), JSONObject.class);
            if (entity.getStatusCodeValue() != 200) {
                tip = new ErrorTip(entity.getStatusCodeValue(), String.valueOf(jsonObject.get("error")));
            } else {
                if ("OK".equals(String.valueOf(jsonObject.get("data")).toUpperCase())) {
                    tip = new SuccessTip(entity.getStatusCodeValue(), String.valueOf(jsonObject.get("data")));
                } else {
                    tip = new SuccessTip(Integer.parseInt(String.valueOf(JSONObject.parseObject(String.valueOf(jsonObject.get("error")), JSONObject.class).get("code"))), String.valueOf(JSONObject.parseObject(String.valueOf(jsonObject.get("error")), JSONObject.class).get("message")));
                }
            }
            return tip;
        } catch (Exception e) {
            e.printStackTrace();
            return ErrorTip.ERROR_TIP;
        }
    }

    /**
     * restTemplate get
     *
     * @param basicDTO
     * @return ResponseEntity<String>
     */
    public Tip restTemplateGet(BasicDTO basicDTO) {
        Tip tip = null;
        ResponseEntity<String> entity = restTemplate.getForEntity(basicDTO.getUrl(), String.class);
        JSONObject jsonObject = JSONObject.parseObject(entity.getBody(), JSONObject.class);
        if (entity.getStatusCodeValue() != 200) {
            tip = new ErrorTip(entity.getStatusCodeValue(), String.valueOf(jsonObject.get("error")));
        } else {
            if ("OK".equals(String.valueOf(jsonObject.get("data")).toUpperCase())) {
                tip = new SuccessTip(entity.getStatusCodeValue(), String.valueOf(jsonObject.get("data")));
            } else if (jsonObject.get("error") == null) {
                tip = new SuccessTip(entity.getStatusCodeValue(), String.valueOf(jsonObject.get("data")));
            } else {
                tip = new SuccessTip(Integer.parseInt(String.valueOf(JSONObject.parseObject(String.valueOf(jsonObject.get("error")), JSONObject.class).get("code"))), String.valueOf(JSONObject.parseObject(String.valueOf(jsonObject.get("error")), JSONObject.class).get("message")));
            }
        }
        return tip;
    }

    /**
     * 生成美团签名
     *
     * @param params
     * @return String
     */
    private static String createMeituanSgin(Map<String, Object> params) {
        return SignUtil.calcSign(params, MeituanMethod.SIGNKEY);
    }

    /**
     * 获取序列
     *
     * @param type
     * @return
     */
    private long getNextDateId(String type) {
        String todayStr = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMdd");//历史遗留Key暂不考虑，一天也就一个。
        long l2 = redisTemplate.opsForValue().increment("PUSH:" + type + ":" + todayStr, 1);
        String s1 = StringUtils.leftPad("" + l2, 7, "0");
        return Long.parseLong(todayStr + s1);
    }

}
