package com.clei.websocket;

import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/socket/chat")
@Component
public class ChatSocket {
    private static volatile int onlineCount = 0;
    private static CopyOnWriteArraySet<ChatSocket> websocketSet = new CopyOnWriteArraySet<>();
    private Session session;

    @OnOpen
    public void openSession(Session session){
        this.session = session;
        ChatSocket.websocketSet.add(this);
        ChatSocket.addOnlineCount();
        String message = "有新连接加入！当前在线人数为： "+getOnlineCount();
        System.out.println(message);
        try {
            sendMessage(message);
        } catch (IOException e) {
            System.out.println("ERROR!!!ERROR!!!ERROR!!!ERROR!!!" + "OPENSESSION");
        }
    }

    @OnClose
    public void closeSession(){
        ChatSocket.websocketSet.remove(this);
        ChatSocket.subOnlineCount();
        String message = "有一连接关闭！当前在线人数为： "+getOnlineCount();
        System.out.println(message);
    }

    @OnMessage
    public void getMessage(String message, Session session){
        System.out.println("来自客户端的消息： "+ message);
        try {
            sendMessage2Group(message);
        } catch (Exception e) {
            System.out.println("ERROR!!!ERROR!!!ERROR!!!ERROR!!!" + "GETMESSAGE");
        }
    }

    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("ERROR!!!ERROR!!!ERROR!!!ERROR!!!" + "ONERROR");
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
