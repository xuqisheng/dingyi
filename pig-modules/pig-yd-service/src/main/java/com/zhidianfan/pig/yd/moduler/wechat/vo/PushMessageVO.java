package com.zhidianfan.pig.yd.moduler.wechat.vo;

import com.zhidianfan.pig.yd.moduler.wechat.util.OrderTemplate;
import lombok.Data;

/**
 * 推送消息对象
 *
 * @author wangyz
 * @version v 0.1 2019-04-15 16:54 wangyz Exp $
 */
@Data
public class PushMessageVO {

    /**
     * 性别
     */
    private String sex;
    /**
     * 姓名
     */
    private String name;
    /**
     * 日期
     */
    private String date;
    /**
     * 电话
     */
    private String phone;
    /**
     * openId
     */
    private String openId;
    /**
     * 桌位区域
     */
    private String tableArea;
    /**
     * 预定人数
     */
    private Integer personNum;
    /**
     * 酒店名称
     */
    private String businessName;
    /**
     * 订单模板
     */
    private OrderTemplate orderTemplate;
}
