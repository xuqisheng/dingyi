package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.zhidianfan.pig.common.util.PageFactory;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.service.*;
import com.zhidianfan.pig.yd.moduler.resv.bo.SmsRecordBO;
import com.zhidianfan.pig.yd.moduler.resv.dto.HomePageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: huzp
 * @Date: 2018/9/20 10:22
 */
@Service
public class BusinessInfoService {


    @Autowired
    private IBusinessSmsRechargeLogService iBusinessSmsRechargeLogService;

    @Autowired
    private IBusinessService businessService;

    @Autowired
    private IMealTypeService mealTypeService;

    @Autowired
    private ITableAreaService tableAreaService;

    @Autowired
    private IBusinessThirdPartyService businessThirdPartyService;

    @Autowired
    private IVipService vipService;

    @Autowired
    private IResvOrderAndroidService resvOrderAndroidService;

    @Autowired
    private ITableService tableService;

    /**
     * 获取酒店信息
     *
     * @param id 酒店id
     * @return 返回酒店信息
     */
    public Business getBusinessInfo(Integer id) {

        Business businessinfo = businessService.selectOne(new EntityWrapper<Business>()
                .eq("id", id).eq("status", 1));


        return businessinfo;
    }


    /**
     * 获取餐别
     *
     * @param id 酒店id
     * @return 返回餐别
     */
    public List<MealType> getMealTypeInfo(Integer id) {

        List<MealType> mealtype = mealTypeService.selectList(new EntityWrapper<MealType>()
                .eq("business_id", id)
                .eq("status","1")
                .eq("isnew",1));
        return mealtype;
    }

    /**
     * 获取酒店区域信息
     *
     * @param id 酒店id
     * @return 返回酒店区域信息
     */
    public List<TableArea> getTableAreaInfo(Integer id,Integer tableAreaStatus) {

        Wrapper<TableArea> wrapper = new EntityWrapper<TableArea>()
                .eq("business_id", id);
        if(tableAreaStatus!=null){
            wrapper.eq("status",tableAreaStatus);
        }
        List<TableArea> areainfo = tableAreaService.selectList(wrapper);
        return areainfo;
    }


    /**
     * 有效的客户合约平台
     *
     * @param id
     * @return
     */
    public List<BusinessThirdParty> getThirdPartyInfo(Integer id) {

        List<BusinessThirdParty> businessThirdPartyList = businessThirdPartyService.findThirdPartyByBusinessId(id);
        return businessThirdPartyList;
    }

    /**
     * 修改酒店免审订单按钮状态
     *
     * @param  business     酒店id orderExemptionVerifyCheck 按钮状态
     * @return true代表操作成功
     */
    public boolean changeOrderExemptionVerifyCheck(Business business) {

        return  businessService.update(business, new EntityWrapper<Business>()
                .eq("id", business.getId()));
    }

    /**
     * 修改酒店默认就餐人数
     *
     * @param business  酒店id  defMealsNum 默认就餐人数
     * @return true代表操作成功
     */
    public boolean changeDefMealsNum(Business business) {

        return businessService.update(business, new EntityWrapper<Business>()
                .eq("id", business.getId()));
    }

    /**
     * 分页获取一个酒店的充值记录
     *
     * @param businessId
     * @return 充值信息
     */
    public Page<SmsRecordBO> getSmsRechargeRecord(Integer businessId) {

        Page<SmsRecordBO> page = new PageFactory().defaultPage();

        Page<SmsRecordBO> businessSmsRechargeLogPage = iBusinessSmsRechargeLogService.selectSmsRechargeRecordPage(page, businessId);
        //分页查询出短信
        return businessSmsRechargeLogPage;

    }

    /**
     * 获取总充值
     *
     * @param businessId 酒店id
     * @return 返回总充值记录 payamountsum 支付金额 smssum 总充值短信条数
     */
    public Map<String, String> getGeneralRechargeRecord(Integer businessId) {

        Map<String, String> generalRechargeRecord = iBusinessSmsRechargeLogService.getGeneralRechargeRecord(businessId);

        return generalRechargeRecord;
    }


    /**
     * 酒店关联的第三方平台信息刪除
     * @param id 第三方平台id
     */
    public void deleteThirdPartyById(Integer id) {

        businessThirdPartyService.deleteById(id);
    }


    /**
     * 酒店关联的第三方平台信息新增
     *
     * @param businessThirdParty 第三方平台信息
     */
    public void insertThirdParty(BusinessThirdParty businessThirdParty) {

        BusinessThirdParty condition = new BusinessThirdParty();
        condition.setBusinessId(businessThirdParty.getBusinessId());

        EntityWrapper<BusinessThirdParty> wrapper = new EntityWrapper<>(condition);
        wrapper.orderBy("sort",false);
        BusinessThirdParty one = businessThirdPartyService.selectOne(wrapper);
        if(one==null){
            businessThirdParty.setSort(0);
        }else {
            businessThirdParty.setSort(one.getSort()+1);
        }
        businessThirdPartyService.insert(businessThirdParty);

    }


    /**
     * 门店后台主页数据
     * @param businessId 酒店id
     */
    public HomePageDTO getBusinessHomepageInfo(Integer businessId) {

        HomePageDTO homePageDTO = new HomePageDTO();

        //客户数量
        int vipNum = vipService.selectCount(new EntityWrapper<Vip>().eq("business_id", businessId));
        homePageDTO.setVipNum(vipNum);

        //桌子总数
        int tableCount = tableService.selectCount(new EntityWrapper<Table>()
                .eq("status", 1)
                .eq("business_id", businessId));
        homePageDTO.setTableCount(tableCount);

        //昨日新增客户
        Integer newVipNum  = vipService.getNewVipNum(businessId);
        homePageDTO.setNewVipNum(newVipNum);


        //预定成功数量
        String orderSucNum = resvOrderAndroidService.getorderSucNum(businessId);
        homePageDTO.setOrderSucNum(orderSucNum);

        //营业总金额
        String paySum = resvOrderAndroidService.getpaySum(businessId);
        homePageDTO.setPaySum(paySum);

        //昨天 就餐人数 订单总数 人均消费
        Map<String, Object> data =  resvOrderAndroidService.getYesterdayData(businessId);

        //昨日总预定数
        homePageDTO.setOrderSum(data.get("orderSum").toString());
        //昨日人均消费
        homePageDTO.setPerConsum(data.get("perConsum").toString());
        //昨日就餐人次
        homePageDTO.setResvSum(data.get("resvSum").toString());

        return homePageDTO;
    }

    /**
     * 酒店距离过期天数
     * @param id 酒店id
     * @return 正值为距离过期多少天，0代表过期当天，负值代表已经过期多少天
     */
    public Map<String,Integer> getDistanceExpiredDays(Integer id) {

        //查询酒店过期日期
        Business business = businessService.selectById(id);

        //计算酒店距离多久过期
        Date dueDate = business.getDueDate();
        Date now = new Date();
        //计算出过期天数
        int distanceExpiredDays = (int) ((dueDate.getTime() - now.getTime()) / (1000*3600*24));

        //推迟天数
        int delayDays = business.getDelayDays();

        HashMap<String, Integer> distanceExpiredDaysMap = Maps.newHashMap();

        //过期天数
        distanceExpiredDaysMap.put("distanceExpiredDays",distanceExpiredDays);
        //推迟天数
        distanceExpiredDaysMap.put("delayDays",delayDays);

        return distanceExpiredDaysMap;
    }

    /**
     * 获取所有过期酒店
     * @return
     */
    public List<Business> getExpiredHotel() {

        EntityWrapper<Business> businessEntityWrapper = new EntityWrapper<>();
        businessEntityWrapper.where("datediff(due_date,NOW()) < 0");
        List<Business> businesses = businessService.selectList(businessEntityWrapper);


        return businesses;
    }
}
