package com.zhidianfan.pig.yd.moduler.meituan.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.yd.moduler.common.constant.QueueName;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.common.service.*;
import com.zhidianfan.pig.yd.moduler.meituan.bo.BasicBO;
import com.zhidianfan.pig.yd.moduler.meituan.bo.BusinessBo;
import com.zhidianfan.pig.yd.moduler.meituan.bo.OrderBO;
import com.zhidianfan.pig.yd.moduler.meituan.bo.OrderQueryBO;
import com.zhidianfan.pig.yd.moduler.meituan.constant.MeituanMethod;
import com.zhidianfan.pig.yd.moduler.meituan.dto.*;
import com.zhidianfan.pig.yd.moduler.meituan.service.rmi.PushFeign;
import com.zhidianfan.pig.yd.moduler.meituan.service.rmi.dto.JgPush;
import com.zhidianfan.pig.yd.moduler.resv.enums.OrderStatus;
import com.zhidianfan.pig.yd.moduler.resv.service.AutoReceiptConfigService;
import com.zhidianfan.pig.yd.moduler.resv.service.VipService;
import com.zhidianfan.pig.yd.moduler.wechat.util.OrderTemplate;
import com.zhidianfan.pig.yd.moduler.wechat.util.WeChatUtils;
import com.zhidianfan.pig.yd.moduler.wechat.vo.PushMessageVO;
import com.zhidianfan.pig.yd.utils.IdUtils;
import com.zhidianfan.pig.yd.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/9/03
 * @Modified By:
 */

@Service
@Slf4j
public class YdService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ITableService tableService;

    @Autowired
    private IMealTypeService mealTypeService;

    @Autowired
    private IResvOrderThirdService resvOrderThirdService;

    @Autowired
    private IBusinessService businessService;

    @Autowired
    private IResvOrderService iResvOrderService;
    @Autowired
    private IResvOrderAndroidService iResvOrderAndroidService;

    @Autowired
    private IResvOrderThirdService iResvOrderThirdService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PushFeign pushFeign;

    @Autowired
    private IDeviceOrderNoticeService deviceOrderNoticeService;

    @Autowired
    private AutoReceiptConfigService autoReceiptConfigService;

    @Autowired
    private ITableAreaService iTableAreaService;

    @Autowired
    private MeituanService meituanService;

    @Autowired
    private VipService vipService;

    @Autowired
    IVipService iVipService;

    /**
     * 订单日志记录
     */
    @Autowired
    private IResvOrderLogsService iResvOrderLogsService;

    /**
     * 易订桌位查询接口
     *
     * @param tableDTO 桌位dto
     * @return BasicBO 基础bo
     */
    public BasicBO tablesQuery(TableDTO tableDTO) {
        BasicBO basicBO = new BasicBO();
        if (JsonUtils.jsonStr2Obj(tableDTO.getEPoiIds(), JSONArray.class).size() > 10) {
            basicBO = failSign(20001, "门店查询数量超过限制");
            return basicBO;
        }
        JSONArray data = new JSONArray();
        for (Object businessId : JsonUtils.jsonStr2Obj(tableDTO.getEPoiIds(), JSONArray.class)) {
            businessId = Integer.parseInt(businessId.toString());
            List<Table> tables = tableService.selectList(new EntityWrapper<Table>().eq("business_id", businessId).eq("status", 1));
            JSONArray tableArray = new JSONArray();
            for (Table table : tables) {
                JSONObject tableObject = new JSONObject();
                tableObject.put("tableId", table.getId().toString());
                tableObject.put("tableName", table.getTableName());
                tableObject.put("tableType", table.gettType() == 0 ? 1 : 0);
                tableObject.put("maxPeople", Integer.parseInt(table.getMaxPeopleNum()));
                tableObject.put("isOnlineSale", 0);
                tableArray.add(tableObject);
            }
            JSONObject result = new JSONObject();
            Business business = businessService.selectOne(new EntityWrapper<Business>().eq("id", businessId));
            result.put("appAuthToken", business.getAppToken());
            result.put("tables", tableArray);
            data.add(result);
        }
        basicBO.setData(data);
        basicBO.setError(null);
        basicBO.setSign(createMeituanSgin(object2Map(basicBO)));
        return basicBO;
    }

    /**
     * 易订已订桌位查询接口
     *
     * @param tablesOccDTO 已经预定桌位dto
     * @return BasicBO 基础bo
     */
    public BasicBO tableOccupiedQuery(TablesOccDTO tablesOccDTO) throws Exception {
        BasicBO basicBO = new BasicBO();
        if (JsonUtils.jsonStr2Obj(tablesOccDTO.getEPoiIds(), JSONArray.class).size() > 10) {
            basicBO = failSign(20001, "门店查询数量超过限制");
            return basicBO;
        }
        TimeDTO timeDTO = JSONObject.parseObject(tablesOccDTO.getData(), TimeDTO.class);
        JSONArray data = new JSONArray();
        for (Object businessId : JsonUtils.jsonStr2Obj(tablesOccDTO.getEPoiIds(), JSONArray.class)) {
            businessId = Integer.parseInt(businessId.toString());
            Business business = businessService.selectOne(new EntityWrapper<Business>().eq("id", businessId));
            JSONObject result = new JSONObject();
//            Date date3 = unixTimeToDate(timeDTO.getStartTime());
//            Date date4 = unixTimeToDate(timeDTO.getEndTime());
            List<ResvOrderAndroid> resvOrders = iResvOrderAndroidService.selectList(new EntityWrapper<ResvOrderAndroid>().eq("business_id", businessId)
                    .ge("resv_date", unixTimeToDate(timeDTO.getStartTime())).le("resv_date", unixTimeToDate(timeDTO.getEndTime())).in("status", new String[]{"1", "2", "5"}));

            //添加普通订单到电话机订单list中
            List<ResvOrder> resvOrders1 = iResvOrderService.selectList(new EntityWrapper<ResvOrder>().eq("business_id", businessId)
                    .ge("resv_date", unixTimeToDate(timeDTO.getStartTime())).le("resv_date", unixTimeToDate(timeDTO.getEndTime())).in("status", new String[]{"1", "2", "5"}));
            for (ResvOrder resvOrder : resvOrders1) {
                ResvOrderAndroid resvOrderAndroid = new ResvOrderAndroid();
                BeanUtils.copyProperties(resvOrder, resvOrderAndroid);
                resvOrders.add(resvOrderAndroid);
            }


            JSONArray tables = new JSONArray();
            for (ResvOrderAndroid resvOrder : resvOrders) {
                MealType mealType = mealTypeService.selectOne(new EntityWrapper<MealType>().eq("id", resvOrder.getMealTypeId()));
                JSONObject object = new JSONObject();
                object.put("tableId", resvOrder.getTableAreaId());
                object.put("orderTime", Integer.valueOf(String.valueOf(resvOrder.getResvDate().getTime() / 1000)));
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String startTime = sdf.format(resvOrder.getResvDate()) + " " + mealType.getResvStartTime() + ":00";
                Date date1 = format.parse(startTime);
                String endTime = sdf.format(resvOrder.getResvDate()) + " " + mealType.getResvEndTime() + ":00";
                Date date2 = format.parse(endTime);
                object.put("startTime", Integer.valueOf(String.valueOf(date1.getTime() / 1000)));
                object.put("endTime", Integer.valueOf(String.valueOf(date2.getTime() / 1000)));
                object.put("status", 0);
                object.put("timePeriod", mealType.getConfigId());
                tables.add(object);
            }
            result.put("appAuthToken", business.getAppToken());
            result.put("tables", tables);
            data.add(result);
        }
        basicBO.setData(data);
        basicBO.setError(null);
        basicBO.setSign(createMeituanSgin(object2Map(basicBO)));
        return basicBO;
    }

    /**
     * 门店时段查询
     *
     * @param mealDTO 餐别dto
     * @return BasicBO 基础dto
     */
    public BasicBO mealQuery(MealDTO mealDTO) {
        BasicBO basicBO = new BasicBO();
        if (JsonUtils.jsonStr2Obj(mealDTO.getEPoiIds(), JSONArray.class).size() > 50) {
            basicBO = failSign(20001, "门店查询数量超过限制");
            return basicBO;
        }
        JSONArray list = new JSONArray();
        for (Object businessId : JsonUtils.jsonStr2Obj(mealDTO.getEPoiIds(), JSONArray.class)) {
            businessId = Integer.parseInt(businessId.toString());
            Map<String, Object> result = new HashMap<>();
            JSONArray list1 = new JSONArray();
            List<MealType> meals = mealTypeService.selectList(new EntityWrapper<MealType>().eq("business_id", businessId).eq("status", "1"));
            JSONArray data = new JSONArray();
            JSONObject params1 = new JSONObject();
            for (MealType meal : meals) {
                JSONObject params = new JSONObject();
                params.put("type", meal.getConfigId());
                params.put("startHour", Integer.parseInt(meal.getResvStartTime().substring(0, 2).replace("0", "")));
                params.put("startMinute", Integer.parseInt(meal.getResvStartTime().substring(3, 5).replace("00", "0")));
                params.put("endHour", Integer.parseInt(meal.getResvEndTime().substring(0, 2).replace("0", "")));
                params.put("endMinute", Integer.parseInt(meal.getResvEndTime().substring(3, 5).replace("00", "0")));
                list1.add(params);
            }
            List week = new ArrayList();
            week.add(1);
            week.add(2);
            week.add(3);
            week.add(4);
            week.add(5);
            week.add(6);
            week.add(7);
            params1.put("weeks", week);
            params1.put("diningTime", list1);
            data.add(params1);
            Business business = businessService.selectOne(new EntityWrapper<Business>().eq("id", businessId));
            result.put("appAuthToken", business.getAppToken());
            result.put("time", data);
            list.add(result);
        }
        basicBO.setData(list);
        basicBO.setError(null);
        basicBO.setSign(createMeituanSgin(object2Map(basicBO)));
        return basicBO;
    }

    /**
     * 易订接收美团订单
     *
     * @param orderDTO 订单dto
     * @return BasicBO 基础dto
     */
    public BasicBO orderCreate(OrderDTO orderDTO) throws Exception {
        BasicBO basicBO = new BasicBO();
        ResvOrderThird resvOrderThird = new ResvOrderThird();
        MeituanOrderDTO meituanOrderDTO = JSONObject.parseObject(orderDTO.getData(), MeituanOrderDTO.class);
        MealType mealType = mealTypeService.selectOne(new EntityWrapper<MealType>().eq("business_id", orderDTO.getEPoiId())
                .ge("resv_end_time", unixTimeToDate1(meituanOrderDTO.getBookingTime())).le("resv_start_time", unixTimeToDate1(meituanOrderDTO.getBookingTime()))
                .eq("status", '1'));
        resvOrderThird.setBusinessId(Integer.parseInt(orderDTO.getEPoiId()));
        resvOrderThird.setThirdOrderNo(meituanOrderDTO.getOrderSerializedId());
        resvOrderThird.setMealTypeId(mealType.getId());
        resvOrderThird.setMealTypeName(mealType.getMealTypeName());
        resvOrderThird.setResvNum(meituanOrderDTO.getNumber());
        resvOrderThird.setResvDate(unixTimeToDate2(meituanOrderDTO.getBookingTime()));
        resvOrderThird.setTableType(meituanOrderDTO.getRequirements().getTableType());
        resvOrderThird.setCreatedAt(unixTimeToDate2(meituanOrderDTO.getCreateTime()));
        resvOrderThird.setRemark(meituanOrderDTO.getRequirements().getRemark());
        resvOrderThird.setStatus(meituanOrderDTO.getStatus());
        resvOrderThird.setTableTypeName(meituanOrderDTO.getRequirements().getTableTypeName());
        resvOrderThird.setVipName(meituanOrderDTO.getName());
        resvOrderThird.setVipPhone(meituanOrderDTO.getPhone());
        resvOrderThird.setSource("美团/大众");
        resvOrderThird.setVipSex(meituanOrderDTO.getGender() == 10 ? "女士" : "先生");

        JSONObject data = new JSONObject();
        //插入第三方订单
        boolean success = resvOrderThirdService.insert(resvOrderThird);
        Business business = businessService.selectOne(new EntityWrapper<Business>().eq("id", resvOrderThird.getBusinessId()));

        if (success) {
            boolean receiptSign = false;
            //默认为稍后接单
            data.put("result", 3);
            //默认推送自动接单内容
            JgPush jgPush = new JgPush();
            jgPush.setBusinessId(String.valueOf(resvOrderThird.getBusinessId()));
            jgPush.setMsgSeq(String.valueOf(getNextDateId("MT_ORDER")));
            jgPush.setType("PAD");
            jgPush.setUsername("13777575146");
//            String orderMsg1 = JSONUtils.toJSONString(object2Map(resvOrderThird));
            String orderMsg = JsonUtils.obj2Json(resvOrderThird).replaceAll("\r|\n", "").replaceAll("\\s*", "");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", orderMsg);
            jsonObject.put("type", "8");
            jsonObject.put("orderType", "mt");
            jgPush.setMsg(jsonObject.toString());
            if (business.getDevice() == 2) {
                //接单标识
                //1.查询酒店是否满足自动接单条件
                //查询此时空闲桌的桌位
                List<Table> tables = tableService.selectFreeTable(resvOrderThird.getBusinessId(), resvOrderThird.getResvDate(), resvOrderThird.getMealTypeId());
                receiptSign = checkBusinessReceipt(resvOrderThird, tables);
                //能自动接单进行判断
                if (receiptSign) {
                    //计算哪个桌位来接这个单
                    Table rightTable = calRightTable(tables, resvOrderThird.getResvNum());
                    //查询区域名字
                    TableArea tableArea = iTableAreaService.selectOne(new EntityWrapper<TableArea>().eq("id", rightTable.getTableAreaId()));

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String startTime = sdf.format(resvOrderThird.getResvDate()) + " " + mealType.getResvStartTime() + ":00";
                    Date date1 = format.parse(startTime);
                    String endTime = sdf.format(resvOrderThird.getResvDate()) + " " + mealType.getResvEndTime() + ":00";
                    Date date2 = format.parse(endTime);
                    Integer tableId = rightTable.getId();
                    String tableName = rightTable.getTableName();
                    Integer startTime1 = Integer.valueOf(String.valueOf(date1.getTime() / 1000));
                    Integer endTime1 = Integer.valueOf(String.valueOf(date2.getTime() / 1000));
                    ResvOrderAndroid resvOrderAndroid = new ResvOrderAndroid();
                    BeanUtils.copyProperties(resvOrderThird, resvOrderAndroid);

                    //吃饭时间 14:00 之类
                    resvOrderAndroid.setDestTime(unixTimeToDate2(meituanOrderDTO.getBookingTime()).toString().substring(11, 16));
                    resvOrderAndroid.setStatus(OrderStatus.RESERVE.code);
                    resvOrderAndroid.setBusinessName(business.getBusinessName());
                    resvOrderAndroid.setResvNum(String.valueOf(resvOrderThird.getResvNum()));
                    resvOrderAndroid.setTableAreaId(tableArea.getId());
                    resvOrderAndroid.setTableAreaName(tableArea.getTableAreaName());
                    resvOrderAndroid.setTableId(tableId);
                    resvOrderAndroid.setTableName(tableName);
                    String orderNo = IdUtils.makeOrderNo();
                    resvOrderAndroid.setResvOrder(orderNo);
                    resvOrderAndroid.setBatchNo("pc" + orderNo);
                    resvOrderAndroid.setVipSex(meituanOrderDTO.getGender() == 10 ? "女" : "男");

                    resvOrderAndroid.setExternalSourceId(2);
                    resvOrderAndroid.setExternalSourceName(resvOrderThird.getSource());

                    resvOrderAndroid.setVipId(0);
                    boolean insert = iResvOrderAndroidService.insert(resvOrderAndroid);
                    //插入订单自动接单日志
                    insertAutoAcceptLogs(resvOrderAndroid.getResvOrder());


                    if (insert) {
                        //更新批次号
                        resvOrderThird.setBatchNo("pc" + orderNo);
                        resvOrderThird.setFlag(1);
                        resvOrderThird.setStatus(40);
                        iResvOrderThirdService.update(resvOrderThird, new EntityWrapper<ResvOrderThird>().eq("third_order_no", resvOrderThird.getThirdOrderNo()));

                        //推送自动接单的第三方单号
                        rabbitTemplate.convertAndSend(QueueName.TRVIPINFO_AUTOUPDATE, meituanOrderDTO.getOrderSerializedId());

                        //自动接单
                        data.put("result", 1);
                        data.put("tableId", tableId);
                        data.put("tableName", tableName);
                        data.put("startTime", startTime1);
                        data.put("endTime", endTime1);
                    }
                }
            }
            basicBO.setData(data);
            basicBO.setError(null);
            try {
                if (business.getIsPadPush() == 1) {
                    pushFeign.pushMsg(jgPush.getType(), jgPush.getUsername(), jgPush.getMsgSeq(), jgPush.getBusinessId(), jgPush.getMsg());
                }
                //如果自动接单则不推送这个消息
                if (!receiptSign) {
                    jgPush.setType("ANDROID_PHONE");
                    pushFeign.pushMsg(jgPush.getType(), jgPush.getUsername(), jgPush.getMsgSeq(), jgPush.getBusinessId(), jgPush.getMsg());
                }

                List<Business> businessList = businessService.selectList(new EntityWrapper<Business>()
                        .eq("brand_id", business.getBrandId())
                        .eq("status", '1'));

                for (Business business1 : businessList) {
                    if (business1.getIsPcPush() == 1) {
                        jgPush.setType("WEB");
                        jgPush.setBusinessId(String.valueOf(business1.getId()));
                        log.info("pc版推送:{}", business1.getId());
                        pushFeign.pushMsg(jgPush.getType(), jgPush.getUsername(), jgPush.getMsgSeq(), jgPush.getBusinessId(), jgPush.getMsg());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            data.put("code", 30003);
            data.put("message", "缺少订单参数");
            basicBO.setData(null);
            basicBO.setError(data);
        }
        basicBO.setSign(createMeituanSgin(object2Map(basicBO)));
        return basicBO;
    }


    /**
     * 判断酒店是否能否自动接单
     *
     * @return true 成功 false 失败
     */
    private boolean checkBusinessReceipt(ResvOrderThird resvOrderThird, List<Table> tables) {

        AutoReceiptConfig autoReceiptConfig = autoReceiptConfigService.getAutoReceiptConfig(resvOrderThird.getBusinessId());
        if (null == autoReceiptConfig || autoReceiptConfig.getStatus() == 0) {
            //如果不存在配置则无法自动接单
            return false;
        }

        //酒店保留桌位数量
        int reservedTableNum = autoReceiptConfig.getReservedTableCount();
        log.info("酒店保留桌位数量:" + reservedTableNum);
        //酒店保留的桌位id
        String reservedTableIds = autoReceiptConfig.getReservedTableIds();

        String[] split;
        List<String> tableIdList;
        if (StringUtils.isNotBlank(reservedTableIds)) {
            split = reservedTableIds.split(",");
            tableIdList = Arrays.asList(split);
        } else {
            tableIdList = new ArrayList<>();
        }
        log.info("酒店保留的桌位id:" + tableIdList.toString());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //查询酒店这个餐别的正在使用的桌子数
        int useNum = iResvOrderAndroidService.selectCount(new EntityWrapper<ResvOrderAndroid>()
                .eq("meal_type_id", resvOrderThird.getMealTypeId())
                .eq("resv_date", sdf.format(resvOrderThird.getResvDate()))
                .notIn("table_id", "(" + autoReceiptConfig.getReservedTableIds() + ")"));
        log.info("查询酒店这个餐别的正在使用的桌子数:" + useNum);


        //计算这个酒店启用的桌子数量
        int allTablesNum = tableService.selectCount(new EntityWrapper<Table>()
                .eq("business_id", resvOrderThird.getBusinessId())
                .eq("status", 1));
        log.info("计算这个酒店启用的桌子数量:" + allTablesNum);


        //需要保留值
        int reserveAmount = reservedTableNum + tableIdList.size();
        log.info("需要保留值:" + reserveAmount);
        //剩余值
        int surplus = allTablesNum - useNum;
        log.info("剩余值:" + surplus);

        if (surplus <= reserveAmount) {
            log.info("剩余值小于需要保留值");
            return false;
        }

        Integer resvNum = resvOrderThird.getResvNum();
        //移除需要保留的桌位
        if (tableIdList.size() != 0 && tables.size() != 0) {
            for (int i = 0; i < tableIdList.size(); i++) {
                for (int j = 0; j < tables.size(); j++) {
                    if (tables.get(j).getId() == Integer.parseInt(tableIdList.get(i))) {
                        tables.remove(j);
                    }
                }
            }
        }

        for (Table table : tables) {
            if (Integer.parseInt(table.getMaxPeopleNum()) >= resvNum && Integer.parseInt(table.getMinPeopleNum()) <= resvNum) {
                log.info("寻找到符合桌位:" + table);
                return true;
            }
        }
        log.info("未曾寻找到符合桌位");
        return false;
    }


    /**
     * 计算最适合的table
     *
     * @param tables  最接近适合预订人数的table
     * @param resvNum 预约的人数
     * @return 返回最适合的table
     */
    private Table calRightTable(List<Table> tables, Integer resvNum) {

        //最适合的桌位的位置
        int bestTableIndex = 0;
        //最小差值
        int minDif = Integer.MAX_VALUE;
        //如果跟最大容纳人数与预订人数差值为0的话,直接返回这个桌位
        for (Table table : tables) {
            int maxPeopleNum = Integer.parseInt(table.getMaxPeopleNum());
            int minPeopleNum = Integer.parseInt(table.getMinPeopleNum());
            if (maxPeopleNum >= resvNum && minPeopleNum <= resvNum) {
                int i = maxPeopleNum - resvNum;
                if (i > 0 && i < minDif) {
                    bestTableIndex = tables.indexOf(table);
                    minDif = i;
                } else if (i == 0) {
                    return table;
                }
            }
        }
        return tables.get(bestTableIndex);
    }

    /**
     * 易订接收美团订单修改
     *
     * @param orderDTO 订单dto
     * @return OrderBO 订单bo
     */
    public OrderBO orderChange(OrderDTO orderDTO) {
        log.info("-------订单状态变更-------");
        OrderBO orderBO = new OrderBO();
        ResvOrderThird resvOrderThird = new ResvOrderThird();
        MeituanOrderUpdateDTO meituanOrderUpdateDTO = JSONObject.parseObject(orderDTO.getData(), MeituanOrderUpdateDTO.class);
        log.info("meituanOrderUpdateDTO:" + meituanOrderUpdateDTO);

        //如果是客户取消订单,则设置消息为未读
        if(meituanOrderUpdateDTO.getStatus() == 70){
            resvOrderThird.setFlag(0);
        }

        //如果订单状态为30
//        if (meituanOrderUpdateDTO.getStatus() == 30) {
//            resvOrderThird.setRemark(meituanOrderUpdateDTO.getDetail().getReason() == null ? "" : meituanOrderUpdateDTO.getDetail().getReason());
//        }

        int status = meituanOrderUpdateDTO.getStatus();
        log.info("-------订单状态为status:" + status + "-------");

        ResvOrderThird thisResvOrderThird = iResvOrderThirdService.selectOne(new EntityWrapper<ResvOrderThird>().eq("third_order_no", meituanOrderUpdateDTO.getOrderSerializedId()));
        log.info("-------1订单信息为thisResvOrderThird" + thisResvOrderThird.toString());
        if (meituanOrderUpdateDTO.getStatus() == 40) {
            log.info("-------订单状态为40-------");
            //如果两个订单表都没有关于这个批次号的订单,则将第三方订单状态设置为45 已经接单未曾选座
            List<ResvOrderAndroid> androidOrders = iResvOrderAndroidService.selectList(new EntityWrapper<ResvOrderAndroid>().eq("third_order_no", meituanOrderUpdateDTO.getOrderSerializedId()));
            List<ResvOrder> orders = iResvOrderService.selectList(new EntityWrapper<ResvOrder>().eq("third_order_no", meituanOrderUpdateDTO.getOrderSerializedId()));
            //如果两个订单表中均无此第三方订单号的订单
            if (androidOrders.size() == 0 && orders.size() == 0) {
                //45代表已经接单但是未选座状态
                log.info("-------订单状态为45-------");
                status = 45;
            }
        }
        //设置第三方订单状态
        resvOrderThird.setStatus(status);
        boolean isUpdate = resvOrderThirdService.update(resvOrderThird, new EntityWrapper<ResvOrderThird>().eq("third_order_no", meituanOrderUpdateDTO.getOrderSerializedId()));

        log.info("-------2订单信息为thisResvOrderThird:" + thisResvOrderThird.toString() + "-------");
        Business business = businessService.selectOne(new EntityWrapper<Business>().eq("id", thisResvOrderThird.getBusinessId()));
        //如果第三方订单为状态40 的时候且为 安卓电话机,更新订单表第三方订单表信息
        if (isUpdate && meituanOrderUpdateDTO.getStatus() == 40 && business.getDevice() == 2) {
            updateOrderAndVipInfo(meituanOrderUpdateDTO.getOrderSerializedId());
        }

        if (isUpdate) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "8");
            jsonObject.put("orderType", "mt");

            ResvOrderThird resvOrderThird1 = resvOrderThirdService.selectOne(new EntityWrapper<ResvOrderThird>().eq("third_order_no", meituanOrderUpdateDTO.getOrderSerializedId()));
            if (meituanOrderUpdateDTO.getStatus() == 70) {
                if (StringUtils.isNotBlank(resvOrderThird1.getBatchNo())) {
                    ResvOrderAndroid resvOrder = new ResvOrderAndroid();
                    resvOrder.setStatus("4");
                    resvOrder.setUnorderReason("顾客取消");

                    ResvOrder commonResvOrder = new ResvOrder();
                    commonResvOrder.setStatus("4");
                    commonResvOrder.setUnorderReason("顾客取消");

                    iResvOrderAndroidService.update(resvOrder, new EntityWrapper<ResvOrderAndroid>().eq("batch_no", resvOrderThird1.getBatchNo()));
                    iResvOrderService.update(commonResvOrder, new EntityWrapper<ResvOrder>().eq("batch_no", resvOrderThird1.getBatchNo()));

                    List<ResvOrderAndroid> resvOrders = iResvOrderAndroidService.selectList(new EntityWrapper<ResvOrderAndroid>().eq("batch_no", resvOrderThird1.getBatchNo()));
                    if (resvOrders.size() == 0) {
                        List<ResvOrder> commonResvOrders = iResvOrderService.selectList(new EntityWrapper<ResvOrder>().eq("batch_no", resvOrderThird1.getBatchNo()));
                        for (ResvOrder resvOrder1 : commonResvOrders) {
                            ResvOrderAndroid resvOrderAndroid = new ResvOrderAndroid();
                            BeanUtils.copyProperties(resvOrder1, resvOrderAndroid);
                            resvOrders.add(resvOrderAndroid);
                        }
                    }

                    for (ResvOrderAndroid resvOrder1 : resvOrders) {
                        DeviceOrderNotice deviceOrderNotice = new DeviceOrderNotice();
                        BeanUtils.copyProperties(resvOrder1, deviceOrderNotice);
                        deviceOrderNotice.setType("2");
                        deviceOrderNotice.setCreatedAt(new Date());
                        deviceOrderNotice.setRemark("顾客取消新美大订单");
                        deviceOrderNotice.setOperater("客户");
                        deviceOrderNotice.setAppUserName("客户");
                        deviceOrderNoticeService.insert(deviceOrderNotice);
                    }
                }
                BeanUtils.copyProperties(resvOrderThird1, resvOrderThird);
                resvOrderThird.setRemark("顾客取消新美大订单");
                jsonObject.put("type", "7");
            }
            orderBO.setData("OK");
            orderBO.setError(null);


            JgPush jgPush = new JgPush();
            jgPush.setBusinessId(orderDTO.getEPoiId());
            jgPush.setMsgSeq(String.valueOf(getNextDateId("MT_ORDER")));
            jgPush.setType("PAD");
            jgPush.setUsername("13777575146");
//            String orderMsg = JSONUtils.toJSONString(object2Map(resvOrderThird));
            String orderMsg = JsonUtils.obj2Json(resvOrderThird).replaceAll("\r|\n", "").replaceAll("\\s*", "");
            jsonObject.put("data", orderMsg);
            jgPush.setMsg(jsonObject.toString());

            try {
                pushFeign.pushMsg(jgPush.getType(), jgPush.getUsername(), jgPush.getMsgSeq(), jgPush.getBusinessId(), jgPush.getMsg());
                jgPush.setType("ANDROID_PHONE");
                pushFeign.pushMsg(jgPush.getType(), jgPush.getUsername(), jgPush.getMsgSeq(), jgPush.getBusinessId(), jgPush.getMsg());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            orderBO.setData(null);
            JSONObject error = new JSONObject();
            error.put("code", 40003);
            error.put("message", "订单状态已经改变，不允许再次修改");
            orderBO.setError(error);
        }
        orderBO.setSign(createMeituanSgin(object2Map(orderBO)));
        return orderBO;
    }


    /**
     * 订单成功返回后更新客户表与订单表
     *
     * @param orderSerializedId 第三方订单号码
     */
    public void updateOrderAndVipInfo(String orderSerializedId) {
        ResvOrderThird thisResvOrderThird = iResvOrderThirdService.selectOne(new EntityWrapper<ResvOrderThird>().eq("third_order_no", orderSerializedId));

        OrderQueryBO orderQueryBO = meituanService.orderQuery(thisResvOrderThird.getBusinessId(), thisResvOrderThird.getBatchNo(), thisResvOrderThird.getThirdOrderNo());
        JSONObject data = (JSONObject) orderQueryBO.getData();
        String phone = data.get("phone").toString();
        thisResvOrderThird.setVipPhone(phone);
        thisResvOrderThird.setFlag(0);

        //建立或者更新客户信息
        Vip vip = new Vip();
        //去除订单信息中的tag 与 remark
        vip.setVipPhone(phone);
        vip.setBusinessId(thisResvOrderThird.getBusinessId());
        vip.setVipName(thisResvOrderThird.getVipName());
        vip.setVipSex(thisResvOrderThird.getVipSex().equals("先生") ? "男" : "女");
        vipService.updateOrInsertVip(vip);
        Vip basicVipInfo = vipService.getBasicVipInfo(thisResvOrderThird.getBusinessId(), phone);
        ResvOrderAndroid resvOrder = new ResvOrderAndroid();
        resvOrder.setVipId(basicVipInfo.getId());
        resvOrder.setVipPhone(basicVipInfo.getVipPhone());
        //更新订单表中的信息
        iResvOrderAndroidService.update(resvOrder, new EntityWrapper<ResvOrderAndroid>()
                .eq("third_order_no", thisResvOrderThird.getThirdOrderNo()));
        //更新第三方订单表信息 手机号
        iResvOrderThirdService.update(thisResvOrderThird, new EntityWrapper<ResvOrderThird>().
                eq("third_order_no", thisResvOrderThird.getThirdOrderNo()));
    }

    /**
     * 口碑订单接收
     *
     * @return 成功失败标志
     */
    public Tip kbOrderCreate(KbOrderDTO kbOrderDTO) {
        ResvOrderThird resvOrderThird = new ResvOrderThird();
        BeanUtils.copyProperties(kbOrderDTO, resvOrderThird);
        MealType mealType = mealTypeService.selectOne(new EntityWrapper<MealType>().eq("business_id", kbOrderDTO.getBusinessId())
                .ge("resv_end_time", new SimpleDateFormat("HH:mm").format(kbOrderDTO.getResvDate())).le("resv_start_time", new SimpleDateFormat("HH:mm").format(kbOrderDTO.getResvDate()))
                .eq("status", '1'));
        resvOrderThird.setThirdOrderNo("KB" + System.currentTimeMillis());
        if (mealType != null) {
            resvOrderThird.setMealTypeId(mealType.getId());
            resvOrderThird.setMealTypeName(mealType.getMealTypeName());
        }
        resvOrderThird.setCreatedAt(new Date());
        resvOrderThird.setSource("口碑");
        resvOrderThird.setStatus(10);
        resvOrderThird.setResult(0);
        resvOrderThird.setFlag(0);
        boolean success = resvOrderThirdService.insert(resvOrderThird);
        if (success) {
            JgPush jgPush = new JgPush();
            jgPush.setBusinessId(String.valueOf(kbOrderDTO.getBusinessId()));
            jgPush.setMsgSeq(String.valueOf(getNextDateId("MT_ORDER")));
            jgPush.setType("PAD");
            jgPush.setUsername("13777575146");
//            String orderMsg1 = JSONUtils.toJSONString(object2Map(resvOrderThird));
            String orderMsg = JsonUtils.obj2Json(resvOrderThird).replaceAll("[\r\n]", "").replaceAll("\\s*", "");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", orderMsg);
            jsonObject.put("type", "8");
            jgPush.setMsg(jsonObject.toString());
            try {
                pushFeign.pushMsg(jgPush.getType(), jgPush.getUsername(), jgPush.getMsgSeq(), jgPush.getBusinessId(), jgPush.getMsg());
                jgPush.setType("ANDROID_PHONE");
                pushFeign.pushMsg(jgPush.getType(), jgPush.getUsername(), jgPush.getMsgSeq(), jgPush.getBusinessId(), jgPush.getMsg());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new SuccessTip();
        } else {
            return new ErrorTip();
        }
    }


    /**
     * 微信公众号接收
     */
    public boolean PACreatOrder(PublicOrderDTO publicOrderDTO) {

        //获得餐别
        MealType mealType = mealTypeService.selectOne(new EntityWrapper<MealType>().eq("business_id", publicOrderDTO.getBusinessId())
                .ge("resv_end_time", unixTimeToDate3(publicOrderDTO.getResvDate().getTime()))
                .le("resv_start_time", unixTimeToDate3(publicOrderDTO.getResvDate().getTime()))
                .eq("status", '1'));

        if (mealType == null) {
            log.error("该时间段没有餐别,入库失败");
            return false;
        }

        ResvOrderThird resvOrderThird = new ResvOrderThird();
        resvOrderThird.setBusinessId(publicOrderDTO.getBusinessId());
        resvOrderThird.setResvNum(publicOrderDTO.getNumber());
        resvOrderThird.setResvDate(publicOrderDTO.getResvDate());
        resvOrderThird.setTableType(publicOrderDTO.getTableType());
        resvOrderThird.setCreatedAt(new Date());
        resvOrderThird.setStatus(publicOrderDTO.getStatus());
        resvOrderThird.setTableTypeName(publicOrderDTO.getTableTypeName());
        resvOrderThird.setVipName(publicOrderDTO.getName());
        resvOrderThird.setVipPhone(publicOrderDTO.getPhone());
        resvOrderThird.setSource("易订公众号");
        resvOrderThird.setVipSex(publicOrderDTO.getGender() == 10 ? "先生" : "女士");
        resvOrderThird.setMealTypeId(mealType.getId());
        resvOrderThird.setMealTypeName(mealType.getMealTypeName());
        resvOrderThird.setOpenId(publicOrderDTO.getOpenId());
        resvOrderThird.setRemark(publicOrderDTO.getRemark());
        //生成订单号
        resvOrderThird.setThirdOrderNo(IdUtils.makeOrderNo());

        boolean success = resvOrderThirdService.insert(resvOrderThird);

        if (success) {
            Business business = businessService.selectOne(new EntityWrapper<Business>().eq("id", resvOrderThird.getBusinessId()));
            autoAcceptOrder(resvOrderThird, business);


        } else {
            log.error("自动接单失败");
            return false;
        }
        return true;
    }


    /**
     * 公众号自动接单
     *
     * @param resvOrderThird 第三方订单
     * @param business       酒店
     */
    @Transactional
    public void autoAcceptOrder(ResvOrderThird resvOrderThird, Business business) {

        //默认为稍后接单
        //默认推送自动接单内容
        //接单标识
        //1.查询酒店是否满足自动接单条件
        //查询此时空闲桌的桌位
        List<Table> tables = tableService.selectFreeTable(resvOrderThird.getBusinessId(), resvOrderThird.getResvDate(), resvOrderThird.getMealTypeId());
        boolean receiptSign = checkBusinessReceipt(resvOrderThird, tables);

        //能自动接单进行判断
        //推送自动接单的第三方单号
        JgPush jgPush = new JgPush();
        jgPush.setUsername("13777575146");
        //自动接单推送内容
        jgPush.setMsgSeq(String.valueOf(getNextDateId("YD_ORDER")));
        jgPush.setType("ANDROID_PHONE");
        jgPush.setBusinessId(resvOrderThird.getBusinessId().toString());

        if (receiptSign) {
            //计算哪个桌位来接这个单
            Table rightTable = calRightTable(tables, resvOrderThird.getResvNum());
            Integer tableId = rightTable.getId();
            String tableName = rightTable.getTableName();
            //查询区域名字
            TableArea tableArea = iTableAreaService.selectOne(new EntityWrapper<TableArea>()
                    .eq("id", rightTable.getTableAreaId()));

            //查询客户,没有就插入新客户
            Vip vip1 = iVipService.selectOne(new EntityWrapper<Vip>()
                    .eq("business_id", resvOrderThird.getBusinessId())
                    .eq("vip_phone", resvOrderThird.getVipPhone()));

            if (vip1 == null) {

                Vip vip = new Vip();
                vip.setVipPhone(resvOrderThird.getVipPhone());
                vip.setBusinessId(business.getId());
                vip.setBusinessName(business.getBusinessName());
                vip.setVipName(resvOrderThird.getVipName());
                vip.setVipSex(resvOrderThird.getVipSex().equals("先生") ? "男" : "女");
                iVipService.insert(vip);
                log.info(vip.toString());
                vip1 = vip;
            }


            ResvOrderAndroid resvOrderAndroid = new ResvOrderAndroid();
            BeanUtils.copyProperties(resvOrderThird, resvOrderAndroid);
            //吃饭时间 14:00 之类
            resvOrderAndroid.setDestTime(unixTimeToDate3(resvOrderThird.getResvDate().getTime()));
            resvOrderAndroid.setStatus(OrderStatus.RESERVE.code);
            resvOrderAndroid.setBusinessName(business.getBusinessName());
            resvOrderAndroid.setResvNum(String.valueOf(resvOrderThird.getResvNum()));
            resvOrderAndroid.setTableAreaId(tableArea.getId());
            resvOrderAndroid.setTableAreaName(tableArea.getTableAreaName());
            resvOrderAndroid.setTableId(tableId);
            resvOrderAndroid.setTableName(tableName);
            String orderNo = IdUtils.makeOrderNo();
            //增加ExternalSourceName  ID
            resvOrderAndroid.setExternalSourceId(4);
            resvOrderAndroid.setExternalSourceName(resvOrderThird.getSource());
            resvOrderAndroid.setResvOrder(orderNo);
            resvOrderAndroid.setBatchNo("pc" + orderNo);
            resvOrderAndroid.setVipSex(resvOrderThird.getVipSex().equals("先生") ? "男" : "女");
            resvOrderAndroid.setVipId(vip1.getId());


            boolean insert = iResvOrderAndroidService.insert(resvOrderAndroid);
            //插入订单自动接单日志
            insertAutoAcceptLogs(resvOrderAndroid.getResvOrder());


            if (insert) {
                //更新批次号
                resvOrderThird.setBatchNo("pc" + orderNo);
                resvOrderThird.setFlag(1);
                resvOrderThird.setStatus(40);
                iResvOrderThirdService.update(resvOrderThird, new EntityWrapper<ResvOrderThird>().eq("third_order_no", resvOrderThird.getThirdOrderNo()));

                //推送消息
                String orderMsg1 = JsonUtils.obj2Json(resvOrderAndroid).replaceAll("[\r\n]", "").replaceAll("\\s*", "");
                jgPush.setBusinessId(String.valueOf(resvOrderAndroid.getBusinessId()));
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("data", orderMsg1);
                jsonObject1.put("type", "10");
                jsonObject1.put("orderType", "yd");
                jgPush.setMsg(jsonObject1.toString());

                //推送给安卓电话机用户
                log.info("自动接单完成,推送给安卓电话机用户.");
                pushFeign.pushMsg(jgPush.getType(), jgPush.getUsername(), jgPush.getMsgSeq(), jgPush.getBusinessId(), jgPush.getMsg());

                //推送接单成功给微信客户
                log.info("自动接单完成,推送接单成功给微信客户.");
                wechatPushMes(resvOrderAndroid, resvOrderThird, OrderTemplate.ORDER_RESV_SUCCESS, business);

            }
        }
        //如果自动接单则不推送这个消息
        if (!receiptSign) {

            //推送消息
            JSONObject jsonObject = new JSONObject();
            String orderMsg = JsonUtils.obj2Json(resvOrderThird).replaceAll("[\r\n]", "").replaceAll("\\s*", "");
            jsonObject.put("data", orderMsg);
            jsonObject.put("type", "8");
            jgPush.setMsg(jsonObject.toString());

            pushFeign.pushMsg(jgPush.getType(), jgPush.getUsername(), jgPush.getMsgSeq(), jgPush.getBusinessId(), jgPush.getMsg());
        }
    }


    /**
     * apptoken回调接口
     *
     * @param callBackDTO 回调dto
     * @return boolean
     */
    public boolean callBackToken(CallBackDTO callBackDTO) {
        log.warn("apptoken:{}", callBackDTO.getAppAuthToken());
        Business business = new Business();
        business.setAppToken(callBackDTO.getAppAuthToken());
        return businessService.update(business, new EntityWrapper<Business>().eq("id", callBackDTO.getEPoiId()));
    }

    /**
     * apptoken解绑回调接口
     *
     * @param callBackDTO 回调dto
     * @return boolean
     */
    public boolean callBackUnToken(CallBackDTO callBackDTO) {
        Business business = new Business();
        business.setAppToken("");
        return businessService.update(business, new EntityWrapper<Business>().eq("id", callBackDTO.getEPoiId()));
    }

    /**
     * 请求校验失败返回结果
     *
     * @param code    code
     * @param message 信息
     * @return BasicBO
     */
    public BasicBO failSign(Integer code, String message) {
        BasicBO basicBO = new BasicBO();
        basicBO.setData(null);
        JSONObject error = new JSONObject();
        error.put("code", code);
        error.put("message", message);
        basicBO.setError(error);
        basicBO.setSign(createMeituanSgin(object2Map(basicBO)));
        return basicBO;
    }

    /**
     * 校验美团签名
     *
     * @param params 参数
     * @return boolean
     */
    public boolean verifySignature(Map<String, Object> params) {
        return SignUtil.verifySignature(params, MeituanMethod.SIGNKEY);
    }


    /**
     * 自动接单插入订单日志
     *
     * @param orderNo 订单id
     */
    private void insertAutoAcceptLogs(String orderNo) {
        //订单日志记录
        ResvOrderLogs resvOrderLogs = new ResvOrderLogs();

        String log = "订单预订成功-安卓电话机(系统自动接单)";
        //将订单操作日志插入订单日志表
        resvOrderLogs.setResvOrder(orderNo);
        resvOrderLogs.setCreatedAt(new Date());
        resvOrderLogs.setStatus("1");
        resvOrderLogs.setStatusName("已预定");
        resvOrderLogs.setLogs(log);
        iResvOrderLogsService.insert(resvOrderLogs);
    }


    /**
     * 生成美团签名
     *
     * @param params 参数
     * @return String
     */
    private static String createMeituanSgin(Map<String, Object> params) {
        return SignUtil.calcSign(params, MeituanMethod.SIGNKEY);
    }


    /**
     * 实体对象转成Map
     *
     * @param obj 实体对象
     * @return
     */
    private static Map<String, Object> object2Map(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 时间戳转换yyyy-MM-dd
     *
     * @param unixTime 时间戳
     * @return yyyy-MM-dd
     * @throws ParseException 转化异常
     */
    private static Date unixTimeToDate(int unixTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Long time = (long) unixTime * 1000;
        String d = format.format(time);
        return format.parse(d);
    }

    /**
     * 时间戳转换yyyy-MM-dd HH:mm:ss
     *
     * @param unixTime 时间戳
     * @return yyyy-MM-dd HH:mm:ss
     * @throws ParseException 转化异常
     */
    private static Date unixTimeToDate2(int unixTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long time = (long) unixTime * 1000;
        String d = format.format(time);
        return format.parse(d);
    }

    /**
     * 时间戳转换HH:mm
     *
     * @param unixTime 时间戳
     * @return 返回HH:mm
     * @throws ParseException 转化异常
     */
    private static String unixTimeToDate1(int unixTime) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Long time = (long) unixTime * 1000;
        String d = format.format(time);
        return d;
    }

    /**
     * Long 时间戳转换HH:mm
     *
     * @param unixTime 时间戳
     * @return HH:mm
     */
    private static String unixTimeToDate3(Long unixTime) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String d = format.format(unixTime);
        return d;
    }

    /**
     * Long时间戳转换yyyy-MM-dd HH:mm:ss
     *
     * @param unixTime 时间戳
     * @return 返回日期
     * @throws ParseException 日期转换异常
     */
    public static Date unixTimeToDate4(Long unixTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format(unixTime);
        return format.parse(d);
    }

    /**
     * 获取序列
     *
     * @param type 类型
     * @return 序列
     */
    public long getNextDateId(String type) {
        String todayStr = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMdd");//历史遗留Key暂不考虑，一天也就一个。
        long l2 = redisTemplate.opsForValue().increment("PUSH:" + type + ":" + todayStr, 1);
        String s1 = StringUtils.leftPad("" + l2, 7, "0");
        return Long.parseLong(todayStr + s1);
    }

    /**
     * 根据口碑门店id获取酒店信息
     *
     * @param shopId      门店id
     * @param merchantPid pid
     * @return 酒店信息
     */
    public BusinessBo getBusinessInfo(String shopId, String merchantPid) {
        Business business = businessService.selectOne(new EntityWrapper<Business>().eq("shop_id", shopId).eq("merchant_pid", merchantPid).eq("status", '1'));
        BusinessBo businessBo = null;
        if (business != null) {
            businessBo.setBusinessId(business.getId());
            businessBo.setBusinessName(business.getBusinessName());
        }
        return businessBo;
    }

    /**
     * 客户取消订单
     *
     * @param thirdOrderId 第三方订单号
     * @return 操作结果
     */
    public Tip PAOrderUpdate(String thirdOrderId) {

        //客户取消订单
        ResvOrderThird resvOrderThird = iResvOrderThirdService.selectOne(new EntityWrapper<ResvOrderThird>()
                .eq("third_order_no", thirdOrderId));


        Integer result = resvOrderThird.getResult();
        //如果是未处理或者已经接单取消订单
        if (result == 0 || result == 1) {

            //修改第三方订单状态,设置为客户拒单
            resvOrderThird.setStatus(70);
            resvOrderThird.setRemark("客户取消易订公众号订单");
            iResvOrderThirdService.updateById(resvOrderThird);

            
            //根据有无订单是否修改订单状态
            List<ResvOrderAndroid> orderAndroidList = iResvOrderAndroidService.selectList(new EntityWrapper<ResvOrderAndroid>()
                    .eq("third_order_no", thirdOrderId));


            ArrayList<ResvOrderLogs> resvOrderLogsList = new ArrayList<>();

            //如果存在订单,就更新订单信息
            if (orderAndroidList.size() != 0) {
                for (ResvOrderAndroid resvOrderAndroid : orderAndroidList) {

                    //退订
                    resvOrderAndroid.setStatus("4");
                    resvOrderAndroid.setRemark("顾客取消易订订单");

                    ResvOrderLogs resvOrderLogs = new ResvOrderLogs();
                    String log = "变更订单状态为退订-安卓电话机(系统)";
                    //将订单操作日志插入订单日志表
                    resvOrderLogs.setResvOrder(resvOrderAndroid.getResvOrder());
                    resvOrderLogs.setCreatedAt(new Date());
                    resvOrderLogs.setStatus("4");
                    resvOrderLogs.setStatusName("已退订");
                    resvOrderLogs.setLogs(log);

                    resvOrderLogsList.add(resvOrderLogs);
                }

                //批量更新
                iResvOrderAndroidService.updateBatchById(orderAndroidList);

                //批量插入订单日志
                iResvOrderLogsService.insertBatch(resvOrderLogsList);

            }

            //推送商家
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "7");

            //分类推送信息类型 yd公众号 和 美团
            jsonObject.put("orderType", "yd");
            JgPush jgPush = new JgPush();
            jgPush.setBusinessId(resvOrderThird.getBusinessId().toString());
            jgPush.setMsgSeq(String.valueOf(getNextDateId("YD_ORDER")));
            jgPush.setUsername("13777575146");
            String orderMsg = JsonUtils.obj2Json(resvOrderThird).replaceAll("[\r\n]", "").replaceAll("\\s*", "");
            jsonObject.put("data", orderMsg);
            jgPush.setType("ANDROID_PHONE");
            jgPush.setMsg(jsonObject.toString());
            pushFeign.pushMsg(jgPush.getType(), jgPush.getUsername(), jgPush.getMsgSeq(), jgPush.getBusinessId(), jgPush.getMsg());


            return SuccessTip.SUCCESS_TIP;

        } else {

            //提示商家已经拒单
            ErrorTip errorTip = new ErrorTip();
            errorTip.setCode(500);
            errorTip.setMsg("商家已经拒单");

            return errorTip;
        }
    }


    /**
     * 微信推送
     *
     * @param resvOrderAndroid 安卓电话机订单
     * @param resvOrderThird   第三方订单信息
     * @param orderTemplate    模板
     * @param business         酒店信息
     */
    public static void wechatPushMes(ResvOrderAndroid resvOrderAndroid, ResvOrderThird resvOrderThird, OrderTemplate orderTemplate, Business business) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        PushMessageVO pushMessageVO = new PushMessageVO();
        pushMessageVO.setDate(sdf.format(resvOrderThird.getResvDate()));
        pushMessageVO.setName(resvOrderThird.getVipName());
        pushMessageVO.setOpenId(resvOrderThird.getOpenId());
        pushMessageVO.setPersonNum(resvOrderThird.getResvNum());
        pushMessageVO.setPhone(resvOrderThird.getVipPhone());
        pushMessageVO.setSex(resvOrderThird.getVipSex().equals("先生") ? "1" : "0");
        pushMessageVO.setOrderTemplate(orderTemplate);

        pushMessageVO.setBusinessName(business.getBusinessName());
        pushMessageVO.setTableType(resvOrderThird.getTableTypeName());
        pushMessageVO.setTableArea(resvOrderAndroid.getTableAreaName() + " " + resvOrderAndroid.getTableName());
        pushMessageVO.setDesc(resvOrderThird.getRemark());
        pushMessageVO.setBusinessAddr(business.getBusinessAddress());

        log.info("调用微信推送:" + pushMessageVO.toString());

        WeChatUtils.pushMessage(
                pushMessageVO.getOpenId(),
                pushMessageVO.getOrderTemplate().getCode(),
                "http://eding.zhidianfan.com/#/OrderDetail?id=" + resvOrderThird.getThirdOrderNo(),
                WeChatUtils.getMessageContent(pushMessageVO));
    }

}
