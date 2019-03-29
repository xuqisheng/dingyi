package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.common.util.PageFactory;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Anniversary;
import com.zhidianfan.pig.yd.moduler.common.service.IAnniversaryService;
import com.zhidianfan.pig.yd.moduler.resv.bo.CustomerCareBO;
import com.zhidianfan.pig.yd.moduler.resv.dto.AnniversaryDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.CustomerCareDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: hzp
 * @Date: 2019-03-28 16:05
 * @Description:
 */
@Service
public class AnniversaryService {

    @Autowired
    IAnniversaryService iAnniversaryService;


    /**
     * 根据主键查询确切的纪念日
     * @param anniversaryId  纪念日id
     * @return 纪念日
     */
    public Anniversary getExactAnniversary(Integer anniversaryId) {


        Anniversary exactAnniversary = iAnniversaryService.selectById(anniversaryId);
        return exactAnniversary;
    }


    public Boolean deleteExactAnniversary(Integer anniversaryId) {

        Boolean b = iAnniversaryService.deleteById(anniversaryId);
        return b;
    }

    /**
     * 根据客户id 查询该客户的所有纪念日
     * @param vipId 客户id
     * @return 纪念日list
     */
    public List<Anniversary> getAnniversaryListByVipID(Integer vipId) {

        List<Anniversary> anniversaryList = iAnniversaryService.selectList(new EntityWrapper<Anniversary>()
                .eq("vip_id", vipId));

        return anniversaryList;
    }


    /**
     * 编辑客户纪念日
     * @param anniversaryDTO 纪念日
     * @return 返回编辑结果
     */
    public Boolean editExactAnniversary(AnniversaryDTO anniversaryDTO) {

        Anniversary anniversary= new Anniversary();

        //todo 如果日期需要为农历,将公历转化为转换为农历,计算下一次农历存入下一次生日时间

        BeanUtils.copyProperties(anniversaryDTO ,anniversary);

        //更新或者插入
        Boolean b = iAnniversaryService.insertOrUpdate(anniversary);

        return b;
    }


    /**
     * 分页查询客户生日与纪念日信息
     * @return 客户关怀数据
     */
    public Page<CustomerCareDTO> getCustomerCarePage(CustomerCareDTO customerCareDTO) {


        Page<CustomerCareDTO> page = new PageFactory().defaultPage();

        //查询出客户关怀等信息
        List<CustomerCareBO> customerCareBOS=  iAnniversaryService.selectCustomerCareInfoByPage(page,customerCareDTO);

        List<CustomerCareDTO> customerCareDTOS = new ArrayList<>();
        //对数据的处理
        for (CustomerCareBO customerCareBO: customerCareBOS) {
            CustomerCareDTO customerCareData =  new CustomerCareDTO();
            customerCareData.setVipId(customerCareBO.getId());
            String name = customerCareBO.getVipName() + (customerCareBO.getVipSex().equals("女") ? "小姐" : "先生");
            customerCareData.setName(name);
            customerCareData.setPhone(customerCareBO.getVipPhone());
            customerCareData.setTitle(customerCareBO.getAnniversaryTitle());
            customerCareData.setCustomerValue(customerCareBO.getVipValueName());


            //对日期的处理 --几天后的处理
            String surplusDay  = "暂无";
            if (customerCareBO.getSurplusTime() == null || customerCareBO.getSurplusTime() < 0){
                surplusDay = "暂无";
            } else if (customerCareBO.getSurplusTime() == 0){
                surplusDay= "今天";
            } else  if(customerCareBO.getSurplusTime() >  0) {
                surplusDay = customerCareBO.getSurplusTime() + "天后";
            }

            //todo 几周年或者几岁的处理
//            if(customerCareBO.getType() == 0){
//                String nexttime = customerCareBO.getNexttime();
//                customerCareBO.get
//
//            }

            String surplusTime = surplusDay + "";
            customerCareData.setSurplusTime(surplusTime);


            customerCareDTOS.add(customerCareData);

        }
        page.setRecords(customerCareDTOS);


        return page;
    }
}










