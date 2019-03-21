package com.zhidianfan.pig.yd.moduler.order.query;

import com.zhidianfan.pig.yd.moduler.order.constant.XopMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 获取餐饮营业点
 *
 * @author mikrotik
 */
@Getter
@Setter
@ToString
public class PosPcCodeQueryOption extends BasicQueryOption{
    private final String method = XopMethod.GET_POS_PC_CODES;
}
