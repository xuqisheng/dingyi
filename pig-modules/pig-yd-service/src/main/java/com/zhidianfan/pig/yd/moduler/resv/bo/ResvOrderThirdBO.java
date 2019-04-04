package com.zhidianfan.pig.yd.moduler.resv.bo;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderThird;
import lombok.Data;

/**
 * @Author: huzp
 * @Date: 2018/10/12 14:00
 */
@Data
public class ResvOrderThirdBO extends ResvOrderThird {

    /**
     * 价值id
     */
    private Integer vipValueId;

    /**
     * 价值名称
     */
    private String vipValueName;

    private String orderResult;

    /**
     * 就餐时间
     */
    private String destDate;

    private String businessName;
}
