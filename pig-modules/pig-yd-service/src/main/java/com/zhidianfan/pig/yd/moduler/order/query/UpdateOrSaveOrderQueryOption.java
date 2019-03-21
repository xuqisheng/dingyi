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
public class UpdateOrSaveOrderQueryOption extends BasicQueryOption{
    private final String method = XopMethod.SAVE_POS_RES_SERVE;
}
