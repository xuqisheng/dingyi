package com.zhidianfan.pig.yd.moduler.order.query;

import com.zhidianfan.pig.yd.moduler.order.constant.XopMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author danda
 */
@Getter
@Setter
@ToString
public class ReserveListQueryOption extends BasicQueryOption{
	private String method = XopMethod.GET_RESERVE_LIST;
}
