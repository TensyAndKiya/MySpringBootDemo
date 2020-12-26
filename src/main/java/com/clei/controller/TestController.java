package com.clei.controller;

import com.alibaba.fastjson.JSONObject;
import com.clei.config.runner.ListStrConfig;
import com.clei.constant.Global;
import com.clei.entity.Dog;
import com.clei.entity.security.User;
import com.clei.service.DogService;
import com.clei.service.security.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private Global global;
    @Autowired
    private ListStrConfig config;
    @Autowired
    private UserService userService;

    @Autowired
    DogService dogService;

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    // 普通spring web项目里得Controller使用@Value会有问题，
    // 然而springboot项目里就没问题。。有点意思哦

    @Value("${application.hello}")
    private String str;

    @GetMapping("/insertUser")
    public User insertUser() {
        User user = new User();
        user.setLoginName("yueyaye");
        user.setNickname("月澤淵");
        user.setPassword("blackliuli");
        user.setGender(true);
        user.setAge(18);
        user.setEmail("yueyaye@163.com");

        userService.insert(user);

        return user;
    }

    @PostMapping("/commonPost")
    public String acceptJson(String json) {
        logger.info("json : {}", json);
        JSONObject obj = new JSONObject();
        obj.put("message", "");
        obj.put("status", "success");
        return obj.toJSONString();
    }

    @GetMapping("/test")
    public String test() {
        /*logger.info("This str:{},Global.str:{}",str,global.str);
        logger.info("new Global:{}",new Global().str);*/

        logger.info("{}", config.getStrs());
        return str == null ? global.str : str;
    }

    @RequestMapping("/jsonTest")
    public Map<String, Object> jsonTest(String json) {

        logger.info(json);
        JSONObject jsonObject = null;
        if (null != json && !"".equals(json)) {
            jsonObject = JSONObject.parseObject(json);
            logger.info("{}", jsonObject.get("name").toString().equals("张三"));
            logger.info("{}", jsonObject.get("age"));
            logger.info("{}", jsonObject.get("weight"));
            logger.info("{}", jsonObject.get("dog"));
        }
        return jsonObject;
    }

    @RequestMapping("getDog")
    public Dog getDog(@RequestParam("id") Integer id) {
        return dogService.getDog(id);
    }

    @RequestMapping("getDogList")
    public List<Dog> getDogList() {
        return dogService.getDogList();
    }

    /**
     * 指定文件url下载
     *
     * @param fileUrl
     * @param response
     * @throws Exception
     */
    @GetMapping("/download")
    public void download(@RequestParam("fileUrl") String fileUrl, HttpServletResponse response) throws Exception {
        if (null == fileUrl || 0 == fileUrl.length()) {
            throw new RuntimeException("文件url不能为空");
        }
        // 指定是下载文件
        response.setContentType("application/x-msdownload");
        // 指定文件名
        response.setHeader("Content-Disposition", "attachment;filename=response.pdf");

        URL url = new URL(fileUrl);
        ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
        WritableByteChannel writableByteChannel = Channels.newChannel(response.getOutputStream());

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (readableByteChannel.read(buffer) != -1) {
            //切换为读状态
            buffer.flip();
            //保证全部写入
            while (buffer.hasRemaining()) {
                writableByteChannel.write(buffer);
            }
            //清空缓冲区
            buffer.clear();
        }
    }

    /**
     * 将传入的值设置到cookie里
     *
     * @param cookieMap
     * @return
     */
    @GetMapping("addCookie")
    public String addCookie(HttpServletResponse response, @RequestParam Map<String, String> cookieMap) {
        cookieMap.forEach((k, v) -> {
            Cookie cookie = new Cookie(k, v);
            response.addCookie(cookie);
        });
        return cookieMap.toString();
    }

    /**
     * 接受获取到的cookie并返回
     *
     * @return
     */
    @GetMapping("getCookie")
    public String addCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Map<String, String> map = new HashMap<>(cookies.length);
        for (Cookie cookie : cookies) {
            map.put(cookie.getName(), cookie.getValue());
        }
        return Arrays.toString(cookies);
    }
}
