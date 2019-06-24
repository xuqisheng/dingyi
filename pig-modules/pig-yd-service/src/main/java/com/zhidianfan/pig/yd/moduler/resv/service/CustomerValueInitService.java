package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Business;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ConfigHotel;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.LossValueConfig;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessService;
import com.zhidianfan.pig.yd.moduler.common.service.IConfigHotelService;
import com.zhidianfan.pig.yd.moduler.common.service.ILossValueConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 客户价值，初始化配置，一级价值，细分价值
 * @author sjl
 * 2019-06-12 18:53
 */
@Slf4j
@Service
public class CustomerValueInitService {


    @Autowired
    private IConfigHotelService configHotelMapper;

    @Autowired
    private IBusinessService businessMapper;

    @Autowired
    private ILossValueConfigService lossValueConfigMapper;


    /**
     * 沉睡与唤醒之间
     */
    private static final String ACTIVE_SLEEP_BETWEEN_K = "active_sleep_between";
    private static final String ACTIVE_SLEEP_BETWEEN_V = "30";
    /**
     * 沉睡与流失之间
     */
    private static final String SLEEP_LOSS_BETWEEN_K = "sleep_loss_between";
    private static final String SLEEP_LOSS_BETWEEN_V = "60";
    /**
     * 细分价值中的时间周期
     */
    private static final String VALUE_CATEGORY_CYCLE_K = "value_category_cycle";
    private static final String VALUE_CATEGORY_CYCLE_V = "60";
    /**
     * 黑名单
     */
    private static final String BLACK_LIST_ORDER_NUM_K = "black_list_order_num";
    private static final String BLACK_LIST_ORDER_NUM_V = "3";

    /**
     * 初始化一级价值的所有酒店配置
     * @param hotelId 酒店id
     */
    public void initFirstValue(String hotelId) {
        if (StringUtils.isBlank(hotelId)) {
            log.info("-------------清空之前的配置-------------");
            deleteConfigAll();
            log.info("-------------初始化全部的酒店-------------");
            List<Business> businesses = getBusinessesAll();
            List<ConfigHotel> configHotelList = getConfigHotels(businesses, businesses.size() * 4);
            configHotelMapper.insertBatch(configHotelList);
        } else {
            log.info("-------------清空指定酒店的配置-------------");
            deleteConfigByBusinessId(hotelId);
            log.info("-------------初始化指定酒店：[{}]-------------", hotelId);
            Business business = getBusinessesById(hotelId);
            List<Business> businesses = Collections.singletonList(business);
            List<ConfigHotel> configHotels = getConfigHotels(businesses, 4);
            configHotelMapper.insertBatch(configHotels);
        }
    }

    private void deleteConfigByBusinessId(String hotelId) {
        Business business = getBusinessesById(hotelId);
        Integer id = business.getId();
        Wrapper<ConfigHotel> wrapper = new EntityWrapper<>();
        wrapper.eq("hotel_id", id);
        configHotelMapper.delete(wrapper);
    }

    private void deleteConfigAll() {
        Wrapper<ConfigHotel> wrapper = new EntityWrapper<>();
        configHotelMapper.delete(wrapper);
    }

    private List<ConfigHotel> getConfigHotels(List<Business> businesses, int size) {
        List<ConfigHotel> configHotelList = new ArrayList<>(size);
        for (Business business : businesses) {
            ConfigHotel configHotel1 = getConfigHotel(business.getId(), ACTIVE_SLEEP_BETWEEN_K, ACTIVE_SLEEP_BETWEEN_V);
            ConfigHotel configHotel2 = getConfigHotel(business.getId(), SLEEP_LOSS_BETWEEN_K, SLEEP_LOSS_BETWEEN_V);
            ConfigHotel configHotel3 = getConfigHotel(business.getId(), VALUE_CATEGORY_CYCLE_K, VALUE_CATEGORY_CYCLE_V);
            ConfigHotel configHotel4 = getConfigHotel(business.getId(), BLACK_LIST_ORDER_NUM_K, BLACK_LIST_ORDER_NUM_V);
            configHotelList.add(configHotel1);
            configHotelList.add(configHotel2);
            configHotelList.add(configHotel3);
            configHotelList.add(configHotel4);
        }
        return configHotelList;
    }

    private Business getBusinessesById(String businessId) {
        int id;
        try {
            id = Integer.parseInt(businessId);
        } catch (NumberFormatException e) {
            log.error("传入的 businessId 错误 [{}]", businessId);
            throw e;
        }
        return businessMapper.selectById(id);
    }

    private ConfigHotel getConfigHotel(Integer businessId, String key, String v) {
        ConfigHotel configHotel = new ConfigHotel();
        String s = String.valueOf(businessId);
        Long hotelId = Long.valueOf(s);
        configHotel.setHotelId(hotelId);
        configHotel.setK(key);
        configHotel.setV(v);
        configHotel.setDescri("init");
        configHotel.setFlag(1);
        configHotel.setCreateTime(LocalDateTime.now());
        configHotel.setUpdateTime(LocalDateTime.now());
        return configHotel;
    }


    private List<Business> getBusinessesAll() {
        return businessMapper.selectList(new EntityWrapper<>());
    }


    private static final String VALUE_NAME_1 = "vip";
    private static final Integer CUSTOMER_COUNT_START_1 = 2;
    private static final String VALUE_NAME_2 = "svip";
    private static final Integer CUSTOMER_COUNT_END_2 = 4;
    // 单位：分
    public static final Integer CUSTOMER_TOTAL_START = 500000;

    /**
     * 初始化细分价值酒店配置
     * @param businessId 酒店 id
     */
    public void initSubValue(String businessId) {
        if (StringUtils.isBlank(businessId)) {
            log.info("-------------清空之前的配置-------------");
            deleteSubValueConfigAll();
            log.info("-------------初始化全部的酒店-------------");
            List<Business> businesses = getBusinessesAll();
            List<LossValueConfig> lossValueConfigList = getLossValueConfigList(businesses, businesses.size() * 2);
            lossValueConfigMapper.insertBatch(lossValueConfigList);
        } else {
            log.info("-------------清空指定酒店的配置-------------");
            deleteSubValueConfigByBusinessId(businessId);
            log.info("-------------初始化指定酒店：[{}]-------------", businessId);
            Business business = getBusinessesById(businessId);
            List<Business> businessList = Collections.singletonList(business);
            List<LossValueConfig> lossValueConfigList = getLossValueConfigList(businessList, 2);
            lossValueConfigMapper.insertBatch(lossValueConfigList);
        }
    }


    private List<LossValueConfig> getLossValueConfigList(List<Business> businesses, int size) {
        List<LossValueConfig> lossValueConfigList = new ArrayList<>(size);
        for (Business business : businesses) {
            LossValueConfig lossValueConfig1 = getLossValueConfig(business, VALUE_NAME_1, CUSTOMER_COUNT_START_1);
            LossValueConfig lossValueConfig2 = getLossValueConfig(business, VALUE_NAME_2, CUSTOMER_COUNT_END_2, CUSTOMER_TOTAL_START);
            lossValueConfigList.add(lossValueConfig1);
            lossValueConfigList.add(lossValueConfig2);
        }
        return lossValueConfigList;
    }

    private LossValueConfig getLossValueConfig(Business business, String valueName, Integer customerCountStart) {
        return getLossValueConfig(business, valueName, customerCountStart, -1);
    }

    private LossValueConfig getLossValueConfig(Business business, String valueName, Integer customerCountStart, Integer customerTotalStart) {
        LossValueConfig config = new LossValueConfig();
        config.setHotelId(business.getId());
        config.setValueName(valueName);
        config.setCustomerPersonAvgStart(-1);
        config.setCustomerPersonAvgEnd(-1);
        config.setCustomerTotalStart(customerTotalStart);
        config.setCustomerTotalEnd(-1);
        config.setCustomerCountStart(customerCountStart);
        config.setCustomerCountEnd(-1);
        if (valueName.equals(VALUE_NAME_1)) {
            config.setSort(2);
        } else {
            config.setSort(1);
        }
        config.setFlag(1);
        config.setCreateTime(LocalDateTime.now());
        config.setUpdateTime(LocalDateTime.now());
        return config;
    }

    private void deleteSubValueConfigAll() {
        Wrapper<LossValueConfig> wrapper = new EntityWrapper<>();
        lossValueConfigMapper.delete(wrapper);
    }


    private void deleteSubValueConfigByBusinessId(String businessId) {
        Wrapper<LossValueConfig> wrapper = new EntityWrapper<>();
        wrapper.eq("hotel_id", businessId);
        lossValueConfigMapper.delete(wrapper);
    }


}
