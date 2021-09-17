package com.sonic.agent.tools;

import com.alibaba.fastjson.JSONObject;
import com.sonic.agent.automation.AppiumServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
@DependsOn("rabbitMsgInit")
public class LaunchTool implements ApplicationRunner {
    private final Logger logger = LoggerFactory.getLogger(LaunchTool.class);
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${sonic.agent.key}")
    private String key;

    @Override
    public void run(ApplicationArguments args) {
        AppiumServer.start();
        JSONObject auth = new JSONObject();
        auth.put("msg", "auth");
        auth.put("key", key);
        rabbitTemplate.convertAndSend("DataExchange", "data", auth);
    }

    @PreDestroy
    public void destroy(){
        System.out.println(1);
    }
}