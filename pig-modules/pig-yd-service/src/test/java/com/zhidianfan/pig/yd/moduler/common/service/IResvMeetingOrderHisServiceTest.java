package com.zhidianfan.pig.yd.moduler.common.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvMeetingOrderHis;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class IResvMeetingOrderHisServiceTest {
    @Autowired
    private IResvMeetingOrderHisService resvMeetingOrderHisService;

    @Test
    public void test1(){
        System.out.println(Arrays.asList(new ResvMeetingOrderHis(),new ResvMeetingOrderHis()));
        ResvMeetingOrderHis resvMeetingOrderHis1 = new ResvMeetingOrderHis();
        resvMeetingOrderHis1.setId(1);
        ResvMeetingOrderHis resvMeetingOrderHis2 = new ResvMeetingOrderHis();
        resvMeetingOrderHis2.setId(2);
        resvMeetingOrderHisService.insertBatch(Arrays.asList(resvMeetingOrderHis1, resvMeetingOrderHis2));
    }
}