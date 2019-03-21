package com.zhidianfan.pig.yd.moduler.resv.bo;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderRating;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @User: ljh
 * @Date: 2018-12-20
 * @Time: 13:44
 */
@Data
public class VipMealInfoBo extends ResvOrderRating {

    @ApiModelProperty("次数")
    private Integer times;

    private Integer tableNum;

    @ApiModelProperty("人数")
    private Integer peopleNum;

    private String payAmount;

    @ApiModelProperty("最后一次就餐时间")
    private Date lastTime;

    @ApiModelProperty("最后一次就餐金额")
    private String lastAmount;



}
