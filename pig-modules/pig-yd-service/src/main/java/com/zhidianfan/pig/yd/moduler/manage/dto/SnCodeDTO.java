package com.zhidianfan.pig.yd.moduler.manage.dto;

import io.swagger.models.auth.In;
import lombok.Data;

import java.util.List;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/11/21
 * @Modified By:
 */
@Data
public class SnCodeDTO {

    private Long id;
    /**
     * 设备码
     */
    private String code;

    /**
     * 经销商id
     */
    private Long agentId;

    private String agentName;

    private String operator;

    private String remark;
    /**
     * 酒店id
     */
    private Long businessId;

    private String businessName;
    /**
     * 1入库 2绑定 3出库
     */
    private String status;

    private Integer operatorStatus;

    private String operatorStatusName;

    private String startTime;

    private String endTime;

    private String snCodes;

    private Integer bind;

    private String sidx;

    private String sord;

    private List<String> snCodeList;

    private String agentName1;

    private int page;

    private int rows;

}
