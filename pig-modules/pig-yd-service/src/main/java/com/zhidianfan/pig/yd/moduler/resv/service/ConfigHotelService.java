package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ConfigHotel;
import com.zhidianfan.pig.yd.moduler.common.service.IConfigHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author sjl
 * 2019-05-23 15:19
 */
@Service
public class ConfigHotelService {

    @Autowired
    private IConfigHotelService configHotelMapper;

    /**
     * 根据 酒店id 获取配置
     *
     * @param hotelId 酒店id
     * @return FirstValueConfig 配置, k-v 对
     */
    public Map<String, String> getConfig(Integer hotelId) {
        Wrapper<ConfigHotel> wrapper = new EntityWrapper<>();
        wrapper.eq("hotel_id", hotelId);
        List<ConfigHotel> configHotelList = configHotelMapper.selectList(wrapper);
        return configHotelList.stream()
                .collect(Collectors.toMap(ConfigHotel::getK, ConfigHotel::getV));
    }

}
