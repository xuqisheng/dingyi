package com.zhidianfan.pig.push.controller.dto;

import lombok.Data;

import java.util.Set;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/5
 * @Modified By:
 */
@Data
public class BasicAliasPushDTO {
    private Set<String> alias;
    private String content;
}
