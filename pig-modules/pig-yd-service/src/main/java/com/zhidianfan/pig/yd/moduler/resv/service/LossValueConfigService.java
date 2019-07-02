package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.google.common.collect.Maps;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.LossValueConfig;
import com.zhidianfan.pig.yd.moduler.common.service.ILossValueConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author sjl
 * 2019-05-23 15:34
 */
@Service
public class LossValueConfigService {

    @Autowired
    private ILossValueConfigService lossValueConfigMapper;

    /**
     * 根据酒店 id 获取配置项
     * @param hotelId 酒店 id
     * @return 配置项
     */
    public List<LossValueConfig> getLossValueConfig(Integer hotelId) {
        Wrapper<LossValueConfig> wrapper = new EntityWrapper<>();
        wrapper.eq("hotel_id", hotelId)
                .eq("flag", 1);
        return lossValueConfigMapper.selectList(wrapper);
    }


    /**
     * 查询所有酒店的配置
     * @return map, k->businessId, v->配置项
     */
    public Map<Integer, List<LossValueConfig>> getLossValueConfigList() {
        Wrapper<LossValueConfig> wrapper = new EntityWrapper<>();
        wrapper.eq("flag", 1);
        List<LossValueConfig> lossValueConfigList = lossValueConfigMapper.selectList(wrapper);
        if (lossValueConfigList == null) {
            return Maps.newHashMap();
        }

        Map<Integer, List<LossValueConfig>> configMap = lossValueConfigList.stream()
                .collect(Collectors.groupingBy(LossValueConfig::getHotelId));
        return configMap;
    }



}
