package com.zhidianfan.pig.yd.moduler.config.dto;

import lombok.Data;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/11/5
 * @Modified By:
 */
@Data
public class DistDTO {
    private Integer id;
    private String key;
    private String value;
    private Integer sortNum;
    /**
     * 0停用  1启用
     */
    private Integer isUse;
    /**
     * 注释
     */
    private String note;
    private Integer parentId;
}
