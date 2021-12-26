package com.xxk.management.websocket.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @desp Socket处理类
 *
 */
@Service
public class ComWebSocketHandler implements WebSocketHandler {

    private static final Logger log = LogManager.getLogger();
    private static final ArrayList<WebSocketSession> users;

    static{
        users = new ArrayList<WebSocketSession>();

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session)
            throws Exception {
        log.info("成功建立socket连接");
        users.add(session);
        String username = session.getAttributes().get("userName").toString();
        session.sendMessage(new TextMessage("我们已经成功建立socket通信了"));
    }

    @Override
    public void handleMessage(WebSocketSession arg0, WebSocketMessage<?> arg1)
            throws Exception {
        log.info(arg1.getPayload());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable error)
            throws Exception {
        if(session.isOpen()){
            session.close();
        }
        log.error("连接出现错误:"+error.toString());
        users.remove(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus arg1)
            throws Exception {
        log.debug("连接已关闭");
        users.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给某个用户发送消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        for (WebSocketSession user : users) {
            if (user.getAttributes().get("userName").equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //break;
            }
        }
    }

}
