package com.zhidianfan.pig.push.service;

import com.zhidianfan.pig.common.constant.Tip;
import com.zhidianfan.pig.push.controller.bo.TagAliasResult;
import com.zhidianfan.pig.push.controller.dto.DeviceDTO;
import com.zhidianfan.pig.push.controller.dto.DeviceTagAliasDTO;
import com.zhidianfan.pig.push.dao.entity.PushDevice;

import java.util.List;

public interface DeviceService {

    /**
     * 添加设备
     * @param deviceDTO
     * @return
     */
    Tip add(DeviceDTO deviceDTO);


    /**
     * 禁用或启用
     * @param
     * @return
     */
    Tip banOrOpen(Integer banOrOpen,String registrationId);

    /**
     * 设备列表
     * @param status
     * @return
     */
    List<PushDevice> list(Integer status);


    /**
     * 查询设备的别名与标签 (极光对应)
     * @param registrationId
     * @return
     */
    TagAliasResult deviceInfo(String registrationId);


    /**
     * 设置设备的别名与标签(极光对应)
     * @param deviceTagAliasDTO
     * @return
     */
    Boolean deviceInfo(String registrationId,DeviceTagAliasDTO deviceTagAliasDTO);




}
