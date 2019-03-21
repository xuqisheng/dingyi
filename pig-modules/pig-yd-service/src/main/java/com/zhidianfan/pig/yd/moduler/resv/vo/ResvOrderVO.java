package com.zhidianfan.pig.yd.moduler.resv.vo;

import com.zhidianfan.pig.yd.moduler.resv.enums.OrderStatus;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/13
 * @Modified By:
 */
@Data
public class ResvOrderVO {
    /**
     * 客户姓名
     */
    private String vipName;
    /**
     * 客户性别,先生  女士
     */
    private String vipSex;
    /**
     * 客户手机号码
     */
    private String vipPhone;
    /**
     * 预定人数
     */
    private int resvNum;
    /**
     * 订单状态
     */
    private String status;
    /**
     * 订单状态文字
     */
    private String statusText;
    /**
     * 到点时间
     */
    private String destTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 订单外部来源
     */
    private String externalSourceName;
    /**
     * 桌位
     */
    private List<TableVO> tables;

    public void setStatus(String status) {
        this.status = status;
        if(StringUtils.equals(this.status, OrderStatus.RESERVE.code)){
            this.status = OrderStatus.RESERVE.label;
        }else if(StringUtils.equals(this.status,OrderStatus.HAVE_SEAT.code)){
            this.statusText = OrderStatus.HAVE_SEAT.label;
        }else if(StringUtils.equals(this.status,OrderStatus.SETTLE_ACCOUNTS.code)){
            this.statusText = OrderStatus.SETTLE_ACCOUNTS.label;
        }
    }
}
