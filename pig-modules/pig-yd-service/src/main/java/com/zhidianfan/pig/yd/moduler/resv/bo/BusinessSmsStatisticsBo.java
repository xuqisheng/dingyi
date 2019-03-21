package com.zhidianfan.pig.yd.moduler.resv.bo;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Description: 商户短信统计
 * @User: ljh
 * @Date: 2018-11-09
 * @Time: 09:56
 */
@Data
public class BusinessSmsStatisticsBo {

    @ApiModelProperty("通知类")
    private List<BusinessConsumeBo> notifications;

    @ApiModelProperty("营销类")
    private List<BusinessConsumeBo> marketing;

    @ApiModelProperty("当月通知数量")
    private Integer nowMonthNotification;

    @ApiModelProperty("当月营销数量")
    private Integer nowMonthMarketing;

    @ApiModelProperty("累计消耗通知短信")
    private Integer allNotification;

    @ApiModelProperty("累计消耗营销短信")
    private Integer allMarketing;

    @ApiModelProperty("剩余短信")
    private Integer leftSms;


    @ApiModelProperty("充值短信总量  获赠短信总量")
    private Map allSms;


}
