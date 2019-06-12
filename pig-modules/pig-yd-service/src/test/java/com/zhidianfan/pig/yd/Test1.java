package com.zhidianfan.pig.yd;

import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.yd.moduler.common.constant.ClientId;
import org.apache.velocity.runtime.parser.node.MathUtils;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/30
 * @Modified By:
 */

public class Test1 {

    private String url = "http://47.99.14.92:9999/auth/oauth/token";

    @Test
    public void test3(){
        
    }

    @Test
    public void test1() {
        ClientId client = ClientId.valueOf("PAD");
        System.out.println(client);
        ClientId clientId = ClientId.valueOf("pad");
        System.out.println(clientId);
    }

    @Test
    public void test2() throws UnsupportedEncodingException {

        Map<String, Object> tokenMap = this.getToken("OLD_SERVER", "OLD_SERVER", "older_server", "111111");
        System.out.println(JsonUtils.obj2Json(tokenMap));

        long s = System.currentTimeMillis();
        for (int i = 0; i < 2; i++) {
            tokenMap = this.refreshToken("OLD_SERVER", "OLD_SERVER", (String) tokenMap.get("refresh_token"));
            System.out.println(JsonUtils.obj2Json(tokenMap));
        }
        long e = System.currentTimeMillis();

        System.out.println(e - s);


    }

    /**
     * 获取token
     *
     * @param clientId
     * @param clientMaster
     * @param username
     * @param password
     * @return
     * @throws UnsupportedEncodingException
     */
    private Map<String, Object> getToken(String clientId, String clientMaster, String username, String password) throws UnsupportedEncodingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        String auth = Base64.getEncoder().encodeToString((clientId + ":" + clientMaster).getBytes("utf8"));
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.add("Authorization", "Basic " + auth);//各个客户端使用自己的认证信息


        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("username", username);
        param.add("password", password);
        param.add("grant_type", "password");
        param.add("scope", "server");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(param, httpHeaders);


        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(url,
                requestEntity, Map.class, param);

        return responseEntity.getBody();
    }

    /**
     * 刷新token
     *
     * @param clientId
     * @param clientMaster
     * @param refreshToken
     * @return
     * @throws UnsupportedEncodingException
     */
    private Map<String, Object> refreshToken(String clientId, String clientMaster, String refreshToken) throws UnsupportedEncodingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        String auth = Base64.getEncoder().encodeToString((clientId + ":" + clientMaster).getBytes("utf8"));
        httpHeaders.add("Authorization", "Basic " + auth);//各个客户端使用自己的认证信息
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");


        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("refresh_token", refreshToken);
        param.add("grant_type", "refresh_token");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(param, httpHeaders);


        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(url,
                requestEntity, Map.class, param);

        return responseEntity.getBody();
    }


    @Test
    public void test() {
        Date date = new Date(2019-1900, 4, 20);
        Date date1 = new Date(2019-1900, 4, 23);
        Date date2 = new Date(2019-1900, 4, 31);
        List<Date> dateList = Arrays.asList(date, date1, date2);
        Date date3 = dateList.stream()
                .max(Comparator.naturalOrder())
                .get();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(format.format(date3));

    }

    @Test
    public void test4() {
        String date = "2019-6-2";
        LocalDate parseDate = LocalDate.parse(date);


        System.out.println(parseDate);
    }

    @Test
    public void test5() {
        int a = 100;
        double b = 5.1;
        Number divide = MathUtils.divide(a, b);
        System.out.println(divide.intValue());
    }

}
