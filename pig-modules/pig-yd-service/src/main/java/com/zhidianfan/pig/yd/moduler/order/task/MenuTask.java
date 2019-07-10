package com.zhidianfan.pig.yd.moduler.order.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.service.*;
import com.zhidianfan.pig.yd.moduler.order.bo.InfoBO;
import com.zhidianfan.pig.yd.moduler.order.bo.PosPluCodesBO;
import com.zhidianfan.pig.yd.moduler.order.bo.PosPlusBO;
import com.zhidianfan.pig.yd.moduler.order.bo.PosSortsBO;
import com.zhidianfan.pig.yd.moduler.order.service.XopService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author danda
 */
//@Component
@Slf4j
@ConditionalOnProperty(name = "yd.xms.task", havingValue = "true")
public class MenuTask {
    /**
     * 西软业务逻辑类
     */
    @Autowired
    private XopService xopService;
    /**
     * 西软接口信息数据接口
     */
    @Autowired
    private IXmsBusinessService xmsBusinessService;
    /**
     * 菜目数据接口
     */
    @Autowired
    private IDishCmService dishCmService;
    /**
     * 菜品大类数据接口
     */
    @Autowired
    private IDishDlService dishDlService;
    /**
     * 菜目小类数据接口
     */
    @Autowired
    private IDishXlService dishXlService;
    /**
     * 第三方订单数据接口
     */
    @Autowired
    private IResvOrderTemService resvOrderTemService;
    /**
     * 订单数据接口
     */
    @Autowired
    private IResvOrderService resvOrderService;
    /**
     * 第三方菜目数据接口
     */
    @Autowired
    private IResvDishThirdService resvDishThirdService;
    @Autowired
    private IMealTypeService mealTypeService;
    /**
     * 实时报表数据接口
     */
    @Autowired
    private IBillMxSyncService billMxSyncService;
    /**
     * 报表数据接口
     */
    @Autowired
    private IBillSyncService billSyncService;
    /**
     * 桌位数据接口
     */
    @Autowired
    private ITableService tableService;
    @Autowired
    private IBusinessService businessService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void update() {
        int currentPage = 1;
        int pageSize = 1000;
        while (true) {
            Page<XmsBusiness> page = new Page<>(currentPage, pageSize);
            Page<XmsBusiness> xmsBusinessPage = xmsBusinessService.selectPage(page);
            xmsBusinessPage.getRecords()
                    .parallelStream()
                    .forEach(xmsBusiness -> {
                        int businessId = xmsBusiness.getBusinessId();
                        //更新酒店大类
                        updateBigCategory(businessId);
                        //更新酒店小类
                        updateSmallCategory(businessId);
                        //更新菜品
                        updateDish(businessId);
                    });
            if (!xmsBusinessPage.hasNext()) {
                break;
            }
            currentPage++;
        }

    }

//    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    public void updateMenuOrder() {
        int currentPage = 1;
        int pageSize = 1000;
        while (true) {
            Page<XmsBusiness> page = new Page<>(currentPage, pageSize);
            Page<XmsBusiness> xmsBusinessPage = xmsBusinessService.selectPage(page,new EntityWrapper<XmsBusiness>().eq("business_id",965));
            for (XmsBusiness xmsBusiness : xmsBusinessPage.getRecords()) {
                int businessId = xmsBusiness.getBusinessId();
                Business business = businessService.selectById(businessId);
                InfoBO infoBO = xopService.posInfo(businessId);
                if(infoBO == null || !infoBO.isSuccess()){
                    return;
                }
                infoBO.getResults().forEach(result -> {
                    String thirdOrderNo = result.get("menu");
                    ResvOrderTem resvOrderTem = resvOrderTemService.selectOne(new EntityWrapper<ResvOrderTem>().eq("third_order_no", thirdOrderNo));
                    ResvOrder resvOrder = null;
                    if (resvOrderTem != null) {
                        resvOrder = resvOrderService.selectOne(new EntityWrapper<ResvOrder>().eq("resv_order", resvOrderTem.getResvOrder()));
                    } else {
                        resvOrder = resvOrderService.selectOne(new EntityWrapper<ResvOrder>().eq("business_id", businessId).eq("third_order_no", thirdOrderNo));
                    }
                    if (resvOrder == null) {
                        log.info("订单不存在");
                        return;
                    }
                    String tableNo = result.get("tableno");
                    BillSync billSync = billSyncService.selectOne(new EntityWrapper<BillSync>().eq("business_id", businessId).eq("zdbh", thirdOrderNo));
                    if (billSync == null) {
                        Table table = tableService.selectOne(new EntityWrapper<Table>().eq("business_id", businessId).eq("table_code", tableNo));
                        billSync = new BillSync();
                        billSync.setBusinessId(businessId);
                        billSync.setBusinessName(business.getBusinessName());
                        billSync.setAreaCode(table.getAreaCode());
                        billSync.setTableCode(table.getTableCode());
                        int shift = Integer.valueOf(result.get("shift"));
                        String mealName = null;
                        if (shift == 2) {
                            MealType mealType = mealTypeService.selectOne(new EntityWrapper<MealType>().eq("business_id",businessId).like("meal_type_name", "中"));
                            mealName = StringUtils.isBlank(mealType.getMealTypeName()) ? "中餐" : mealType.getMealTypeName();
                        } else {
                            MealType mealType = mealTypeService.selectOne(new EntityWrapper<MealType>().eq("business_id",businessId).like("meal_type_name", "晚"));
                            mealName = StringUtils.isBlank(mealType.getMealTypeName()) ? "晚餐" : mealType.getMealTypeName();
                        }
                        billSync.setBbbc(mealName);

                        billSync.setCreatedAt(new Date());
                    }
                    String amount = result.get("amount");
                    billSync.setSjje(amount);
                    billSync.setBbrq(new Date());
                    billSyncService.insertOrUpdate(billSync);
                    String batchNo = resvOrder.getBatchNo();
                    String orderNo = resvOrder.getResvOrder();
                    List<ResvDishThird> resvDishThirdList = Lists.newArrayList();
                    List<BillMxSync> billMxSyncList = Lists.newArrayList();
                    JSONObject.parseArray(result.get("dishs")).forEach(obj -> {
                        JSONObject jsonObject = (JSONObject) obj;
                        String code = jsonObject.getString("code");
                        //第三方订单
                        ResvDishThird resvDishThird = resvDishThirdService.selectOne(new EntityWrapper<ResvDishThird>().eq("batch_no", batchNo).eq("resv_order",orderNo).eq("cmbh", code));
                        if (resvDishThird == null) {
                            resvDishThird = new ResvDishThird();
                            resvDishThird.setBatchNo(batchNo);
                            resvDishThird.setResvOrder(orderNo);
                            resvDishThird.setCmbh(code);
                            resvDishThird.setCreatedAt(new Date());
                        }
                        resvDishThird.setDishName(jsonObject.getString("descript"));
                        resvDishThird.setDishNum(Double.valueOf(jsonObject.getString("number")));
                        resvDishThird.setDishPrice(BigDecimal.valueOf(Double.valueOf(jsonObject.getString("amount"))));
                        resvDishThird.setDwmc(jsonObject.getString("unit"));
                        resvDishThirdList.add(resvDishThird);
                        //报表
                        BillMxSync billMxSync = billMxSyncService.selectOne(new EntityWrapper<BillMxSync>().eq("business_id", businessId).eq("zdbh", thirdOrderNo).eq("cmbh", code));
                        if (billMxSync == null) {
                            billMxSync = new BillMxSync();
                            billMxSync.setBusinessId(businessId);
                            billMxSync.setBusinessName(business.getBusinessName());
                            billMxSync.setZdbh(thirdOrderNo);
                            billMxSync.setCmbh(code);
                            billMxSync.setCreatedAt(new Date());
                        }
                        billMxSync.setCmsl(Integer.valueOf(jsonObject.getString("number")));
                        billMxSync.setCmmc(jsonObject.getString("descript"));
                        billMxSync.setSjje(Double.valueOf(jsonObject.getString("amount")));
                        billMxSyncList.add(billMxSync);
                    });
                    resvDishThirdService.insertOrUpdateBatch(resvDishThirdList);
                    billMxSyncService.insertOrUpdateBatch(billMxSyncList);
                });
            }
            if (!xmsBusinessPage.hasNext()) {
                break;
            }
            currentPage++;
        }

    }

    private void updateBigCategory(Integer businessId) {
        PosPluCodesBO posPluCodesBO = xopService.plucodes(businessId);
        if (posPluCodesBO != null && posPluCodesBO.getResults().size() != 0) {
            List<DishDl> dishDlList = Lists.newArrayList();
            posPluCodesBO.getResults().forEach(map -> {
                String dlbh = map.get("plucode");
                DishDl dishDl = dishDlService.selectOne(new EntityWrapper<DishDl>().eq("dlbh", dlbh).eq("business_id", businessId));
                if (dishDl == null) {
                    dishDl = new DishDl();
                    dishDl.setBusinessId(businessId);
                    dishDl.setCreatedAt(new Date());
                }
                dishDl.setDlbh(map.get("plucode"));
                dishDl.setDlmc(map.get("descript"));
                dishDl.setStatus("1");
                dishDl.setUpdatedAt(new Date());
                dishDlList.add(dishDl);
            });
            if (dishDlList.size() > 0) {
                dishDlService.insertOrUpdateBatch(dishDlList);
            }
        }
    }

    private void updateSmallCategory(Integer businessId) {
        PosSortsBO posSortsBO = xopService.posSorts(businessId);
        if (posSortsBO != null && posSortsBO.getResults().size() != 0) {
            List<DishXl> dishXlList = Lists.newArrayList();
            posSortsBO.getResults().forEach(map -> {
                String sort = map.get("sort");
                DishXl dishXl = dishXlService.selectOne(new EntityWrapper<DishXl>().eq("xlbh", sort).eq("business_id", businessId));
                if (dishXl == null) {
                    dishXl = new DishXl();
                    dishXl.setBusinessId(businessId);
                    dishXl.setCreatedAt(new Date());
                }
                dishXl.setXlbh(sort);
                dishXl.setXlmc(map.get("descript"));
                dishXl.setDlbh(map.get("plucode"));
                dishXl.setStatus("1");
                dishXl.setUpdatedAt(new Date());
                dishXlList.add(dishXl);
            });
            dishXlService.insertOrUpdateBatch(dishXlList);
        }
    }

    private void updateDish(Integer businessId) {
        PosPlusBO posPlusBO = xopService.posPlus(businessId);
        //菜品为空
        if (!posPlusBO.isSuccess() || posPlusBO.getResults().size() == 0) {
            log.info("菜品记录为空");
            return;
        }
        List<DishCm> dishCmList = Lists.newArrayList();
        posPlusBO.getResults().forEach(result -> {
            String cmbh = result.get("code");
            DishCm dishCm = dishCmService.selectOne(new EntityWrapper<DishCm>().eq("cmbh", cmbh).eq("business_id", businessId));
            if (dishCm == null) {
                dishCm = new DishCm();
                dishCm.setCreatedAt(new Date());
                dishCm.setBusinessId(businessId);
            }
            //大类编号
            String plucode = result.get("plucode");
            dishCm.setCmbh(cmbh);
            dishCm.setCmmc(result.get("descript"));
            dishCm.setXlbh(result.get("sort"));
            dishCm.setUpdatedAt(new Date());
            dishCm.setStatus(StringUtils.isBlank(result.get("status")) ? "1" : result.get("status"));
            dishCm.setCmmcPy(plucode);
            String price = result.get("price");
            if (price == null) {
                dishCm.setCmje(0.0);
            } else {
                JSONArray jsonArray = JSONArray.parseArray(price);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                dishCm.setCmje(Double.valueOf(jsonObject.getString("price")));
            }
            dishCmList.add(dishCm);
        });
        dishCmService.insertOrUpdateBatch(dishCmList);
    }
}
