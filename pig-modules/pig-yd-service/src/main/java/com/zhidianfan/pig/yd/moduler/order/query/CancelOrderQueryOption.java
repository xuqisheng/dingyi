package com.zhidianfan.pig.yd.moduler.order.query;

import com.zhidianfan.pig.yd.moduler.order.constant.XopMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author mikrotik
 */
@Getter
@Setter
@ToString
public class CancelOrderQueryOption extends BasicQueryOption{
    private final String method = XopMethod.CANCEL_POS_RESERVE;
}
