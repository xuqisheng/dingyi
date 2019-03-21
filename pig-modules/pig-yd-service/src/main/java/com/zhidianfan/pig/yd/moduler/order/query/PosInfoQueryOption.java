package com.zhidianfan.pig.yd.moduler.order.query;

import com.zhidianfan.pig.yd.moduler.order.constant.XopMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PosInfoQueryOption extends BasicQueryOption{
    private final String method = XopMethod.GET_POS_INFO;
}
