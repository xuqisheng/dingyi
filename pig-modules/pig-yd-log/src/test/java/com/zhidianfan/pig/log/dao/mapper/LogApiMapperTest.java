package com.zhidianfan.pig.log.dao.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.log.dao.entity.LogApi;
import com.zhidianfan.pig.log.dao.service.ILogApiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/11/16
 * @Modified By:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LogApiMapperTest {

    @Autowired
    private ILogApiService logApiService;

    @Test
    public void test1(){
        Page page = new Page<>(2,15);
        logApiService.selectPage(page);
    }

}