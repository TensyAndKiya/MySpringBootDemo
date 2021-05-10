package com.clei.controller;

import com.clei.bo.ChatUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 聊天服务客服接口
 *
 * @author KIyA
 */
@Controller
@RequestMapping("/chat")
public class ChatController {

    /**
     * 测试让服务端主动发送消息给浏览器
     *
     * @param msg 消息
     * @return
     * @throws Exception
     */
    @RequestMapping("send")
    @ResponseBody
    public String sendMessageToBrowser(@RequestParam("msg") String msg) throws Exception {
        ChatUser.sendMessage2Group(msg);
        return "over";
    }
}