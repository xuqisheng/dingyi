package com.zhidianfan.pig.yd.moduler.resv.dto;


import com.zhidianfan.pig.yd.moduler.resv.bo.OrderTableBo;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 预订单修改
 * @author LJH
 * @version 1.0
 * @Date 2018/9/20 10:18
 */

@Data
public class ReserveOrderEditDTO {


    /**
     * 批次号
     */
    @NotBlank(message = "批次号不能为空")
    private String batchNo;


    /**
     * 是否退订 所有桌位
     */
    private Boolean debook;


    /**
     * 预定人数
     */
    private String resvNum;

    /**
     * 外部来源
     */
    private String externalSourceName;


    private Integer externalSourceId;

    /**
     * 订单备注
     */
    private String remark;

    /**
     * 桌位id 列表 (所有选中的都要传过来)
     */
    @NotNull(message = "桌位信息不能为空")
    private List<OrderTableBo> orderTables;

    private String destTime;

    private String tag;

 }
