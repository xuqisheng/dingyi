package com.zhidianfan.pig.yd.utils;

import lombok.Data;

/**
 * @Author: huzp
 * @Date: 2019年03月01日15:14:54
 */
@Data
public class Lunar {
    public boolean isleap;
    public int lunarDay;
    public int lunarMonth;
    public int lunarYear;

    public Lunar() {
    }

    public Lunar(int lunarYear , int lunarMonth, int lunarDay) {
        this.lunarDay = lunarDay;
        this.lunarMonth = lunarMonth;
        this.lunarYear = lunarYear;
    }

    public Lunar(int lunarYear , int lunarMonth, int lunarDay,boolean isleap) {
        this.lunarDay = lunarDay;
        this.lunarMonth = lunarMonth;
        this.lunarYear = lunarYear;
        this.isleap = isleap;
    }


    @Override
    public String toString() {
        String month;
        if (lunarMonth < 10) {
            month = "0" + lunarMonth;
        } else {
            month = String.valueOf(lunarMonth);
        }
        String day;
        if (lunarDay < 10) {
            day = "0" + lunarDay;
        } else {
            day = String.valueOf(lunarDay);
        }

        return "" + lunarYear + "-" + month + "-" + day;
    }
}

