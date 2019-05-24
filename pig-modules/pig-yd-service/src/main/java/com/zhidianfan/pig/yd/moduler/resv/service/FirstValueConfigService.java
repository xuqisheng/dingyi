package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.FirstValueConfig;
import com.zhidianfan.pig.yd.moduler.common.service.IFirstValueConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sjl
 * 2019-05-23 15:19
 */
@Service
public class FirstValueConfigService {

    @Autowired
    private IFirstValueConfigService firstValueConfigMapper;

    /**
     * 根据 酒店id 获取配置
     *
     * @param hotelId 酒店id
     * @return FirstValueConfig 配置
     */
    public FirstValueConfig getConfig(Integer hotelId) {
        Wrapper<FirstValueConfig> wrapper = new EntityWrapper<>();
        wrapper.eq("hotel_id", hotelId);
        return firstValueConfigMapper.selectOne(wrapper);
    }

}
