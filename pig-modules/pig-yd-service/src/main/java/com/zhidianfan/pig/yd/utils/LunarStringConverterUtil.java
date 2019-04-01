package com.zhidianfan.pig.yd.utils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static com.zhidianfan.pig.yd.utils.LunarSolarConverter.SolarToLunar;

/**
 * @Author: huzp
 * @Date: 2019年03月01日15:15:12
 * @Desc 农历生日中文字符串转换
 */
public class LunarStringConverterUtil {

    final static String chineseMonthNumber[] = {"正", "二", "三", "四", "五", "六", "七",
            "八", "九", "十", "冬", "腊"};

    final static String chineseDayNumber[] = {"十","一", "二", "三", "四", "五", "六", "七",
            "八", "九"};

    final static  String[] tianGan = {"甲","乙","丙","丁","戊","己","庚","辛","壬","癸"};
    final static  String[] diZhi =   {"子","丑","寅","卯","辰","巳","午","未","申","酉","戌","亥"};


    final static LinkedHashMap<String,Integer> yearMap= new LinkedHashMap<>();

    static {
        for (int i=0;i<60;i++) {
            yearMap.put(tianGan[i%10]+diZhi[(i)%12],i);
        }
    }


    public final static HashMap<String,String> monthMap= new HashMap<>();
    static {
        monthMap.put("十月","10");monthMap.put("九月","09");monthMap.put("八月","08");
        monthMap.put("七月","07");monthMap.put("六月","06");monthMap.put("五月","05");
        monthMap.put("四月","04");monthMap.put("三月","03");monthMap.put("二月","02");
        monthMap.put("正月","01");monthMap.put("冬月","11");monthMap.put("腊月","12");
        monthMap.put("十一月","12");monthMap.put("十二月","12");
    }

    public final static HashMap<String,String> dayMap= new HashMap<>();
    static {
        dayMap.put("十","0");dayMap.put("九","9");dayMap.put("八","8");
        dayMap.put("七","7");dayMap.put("六","6");dayMap.put("五","5");
        dayMap.put("四","4");dayMap.put("三","3");dayMap.put("二","2");
        dayMap.put("一","1");
    }


    /**
     *
     * @param lunarYear 农历年份
     * @return String of Ganzhi: 甲子年
     * Tiangan:甲乙丙丁戊己庚辛壬癸<br/>Dizhi: 子丑寅卯辰巳无为申酉戌亥
     */
    public static String lunarYearToGanZhi(int lunarYear){
        return tianGan[(lunarYear-4) % 10]+diZhi[(lunarYear-4) % 12];
    }

    /**
     *
     * @param luanrmonth 农历 月
     * @return 返回冬月之类的农历说法
     */
    public static String getLunarMonthString(int luanrmonth) {
        if (luanrmonth<0 || luanrmonth>12){
            throw new RuntimeException("输入农历月份错误");
        }

        return chineseMonthNumber[luanrmonth-1]+"月";
    }

    /**
     *
     * @param luanrday 农历 日
     * @return 初一之类的农历说法
     */
    public static String getLunarDayString(int luanrday) {
        String chineseTen[] = {"初", "十", "廿","三"};
        if (luanrday == 10){
            return "初十";
        }
        int n = luanrday % 10;
        return chineseTen[luanrday / 10] + chineseDayNumber[n];
    }

    /**
     *
     * @param lunarYear 农历年份
     * @param luanrmonth 农历月份
     * @param luanrday 农历日期
     * @return 返回农历口语字符串
     */
    public static String getLunarChinaString(int lunarYear,int luanrmonth,int luanrday){
        return lunarYearToGanZhi(lunarYear)+getLunarMonthString(luanrmonth)+getLunarDayString(luanrday);
    }



    /**
     *
     * @param lunarYear 农历年份
     * @return String of Ganzhi: 甲子年
     * Tiangan:甲乙丙丁戊己庚辛壬癸<br/>Dizhi: 子丑寅卯辰巳无为申酉戌亥
     */
    public static String GanZhiTolunarYear(String lunarYear){

        LocalDate localDate= LocalDate.now();
        Solar solar = new Solar();
        solar.solarYear = localDate.getYear();
        solar.solarMonth = localDate.getMonthValue();
        solar.solarDay = localDate.getDayOfMonth();

        Lunar lunar = SolarToLunar(solar);

        String todayTD = lunarYearToGanZhi(lunar.lunarYear);
        Integer todaykey = yearMap.get(todayTD);
        Integer anskey = yearMap.get(lunarYear);
        if (anskey == null){
            return "";
        }
        int i = todaykey - anskey;
        i = (i<0 ? i+60 : i);
        return String.valueOf((lunar.lunarYear - i));
    }


    /**
     * 根据字符串月份返回数字月份
     * @param luanrmonth 农历字符串月份
     * @return 数字表示的月份
     */
    public static String getLunarMonth(String luanrmonth) {
        String month = monthMap.get(luanrmonth);
        return month==null ? "" : month;
    }

    /**
     * 农历日转为数字表示
     * @param luanrday 农历日 初十之类的日期
     * @return 对应的数字日期
     */
    public static String getLunarDay(String luanrday) {

        if (luanrday.equals("初十")){
            return "10";
        }

        StringBuffer day =new StringBuffer();
        String dayhead = luanrday.substring(0, 1);

        switch (dayhead){
            case "初":
                day.append(0);
                break;
            case  "十" :
                day.append(1);
                break;
            case "廿" :
                day.append(2);
                break;
            case "三" :
                day.append(3);
                break;
            default:
                return "";
        }

        String dayTail = dayMap.get(luanrday.substring(1, 2));
        if (dayTail == null){
            return "";
        }
        day.append(dayTail);
        return day.toString();
    }


    public static String getLunar(String GanZhi,String luanrmonth,String luanrday){
        return GanZhiTolunarYear(GanZhi)+"-"+getLunarMonth(luanrmonth)+"-"+getLunarDay(luanrday);
    }

    public static String getLunarWithoutGZ(String luanrmonth,String luanrday){
        return getLunarMonth(luanrmonth)+"-"+getLunarDay(luanrday);
    }


}
