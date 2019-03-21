package com.zhidianfan.pig.user.dao.mapper;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.user.dao.entity.Business;
import com.zhidianfan.pig.user.dao.service.IBusinessService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/11/9
 * @Modified By:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class BusinessMapperTest {

    @Autowired
    private IBusinessService businessService;

    @Test
    public void test1() {
        Business businessinfo = businessService.selectOne(new EntityWrapper<Business>()
                .eq("id", 1).eq("status", 1));
        System.out.println(businessinfo);
    }

}