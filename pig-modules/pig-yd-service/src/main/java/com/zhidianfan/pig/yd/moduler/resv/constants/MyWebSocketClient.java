package com.zhidianfan.pig.yd.moduler.resv.constants;


import com.zhidianfan.pig.yd.config.prop.WebSocketProperties;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;

/**
 * @author hzp
 * @date 2019年03月06日14:22:01
 * @desc websocket 单例获取
 */
@Slf4j
@Component
public class MyWebSocketClient {


    private static WebSocketClient webSocketClient;

    private static WebSocketProperties webSocketProperties;

    @Autowired
    public MyWebSocketClient(WebSocketProperties webSocketProperties) {
        this.webSocketProperties = webSocketProperties;
    }

    private MyWebSocketClient() {
    }

    public static WebSocketClient getInstance(){

            try {
                webSocketClient = new WebSocketClient(new URI(webSocketProperties.getUrl() + "/wspush/30/1/0"), new Draft_6455()) {
                    @Override
                    public void onOpen(ServerHandshake serverHandshake) {
                        log.info("打开链接");
                    }

                    @Override
                    public void onMessage(String s) {
                        log.info("收到消息" + s);
                    }

                    @Override
                    public void onClose(int i, String s, boolean b) {
                        log.info("链接已关闭");
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                        log.info("发生错误已关闭");
                    }
                };
            } catch (Exception e) {
                e.printStackTrace();
            }
        return webSocketClient;
    }

}
