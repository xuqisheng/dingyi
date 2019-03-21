package com.zhidianfan.pig.yd.moduler.push.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.config.prop.YdPropertites;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.PushWsRegInfo;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.PushWsRegInfoMapper;
import com.zhidianfan.pig.yd.moduler.push.dto.OpenIdDTO;
import com.zhidianfan.pig.yd.moduler.push.dto.OpenIdResultDTO;
import com.zhidianfan.pig.yd.moduler.push.ws.WSMessagePush;
import com.zhidianfan.pig.yd.moduler.push.ws.dto.WSResultDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.websocket.Session;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sjl
 * 2019-03-01 16:28
 */
@Slf4j
@Service
public class WSService {

    @Autowired
    private YdPropertites ydPropertites;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 消息注册 DAO
     */
    @Autowired
    private PushWsRegInfoMapper pushRegInfoDao;

    /**
     * 检查连接的信息表中是否存在 hotel_id
     *
     * @param hotelId 酒店 id
     * @return 存在 true, 不返回 false
     */
    public boolean checkHotelIdExists(Integer hotelId) {
        Integer count = pushRegInfoDao.selectCount(new EntityWrapper<PushWsRegInfo>().eq("hotel_id", hotelId));
        return count > 0;
    }

    /**
     * 根据酒店 id,获取会话 id
     *
     * @param hotelId 酒店 id
     * @return 查询结果信息
     */
    public List<WSResultDto> getOpenid(Integer hotelId) {
        List<PushWsRegInfo> resultList = pushRegInfoDao.selectList(new EntityWrapper<PushWsRegInfo>().eq("hotel_id", hotelId));
        log.info("resultList:{}", resultList);
        List<WSResultDto> resultDtoList = convert(resultList);
        return resultDtoList;
    }

    /**
     * 转换 DTO 结果，从 resultList 选择两个需要的字段返回： sessionId, deviceType
     */
    private List<WSResultDto> convert(List<PushWsRegInfo> resultList) {
        List<WSResultDto> resultDtoList = new ArrayList<>();
        WSResultDto dto;
        for (PushWsRegInfo pushInfoEntity : resultList) {
            dto = new WSResultDto();
            // 返回给前端的是 openid
            dto.setOpenid(pushInfoEntity.getOpenid());
            dto.setDeviceType(pushInfoEntity.getDeviceType());
            resultDtoList.add(dto);
        }
        return resultDtoList;
    }

    /**
     * 下线所有设备
     */
    public void offLineAll(){
        Map<String, Session> sessionMap = WSMessagePush.getSessionMap();
        sessionMap.clear();
        pushRegInfoDao.delete(new EntityWrapper<PushWsRegInfo>().where("1=1"));
    }

    /**
     * 下线单个设备
     * @param openid 要下线设备 id
     */
    public void offLineOne(String openid) {
        Map<String, Session> sessionMap = WSMessagePush.getSessionMap();
        sessionMap.remove(openid);
        pushRegInfoDao.delete(new EntityWrapper<PushWsRegInfo>().eq("openid", openid));
    }

    /**
     * 请求微信接口，获取 openId
     * @param openIdDTO 微信接口所需要参数
     * @return openid
     */
    public String getOpenId(OpenIdDTO openIdDTO) {
        String url = ydPropertites.getPush().getUrl();
        OpenIdResultDTO openIdResultDTO = sendRequest(url, openIdDTO);
        if (openIdResultDTO == null) {
            log.error("openIdResultDTO:{}", openIdResultDTO);
            return "";
        }
        return openIdResultDTO.getOpenid();
    }

    /**
     * 发送请求
     * @param url 请求 url
     * @param openIdDTO [微信参数封装 dto]
     * @return OpenIdResultDTO
     */
    private OpenIdResultDTO sendRequest(String url, OpenIdDTO openIdDTO) {
        String requestUrl = url + "?appid={1}&secret={2}&js_code={3}&grant_type={4}";
        String result = restTemplate.getForObject(requestUrl, String.class, openIdDTO.getAppid(), openIdDTO.getSecret(), openIdDTO.getCode(), openIdDTO.getGrantType());
        log.info("微信接口返回：{}", result);
        OpenIdResultDTO openIdResultDTO = JSON.parseObject(result, OpenIdResultDTO.class);
        if (StringUtils.isNotBlank(openIdResultDTO.getOpenid())) {
            return openIdResultDTO;
        }
        return null;
    }
}
