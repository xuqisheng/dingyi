package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.common.util.PageFactory;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Vip;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.VipClass;
import com.zhidianfan.pig.yd.moduler.common.service.IVipClassService;
import com.zhidianfan.pig.yd.moduler.common.service.IVipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VipClassService {

    @Autowired
    private IVipClassService iVipClassService;

    @Autowired
    private IVipService iVipService;

    /**
     * 查询一个酒店启用的客户分类
     *
     * @param businessId 酒店id
     * @return 分类客户list
     */
    public Page<VipClass> getBusinessEnableClass(Integer businessId) {

        Page<VipClass> page = new PageFactory().defaultPage();

        //状态1 为启用0 为停用
        iVipClassService.selectPage(page, new EntityWrapper<VipClass>()
                .eq("status", 1)
                .eq("business_id", businessId)
                .orderBy("sort_id", false));

        return page;
    }

    /**
     * 修改一个class的name或者注释等操作
     *
     * @param vipClass
     * @return
     */
    public Boolean editVIPClassInfo(VipClass vipClass) {

        boolean b = iVipClassService.updateById(vipClass);

        //然后更新用户表内的所有信息
        if (b == true) {

            Vip vip = new Vip();

            Wrapper<Vip> eq = new EntityWrapper<Vip>()
                    .eq("business_id", vipClass.getBusinessId())
                    .eq("vip_class_id", vipClass.getId());

            vip.setVipClassName(vipClass.getVipClassName());
            iVipService.update(vip, eq);
        }

        return b;
    }

    /**
     * 统计酒店当前的所有用的class数量
     * @param vipClass
     * @return
     */
    public Integer countSortId(VipClass vipClass) {

        return iVipClassService.selectCount((new EntityWrapper<VipClass>()
                .eq("business_id", vipClass.getBusinessId())));
    }

    public Boolean insertVIPClassInfo(VipClass vipClass) {

        boolean insert = iVipClassService.insert(vipClass);
        return  insert;
    }

    /**
     * 删除分类信息
     * @param vipClass
     * @return
     */
    public Boolean delVipclass(VipClass vipClass) {

        //删除该分类
        boolean b = iVipClassService.deleteById(vipClass.getId());


        if (b==true){
            iVipService.updateVipClassNULL(vipClass);
        }

        return b;
    }
}
