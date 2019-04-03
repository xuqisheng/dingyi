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
 * @Description: 纪念日service
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

        return iAnniversaryService.selectById(anniversaryId);
    }


    /**
     * 删除某个纪念日
     * @param anniversaryId 纪念日id
     * @return 返回操作结果
     */
    public Boolean deleteExactAnniversary(Integer anniversaryId) {

        return  iAnniversaryService.deleteById(anniversaryId);
    }

    /**
     * 根据客户id 查询该客户的所有纪念日
     *
     * @param vipId 客户id
     * @return 纪念日list
     */
    public List<AnniversaryDTO> getAnniversaryListByVipID(Integer vipId) {

        //查询初始的 纪念日list
        List<Anniversary> anniversaryList = iAnniversaryService.selectList(new EntityWrapper<Anniversary>()
                .eq("vip_id", vipId));


        List<AnniversaryDTO> anniversaryDTOS = new ArrayList<>();

        //对纪念日相差天数,几周年以及 展示的日期的处理
        for (Anniversary anniversary : anniversaryList) {

            AnniversaryDTO anniversaryDTO = new AnniversaryDTO();
            BeanUtils.copyProperties(anniversary, anniversaryDTO);


            //现在时间
            LocalDate now = LocalDate.now();

            // atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
            ZoneId zoneId = ZoneId.systemDefault();

            //计算下次纪念日距离多少天以及几周年
            Date nextAnniversaryTime = anniversary.getNextAnniversaryTime();
            Instant instant = nextAnniversaryTime.toInstant();
            LocalDate nextAnniversaryTimeLocalDate = instant.atZone(zoneId).toLocalDate();

            //相差多少天
            long day = nextAnniversaryTimeLocalDate.toEpochDay() - now.toEpochDay();

            anniversaryDTO.setSurplusDay((int) day);

            //几周年
            Date anniversaryDate = anniversary.getAnniversaryDate();
            Instant instant1 = anniversaryDate.toInstant();
            LocalDate anniversaryDateLocalDate = instant1.atZone(zoneId).toLocalDate();
            int toTotalMonths = (int) Period.between(anniversaryDateLocalDate, now).toTotalMonths();

            toTotalMonths = now.getDayOfMonth() > anniversaryDateLocalDate.getDayOfMonth() ? toTotalMonths + 1 : toTotalMonths;

            //周年数
            int year = toTotalMonths / 12 + (toTotalMonths % 12 == 0 ? 0 : 1);
            anniversaryDTO.setYearsNumber(year);

            //展示的字段
            String dateOfDisplay = getDisplayDate(anniversary.getCalendarType(), anniversary.getAnniversaryYearFlag(), anniversaryDateLocalDate);

            //展示的日期
            anniversaryDTO.setDateOfDisplay(dateOfDisplay);

            //处理完成,加入list
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

        //客户下次纪念日时间计算
        Date nextAnniversaryTime = calNextAnniveryDate(anniversaryDTO.getCalendarType(), anniversaryDTO.getAnniversaryDate());
        anniversary.setNextAnniversaryTime(nextAnniversaryTime);

        boolean b;
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
     * @param customerCareDTO 关怀查询条件
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

            //几天后 就(几周年 || 几岁)
            String surplusTime = getSurplusTime(customerCareBO);
            customerCareData.setSurplusTime(surplusTime);

            //日期的形容
            String dateDesc = getDateDesc(customerCareBO);
            customerCareData.setDate(dateDesc);


            customerCareDTOS.add(customerCareData);

        }
        page.setRecords(customerCareDTOS);


        return page;
    }


    /**
     * 计算下一次纪念日日期
     *
     * @param calendarType    日历类型
     * @param anniversaryDate 公历日期
     * @return 日期对应的下一次日期
     */
    private Date calNextAnniveryDate(Integer calendarType, Date anniversaryDate) {

        Date nextAnniversaryTime = null;

        if (calendarType == 1) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(anniversaryDate);
            Solar solar = new Solar(calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH));

            Lunar lastLunarBirth = nextLunarTime(solar.toString());
            String solarString = LunarSolarConverter.LunarToSolar(lastLunarBirth).toString();
            try {
                nextAnniversaryTime = sdf.parse(solarString);
            } catch (ParseException e) {
                log.error("更新下次日期格式出错:" + e.getMessage());
            }
        } else {
            //需要过公历的下一年的日期
            nextAnniversaryTime = anniversaryDate;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nextAnniversaryTime);
            calendar.add(calendar.YEAR, 1); //把日期往后增加一年.整数往后推,负数往前移动
            nextAnniversaryTime = calendar.getTime();
        }

        return nextAnniversaryTime;
    }


    /**
     * 要展示的日历日期样式
     *
     * @param calendarType             日历类型  0--公历 1--农历
     * @param anniversaryYearFlag      是否隐藏年份 0--不隐藏年份  1--隐藏年份
     * @param anniversaryDateLocalDate 纪念日localdate
     * @return 对应的日历格式
     */
    private String getDisplayDate(Integer calendarType, Integer anniversaryYearFlag, LocalDate anniversaryDateLocalDate) {

        //农历公历判断
        //1.公历
        if (calendarType == 0) {
            // 隐藏年份
            if (anniversaryYearFlag == 1) {

                return anniversaryDateLocalDate.getMonth() + "-" + anniversaryDateLocalDate.getDayOfMonth();
            } else {

                return anniversaryDateLocalDate.toString();
            }

        } else {

            //localdate 转为solar
            Solar solar = new Solar(anniversaryDateLocalDate.getYear(),
                    anniversaryDateLocalDate.getMonthValue(),
                    anniversaryDateLocalDate.getDayOfMonth());

            Lunar lunar = SolarToLunar(solar);

            if (anniversaryYearFlag == 1) {

                return getLunarMonthString(lunar.lunarMonth) + getLunarDayString(lunar.lunarDay);
            } else {

                String sign = "";

                if (lunar.isleap) {
                    sign = "闰";
                }

                return lunarYearToGanZhi(lunar.lunarYear) + "(" + lunar.lunarYear + ")" + "年"
                        + sign + getLunarMonthString(lunar.lunarMonth) + getLunarDayString(lunar.lunarDay);
            }
        }
    }

    /**
     * 下一个纪念日的日期形容
     *
     * @param customerCareBO 拼接条件
     * @return 几天后到几周天
     */
    private String getSurplusTime(CustomerCareBO customerCareBO) {

        //对日期的处理 --几天后的处理
        String surplusDay;
        if (customerCareBO.getSurplusTime() == null || customerCareBO.getSurplusTime() < 0) {
            surplusDay = "暂无";
        } else if (customerCareBO.getSurplusTime() == 0) {
            surplusDay = "今天";
        } else {
            surplusDay = customerCareBO.getSurplusTime() + "天后";
        }

        //对几周年或者几岁的处理
        LocalDateTime nexttime = customerCareBO.getNexttime();
        LocalDateTime now = LocalDateTime.now();
        String yearDesc;
        if (null == nexttime || customerCareBO.getHideFlag() == 1) {
            yearDesc = "";
        } else {
            int yearDif = now.getYear() - nexttime.getYear() + 2;
            if (customerCareBO.getType() == 0) {
                yearDesc = "(" + yearDif + "周年)";
            } else {
                yearDesc = "(" + yearDif + "岁)";
            }
        }

        return surplusDay + yearDesc;
    }


    /**
     * customerCareBO 选择适当的日期,并赋予适当的形态展示
     * @param customerCareBO 客户关怀日信息
     * @return 适当的形式的字符串
     */
    private String getDateDesc(CustomerCareBO customerCareBO) {

        String dateDesc = "";

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

                dateDesc = nlDate;
            } else {
                //如果是公历
                dateDesc = solarStringDesc(customerCareBO);
            }
        } else if (customerCareBO.getType() == 0) {

            //2.对纪念日的展示
            if (customerCareBO.getCalendarType() != null && customerCareBO.getCalendarType() == 1) {
                String anniversaryDate = customerCareBO.getAnniversaryDate();
                String[] split = anniversaryDate.split("-");
                //如果是农历
                //1.公历转为农历
                Solar solar = new Solar(Integer.valueOf(split[0]), Integer.valueOf(split[1]), Integer.valueOf(split[2]));
                Lunar lunar = SolarToLunar(solar);
                String nlDate = "";
                if (customerCareBO.getIsLeap() != null && customerCareBO.getIsLeap() == 1) {
                    nlDate = "闰";
                }
                dateDesc = nlDate + getLunarMonthString(lunar.lunarMonth) + getLunarDayString(lunar.lunarDay);
            } else {

                dateDesc = solarStringDesc(customerCareBO);
            }
        }

        return dateDesc;
    }


    /**
     * 公历形式返回
     * @param customerCareBO 店里字符串
     * @return 公历形式返回
     */
    private String solarStringDesc(CustomerCareBO customerCareBO) {

        String date = customerCareBO.getType() == 1 ? customerCareBO.getVipBirthday() : customerCareBO.getAnniversaryDate();
        //如果是公历
        String anniversaryDate1;
        //如果忽略年份
        if (customerCareBO.getHideFlag() != null && customerCareBO.getHideFlag() == 1) {
            anniversaryDate1 = date.substring(5);
        } else {
            anniversaryDate1 = date;
        }
        return anniversaryDate1;
    }


}










