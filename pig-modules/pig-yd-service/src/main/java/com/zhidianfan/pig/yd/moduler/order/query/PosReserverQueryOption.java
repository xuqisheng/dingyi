package com.zhidianfan.pig.yd.moduler.order.query;

import com.zhidianfan.pig.yd.moduler.order.constant.XopMethod;
import lombok.Data;

@Data
public class PosReserverQueryOption extends BasicQueryOption {
	private final String method = XopMethod.GET_POS_RESERVER;
}
