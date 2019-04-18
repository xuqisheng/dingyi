package com.zhidianfan.pig.yd.moduler.resv.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: hzp
 * @Date: 2019-04-17 15:37
 * @Description:
 */
@Data
public class FreeTableCapacityDTO {

    private Integer businessid;
    private Integer mealtypeid;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    private  Date resvdate;
}
