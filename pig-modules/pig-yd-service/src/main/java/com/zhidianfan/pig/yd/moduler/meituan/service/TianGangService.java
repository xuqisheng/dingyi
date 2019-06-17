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
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

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

    @Autowired
    private IResvOrderService resvOrderService;

    @Autowired
    private IResvOrderSyncService resvOrderSyncService;

    @Autowired
    private IResvMeetingOrderService resvMeetingOrderService;

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
        MealType mealType = mealTypeService.selectOne(new EntityWrapper<MealType>().eq("business_id",business.getId()).eq("config_id",tianGangOrderBO.getMealConfigId()).eq("status","1"));
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
            resvOrderThird.setVipSex(tianGangOrderBO.getVipSex());
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
            resvMeetingKey.setVipSex("先生".equals(tianGangOrderBO.getVipSex()) ? "男" : "女");
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            resvMeetingKey.setResvDate(format.parse(tianGangOrderBO.getResvDate()));
            String keyNo = IdUtils.makeOrderNo();
            resvMeetingKey.setKeyNo("XS"+keyNo);
            resvMeetingKey.setKeyNoBusiness(tianGangOrderBO.getThirdOrderNo().substring(tianGangOrderBO.getThirdOrderNo().length()-3));

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
    public boolean createTianGangOrder(TGOrderCreateDTO tgOrderCreateDTO,Integer orderType){

        boolean create = false;

        //连接是否健康
        if(ApiHealthCheck()){

            Map<String, Object> params = JsonUtils.object2Map(tgOrderCreateDTO);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity httpEntity = new HttpEntity(params,headers);

            String url = TianGangMethod.CREATE_ORDER_URL + "?access_token=" + accessToken;

            try {

                ResponseEntity<String> entity = restTemplate.postForEntity(url, httpEntity, String.class);

                JSONObject result = JSONObject.parseObject(entity.getBody(), JSONObject.class);

                if(Boolean.parseBoolean(String.valueOf(result.get("success")))){
                    JSONObject result1 = JSONObject.parseObject(String.valueOf(result.get("result")), JSONObject.class);
                    if(orderType == 1){
                        updateOrderSyncStatus(tgOrderCreateDTO.getYdOrderNumber(),String.valueOf(result1.get("orderNumber")));
                    }else {
                        updateMeetingOrderSyncStatus(tgOrderCreateDTO.getYdOrderNumber(),String.valueOf(result1.get("orderNumber")));
                    }
                    create = true;
                }

            }catch (Exception e){

                e.printStackTrace();

            }

        }

        return create;

    }

    /**
     * 修改订单--天港
     * @param tgOrderUpdateDTO
     */
    public boolean updateTianGangOrder(TGOrderUpdateDTO tgOrderUpdateDTO){

        boolean update = false;

        //连接是否健康
        if(ApiHealthCheck()){

            Map<String, Object> params = JsonUtils.object2Map(tgOrderUpdateDTO);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity httpEntity = new HttpEntity(params,headers);

            String url = TianGangMethod.UPDATE_ORDER_URL + "?access_token=" + accessToken;

            try {

                ResponseEntity<String> entity = restTemplate.postForEntity(url, httpEntity, String.class);

                JSONObject result = JSONObject.parseObject(entity.getBody(), JSONObject.class);

                if(Boolean.parseBoolean(result.get("success").toString())){
                    update = true;
                }

            }catch (Exception e){

                e.printStackTrace();

            }

        }

        return update;

    }


    /**
     * 取消订单--天港
     * @param tgOrderCanCelDTO
     */
    public boolean cancelTianGangOrder(TGOrderCanCelDTO tgOrderCanCelDTO){

        boolean cancel = false;

        if(StringUtils.isBlank(tgOrderCanCelDTO.getOrderNumber())){
            return cancel;
        }

        //连接是否健康
        if(ApiHealthCheck()){

            Map<String, Object> params = JsonUtils.object2Map(tgOrderCanCelDTO);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity httpEntity = new HttpEntity(params,headers);

            String url = TianGangMethod.CANCEL_ORDER_URL + "?access_token=" + accessToken;

            try {

                ResponseEntity<String> entity = restTemplate.postForEntity(url, httpEntity, String.class);

                JSONObject result = JSONObject.parseObject(entity.getBody(), JSONObject.class);

                if(Boolean.parseBoolean(result.get("success").toString())){
                    cancel = true;
                }

            }catch (Exception e){

                e.printStackTrace();

            }

        }

        return cancel;

    }

    /**
     * 结算订单--天港
     * @param tgOrderSubmitDTO
     */
    public boolean submitTianGangOrder(TGOrderSubmitDTO tgOrderSubmitDTO){

        boolean submit = false;

        if(StringUtils.isBlank(tgOrderSubmitDTO.getOrderNumber())){
            return submit;
        }

        //连接是否健康
        if(ApiHealthCheck()){

            Map<String, Object> params = JsonUtils.object2Map(tgOrderSubmitDTO);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity httpEntity = new HttpEntity(params,headers);

            String url = TianGangMethod.SUBMIT_ORDER_URL + "?access_token=" + accessToken;

            try {

                ResponseEntity<String> entity = restTemplate.postForEntity(url, httpEntity, String.class);

                JSONObject result = JSONObject.parseObject(entity.getBody(), JSONObject.class);

                if(Boolean.parseBoolean(result.get("success").toString())){
                    submit = true;
                }

            }catch (Exception e){

                e.printStackTrace();

            }

        }

        return submit;

    }


    /**
     * 遍历天港酒店
     */
    public void tianGangTask(){

        log.info("开始----------------");

        //查找天港需要同步的酒店
        List<Business> businessList = businessService.selectList(new EntityWrapper<Business>().isNotNull("branch_code").eq("status","1"));

        for(Business business : businessList){
            //同步易订普通订单到天港
            syncYidingOrderToTiangang(business.getId(),business.getBranchCode());
            //同步易订宴会订单到天港
            syncYidingMeetingOrderToTiangang(business.getId(),business.getBranchCode());
        }

        log.info("结束----------------");

    }

    /**
     * 同步易订普通订单到天港
     * @param businessId
     * @param branchCode
     */
    public void syncYidingOrderToTiangang(Integer businessId,String branchCode){

        //订单状态
        List<String> statusList = new ArrayList<>();
        statusList.add("1");
        statusList.add("2");
        statusList.add("3");
        statusList.add("4");

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        List<ResvOrder> resvOrderList = resvOrderService.selectList(new EntityWrapper<ResvOrder>().eq("business_id",businessId).ge("resv_date",format.format(new Date())).in("status",statusList));

        List<MealType> mediaTypeList = mealTypeService.selectList(new EntityWrapper<MealType>().eq("business_id",businessId).eq("status","1"));

        for (ResvOrder resvOrder : resvOrderList){

            ResvOrderSync resvOrderSync = resvOrderSyncService.selectOne(new EntityWrapper<ResvOrderSync>().eq("batch_no",resvOrder.getBatchNo()));

            if(resvOrderSync != null && resvOrderSync.getIsSync() == 1){
                continue;
            }

            String status = resvOrder.getStatus();

            String tableName = resvOrder.getTableAreaName() + "-" + resvOrder.getTableName();

            String remark = resvOrder.getRemark();

            List<ResvOrder> resvOrderList1 = resvOrderService.selectList(new EntityWrapper<ResvOrder>().eq("batch_no",resvOrder.getBatchNo()));

            if(resvOrderList1.size() > 1){

                tableName = "";

                remark = "";

                for(ResvOrder resvOrder1:resvOrderList1){

                    //退订的除外
                    if(!"4".equals(resvOrder1.getStatus())){

                        tableName += resvOrder1.getTableAreaName() + "-" + resvOrder1.getTableName() + ",";

                        if(StringUtils.isNotBlank(resvOrder1.getRemark())){
                            remark += resvOrder1.getTableAreaName() + "-" + resvOrder1.getTableName() + "(" + resvOrder1.getRemark() + ")" + ",";
                        }
                    }

                    if(!status.equals(resvOrder1.getStatus())){
                        if("3".equals(resvOrder1.getStatus())||"3".equals(status)){
                            status = "3";
                            break;
                        } else if("4".equals(resvOrder1.getStatus())||"4".equals(status)){
                            status = "1";
                            break;
                        }
                    }
                }

                if(StringUtils.isNotBlank(tableName)){
                    tableName = tableName.substring(0,tableName.length()-1);
                }

                if(StringUtils.isNotBlank(remark)){
                    remark = remark.substring(0,remark.length()-1);
                }

            }

            resvOrder.setTableName(tableName);

            resvOrder.setRemark(remark);

            if("1".equals(status) || "2".equals(status)){

                if(resvOrderSync != null && StringUtils.isNotBlank(resvOrderSync.getThirdOrderNo())){
                    //天港下单 预订台接单  更新天港订单
                    TGOrderUpdateDTO tgOrderUpdateDTO = resvOrderToTGOrderUpdateDTO(resvOrder,getMealConfigId(mediaTypeList,resvOrder.getMealTypeId()),resvOrderSync.getThirdOrderNo());
                    boolean b = updateTianGangOrder(tgOrderUpdateDTO);
                    if(b){
                        updateOrderSyncStatus(resvOrder.getBatchNo(),null);
                    }
                }else {
                    //易订下单 天港新建订单
                    TGOrderCreateDTO tgOrderCreateDTO = resvOrderToTGOrderCreateDTO(resvOrder,getMealConfigId(mediaTypeList,resvOrder.getMealTypeId()),branchCode);
                    createTianGangOrder(tgOrderCreateDTO,1);
                }
            } else if("3".equals(status)){

                //易订结账 同步天港结账
                TGOrderSubmitDTO tgOrderSubmitDTO = new TGOrderSubmitDTO();
                tgOrderSubmitDTO.setYdOrderNumber(resvOrder.getBatchNo());
                tgOrderSubmitDTO.setOrderNumber(resvOrderSync.getThirdOrderNo());
                tgOrderSubmitDTO.setMealCategoryId(2);
                tgOrderSubmitDTO.setBill(Integer.valueOf(resvOrder.getPayamount()));
                boolean b = submitTianGangOrder(tgOrderSubmitDTO);
                if(b){
                    updateOrderSyncStatus(resvOrder.getBatchNo(),null);
                }

            } else if("4".equals(status)){

                //易订退订 同步天港取消订单
                TGOrderCanCelDTO tgOrderCanCelDTO = new TGOrderCanCelDTO();
                tgOrderCanCelDTO.setYdOrderNumber(resvOrder.getBatchNo());
                tgOrderCanCelDTO.setOrderNumber(resvOrderSync.getThirdOrderNo());
                boolean b = cancelTianGangOrder(tgOrderCanCelDTO);
                if(b){
                    updateOrderSyncStatus(resvOrder.getBatchNo(),null);
                }

            }
        }
    }

    /**
     * 同步易订宴会订单到天港
     * @param businessId
     * @param branchCode
     */
    public void syncYidingMeetingOrderToTiangang(Integer businessId,String branchCode){

        //订单状态
        List<String> statusList = new ArrayList<>();
        statusList.add("1");
        statusList.add("2");
        statusList.add("3");
        statusList.add("4");

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        List<ResvMeetingOrder> resvMeetingOrderList = resvMeetingOrderService.selectList(new EntityWrapper<ResvMeetingOrder>().eq("business_id",businessId).ge("resv_date",format.format(new Date())).in("status",statusList).eq("tg_is_sync",0));

        List<MealType> mediaTypeList = mealTypeService.selectList(new EntityWrapper<MealType>().eq("business_id",businessId).eq("status","1"));

        for (ResvMeetingOrder resvMeetingOrder : resvMeetingOrderList){

            ResvMeetingOrder resvMeetingOrder2 = resvMeetingOrderService.selectOne(new EntityWrapper<ResvMeetingOrder>().eq("resv_order",resvMeetingOrder.getResvOrder()));

            if(resvMeetingOrder2 != null && resvMeetingOrder2.getTgIsSync() == 1){
                continue;
            }

            String status = resvMeetingOrder.getStatus();

            String tableName = resvMeetingOrder.getTableAreaName() + "-" + resvMeetingOrder.getTableName();

            String remark = resvMeetingOrder.getRemark();

            List<ResvMeetingOrder> resvMeetingOrderList1 = resvMeetingOrder.selectList(new EntityWrapper<ResvMeetingOrder>().eq("batch_no",resvMeetingOrder.getBatchNo()));

            if(resvMeetingOrderList1.size() > 1){

                tableName = "";

                remark = "";

                for(ResvMeetingOrder resvMeetingOrder1:resvMeetingOrderList1){

                    //退订的除外
                    if(!"4".equals(resvMeetingOrder1.getStatus())){

                        tableName += resvMeetingOrder1.getTableAreaName() + "-" + resvMeetingOrder1.getTableName() + ",";

                        if(StringUtils.isNotBlank(resvMeetingOrder1.getRemark())){
                            remark += resvMeetingOrder1.getTableAreaName() + "-" + resvMeetingOrder1.getTableName() + "(" + resvMeetingOrder1.getRemark() + ")" + ",";
                        }
                    }

                    if(!status.equals(resvMeetingOrder1.getStatus())){
                        if("3".equals(resvMeetingOrder1.getStatus())||"3".equals(status)){
                            status = "3";
                            break;
                        } else if("4".equals(resvMeetingOrder1.getStatus())||"4".equals(status)){
                            status = "1";
                            break;
                        }
                    }
                }

                if(StringUtils.isNotBlank(tableName)){
                    tableName = tableName.substring(0,tableName.length()-1);
                }

                if(StringUtils.isNotBlank(remark)){
                    remark = remark.substring(0,remark.length()-1);
                }

            }

            resvMeetingOrder.setTableName(tableName);

            resvMeetingOrder.setRemark(remark);

            if("1".equals(status) || "2".equals(status)){

                if(StringUtils.isNotBlank(resvMeetingOrder.getTgOrderNo())){
                    //天港下单 预订台接单  更新天港订单
                    TGOrderUpdateDTO tgOrderUpdateDTO = resvMeetingOrderToTGOrderUpdateDTO(resvMeetingOrder,getMealConfigId(mediaTypeList,resvMeetingOrder.getMealTypeId()));
                    boolean b = updateTianGangOrder(tgOrderUpdateDTO);
                    if(b){
                        updateMeetingOrderSyncStatus(resvMeetingOrder.getBatchNo(),null);
                    }
                }else {
                    //易订下单 天港新建订单
                    TGOrderCreateDTO tgOrderCreateDTO = resvMeetingOrderToTGOrderCreateDTO(resvMeetingOrder,getMealConfigId(mediaTypeList,resvMeetingOrder.getMealTypeId()),branchCode);
                    createTianGangOrder(tgOrderCreateDTO,2);
                }
            } else if("3".equals(status)){

                //易订结账 同步天港结账
                TGOrderSubmitDTO tgOrderSubmitDTO = new TGOrderSubmitDTO();
                tgOrderSubmitDTO.setYdOrderNumber(resvMeetingOrder.getBatchNo());
                tgOrderSubmitDTO.setOrderNumber(resvMeetingOrder.getTgOrderNo());
                tgOrderSubmitDTO.setMealCategoryId(1);
                tgOrderSubmitDTO.setBill(Integer.valueOf(resvMeetingOrder.getPayAmount()));
                boolean b = submitTianGangOrder(tgOrderSubmitDTO);
                if(b){
                    updateMeetingOrderSyncStatus(resvMeetingOrder.getBatchNo(),null);
                }

            } else if("4".equals(status)){

                //易订退订 同步天港取消订单
                TGOrderCanCelDTO tgOrderCanCelDTO = new TGOrderCanCelDTO();
                tgOrderCanCelDTO.setYdOrderNumber(resvMeetingOrder.getBatchNo());
                tgOrderCanCelDTO.setOrderNumber(resvMeetingOrder.getTgOrderNo());
                boolean b = cancelTianGangOrder(tgOrderCanCelDTO);
                if(b){
                    updateMeetingOrderSyncStatus(resvMeetingOrder.getBatchNo(),null);
                }

            }
        }
    }

    /**
     * 修改天港宴会订单同步状态
     * @param batchNo
     */
    public void updateMeetingOrderSyncStatus(String batchNo,String tgOrderNo){

        List<ResvMeetingOrder> resvMeetingOrderList = resvMeetingOrderService.selectList(new EntityWrapper<ResvMeetingOrder>().eq("batch_no",batchNo));

        for(ResvMeetingOrder resvMeetingOrder1:resvMeetingOrderList){
            resvMeetingOrder1.setTgIsSync(1);
            if(StringUtils.isNotBlank(tgOrderNo)){
                resvMeetingOrder1.setTgOrderNo(tgOrderNo);
            }
        }

        resvMeetingOrderService.updateBatchById(resvMeetingOrderList);

    }

    /**
     * 修改天港普通订单同步状态
     * @param batchNo
     */
    public void updateOrderSyncStatus(String batchNo,String thirdOrderNo){

        ResvOrderSync resvOrderSync = resvOrderSyncService.selectOne(new EntityWrapper<ResvOrderSync>().eq("batch_no",batchNo));

        ResvOrderSync resvOrderSync1 = new ResvOrderSync();

        if(resvOrderSync == null){
            resvOrderSync1.setBatchNo(batchNo);
            resvOrderSync1.setIsSync(1);
            resvOrderSync1.setCreateTime(new Date());
            if(StringUtils.isNotBlank(thirdOrderNo)){
                resvOrderSync1.setThirdOrderNo(thirdOrderNo);
            }
            resvOrderSyncService.insert(resvOrderSync1);
        }else {
            resvOrderSync1.setUpdateTime(new Date());
            resvOrderSync1.setIsSync(1);
            if(StringUtils.isNotBlank(thirdOrderNo)){
                resvOrderSync1.setThirdOrderNo(thirdOrderNo);
            }
            resvOrderSyncService.update(resvOrderSync1,new EntityWrapper<ResvOrderSync>().eq("batch_no",batchNo));
        }

    }

    /**
     * 筛选早中晚餐别id
     * @return
     */
    public Integer getMealConfigId(List<MealType> mealTypeList,Integer mealTypeId){

        Integer mealConfigId = 1;

        for (MealType mealType : mealTypeList){
            if(mealTypeId.equals(mealType.getId())){
                if("10".equals(String.valueOf(mealType.getConfigId()))){
                    mealConfigId = 1;
                }
                if("11".equals(String.valueOf(mealType.getConfigId()))){
                    mealConfigId = 2;
                }
                if("12".equals(String.valueOf(mealType.getConfigId()))){
                    mealConfigId = 3;
                }
                if("13".equals(String.valueOf(mealType.getConfigId()))){
                    mealConfigId = 4;
                }
                break;
            }
        }

        return mealConfigId;
    }

    /**
     * 对象转换
     * @param resvOrder
     * @param mealConfigId
     * @param thirdOrderNo
     * @return
     */
    public TGOrderUpdateDTO resvOrderToTGOrderUpdateDTO(ResvOrder resvOrder,Integer mealConfigId,String thirdOrderNo){
        TGOrderUpdateDTO tgOrderUpdateDTO = new TGOrderUpdateDTO();
        tgOrderUpdateDTO.setYdOrderNumber(resvOrder.getBatchNo());
        tgOrderUpdateDTO.setOrderNumber(thirdOrderNo);
        tgOrderUpdateDTO.setCustomerName(resvOrder.getVipName());
        tgOrderUpdateDTO.setCustomerPhone(resvOrder.getVipPhone());
        tgOrderUpdateDTO.setCustomerGender("男".equals(resvOrder.getVipSex()) ? 2 : 1);
        tgOrderUpdateDTO.setOwnerPhone("19857085775");
        tgOrderUpdateDTO.setMealCategoryId(2);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        tgOrderUpdateDTO.setDate(format.format(resvOrder.getResvDate()));
        tgOrderUpdateDTO.setMealTimeTypeId(mealConfigId);
        tgOrderUpdateDTO.setTableNumber(resvOrder.getTableName());
        tgOrderUpdateDTO.setCustomerAmount(Integer.valueOf(resvOrder.getResvNum()));
        tgOrderUpdateDTO.setExtraNotes(resvOrder.getRemark());
        return tgOrderUpdateDTO;
    }

    /**
     * 对象转换
     * @param resvMeetingOrder
     * @param mealConfigId
     * @return
     */
    public TGOrderUpdateDTO resvMeetingOrderToTGOrderUpdateDTO(ResvMeetingOrder resvMeetingOrder,Integer mealConfigId){
        TGOrderUpdateDTO tgOrderUpdateDTO = new TGOrderUpdateDTO();
        tgOrderUpdateDTO.setYdOrderNumber(resvMeetingOrder.getBatchNo());
        tgOrderUpdateDTO.setOrderNumber(resvMeetingOrder.getTgOrderNo());
        tgOrderUpdateDTO.setCustomerName(resvMeetingOrder.getVipName());
        tgOrderUpdateDTO.setCustomerPhone(resvMeetingOrder.getVipPhone());
        tgOrderUpdateDTO.setCustomerGender("男".equals(resvMeetingOrder.getVipSex()) ? 2 : 1);
        tgOrderUpdateDTO.setOwnerPhone("19857085775");
        tgOrderUpdateDTO.setMealCategoryId(1);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        tgOrderUpdateDTO.setDate(format.format(resvMeetingOrder.getResvDate()));
        tgOrderUpdateDTO.setMealTimeTypeId(mealConfigId);
        tgOrderUpdateDTO.setTableNumber(resvMeetingOrder.getTableName());
        tgOrderUpdateDTO.setYdBanquetCategoryId(resvMeetingOrder.getResvMeetingOrderType());
        tgOrderUpdateDTO.setExtraNotes(resvMeetingOrder.getRemark());
        tgOrderUpdateDTO.setStandardMealMark(resvMeetingOrder.getDishStandard());
        tgOrderUpdateDTO.setTableAmount(resvMeetingOrder.getResvTableNum());
        return tgOrderUpdateDTO;
    }

    /**
     * 对象转换
     * @param resvOrder
     * @param mealConfigId
     * @param branchCode
     * @return
     */
    public TGOrderCreateDTO resvOrderToTGOrderCreateDTO(ResvOrder resvOrder,Integer mealConfigId,String branchCode){
        TGOrderCreateDTO tgOrderCreateDTO = new TGOrderCreateDTO();
        tgOrderCreateDTO.setYdOrderNumber(resvOrder.getBatchNo());
        tgOrderCreateDTO.setBranchCode(branchCode);
        tgOrderCreateDTO.setCustomerName(resvOrder.getVipName());
        tgOrderCreateDTO.setCustomerPhone(resvOrder.getVipPhone());
        tgOrderCreateDTO.setCustomerGender("男".equals(resvOrder.getVipSex()) ? 2 : 1);
        tgOrderCreateDTO.setOwnerPhone("19857085775");
        tgOrderCreateDTO.setMealCategoryId(2);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        tgOrderCreateDTO.setDate(format.format(resvOrder.getResvDate()));
        tgOrderCreateDTO.setMealTimeTypeId(mealConfigId);
        tgOrderCreateDTO.setCustomerAmount(Integer.valueOf(resvOrder.getResvNum()));
        tgOrderCreateDTO.setExtraNotes(resvOrder.getRemark());
        tgOrderCreateDTO.setTableNumber(resvOrder.getTableName());
        return tgOrderCreateDTO;
    }

    /**
     * 对象转换
     * @param resvMeetingOrder
     * @param mealConfigId
     * @param branchCode
     * @return
     */
    public TGOrderCreateDTO resvMeetingOrderToTGOrderCreateDTO(ResvMeetingOrder resvMeetingOrder,Integer mealConfigId,String branchCode){
        TGOrderCreateDTO tgOrderCreateDTO = new TGOrderCreateDTO();
        tgOrderCreateDTO.setYdOrderNumber(resvMeetingOrder.getBatchNo());
        tgOrderCreateDTO.setBranchCode(branchCode);
        tgOrderCreateDTO.setCustomerName(resvMeetingOrder.getVipName());
        tgOrderCreateDTO.setCustomerPhone(resvMeetingOrder.getVipPhone());
        tgOrderCreateDTO.setCustomerGender("男".equals(resvMeetingOrder.getVipSex()) ? 2 : 1);
        tgOrderCreateDTO.setOwnerPhone("19857085775");
        tgOrderCreateDTO.setMealCategoryId(1);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        tgOrderCreateDTO.setDate(format.format(resvMeetingOrder.getResvDate()));
        tgOrderCreateDTO.setMealTimeTypeId(mealConfigId);
        tgOrderCreateDTO.setTableAmount(Integer.valueOf(resvMeetingOrder.getResvTableNum()));
        tgOrderCreateDTO.setExtraNotes(resvMeetingOrder.getRemark());
        tgOrderCreateDTO.setYdBanquetCategoryId(resvMeetingOrder.getResvMeetingOrderType());
        tgOrderCreateDTO.setTableNumber(resvMeetingOrder.getTableName());
        return tgOrderCreateDTO;
    }



}
