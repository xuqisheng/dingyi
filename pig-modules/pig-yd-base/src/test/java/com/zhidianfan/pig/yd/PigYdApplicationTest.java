package com.zhidianfan.pig.yd;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Iterator;
import java.util.Map;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/15
 * @Modified By:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PigYdBaseApplication.class)
public class PigYdApplicationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void main() {
        Map<String, Object> map = applicationContext.getBeansWithAnnotation(Controller.class);
        System.out.println("=============== 开始统计 ===============");
        for (Iterator<String> iterator = map.keySet().iterator(); iterator.hasNext(); ) {
            String k = iterator.next();
            Object v = map.get(k);
            String className = v.getClass().getSimpleName();
            System.out.println(k + ":" + className);
        }
        System.out.println("=============== 结束统计 ===============");
    }
}