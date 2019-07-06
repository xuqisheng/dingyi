package com.zhidianfan.pig.yd.moduler.order.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.service.*;
import com.zhidianfan.pig.yd.moduler.order.bo.*;
import com.zhidianfan.pig.yd.moduler.order.constant.XopMethod;
import com.zhidianfan.pig.yd.moduler.order.dto.OrderDTO;
import com.zhidianfan.pig.yd.moduler.order.query.*;
import com.zhidianfan.pig.yd.utils.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 西软业务逻辑处理类
 *
 * @author mikrotik
 */
@Slf4j
@Service
public class XopService {
    /**
     * 桌位区域数据接口
     */
    @Autowired
    private ITableAreaService tableAreaService;
    /**
     * 订餐类别数据接口
     */
    @Autowired
    private IMealTypeService mealTypeService;
    /**
     * 第三方订单数据接口
     */
    @Autowired
    private IResvOrderTemService resvOrderTemService;
    @Autowired
    private IResvMeetingOrderService resvMeetingOrderService;
    /**
     * 西软对接数据接口
     */
    @Autowired
    private IXmsBusinessService xmsBusinessService;
    /**
     * 酒店数据接口
     */
    @Autowired
    private IBusinessService businessService;
    /**
     * 酒店预订员数据接口
     */
    @Autowired
    private IAppUserService appUserService;
    /**
     * 客户数据接口
     */
    @Autowired
    private IVipService vipService;
    /**
     * 桌位数据接口
     */
    @Autowired
    private ITableService tableService;
    /**
     * 订单数据接口
     */
    @Autowired
    private IResvOrderService resvOrderService;
    @Autowired
    private RestTemplate restTemplate;
    /**
     * 西软数据接口查询存储
     */
    private Map<Integer, XmsBusiness> xmsBusinessMap = Maps.newHashMap();
    /**
     * 所有登录的session
     */
    private static Map<String, LoginBO> loginBOMap = Maps.newHashMap();

    /**
     * 获取营业点信息
     *
     * @param businessId 酒店id
     * @return 返回营业点信息
     */
    public PosPcCodeBO posPcCodes(Integer businessId) {
        XmsBusiness xms = getBusinessXms(businessId);
        String session = getSession(xms.getBrandHotelId());
        //生成接口查询对象
        PosPcCodeQueryOption posPcCodeQueryOption = new PosPcCodeQueryOption();
        posPcCodeQueryOption.setSession(session);
        posPcCodeQueryOption.setHotelid(xms.getBrandHotelId());
        HashMap<String, String> map = Maps.newHashMap();
        //酒店id
        map.put("hotelid", xms.getBusinessHotelId());
        //查询所有
        map.put("type", "0");
        //业务参数
        List<Map<String, String>> params = Lists.newArrayList();
        params.add(map);
        posPcCodeQueryOption.setParams(params);
        //生成签名
        String sign = createRequestSign(JsonUtils.obj2JsonObj(posPcCodeQueryOption), XopMethod.SECRET);
        posPcCodeQueryOption.setSign(sign);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<PosPcCodeQueryOption> httpEntity = new HttpEntity<>(posPcCodeQueryOption, headers);
        ResponseEntity<String> entity = null;
        try {
            if (checkTimes()) {
                entity = restTemplate.postForEntity(XopMethod.URL, httpEntity, String.class);
            }
        } catch (RestClientException e) {
            log.info("获取营业点接口调用异常");
            log.error(e.getMessage(), e);
        }
        if (entity == null) {
            PosPcCodeBO posPcCodeBO = new PosPcCodeBO();
            posPcCodeBO.setSuccess(false);
            return posPcCodeBO;
        }
        return JSONObject.parseObject(entity.getBody(), PosPcCodeBO.class);
    }

    /**
     * 获取营业点下所有换桌位
     *
     * @param businessId 酒店id
     * @param pcCode     营业点编号
     * @return 所有桌位信息
     */
    public TablesBO tables(Integer businessId, String pcCode) {
        PosPcCodeBO posPcCodeBO = posPcCodes(businessId);
        if (!posPcCodeBO.isSuccess() || posPcCodeBO.getResults().size() == 0) {
            log.info("区域信息为空");
            return null;
        }
        XmsBusiness xms = getBusinessXms(businessId);
        String session = getSession(xms.getBrandHotelId());
        //生成接口查询对象
        TablesQueryOption tablesQueryOption = new TablesQueryOption();
        tablesQueryOption.setSession(session);
        tablesQueryOption.setHotelid(xms.getBrandHotelId());
        //业务参数
        List<Map<String, String>> params = Lists.newArrayList();
        Map<String, String> map = Maps.newHashMap();
        map.put("hotelid", xms.getBusinessHotelId());
        map.put("type", "a");
        map.put("pccode", pcCode);
        params.add(map);
        tablesQueryOption.setParams(params);
        String sign = createRequestSign(JsonUtils.obj2JsonObj(tablesQueryOption), XopMethod.SECRET);
        tablesQueryOption.setSign(sign);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<TablesQueryOption> httpEntity = new HttpEntity<>(tablesQueryOption, headers);
        ResponseEntity<String> entity = null;
        try {
            if (checkTimes()) {
                entity = restTemplate.postForEntity(XopMethod.URL, httpEntity, String.class);
            }
        } catch (RestClientException e) {
            log.info("调用获取桌位接口异常");
            log.error(e.getMessage(), e);
        }
        if (entity == null) {
            TablesBO tablesBO = new TablesBO();
            tablesBO.setSuccess(false);
            return tablesBO;
        }
        return JSONObject.parseObject(entity.getBody(), TablesBO.class);
    }

    /**
     * 获取桌位状态
     *
     * @param businessId 酒店id
     * @return 获取桌位的状态
     */
    public TableStatusBO tableStatus(Integer businessId) {
        PosPcCodeBO posPcCodeBO = posPcCodes(businessId);
        if (!posPcCodeBO.isSuccess() || posPcCodeBO.getResults().size() == 0) {
            log.info("区域信息为空");
            return null;
        }
        String pcCode = posPcCodeBO.getResults().get(0).get("pccode");
        XmsBusiness xms = getBusinessXms(businessId);
        String session = getSession(xms.getBrandHotelId());
        return getTableStatus(session, xms, pcCode);
    }

    public boolean checkTable(Integer businessId, String tableCode) {
        Table table = tableService.selectOne(new EntityWrapper<Table>().eq("table_code", tableCode).eq("business_id", businessId));
        if (table == null) {
            throw new RuntimeException("桌位不存在");
        }
        TableArea tableArea = tableAreaService.selectById(table.getTableAreaId());
        if (tableArea == null) {
            throw new RuntimeException("桌位区域不存在");
        }
        if(tableArea.getPccode() == null){
            throw new RuntimeException(" pcCode 为空");
        }
        XmsBusiness xms = getBusinessXms(businessId);
        String session = getSession(xms.getBrandHotelId());
        TableStatusBO tableStatus = getTableStatus(session, xms, tableArea.getPccode());
        AtomicBoolean tag = new AtomicBoolean(false);
        if (tableStatus.isSuccess() && tableStatus.getResults() != null && tableStatus.getResults().size() > 0) {
            tableStatus.getResults().forEach(result -> {
                if (StringUtils.equals(result.get("tableno"), tableCode) && StringUtils.equalsAny(result.get("status"), "0", "1")) {
                    tag.set(true);
                }
            });

        }
        return tag.get();
    }

    /**
     * 获取订餐类别
     *
     * @param businessId 酒店id
     * @return 获取订餐列表
     */
    public PosTagBo posTag(Integer businessId) {
        XmsBusiness xms = getBusinessXms(businessId);
        String session = getSession(xms.getBrandHotelId());
        PosTagQueryOption posTagQueryOption = new PosTagQueryOption();
        posTagQueryOption.setSession(session);
        posTagQueryOption.setHotelid(xms.getBrandHotelId());
        List<Map<String, String>> params = Lists.newArrayList();
        Map<String, String> map = Maps.newHashMap();
        map.put("hotelid", xms.getBusinessHotelId());
        params.add(map);
        posTagQueryOption.setParams(params);
        String sign = createRequestSign(JsonUtils.obj2JsonObj(posTagQueryOption), XopMethod.SECRET);
        posTagQueryOption.setSign(sign);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<PosTagQueryOption> httpEntity = new HttpEntity<>(posTagQueryOption, headers);
        ResponseEntity<String> entity = null;
        try {
            if (checkTimes()) {
                entity = restTemplate.postForEntity(XopMethod.URL, httpEntity, String.class);
            }
        } catch (RestClientException e) {
            log.info("调用订餐类别接口异常");
            log.error(e.getMessage(), e);
        }
        if (entity == null) {
            PosTagBo posTagBo = new PosTagBo();
            posTagBo.setSuccess(false);
            return posTagBo;
        }
        return JSONObject.parseObject(entity.getBody(), PosTagBo.class);
    }

    public OrderBO orderList(Integer businessId) {
        PosPcCodeBO posPcCodeBO = posPcCodes(businessId);
        if (posPcCodeBO == null || posPcCodeBO.getResults().size() == 0) {
            return null;
        }
        List<String> pcCodes = Lists.newArrayList();
        posPcCodeBO.getResults().forEach(map -> {
            pcCodes.add(map.get("pccode"));
        });
        XmsBusiness xms = getBusinessXms(businessId);
        String session = getSession(xms.getBrandHotelId());
        OrderListQueryOption orderListQueryOption = new OrderListQueryOption();
        orderListQueryOption.setSession(session);
        orderListQueryOption.setHotelid(xms.getBrandHotelId());
        List<Map<String, String>> params = Lists.newArrayList();
        Map<String, String> map = Maps.newHashMap();
        map.put("pccode", StringUtils.join(pcCodes, ","));
        map.put("hotelid", xms.getBusinessHotelId());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date beginTime = calendar.getTime();
        map.put("begindate", DateFormatUtils.format(beginTime, "yyyy-MM-dd HH:mm:ss"));
        map.put("mode", "2");
        map.put("pcid", "PCID");
        params.add(map);
        orderListQueryOption.setParams(params);
        String sign = createRequestSign(JsonUtils.obj2JsonObj(orderListQueryOption), XopMethod.SECRET);
        orderListQueryOption.setSign(sign);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<OrderListQueryOption> httpEntity = new HttpEntity<>(orderListQueryOption, headers);
        ResponseEntity<String> entity = null;
        try {
            if (checkTimes()) {
                entity = restTemplate.postForEntity(XopMethod.URL, httpEntity, String.class);
            }
        } catch (RestClientException e) {
            log.info("调用西软订单列表同步异常");
            log.error(e.getMessage(), e);
        }
        if (entity == null) {
            OrderBO orderBO = new OrderBO();
            orderBO.setSuccess(false);
            return orderBO;
        }
        return JSONObject.parseObject(entity.getBody(), OrderBO.class);
    }

    /**
     * 更新保存订单
     *
     * @param resvOrderTem 第三方订单数据
     */
    public void updateOrSaveOrder(ResvOrderTem resvOrderTem) {
        log.info("订单:{}", resvOrderTem.toString());
        XmsBusiness xms = getBusinessXms(resvOrderTem.getBusinessId());
        String session = getSession(xms.getBrandHotelId());
        UpdateOrSaveOrderQueryOption updateOrSaveOrderQueryOption = new UpdateOrSaveOrderQueryOption();
        updateOrSaveOrderQueryOption.setSession(session);
        updateOrSaveOrderQueryOption.setHotelid(xms.getBrandHotelId());
        List<Map<String, String>> params = Lists.newArrayList();
        Map<String, String> map = Maps.newHashMap();
        map.put("phone", resvOrderTem.getVipPhone());
        MealType mealType = mealTypeService.selectById(resvOrderTem.getMealTypeId());
        String beginTime;
        if (StringUtils.isBlank(mealType.getMealStartTime())) {
            beginTime = "00:00:00";
        } else {
            beginTime = mealType.getMealStartTime() + ":00";
        }
        map.put("begin_time", beginTime);
        map.put("tag", "2");
        String createDate = DateFormatUtils.format(resvOrderTem.getCreatedAt(), "yyyy-MM-dd HH:mm:ss");
        map.put("createdate", createDate);
        String change = DateFormatUtils.format(resvOrderTem.getUpdatedAt() == null ? resvOrderTem.getCreatedAt() : resvOrderTem.getUpdatedAt(), "yyyy-MM-dd HH:mm:ss");
        map.put("changed", change);
        TableArea tableArea = tableAreaService.selectById(resvOrderTem.getTableAreaId());
        map.put("pccode", tableArea.getPccode());
        map.put("hotelid", xms.getBusinessHotelId());
        String resvDate = DateFormatUtils.format(resvOrderTem.getResvDate(), "yyyy-MM-dd") + " " + (StringUtils.isBlank(resvOrderTem.getDestTime()) ? DateFormatUtils.format(new Date(), "HH:mm:ss") : resvOrderTem.getDestTime() + ":00");
        map.put("date0", resvDate);
        map.put("sta", resvOrderTem.getStatus());
        map.put("contacter", resvOrderTem.getVipName());
        map.put("cby", StringUtils.isBlank(resvOrderTem.getAppUserName()) ? "公共" : resvOrderTem.getAppUserName());
        map.put("stdunit", "1");
        map.put("res_name", StringUtils.isBlank(resvOrderTem.getVipName()) ? "散客" : resvOrderTem.getVipName());
        map.put("resby", StringUtils.isBlank(resvOrderTem.getAppUserName()) ? "公共" : resvOrderTem.getAppUserName());
        map.put("orderid", resvOrderTem.getResvOrder());
        map.put("scid", resvOrderTem.getThirdOrderNo());
        map.put("tableno", resvOrderTem.getTableCode());
        if (resvOrderTem.getThirdOrderNo() != null) {
            map.put("resno", resvOrderTem.getThirdOrderNo());
        }
        params.add(map);
        updateOrSaveOrderQueryOption.setParams(params);
        String sign = createRequestSign(JsonUtils.obj2JsonObj(updateOrSaveOrderQueryOption), XopMethod.SECRET);
        updateOrSaveOrderQueryOption.setSign(sign);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<UpdateOrSaveOrderQueryOption> httpEntity = new HttpEntity<>(updateOrSaveOrderQueryOption, headers);
        ResponseEntity<String> entity = null;
        try {
            if (checkTimes()) {
                entity = restTemplate.postForEntity(XopMethod.URL, httpEntity, String.class);
            }
        } catch (RestClientException e) {
            log.info("调用西软更新保存订单异常");
            log.error(e.getMessage(), e);
        }
        if (entity == null) {
            log.info("调用西软更新保存订单null");
            return;
        }
        log.info("西软请求参数:{}", JsonUtils.obj2Json(updateOrSaveOrderQueryOption));
        log.info("调用西软更新保存订单:{}", entity.getBody());
        UpdateOrSaveOrderBO updateOrSaveOrderBO = JSONObject.parseObject(entity.getBody(), UpdateOrSaveOrderBO.class);
        if (updateOrSaveOrderBO != null && updateOrSaveOrderBO.isSuccess()) {
            ResvOrderTem tem = resvOrderTemService.selectById(resvOrderTem.getId());
            tem.setThirdOrderNo(updateOrSaveOrderBO.getResults().get(0).get("resno"));
            tem.setXmsUpdateStatus(1);
            boolean b = resvOrderTemService.updateById(tem);
            log.info("更新订单:{}", b);
        } else {
            log.info("更新订单失败");
        }
    }


    public void updateOrSaveMettingOrder(ResvMeetingOrder resvMeetingOrder) {
        log.info("订单:{}", resvMeetingOrder.toString());
        XmsBusiness xms = getBusinessXms(resvMeetingOrder.getBusinessId());
        String session = getSession(xms.getBrandHotelId());
        UpdateOrSaveOrderQueryOption updateOrSaveOrderQueryOption = new UpdateOrSaveOrderQueryOption();
        updateOrSaveOrderQueryOption.setSession(session);
        updateOrSaveOrderQueryOption.setHotelid(xms.getBrandHotelId());
        List<Map<String, String>> params = Lists.newArrayList();
        Map<String, String> map = Maps.newHashMap();
        map.put("phone", resvMeetingOrder.getVipPhone());
        MealType mealType = mealTypeService.selectById(resvMeetingOrder.getMealTypeId());
        String beginTime;
        if (StringUtils.isBlank(mealType.getMealStartTime())) {
            beginTime = "00:00:00";
        } else {
            beginTime = mealType.getMealStartTime() + ":00";
        }
        map.put("begin_time", beginTime);
        map.put("tag", "2");
        String createDate = DateFormatUtils.format(resvMeetingOrder.getCreatedAt(), "yyyy-MM-dd HH:mm:ss");
        map.put("createdate", createDate);
        String change = DateFormatUtils.format(resvMeetingOrder.getUpdatedAt() == null ? resvMeetingOrder.getCreatedAt() : resvMeetingOrder.getUpdatedAt(), "yyyy-MM-dd HH:mm:ss");
        map.put("changed", change);
        TableArea tableArea = tableAreaService.selectById(resvMeetingOrder.getTableAreaId());
        map.put("pccode", tableArea.getPccode());
        map.put("hotelid", xms.getBusinessHotelId());
        String resvDate = DateFormatUtils.format(resvMeetingOrder.getResvDate(), "yyyy-MM-dd") + " " + beginTime;
        map.put("date0", resvDate);
        map.put("sta", resvMeetingOrder.getStatus());
        map.put("contacter", resvMeetingOrder.getVipName());
        map.put("cby", StringUtils.isBlank(resvMeetingOrder.getAppUserName()) ? "公共" : resvMeetingOrder.getAppUserName());
        map.put("stdunit", "1");
        map.put("res_name", StringUtils.isBlank(resvMeetingOrder.getVipName()) ? "散客" : resvMeetingOrder.getVipName());
        map.put("resby", StringUtils.isBlank(resvMeetingOrder.getAppUserName()) ? "公共" : resvMeetingOrder.getAppUserName());
        map.put("orderid", resvMeetingOrder.getResvOrder());
        map.put("scid", resvMeetingOrder.getThirdOrderNo());
        Table table = tableService.selectById(resvMeetingOrder.getTableId());
        map.put("tableno", table.getTableCode());
        if (resvMeetingOrder.getThirdOrderNo() != null) {
            map.put("resno", resvMeetingOrder.getThirdOrderNo());
        }
        params.add(map);
        updateOrSaveOrderQueryOption.setParams(params);
        String sign = createRequestSign(JsonUtils.obj2JsonObj(updateOrSaveOrderQueryOption), XopMethod.SECRET);
        updateOrSaveOrderQueryOption.setSign(sign);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<UpdateOrSaveOrderQueryOption> httpEntity = new HttpEntity<>(updateOrSaveOrderQueryOption, headers);
        ResponseEntity<String> entity = null;
        log.info(JsonUtils.obj2Json(updateOrSaveOrderQueryOption));
        try {
            if (checkTimes()) {
                entity = restTemplate.postForEntity(XopMethod.URL, httpEntity, String.class);
            }
        } catch (RestClientException e) {
            log.info("调用西软更新保存订单异常");
            log.error(e.getMessage(), e);
        }
        if (entity == null) {
            log.info("调用西软更新保存订单null");
            return;
        }
        log.info("西软请求参数:{}", JsonUtils.obj2Json(updateOrSaveOrderQueryOption));
        log.info("调用西软更新保存订单:{}", entity.getBody());
        UpdateOrSaveOrderBO updateOrSaveOrderBO = JSONObject.parseObject(entity.getBody(), UpdateOrSaveOrderBO.class);
        if (updateOrSaveOrderBO != null && updateOrSaveOrderBO.isSuccess()) {
            resvMeetingOrder.setXmsUpdateStatus(1);
            resvMeetingOrder.setThirdOrderNo(updateOrSaveOrderBO.getResults().get(0).get("resno"));
            boolean b = resvMeetingOrderService.updateById(resvMeetingOrder);
            log.info("更新订单:{}", b);
        } else {
            log.info("更新订单失败");
        }
    }

    /**
     * 取消订单
     *
     * @param resvOrderTem 第三方订单数据
     */
    public void cancelOrder(ResvOrderTem resvOrderTem) {
        XmsBusiness xms = getBusinessXms(resvOrderTem.getBusinessId());
        String session = getSession(xms.getBrandHotelId());
        CancelOrderQueryOption cancelOrderQueryOption = new CancelOrderQueryOption();
        cancelOrderQueryOption.setSession(session);
        cancelOrderQueryOption.setHotelid(xms.getBrandHotelId());
        List<Map<String, String>> params = Lists.newArrayList();
        Map<String, String> map = Maps.newHashMap();
        map.put("empno", "know");
        map.put("resno", resvOrderTem.getThirdOrderNo());
        map.put("hotelid", xms.getBusinessHotelId());
        params.add(map);
        cancelOrderQueryOption.setParams(params);
        String sign = createRequestSign(JsonUtils.obj2JsonObj(cancelOrderQueryOption), XopMethod.SECRET);
        cancelOrderQueryOption.setSign(sign);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<CancelOrderQueryOption> httpEntity = new HttpEntity<>(cancelOrderQueryOption, headers);
        ResponseEntity<String> entity = null;
        try {
            if (checkTimes()) {
                entity = restTemplate.postForEntity(XopMethod.URL, httpEntity, String.class);
            }
        } catch (RestClientException e) {
            log.info("调用西软取消订单异常");
            log.error(e.getMessage(), e);
        }
        if (entity == null) {
            log.info("调用西软取消订单null");
            return;
        }
        CancelOrderBO cancelOrderBO = JSONObject.parseObject(entity.getBody(), CancelOrderBO.class);
        if (cancelOrderBO != null && cancelOrderBO.isSuccess()) {
            ResvOrderTem tem = resvOrderTemService.selectById(resvOrderTem.getId());
            tem.setXmsUpdateStatus(1);
            resvOrderTemService.updateById(tem);
        }
    }



    /**
     * 取消订单
     *
     * @param resvMeetingOrder 宴会订单
     */
    public void cancelMeetingOrder(ResvMeetingOrder resvMeetingOrder) {
        XmsBusiness xms = getBusinessXms(resvMeetingOrder.getBusinessId());
        String session = getSession(xms.getBrandHotelId());
        CancelOrderQueryOption cancelOrderQueryOption = new CancelOrderQueryOption();
        cancelOrderQueryOption.setSession(session);
        cancelOrderQueryOption.setHotelid(xms.getBrandHotelId());
        List<Map<String, String>> params = Lists.newArrayList();
        Map<String, String> map = Maps.newHashMap();
        map.put("empno", "know");
        map.put("resno", resvMeetingOrder.getThirdOrderNo());
        map.put("hotelid", xms.getBusinessHotelId());
        params.add(map);
        cancelOrderQueryOption.setParams(params);
        String sign = createRequestSign(JsonUtils.obj2JsonObj(cancelOrderQueryOption), XopMethod.SECRET);
        cancelOrderQueryOption.setSign(sign);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<CancelOrderQueryOption> httpEntity = new HttpEntity<>(cancelOrderQueryOption, headers);
        ResponseEntity<String> entity = null;
        try {
            if (checkTimes()) {
                entity = restTemplate.postForEntity(XopMethod.URL, httpEntity, String.class);
            }
        } catch (RestClientException e) {
            log.info("调用西软取消订单异常");
            log.error(e.getMessage(), e);
        }
        if (entity == null) {
            log.info("调用西软取消订单null");
            return;
        }
        CancelOrderBO cancelOrderBO = JSONObject.parseObject(entity.getBody(), CancelOrderBO.class);
        if (cancelOrderBO != null && cancelOrderBO.isSuccess()) {
            resvMeetingOrder.setXmsUpdateStatus(1);
            resvMeetingOrderService.updateById(resvMeetingOrder);
        }
    }

    /**
     * 获取菜品大类信息
     *
     * @param businessId 酒店id
     * @return 酒店大类信息
     */
    public PosPluCodesBO plucodes(Integer businessId) {
        XmsBusiness xms = getBusinessXms(businessId);
        PosPluCodesQueryOption posPlucodesQueryOption = new PosPluCodesQueryOption();
        String session = getSession(xms.getBrandHotelId());
        posPlucodesQueryOption.setSession(session);
        posPlucodesQueryOption.setHotelid(xms.getBrandHotelId());
        List<Map<String, String>> params = Lists.newArrayList();
        Map<String, String> map = Maps.newHashMap();
        map.put("hotelid", xms.getBusinessHotelId());
        params.add(map);
        posPlucodesQueryOption.setParams(params);
        String sign = createRequestSign(JsonUtils.obj2JsonObj(posPlucodesQueryOption), XopMethod.SECRET);
        posPlucodesQueryOption.setSign(sign);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<PosPluCodesQueryOption> httpEntity = new HttpEntity<>(posPlucodesQueryOption, headers);
        ResponseEntity<String> entity = null;
        try {
            if (checkTimes()) {
                entity = restTemplate.postForEntity(XopMethod.URL, httpEntity, String.class);
            }
        } catch (RestClientException e) {
            log.info("调用西软获取残品大类异常");
            log.error(e.getMessage(), e);
        }
        if (entity == null) {
            PosPluCodesBO posPluCodesBO = new PosPluCodesBO();
            posPluCodesBO.setSuccess(false);
            return posPluCodesBO;
        }
        return JSONObject.parseObject(entity.getBody(), PosPluCodesBO.class);
    }

    /**
     * 获取菜品小类信息
     *
     * @param businessId 酒店id
     * @return 获取酒店小类信息
     */
    public PosSortsBO posSorts(Integer businessId) {
        XmsBusiness xms = getBusinessXms(businessId);
        PosSortsQueryOption posSortsQueryOption = new PosSortsQueryOption();
        String session = getSession(xms.getBrandHotelId());
        posSortsQueryOption.setSession(session);
        posSortsQueryOption.setHotelid(xms.getBrandHotelId());
        List<Map<String, String>> params = Lists.newArrayList();
        Map<String, String> map = Maps.newHashMap();
        map.put("hotelid", xms.getBusinessHotelId());
        params.add(map);
        posSortsQueryOption.setParams(params);
        String sign = createRequestSign(JsonUtils.obj2JsonObj(posSortsQueryOption), XopMethod.SECRET);
        posSortsQueryOption.setSign(sign);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<PosSortsQueryOption> httpEntity = new HttpEntity<>(posSortsQueryOption, headers);
        ResponseEntity<String> entity = null;
        try {
            if (checkTimes()) {
                entity = restTemplate.postForEntity(XopMethod.URL, httpEntity, String.class);
            }
        } catch (RestClientException e) {
            log.info("调用西软获取菜品小类异常");
            log.error(e.getMessage(), e);
        }
        if (entity == null) {
            PosSortsBO posSortsBO = new PosSortsBO();
            posSortsBO.setSuccess(false);
            return posSortsBO;
        }
        return JSONObject.parseObject(entity.getBody(), PosSortsBO.class);
    }

    /**
     * 获取酒店菜品
     *
     * @param businessId 酒店id
     * @return 菜品信息
     */
    public PosPlusBO posPlus(Integer businessId) {
        XmsBusiness xms = getBusinessXms(businessId);
        PosPlusQueryOption posPlusQueryOption = new PosPlusQueryOption();
        String session = getSession(xms.getBrandHotelId());
        posPlusQueryOption.setSession(session);
        posPlusQueryOption.setHotelid(xms.getBrandHotelId());
        List<Map<String, String>> params = Lists.newArrayList();
        Map<String, String> map = Maps.newHashMap();
        map.put("hotelid", xms.getBusinessHotelId());
        params.add(map);
        posPlusQueryOption.setParams(params);
        String sign = createRequestSign(JsonUtils.obj2JsonObj(posPlusQueryOption), XopMethod.SECRET);
        posPlusQueryOption.setSign(sign);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<PosPlusQueryOption> httpEntity = new HttpEntity<>(posPlusQueryOption, headers);
        ResponseEntity<String> entity = null;
        try {
            if (checkTimes()) {
                entity = restTemplate.postForEntity(XopMethod.URL, httpEntity, String.class);
            }
        } catch (RestClientException e) {
            log.info("调用西软获取残品异常");
            log.error(e.getMessage(), e);
        }
        if (entity == null) {
            PosPlusBO posPlusBO = new PosPlusBO();
            posPlusBO.setSuccess(false);
            return posPlusBO;
        }
        return JSONObject.parseObject(entity.getBody(), PosPlusBO.class);
    }

    /**
     * 菜品价格
     *
     * @param businessId 酒店id
     * @return 菜品价格信息
     */
    public PosPricesBO posPrice(Integer businessId) {
        XmsBusiness xms = getBusinessXms(businessId);
        PosPricesQueryOption posPricesQueryOption = new PosPricesQueryOption();
        String session = getSession(xms.getBrandHotelId());
        posPricesQueryOption.setSession(session);
        posPricesQueryOption.setHotelid(xms.getBrandHotelId());
        List<Map<String, String>> params = Lists.newArrayList();
        Map<String, String> map = Maps.newHashMap();
        map.put("hotelid", xms.getBusinessHotelId());
        params.add(map);
        posPricesQueryOption.setParams(params);
        String sign = createRequestSign(JsonUtils.obj2JsonObj(posPricesQueryOption), XopMethod.SECRET);
        posPricesQueryOption.setSign(sign);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<PosPricesQueryOption> httpEntity = new HttpEntity<>(posPricesQueryOption, headers);
        ResponseEntity<String> entity = null;
        try {
            if (checkTimes()) {
                entity = restTemplate.postForEntity(XopMethod.URL, httpEntity, String.class);
            }
        } catch (RestClientException e) {
            log.info("调用西软菜品价格异常");
            log.error(e.getMessage(), e);
        }
        if (entity == null) {
            PosPricesBO posPricesBO = new PosPricesBO();
            posPricesBO.setSuccess(false);
            return posPricesBO;
        }
        return JSONObject.parseObject(entity.getBody(), PosPricesBO.class);
    }

    /**
     * 获取订单列表
     *
     * @param businessId 酒店id
     * @return 订单列表信息
     */
    public ReserveListBO reserveList(Integer businessId) {
        XmsBusiness xms = getBusinessXms(businessId);
        ReserveListQueryOption reserveListQueryOption = new ReserveListQueryOption();
        String session = getSession(xms.getBrandHotelId());
        reserveListQueryOption.setSession(session);
        reserveListQueryOption.setHotelid(xms.getBrandHotelId());
        List<Map<String, String>> params = Lists.newArrayList();
        Map<String, String> map = Maps.newHashMap();
        map.put("hotelid", xms.getBusinessHotelId());
        PosPcCodeBO posPcCodeBO = posPcCodes(businessId);
        if (!posPcCodeBO.isSuccess() && posPcCodeBO.getResults().size() == 0) {
            return null;
        }
        List<String> pcCodes = Lists.newArrayList();
        posPcCodeBO.getResults().forEach(result -> {
            pcCodes.add(result.get("pccode"));
        });
        map.put("pccode", StringUtils.join(pcCodes, ","));
        map.put("mode", "0");
        map.put("pcid", "PCID");
        params.add(map);
        reserveListQueryOption.setParams(params);
        String sign = createRequestSign(JsonUtils.obj2JsonObj(reserveListQueryOption), XopMethod.SECRET);
        reserveListQueryOption.setSign(sign);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<ReserveListQueryOption> httpEntity = new HttpEntity<>(reserveListQueryOption, headers);
        ResponseEntity<String> entity = null;
        try {
            if (checkTimes()) {
                entity = restTemplate.postForEntity(XopMethod.URL, httpEntity, String.class);
            }
        } catch (RestClientException e) {
            log.info("调用西软订单列表异常");
            log.error(e.getMessage(), e);
        }
        if (entity == null) {
            ReserveListBO reserveListBO = new ReserveListBO();
            reserveListBO.setSuccess(false);
            return reserveListBO;
        }
        return JSONObject.parseObject(entity.getBody(), ReserveListBO.class);
    }

    /**
     * 同步订单信息
     *
     * @param orderDTO 订单查询信息
     * @return 订单同步是否成功
     */
    public boolean asyncOrder(OrderDTO orderDTO) {
        ResvOrder resvOrder = resvOrderService.selectOne(new EntityWrapper<ResvOrder>().eq("third_order_no", orderDTO.getOrderNo()));
        //获取相关酒店信息
        XmsBusiness xmsBusiness = xmsBusinessService.selectOne(new EntityWrapper<XmsBusiness>().eq("business_hotel_id", orderDTO.getBusinessHotelId()));
        //酒店id
        int businessId = xmsBusiness.getBusinessId();
        if (resvOrder == null) {
            String orderNo = IdUtils.makeOrderNo();
            resvOrder = new ResvOrder();
            resvOrder.setCreatedAt(new Date());
            resvOrder.setResvOrder(orderNo);
            resvOrder.setBatchNo("pc" + orderNo);
            resvOrder.setBusinessId(businessId);
            //获取预订员信息
            AppUser appUser = appUserService.selectOne(new EntityWrapper<AppUser>().eq("app_user_id", orderDTO.getAppUserPhone()));
            resvOrder.setAppUserId(appUser.getId());
            resvOrder.setAppUserName(appUser.getAppUserName());
            resvOrder.setAppUserPhone(appUser.getAppUserPhone());
            resvOrder.setStatus("1");
        }
        resvOrder.setUpdatedAt(new Date());
        Business business = businessService.selectById(businessId);
        resvOrder.setBusinessName(business.getBusinessName());

        //客户信息
        Vip vip = vipService.selectOne(new EntityWrapper<Vip>().eq("business_id", businessId).eq("vip_phone", orderDTO.getAppUserPhone()));
        if (vip == null) {
            vip = new Vip();
            vip.setBusinessId(businessId);
            vip.setBusinessName(business.getBusinessName());
            vip.setVipName(orderDTO.getVipName());
            vip.setVipPhone(orderDTO.getPhone());
            vip.setVipSex(orderDTO.getSex());
            vipService.insert(vip);
        }
        resvOrder.setVipId(vip.getId());
        resvOrder.setVipPhone(vip.getVipPhone());
        resvOrder.setVipName(vip.getVipName());
        resvOrder.setVipSex(vip.getVipSex());
        //桌位区域
        TableArea tableArea = tableAreaService.selectOne(new EntityWrapper<TableArea>().eq("business_id", businessId).eq("area_code", orderDTO.getPcCode()));
        resvOrder.setTableAreaId(tableArea.getId());
        resvOrder.setTableAreaName(tableArea.getTableAreaName());
        Table table = tableService.selectOne(new EntityWrapper<Table>().eq("business_id", businessId).eq("table_area_code", orderDTO.getPcCode()).eq("table_code", orderDTO.getTableCode()));
        resvOrder.setTableId(table.getId());
        resvOrder.setTableName(table.getTableName());
        resvOrder.setMaxPeopleNum(table.getMaxPeopleNum());
        resvOrder.setResvNum(orderDTO.getResvNum());
        DateFormat dateFormat = SimpleDateFormat.getTimeInstance();
        try {
            resvOrder.setResvDate(dateFormat.parse(orderDTO.getResvDate()));
        } catch (ParseException e) {
            return false;
        }

        resvOrder.setIstirdparty(1);
        resvOrderService.insertOrUpdate(resvOrder);
        return true;
    }

    /**
     * 同步取消订单
     *
     * @param businessId   酒店id
     * @param thirdOrderNo 第三方订单编号
     * @return 取消是否成功
     */
    public boolean asyncCancelOrder(Integer businessId, String thirdOrderNo) {
        ResvOrder resvOrder = resvOrderService.selectOne(new EntityWrapper<ResvOrder>().eq("business_id", businessId).eq("third_order_no", thirdOrderNo));
        if (resvOrder == null) {
            return false;
        }
        resvOrder.setStatus("4");
        resvOrder.setThirdOrderNo(thirdOrderNo);
        resvOrderService.updateById(resvOrder);
        return true;
    }

    /**
     * 获取餐单信息
     *
     * @param businessId 酒店id
     * @return 餐单信息
     */
    public InfoBO posInfo(Integer businessId) {
        XmsBusiness xms = getBusinessXms(businessId);
        String session = getSession(xms.getBrandHotelId());
        PosInfoQueryOption posInfoQueryOption = new PosInfoQueryOption();
        posInfoQueryOption.setSession(session);
        posInfoQueryOption.setHotelid(xms.getBrandHotelId());
        HashMap<String, String> map = Maps.newHashMap();
        //酒店id
        map.put("hotelid", xms.getBusinessHotelId());
        //业务参数
        List<Map<String, String>> params = Lists.newArrayList();
        String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
        map.put("begintime", date);
        map.put("endtime", date);
        PosPcCodeBO posPcCodeBO = posPcCodes(businessId);
        List<String> pcCodes = Lists.newArrayList();
        posPcCodeBO.getResults().forEach(result -> {
            pcCodes.add(result.get("pccode"));
        });
        if (CollectionUtils.isEmpty(pcCodes)) {
            InfoBO infoBO = new InfoBO();
            infoBO.setSuccess(false);
            return infoBO;
        }
        map.put("pccode", StringUtils.join(pcCodes, ","));
        params.add(map);
        posInfoQueryOption.setParams(params);
        String sign = createRequestSign(JsonUtils.obj2JsonObj(posInfoQueryOption), XopMethod.SECRET);
        posInfoQueryOption.setSign(sign);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<PosInfoQueryOption> httpEntity = new HttpEntity<>(posInfoQueryOption, headers);
        ResponseEntity<String> entity = null;
        try {
            if (checkTimes()) {
                entity = restTemplate.postForEntity(XopMethod.URL, httpEntity, String.class);
            }
        } catch (RestClientException e) {
            log.info("调用西软餐单信息异常");
            log.error(e.getMessage(), e);
        }
        if (entity == null) {
            InfoBO infoBO = new InfoBO();
            infoBO.setSuccess(false);
            return infoBO;
        }
        return JSONObject.parseObject(entity.getBody(), InfoBO.class);
    }

    public PosReserverBO posReserver(Integer businessId, String thirdOrderNo) {
        XmsBusiness xms = getBusinessXms(businessId);
        String session = getSession(xms.getBrandHotelId());
        PosReserverQueryOption posReserverQueryOption = new PosReserverQueryOption();
        posReserverQueryOption.setSession(session);
        posReserverQueryOption.setHotelid(xms.getBrandHotelId());
        List<Map<String, String>> params = Lists.newArrayList();
        Map<String, String> map = Maps.newHashMap();
        map.put("hotelid", xms.getBusinessHotelId());
        map.put("resno", thirdOrderNo);
        params.add(map);
        posReserverQueryOption.setParams(params);
        String sign = createRequestSign(JsonUtils.obj2JsonObj(posReserverQueryOption), XopMethod.SECRET);
        posReserverQueryOption.setSign(sign);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<PosReserverQueryOption> httpEntity = new HttpEntity<>(posReserverQueryOption, headers);
        ResponseEntity<String> entity = null;
        try {
            if (checkTimes()) {
                entity = restTemplate.postForEntity(XopMethod.URL, httpEntity, String.class);
            }
        } catch (Exception e) {
            log.info("调用西软订单同步异常");
            log.error(e.getMessage(), e);
        }
        if (entity == null) {
            PosReserverBO posReserverBO = new PosReserverBO();
            posReserverBO.setSuccess(false);
            return posReserverBO;
        }
        return JSONObject.parseObject(entity.getBody(), PosReserverBO.class);
    }

    public TableMenusBO tableMenus(Integer businessId, String tableCode) {
        XmsBusiness xms = getBusinessXms(businessId);
        String session = getSession(xms.getBrandHotelId());
        TableMenuQueryOption tableMenuQueryOption = new TableMenuQueryOption();
        tableMenuQueryOption.setSession(session);
        tableMenuQueryOption.setHotelid(xms.getBrandHotelId());
        List<Map<String, String>> params = Lists.newArrayList();
        Map<String, String> map = Maps.newHashMap();
        map.put("hotelid", xms.getBusinessHotelId());
        map.put("pcid", "PCID");
        map.put("key", tableCode);
        params.add(map);
        tableMenuQueryOption.setParams(params);
        String sign = createRequestSign(JsonUtils.obj2JsonObj(tableMenuQueryOption), XopMethod.SECRET);
        tableMenuQueryOption.setSign(sign);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<TableMenuQueryOption> httpEntity = new HttpEntity<>(tableMenuQueryOption, headers);
        ResponseEntity<String> entity = null;
        try {
            if (checkTimes()) {
                entity = restTemplate.postForEntity(XopMethod.URL, httpEntity, String.class);
            }
        } catch (Exception e) {
            log.info("调用西软菜单同步异常");
            log.error(e.getMessage(), e);
        }

        if (entity == null) {
            TableMenusBO tableMenusBO = new TableMenusBO();
            tableMenusBO.setSuccess(false);
            return tableMenusBO;
        }
        return JSONObject.parseObject(entity.getBody(), TableMenusBO.class);
    }

    /**
     * 获取西软的session信息
     *
     * @param hotelId 酒店id
     * @return session信息
     */
    private synchronized String getSession(String hotelId) {
        LoginBO loginBO = loginBOMap.get(hotelId);
        if (loginBO == null) {
            loginBO = login(hotelId);
            loginBOMap.put(hotelId, loginBO);
        }
        return loginBO.getSession();
    }

    /**
     * 酒店登录西软平台
     *
     * @param hotelId 酒店id
     * @return 登录信息
     */
    private LoginBO login(String hotelId) {
        LoginQueryOption loginQueryOption = new LoginQueryOption();
        loginQueryOption.setHotelid(hotelId);
        String sign = createRequestSign(JsonUtils.obj2JsonObj(loginQueryOption), XopMethod.SECRET);
        loginQueryOption.setSign(sign);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<LoginQueryOption> httpEntity = new HttpEntity<>(loginQueryOption, headers);
        ResponseEntity<String> entity = null;
        try {
            if (checkTimes()) {
                entity = restTemplate.postForEntity(XopMethod.URL, httpEntity, String.class);
            }
        } catch (RestClientException e) {
            log.info("调用西软订单同步异常");
            log.error(e.getMessage(), e);
        }
        if (entity == null) {
            LoginBO loginBO = new LoginBO();
            loginBO.setSuccess(false);
            return loginBO;
        }
        return JSONObject.parseObject(entity.getBody(), LoginBO.class);
    }

    /**
     * 获取西软接口数据
     *
     * @param businessId 酒店id
     * @return 西软接口数据
     */
    private synchronized XmsBusiness getBusinessXms(Integer businessId) {
        XmsBusiness xmsBusiness = xmsBusinessMap.get(businessId);
        if (xmsBusiness == null) {
            //查询酒店id跟西软关联的数据
            log.info("getBusinessXms酒店id:{}", businessId);
            xmsBusiness = xmsBusinessService.selectOne(new EntityWrapper<XmsBusiness>()
                    .eq("business_id", businessId));
            if (xmsBusiness == null) {
                throw new RuntimeException("酒店不存在");
            }
            xmsBusinessMap.put(businessId, xmsBusiness);
        }
        return xmsBusiness;
    }

    /**
     * 获取桌位状态
     *
     * @param session 西软登录的session
     * @param xms     西软数据接口
     * @param pcCode  营业点区域
     * @return 桌位状态
     */
    private TableStatusBO getTableStatus(String session, XmsBusiness xms, String pcCode) {
        //生成接口查询对象
        TableStatusQueryOption tableStatusQueryOption = new TableStatusQueryOption();
        tableStatusQueryOption.setSession(session);
        tableStatusQueryOption.setHotelid(xms.getBrandHotelId());
        //业务参数
        List<Map<String, String>> params = Lists.newArrayList();
        Map<String, String> map = Maps.newHashMap();
        if (!pcCode.isEmpty()) {
            map.put("pccode", pcCode);
        }
        map.put("bdate", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String time = DateFormatUtils.format(new Date(), "HH:mm");
        MealType mealType = mealTypeService.selectOne(
                new EntityWrapper<MealType>()
                        .eq("business_id", xms.getBusinessId())
                        .le("resv_start_time", time)
                        .ge("resv_end_time", time)
        );
        if(mealType == null){
            throw new RuntimeException("当前时间没有餐次");
        }
        map.put("shift", String.valueOf(mealType.getConfigId() == 11?2:3));
        map.put("hotelid", xms.getBusinessHotelId());
        params.add(map);
        tableStatusQueryOption.setParams(params);
        String sign = createRequestSign(JsonUtils.obj2JsonObj(tableStatusQueryOption), XopMethod.SECRET);
        tableStatusQueryOption.setSign(sign);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<TableStatusQueryOption> httpEntity = new HttpEntity<>(tableStatusQueryOption, headers);
        ResponseEntity<String> entity = null;
        try {
            if (checkTimes()) {
                entity = restTemplate.postForEntity(XopMethod.URL, httpEntity, String.class);
            }
        } catch (RestClientException e) {
            log.info("调用西软订单同步异常");
            log.error(e.getMessage(), e);
        }
        if (entity == null) {
            TableStatusBO tableStatusBO = new TableStatusBO();
            tableStatusBO.setSuccess(false);
            return tableStatusBO;
        }
        return JSONObject.parseObject(entity.getBody(), TableStatusBO.class);
    }


    /**
     * 生成西软签名
     *
     * @param request
     * @param secret
     * @return
     */
    private static String createRequestSign(JSONObject request, String secret) {
        String md5 = null;
        List<String> values = Lists.newArrayList();
        JSONArray params = request.getJSONArray("params");
        JsonUtils.walkRequest(params, values);
        Collections.sort(values);

        StringBuilder signStr = new StringBuilder();
        signStr.append(request.getString("appkey")).append(request.getString("hotelid")).append(request.getString("loc")).append(request.getString("ts"));
        for (String value : values) {
            signStr.append(value);
        }
        signStr.append(secret);
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(signStr.toString().getBytes());
            StringBuilder stringBuffer = new StringBuilder();
            for (byte b : messageDigest.digest()) {
                stringBuffer.append(b);
            }
            md5 = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }

    private boolean checkTimes() {
        try {
            TimeUnit.MILLISECONDS.sleep(15);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
//        String currentStr = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmss");
//        String key = "XMS:" + currentStr;
//        long times = redisTemplate.opsForValue().increment(key, 1);
//        if (redisTemplate.getExpire(key) == -1) {
//            redisTemplate.expire(key, 1, TimeUnit.SECONDS);
//        }
//        if (times > ydPropertites.getXms().getTimes()) {
//            log.info("西软接口请求次数过多");
//            return false;
//        }
        return true;
    }

    public TableMenuBO getMenuOrder(Integer businessId, String tableCode) {
        XmsBusiness xms = getBusinessXms(businessId);
        String session = getSession(xms.getBrandHotelId());
        TableMenuOption option = new TableMenuOption();
        option.setSession(session);
        option.setHotelid(xms.getBrandHotelId());
        HashMap<String,String> data = Maps.newHashMap();
        data.put("hotelid",xms.getBrandHotelId());
        data.put("pcid","PCID");
        data.put("key",tableCode);
        ArrayList<Map<String,String>> params = Lists.newArrayList();
        params.add(data);
        option.setParams(params);
        String sign = createRequestSign(JsonUtils.obj2JsonObj(option), XopMethod.SECRET);
        option.setSign(sign);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<TableMenuOption> httpEntity = new HttpEntity<>(option, headers);
        ResponseEntity<String> entity = null;
        try {
            if (checkTimes()) {
                entity = restTemplate.postForEntity(XopMethod.URL, httpEntity, String.class);
            }
        } catch (RestClientException e) {
            log.info("获取营业点接口调用异常");
            log.error(e.getMessage(), e);
        }

        if (entity == null) {
            TableMenuBO tableMenuBO = new TableMenuBO();
            tableMenuBO.setSuccess(false);
            return tableMenuBO;
        }
        return JSONObject.parseObject(entity.getBody(), TableMenuBO.class);
    }

    public OrderStatusBO getOrderStatus(Integer businessId, Set<String> menuOrders) {
        XmsBusiness xms = getBusinessXms(businessId);
        String session = getSession(xms.getBrandHotelId());
        OrderStatusQueryOption option = new OrderStatusQueryOption();
        option.setSession(session);
        option.setHotelid(xms.getBrandHotelId());
        List<Map<String,String>> params = Lists.newArrayList();
        Map<String, String> data = Maps.newHashMap();
        data.put("hotelid",xms.getBusinessHotelId());
        data.put("menu",StringUtils.join(menuOrders,","));
        params.add(data);
        option.setParams(params);
        String sign = createRequestSign(JsonUtils.obj2JsonObj(option), XopMethod.SECRET);
        option.setSign(sign);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<OrderStatusQueryOption> httpEntity = new HttpEntity<>(option, headers);
        ResponseEntity<String> entity = null;
        try {
            if (checkTimes()) {
                entity = restTemplate.postForEntity(XopMethod.URL, httpEntity, String.class);
            }
        } catch (RestClientException e) {
            log.info("获取营业点接口调用异常");
            log.error(e.getMessage(), e);
        }

        if (entity == null) {
            OrderStatusBO orderStatus = new OrderStatusBO();
            orderStatus.setSuccess(false);
            return orderStatus;
        }
        return JSONObject.parseObject(entity.getBody(), OrderStatusBO.class);
    }

    public OrderDishBO getOrderDish(Integer businessId, String menu) {
        XmsBusiness xms = getBusinessXms(businessId);
        String session = getSession(xms.getBrandHotelId());
        OrderDishQueryOption option = new OrderDishQueryOption();
        option.setSession(session);
        option.setHotelid(xms.getBrandHotelId());
        List<Map<String,String>> params = Lists.newArrayList();
        Map<String, String> data = Maps.newHashMap();
        data.put("hotelid",xms.getBusinessHotelId());
        data.put("menu",menu);
        data.put("pcid","PCID");
        data.put("type","1");
        params.add(data);
        option.setParams(params);
        String sign = createRequestSign(JsonUtils.obj2JsonObj(option), XopMethod.SECRET);
        option.setSign(sign);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<OrderDishQueryOption> httpEntity = new HttpEntity<>(option, headers);
        ResponseEntity<String> entity = null;
        try {
            if (checkTimes()) {
                entity = restTemplate.postForEntity(XopMethod.URL, httpEntity, String.class);
            }
        } catch (RestClientException e) {
            log.info("获取营业点接口调用异常");
            log.error(e.getMessage(), e);
        }

        if (entity == null) {
            OrderDishBO orderDishBO = new OrderDishBO();
            orderDishBO.setSuccess(false);
            return orderDishBO;
        }
        return JSONObject.parseObject(entity.getBody(), OrderDishBO.class);
    }
}