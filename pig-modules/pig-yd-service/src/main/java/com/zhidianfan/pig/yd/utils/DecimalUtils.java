package com.zhidianfan.pig.yd.utils;

import java.text.DecimalFormat;

/**
 * @author danda
 */
public class DecimalUtils {
	private static DecimalFormat decimalFormat = new DecimalFormat();
	public static String format(String pattern,Integer number){
		decimalFormat.applyPattern(pattern);
		return decimalFormat.format(number);
	}
}
