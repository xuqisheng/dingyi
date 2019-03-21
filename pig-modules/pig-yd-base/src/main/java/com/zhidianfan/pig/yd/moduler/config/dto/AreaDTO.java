package com.zhidianfan.pig.yd.moduler.config.dto;

import lombok.Data;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/11/5
 * @Modified By:
 */
@Data
public class AreaDTO {

    private Integer id;
    private String areaCode;
    private String areaName;
    private String parentCode;
    /**
     * 省市区类型，0省、1市、2区
     */
    private Integer type;
    private Integer soutNum;
}
