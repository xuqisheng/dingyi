package com.zhidianfan.pig.yd.moduler.resv.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/11/2
 * @Modified By:
 */
@Data
public class BusinessDTO {

    @NotNull(message = "id不能为空")
    private Integer id;
    /**
     * 酒店名称
     */
    @ApiModelProperty("酒店名称")
    private String businessName;
    /**
     * 上级id
     */
    private Integer parentId;
    /**
     * 上级名称
     */
    private String parentName;
    /**
     * 酒店手机号
     */
    @ApiModelProperty("联系电话")
    private String businessPhone;
    /**
     * 酒店地址
     */
    @ApiModelProperty("地址")
    private String businessAddress;
    /**
     * 酒店星级
     */
    private Integer businessStar;
    /**
     * 酒店管理者
     */
    private String businessManager;
    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 设备列表
     */
    private String deviceItems;
    /**
     * 配置列表
     */
    private String businessConfigItems;
    /**
     * 串口号
     */
    private String serialPort;
    /**
     * 经销商id
     */
    private Integer agentId;
    /**
     * 经销商名称
     */
    private String agentName;
    /**
     * 城市id
     */
    private Integer cityId;
    /**
     * 省份id
     */
    private Integer provinceId;
    /**
     * 区域id
     */
    private Integer areaId;
    private String provinceName;
    private String cityName;
    private String areaName;
    /**
     * 酒店状态 1启用 0停用
     */
    private String status;
    /**
     * 新增时间
     */
    private Date createdAt;
    /**
     * 修改时间
     */
    private Date updatedAt;
    /**
     * 地址url
     */
    private String localUrl;
    private String licenceUrl;
    /**
     * 版本
     */
    private String softVersion;
    /**
     * 酒店开户时间
     */
    private Date openDate;
    /**
     * 结算价格
     */
    private BigDecimal price;
    /**
     * 年费
     */
    private BigDecimal yearPrice;
    private BigDecimal yearPriceOne;
    private BigDecimal yearPriceTwo;
    private BigDecimal yearPriceThree;
    /**
     * 到期时间
     */
    private Date dueDate;
    private Integer delayDays;
    /**
     * 审批人
     */
    private Date reviewTime;
    /**
     * 审批时间
     */
    private String reviewUser;

    private Integer oldStatus;

    private int page;

    private int rows;

    /**
     * 经度
     */
    @ApiModelProperty("经度")
    private String longitude;

    /**
     * 纬度
     */
    @ApiModelProperty("纬度")
    private String latitude;

    /**
     * logo图
     */
    @ApiModelProperty("logo图")
    private String logoPic;


    @ApiModelProperty("桌位标签")
    private String tableTag;


    @ApiModelProperty("删除的桌位标签")
    private List<String> deleteTableTag;
}
