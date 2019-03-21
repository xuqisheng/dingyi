package com.zhidianfan.pig.yd.moduler.resv.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Description: 消耗短信
 * @User: ljh
 * @Date: 2018-11-08
 * @Time: 15:16
 */
@Data
public class BusinessConsumeBo {

    private Integer num;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone ="GMT+08")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone ="GMT+08")
    private Date endDate;

    /**
     * 1 通知类 2营销类
     */
  //  private Byte type;
}
