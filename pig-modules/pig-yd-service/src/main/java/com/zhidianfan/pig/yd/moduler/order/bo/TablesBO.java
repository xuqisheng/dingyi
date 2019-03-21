package com.zhidianfan.pig.yd.moduler.order.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * 获取餐饮营业点
 *
 * @author mikrotik
 */
@Getter
@Setter
@ToString
public class TablesBO {
    private boolean success;
    private List<Map<String,String>> results;
    private Long ts;
}
