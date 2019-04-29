package com.zhidianfan.pig.yd.moduler.resv.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.common.util.PageFactory;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Business;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderAndroid;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderRating;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Vip;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.common.service.*;
import com.zhidianfan.pig.yd.moduler.resv.bo.VipMealInfoBo;
import com.zhidianfan.pig.yd.moduler.resv.bo.VipValueBo;
import com.zhidianfan.pig.yd.moduler.resv.bo.VipValueCountBo;
import com.zhidianfan.pig.yd.moduler.resv.dto.*;
import com.zhidianfan.pig.yd.moduler.resv.enums.OrderStatus;
import com.zhidianfan.pig.yd.utils.ExcelUtil;
import com.zhidianfan.pig.yd.utils.Lunar;
import com.zhidianfan.pig.yd.utils.LunarSolarConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Part;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.zhidianfan.pig.yd.moduler.resv.service.VipNextBirthDayAnniversaryService.*;

/**
 * @Author: huzp
 * @Date: 2018/9/20 15:25
 */
@Service
@Slf4j
public class VipService {

    @Autowired
    IVipService iVipService;

    @Resource
    private IVipValueRecordService iVipValueRecordService;


    @Resource
    private IResvOrderAndroidService iResvOrderAndroidService;

    @Autowired
    private IBusinessService businessService;

    @Resource
    private IResvOrderRatingService iResvOrderRatingService;


    /**
     * 根据酒店id与手机号更新或者新增Vip
     *
     * @param vip vip信息
     * @return 更新或者新增操作的 成功或者失败标志
     */
    public boolean updateOrInsertVip(Vip vip) {

        //是否操作成功标志
        boolean b;

        //判断是否存在
        Vip vipInfo = iVipService.selectOne(new EntityWrapper<Vip>()
                .eq("business_id", vip.getBusinessId())
                .eq("vip_phone", vip.getVipPhone()));

        try {
            //更新客户的生日
            conversionVipBirth(vip);
        } catch (ParseException e) {
            log.error("updateOrInsertVip : 客户信息出错" + e.getMessage());
        }

        //如果为null 则进行新增操作
        if (null == vipInfo) {
            b = iVipService.insert(vip);
        } else {
            //不为null则更新
            vip.setId(vipInfo.getId());
            vip.setUpdatedAt(new Date());
            b = iVipService.updateById(vip);
        }


        return b;
    }

    /**
     * @param businessId 酒店id
     * @param phone      vip手机号
     * @return 返回vip信息
     */
    public VipInfoDTO getVipInfo(Integer businessId, String phone) {

        Vip vipInfo = iVipService.selectOne(new EntityWrapper<Vip>()
                .eq("business_id", businessId)
                .eq("vip_phone", phone));

        if (vipInfo == null) {
            return null;
        }


        //1.查询客户预订次数
        Integer resvTimes = iResvOrderAndroidService.selectResvTimes(vipInfo.getId(), null);

        //2.查询实际消费次数
        Integer actResvTimes = iResvOrderAndroidService.selectResvTimes(vipInfo.getId(), OrderStatus.SETTLE_ACCOUNTS.code);

        //3.查询预订总桌数和总数人
        Map<String, String> tableAndPeopleNum = iResvOrderAndroidService.selectTableAndPeopleNum(vipInfo.getId(), null);

        //4.查询实际消费总桌数和总数人
        Map<String, String> actTableAndPeopleNum = iResvOrderAndroidService.selectTableAndPeopleNum(vipInfo.getId(), OrderStatus.SETTLE_ACCOUNTS.code);

        //查询上一次就餐时间
        Date lastEatTime = iResvOrderAndroidService.selectLastEatTime(vipInfo.getId(), OrderStatus.SETTLE_ACCOUNTS.code);

        //查询上一次就餐评分
        VipInfoDTO vipInfoDTO = new VipInfoDTO();
        BeanUtils.copyProperties(vipInfo, vipInfoDTO);


        vipInfoDTO.setResvTimes(resvTimes);
        vipInfoDTO.setActResvTimes(actResvTimes);
        vipInfoDTO.setTableNum(String.valueOf(tableAndPeopleNum.get("tableNum")));
        vipInfoDTO.setActTableNum(String.valueOf(actTableAndPeopleNum.get("tableNum")));
        vipInfoDTO.setPeopleNum(String.valueOf(tableAndPeopleNum.get("peopleNum")));
        vipInfoDTO.setActPeopleNum(String.valueOf(actTableAndPeopleNum.get("peopleNum")));
        vipInfoDTO.setLastEatTime(lastEatTime);

        return vipInfoDTO;
    }


    /**
     * 价值类别客户统计
     *
     * @param businessId 酒店id
     * @return 统计数据
     */
    public VipValueCountBo valueCount(Integer businessId) {

        //价值类别
        VipValueCountBo vipValueCountBo = new VipValueCountBo();
        List<VipValueBo> vipValueBo = iVipValueRecordService.countByBusiness(businessId);

        //总数
        Vip vip = new Vip();
        vip.setBusinessId(businessId);
        int count = iVipService.selectCount(new EntityWrapper<>(vip));

        vipValueCountBo.setVipValues(vipValueBo);
        vipValueCountBo.setSum(count);

        return vipValueCountBo;
    }

    /**
     * 客户订单查询（状态是正在进行的）
     *
     * @param orderConditionDTO 查询条件
     * @return 查询结果
     */
    public List<ResvOrderAndroid> orders(OrderConditionDTO orderConditionDTO) {

        ResvOrderAndroid resvOrder = new ResvOrderAndroid();
        BeanUtils.copyProperties(orderConditionDTO, resvOrder);
        List<ResvOrderAndroid> resvOrders = iResvOrderAndroidService.selectList(new EntityWrapper<>(resvOrder));

        return resvOrders;
    }

    /**
     * 粗略条件查询查询一个酒店的客户
     *
     * @param vipConditionCountDTO businessId酒店id必须
     * @return 查询结果
     */
    public Page<VipTableDTO> getConditionVip(VipConditionCountDTO vipConditionCountDTO) {

        Page<VipTableDTO> page = new PageFactory().defaultPage();

        iVipService.getConditionVipPage(page, vipConditionCountDTO);

        return page;
    }

    /**
     * 获取基础信息
     *
     * @param businessId 酒店id
     * @param vipPhone   客户号码
     * @return
     */
    public Vip getBasicVipInfo(Integer businessId, String vipPhone) {
        Vip vipInfo = iVipService.selectOne(new EntityWrapper<Vip>()
                .eq("business_id", businessId)
                .eq("vip_phone", vipPhone));

        return vipInfo;
    }

    /**
     * 根据id更新客户信息
     *
     * @param vip
     * @return
     */
    public boolean updateVipInfo(Vip vip) {

        try {
            conversionVipBirth(vip);
        } catch (ParseException e) {
            log.error("updateVipInfo转换生日日期error" + e.getMessage());
        }

        return iVipService.update(vip, new EntityWrapper<Vip>().eq("id", vip.getId()));
    }


    /**
     * 模糊查询客户list
     *
     * @param businessId 酒店id
     * @param phone      号码
     * @return
     */
    public Page<Vip> fuzzyQueryVipList(Integer businessId, String phone) {

        Page<Vip> page = new PageFactory().defaultPage();

        //分页模糊查询
        Page<Vip> vipPage = iVipService.selectPage(page, new EntityWrapper<Vip>()
                .like("vip_phone", phone)
                .eq("business_id", businessId)
                .orderBy("updated_at", false)
                .orderBy("id", false));

        return vipPage;
    }

    public Page<StatisticsVipDTO> statisticsViplist(Integer businessId, String queryVal) {
        Page<StatisticsVipDTO> page = new PageFactory().defaultPage();

        //分页模糊查询
        List<StatisticsVipDTO> viplist = iVipService.findStatisticsViplist(page, businessId, queryVal);
        page.setRecords(viplist);
        return page;

    }

    /**
     * 精细查询酒店客户
     *
     * @param vipInfoDTO 姓名 电话 客户分类 客户价值 预订次数 最近就餐
     * @return
     */
    public Page<VipTableDTO> conditionFindVips(VipInfoDTO vipInfoDTO) {

        Page<VipTableDTO> page = new PageFactory().defaultPage();

        iVipService.conditionFindVips(page, vipInfoDTO);

        return page;
    }

    public List<VipTableDTO> excelConditionFindVips(VipInfoDTO vipInfoDTO) {

        List<VipTableDTO> vipInfoDTOS = iVipService.excelConditionFindVips(vipInfoDTO);

        return vipInfoDTOS;
    }

    public void downloadexcel(List<VipTableDTO> records) {

        //excel导出标志
        String sign = "vipinfo";

        ExcelUtil.ListExport2Excel(sign, records);
    }

    /**
     * excel 导入
     *
     * @param file 文件
     * @return 成功失败标志
     */
    public Tip importExcel(Part file, Integer id) throws InvocationTargetException, IllegalAccessException {

        try {
            //检查文件格式
            ExcelUtil.preReadCheck(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            ErrorTip errorTip = new ErrorTip();
            errorTip.setMsg("文件不是excel或者文件不存在");
            return errorTip;
        }

        //读取
        List<Map<String, Object>> list = ExcelUtil.ReadExcel(file, "vip");

        //查询出酒店信息
        Business businessInfo = businessService.selectOne(new EntityWrapper<Business>()
                .eq("id", id).eq("status", 1));

        List<Vip> sucVips = new ArrayList<>();
        List<Vip> failVips = new ArrayList<>();

        Date date = new Date();

        for (Map<String, Object> map : list) {


            Vip vip = new Vip();
            //拷贝对应字段到vip中
            org.apache.commons.beanutils.BeanUtils.populate(vip, map);

            //剔除名字和手机号码字段为空的
            //性别为空的过滤
            //性别为男或者女
            if ((map.get("vipName") != null && !"".equals(map.get("vipName")))
                    && (map.get("vipPhone") != null && !"".equals(map.get("vipPhone")))
                    && isMobileNO(map.get("vipPhone").toString())
                    && map.get("vipSex") != null && !"".equals(map.get("vipSex"))
                    && ("男".equals(map.get("vipSex")) ||  "女".equals(map.get("vipSex")))

            ) {

                //剔除生日格式不正确的
                if (map.get("vipBirthday") != null  && StringUtils.isNotEmpty(map.get("vipBirthday").toString())
                        && !valiDateTimeWithLongFormat(map.get("vipBirthday").toString())) {
                    //加入录入失败的客户信息
                    failVips.add(vip);
                    continue;
                }

                vip.setBusinessId(businessInfo.getId());
                vip.setBusinessName(businessInfo.getBusinessName());
                vip.setCreatedAt(date);
                sucVips.add(vip);

            } else {

                //加入失败的客户信息
                failVips.add(vip);

            }
        }


        if (sucVips.size() != 0) {
            iVipService.excelInsertVIPInfo(sucVips);
        }


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", list.size());
        jsonObject.put("successNum", sucVips.size());
        jsonObject.put("failNum", failVips.size());
        jsonObject.put("failData", failVips);


        SuccessTip successTip = new SuccessTip();
        successTip.setMsg(jsonObject.toString());


        return successTip;
    }


    /**
     * 下载Excel模板
     */
    public void downLoadExcelDemo() {

        String sign = "vip";
        //下载excel 模板
        ExcelUtil.ListExport2Excel(sign, new ArrayList<>());
    }


    public VipMealInfoBo mealInfoNear(Integer vipId, Integer businessId, Integer ageDay) {

        //最近评论
        ResvOrderRating resvOrderRating = new ResvOrderRating();
        resvOrderRating.setVipId(vipId);
        resvOrderRating.setBusinessId(businessId);
        ResvOrderRating orderDate = iResvOrderRatingService.selectOne(new EntityWrapper<>(resvOrderRating).orderBy("order_date", false));

        VipMealInfoBo orderRating = new VipMealInfoBo();
        if (orderDate != null)
            BeanUtils.copyProperties(orderDate, orderRating);

        //指定日期范围订单统计
        ResvOrderAndroid resvOrder = new ResvOrderAndroid();
        resvOrder.setVipId(vipId);
        resvOrder.setStatus("3");
        resvOrder.setBusinessId(businessId);
        EntityWrapper<ResvOrderAndroid> wrapper = new EntityWrapper<>(resvOrder);

        wrapper.gt("resv_date", DateUtils.addDays(new Date(), -ageDay));
        wrapper.le("resv_date", new Date());

        List<ResvOrderAndroid> resvOrders = iResvOrderAndroidService.selectList(wrapper.orderBy("resv_date", false));
        if (resvOrders == null) {
            orderRating.setPayAmount("0");
            orderRating.setPeopleNum(0);
            orderRating.setTableNum(0);
            orderRating.setTimes(0);
            return orderRating;
        }

        List<String> collect = resvOrders.stream().map(ResvOrderAndroid::getBatchNo).collect(Collectors.toList());
        orderRating.setTimes(collect.size());

        orderRating.setTableNum(resvOrders.size());
        int peopleNum = 0;
        BigDecimal payAmount = new BigDecimal(0);
        for (ResvOrderAndroid order : resvOrders) {
            String actualNum = order.getActualNum();
            String payamount = order.getPayamount();
            if (StringUtils.isNotBlank(actualNum))
                peopleNum += Integer.valueOf(actualNum);

            if (StringUtils.isNotBlank(payamount))
                payAmount = payAmount.add(new BigDecimal(payamount));
        }
        orderRating.setPeopleNum(peopleNum);
        orderRating.setPayAmount(payAmount.toString());

        //最后一次就餐
        if (resvOrders.size() > 0) {
            orderRating.setLastAmount(resvOrders.get(0).getPayamount());
            orderRating.setLastTime(resvOrders.get(0).getResvDate());
        }


        return orderRating;
    }


    /**
     * 设置客户的农历生日阳历生日等方法
     *
     * @param vip 客户
     */
    private void conversionVipBirth(Vip vip) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //对生日的处理,接收到阳历的转为农历
        String vipBirthday = vip.getVipBirthday();
        if (StringUtils.isNotBlank(vipBirthday)) {
            Lunar lunarBirth = getLunarBirthDay(vipBirthday);
            vip.setIsLeap(lunarBirth.isleap ? 1 : 0);
            String lunarBirthDayString = lunarBirth.toString();
            vip.setVipBirthdayNl(lunarBirthDayString);
            Integer birthFlag = vip.getBirthFlag();

            //1.判断过农历还是阳历,默认为阳历
            if (birthFlag != null && birthFlag == 1) {
                //如果过农历,转为农历生日为阳历,拿出月日,计算下次生日时间
                //1.根据阳历生日计算出农历
                Lunar lastLunarBirth = nextLunarTime(vipBirthday);
                String solar = LunarSolarConverter.LunarToSolar(lastLunarBirth).toString();
                vip.setNextVipBirthday(sdf.parse(solar));
            } else {
                vip.setBirthFlag(0);
                String lastSolarBirth = nextSolarTime(vipBirthday).toString();
                vip.setNextVipBirthday(sdf.parse(lastSolarBirth));
            }
        }

    }


    private static boolean isMobileNO(String mobiles) {

        Pattern p = Pattern.compile("^.\\d{10}$");
        Matcher m = p.matcher(mobiles);
        System.out.println(m.matches() + "---");
        return m.matches();
    }

    /**
     * 判断是否为时间字符串
     *
     * @param timeStr 时间字符串
     * @return 是否为YYYY-MM-DD格式的字符串
     */
    private static boolean valiDateTimeWithLongFormat(String timeStr) {
        String format = "((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(timeStr);
        if (matcher.matches()) {
            pattern = Pattern.compile("(\\d{4})-(\\d+)-(\\d+).*");
            matcher = pattern.matcher(timeStr);
            if (matcher.matches()) {
                int y = Integer.valueOf(matcher.group(1));
                int m = Integer.valueOf(matcher.group(2));
                int d = Integer.valueOf(matcher.group(3));
                if (d > 28) {
                    Calendar c = Calendar.getInstance();
                    c.set(y, m - 1, 1);
                    int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
                    return (lastDay >= d);
                }
            }
            return true;
        }
        return false;
    }

}
