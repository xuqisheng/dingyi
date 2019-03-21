package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Blacklist;
import com.zhidianfan.pig.yd.moduler.common.service.IBlacklistService;
import com.zhidianfan.pig.yd.moduler.resv.dto.CustomerDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: huzp
 * @Date: 2018/9/26 10:40
 */

@Service
public class BlackListService {

    @Autowired
    IBlacklistService iBlacklistService;

    /**
     * 查询单个用户是否为黑名单用户
     *
     * @param businessId 酒店id
     * @param phone 客户手机号
     * @return 返回客户信息
     */
    public Blacklist selectInfo(Integer businessId, String phone) {

        Blacklist blacklist = iBlacklistService.selectOne(new EntityWrapper<Blacklist>()
                .eq("status", 1)
                .eq("vip_phone", phone)
                .eq("business_id", businessId));

        return blacklist;
    }

    /**
     * 增加黑名单客户
     *
     * @param customerDTO 客户数据
     * @return 增加是否成功
     */
    public boolean addBlackCustomer(CustomerDTO customerDTO) {

        Blacklist blacklist = new Blacklist();
        blacklist.setStatus(1);

        //查询条件
        Wrapper<Blacklist> eq = new EntityWrapper<Blacklist>()
                .eq("vip_phone", customerDTO.getVipPhone())
                .eq("business_id", customerDTO.getBusinessId());

        //复制相同属性
        BeanUtils.copyProperties(customerDTO, blacklist);

        //先查询是否已经在黑名单了
        Blacklist info = iBlacklistService.selectOne(eq);


        //已经在了就更新黑名单信息
        if (null != info) {
            blacklist.setUpdatedAt(new Date());
            boolean b = iBlacklistService.update(blacklist, eq);
            return b;
        }

        //不在黑名单则新增
        blacklist.setCreatedAt(new Date());

        boolean insert = iBlacklistService.insert(blacklist);

        return insert;
    }

    /**
     * 修改黑名单客户的状态为 0 :移除黑名单
     * 修改客户信息
     * @return 更新是否成功
     */
    public boolean updateBlackCustomer(CustomerDTO customerDTO) {

        Blacklist blacklist = new Blacklist();
        //设置黑名单状态为失效
        blacklist.setStatus(0);
        blacklist.setUpdatedAt(new Date());
        BeanUtils.copyProperties(customerDTO, blacklist);

        boolean update = iBlacklistService.update(blacklist, new EntityWrapper<Blacklist>()
                .eq("vip_phone", customerDTO.getVipPhone())
                .eq("business_id", customerDTO.getBusinessId()));

        return update;
    }
}
