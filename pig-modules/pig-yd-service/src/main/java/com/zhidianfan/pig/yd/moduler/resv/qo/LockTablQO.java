package com.zhidianfan.pig.yd.moduler.resv.qo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author: huzp
 * @Date: 2018/11/12 10:39
 * @DESCRIPTION 锁台记录查询条件
 */
@ApiModel(value="锁台记录查询条件")
@Data
public class LockTablQO {

    /**
     * 酒店id
     */
    @ApiModelProperty(value="酒店id")
    @NotNull(message = "酒店id不能为空")
    private Integer businessId;

    /**
     * 桌位名称
     */
    @ApiModelProperty(value="桌位名称")
    private String tableName;

    /**
     * 锁台日期
     */
    @ApiModelProperty(value="锁台时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    private Date lockTime;


    /**
     * 操作内容: 锁台状态
     */
    @ApiModelProperty(value="锁台状态,5为锁台,6为解锁")
    private Integer status;


}
