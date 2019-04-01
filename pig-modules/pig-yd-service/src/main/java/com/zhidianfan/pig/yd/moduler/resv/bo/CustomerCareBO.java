package com.zhidianfan.pig.yd.moduler.resv.bo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @Author: hzp
 * @Date: 2019-03-29 11:01
 * @Description: 客户关怀
 */
@Getter
@Setter
public class CustomerCareBO {

    private String id ;
    private String vipName;
    private String vipSex;
    private String vipPhone;
    private String vipValueName;
    private String vipBirthday;
    private String vipBirthdayNl;
    private String birthFlag;
    private String anniversaryDate;
    private String anniversaryTitle;
    private LocalDateTime nexttime;
    private Integer surplusTime;
    /**
     * 0 代表纪念日  1 代表生日
     */
    private Integer type;

}
