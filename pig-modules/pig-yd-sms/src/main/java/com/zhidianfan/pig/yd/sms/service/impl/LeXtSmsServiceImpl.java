package com.zhidianfan.pig.yd.sms.service.impl;

import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.yd.sms.config.prop.YdPropertites;
import com.zhidianfan.pig.yd.sms.dao.entity.BaseSmsLog;
import com.zhidianfan.pig.yd.sms.dto.SmsSendResDTO;
import com.zhidianfan.pig.yd.sms.service.IBaseSmsLogService;
import com.zhidianfan.pig.yd.sms.service.LeXtSmsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;

/**
 * @Author: huzp
 * @Date: 2018/11/2 15:11
 * @DESCRIPTION 乐信通短信发送
 */
@Slf4j
@Service
public class LeXtSmsServiceImpl implements LeXtSmsService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IBaseSmsLogService baseSmsLogService;

    @Autowired
    private YdPropertites ydPropertites;


    //短信结尾
    private static final String ENDING = "【易订】";



    @Override
    public SmsSendResDTO sendMsg(String phone, String msg, String type, long smsId) {

        //如果环境是测试环境或者本地环境则直接打印日志 不实际发送短信
        //开发环境和测试环境下不发送短信
        if ("dev".equals(ydPropertites.getActive())
                || "test".equals(ydPropertites.getActive())) {

            String msgid = String.valueOf(System.currentTimeMillis());

            SmsSendResDTO smsSendResDTO = new SmsSendResDTO();
            smsSendResDTO.setCode("0");
            smsSendResDTO.setMsgId(Long.parseLong(msgid));
            smsSendResDTO.setStatus("SUCCESS");
            smsSendResDTO.setTime(new Date().toString());
            smsSendResDTO.setErrorMsg("errorMsg");
            smsSendResDTO.setText(msg);


            BaseSmsLog baseSmsLog = new BaseSmsLog();
            baseSmsLog.setId(smsId);
            baseSmsLog.setOperator(1);
            //更新msgid 本地和
            baseSmsLog.setMsgid(msgid);
            //设置提交成功
            baseSmsLog.setStatus(1);

            //更新短信
            baseSmsLog.setResTime(new Date());
            baseSmsLog.setSendRes("这是条测试短信发送成功");

            baseSmsLogService.updateById(baseSmsLog);

            log.debug("发送短信成功");

            //更新回调状态
            baseSmsLogService.updateCallBackSucStatus(baseSmsLog);

            return smsSendResDTO;
        }

        msg = msg.replace("【易订】", "");
        msg = msg.replace("【", "[");
        msg = msg.replace("】", "]");
        msg = msg + ENDING;
//        LxMsgContent lxMsgContent = createMsgContent(sdst,smsg,type);
//        String jsonStr = JsonUtils.obj2Json(lxMsgContent);
        String sname = ydPropertites.getLx().getSname();
        String spwd = ydPropertites.getLx().getSpwd();
        String scorpid = ydPropertites.getLx().getScorpid();
        String sprdid = null;
        if ("YX".equals(type)) {
            sprdid = ydPropertites.getLx().getYxsprdid();
        } else {
            sprdid = ydPropertites.getLx().getSprdid();
        }

//        String url = ydPropertites.getLx().getUrl()+
//                "?sname="+sname+"&spwd="+spwd+"&scorpid="+scorpid+"&sprdid="+sprdid+"&sdst="+phone+"&smsg="+msg;

        String url = MessageFormat.format(
                ydPropertites.getLx().getUrl() +
                        "?sname={0}" +
                        "&spwd={1}" +
                        "&scorpid={2}" +
                        "&sprdid={3}" +
                        "&sdst={4}" +
                        "&smsg={5}",
                sname, spwd, scorpid, sprdid, phone, msg);


        BaseSmsLog baseSmsLog = new BaseSmsLog();
        baseSmsLog.setId(smsId);
        baseSmsLog.setStatus(1);
        baseSmsLog.setOperator(2);
//        乐信测试实体类
//        LxSmsSendResDTO lxSmsSendResDTO = new LxSmsSendResDTO();
//        lxSmsSendResDTO.setOperator(2);
        SmsSendResDTO smsSendResDTO = new SmsSendResDTO();
        smsSendResDTO.setOperator(2);
        String res ;
        try {
//            返回方式为xml
//            String response = restTemplate.getForObject(url,String.class);
//            JSONObject xmlJSONObj = XML.toJSONObject(response);
//            JSONObject CSubmitState = xmlJSONObj.getJSONObject("CSubmitState");
//            res = CSubmitState.toString();
//            lxSmsSendResDTO.setState(CSubmitState.getInt("State"));
//            lxSmsSendResDTO.setMsgID(CSubmitState.getInt("MsgID"));
//            lxSmsSendResDTO.setMsgState(CSubmitState.getString("MsgState"));
//            lxSmsSendResDTO.setReserve(CSubmitState.getInt("Reserve"));
//            lxSmsSendResDTO.setStatus("SUCCESS");
            Map<String, Object> resMap = restTemplate.getForObject(url, Map.class);
            res = JsonUtils.obj2Json(resMap);
//            lxSmsSendResDTO.setState(MapUtils.getInteger(resMap, "State"));
//            lxSmsSendResDTO.setMsgID(MapUtils.getString(resMap,"MsgID"));
//            lxSmsSendResDTO.setMsgState(MapUtils.getString(resMap,"MsgState"));
//            lxSmsSendResDTO.setReserve(MapUtils.getInteger(resMap,"Reserve"));
//            lxSmsSendResDTO.setStatus("SUCCESS");
            smsSendResDTO.setCode(MapUtils.getString(resMap, "State"));
            baseSmsLog.setMsgid(MapUtils.getLong(resMap, "MsgID").toString());
            smsSendResDTO.setMsgId(MapUtils.getLong(resMap, "MsgID"));
            smsSendResDTO.setStatus("SUCCESS");
            smsSendResDTO.setMsgState(MapUtils.getString(resMap, "MsgState"));
            smsSendResDTO.setReserve(MapUtils.getInteger(resMap, "Reserve"));
            if (!"0".equals(resMap.get("State").toString())) {
                smsSendResDTO.setCode(MapUtils.getString(resMap, "State"));
                smsSendResDTO.setMsg("乐信短信发送失败：" + resMap.get("MsgState"));
                smsSendResDTO.setStatus("FAIL");
                res = "乐信短信发送失败：" + resMap.get("MsgState");
                baseSmsLog.setStatus(4);

                //计算短信失败条数
                int smsNum = msg.length() <= 70 ? 1 : (2 + (msg.length() - 70) / 67);
                smsNum = smsNum * phone.split(",").length;
                baseSmsLog.setFailnum(smsNum);
            }


            log.info("乐信短信发送返回结果：{}", resMap);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.error("乐信短信发送失败");
            smsSendResDTO.setCode("500");
            smsSendResDTO.setMsg(e.getMessage() + "导致乐信短信发送失败");
            smsSendResDTO.setStatus("FAIL");
//            tip = new ErrorTip(500, "乐信短信发送失败");
            res = e.getMessage();
            baseSmsLog.setStatus(4);

            //计算短信失败条数
            int smsNum = msg.length() <= 70 ? 1 : (2 + (msg.length() - 70) / 67);
            smsNum = smsNum * phone.split(",").length;
            baseSmsLog.setFailnum(smsNum);
        }
        //更新短信

        baseSmsLog.setResTime(new Date());
        baseSmsLog.setSendRes(res);

        baseSmsLogService.updateById(baseSmsLog);
        return smsSendResDTO;
    }
}
