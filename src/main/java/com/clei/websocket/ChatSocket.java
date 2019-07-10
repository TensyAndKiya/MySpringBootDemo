package com.clei.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/socket/chat")
@Component
public class ChatSocket {
    private static int onlineCount = 0;
    private static CopyOnWriteArraySet<ChatSocket> websocketSet = new CopyOnWriteArraySet<>();
    private Session session;

    private static Logger logger = LoggerFactory.getLogger(ChatSocket.class);

    @OnOpen
    public void openSession(Session session){
        this.session = session;
        ChatSocket.websocketSet.add(this);
        ChatSocket.addOnlineCount();
        String message = "有新连接加入！当前在线人数为： "+getOnlineCount();
        logger.info(message);
        try {
            sendMessage(message);
        } catch (IOException e) {
            logger.info("ERROR!!!ERROR!!!ERROR!!!ERROR!!!" + "OPENSESSION");
        }
    }

    @OnClose
    public void closeSession(){
        ChatSocket.websocketSet.remove(this);
        ChatSocket.subOnlineCount();
        logger.info("有一连接关闭！当前在线人数为： {}",getOnlineCount());
    }

    @OnMessage
    public void getMessage(String message, Session session){
        logger.info("来自客户端的消息： {}",message);
        try {
            sendMessage2Group(message);
        } catch (Exception e) {
           logger.info("ERROR!!!ERROR!!!ERROR!!!ERROR!!!" + "GETMESSAGE");
        }
    }

    @OnError
    public void onError(Session session, Throwable error){
        logger.info("ERROR!!!ERROR!!!ERROR!!!ERROR!!!" + "ONERROR");
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    public static void sendMessage2Group(String message) throws Exception {
        for(ChatSocket cs : ChatSocket.websocketSet){
            cs.sendMessage(message);
        }
    }

    public static synchronized void addOnlineCount(){
        ChatSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount(){
        ChatSocket.onlineCount--;
    }

    public static int getOnlineCount(){
        return ChatSocket.onlineCount;
    }

}
