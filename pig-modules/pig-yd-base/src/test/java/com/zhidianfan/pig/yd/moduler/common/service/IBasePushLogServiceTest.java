package com.zhidianfan.pig.yd.moduler.common.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.BasePushLog;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/22
 * @Modified By:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class IBasePushLogServiceTest {

    @Autowired
    private IBasePushLogService pushLogService;
    private Page<BasePushLog> pushLogPage = new Page(0, 1000);//每次迁移1000条

    @Test
    public void testList(){
        Page<BasePushLog> pageList = pushLogService.selectPage(pushLogPage, new EntityWrapper<BasePushLog>()
                .lt("insert_time", DateUtils.addDays(new Date(), -1)));
        System.out.println(pageList);
    }
}