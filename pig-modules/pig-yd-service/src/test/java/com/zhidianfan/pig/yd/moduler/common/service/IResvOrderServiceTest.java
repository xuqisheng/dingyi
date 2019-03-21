package com.zhidianfan.pig.yd.moduler.common.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/31
 * @Modified By:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class IResvOrderServiceTest {

    @Autowired
    private IResvOrderService orderService;

    @Test
    public void test1(){
        List<ResvOrder> list = orderService.selectList(null);
    }
    @Test
    public void test2(){
        ResvOrder resvOrder  = new ResvOrder();
        resvOrder.setId(1);

        orderService.deleteBatchIds(Arrays.asList(1));




    }

}