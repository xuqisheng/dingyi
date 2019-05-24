package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.LossValueConfig;
import com.zhidianfan.pig.yd.moduler.common.service.ILossValueConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sjl
 * 2019-05-23 15:34
 */
@Service
public class LossValueConfigService {

    @Autowired
    private ILossValueConfigService lossValueConfigMapper;

    public List<LossValueConfig> getLossValueConfig(Integer hotelId) {
        Wrapper<LossValueConfig> wrapper = new EntityWrapper<>();
        wrapper.eq("hotel_id", hotelId)
                .eq("flag", 1);
        return lossValueConfigMapper.selectList(wrapper);
    }

}
