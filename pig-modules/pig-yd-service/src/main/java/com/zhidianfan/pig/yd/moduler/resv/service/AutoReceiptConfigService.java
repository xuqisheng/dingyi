package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.AutoReceiptConfig;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.common.service.IAutoReceiptConfigService;
import com.zhidianfan.pig.yd.moduler.resv.dto.AutoReceiptConfigDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author huzp
 * @date 2019/1/28 0028 14:58
 * @description
 */
@Service
public class AutoReceiptConfigService {


    @Autowired
    IAutoReceiptConfigService iAutoReceiptConfigService;

    /**
     * 修改或者新增 酒店自动接单配置
     * @param autoReceiptConfig 自动接单配置
     * @return 成功失败标志
     */
    public Tip editAutoReceiptConfig(AutoReceiptConfig autoReceiptConfig ) {

        boolean b = iAutoReceiptConfigService.update(autoReceiptConfig,
                            new EntityWrapper<AutoReceiptConfig>().eq("id",autoReceiptConfig.getId()));
        Tip tip = b ?  SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP;
        return tip;
    }

    /**
     * 查询酒店自动配置
     * @param bId
     * @return 酒店配置
     */
    public AutoReceiptConfig getAutoReceiptConfig(Integer bId) {

        AutoReceiptConfig autoReceiptConfig = iAutoReceiptConfigService.selectOne(
                new EntityWrapper<AutoReceiptConfig>().eq("business_id", bId));
        return autoReceiptConfig;
    }
}
