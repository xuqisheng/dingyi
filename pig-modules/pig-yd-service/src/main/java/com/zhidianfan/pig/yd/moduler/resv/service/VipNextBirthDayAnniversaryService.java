package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.netflix.discovery.converters.Auto;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Anniversary;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Vip;
import com.zhidianfan.pig.yd.moduler.common.service.IAnniversaryService;
import com.zhidianfan.pig.yd.moduler.common.service.IVipService;
import com.zhidianfan.pig.yd.utils.Lunar;
import com.zhidianfan.pig.yd.utils.LunarSolarConverter;
import com.zhidianfan.pig.yd.utils.Solar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import static com.zhidianfan.pig.yd.utils.LunarSolarConverter.SolarToLunar;

/**
 * @author huzp
 * @date 2019/1/21 0021 15:13
 * @description
 */
@Service
@Slf4j
public class VipNextBirthDayAnniversaryService {

    @Autowired
    IVipService iVipService;

    @Autowired
    IAnniversaryService iAnniversaryService;


    /**
     * 计算顾客下一次生日
     */
    @Async
    public void calNextBirthday() {

        //查询下次生日为null的或者已经过了的客户
        List<Vip> vips = iVipService.getPastBirthdayVip();

        //根据他过得是农历生日还是公历生日计算他的下一次生日的公历日期
        for (Vip vip : vips) {
            try {
                Integer birthFlag = vip.getBirthFlag();
                //如果过农历
                if (birthFlag == 1) {
                    //转为农历类型
                    Solar solar = LunarStr2NextSolar(vip.getVipBirthdayNl());
                    LocalDate parse = LocalDate.parse(solar.toString());
                    vip.setNextVipBirthday(localDate2Date(parse));
                } else {
                    //如果过公历
                    Solar nextSolarBirth = nextSolarTime(vip.getVipBirthday());
                    LocalDate parse = LocalDate.parse(nextSolarBirth.toString());
                    vip.setNextVipBirthday(localDate2Date(parse));
                }
                iVipService.update(vip, new EntityWrapper<Vip>().eq("id", vip.getId()));
            }catch (Exception e){
                log.error("更新下次生日数据错误:" +vip);
            }finally {
                continue;
            }
        }


    }

    @Async
    public void calNextAnniversary() {

        //根据他过得是农历纪念日还是公历纪念日计算他的下一次纪念日的公历日期
        List<Anniversary> pastAnniversaryVip = iAnniversaryService.getPastAnniversaryVip();
        for (Anniversary anniversary : pastAnniversaryVip) {
            try {
            if (anniversary.getCalendarType() == 1) {
                // 1. 获取公历日期
                LocalDate solarDate = date2LocalDate(anniversary.getAnniversaryDate());
                //时间转为solar类
                Solar anniversaryDate = new Solar(solarDate.getYear(), solarDate.getMonth().getValue(), solarDate.getDayOfMonth());
                // 获取公历转为农历
                Lunar lunar = SolarToLunar(anniversaryDate);

                Solar solar = LunarStr2NextSolar(lunar.toString());
                LocalDate parse = LocalDate.parse(solar.toString());
                anniversary.setNextAnniversaryTime(localDate2Date(parse));
            } else {
                //如果过公历
                Solar nextSolarBirth = nextSolarTime(date2LocalDate(anniversary.getAnniversaryDate()).toString());
                LocalDate parse = LocalDate.parse(nextSolarBirth.toString());
                anniversary.setNextAnniversaryTime(localDate2Date(parse));
            }
            iAnniversaryService.update(anniversary, new EntityWrapper<Anniversary>().eq("id", anniversary.getId()));
            }catch (Exception e){
                log.error("更新下次纪念日数据错误:" +anniversary);
            }finally {
                continue;
            }
        }

    }


    //农历日期转为下一次公历日期
    public static Solar LunarStr2NextSolar(String LunarStr) {
        Lunar lunar = parseLunarText2Lunar(LunarStr);
        Solar solar = LunarSolarConverter.LunarToSolar(lunar);
        Lunar nextLunarTime = nextLunarTime(solar.toString());
        Solar nextSolarTime = LunarSolarConverter.LunarToSolar(nextLunarTime);
        return nextSolarTime;
    }


    //计算下一次农历时间
    public static Lunar nextLunarTime(String solarStr) {
        LocalDate now = LocalDate.now();
        Solar solar = parseText2Solar(solarStr);
        Lunar lunar = SolarToLunar(solar);

        //2.计算今天的农历
        Solar todaySolar = parseText2Solar(now.toString());
        Lunar todayLunar = SolarToLunar(todaySolar);

        Lunar lastLunarBirth;
        //3.判断今天农历的月日与转换出的农历的月日
        //4.如果今天农历月日大于输入的农历的月日,计算转换的农历的月日的下一年的农历 (今天的农历年+1 在append 转化的月日)
        if ((todayLunar.getLunarMonth() > lunar.getLunarMonth())
                || ((todayLunar.getLunarMonth() == lunar.getLunarMonth()) && (todayLunar.getLunarDay() > lunar.getLunarDay()))) {
            lastLunarBirth = new Lunar(todayLunar.getLunarYear() + 1, lunar.getLunarMonth(), lunar.getLunarDay());
        } else {
            lastLunarBirth = new Lunar(todayLunar.getLunarYear(), lunar.getLunarMonth(), lunar.getLunarDay());
        }
        return lastLunarBirth;
    }

    //计算下一次公历时间
    public static Solar nextSolarTime(String solarStr) {
        LocalDate now = LocalDate.now();
        Solar lastSolarBirth;
        //过阳历的话判断今天的月日是否大于传入的月日
        LocalDate parse = LocalDate.parse(solarStr);
        if ((now.getMonth().getValue() > parse.getMonth().getValue())
                || ((now.getMonth().getValue() == parse.getMonth().getValue()) && (now.getDayOfMonth() > parse.getDayOfMonth()))) {
            if(parse.getMonth().getValue() == 2 && parse.getDayOfMonth() == 29){
                int leapYear = isLeapYear(now.getYear()+1);
                lastSolarBirth = new Solar(leapYear, parse.getMonth().getValue(), parse.getDayOfMonth());
            }else {
                lastSolarBirth = new Solar(now.getYear() + 1, parse.getMonth().getValue(), parse.getDayOfMonth());
            }
        } else {
            //如果是二月 29号的话 ,计算今年是否闰年,不是则计算下一个闰年
            if(parse.getMonth().getValue() == 2 && parse.getDayOfMonth() == 29){
                int leapYear = isLeapYear(now.getYear());
                lastSolarBirth = new Solar(leapYear, parse.getMonth().getValue(), parse.getDayOfMonth());
            }else {
                lastSolarBirth = new Solar(now.getYear(), parse.getMonth().getValue(), parse.getDayOfMonth());
            }
        }

        return lastSolarBirth;
    }


    //阳历字符串转为农历生日字符串
    public static Lunar getLunarBirthDay(String time) {
        Solar solar = parseText2Solar(time);
        Lunar lunar = SolarToLunar(solar);
        return lunar;
    }

    /**
     * 转化字符串为Solar
     *
     * @param time 日期
     * @return Solar
     */
    public static Solar parseText2Solar(String time) {
        LocalDate parse = LocalDate.parse(time);
        Solar solar = new Solar(parse.getYear(), parse.getMonth().getValue(), parse.getDayOfMonth());
        return solar;
    }

    /**
     * 转化 农历字符串为农历
     *
     * @param time 字符串
     * @return
     */
    public static Lunar parseLunarText2Lunar(String time) {
        log.info(time);
        String[] split = time.split("-");
//        LocalDate parse = LocalDate.parse(time);
        Lunar lunar = new Lunar(Integer.valueOf(split[0]), Integer.valueOf(split[1]), Integer.valueOf(split[2]));
        return lunar;
    }


    /**
     * LocalDate转Date
     *
     * @param localDate
     * @return
     */
    public static Date localDate2Date(LocalDate localDate) {
        if (null == localDate) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * Date转LocalDate
     *
     * @param date
     */
    public static LocalDate date2LocalDate(Date date) {
        if (null == date) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }


    /**
     * 当前年份之后的第一个闰年
     *
     * @param year
     * @return
     */
    public static int isLeapYear(int year) {
        int temp = year;
        while (true) {//知道找到闰年才结束循环
            if ((temp % 400 == 0) || (temp % 4 == 0 && temp % 100 != 0)) break;
            else temp += 1;
        }
        return temp;
    }

}
