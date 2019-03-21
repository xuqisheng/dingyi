package com.zhidianfan.pig.yd.moduler.push.ws.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2019-01-21
 * @Modified By:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class HttpCommonRes implements Serializable {
    private int code;
    private String msg;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object body;


    public static final HttpCommonRes SUCCESS = new HttpCommonRes(200, "成功", null);

    public static final HttpCommonRes ERROR = new HttpCommonRes(500, "失败", null);

    //不允许new
    private HttpCommonRes() {
    }


}
