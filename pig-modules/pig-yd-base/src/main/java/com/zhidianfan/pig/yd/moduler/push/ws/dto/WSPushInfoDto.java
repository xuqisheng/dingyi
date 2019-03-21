package com.zhidianfan.pig.yd.moduler.push.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author sjl
 * 2019-03-01 11:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WSPushInfoDto {

    /**
     * 接收消息的 sessionId 列表
     */
    private List<String> openid;

    /**
     * 发送的消息内容，客户端的包装必须要 字符串的格式，否则后台会解析错误。
     */
    private String message;
    
}
