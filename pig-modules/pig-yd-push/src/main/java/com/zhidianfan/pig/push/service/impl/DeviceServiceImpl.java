package com.zhidianfan.pig.push.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.common.constant.ErrorTip;
import com.zhidianfan.pig.common.constant.SuccessTip;
import com.zhidianfan.pig.common.constant.Tip;

import com.zhidianfan.pig.push.config.YdPropertites;
import com.zhidianfan.pig.push.controller.bo.TagAliasResult;
import com.zhidianfan.pig.push.controller.dto.DeviceDTO;
import com.zhidianfan.pig.push.controller.dto.DeviceTagAliasDTO;
import com.zhidianfan.pig.push.controller.dto.TagsDTO;
import com.zhidianfan.pig.push.dao.entity.PushDevice;
import com.zhidianfan.pig.push.service.DeviceService;
import com.zhidianfan.pig.push.service.base.IPushDeviceService;
import com.zhidianfan.pig.push.utils.AuthorizationUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

import java.util.*;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Resource
    private IPushDeviceService iPushDeviceService;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private YdPropertites ydPropertites;

    @Resource
    private AuthorizationUtil authorizationUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Tip add(DeviceDTO deviceDTO) {

        //本地存储
        localAdd(deviceDTO);

        //设置标签和别名
        Set<String> tag = deviceDTO.getTag();
        DeviceTagAliasDTO deviceTagAliasDTO = new DeviceTagAliasDTO();
        if (StringUtils.isNotBlank(deviceDTO.getAlias())) {
            deviceTagAliasDTO.setAlias(deviceDTO.getAlias());
        }
        if (tag != null && tag.size() > 0) {
            TagsDTO tagsDTO = new TagsDTO();
            tagsDTO.setAdd(tag);
            deviceTagAliasDTO.setTags(tagsDTO);
        }
        deviceTagAliasDTO.setMobile(deviceDTO.getMobile() == null ? "" : deviceDTO.getMobile());
        Boolean info = jgDeviceInfo(deviceDTO.getRegistrationId(), deviceTagAliasDTO);

        if (!info) {
            throw new RuntimeException("注册设备失败");
        }

        return SuccessTip.SUCCESS_TIP;
    }

    /**
     * 本地注册
     * @param deviceDTO
     */
    private void localAdd(DeviceDTO deviceDTO) {
        List<PushDevice> devices=new ArrayList();
        PushDevice pushDevice = new PushDevice();
        pushDevice.setRegistrationId(deviceDTO.getRegistrationId());

        BeanUtils.copyProperties(deviceDTO, pushDevice);

        Set<String> tag = deviceDTO.getTag();
        if (tag != null) {
            for (String s : tag) {
                PushDevice pushDeviceTag = new PushDevice();
                BeanUtils.copyProperties(deviceDTO, pushDeviceTag);
                pushDeviceTag.setTag(s);
                devices.add(pushDeviceTag);
            }
        }else{
            devices.add(pushDevice);
        }
        iPushDeviceService.insertBatch(devices);
    }

    @Override
    public Tip banOrOpen(Integer banOrOpen, String registrationId) {

        PushDevice registration = new PushDevice();
        registration.setRegistrationId(registrationId);


        PushDevice pushDevice = new PushDevice();
        pushDevice.setIsEnable(banOrOpen);
        boolean b = iPushDeviceService.update(pushDevice,new EntityWrapper<>(registration));

        return b ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP;
    }

    @Override
    public List<PushDevice> list(Integer isEnable) {
        PushDevice dStatus = new PushDevice();
        dStatus.setIsEnable(isEnable);
        List<PushDevice> pushDevices = iPushDeviceService.selectList(new EntityWrapper<>(dStatus));
        return pushDevices;
    }

    @Override
    public TagAliasResult deviceInfo(String registrationId) {
        String url = ydPropertites.getJiguang().getDeviceHostName() + ydPropertites.getJiguang().getDevicesPath() + "/" + registrationId;

        try {
            ResponseEntity<TagAliasResult> exchange = restTemplate.exchange(url, HttpMethod.GET, authorizationUtil.setJGHttpEntity(null), TagAliasResult.class);
            return exchange.getBody();
        } catch (Exception e) {
            throw new RuntimeException("查询设备的别名与标签失败" + e.getMessage());
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deviceInfo(String registrationId, DeviceTagAliasDTO deviceTagAliasDTO) {

        //本地修改
        localDeviceInfoEdit(registrationId, deviceTagAliasDTO);

        //极光修改
        Boolean aBoolean = jgDeviceInfo(registrationId, deviceTagAliasDTO);
        return aBoolean;
    }

    /**
     * 极光标签别名设置
     * @param registrationId
     * @param deviceTagAliasDTO
     * @return
     */
    private Boolean jgDeviceInfo(String registrationId, DeviceTagAliasDTO deviceTagAliasDTO) {
        String url = ydPropertites.getJiguang().getDeviceHostName() + ydPropertites.getJiguang().getDevicesPath();
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(url + "/{registrationId}",
                        authorizationUtil.setJGHttpEntity(deviceTagAliasDTO),
                        String.class, registrationId);
        return responseEntity.getStatusCode() == HttpStatus.OK ? true : false;
    }


    /**
     * 本地标签别名修改
     * @param registrationId
     * @param deviceTagAliasDTO
     */
    private void localDeviceInfoEdit(String registrationId, DeviceTagAliasDTO deviceTagAliasDTO) {

        PushDevice condition = new PushDevice();
        condition.setRegistrationId(registrationId);
        PushDevice pushDevice = iPushDeviceService.selectOne(new EntityWrapper<>(condition));
        if(pushDevice==null){
            throw new RuntimeException("设备id错误");
        }

        PushDevice pushDeviceNew = new PushDevice();
        BeanUtils.copyProperties(deviceTagAliasDTO, pushDeviceNew);

        TagsDTO tags = deviceTagAliasDTO.getTags();
        //删除
        if(tags!=null&&tags.getRemove()!=null){
            Set<String> remove = tags.getRemove();
            for (String s : remove) {
                condition.setTag(s);
                iPushDeviceService.delete(new EntityWrapper<>(condition));
            }
        }
        //新增
        if(tags!=null&&tags.getAdd()!=null){
            List<PushDevice> pushDeviceTags=new ArrayList<>();
            Set<String> add = tags.getAdd();
            for (String s : add) {
                PushDevice pushDeviceTag = new PushDevice();
                BeanUtils.copyProperties(deviceTagAliasDTO, pushDeviceTags);
                pushDeviceTag.setDeviceType(pushDevice.getDeviceType());
                pushDeviceTag.setTag(s);
                pushDeviceTag.setAlias(pushDevice.getAlias());
                pushDeviceTag.setRegistrationId(registrationId);
                pushDeviceTags.add(pushDeviceTag);
            }
            try{
                iPushDeviceService.insertBatch(pushDeviceTags);
            }catch (Exception e){
                e.printStackTrace();
               throw new RuntimeException("新增标签失败，检查是否已有标签存在");
            }
        }

        condition.setTag(null);
        iPushDeviceService.update(pushDeviceNew, new EntityWrapper<>(condition));
    }
}
