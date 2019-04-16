package com.zhidianfan.pig.yd.moduler.push.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.common.util.UserUtils;
import com.zhidianfan.pig.yd.config.prop.YdPropertites;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.BasePushLog;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.BasePushRegid;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.common.service.IBasePushLogService;
import com.zhidianfan.pig.yd.moduler.common.service.IBasePushRegidService;
import com.zhidianfan.pig.yd.moduler.push.dto.JgPush;
import com.zhidianfan.pig.yd.moduler.push.dto.JgRegDev;
import com.zhidianfan.pig.yd.moduler.push.service.ext.PushSupport;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/20
 * @Modified By:
 */
@Service
public class PushService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IBasePushRegidService pushRegidService;
    @Autowired
    private IBasePushLogService pushLogService;
    @Autowired
    private PushSupport pushSupport;

    @Autowired
    private YdPropertites ydPropertites;

    /**
     * 校验当前注册id是否有效
     *
     * @param type           设备
     * @param registrationId 注册id
     * @return
     */
//    @Cacheable(value = "base_push_regid", key = "#type+#registrationId")
    public boolean checkRegId(String type, String registrationId) {
        //TODO 如果极光有提供校验服务，则进行校验
        return true;
    }

    /**
     * 检查是否已经注册
     *
     * @param jgRegDev
     * @return
     */
//    @Cacheable(value = "base_push_regid", key = "'checkRegBefore:'+#jgRegDev.registrationId")//存在缓存，直接获取缓存，不存在缓存，查询后加入缓存
    public boolean checkRegBefore(JgRegDev jgRegDev) {
        int count = pushRegidService.selectCount(new EntityWrapper<BasePushRegid>()
//                .eq("business_id", jgRegDev.getBusinessId())
//                .eq("type", jgRegDev.getType())
//                .eq("username", userName) 注释掉，只要设备在线，就认为已经注册
                .eq("registration_id", jgRegDev.getRegistrationId()));
        return count > 0;
    }

    /**
     * 设备注册信息入库
     *
     * @param jgRegDev
     * @return
     */
//    @CacheEvict(value = "base_push_regid", allEntries = true)//删除指定key的缓存，删除缓存命名空间下的所有key
    public boolean regDev(JgRegDev jgRegDev) {
        boolean f1 = true;

        //如果设备id之前被其他手机号注册过，则覆盖注册，保证同一时刻，一个设备上只有一个账号在线，否则无法从手机号获取到正确的推送设备
        BasePushRegid basePushRegid1 = pushRegidService.selectOne(new EntityWrapper<BasePushRegid>()
                .eq("registration_id", jgRegDev.getRegistrationId()));
        try {
            if (basePushRegid1 == null) {
                BasePushRegid basePushRegid = new BasePushRegid();
                basePushRegid.setRegistrationId(jgRegDev.getRegistrationId());
                basePushRegid.setRegTime(new Date());
                basePushRegid.setUsername(UserUtils.getUserName());
                basePushRegid.setType(jgRegDev.getType());
                basePushRegid.setBusinessId(jgRegDev.getBusinessId());

                pushRegidService.insert(basePushRegid);
            } else {
                basePushRegid1.setRegTime(new Date());
                basePushRegid1.setUsername(UserUtils.getUserName());//将设备绑定到新的手机号、酒店
                basePushRegid1.setBusinessId(jgRegDev.getBusinessId());
                pushRegidService.updateById(basePushRegid1);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            f1 = false;

        }

        return f1;
    }

    /**
     * 指定酒店可推送的设备
     *
     * @param jgPush
     * @return
     */
//    @Cacheable(value = "base_push_regid", key = "'checkUserLine_'+#jgPush.businessId")//缓存哪个区酒店能够推送的数据
    public List<String> checkUserLine(JgPush jgPush) {
        //如果设备在线，则返回待推送的regid
        List<BasePushRegid> list = pushRegidService.selectPage(new Page(0, 1000), new EntityWrapper<BasePushRegid>()
                .eq("type", jgPush.getType())
                .eq("business_id", jgPush.getBusinessId())
                .eq("on_off", 1)
//                .eq("username", jgPush.getUsername())//一个用户可以登录多个预订台，并且他们操作同一个酒店  注释掉，以酒店的维度进行推送
                .isNotNull("registration_id")).getRecords();
        if (!CollectionUtils.isEmpty(list)) {
            List<String> regIds = list.stream()
                    .map(basePushRegid -> {
                        String regId = basePushRegid.getRegistrationId();
                        return regId;
                    }).collect(Collectors.toList());
            return regIds;
        }
        return null;
    }

    /**
     * 推送消息
     *
     * @param jgPush 用户名
     * @param regId  设备注册id
     * @return
     */
    public Tip pushMsg(JgPush jgPush, List<String> regId) {

        BasePushLog pushLog = this.addBasePushLogs(jgPush, regId);

//        Tip tip = pushMsgReal(jgPush, pushLog);
//        if (tip instanceof SuccessTip) {
//            SuccessTip s1 = (SuccessTip) tip;
//            s1.setContent(pushLog.getId());
//            return s1;
//        } else if (tip instanceof ErrorTip) {
//            ErrorTip e1 = (ErrorTip) tip;
//            e1.setContent(pushLog.getId());
//            return e1;
//        }
        return SuccessTip.SUCCESS_TIP;
    }

    /**
     * 消息推送
     *
     * @param jgPush
     * @param pushLog
     */
    public Tip pushMsgReal(JgPush jgPush, BasePushLog pushLog) {
        //检查是否有更新的消息已经发送了
//        int count = pushLogService.selectCount(new EntityWrapper<BasePushLog>()
////                .eq("username", jgPush.getUsername())  注释掉，只考虑酒店维度即可
//                        .eq("business_id", pushLog.getBusinessId())
//                        .gt("msg_seq", jgPush.getMsgSeq())
//        );

//        if (count > 0) {
//            pushLog.setNote("有更新的数据已经推送成功，无需推送");
//            pushLog.setPushTime(new Date());
//            pushLog.setPushStatus(3);
//            pushLogService.updateById(pushLog);
//            Tip tip = new SuccessTip(200, "有更新的数据已经推送成功，无需推送");
//            return tip;
//        } else {
            List<String> regIds = Arrays.asList(pushLog.getRegId().substring(1, pushLog.getRegId().length() - 1).split(","));
            Tip tip = pushSupport.pushMsgByRedIdsWithNode(regIds, jgPush.getMsg());
            if (tip.getCode() == 200) {//推送成功
                pushLog.setNote("推送成功");
                pushLog.setPushTime(new Date());
                pushLog.setPushStatus(1);//1-推送成功
                pushLog.setPushedCount(pushLog.getPushedCount() + 1);
                pushLogService.updateById(pushLog);

            } else {//推送失败
                pushLog.setNote("推送失败，等待下次推送:\n" + tip.getMsg());
                pushLog.setPushTime(new Date());
                pushLog.setPushStatus(2);//2-推送失败
                pushLog.setPushedCount(pushLog.getPushedCount() + 1);
                pushLogService.updateById(pushLog);
                return tip;
            }
//        }
        return SuccessTip.SUCCESS_TIP;
    }

    /**
     * 新增待推送的日志表
     *
     * @param jgPush
     * @param regId
     * @return
     */
    public BasePushLog addBasePushLogs(JgPush jgPush, List<String> regId) {
        Date now = new Date();
        //字符串转化
        BasePushLog basePushLog = new BasePushLog();

        basePushLog.setAppKey(ydPropertites.getJg().getMap().get("pad").get("appKey"));
        basePushLog.setMasterSecret(ydPropertites.getJg().getMap().get("pad").get("masterSecret"));
        basePushLog.setRegId(regId.toString().replace(" ", ""));
        basePushLog.setType(jgPush.getType());
        basePushLog.setUsername(jgPush.getUsername());
        basePushLog.setPushMsg(jgPush.getMsg());
        basePushLog.setPushStatus(0);//0-未推送
        basePushLog.setNote("等待推送");
        basePushLog.setMsgSeq(jgPush.getMsgSeq());
        basePushLog.setBusinessId(jgPush.getBusinessId());
        basePushLog.setPushedCount(0);


        basePushLog.setInsertTime(now);


        pushLogService.insert(basePushLog);
        return basePushLog;
    }

    /**
     * 检查指导客户端（AppKey）1分钟内已经推送的次数
     * 免费版限定了最高频率600条/分钟
     *
     * @return
     */
    public int countMinute() {
        int count = 0;
        List<BasePushLog> list = pushLogService.selectList(new EntityWrapper<BasePushLog>()
//                .eq("type", type)
                        .eq("app_key", ydPropertites.getJg().getMap().get("pad").get("appKey"))
                        .gt("pushed_count", 0)
                        .ge("push_time", DateUtils.addMinutes(new Date(), -1))
        );
        for (BasePushLog basePushLog : list) {
            count += basePushLog.getPushedCount();
        }
        log.info("最近一分钟，{} 推送了 {} 次", ydPropertites.getJg().getMap().get("pad").get("appKey"), count);
        return count;
    }

    /**
     * 设备离线
     *
     * @param registrationId
     */
//    @CacheEvict(value = "base_push_regid", allEntries = true)
    public boolean offlineRegDev(String registrationId) {
//        String username = UserUtils.getUserName();
        Wrapper wrapper = new EntityWrapper<>()
                .eq("registration_id", registrationId);
//                .eq("username", username);//通过设备id唯一进行离线即可
//        int count = pushRegidService.selectCount(wrapper);
//        if (count > 0) {
        pushRegidService.delete(wrapper);
        log.info("让设备：{} 离线", registrationId);
//        }
        return true;
    }

    public boolean checkDevUsers(String userName, String registrationId) {
        int count = pushRegidService.selectCount(new EntityWrapper<BasePushRegid>()
                .eq("username", userName)
                .eq("registration_id", registrationId));
        return count > 0;
    }

    /**
     * 设置推送状态
     *
     * @param status
     * @param registrationId
     */
//    @CacheEvict(value = "base_push_regid", allEntries = true)
    public void setDevPushStatus(Integer status, String registrationId) {
        BasePushRegid basePushRegid = new BasePushRegid();
        basePushRegid.setOnOff(status);
        pushRegidService.update(basePushRegid, new EntityWrapper<BasePushRegid>()
                .eq("registration_id", registrationId));
    }

    /**
     * 把该酒店未推送，而又无需推送的消息。置位
     *
     * @param businessId
     */
    public void makeUnpushed(String businessId, String msgSeq) {
        BasePushLog basePushLog = new BasePushLog();
        basePushLog.setNote("有更新的数据已经准备推送，无需推送");
        basePushLog.setPushStatus(3);
        pushLogService.update(basePushLog, new EntityWrapper<BasePushLog>()
                .eq("business_id", businessId)
                .le("msg_seq", msgSeq));

    }

    /**
     * 所有设备下线
     *
     * @return
     */
//    @CacheEvict(value = "base_push_regid", allEntries = true)
    public boolean offlineRegDevAll() {
        boolean b = pushRegidService.delete(null);
        return b;
    }
}
