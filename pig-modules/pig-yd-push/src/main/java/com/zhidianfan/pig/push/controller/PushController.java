package com.zhidianfan.pig.push.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Sets;
import com.zhidianfan.pig.common.constant.ErrorTip;
import com.zhidianfan.pig.common.constant.SuccessTip;
import com.zhidianfan.pig.push.controller.dto.*;
import com.zhidianfan.pig.push.dao.entity.PushDevice;
import com.zhidianfan.pig.push.dao.entity.PushUser;
import com.zhidianfan.pig.push.service.PushService;
import com.zhidianfan.pig.push.service.base.IPushDeviceService;
import com.zhidianfan.pig.push.service.base.IPushUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/1
 * @Modified By:
 */
@RestController
@RequestMapping("/push")
@Slf4j
public class PushController {
    /**
     * user数据接口
     */
    @Autowired
    private IPushUserService userService;

    /**
     * 设备数据接口
     */
    @Autowired
    private IPushDeviceService deviceService;
    /**
     * push数据接口
     */
    @Autowired
    private PushService pushService;


    /**
     * 推送给单个设备
     * @param basicSinglePushDTO
     * @return
     */
    @PostMapping("/basic/single")
    public ResponseEntity basicSinglePush(@Valid BasicSinglePushDTO basicSinglePushDTO) {
        PushDevice device = deviceService.selectOne(
                new EntityWrapper<PushDevice>()
                        .eq("registration_id", basicSinglePushDTO.getRegistrationId())
                        .eq("is_enable", 1)
        );
        if (device == null) {
            return ResponseEntity.ok(new ErrorTip(400, "用户未注册设备"));
        }

        boolean result = pushService.pushToSingle(device.getRegistrationId(), basicSinglePushDTO.getContent());

        return ResponseEntity.ok(result ? new SuccessTip(200, "推送成功") : new ErrorTip(400, "推送失败"));
    }

    /**
     * 推送给多个设备
     * @param basicBatchPushDTO
     * @return
     */
    @PostMapping("/basic/batch")
    public ResponseEntity basicBatchPush(@Valid @RequestBody BasicBatchPushDTO basicBatchPushDTO) {
        List<PushDevice> devices = deviceService.selectList(
                new EntityWrapper<PushDevice>()
                        .in("registration_id", basicBatchPushDTO.getRegistrationIds())
                        .eq("is_enable", 1)

        );
        Set<String> registrationIds = Sets.newHashSet();
        devices.forEach(device -> {
            registrationIds.add(device.getRegistrationId());
        });
        boolean result = pushService.pushToAll(registrationIds, basicBatchPushDTO.getContent());
        return ResponseEntity.ok(result ? new SuccessTip(200, "推送成功") : new ErrorTip(400, "推送失败"));
    }

    /**
     * 别名推送
     * @param aliasPushDTO
     * @return
     */
    @PostMapping("/basic/alias")
    public ResponseEntity basicAliasPush(@Valid BasicAliasPushDTO aliasPushDTO) {
        boolean result = pushService.pushToAlias(aliasPushDTO.getAlias(), aliasPushDTO.getContent());
        return ResponseEntity.ok(result ? new SuccessTip(200, "推送成功") : new ErrorTip(400, "推送失败"));
    }

    /**
     * tag推送
     * @param tagPushDTO
     * @return
     */
    @PostMapping("/basic/tag")
    public ResponseEntity basicTagPush(@Valid BasicTagPushDTO tagPushDTO) {

        boolean result = pushService.pushToTag(tagPushDTO.getTag(), tagPushDTO.getContent());
        return ResponseEntity.ok(result ? new SuccessTip(200, "推送成功") : new ErrorTip(400, "推送失败"));
    }


    @PostMapping("/single")
    public ResponseEntity push(@Validated SinglePushDTO singlePushDTO) {
        PushUser user = userService.selectOne(new EntityWrapper<PushUser>().eq("business_id", singlePushDTO.getBusinessId()).eq("username", singlePushDTO.getUsername()));
        //用户未注册
        if (user == null) {
            return ResponseEntity.ok(new ErrorTip(400, "用户未注册设备"));
        }
        //用户设置了推送
        if (user.getIsEnable() != 1) {
            return ResponseEntity.ok(new ErrorTip(400, "用户未启用推送"));
        }

        PushDevice device = deviceService.selectById(user.getDeviceId());
        //设备未注册
        if (device == null) {
            return ResponseEntity.ok(new ErrorTip(400, "设备未注册"));
        }
        //设备被禁用
        if (device.getIsEnable() != 1) {
            return ResponseEntity.ok(new ErrorTip(400, "设备被系统禁用"));
        }

        //推送给设备
        boolean result = pushService.pushToSingle(device.getRegistrationId(), singlePushDTO.getContent(), null);

        return ResponseEntity.ok(result ? new SuccessTip(200, "推送成功") : new ErrorTip(400, "推送失败"));
    }


    @PostMapping("/batch")
    public ResponseEntity batch(@RequestBody BatchPushDTO batchPushDTO) {
        List<PushUser> users = userService.selectList(new EntityWrapper<PushUser>()
                .eq("business_id", batchPushDTO.getBusinessId())
                .in("username", batchPushDTO.getUsername())
                .eq("is_enable", 1)
        );
        if (users == null || users.size() == 0) {
            return ResponseEntity.ok(new ErrorTip(400, "用户未注册设备"));
        }
        Set<String> registrationIds = Sets.newHashSet();
        users.forEach(user -> {
            registrationIds.add(user.getRegistrationId());
        });
        boolean result = pushService.pushToAll(registrationIds, batchPushDTO.getContent(), null);
        return ResponseEntity.ok(result ? new SuccessTip(200, "推送成功") : new ErrorTip(400, "推送失败"));
    }
}
