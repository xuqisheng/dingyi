package com.zhidianfan.pig.yd.moduler.resv.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author sjl
 * 2019-05-21 15:39
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CustomerValueBaseInfoServiceTest {

    @Test
    public void test() {
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String s = format + 100;
        Long aLong = Long.valueOf(s);
        System.out.println(aLong);
    }

    @Test
    public void testMax() {
        List<Integer> integers = Arrays.asList(
                1, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 4, 5,5,5
        );

        Optional<Integer> max = integers.stream()
                .max(((o1, o2) -> o1 > o2 ? 1 : -1));
        System.out.println(max.get());


    }

    @Autowired
    private CustomerValueService customerValueService;

    @Test
    public void addCuster() {
//        customerValueService.addCustomerList();
    }

    @Test
    public void getCustomerValueBaseInfoTest() {
        customerValueService.getCustomerValueBaseInfo();
    }

    @Test
    public void getCount() {
//        int customerCount = customerValueService.getCustomerCount(7165);
//        System.out.println(customerCount);
    }

    @Test
    public void testgetCustomerValueBaseInfo() {
        customerValueService.getCustomerValueBaseInfo();
        System.err.println("执行完成，去数据库中观察数据");
    }
}
