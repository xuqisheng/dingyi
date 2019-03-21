package com.zhidianfan.pig.yd.config.prop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/20
 * @Modified By:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class YdPropertitesTest {

    @Autowired
    private YdPropertites ydPropertites;

    @Test
    public void getJg() {
        System.out.println(ydPropertites.getJg().getMap());
    }
}