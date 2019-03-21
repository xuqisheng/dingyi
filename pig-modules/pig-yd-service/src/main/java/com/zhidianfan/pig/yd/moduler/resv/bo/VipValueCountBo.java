package com.zhidianfan.pig.yd.moduler.resv.bo;


 import lombok.Data;

import java.util.List;

@Data
/**
 * 价值客户统计
 */
public class VipValueCountBo {

   private Integer sum;

   private List<VipValueBo> vipValues;


}
