package com.zhidianfan.pig.yd.moduler.resv.service;


import com.zhidianfan.pig.yd.moduler.common.dao.entity.Vip;
import com.zhidianfan.pig.yd.moduler.common.service.IVipService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.zhidianfan.pig.yd.utils.LunarStringConverterUtil.*;


/**
 * @Author: hzp
 * @Date: 2019年03月01日11:18:24
 */
@Slf4j
@Service
public class CorrectBrthdayService {


    @Autowired
    private IVipService iVipService;


    /**
     * 修正数据库中顾客的错误生日数据
     */
    public void CorrectVipBirth() {

        //1. 处理阳历数据(只是处理几种特定的格式)
        //....最后处理更新掉没用的数据为空,且忽略年份,生日农阳历选择为null(因为接下来的农历处理会将生日选择为农历)

        long begin = System.currentTimeMillis();
        log.info("开始处理"+begin);

        //每次1000条
        //1.1 处理 1989.10.02 00:00:00   每次筛选出1000条特定的不合理的阳历数据进行处理  birthflag 为 0,年份小于2003的话,忽略年份
        while (true) {
            List<Vip> vips1 = iVipService.selectBirthType1();
            for (int i = 0; i < vips1.size(); i++) {
                String correctBirth = correctSecond(vips1.get(i).getVipBirthday());
                vips1.get(i).setVipBirthday(correctBirth);
                otherBirthDataCorrect(vips1.get(i), correctBirth, 0);
            }
            if (vips1.size() != 0) {

                iVipService.updateBatchById(vips1);
            }
            if (vips1.size() < 1000) {
                break;
            }
        }

        //1.2 处理 1989.1.02 与 2000.10.10 每次筛选出1000条特定的不合理的阳历数据进行处理  birthflag 为 0,年份小于2003的话,忽略年份
        while (true) {
            List<Vip> vips2 = iVipService.selectBirthType2();
            for (int i = 0; i < vips2.size(); i++) {
                String correctBirth = correctWithoutZero(vips2.get(i).getVipBirthday(), ".");
                vips2.get(i).setVipBirthday(correctBirth);
                otherBirthDataCorrect(vips2.get(i), correctBirth, 0);
            }
            if (vips2.size() != 0) {
                iVipService.updateBatchById(vips2);
            }
            if (vips2.size() < 1000) {
                break;
            }
        }


        //1.3 处理 1989-1-02  每次筛选出1000条特定的不合理的阳历数据进行处理  birthflag 为 0,年份小于2003的话,忽略年份
        while (true) {
            List<Vip> vips3 = iVipService.selectBirthType3();
            for (int i = 0; i < vips3.size(); i++) {
                String correctBirth = correctWithoutZero(vips3.get(i).getVipBirthday(), "-");
                vips3.get(i).setVipBirthday(correctBirth);
                otherBirthDataCorrect(vips3.get(i), correctBirth, 0);
            }
            if (vips3.size() != 0) {

                iVipService.updateBatchById(vips3);
            }
            if (vips3.size() < 1000) {
                break;
            }
        }

        //1.4 置空其他阳历生日数据
        while (true) {
            List<Vip> vips6 = iVipService.selectBirthType4();
            for (int i = 0; i < vips6.size(); i++) {
                //因为575酒店和895 酒店数据可以手动修正,所以不批量置空
                if (vips6.get(i).getBusinessId().equals(575) || vips6.get(i).getBusinessId().equals(895)){
                    continue;
                }
                vips6.get(i).setVipBirthday("");
            }
            if (vips6.size() != 0) {

                iVipService.updateBatchById(vips6);
            }
            if (vips6.size() < 1000) {
                break;
            }
        }


        //处理不了的情况就清空 处理不了返回值为空字符串
        //2. 处理农历数据
        //2.1 处理己未,羊,八月,十三 为1979-08-13 格式 birthflag 为 1,年份小于2003的话,忽略年份
        while (true) {
            List<Vip> vips4 = iVipService.selectBirthLunarType1();
            for (int i = 0; i < vips4.size(); i++) {
                String vipBirthdayNl = vips4.get(i).getVipBirthdayNl();
                String[] split = vipBirthdayNl.split(",");
                String lunar = getLunar(split[0], split[2], split[3]);
                vips4.get(i).setVipBirthdayNl(lunar);
                //因为数据库中有农历数据的birth_flag 基本也都为0 所以暂时都默认为过公历生日
                otherBirthDataCorrect(vips4.get(i), lunar, 0);
            }
            if (vips4.size() != 0) {

                iVipService.updateBatchById(vips4);
            }
            if (vips4.size() < 1000) {
                break;
            }
        }


        //2.2 处理 壬戌,狗,闰四月,廿九 1982-04-29,闰 格式 birthflag 为 1,年份小于2003的话,忽略年份
        while (true) {
            List<Vip> vips5 = iVipService.selectBirthLunarType2();
            for (int i = 0; i < vips5.size(); i++) {
                String vipBirthdayNl = vips5.get(i).getVipBirthdayNl();
                String[] split = vipBirthdayNl.split(",");
                String lunar = getLunar(split[0], split[2].substring(1, 3), split[3]);
                //设置为闰月
                vips5.get(i).setIsLeap(1);
                vips5.get(i).setVipBirthdayNl(lunar);

                //因为数据库中有农历数据的birth_flag 基本也都为0 所以暂时都默认为过公历生日
                otherBirthDataCorrect(vips5.get(i), lunar, 0);
            }
            if (vips5.size() != 0) {
                iVipService.updateBatchById(vips5);
            }
            if (vips5.size() < 1000) {
                break;
            }
        }

        //2.3 置空其他不符合yyyy-mm-dd 的 数据
        while (true) {
            List<Vip> vips7 = iVipService.selectBirthLunarType3();
            for (int i = 0; i < vips7.size(); i++) {
                vips7.get(i).setVipBirthdayNl("");
            }
            if (vips7.size() != 0) {
                iVipService.updateBatchById(vips7);
            }
            if (vips7.size() < 1000) {
                break;
            }
        }

        long end = System.currentTimeMillis();
        log.info("开始完成"+(end - begin));

    }


    /**
     * 更新575,895这两家酒店的客户数据
     */
    public void CorrectSpecificotelHVipBirth(){


        //处理575东兴酒店
        List<Vip> vips1 = iVipService.selectBirthByBusinessId(575);
        for (int i = 0; i < vips1.size(); i++) {

            String vipBirthday = vips1.get(i).getVipBirthday();
            System.out.println(vipBirthday);
            int length = vipBirthday.length();
            int site = vipBirthday.indexOf("月") +1 ;
            String s1 = vipBirthday.substring(0, site);
            String s2 = vipBirthday.substring(site, length);
            String lunarMonth = monthMap.get(s1);
            String replace;
            if (s2.contains("二十") && length == 4){

                 replace = s2.replace("二十", "廿十");

            }else {

                 replace = s2.replace("二十", "廿");
            }
            String lunarDay = getLunarDay(replace);
            vips1.get(i).setVipBirthday("");
            vips1.get(i).setVipBirthdayNl("2018-"+lunarMonth+"-"+lunarDay);
            vips1.get(i).setHideBirthdayYear(1);
            vips1.get(i).setBirthFlag(1);
        }
        iVipService.updateBatchById(vips1);




        List<Vip> vips2 = iVipService.selectBirthByBusinessId(895);
        for (int i = 0; i < vips2.size(); i++) {
            String vipBirthday = vips2.get(i).getVipBirthday();
            int length = vipBirthday.length();
            int site = vipBirthday.indexOf("月") +1 ;
            String s1 = vipBirthday.substring(2, site);
            String lunarMonth = monthMap.get(s1);
            String s2 = vipBirthday.substring(site, length);
            String replace;
            if (s2.contains("二十") && length == 6){
                replace = s2.replace("二十", "廿十");
            }else {
                replace = s2.replace("二十", "廿");
            }
            String lunarDay = getLunarDay(replace);
            vips2.get(i).setVipBirthday("");
            vips2.get(i).setVipBirthdayNl("2018-"+lunarMonth+"-"+lunarDay);
            vips2.get(i).setHideBirthdayYear(1);
            vips2.get(i).setBirthFlag(1);
        }
        iVipService.updateBatchById(vips2);


    }


    /**
     * 更新阳历生日
     * 1.1989.10.02 00:00:00 去除时分秒,修改为yy-mm-dd 形式 有16000条
     */

    public String correctSecond(String birthday) {

        String substring = birthday.substring(0, 10);
        String replace = substring.replace('.', '-');
        System.out.println(replace);

        return replace;
    }

    /**
     * 更新阳历生日
     * 去除用.分隔 且一号这种日期不带0的
     * <p>
     * 去除用-分隔 且一号这种日期不带0的
     */

    public String correctWithoutZero(String birthday, String mark) {
        int year, month, day;

        String[] split;
        if (mark.equals(".")) {

            split = birthday.split("\\.");
        } else if (mark.equals("-")) {
            split = birthday.split("-");
        } else {
            return "";
        }
        List<String> strings = Arrays.asList(split);
        year = Integer.valueOf(strings.get(0));
        month = Integer.valueOf(strings.get(1));
        day = Integer.valueOf(strings.get(2));
        if (day ==  0){
            day = 1;
        }

        LocalDate localDate = LocalDate.of(year, month, day);

        return localDate.toString();
    }


    /**
     * /设置是否忽略年份, 生日过农历还是阳历
     *
     * @param vip          设置的vip 对象
     * @param BirthString  生日字符串
     * @param LunarOrSolar 公历0 农历1
     */
    public void otherBirthDataCorrect(Vip vip, String BirthString, Integer LunarOrSolar) {
        String[] split = BirthString.split("-");

        if (StringUtils.isBlank(split[0])) {
            return;
        }
        if (Integer.valueOf(split[0]) < 2013) {
            vip.setHideBirthdayYear(0);
        } else {
            vip.setHideBirthdayYear(1);
        }
        //设置为过农历或者公历
        vip.setBirthFlag(LunarOrSolar);
    }



}
