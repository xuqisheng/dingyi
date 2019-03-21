package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.AppUser;
import com.zhidianfan.pig.yd.moduler.common.service.IAppUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/16
 * @Modified By:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AppUserServiceImplTest {

    @Autowired
    private IAppUserService appUserService;

    @Test
    public void selectList() {
        List<AppUser> list = appUserService.selectList(new EntityWrapper<AppUser>()
                .eq("app_user_name", "廖用晋"));
        System.out.println(list);
    }
}