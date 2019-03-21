package com.zhidianfan.pig.yd.gen;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhidianfan.pig.yd.moduler.push.dto.OpenIdDTO;
import com.zhidianfan.pig.yd.moduler.push.dto.OpenIdResultDTO;
import org.junit.Test;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestTemplate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sjl
 * 2019-03-18 10:47
 */
public class BeanToMapTest {

    @Test
    public void testBeanToMap() throws IllegalAccessException, NoSuchMethodException {
        OpenIdDTO dto = new OpenIdDTO()
                .setSecret("1111")
                .setAppid("22222")
                .setCode("33333");
        Class<? extends OpenIdDTO> dtoClass = dto.getClass();
        Map<String, String> map = new HashMap<>(4);
        Field[] fields = dto.getClass().getDeclaredFields();
        String key, value;
        for (Field field : fields) {
            field.setAccessible(true);
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof JsonProperty) {
                    key = ((JsonProperty) annotation).value();
                } else {
                    key = field.getName();
                }
            }
//            field.get()
        }

        System.err.println(map);

    }

    @Test
    public void testMap() {
        OpenIdResultDTO dto = JSON.parseObject("{\"session_key\":\"sFzeZpUFGKPi9LpQr4IgVA==\",\"openid\":\"o_DDX5coMSocvfeNsrpp86PXXy-8\"}", OpenIdResultDTO.class);
        System.err.println(dto);
    }


}
