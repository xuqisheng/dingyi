package com.zhidianfan.pig.yd.moduler.resv.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: huzp
 * @Date: 2018/12/3 15:05
 * @DESCRIPTION
 */
@ApiModel(value="排队订单修改信息model")
@Data
public class LineInfoDTO {

    /**
     * 排队号
     */
    @ApiModelProperty(value="排队号")
    private String lineNo;

    /**
     * 预订人数
     */
    @ApiModelProperty(value="预订人数")
    private Integer resvNum;

    /**
     * 预定来源id
     */
    @ApiModelProperty(value="预定来源id")
    private Integer resvSourceId;

    /**
     * 来源名称
     */
    @ApiModelProperty(value="预定来源名称")
    private String resvSourceName;

    /**
     * 备注
     */
    @ApiModelProperty(value="订单备注")
    private String remark;

    /**
     * 到店时间
     */
    @ApiModelProperty(value="到店时间")
    private String resvTime;

    /**
     * 安卓电话机用户信息
     */
    @ApiModelProperty(value="安卓用户id")
    private Integer androidUserId;

    @ApiModelProperty(value="安卓用户名")
    private String androidUserName;

}
