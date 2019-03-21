package com.zhidianfan.pig.yd.moduler.resv.dto;

import lombok.Data;

/**
 * @Author: huzp
 * @Date: 2018/11/13 18:51
 * @DESCRIPTION
 */
@Data
public class LineTableDTO {

    private Integer id;
    private String lineNo;

    private String eatTime;
    private String lineSort;
    //预计到达时间
    private String resvTime;
    private String vipName;
    private String vipPhone;
    private String resvNum;
    private String remark;
    private String status;
}
