package com.zhidianfan.pig.push.controller;

import com.zhidianfan.pig.common.constant.ErrorTip;
import com.zhidianfan.pig.common.constant.SuccessTip;
import com.zhidianfan.pig.common.constant.Tip;
import com.zhidianfan.pig.push.controller.bo.TagAliasResult;
import com.zhidianfan.pig.push.controller.dto.DeviceDTO;
import com.zhidianfan.pig.push.controller.dto.DeviceTagAliasDTO;
import com.zhidianfan.pig.push.dao.entity.PushDevice;
import com.zhidianfan.pig.push.service.DeviceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * ljh
 * 推送终端设备
 *
 */

@RestController
@RequestMapping("/device")
public class DeviceController {


    @Resource
    private DeviceService deviceService;

    /**
     * 注册设备
     * @param deviceDTO
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity add(@Valid DeviceDTO deviceDTO){

        Tip tip = deviceService.add(deviceDTO);
        return ResponseEntity.ok(tip);
    };

    /**
     * 禁用或者启用设备(0禁用 1启用)
     * @param registrationId
     * @return
     */
    @PostMapping("/banOrOpen")
    public ResponseEntity delete(Integer banOrOpen,String registrationId){

        Tip tip = deviceService.banOrOpen(banOrOpen,registrationId);
        return ResponseEntity.ok(tip);
    };


    /**
     * (不开放)所有设备（需要修改和业务关联）
     * @param isEnable
     * @return
     */
    @GetMapping("/all")
    public ResponseEntity all(Integer isEnable){
        List<PushDevice> list = deviceService.list(isEnable);
        return ResponseEntity.ok(list);
    };


    /**
     * 查询设备的别名与标签 (极光对应)
     * @param registrationId
     * @return
     */
    @GetMapping("/deviceTagAlias")
    public ResponseEntity deviceTagAlias(String registrationId) {

        TagAliasResult tagAliasResult = deviceService.deviceInfo(registrationId);
        return ResponseEntity.ok(tagAliasResult);
    }



    /**
     * 设置设备的别名与标签 (极光对应)
     * @param registrationId
     * @return
     */
    @PostMapping("/setDeviceTagAlias")
    public ResponseEntity setDeviceTagAlias(String registrationId, @RequestBody DeviceTagAliasDTO deviceTagAliasDTO) {

        Boolean success = deviceService.deviceInfo(registrationId, deviceTagAliasDTO);
        return ResponseEntity.ok(success? SuccessTip.SUCCESS_TIP: ErrorTip.ERROR_TIP);
    }


}
