package com.zhidianfan.pig.yd.moduler.common.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderHis;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class IResvOrderHisServiceTest {

    @Autowired
    private IResvOrderHisService orderHisService;

    @Test
    public void test1(){
        orderHisService.insertBatch(Arrays.asList(new ResvOrderHis(),new ResvOrderHis()));
    }

}