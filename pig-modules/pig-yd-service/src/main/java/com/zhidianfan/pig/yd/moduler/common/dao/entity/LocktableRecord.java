package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2019-03-07
 * @Modified By:
 */
@TableName("locktable_record")
@Data
public class LocktableRecord extends Model<LocktableRecord> {

    @ApiModelProperty(value="锁台状态名称")
    private String statusName;

    @ApiModelProperty(value="订单号")
    private String resvOrder;

    @ApiModelProperty(value="区域名称")
    private String tableAreaName;

    @ApiModelProperty(value="桌位名称")
    private String tableName;

    @ApiModelProperty(value="餐别名称")
    private String mealTypeName;

    @ApiModelProperty(value="桌子被锁日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    private Date resvDate;

    @ApiModelProperty(value="备注")
    private String remark;

    @ApiModelProperty(value="创建日期")
    private Date  logsCreateTime;

    @ApiModelProperty(value="操作人")
    private String operationName;

    /**
     * 主键值
     */
    @Override
    protected Serializable pkVal() {
        return 1;
    }
}
