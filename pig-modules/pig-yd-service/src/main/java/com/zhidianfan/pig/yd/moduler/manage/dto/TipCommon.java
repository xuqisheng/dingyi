package com.zhidianfan.pig.yd.moduler.manage.dto;

import lombok.Data;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/11/7
 * @Modified By:
 */
@Data
public class TipCommon {
    private Integer code;
    private String msg;
    private String content;

    public TipCommon() {
    }

    public TipCommon(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
