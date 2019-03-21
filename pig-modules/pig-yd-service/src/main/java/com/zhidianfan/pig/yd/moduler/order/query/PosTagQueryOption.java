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
public class PosTagQueryOption extends BasicQueryOption{
    private final String method = XopMethod.GET_POS_TAG;
}
