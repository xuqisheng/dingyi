package com.zhidianfan.pig.yd.moduler.resv.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: hzp
 * @Date: 2019-06-17 15:20
 * @Description:
 */
@Data
public class PerformanceBO {

    /**
     * 订单条数
     */
    private Integer amount;


    private String clientName;
    /**
     * 对应状态
     */
    private String stype;

}
