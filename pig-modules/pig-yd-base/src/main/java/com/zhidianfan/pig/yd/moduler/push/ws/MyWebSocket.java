package com.zhidianfan.pig.yd.moduler.push.ws;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;


@ServerEndpoint(value = "/websocket")
@Component
public class MyWebSocket {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
//    虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
//    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //便于往指定客户端发送消息
    private static Map<String, Session> stringSessionMap = new LinkedHashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        String sessionId = this.session.getId();
        System.out.println("当前客户端sessionId：" + sessionId);
        stringSessionMap.put(sessionId, this.session);
//        webSocketSet.add(this);     //加入set中
        System.out.println("有新连接加入！当前在线人数为" + stringSessionMap.size());
        try {
            sendMessage("当前在线人数：" + stringSessionMap.size());
        } catch (IOException e) {
            System.out.println("IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        stringSessionMap.remove(this.session.getId());
        System.out.println("有一连接关闭！当前在线人数为" + stringSessionMap.size());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("来自客户端的消息:" + message);

        if (message.indexOf(",") > 0) {//发送给指定客户端
            String[] m = message.split(",");
            Session s1 = stringSessionMap.get(m[0]);
            s1.getBasicRemote().sendText(m[1]);
            session.getBasicRemote().sendText("往 " + m[0] + " 推送消息成功");
        } else {//群发消息
//            sendInfo(message);
        }

    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 发送消息
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
//        this.session.getAsyncRemote().sendText(message);
    }

}