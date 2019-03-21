package com.zhidianfan.pig.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/11/16
 * @Modified By:
 */

public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    /**
     * 对象转JSON字符串
     *
     * @param o
     * @return
     * @throws JsonProcessingException
     */
    public static String obj2Json(Object o) {

        String jsonStr = null;
        try {
            jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            logger.error("对{}转化json失败", o);
            logger.error(e.getMessage(), e);
        }
        return jsonStr;
    }

    /**
     * JSON字符串转对象
     *
     * @param str
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T jsonStr2Obj(String str, Class<T> t) {
        try {
            return mapper.readValue(str, t);
        } catch (IOException e) {
            logger.error(str + "\n转化成对象失败");
            logger.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * 实体对象转成Map
     * @param obj 实体对象
     * @return
     */
    public static Map<String, Object> object2Map(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    public static JSONObject obj2JsonObj(Object source){
        return JSONObject.parseObject(JSON.toJSONString(source));
    }

    /**
     * 获取JSONObject中所有不为空的值
     *
     * @param obj
     * @param values
     */
    public static void walkRequest(Object obj, List<String> values) {
        if (obj instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) obj;
            Set<Map.Entry<String, Object>> entries = jsonObject.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                walkRequest(entry.getValue(), values);
            }
        } else if (obj instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) obj;
            for (Object o : jsonArray) {
                walkRequest(o, values);
            }
        } else if (obj != null) {
            if (obj instanceof Date) {
                values.add(String.valueOf(((Date) obj).getTime()).toUpperCase());
            } else {
                values.add(obj.toString().toUpperCase());
            }
        }
    }


}
