package com.zhidianfan.pig.yd.moduler.resv.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.CustomerValueList;
import com.zhidianfan.pig.yd.moduler.common.service.ICustomerValueListService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

/**
 * 2019/6/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CustomerValueListTest {

    @Autowired
    private ICustomerValueListService iCustomerValueListService;

    @Test
    public void testInsertCustomerValueList() {
        CustomerValueList valueList = new CustomerValueList();
        valueList.setId(1L);
        valueList.setVipId(1);
        valueList.setVipName("张三");
        valueList.setVipSex("先生");
        valueList.setVipPhone("11111111111");
        valueList.setVipAge(1);
        valueList.setVipCompany("公司");
        valueList.setAppUserId(1);
        valueList.setAppUserName("张三");
        valueList.setHotelId(1);
        valueList.setCustomerCount(1);
        valueList.setCustomerAmountTotal(1);
        valueList.setCustomerAmountAvg(1);
        valueList.setLastEatTime(LocalDateTime.now());
        valueList.setFirstClassValue(1);
        valueList.setSubValue(110L);
        valueList.setCustomerClass("aaa");
        valueList.setCreateTime(LocalDateTime.now());
        valueList.setUpdateTime(LocalDateTime.now());
        iCustomerValueListService.insert(valueList);
    }

}
