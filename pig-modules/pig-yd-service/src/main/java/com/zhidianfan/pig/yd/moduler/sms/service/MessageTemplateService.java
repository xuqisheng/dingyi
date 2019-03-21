package com.zhidianfan.pig.yd.moduler.sms.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvLine;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * 短信模板
 *
 * @author wangyz
 * @date 2017年12月11日10:35:42
 */
@Service
public class MessageTemplateService {

    //短信头，如果没有短信头就默认易订
    private static final String SIGNER = "【易订】";
    //短信结尾
    private static final String ENDING = "系统短信，请勿回复！";
    //确认预定type
    private static final String CONFIRM_TYPE = "1";
    //入座type
    private static final String SEAT_TYPE = "2";

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private static final Logger LOGGER = Logger.getLogger(MessageTemplateService.class);

    /**
     * 生成登录短信模板
     *
     * @param param code 验证码
     * @return 登录验证短信
     */
    private String getLoginMessage(Map<String, Object> param) {
        Integer code = MapUtils.getInteger(param, "code");
        if (code == null)
            return "登录短信验证码生成异常!";

        return String.format("验证码：%s。如非本人操作，请忽略本短信", code + "");
    }

    /**
     * 生成预订短信
     *
     * @param param 入参
     * @return 预定短信文本
     */
    private String getResvMessage(Map<String, Object> param) {
        String vipName = getMapValue(param, "vipName", "vipNameType");
        String tableName = getMapValue(param, "tableName", "tableNameType");
        String resvNum = getMapValue(param, "resvNum", "resvNumType");
        String resvDate = getMapValue(param, "resvDate", "");
        String mealTypeName = getMapValue(param, "mealTypeName", "");
        String businessAddress = getMapValue(param, "businessAddress", "addressType");
        String addressMap = getMapValue(param, "localUrl", "mapType");
        String businessPhone = getMapValue(param, "businessPhone", "phoneType");
        String smsMessage = getMapValue(param, "smsMessage", "");

        String appUserName = getMapValue(param, "appUserName", "appUserType");
        String appUserPhone = getMapValue(param, "appUserPhone", "appUserType");
        String appUserMsg = isNotEmpty(appUserName) && isNotEmpty(appUserPhone) ? appUserName + " " + appUserPhone : "";

        if ("0".equals(getMapValue(param, "appUserId", ""))) {
            appUserMsg = null;
        }

        String weekName = Boolean.TRUE.equals(MapUtils.getBoolean(param, "weekNameType")) ? getWeekOfDate(resvDate) : "";

        String newResvDate = getFormatDate(resvDate);

        StringBuilder sb = new StringBuilder();

        sb
                .append(isNotEmpty(vipName) ? vipName : "尊敬的贵宾")
                .append("：您好！\n")
                .append("餐位：")
                .append(tableName)
                .append("\n")
                .append(isNotEmpty(resvNum) ? "预订人数：" + resvNum + "人\n" : "")
                .append(isNotEmpty(newResvDate) ? "日期：" + newResvDate + " " : "")
                .append(weekName)
                .append(" ")
                .append(mealTypeName)
                .append(isNotEmpty(businessAddress) ? "\n" + "地址：" + businessAddress : "")
                .append(isNotEmpty(addressMap) ? "\n" + "导航：" + addressMap : "")
                .append(isNotEmpty(businessPhone) ? "\n" + "电话：" + businessPhone : "")
                .append(isNotEmpty(appUserMsg) ? "\n" + "联系人：" + appUserMsg : "")
                .append(isNotEmpty(smsMessage) ? "\n" + smsMessage : "");
        return sb.toString();
    }

    /**
     * 生成取消预定短信
     *
     * @param param 参数
     * @return 取消预定短信文本
     */
    private String getCancelMessage(Map<String, Object> param) {
        String vipName = getMapValue(param, "vipName", "vipNameType");
        //tableNameType
        String tableName = getMapValue(param, "tableName", "");
        String resvDate = getMapValue(param, "resvDate", "");
        String businessAddress = getMapValue(param, "businessAddress", "addressType");
        String addressMap = getMapValue(param, "localUrl", "mapType");
        String businessPhone = getMapValue(param, "businessPhone", "phoneType");
        String smsMessage = getMapValue(param, "smsMessage", "");
        String mealTypeName = getMapValue(param, "mealTypeName", "");

        String appUserName = getMapValue(param, "appUserName", "appUserType");
        String appUserPhone = getMapValue(param, "appUserPhone", "appUserType");
        String appUserMsg = isNotEmpty(appUserName) && isNotEmpty(appUserPhone) ? appUserName + " " + appUserPhone : "";

        if ("0".equals(getMapValue(param, "appUserId", ""))) {
            appUserMsg = null;
        }

        String weekName = Boolean.TRUE.equals(MapUtils.getBoolean(param, "weekNameType")) ? getWeekOfDate(resvDate) : "";

        String newResvDate = getFormatDate(resvDate);


        StringBuilder sb = new StringBuilder();

        sb
                .append(isNotEmpty(vipName) ? vipName : "尊敬的贵宾")
                .append("：您好！\n")
                .append("您预订的 ")
                .append(newResvDate).append(" ")
                .append(weekName).append(" ")
                .append(mealTypeName).append(" ")
                .append(tableName).append(" ")
                .append("已取消，恭候您再次光临\n")
                .append(isNotEmpty(businessAddress) ? "地址：" + businessAddress + "\n" : "")
                .append(isNotEmpty(addressMap) ? "导航：" + addressMap + "\n" : "")
                .append(isNotEmpty(businessPhone) ? "电话：" + businessPhone + "\n" : "")
                .append(isNotEmpty(appUserMsg) ? "联系人：" + appUserMsg + "\n" : "")
                .append(isNotEmpty(smsMessage) ? smsMessage : "");

        return sb.toString();
    }

    /**
     * 生成确认预定短信
     *
     * @param vipName         用户名称
     * @param resvDate        日期
     * @param mealTypeName    餐别
     * @param tablesName      桌位名称
     * @param businessAddress 酒店地址
     * @param businessPhone   酒店电话
     * @return 预定信息文本
     */
    private String getConfirmMessage(String vipName, String resvDate, String mealTypeName,
                                     String tablesName, String businessAddress, String businessPhone) {
        StringBuilder sb = new StringBuilder();
        sb
                .append(vipName)
                .append(": 您好！\n")
                .append("您预订的 ")
                .append(resvDate)
                .append(" ")
                .append(mealTypeName)
                .append(" ")
                .append(tablesName)
                .append(" 已确认，祝您用餐愉快\n地址: ")
                .append(businessAddress)
                .append("\n电话: ")
                .append(businessPhone);

        return sb.toString();
    }

    /**
     * 预定或者入座短信 根据smsType判断 1:确认预定短信 2:入座短信
     *
     * @param param 入参
     * @return 确认或者入座短信文本
     */
    private String getConfirmOrSeatMessage(Map<String, Object> param) {
        String vipName = getMapValue(param, "vipName", "");
        String resvDate = getMapValue(param, "resvDate", "");
        String mealTypeName = getMapValue(param, "mealTypeName", "");
        String businessAddress = getMapValue(param, "businessAddress", "");
        String businessPhone = getMapValue(param, "businessPhone", "");
        String tableName = getMapValue(param, "tableName", "");
        String newResvDate = getFormatDate(resvDate);

        String type = getMapValue(param, "SMSType", "");

        if ("1".equals(type))
            return getConfirmMessage(vipName, newResvDate, mealTypeName, tableName, businessAddress, businessPhone);
        else if ("2".equals(type))
            return getSeatMessage(vipName, newResvDate, mealTypeName, tableName, businessAddress, businessPhone);

        return "不确定的短信类型!";
    }

    /**
     * 生成入座短信
     *
     * @param vipName         用户名称
     * @param resvDate        日期
     * @param mealTypeName    餐别
     * @param tablesName      桌位名称
     * @param businessAddress 酒店地址
     * @param businessPhone   酒店电话
     * @return 入座信息文本
     */
    private String getSeatMessage(String vipName, String resvDate, String mealTypeName,
                                  String tablesName, String businessAddress, String businessPhone) {


        StringBuilder sb = new StringBuilder();
        sb
                .append(vipName)
                .append(":您好！\n")
                .append("您预订的 ")
                .append(resvDate)
                .append(" ")
                .append(mealTypeName)
                .append(" ")
                .append(tablesName)
                .append(" 已入座，祝您用餐愉快！\n")
                .append("地址：")
                .append(businessAddress)
                .append("\n电话：")
                .append(businessPhone);
        return sb.toString();
    }

    /**
     * @param templateType 模板类型
     *                     1:预定短信
     *                     2:确认预定短信
     *                     3:入座短信
     *                     4:取消预定短信
     *                     5:修改桌位短信
     * @param param        模板拼接参数
     * @return 组装完成后的短信文本
     */

    public String getMessage(Integer templateType, Map<String, Object> param) {
        if (templateType == null)
            return "模板类型为空!";

        if (templateType > 7)
            return "不确定的模板类型! templateType:" + templateType;

        String sex = getMapValue(param, "vipSex", "");
        String vipName = getMapValue(param, "vipName", "");
        if ("男".equals(sex)) {
            vipName = vipName.substring(0, 1) + "先生";
        } else if ("女".equals(sex)) {
            vipName = vipName.substring(0, 1) + "女士";
        }
        param.put("vipName", vipName);
        String tableNames ;
        Object checkedTables = MapUtils.getObject(param, "checkedTables");
        if (checkedTables != null && !templateType.equals(4)) {
            List<Map> tirs = JSONObject.parseArray(JSONObject.toJSONString(checkedTables), Map.class);

            tableNames = ("1".equals(MapUtils.getString(param, "areaNameType")) ? tirs.get(0).get("tableAreaName") + "-" : "");

            if ("1".equals(MapUtils.getString(param, "tableNameType"))){
                for (int i = 0; i < tirs.size(); i++) {
                    tableNames = tableNames + tirs.get(i).get("tableName") + ",";
                }
                tableNames = tableNames.substring(0, tableNames.length() - 1);

            }
            if (tableNames.lastIndexOf("-") == tableNames.length() - 1) {
                if (isNotEmpty(tableNames))
                    tableNames = tableNames.substring(0, tableNames.length() - 1);
            }

            param.put("tableName", tableNames);
        }

        // 1-预订,2-确认, 3-入座,4-换桌, 5-退订, 6-加桌
        // 7-宴会确认
        String message = "";
        switch (templateType) {
            case 1:
                message = getResvMessage(param);
                break;
            case 2:
                param.put("SMSType", CONFIRM_TYPE);
                message = getConfirmOrSeatMessage(param);
                break;
            case 3:
                param.put("SMSType", SEAT_TYPE);
                message = getConfirmOrSeatMessage(param);
                break;
            case 4:
                message = getChangeTableMessage(param);
                break;
            case 5:
                message = getCancelMessage(param);
                break;
            case 6:
                message = getResvMessage(param);
                break;
            case 7:
                message = getMeetingOrderMessage(param);
                break;
        }
        String businessName = getMapValue(param, "businessName", "");
        return SIGNER + (isNotEmpty(businessName) ? "【" + businessName + "】" : "") + message + "\n" + ENDING;
    }

    private String getChangeTableMessage(Map<String, Object> param) {
        String vipName = getMapValue(param, "vipName", "vipNameType");
        String tableAreaName = getMapValue(param, "tableAreaName", "areaNameType");
        String oldTableAreaName = getMapValue(param, "oldTableAreaName", "areaNameType");
        String tableName = getMapValue(param, "tableName", "");
        String oldTableName = getMapValue(param, "oldTableName", "");
        String resvNum = getMapValue(param, "resvNum", "resvNumType");
        String resvDate = getMapValue(param, "resvDate", "");
        String mealTypeName = getMapValue(param, "mealTypeName", "");
        String businessAddress = getMapValue(param, "businessAddress", "addressType");
        String addressMap = getMapValue(param, "localUrl", "mapType");
        String businessPhone = getMapValue(param, "businessPhone", "phoneType");
        String smsMessage = getMapValue(param, "smsMessage", "");

        String appUserName = getMapValue(param, "appUserName", "appUserType");
        String appUserPhone = getMapValue(param, "appUserPhone", "appUserType");
        String appUserMsg = isNotEmpty(appUserName) && isNotEmpty(appUserPhone) ? appUserName + " " + appUserPhone : "";

        if ("0".equals(getMapValue(param, "appUserId", ""))) {
            appUserMsg = null;
        }

        String weekName = Boolean.TRUE.equals(MapUtils.getBoolean(param, "weekNameType")) ? getWeekOfDate(resvDate) : "";

        String newResvDate = getFormatDate(resvDate);

        StringBuilder sb = new StringBuilder();
        sb
                .append(isNotEmpty(vipName) ? vipName : "尊敬的贵宾")
                .append("：您好！\n")
                .append("您的餐位已由")
                .append(isNotEmpty(oldTableAreaName) ? oldTableAreaName : "")
                .append(oldTableName)
                .append("更换至")
                .append(isNotEmpty(tableAreaName) ? tableAreaName : "")
                .append(tableName)
                .append("\n")
                .append(isNotEmpty(resvNum) ? "预订人数：" + resvNum + "人\n" : "")
                .append(isNotEmpty(newResvDate) ? "日期：" + newResvDate + " " : "")
                .append(weekName)
                .append(" ")
                .append(mealTypeName)
                .append("\n")
                .append(isNotEmpty(businessAddress) ? "地址：" + businessAddress + "\n" : "")
                .append(isNotEmpty(addressMap) ? "导航：" + addressMap + "\n" : "")
                .append(isNotEmpty(businessPhone) ? "电话：" + businessPhone + "\n" : "")
                .append(isNotEmpty(appUserMsg) ? "联系人：" + appUserMsg : "")
                .append(isNotEmpty(smsMessage) ? "\n" + smsMessage : "");
        return sb.toString();
    }

    /**
     * 获得map对象value
     * 根据flag判断，如果为false 返回null
     *
     * @param param   map对象
     * @param key     数据key
     * @param flagKey 条件key
     * @return 获得值
     */
    private String getMapValue(Map<String, Object> param, String key, String flagKey) {
        Boolean flag = Boolean.TRUE;
        if (isNotEmpty(flagKey))
            flag = new Integer(1).equals(MapUtils.getInteger(param, flagKey));

        if ("vipNameType".equals(flagKey))
            flag = new Integer(0).equals(MapUtils.getInteger(param, flagKey));


        return flag ? MapUtils.getString(param, key) : "";
    }

    /**
     * @param date 日期
     * @return 礼拜几
     */
    public String getWeekOfDate(String date) {
        try {
            String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(getFormatDate(date)));
            int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (w < 0)
                w = 0;
            return weekDays[w];
        } catch (Exception e) {
            LOGGER.info("异常:", e);
        }
        return "";
    }

    private Boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }

    /**
     * @param content 自定义短信模板
     * @param sort    参数排序
     * @param param   参数
     * @return
     */
    public String getMessage2(String content, List<String> sort, Integer signType, Map<String, Object> param) {
        if (MapUtils.isEmpty(param) || CollectionUtils.isEmpty(sort)) {
            return content;
        }
        //两个百分号之间的不发送
        content = content.replaceAll(Matcher.quoteReplacement("$%"), Matcher.quoteReplacement("$$"));

        Object[] params = new String[sort.size()];
        for (int i = 0; i < sort.size(); i++) {
            //如果sort.get(i) 获取的该字段不需要特殊处理(例如男女转变为先生女士) 则直接放入拼装中的params
            params[i] = MapUtils.getString(param, sort.get(i), "");
            if ("firstName".equals(sort.get(i))) {
                String vipName = MapUtils.getString(param, "vipName");
                if (isNotEmpty(vipName)) {
                    params[i] = vipName.substring(0, 1);
                }
            }else if ("vipSex".equals(sort.get(i))) {
                String vipSex = MapUtils.getString(param, "vipSex");
                if (isNotEmpty(vipSex))
                    params[i] = ("男".equals(vipSex) ? "先生" : ("女".equals(vipSex)) ? "女士" : "");
            }else if ("resvNum".equals(sort.get(i))) {
                String resvNum = MapUtils.getString(param, "resvNum");
                if (isNotEmpty(resvNum))
                    params[i] = resvNum + ("人");
            } else if ("tableName".equals(sort.get(i))) {
                String tableNames = "";
                Object checkedTables = MapUtils.getObject(param, "checkedTables");
                List<Map> tirs = JSONObject.parseArray(JSONObject.toJSONString(checkedTables), Map.class);
                if (tirs != null && tirs.size() > 0) {
                    for (int j = 0; j < tirs.size(); j++) {
                        tableNames = tableNames + tirs.get(j).get("tableName") + ",";
                    }
                    tableNames = tableNames.substring(0, tableNames.length() - 1);
                    params[i] = tableNames;
                }
            } else if ("tableAreaName".equals(sort.get(i))) {
                Object checkedTables = MapUtils.getObject(param, "checkedTables");
                List<Map> tirs = JSONObject.parseArray(JSONObject.toJSONString(checkedTables), Map.class);
                if (tirs != null && tirs.size() > 0) {
                    params[i] = tirs.get(0).get("tableAreaName");
                }
            } else if ("appUserName".equals(sort.get(i)) || "appUserPhone".equals(sort.get(i))) {
                String appUserId = MapUtils.getString(param, "appUserId", "");

                /*if ("0".equals(appUserId)) {
                    sort.remove(sort.get(i));
                    i--;
                }

                if (content.indexOf("$%") > 0) {
                    List<String> contentList = Lists.newArrayList(content.split("\\$%"));
                    content = "";
                    if ("0".equals(appUserId)) {
                        contentList.remove(contentList.get(1));
                    }

                    for (int j = 0; j < contentList.size(); j++) {
                        content += contentList.get(j);
                    }
                }*/
            } else if ("resvDate".equals(sort.get(i))) {
                try {
                    String resvDate = getMapValue(param, "resvDate", "");
                    params[i] = getFormatDate(resvDate);
                } catch (Exception e) {
                    params[i] = "";
                }
            } else if ("weekNameType".equals(sort.get(i))) {
                try {
                    String resvDate = getMapValue(param, "resvDate", "");
                    params[i] = getWeekOfDate(resvDate);
                } catch (Exception e) {
                    params[i] = "";
                }
            } else if ("lineNum".equals(sort.get(i))){
                //排队已经到达的序号
                String lineNum = getMapValue(param, "lineNum", "");
                params[i]  = lineNum + "号";

            } else if ("lineSort".equals(sort.get(i))){
                //排队序号
                String lineSort = getMapValue(param, "lineSort", "");
                params[i] = lineSort + "号";
            }
        }
        String result;
        //[易订]标志 前置还是后置
        if (signType != null && signType == 1){
            result = String.format(content, params) + SIGNER;
        } else {
            result = SIGNER + String.format(content, params);
        }

        // 判断发送平台
        Long deviceUserId = null;
        if(param.get("deviceUserId") != null){
            deviceUserId  = Long.parseLong(param.get("deviceUserId").toString());
        }

        //忽略两个百分号之间的内容
        if (result.indexOf("$$") > 0){
            List<String> contentList = Lists.newArrayList(result.split("\\$\\$"));

            StringBuilder contentBuilder = new StringBuilder();
            for (int j = 0; j < contentList.size(); j++) {
                if ((j+1)%2 == 0 && deviceUserId != null && deviceUserId != 0){
                    continue;
                }
                contentBuilder.append(contentList.get(j));
            }
            result = contentBuilder.toString();
        }

        //LOGGER.info(result);
        return result;

    }

    public String getMeetingOrderMessage(Map<String, Object> param) {
        String vipName = getMapValue(param, "vipName", "vipNameType");
        String tableName = getMapValue(param, "tableName", "tableNameType");
        String resvTableNum = getMapValue(param, "resvTableNum", "");
        String resvDate = getMapValue(param, "resvDate", "");
        String mealTypeName = getMapValue(param, "mealTypeName", "");
        String businessAddress = getMapValue(param, "businessAddress", "addressType");
        String addressMap = getMapValue(param, "localUrl", "mapType");
        String businessPhone = getMapValue(param, "businessPhone", "phoneType");
        String smsMessage = getMapValue(param, "smsMessage", "");

        String deposit = getMapValue(param, "deposit", "");
        String payType = getMapValue(param, "payType", "");

        String appUserName = getMapValue(param, "appUserName", "appUserType");
        String appUserPhone = getMapValue(param, "appUserPhone", "appUserType");
        String appUserMsg = isNotEmpty(appUserName) && isNotEmpty(appUserPhone) ? appUserName + " " + appUserPhone : "";

        if ("0".equals(getMapValue(param, "appUserId", ""))) {
            appUserMsg = null;
        }

        String weekName = Boolean.TRUE.equals(MapUtils.getBoolean(param, "weekNameType")) ? getWeekOfDate(resvDate) : "";

        String newResvDate = getFormatDate(resvDate);

        //0未确认 1 押金  2 合同 3-担保人
        String confirmOrderType = getMapValue(param, "confirmOrderType", "");

        StringBuilder sb = new StringBuilder();
        sb
                .append(isNotEmpty(vipName) ? vipName : "尊敬的贵宾")
                .append("：您好！\n")
                .append("餐位：")
                .append(tableName)
                .append("\n")
                .append(isNotEmpty(resvTableNum) ? "预订桌数：" + resvTableNum + "桌\n" : "")
                .append(isNotEmpty(newResvDate) ? "日期：" + newResvDate + " " : "")
                .append(weekName)
                .append(" ")
                .append(mealTypeName);

        if ("1".equals(confirmOrderType)) {
            String payTypeName = "";
            if ("1".equals(payType)) {
                payTypeName = "现金";
            } else if ("2".equals(payType)) {
                payTypeName = "刷卡";
            } else if ("3".equals(payType)) {
                payTypeName = "第三方网络支付";
            }
            sb.append(isNotEmpty(deposit) ? "\n押金：" + deposit : "");
            sb.append(isNotEmpty(payType) ? "\n支付方式：" + payTypeName : "");
        } else if ("2".equals(confirmOrderType)) {
            sb.append("\n合同确认");
        } else if ("3".equals(confirmOrderType)) {
            sb.append("\n担保人：");
            sb.append(getMapValue(param, "dbrName", ""));
        }

        sb.append(isNotEmpty(businessAddress) ? "\n地址：" + businessAddress : "")
                .append(isNotEmpty(addressMap) ? "\n导航：" + addressMap : "")
                .append(isNotEmpty(businessPhone) ? "\n电话：" + businessPhone : "")
                .append(isNotEmpty(appUserMsg) ? "\n联系人：" + appUserMsg : "")
                .append(isNotEmpty(smsMessage) ? "\n" + smsMessage : "");
        return sb.toString();
    }


    /**
     * 排队订单生成短信模板
     * @param resvLine
     * @return
     */
    public String getLineMessage(ResvLine resvLine) {

        String vipName = resvLine.getVipName();
        String businessName = resvLine.getBusinessName();
        String lineSort = resvLine.getLineSort().toString();
        String resvTime = resvLine.getResvTime();

        //todo 增加模板


        StringBuilder sb = new StringBuilder();
        sb
                .append("【"+businessName+"】")
                .append(isNotEmpty(vipName) ? vipName : "尊敬的贵宾")
                .append("：您好！\n")
                .append(isNotEmpty(lineSort) ? "排位序号：" + lineSort +"\n": "")
                .append(isNotEmpty(resvTime) ? "预订时间：" + resvTime + "\n": "")
                .append("系统短信，切勿回复！");

        return SIGNER+sb.toString();
    }


    /**
     * 排队订单催促短信模板
     * @param resvLine
     * @return
     */
    public String getLineUrageMessage(ResvLine resvLine,Integer sort) {

        String vipName = resvLine.getVipName();
        String businessName = resvLine.getBusinessName();
        String lineSort = resvLine.getLineSort().toString();
        String resvTime = resvLine.getResvTime();

        //todo 模板

        StringBuilder sb = new StringBuilder();
        sb
                .append("【"+businessName+"】")
                .append(isNotEmpty(vipName) ? vipName : "尊敬的贵宾")
                .append("：您好！\n")
                .append(isNotEmpty(sort.toString()) ? "前方等待：" + sort + "个\n": "")
                .append(isNotEmpty(lineSort) ? "排位序号：" + lineSort +"号\n": "")
                .append(isNotEmpty(resvTime) ? "预订时间：" + resvTime + "\n": "")
                .append("系统短信，切勿回复！");

        return SIGNER+sb.toString();
    }







    private String getFormatDate(String resvDate) {
        if (isNotEmpty(resvDate)) {
            try {
                Date date = new Date(Long.parseLong(resvDate));
                return sdf.format(date);
            } catch (Exception e) {
                LOGGER.info("日期转换异常:", e);
            }
        }
        return "";
    }

}
