package com.zhidianfan.pig.yd.moduler.order.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PriceBO {
	private Integer id;
	private String unit;
	private String pccode;
	private double price;
	private double hotelid;
	private double inumber;
	private double unit1;
	private double unit2;
}
