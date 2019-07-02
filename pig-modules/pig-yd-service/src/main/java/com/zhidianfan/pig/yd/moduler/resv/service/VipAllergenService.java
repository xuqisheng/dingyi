package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.common.util.PageFactory;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Vip;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.VipAllergen;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.VipClass;
import com.zhidianfan.pig.yd.moduler.common.service.IVipAllergenService;
import com.zhidianfan.pig.yd.moduler.common.service.IVipService;
import com.zhidianfan.pig.yd.moduler.resv.dto.VipAllergenDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: hzp
 * @Date: 2019-06-26 13:18
 * @Description:
 */
@Service
@Slf4j
public class VipAllergenService {


    @Autowired
    private IVipAllergenService iVipAllergenService;

    @Autowired
    private IVipService iVipService;

    /**
     * 新增或者插入客户过敏源
     * @param vip
     */
    public void editvipAllergenInfo(VipAllergenDTO vip) {

        String allergen = vip.getAllergen();
        //如果不为空, 则插入或者新增操作
        if(StringUtils.isEmpty(allergen)){
            return;
        }else {
            Vip vipInfo = iVipService.selectOne(new EntityWrapper<Vip>()
                    .eq("business_id", vip.getBusinessId())
                    .eq("vip_phone", vip.getVipPhone()));

            VipAllergen vipAllergen = iVipAllergenService.selectOne(new EntityWrapper<VipAllergen>()
                    .eq("vip_id", vipInfo.getId()));


            if(vipAllergen == null){
                vipAllergen = new VipAllergen();
                vipAllergen.setCreatedAt(new Date());
            }

            vipAllergen.setAllergen(vip.getAllergen());
            vipAllergen.setVipId(vipInfo.getId());
            iVipAllergenService.insertOrUpdate(vipAllergen);

        }
    }


    public String selectvipAllergen(Integer id) {

        VipAllergen vipAllergen = iVipAllergenService.selectOne(new EntityWrapper<VipAllergen>()
                .eq("vip_id", id));

        return  vipAllergen == null ? "" : vipAllergen.getAllergen();
    }
}
