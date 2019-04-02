package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.common.util.PageFactory;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Anniversary;
import com.zhidianfan.pig.yd.moduler.common.service.IAnniversaryService;
import com.zhidianfan.pig.yd.moduler.resv.bo.CustomerCareBO;
import com.zhidianfan.pig.yd.moduler.resv.dto.AnniversaryDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.CustomerCareDTO;
import com.zhidianfan.pig.yd.utils.Lunar;
import com.zhidianfan.pig.yd.utils.LunarSolarConverter;
import com.zhidianfan.pig.yd.utils.Solar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.zhidianfan.pig.yd.moduler.resv.service.VipNextBirthDayAnniversaryService.nextLunarTime;
import static com.zhidianfan.pig.yd.utils.LunarSolarConverter.SolarToLunar;
import static com.zhidianfan.pig.yd.utils.LunarStringConverterUtil.*;

/**
 * @Author: hzp
 * @Date: 2019-03-28 16:05
 * @Description:
 */
@Slf4j
@Service
public class AnniversaryService {

    @Autowired
    IAnniversaryService iAnniversaryService;


    /**
     * 根据主键查询确切的纪念日
     *
     * @param anniversaryId 纪念日id
     * @return 纪念日
     */
    public Anniversary getExactAnniversary(Integer anniversaryId) {


        Anniversary exactAnniversary = iAnniversaryService.selectById(anniversaryId);
        return exactAnniversary;
    }


    public Boolean deleteExactAnniversary(Integer anniversaryId) {

        Boolean b = iAnniversaryService.deleteById(anniversaryId);
        return b;
    }

    /**
     * 根据客户id 查询该客户的所有纪念日
     *
     * @param vipId 客户id
     * @return 纪念日list
     */
    public List<AnniversaryDTO> getAnniversaryListByVipID(Integer vipId) {

        List<Anniversary> anniversaryList = iAnniversaryService.selectList(new EntityWrapper<Anniversary>()
                .eq("vip_id", vipId));

        List<AnniversaryDTO> anniversaryDTOS = new ArrayList<>();

        for (Anniversary anniversary : anniversaryList) {
            AnniversaryDTO anniversaryDTO = new AnniversaryDTO();
            BeanUtils.copyProperties(anniversary, anniversaryDTO);

            //计算下次纪念日距离多少天以及几周年
            Date nextAnniversaryTime = anniversary.getNextAnniversaryTime();
            Instant instant = nextAnniversaryTime.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            // atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
            LocalDate nextAnniversaryTimeLocalDate = instant.atZone(zoneId).toLocalDate();
            LocalDate now = LocalDate.now();

            //相差多少天
            long day = nextAnniversaryTimeLocalDate.toEpochDay() - now.toEpochDay();

            anniversaryDTO.setSurplusDay((int) day);

            //几周年
            Date anniversaryDate = anniversary.getAnniversaryDate();
            Instant instant1 = anniversaryDate.toInstant();
            ZoneId zoneId1 = ZoneId.systemDefault();
            LocalDate anniversaryDateLocalDate = instant1.atZone(zoneId1).toLocalDate();
            int toTotalMonths = (int) Period.between(anniversaryDateLocalDate, now).toTotalMonths();

            toTotalMonths = now.getDayOfMonth() > anniversaryDateLocalDate.getDayOfMonth() ? toTotalMonths + 1 : toTotalMonths;

            //周年数
            int year = toTotalMonths / 12 + (toTotalMonths % 12 == 0 ? 0 : 1) ;
            anniversaryDTO.setYearsNumber(year);

            //展示的字段
            String dateOfDisplay = getDisplayDate(anniversary,anniversaryDateLocalDate);
            //展示的日期
            anniversaryDTO.setDateOfDisplay(dateOfDisplay);

            anniversaryDTOS.add(anniversaryDTO);
        }

        return anniversaryDTOS;
    }


    /**
     * 编辑客户纪念日
     *
     * @param anniversaryDTO 纪念日
     * @return 返回编辑结果
     */
    public Boolean editExactAnniversary(AnniversaryDTO anniversaryDTO) {

        Anniversary anniversary = new Anniversary();
        BeanUtils.copyProperties(anniversaryDTO, anniversary);

        Date anniversaryDate = anniversaryDTO.getAnniversaryDate();

        //客户下次纪念日时间计算
        //需要过农历的下一年的日期
        if (anniversaryDTO.getCalendarType() == 1) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(anniversaryDate);
            Solar solar = new Solar(calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH));

            Lunar lastLunarBirth = nextLunarTime(solar.toString());
            String solarString = LunarSolarConverter.LunarToSolar(lastLunarBirth).toString();
            try {
                anniversary.setNextAnniversaryTime(sdf.parse(solarString));
            } catch (ParseException e) {
                log.error("更新下次日期格式出错:" + e.getMessage());
            }
        } else {
            //需要过公历的下一年的日期
            Date nextAnniversaryTime = anniversaryDate;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nextAnniversaryTime);
            calendar.add(calendar.YEAR, 1);//把日期往后增加一年.整数往后推,负数往前移动
            nextAnniversaryTime = calendar.getTime();
            anniversary.setNextAnniversaryTime(nextAnniversaryTime);
        }

        Boolean b;
        //更新或者插入
        if (anniversaryDTO.getId() != null) {
            //更新
            anniversary.setUpdatedAt(new Date());
            b = iAnniversaryService.updateById(anniversary);
        } else {
            anniversary.setCreatedAt(new Date());
            b = iAnniversaryService.insert(anniversary);
        }

        return b;
    }


    /**
     * 分页查询客户生日与纪念日信息
     *
     * @return 客户关怀数据
     */
    public Page<CustomerCareDTO> getCustomerCarePage(CustomerCareDTO customerCareDTO) {


        Page<CustomerCareDTO> page = new PageFactory().defaultPage();

        //查询出客户关怀等信息
        List<CustomerCareBO> customerCareBOS = iAnniversaryService.selectCustomerCareInfoByPage(page, customerCareDTO);

        List<CustomerCareDTO> customerCareDTOS = new ArrayList<>();
        //对数据的处理
        for (CustomerCareBO customerCareBO : customerCareBOS) {
            CustomerCareDTO customerCareData = new CustomerCareDTO();
            customerCareData.setVipId(customerCareBO.getId());
            String name = customerCareBO.getVipName() + (customerCareBO.getVipSex().equals("女") ? "小姐" : "先生");
            customerCareData.setName(name);
            customerCareData.setPhone(customerCareBO.getVipPhone());
            customerCareData.setTitle(customerCareBO.getTitle());
            customerCareData.setCustomerValue(customerCareBO.getVipValueName());
            customerCareData.setCustomerValueId(customerCareBO.getVipValueId());
            customerCareData.setBusinessId(customerCareBO.getBusinessId().toString());


            //对日期的处理 --几天后的处理
            String surplusDay = "暂无";
            if (customerCareBO.getSurplusTime() == null || customerCareBO.getSurplusTime() < 0) {
                surplusDay = "暂无";
            } else if (customerCareBO.getSurplusTime() == 0) {
                surplusDay = "今天";
            } else if (customerCareBO.getSurplusTime() > 0) {
                surplusDay = customerCareBO.getSurplusTime() + "天后";
            }

            //对几周年或者几岁的处理
            LocalDateTime nexttime = customerCareBO.getNexttime();
            LocalDateTime now = LocalDateTime.now();
            String yearDesc;
            if (null == nexttime || customerCareBO.getHideFlag() == 1) {
                yearDesc = "";
            } else {
                Integer yearDif = now.getYear() - nexttime.getYear() + 2;
                if (customerCareBO.getType() == 0) {
                    yearDesc = "(" + yearDif + "周年)";
                } else {
                    yearDesc = "(" + yearDif + "岁)";
                }
            }
            String surplusTime = surplusDay + yearDesc;
            customerCareData.setSurplusTime(surplusTime);


            //1. 对生日的展示
            if (customerCareBO.getType() == 1) {

                //如果是农历 ,转为字符串
                if (customerCareBO.getCalendarType() != null && customerCareBO.getCalendarType() == 1) {
                    //转成农历字符串 月日形式
                    String vipBirthdayNl = customerCareBO.getVipBirthdayNl();
                    String nlDate = "";
                    if (customerCareBO.getIsLeap() != null && customerCareBO.getIsLeap() == 1) {
                        nlDate = "闰";
                    }
                    String[] split = vipBirthdayNl.split("-");
                    nlDate = nlDate + getLunarMonthString(Integer.valueOf(split[1])) + getLunarDayString(Integer.valueOf(split[2]));
                    customerCareData.setDate(nlDate);
                } else {
                    //如果是公历
                    String cusVipBirthday = customerCareBO.getVipBirthday();
                    String vipBirthday;
                    //如果忽略年份
                    if (customerCareBO.getHideFlag() != null && customerCareBO.getHideFlag() == 1) {
                        vipBirthday = cusVipBirthday.substring(5);
                    } else {
                        vipBirthday = cusVipBirthday;
                    }
                    customerCareData.setDate(vipBirthday);
                }

            } else {

                String anniversaryDate = customerCareBO.getAnniversaryDate();
                //2.对纪念日的展示
                if (customerCareBO.getCalendarType() != null && customerCareBO.getCalendarType() == 1) {
                    String[] split = anniversaryDate.split("-");
                    //如果是农历
                    //1.公历转为农历
                    Solar solar = new Solar(Integer.valueOf(split[0]), Integer.valueOf(split[1]), Integer.valueOf(split[2]));
                    Lunar lunar = SolarToLunar(solar);
                    String nlDate = "";
                    if (customerCareBO.getIsLeap() != null && customerCareBO.getIsLeap() == 1) {
                        nlDate = "闰";
                    }
                    nlDate = nlDate + getLunarMonthString(Integer.valueOf(lunar.lunarMonth)) + getLunarDayString(Integer.valueOf(lunar.lunarDay));

                    customerCareData.setDate(nlDate);
                } else {
                    //如果是公历
                    String anniversaryDate1;
                    //如果忽略年份
                    if (customerCareBO.getHideFlag() != null && customerCareBO.getHideFlag() == 1) {
                        anniversaryDate1 = anniversaryDate.substring(5);
                    } else {
                        anniversaryDate1 = anniversaryDate;
                    }
                    customerCareData.setDate(anniversaryDate1);
                }

            }

            customerCareDTOS.add(customerCareData);

        }
        page.setRecords(customerCareDTOS);


        return page;
    }


    /**
     * 要展示的日历日期样式
     * @param anniversary 纪念日class
     * @param anniversaryDateLocalDate  纪念日localdate
     * @return 对应的日历格式
     */
    private String getDisplayDate(Anniversary anniversary,LocalDate anniversaryDateLocalDate) {

        //农历公历判断
        //1.公历
        if(anniversary.getCalendarType() == 0){
            // 隐藏
            if (anniversary.getAnniversaryYearFlag() == 1 ){
                return anniversaryDateLocalDate.getMonth() + "-" +anniversaryDateLocalDate.getDayOfMonth();
            }else {
                return  anniversaryDateLocalDate.toString();
            }

        }else {
            //localdate 转为solar
            Solar solar = new Solar(anniversaryDateLocalDate.getYear(),
                                    anniversaryDateLocalDate.getMonthValue(),
                                    anniversaryDateLocalDate.getDayOfMonth());
            Lunar lunar = SolarToLunar(solar);
            if (anniversary.getAnniversaryYearFlag() == 1 ) {
                return getLunarMonthString(lunar.lunarMonth)+getLunarDayString(lunar.lunarDay);
            }else {
                String sign = "" ;
                if(lunar.isleap == true){
                    sign = "闰";
                }
                return lunarYearToGanZhi(lunar.lunarYear)+"("+lunar.lunarYear+")"+"年"
                        +sign+getLunarMonthString(lunar.lunarMonth)+getLunarDayString(lunar.lunarDay);
            }
        }
    }


}










