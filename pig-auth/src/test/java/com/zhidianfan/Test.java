package com.zhidianfan;


import java.io.IOException;
import java.util.Map;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2019-03-05
 * @Modified By:
 */

public class Test {
    @org.junit.Test
    public void test1() throws IOException {
        org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();

        mapper.readValue("{\"PAD\":\"PAD\"}", Map.class);
    }
}
