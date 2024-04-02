package com.sky.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@ServerEndpoint("/ws/{sid}")
public class WebSocketServer {

    // 存放会话对象
    private static Map<String, Session> sessionMap = new HashMap<>();

    /**
     * 连接简历成功调用的方法
     *
     * @param session
     * @param sid
     */
    @OnMessage
    public void onOpen(Session session, @PathParam("sid") String sid) {
        System.out.println("客户端: " + sid + "建立连接");
        sessionMap.put(sid, session);
    }

    /**
     * 收到客户端的消息后调用的方法
     *
     * @param message
     * @param sid
     */
    public void onMessage(String message, @PathParam("sid") String sid) {
        System.out.println("收到来自客户端: " + sid + "的信息: " + message);
    }

    /**
     * 连接关闭的调用的方法
     *
     * @param sid
     */
    public void onCloose(@PathParam("sid") String sid) {
        System.out.println("连接断开: " + sid);
        sessionMap.remove(sid);
    }

    /**
     * 群发
     *
     * @param message
     */
    public void sendToAllClient(String message) {
        Collection<Session> sessions = sessionMap.values();
        for (Session session : sessions) {
            try {
                // 服务器向客户端发送消息
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
