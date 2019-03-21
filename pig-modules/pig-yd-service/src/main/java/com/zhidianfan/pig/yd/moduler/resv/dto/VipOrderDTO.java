package com.zhidianfan.pig.yd.moduler.resv.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2019-01-25
 * @Modified By:
 */
@Data
public class VipOrderDTO {
    /**
     * vip id
     */
    private Integer vipId;

    /**
     * 酒店id
     */
    private Integer businessId;

    /**
     * 开始统计日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginDate;
}
