package com.zhidianfan.pig.yd.moduler.order.query;

import com.zhidianfan.pig.yd.moduler.order.constant.XopMethod;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2019-06-04
 * @Modified By:
 */
@Data
@Accessors(chain = true)
public class TableMenuOption extends BasicQueryOption {
    private final String method = XopMethod.GET_TABLE_MENU;
}
