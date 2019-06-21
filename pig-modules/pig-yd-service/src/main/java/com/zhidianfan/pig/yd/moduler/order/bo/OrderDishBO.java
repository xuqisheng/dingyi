package com.zhidianfan.pig.yd.moduler.order.bo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2019-06-04
 * @Modified By:
 */
@Data
public class OrderDishBO {
    /**
     * 成功返回true
     */
    private boolean success;
    /**
     * 代码
     */
    private String code;
    /**
     * 描述
     */
    private String msg;
    /**
     * 响应时间
     */
    private Long ts;
    /**
     * 额外数据
     */
    private Map<String, String> extras;
    /**
     * 业务返回结果
     */
    private List<Result> results;

    @Data
    public static class Result{
        private String menudate;

        private String empno;
        private String tableno;
        private String menu;
        private String srv;
        private String mode;
        private String amount0;
        private String amount;
        private String pccode;
        private List<Map<String,String>> dishes;

    }
}
