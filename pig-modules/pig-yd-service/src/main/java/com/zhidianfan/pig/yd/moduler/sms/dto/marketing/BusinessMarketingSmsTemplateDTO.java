package com.zhidianfan.pig.yd.moduler.sms.dto.marketing;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author: hzp
 * @Date: 2019-06-12 09:21
 * @Description: dd生日营销短信
 */
@Data
public class BusinessMarketingSmsTemplateDTO {


    private Integer id;
    /**
     * 酒店id
     */
    @NotNull
    private Integer businessId;
    /**
     * 模板标题
     */
    @NotNull
    private String templateTitle;

    @NotNull
    private String templateContent;
    /**
     * 模板变量
     */
    private String templateVariable;
    /**
     * 创建时间
     */
    private Date createAt;
    /**
     * 更新时间
     */
    private Date updateAt;
    /**
     * 是否启用
     */
    @NotNull
    private Integer isEnable;

    private Integer useNum;

    /**
     * 是否自动发送
     */
    @NotNull
    private Integer isAutoSend;

    /**
     * 纪念日类型
     */
    private Integer anniversaryType;

    /**
     * 纪念日对象
     */
    private String anniversaryObj;
    /**
     * 提前天数
     */
    @NotNull
    private Integer advanceDayNum;


    /**
     * 发送时间,安卓电话机固定为十点
     */
    @NotNull
    private String sendTime;


    @NotNull
    private Integer num;
    /**
     * 1 就餐过
     * 0 没有就餐过
     */
    @NotNull
    private Integer isEat;

}
