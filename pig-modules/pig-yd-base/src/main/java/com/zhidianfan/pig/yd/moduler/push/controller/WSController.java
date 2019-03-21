package com.zhidianfan.pig.yd.moduler.push.controller;

import com.zhidianfan.pig.yd.moduler.push.dto.OpenIdDTO;
import com.zhidianfan.pig.yd.moduler.push.service.WSService;
import com.zhidianfan.pig.yd.moduler.push.ws.dto.HttpCommonRes;
import com.zhidianfan.pig.yd.moduler.push.ws.dto.WSResultDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author sjl
 * 2019-03-01 16:25
 */
@RestController
@RequestMapping("/ws")
@Slf4j
public class WSController {

    /**
     * WebSocket协议的消息推送相关服务类
     */
    @Autowired
    private WSService wsService;

    /**
     * 根据 hotel_id 查询 session_id
     *
     * @param hotelId 酒店 id
     * @return jsonArray [{"device_type_id": 2,"session_id": "6:0:3"},{"device_type_id": 2,"session_id": "6:0:3"}]
     */
    @GetMapping(value = "/openid", params = "hotelId")
    public ResponseEntity getSessionId(Integer hotelId) {
        log.debug("请求：根据hotel_id{}，查询openid", hotelId);
        boolean exists = wsService.checkHotelIdExists(hotelId);
        if (!exists) {
            return ResponseEntity.ok(new HttpCommonRes(500, "失败", "该 hotelId 不存在连接信息"));
        }
        List<WSResultDto> resultList = wsService.getOpenid(hotelId);
        log.debug("响应：根据hotelId {}查询到的sessionId列表{}", hotelId, resultList);
        return ResponseEntity.ok(new HttpCommonRes(200, "成功", resultList));
    }

    /**
     * 下线所有注册的设备
     */
    @GetMapping("/offline/all")
    public ResponseEntity offlineAll(HttpServletRequest request) {
        log.debug("下线所有设备, 发起下线IP：{}", request.getRemoteHost());
        wsService.offLineAll();
        log.debug("下线所有设备完成");
        return ResponseEntity.ok(HttpCommonRes.SUCCESS);
    }

    /**
     * 下线指定的设备
     * @param openid 要下线设备的 openid
     */
    @GetMapping(value = "/offline/one", params = "openid")
    public ResponseEntity offlineOne(String openid) {
        log.debug("下线单个设备-sessionId:{}", openid);
        wsService.offLineOne(openid);
        log.debug("下线单个设备完成");
        return ResponseEntity.ok(HttpCommonRes.SUCCESS);
    }

    /**
     * 获取 openId
     */
    @GetMapping("/wx/openid")
    public ResponseEntity openId(@Valid OpenIdDTO openIdDTO) {
        log.info("请求参数：{}", openIdDTO);
        String openId = wsService.getOpenId(openIdDTO);
        HttpCommonRes result;
        if (StringUtils.isBlank(openId)) {
            log.error("获取 openId 失败, 请求参数{}", openIdDTO);
            result = new HttpCommonRes(500, "失败", "获取 openid 失败");
        } else {
            result = new HttpCommonRes(200, "成功", openId);
            log.info("响应信息：{}", result);
        }
        return ResponseEntity.ok(result);
    }
}
