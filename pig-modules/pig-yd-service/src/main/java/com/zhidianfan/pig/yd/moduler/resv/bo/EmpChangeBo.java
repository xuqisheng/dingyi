package com.zhidianfan.pig.yd.moduler.resv.bo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2019-03-12
 * @Modified By:
 */
@Data
public class EmpChangeBo {

    /**
     *  修改前酒店id
     */
    @NotNull
    private Integer oldBusinessId;

    /**
     * 新的酒店id
     */
    @NotNull
    private Integer newBusinessId;

    /**
     * 用户id
     */
    @NotNull
    private Integer userId;
}
