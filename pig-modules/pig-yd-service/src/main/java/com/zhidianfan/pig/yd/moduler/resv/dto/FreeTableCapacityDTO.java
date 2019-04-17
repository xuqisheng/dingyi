package com.zhidianfan.pig.yd.moduler.resv.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

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
    private  Date resvdate;
}
