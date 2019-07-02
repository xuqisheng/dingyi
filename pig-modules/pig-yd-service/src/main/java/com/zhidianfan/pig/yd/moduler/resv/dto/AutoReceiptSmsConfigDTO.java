package com.zhidianfan.pig.yd.moduler.resv.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: hzp
 * @Date: 2019-06-13 14:39
 * @Description:
 */
@Data
public class AutoReceiptSmsConfigDTO {


    private Integer id;
    /**
     * 酒店id
     */
    @NotNull
    private Integer businessId;
    /**
     * 酒店名字
     */
    private String businessName;
    /**
     * 是否自动发送短信,1发送 0 不发送
     */
    @NotNull
    private Integer status;


}
