package com.zhidianfan.pig.yd.moduler.resv.service;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.AutoReceiptSmsConfig;
import com.zhidianfan.pig.yd.moduler.common.service.IAutoReceiptSmsConfigService;
import com.zhidianfan.pig.yd.moduler.resv.dto.AutoReceiptSmsConfigDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author huzp
 * @date 2019/1/28 0028 14:58
 * @description
 */
@Service
public class AutoReceiptSmsConfigService {


    @Autowired
    IAutoReceiptSmsConfigService iAutoReceiptSmsConfigService;


    /**
     * 自动接单配置
     * @param autoReceiptSmsConfigDTO 自动接单配置
     * @return 操作是否成功
     */
    public boolean editAutoReceiptSmsConfig(AutoReceiptSmsConfigDTO autoReceiptSmsConfigDTO) {

        AutoReceiptSmsConfig autoReceiptSmsConfig = new AutoReceiptSmsConfig();
        BeanUtils.copyProperties(autoReceiptSmsConfigDTO, autoReceiptSmsConfig);

        if(autoReceiptSmsConfig.getCreatedAt() == null){
            autoReceiptSmsConfig.setCreatedAt(new Date());
        }else {
            autoReceiptSmsConfig.setUpdatedAt(new Date());
        }

        return  iAutoReceiptSmsConfigService.insertOrUpdate(autoReceiptSmsConfig);
    }


    /**
     * 获取信息
     * @param id 酒店id
     * @return 结果信息
     */
    public AutoReceiptSmsConfig getAutoReceiptSmsConfig(Integer id) {


        AutoReceiptSmsConfig configInfo = iAutoReceiptSmsConfigService.selectOne(new EntityWrapper<AutoReceiptSmsConfig>()
                .eq("business_id", id));

        return configInfo;
    }
}
