package com.zhidianfan.pig.yd.moduler.resv.dto;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.Vip;
import lombok.Data;

/**
 * @Author: hzp
 * @Date: 2019-06-26 13:14
 * @Description:
 */
@Data
public class VipAllergenDTO  extends Vip {

    private String allergen;
}
