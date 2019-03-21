package com.zhidianfan.pig.yd.moduler.push.ws.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.PushWsRegInfo;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.PushWsRegInfoMapper;
import com.zhidianfan.pig.yd.moduler.push.ws.WSMessagePush;
import com.zhidianfan.pig.yd.moduler.push.ws.common.RegStateEnum;
import com.zhidianfan.pig.yd.moduler.push.ws.dto.HttpCommonRes;
import com.zhidianfan.pig.yd.moduler.push.ws.dto.WSPushInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.websocket.Session;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author sjl
 * 2019-02-28 17:35
 */
@Slf4j
@Service
public class WSMessagePushService {

    /**
     * 推送注册信息服务类
     */
    @Autowired
    private PushWsRegInfoMapper pushRegInfoDao;

    @Value("${yd.push.ws.defaultMaxConnectCount}")
    private Integer defaultMaxConnectCount;

    /**
     * 检查连接上限
     *
     * @param connectCount 当前连接数量
     * @return 超过打比赛连接数返回 false, 未超过返回 true
     */
    public boolean checkConnectMax(int connectCount) {
        return connectCount > getMaxConnectionCount();
    }

    /**
     * 从配置表里获取最大连接数，如果获取不到，则设置默认值。
     *
     * @return 配置的最大连接数量
     */
    private int getMaxConnectionCount() {
        return defaultMaxConnectCount == null ? Integer.MAX_VALUE : defaultMaxConnectCount;
    }

    /**
     * 保存连接信息
     *
     * @param sessionId  当前会话 id
     * @param hotelId    酒店 id
     * @param deviceType 设备类型 id
     * @param openid 小程序端唯一id
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean saveClientInfo(String sessionId, Integer hotelId, String deviceType, String openid) {
        PushWsRegInfo pushInfoEntity = getPushInfoEntity(sessionId, hotelId, deviceType, openid);
        log.info("保存信息到数据库中:{}", JsonUtils.obj2Json(pushInfoEntity));
        Integer i = pushRegInfoDao.insert(pushInfoEntity);
        log.info("保存信息到数据库成功:{}", JsonUtils.obj2Json(pushInfoEntity));
        return i > 0;
    }


    /**
     * 生成生成实体对象
     *
     * @param sessionId  会话 id
     * @param hotelId    酒店 id
     * @param deviceType 设备类型 id
     * @return PushInfoEntity
     */
    private PushWsRegInfo getPushInfoEntity(String sessionId, Integer hotelId, String deviceType, String openid) {
        return PushWsRegInfo.builder()
                .hotelId(hotelId)
                .sessionId(sessionId)
                .openid(openid)
                .deviceType(deviceType)
                .regState(RegStateEnum.ACTIVE.getStatCode())
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }

    /**
     * 正常断开连接，客户端发起。删除数据库中的记录
     *
     * @param sessionId 会话 id
     */
    @Transactional(rollbackFor = Exception.class)
    public void closeConnect(String sessionId) {
        Map<String, Session> sessionMap = WSMessagePush.getSessionMap();
        if (sessionMap != null) {
            sessionMap.remove(sessionId);
        }
        pushRegInfoDao.delete(new EntityWrapper<PushWsRegInfo>().eq("session_id", sessionId));
//        pushRegInfoDao.deleteBySessionId(sessionId);
    }

    /**
     * 关闭时发生异常
     *
     * @param sessionId 会话 id
     */
    @Transactional(rollbackFor = Exception.class)
    public void errorCloseException(String sessionId) {
        closeConnect(sessionId);
    }

    /**
     * 发送消息
     *
     * @param sendSession        发送消息者的连接
     * @param receiveSessionList 接收消息的连接列表
     * @param content            推送内容
     */
    public void sendMessage(Session sendSession, List<Session> receiveSessionList, String content) {
        boolean b = checkSendMessage(content);
        if (!b) {
            sendResponseMessage(sendSession, "消息格式错误");
            return;
        }
        WSPushInfoDto wsPushInfoDto = parseMessage(content);
        // session 检查
        if (!checkSession(receiveSessionList, wsPushInfoDto.getOpenid())) {
            sendResponseMessage(sendSession, "会话连接不存在");
            return;
        }
        try {
            // 向多个接收端推送消息
            for (Session receiveSession : receiveSessionList) {
                receiveSession.getBasicRemote().sendText(wsPushInfoDto.getMessage());
            }
        } catch (IOException e) {
            sendResponseMessage(sendSession, JsonUtils.obj2Json(HttpCommonRes.ERROR));
            log.error("发送消息发生异常", e);
        }
    }

    /**
     * @param session 会话连接
     * @param message 消息内容
     */
    public void sendResponseMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("响应消息发生异常", e);
        }
    }

    /**
     * session 检查是否为 null
     *
     * @param sessionList   会话连接
     * @param sessionIdList 会话 id
     * @return 为 null 返回 false 不为 null 返回 true
     */
    private boolean checkSession(List<Session> sessionList, List<String> sessionIdList) {
        if (sessionList == null) {
            // 该连接在 Map 中不存在，更改数据库中状态
            List<PushWsRegInfo> entity = getPushWsRegInfoEntity(sessionIdList);
            return false;
        } else {
            for (int i = 0; i < sessionIdList.size(); i++) {
                if (sessionIdList.get(i) == null) {
                    sessionIdList.remove(i);
                }
            }

        }
        return true;
    }

    /**
     * 对 sessionId 去重
     *
     * @param sessionIdList 去重前 sessionId 列表
     * @return 去重后的 sessionId 列表
     */
    private List<String> distinctOpenid(List<String> sessionIdList) {
        return sessionIdList.stream().distinct().collect(Collectors.toList());
    }

    /**
     * @param sessionIdList sessionId 列表
     * @return 去重后的
     */
    private List<PushWsRegInfo> getPushWsRegInfoEntity(List<String> sessionIdList) {
        // 相同的 sessionId 去重
        List<String> sessionIds = distinctOpenid(sessionIdList);
        List<PushWsRegInfo> result = new ArrayList<>(sessionIds.size());
        PushWsRegInfo entity;
        for (String sessionId : sessionIds) {
            entity = PushWsRegInfo.builder()
                    .sessionId(sessionId)
                    .build();
            result.add(entity);
        }
        return result;
    }

    /**
     * 校验发送内容格式是否正确
     *
     * @param message 发送内容
     * @return 正确-true 不正确-false
     */
    private boolean checkSendMessage(String message) {
        try {
            parseMessage(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 解析发送消息中的 sessionId
     *
     * @param content jsonString 格式的消息内容
     * @return sessionId
     */
    public List<String> parseOpenid(String content) {
        WSPushInfoDto wsPushInfoDto = parseMessage(content);
        return distinctOpenid(wsPushInfoDto.getOpenid());
    }

    /**
     * 解析发送消息的内容
     *
     * @param message jsonString 格式的消息内容
     * @return WSPushInfoDto 对象
     */
    private WSPushInfoDto parseMessage(String message) {
        return JsonUtils.jsonStr2Obj(message, WSPushInfoDto.class);
    }

    /**
     * 消息发送异常处理
     *
     * @param session 发送消息的 session
     */
    public void sendMessageException(Session session) {
        // 消息发送异常，提醒客户端，消息需要重新发送
        try {
            session.getBasicRemote().sendText(JsonUtils.obj2Json(HttpCommonRes.ERROR));
        } catch (IOException e) {
            log.error("消息发送异常", e);
        }
    }

    /**
     * 其他异常处理
     *
     * @param sessionId 会话 id
     */
    @Transactional(rollbackFor = Exception.class)
    public void otherException(String sessionId) {
        Map<String, Session> sessionMap = WSMessagePush.getSessionMap();
        sessionMap.remove(sessionId);
        pushRegInfoDao.delete(new EntityWrapper<PushWsRegInfo>().eq("session_id", sessionId));
    }

    /**
     * 根据 openid 列表查询所有的 sessionid 列表
     * @param receiveOpenidList 接收消息的 openid 列表
     * @return 接收消息的 sessionId 列表
     */
    public List<String> getSessiondIdList(List<String> receiveOpenidList) {
        log.info("openid 列表{}", receiveOpenidList);
        List<PushWsRegInfo> regInfoList = pushRegInfoDao.selectList(new EntityWrapper<PushWsRegInfo>().in("openid", receiveOpenidList));
        List<String> openidList = regInfoList.stream().map(PushWsRegInfo::getSessionId).collect(Collectors.toList());
        log.info("sessionId 列表{}", openidList);
        return openidList;
    }
}
