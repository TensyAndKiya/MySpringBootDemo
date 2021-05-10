package com.clei.websocket;

import com.clei.bo.ChatUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * chat
 * WebSocket EndPoint
 *
 * @author KIyA
 */
@ServerEndpoint(value = "/socket/chat")
@Component
public class ChatSocketServer {

    private static Logger logger = LoggerFactory.getLogger(ChatSocketServer.class);

    @OnOpen
    public void openSession(Session session) {
        // 删除用户
        ChatUser.add(session);
        String message = "有新连接加入！当前在线人数为： " + ChatUser.getOnlineCount();
        logger.info(message);
        try {
            ChatUser.sendMessage2Group(message);
        } catch (Exception e) {
            logger.error("ERROR!!!ERROR!!!ERROR!!!ERROR!!!", e);
        }
    }

    @OnClose
    public void closeSession(Session session) {
        ChatUser.remove(session);
        String message = "有一连接关闭！当前在线人数为： " + ChatUser.getOnlineCount();
        logger.info(message);
        try {
            ChatUser.sendMessage2Group(message, session);
        } catch (Exception e) {
            logger.error("ERROR!!!ERROR!!!ERROR!!!ERROR!!!", e);
        }
    }

    @OnMessage
    public void getMessage(String message, Session session) {
        logger.info("来自客户端的消息： {}", message);
        try {
            ChatUser.sendMessage2Group(message, session);
        } catch (Exception e) {
            logger.error("ERROR!!!ERROR!!!ERROR!!!ERROR!!!", e);
        }
    }

    @OnError
    public void onError(Session session, Throwable t) {
        logger.error("ERROR!!!ERROR!!!ERROR!!!ERROR!!!", t);
        closeSession(session);
    }
}
