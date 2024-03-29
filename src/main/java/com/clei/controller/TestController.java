package com.clei.controller;

import com.alibaba.fastjson.JSONObject;
import com.clei.config.runner.ListStrConfig;
import com.clei.constant.Global;
import com.clei.entity.Dog;
import com.clei.entity.security.User;
import com.clei.model.request.test.ValidateReq;
import com.clei.pojo.Person;
import com.clei.service.AsyncTaskService;
import com.clei.service.DogService;
import com.clei.service.security.UserService;
import com.clei.util.RocketMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

    @Autowired
    private AsyncTaskService asyncTaskService;

    @Autowired
    private RocketMQProducer rocketMQProducer;

    @Autowired
    private Person person;

    @Autowired
    private ApplicationContext applicationContext;

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
     * 展示用户信息
     *
     * @return
     */
    @RequestMapping("/userInfo")
    @ResponseBody
    public String showRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("isAuthenticated :{}", authentication.isAuthenticated());
        logger.info("name :{}", authentication.getName());
        logger.info("authorities :{}", authentication.getAuthorities());
        logger.info("details :{}", authentication.getDetails());
        logger.info("credentials :{}", authentication.getCredentials());
        logger.info("principal :{}", authentication.getPrincipal());
        return "see log";
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
    public Map<String, String> addCookie(HttpServletResponse response, @RequestParam Map<String, String> cookieMap) {
        cookieMap.forEach((k, v) -> {
            Cookie cookie = new Cookie(k, v);
            response.addCookie(cookie);
        });
        return cookieMap;
    }

    /**
     * 接受获取到的cookie并返回
     *
     * @return
     */
    @GetMapping("getCookie")
    public Map<String, String> addCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Map<String, String> cookieMap = Arrays.stream(cookies).collect(Collectors.toMap(Cookie::getName, Cookie::getValue));
        return cookieMap;
    }

    @PostMapping("addTransactionDog")
    public String addTransactionDog(@RequestBody String json) {
        rocketMQProducer.sendTransactionMsg(MessageBuilder.withPayload(json).build(), null);
        return json;
    }

    @PostMapping("validate")
    public ValidateReq validate(@Validated @RequestBody ValidateReq req) {
        return req;
    }

    /**
     * 异步任务测试
     *
     * @return
     */
    @GetMapping("/async")
    public String async() {
        asyncTaskService.handleTestData();
        logger.info("请求执行完毕");
        return "request over!";
    }

    /**
     * 自定义starter测试
     *
     * @return
     */
    @GetMapping("/starter")
    public Person person() {
        return person;
    }

    /**
     * sleep
     * 配合优雅停机操作测试的接口
     *
     * @param sleepSecond 睡眠秒数
     * @return
     */
    @GetMapping("/sleep")
    public String sleep(@RequestParam(value = "sleepSecond", required = false, defaultValue = "888") Integer sleepSecond) {
        try {
            logger.info("睡啦睡啦 {}秒后才醒", sleepSecond);
            TimeUnit.SECONDS.sleep(sleepSecond);
        } catch (Exception e) {
            logger.error("睡眠被打断", e);
            return "啊~";
        }
        return "睡醒了";
    }

    /**
     * 优雅停机
     * <p>
     * 方法1 kill -15 pid
     * 方法2 如下
     * 方法3 actuator /actuator/shutdown
     */
    @GetMapping("/shutdown")
    public void shutdown() {
        ConfigurableApplicationContext ctx = (ConfigurableApplicationContext) applicationContext;
        ctx.close();
    }

    /**
     * 动态添加 controller
     */
    @RequestMapping("/addController")
    public Collection<Dog> addController() throws Exception {
        // 添加到BeanFactory
        ConfigurableApplicationContext ctx = (ConfigurableApplicationContext) applicationContext;
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) ctx.getBeanFactory();
        BeanDefinitionBuilder beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(NewController.class);
        String beanName = "newController";
        beanFactory.registerBeanDefinition(beanName, beanDefinition.getRawBeanDefinition());
        // 注册整个controller类
        RequestMappingHandlerMapping handlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Method method = handlerMapping.getClass().getSuperclass().getSuperclass().getDeclaredMethod("detectHandlerMethods", Object.class);
        method.setAccessible(true);
        NewController newController = (NewController) applicationContext.getBean(beanName);
        // 使用bean实例或beanName都可以
        // 之前newController.test()直接返回的对象，没有加@ResponseBody，结果它去找jsp了，报404
        // method.invoke(handlerMapping, beanName);
        method.invoke(handlerMapping, newController);
        return newController.test();
    }

    /**
     * 动态删除 controller
     */
    @RequestMapping("/deleteController")
    public String addBean() throws Exception {
        String beanName = "newController";
        Object controller = applicationContext.getBean(beanName);
        RequestMappingHandlerMapping handlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Method mappingMethod = RequestMappingHandlerMapping.class.getDeclaredMethod("getMappingForMethod", Method.class, Class.class);
        mappingMethod.setAccessible(Boolean.TRUE);
        Class<?> clazz = controller.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        // 删除接口
        for (Method m : methods) {
            RequestMappingInfo mappingInfo = (RequestMappingInfo) mappingMethod.invoke(handlerMapping, m, clazz);
            if (null != mappingInfo) {
                handlerMapping.unregisterMapping(mappingInfo);
            }
        }
        // 注销bean
        ConfigurableApplicationContext ctx = (ConfigurableApplicationContext) applicationContext;
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) ctx.getBeanFactory();
        beanFactory.removeBeanDefinition(beanName);

        return "success";
    }
}
