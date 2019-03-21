package com.zhidianfan.pig.yd;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrder;
import com.zhidianfan.pig.yd.moduler.common.service.IResvOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/15
 * @Modified By:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PigYdApplication.class)
public class PigYdApplicationTest {
    @Autowired
    private IResvOrderService orderService;

    @Test
    public void main() {
        orderService.selectList(null);

    }
}