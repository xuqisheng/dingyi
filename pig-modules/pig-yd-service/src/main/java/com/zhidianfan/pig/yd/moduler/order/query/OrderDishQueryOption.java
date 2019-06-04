package com.zhidianfan.pig.yd.moduler.order.query;

import com.zhidianfan.pig.yd.moduler.order.constant.XopMethod;
import lombok.Data;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2019-06-04
 * @Modified By:
 */
@Data
public class OrderDishQueryOption extends BasicQueryOption {
    private String method = XopMethod.GET_ORDER_DISH;
}
