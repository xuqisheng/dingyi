package com.zhidianfan.pig.yd.moduler.push.service.ext;

import com.zhidianfan.pig.common.util.JsonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.*;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/21
 * @Modified By:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PushSupportTest {


    @Autowired
    private PushSupport pushSupport;

    @Test
    public void pushMsgByRegId() {
        //Base64 编码
        String authToken = Base64.getEncoder().encodeToString("578e3a217359f7b22e2c869b:f9f4e8c1ff145b84f5a208d4".getBytes(Charset.forName("utf8")));
        System.out.println(authToken);//NTc4ZTNhMjE3MzU5ZjdiMjJlMmM4NjliOjU3OGUzYTIxNzM1OWY3YjIyZTJjODY5Yg==

        //Header头认证参数
        String authorization = "Basic " + authToken;
        String url = "https://api.jpush.cn/v3/push";//POST方法
        String param = "{\n" +
                "\t\"platform\" : \"android\",\n" +
                "\t\"audience\" : {\n" +
                "\t\t\"registration_id\" : [\"190e35f7e061e0bebb0\",\"1a0018970afde7f6762\"]\n" +
                "\t\t\n" +
                "\t},\n" +
                "\t\"message\" : {\n" +
                "\t\t\"msg_content\":\"推送到指定Android设备" + new Date() + "\"\n" +
                "\t\t\n" +
                "\t}\n" +
                "}";

        //设置头信息
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", authorization);
        httpHeaders.add("Content-Type", "application/json;charset=UTF-8");


        HttpEntity httpEntity = new HttpEntity(param, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void pushMsgByRegIds() {
        List<String> list = Arrays.asList("1", "2", "3");
        System.out.println(JsonUtils.obj2Json(list));
    }

    @Test
    public void pushMsgByRedIdsWithNode() throws InterruptedException {
        Map<String, String> map = new HashMap<>();
        map.put("k1", "v1");
        map.put("k2", "v2");
        map.put("k3", "v3哈哈😆");
        pushSupport.pushMsgByRedIdsWithNode(Arrays.asList("uo7JE7VeU3kk_nSWAAAC","7rwq4klD7N21p0TqAAAD"), JsonUtils.obj2Json(map));


        Thread.sleep(5000);
    }
}