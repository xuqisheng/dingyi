package com.zhidianfan.pig.yd.utils;

import lombok.Data;

/**
 *
 */
@Data
public class Solar {
	public int solarDay;
	public int solarMonth;
	public int solarYear;

	public Solar() {
	}

	public Solar(int solarYear ,  int solarMonth,int solarDay) {
		this.solarDay = solarDay;
		this.solarMonth = solarMonth;
		this.solarYear = solarYear;
	}

	@Override
	public String toString() {

		String month;
		if (solarMonth < 10){
			month = "0"+ solarMonth;
		}else {
			month = String.valueOf(solarMonth);
		}
		String day;
		if (solarDay < 10) {
			day = "0" + solarDay;
		} else {
			day = String.valueOf(solarDay);
		}

		return ""+solarYear+"-"+month+"-"+day;
	}
}

