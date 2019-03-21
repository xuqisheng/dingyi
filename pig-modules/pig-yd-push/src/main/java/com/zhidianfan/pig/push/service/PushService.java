package com.zhidianfan.pig.push.service;

import com.zhidianfan.pig.push.dao.entity.PushMessage;

import java.util.Set;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/1
 * @Modified By:
 */
public interface PushService {
    /**
     * 推送给单个设备
     *
     * @param registrationId
     * @param content
     * @return
     */
    boolean pushToSingle(String registrationId, String content);

    /**
     * 推送给单个设备
     *
     * @param registrationId
     * @param content
     * @param pushMessage
     * @return
     */
    boolean pushToSingle(String registrationId, String content, PushMessage pushMessage);

    /**
     * 推送给多个设备
     *
     * @param registrationIds
     * @param content
     * @return
     */
    boolean pushToAll(Set<String> registrationIds, String content);

    /**
     * 推送给多个设备
     *
     * @param registrationIds
     * @param content
     * @param pushMessage
     * @return
     */
    boolean pushToAll(Set<String> registrationIds, String content, PushMessage pushMessage);

    /**
     * 推送给标签
     *
     * @param tags 推送标签
     * @param content 推送内容
     * @return
     */
    boolean pushToTag(Set<String> tags, String content);

    /**
     * 推送给标签
     * @param tags 推送标签
     * @param content 推送内容
     * @param pushMessage 重试推送内容
     * @return
     */
    boolean pushToTag(Set<String> tags, String content, PushMessage pushMessage);

    /**
     * 推送给别名
     * @param aliases 随同别名
     * @param content 推送内容
     * @return
     */
    boolean pushToAlias(Set<String> aliases, String content);

    /**
     * 推送给别名
     * @param aliases 推送别名
     * @param content 推送内容
     * @param pushMessage 推送消息
     * @return
     */
    boolean pushToAlias(Set<String> aliases, String content, PushMessage pushMessage);
}
