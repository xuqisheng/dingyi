package com.zhidianfan.pig.yd.sms.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.base.Joiner;
import com.zhidianfan.pig.yd.sms.dao.entity.BaseSmsLog;
import com.zhidianfan.pig.yd.sms.dao.entity.ConfigSms;
import com.zhidianfan.pig.yd.sms.dao.entity.TaskSmsResult;
import com.zhidianfan.pig.yd.sms.dto.Channel;
import com.zhidianfan.pig.yd.sms.dto.ClMsgParam;
import com.zhidianfan.pig.yd.sms.dto.SmsSendResDTO;
import com.zhidianfan.pig.yd.sms.dto.SuccessTip;
import com.zhidianfan.pig.yd.sms.service.IBaseSmsLogService;
import com.zhidianfan.pig.yd.sms.service.IConfigSmsService;
import com.zhidianfan.pig.yd.sms.service.ITaskSmsResultService;
import com.zhidianfan.pig.yd.sms.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: huzp
 * @Date: 2018/11/2 15:11
 * @DESCRIPTION 短信发送
 */
@RestController
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private Map<String, SmsService> smsServiceMap;

    @Autowired
    private IConfigSmsService iConfigSmsService;

    @Autowired
    private ITaskSmsResultService iTaskSmsResultService;

    @Autowired
    private IBaseSmsLogService baseSmsLogService;



    private Logger log = LoggerFactory.getLogger(getClass());


    /**
     * 单笔普通短信发送
     *
     * @param phone 电话号码
     * @param msg 信息
     * @return 发送短信具体成功失败信息
     */
    @PostMapping(value = "/send", params = {"phone", "msg"})
    public ResponseEntity sendNormalMsg(@RequestParam String phone
            , @RequestParam String msg) {
        log.info("向 {} 发送短信 {} ", phone, msg);


        TaskSmsResult taskSmsResult = iTaskSmsResultService.selectOne(new EntityWrapper<TaskSmsResult>()
                .eq("operator_status", 1));

        SmsService smsService = smsServiceMap.get(taskSmsResult.getBeanName());


        long smsId = insertSmsLog(phone, msg).getId();


        SmsSendResDTO smsSendResDTO = smsService.sendMsg(phone, msg, "", smsId);
        return ResponseEntity.ok(smsSendResDTO);
    }



    /**
     * 批量普通短信发送
     *
     * @param clMsgParam 批量发送参数
     * @return 发送短信具体成功失败信息
     */
    @PostMapping(value = "/sendLot")
    public ResponseEntity sendBatchNormalMsg(@RequestBody ClMsgParam clMsgParam) {
        List<String> phones = clMsgParam.getPhone();
        String phone = Joiner.on(",").join(phones);
        String msg = clMsgParam.getMsg();
        log.info("向 {} 发送短信 {} ", phone, msg);

        TaskSmsResult taskSmsResult = iTaskSmsResultService.selectOne(new EntityWrapper<TaskSmsResult>()
                .eq("operator_status", 1));

        SmsService smsService = smsServiceMap.get(taskSmsResult.getBeanName());

        long smsId = insertSmsLog(phone, msg).getId();

        SmsSendResDTO smsSendResDTO = smsService.sendMsg(phone, msg, "", smsId);
        return ResponseEntity.ok(smsSendResDTO);
    }

    /**
     * 单笔营销短信发送
     *
     * @param phone 电话号码
     * @param msg 短信内容
     * @return 发送短信具体成功失败信息
     */
    @PostMapping(value = "/send/v1", params = {"phone", "msg"})
    public ResponseEntity sendmarkMsg(@RequestParam String phone
            , @RequestParam String msg) {
        log.info("向 {} 发送短信 {} ", phone, msg);

        TaskSmsResult taskSmsResult = iTaskSmsResultService.selectOne(new EntityWrapper<TaskSmsResult>()
                .eq("operator_status", 1));

        SmsService smsService = smsServiceMap.get(taskSmsResult.getBeanName());

        long smsId = insertSmsLog(phone, msg).getId();

        SmsSendResDTO smsSendResDTO = smsService.sendMsg(phone, msg, "YX", smsId);
        return ResponseEntity.ok(smsSendResDTO);
    }

    /**
     * 批量营销短信发送
     *
     * @param clMsgParam  批量发送参数
     * @return 发送短信具体成功失败信息
     */
    @PostMapping(value = "/sendLot/v1")
    public ResponseEntity sendBatchmarkMsg(@RequestBody ClMsgParam clMsgParam) {
        List<String> phones = clMsgParam.getPhone();
        String phone = Joiner.on(",").join(phones);
        String msg = clMsgParam.getMsg();
        log.info("向 {} 发送短信 {} ", phone, msg);

        TaskSmsResult taskSmsResult = iTaskSmsResultService.selectOne(new EntityWrapper<TaskSmsResult>()
                .eq("operator_status", 1));
        SmsService smsService = smsServiceMap.get(taskSmsResult.getBeanName());

        long smsId = insertSmsLog(phone, msg).getId();

        SmsSendResDTO smsSendResDTO = smsService.sendMsg(phone, msg, "YX", smsId);
        return ResponseEntity.ok(smsSendResDTO);
    }


    /**
     * 人工干预 运行商启用状态
     * @param channel 需要三个参数 1运行商id 2.开始时间 3.结束时间
     * @return 200为成功  500为失败
     */
    @PostMapping("/channelstatus")
    public ResponseEntity changeChannel(@RequestBody Channel channel) {

        //设置优先通道
        ConfigSms channelStatus = new ConfigSms();
        BeanUtils.copyProperties(channel, channelStatus);
        channelStatus.setStatus(1);
        iConfigSmsService.update(channelStatus, new EntityWrapper<ConfigSms>()
                .eq("id", channelStatus.getId()));


        //更新其他通道优先启用状态为关闭 0
        channelStatus.setStatus(0);
        iConfigSmsService.update(channelStatus, new EntityWrapper<ConfigSms>()
                .ne("id", channelStatus.getId()));

        //更改结果表
        TaskSmsResult taskSmsResult = new TaskSmsResult();

        taskSmsResult.setId(channel.getId());
        taskSmsResult.setOperatorStatus(1);
        taskSmsResult.setRemark("人工干预后结果");
        iTaskSmsResultService.update(taskSmsResult,new EntityWrapper<TaskSmsResult>()
                .eq("id", taskSmsResult.getId()));

        taskSmsResult.setOperatorStatus(0);
        iTaskSmsResultService.update(taskSmsResult,new EntityWrapper<TaskSmsResult>()
                .ne("id", taskSmsResult.getId()));


        return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
    }

    /**
     * 计算最优通道,更新result表
     * @return 返回执行成功标志 + 现在使用的beanName
     */
    @PostMapping("/calbestchannel")
    public ResponseEntity calBestChannel() {

        //计算最优通道获取beanName
        String beanName = getChannel();

        //将结果刷入结果表
        iTaskSmsResultService.updateChannelStatus(beanName);

        return ResponseEntity.ok("执行成功: " + beanName);
    }


    /**
     * 获取最优通道
     *
     * @return 通道beanName
     */
    private String getChannel() {

        //先找是否人为干预 如果有干预直接取beanName
        //人为干预条件为status 为1 且干预有效时间在现在 之后
        Date date = new Date();
        ConfigSms configSms = iConfigSmsService.selectOne(new EntityWrapper<ConfigSms>()
                .eq("status", 1)
                .lt("start_time",date)
                .gt("end_time", date));


        //获取目前推荐使用通道
        if (configSms!= null) {
            return configSms.getBeanName();
        }


        //没有干预过的则进行计算.
        //获取默认的通道
        ConfigSms defaultChannel = iConfigSmsService.selectOne(new EntityWrapper<ConfigSms>().eq("default_operator", 1));


        //计算默认通道的成功率 defaultChannelThreshold
        Double sucRate = iConfigSmsService.calChannelSucRate(defaultChannel.getId());
        //如果没有发送过短信认为成功率百分之一百
        sucRate = (null == sucRate ? 100 : sucRate);

        Double defaultChannelThreshold = Double.valueOf(defaultChannel.getThreshold());


        //查看默认通道阈值是否低于设置,不低于直接选用默认
        if (sucRate >= defaultChannelThreshold) {

            return defaultChannel.getBeanName();

        } else {

            //高于设定阈值的通道list
            List<Channel> higherChannels = new ArrayList<>();

            //低于设定阈值的通道list
            List<Channel> lowerChannels = new ArrayList<>();

            //先加入默认通道 Channel类为当前通道所有属性在加上计算得出的当前通道阈值
            Channel channelStatus = new Channel();
            BeanUtils.copyProperties(defaultChannel, channelStatus);
            channelStatus.setCurrSucRate(sucRate);

            lowerChannels.add(channelStatus);


            //取出不为默认通道外的所有通道
            List<ConfigSms> channels = iConfigSmsService.selectList(new EntityWrapper<ConfigSms>()
                    .ne("default_operator", 1));

            //如果没有其他通道则直接返回默认通道
            if (channels == null) {

                return defaultChannel.getBeanName();

            }

            Double channelSucRate;

            for (ConfigSms channel : channels) {

                Channel otherChannelStatus = new Channel();

                //计算其他通道的阈值
                channelSucRate = iConfigSmsService.calChannelSucRate(channel.getId());
                channelSucRate = (null == channelSucRate ? 100 : channelSucRate);


                BeanUtils.copyProperties(channel, otherChannelStatus);
                otherChannelStatus.setCurrSucRate(channelSucRate);

                if (channelSucRate >= Double.valueOf(channel.getThreshold())) {

                    //将阈值高于设定阈值的通道放入higherChannels
                    higherChannels.add(otherChannelStatus);

                } else {

                    //将阈值低于设定阈值的通道放入lowerChannels
                    lowerChannels.add(otherChannelStatus);
                }

            }


            //最高阈值
            Double maxThreshold = 0D;
            //最佳通道beanName 默认为创蓝
            String bestChannelName = "chuangLSmsServiceImpl";

            //如果有高于自己设定阈值的通道,则从高于自己设定阈值的通道里选择
            if (higherChannels.size() > 0) {
                for (Channel channel : higherChannels) {

                    //如果当前成功率高于阈值则替换beanName为现在channel名字
                    if (channel.getCurrSucRate() > maxThreshold) {
                        maxThreshold = channel.getCurrSucRate();
                        bestChannelName = channel.getBeanName();
                    }

                }
                return bestChannelName;
            }
            //如果没有高于自己设定阈值的通道,则从低的通道选择阈值最高的
            else {
                for (Channel channel : lowerChannels) {

                    if (channel.getCurrSucRate() > maxThreshold) {
                        maxThreshold = channel.getCurrSucRate();
                        bestChannelName = channel.getBeanName();
                    }

                }
                return bestChannelName;
            }
        }
    }

    /**
     * 插入短信日志
     *
     * @param phone 电话号码
     * @param msg 短信Neri
     * @return 返回短信日志信息
     */
    private BaseSmsLog insertSmsLog(String phone, String msg) {
        BaseSmsLog smsLog = new BaseSmsLog();
        smsLog.setPhone(phone);//手机号
        smsLog.setSmsContent(msg);//短信内容
        smsLog.setReqTime(new Date());
        baseSmsLogService.insert(smsLog);
        return smsLog;
    }

}
