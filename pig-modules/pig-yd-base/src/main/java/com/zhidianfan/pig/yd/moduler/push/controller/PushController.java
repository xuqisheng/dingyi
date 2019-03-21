package com.zhidianfan.pig.yd.moduler.push.controller;

import com.zhidianfan.pig.common.util.UserUtils;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.push.dto.JgPush;
import com.zhidianfan.pig.yd.moduler.push.dto.JgRegDev;
import com.zhidianfan.pig.yd.moduler.push.service.PushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 推送相关接口
 *
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/20
 * @Modified By:
 */
@RestController
@RequestMapping("/push")
public class PushController {

    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 推送服务类
     */
    @Autowired
    private PushService pushService;

    /**
     * 检查设备是否在线
     * 由设备直接调用
     *
     * @param jgRegDev
     * @return
     */
    @GetMapping("/regid/status")
    public ResponseEntity checkDevRegBefore(@Valid JgRegDev jgRegDev) {

        boolean f = pushService.checkRegBefore(jgRegDev);

        Tip tip = f ? new SuccessTip(201, "该设备已注册") : new SuccessTip(202, "该设备未注册");
        return ResponseEntity.ok(tip);

    }

    /**
     * 注册设备id
     * 由设备直接调用
     *
     * @param jgRegDev 注册信息
     * @return
     */
    @PostMapping(value = "/regid", params = "reg")
    public ResponseEntity regDev(@Valid JgRegDev jgRegDev) {
        Tip tip = SuccessTip.SUCCESS_TIP;


        //Step 2 校验是否已经注册过了
        boolean f3 = pushService.checkRegBefore(jgRegDev);
        if (f3) {
            tip = new ErrorTip(500, "该设备已注册");
            log.error("该设备已注册", jgRegDev);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(tip);
        }

        //Step 3 校验当前注册id是否有效
        boolean f1 = pushService.checkRegId(jgRegDev.getType(), jgRegDev.getRegistrationId());
        if (!f1) {
            tip = new ErrorTip(500, "注册id无效");
            log.error("注册id无效", jgRegDev);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(tip);
        }

        //Step 4 设备注册
        boolean f2 = pushService.regDev(jgRegDev);

        if (!f2) {
            tip = new ErrorTip(500, "注册失败");
            log.error("注册失败", jgRegDev);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(tip);
        }
        return ResponseEntity.ok(tip);

    }

    /**
     * 设备离线
     *
     * @param registrationId
     * @return
     */
    @PostMapping("/regid/offline")
    public ResponseEntity offlineRegDev(@RequestParam String registrationId) {
        boolean b = pushService.offlineRegDev(registrationId);

        Tip tip = b ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP;

        return ResponseEntity.ok(tip);
    }

    /**
     * 设备离线
     *
     * @return
     */
    @PostMapping("/regid/offlineall")
    public ResponseEntity offlineRegDevAll() {
        boolean b = pushService.offlineRegDevAll();

        Tip tip = b ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP;

        return ResponseEntity.ok(tip);
    }

    /**
     * 设置推送开关状态
     *
     * @param status
     * @param registrationId
     * @return
     */
    @PostMapping("/dev/status")
    public ResponseEntity doPushStatus(@RequestParam Integer status, @RequestParam String registrationId) {

        if (status != 0
                && status != 1) {
            Tip tip = new ErrorTip(400, "status参数有误");
            return ResponseEntity.ok(tip);
        }

        //检查当前设备是否属于此用户

        boolean f1 = pushService.checkDevUsers(UserUtils.getUserName(), registrationId);
        if (!f1) {
            Tip tip = new ErrorTip(400, "该设备当前未绑定此手机，或设备id无效，无法设置推送状态");
            return ResponseEntity.ok(tip);
        }


        //设置状态
        pushService.setDevPushStatus(status, registrationId);


        return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
    }

    /**
     * 服务端消息推送到设备
     * 仅供pad预订台应用推送使用
     * 由内部服务调用
     * 第一阶段被其他服务通过网关调用
     * 后期直接内部微服务间调用
     * <p>
     * 这里推送的消息是实际数据，推向的是某个酒店的全部需要推送的PAD设备
     *
     * @param jgPush
     * @return
     */
    @PostMapping("/msg")
    public ResponseEntity pushMsg(@Valid JgPush jgPush) {

        //指定酒店可推送的设备数
        List<String> regIds = pushService.checkUserLine(jgPush);

        if (CollectionUtils.isEmpty(regIds)) {
            Tip tip = new ErrorTip(400, "当前设备未注册，或推送开关未打开，无法完成推送");
            log.debug("当前设备未注册，或推送开关未打开，无法完成推送：{}", jgPush);
            return ResponseEntity.ok(tip);
        }

        //检查近一分钟内的推送次数
//        int count = jgService.countMinute();  使用自己服务，把这个去掉
//        if (count > Integer.parseInt(ydPropertites.getJg().getMap().get("pad").get("count"))) {
//            //存入日志表等待稍候推送
//            jgService.addBasePushLogs(jgPush, regIds);
//            //把该酒店的之前的未推送，且无需推送的数据，置位
//            jgService.makeUnpushed(jgPush.getBusinessId(), jgPush.getMsgSeq());
//
//            log.info("推送过于频繁，稍候推送................");
//
//            Tip tip = new SuccessTip(201, "已存入推送队列，稍候将进行推送");
//            return ResponseEntity.ok(tip);
//        }

        //推送消息
        Tip tip = pushService.pushMsg(jgPush, regIds);

        return ResponseEntity.ok(tip);
//        if (tip.getCode() == 200) {
//            Tip tip1 = SuccessTip.SUCCESS_TIP;
//            return ResponseEntity.ok(tip1);
//        } else {
//            Tip tip1 = new ErrorTip(500,tip.getMsg());
//            return ResponseEntity.ok(tip1);
//        }

    }

    /**
     * 查询近一分钟内的PAD推送量
     *
     * @return
     */
    @GetMapping("/pad/count")
    public ResponseEntity get1MinutesCount() {
        int count = pushService.countMinute();
        Tip tip = new SuccessTip(200, count + "");
        return ResponseEntity.ok(tip);
    }

//    @Autowired
//    private PushSupport pushSupport;
//
//    @PostMapping("/test")
//    public ResponseEntity pushTest(String msg, String regId) {
//        pushSupport.pushMsgByRedIdsWithNode(Arrays.asList(regId), msg);
//        return ResponseEntity.ok(msg);
//    }

}
