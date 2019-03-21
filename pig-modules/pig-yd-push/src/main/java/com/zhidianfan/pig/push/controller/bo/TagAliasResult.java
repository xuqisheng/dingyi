package com.zhidianfan.pig.push.controller.bo;

import lombok.Data;

import java.util.List;

/**
 * 设备的别名与标签
 */
@Data
public class TagAliasResult{

    private List<String> tags;

    private String alias;

    private String mobile;
        
}
