package com.clei.controller;

import com.clei.websocket.ChatSocket;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/chat")
public class ChatController {

    //这个方法主要是测试让服务端主动发送消息给浏览器
    //使用controller是为了控制发送时间。。
    @RequestMapping("send")
    @ResponseBody
    public String sendMessageToBrowser(String str) throws Exception{

        ChatSocket.sendMessage2Group(str);

        return "over";
    }
}
