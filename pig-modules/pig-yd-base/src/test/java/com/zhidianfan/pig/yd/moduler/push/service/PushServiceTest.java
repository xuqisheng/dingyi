package com.zhidianfan.pig.yd.moduler.push.service;

import com.zhidianfan.pig.yd.moduler.common.service.IBasePushLogService;
import com.zhidianfan.pig.yd.moduler.push.dto.JgPush;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/21
 * @Modified By:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class PushServiceTest {

    @Autowired
    private IBasePushLogService pushLogService;

    @Autowired
    private PushService pushService;

    @Test
    public void regDev() {
    }

    @Test
    public void checkRegBefore() {
    }

    @Test
    public void addBasePushLogs() {
        JgPush jgPush = new JgPush();
        jgPush.setType("PAD");
        jgPush.setUsername("15658219258");
        jgPush.setBusinessId("33");
        List<String> regId = Arrays.asList("190e35f7e061e0bebb0"
                , "18071adc035685b9083"
                , "120c83f7607f42791ba"
                , "1a0018970afde7f6762");

        for (int i = 0; i < 1000; i++) {
            jgPush.setMsgSeq("1");//递增
            jgPush.setMsg(i + "");
            pushService.addBasePushLogs(jgPush, regId);
        }

    }
}