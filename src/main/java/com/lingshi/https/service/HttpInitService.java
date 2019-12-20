package com.lingshi.https.service;

import com.alibaba.fastjson.JSONObject;
import com.lingshi.https.bean.Execed;
import com.lingshi.https.bean.User;
import com.lingshi.https.util.SendHttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.nio.cs.US_ASCII;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: HttpInitService
 * @Create By: chenxihua
 * @Author: Administrator
 * @Date: 2019/12/20 9:28
 **/
@Service
public class HttpInitService {

    private static final Logger logger = LoggerFactory.getLogger(HttpInitService.class);

    @Autowired
    SendHttpUtil httpUtil;


//    @PostConstruct
//    public void postMessage(){
//        String url = "http://127.0.0.1:8155/hello";
//
//        Execed exec = new Execed();
//        exec.setId(12);
//        exec.setDeptName("云防系统部门");
//        exec.setPeoples(25);
//
//        User user = new User();
//        user.setId(1);
//        user.setUsername("陈喜华，chenxihua");
//        user.setPassword("12345");
//        user.setCreateTime(new Date());
//        user.setExeced(exec);
//
//        JSONObject message = new JSONObject();
//        message.put("Message", user);
//
//        logger.warn("发送的消息： {}", message.toString());
//        String doPostData = httpUtil.doPost(url, message.toString());
//        logger.warn("doPostData: {}", doPostData);
//
//        // 数据格式转换
//        JSONObject parseObject = JSONObject.parseObject(doPostData);
//        Integer code = (Integer) parseObject.get("code");
//        String msg = (String) parseObject.get("message");
//        JSONObject objectJSONObject = parseObject.getJSONObject("object");
//        User jsonToUser = JSONObject.toJavaObject(objectJSONObject, User.class);
//        logger.warn("转换后的数据： {}, {}, {}", code, msg, jsonToUser.toString());
//
//        /**
//         * 这个是输出的结果：
//         * 发送的消息： {"Message":{"CreateTime":"2019-12-20 13:51:55:142","Execed":{"DeptName":"云防系统部门","Id":12,"Peoples":25},"Id":1,"Password":"12345","Username":"陈喜华，chenxihua"}}
//         * doPostData: {"code":0,"message":"成功","object":{"Username":"陈喜华，chenxihua","CreateTime":"2019-12-20 13:51:55:142","Execed":{"Peoples":25,"DeptName":"云防系统部门","Id":12},"Id":1,"Password":"12345"}}
//         * 转换后的数据： 0, 成功, User(id=1, username=陈喜华，chenxihua, password=12345, createTime=Fri Dec 20 13:51:55 CST 2019, execed={"Peoples":25,"DeptName":"云防系统部门","Id":12})
//         */
//
//    }


//    @PostConstruct
//    public void postMessage(){
//        String url = "http://127.0.0.1:8155/hello";
//
//        JSONObject exec = new JSONObject();
//        exec.put("id", 10);
//        exec.put("username", "陈喜华，aili");
//        exec.put("password", "123456");
//
//        JSONObject message = new JSONObject();
//        message.put("Message", exec);
//        logger.warn("发送的消息： {}", message.toString());
//        String doPostData = httpUtil.doPost(url, message.toString());
//        logger.warn("doPostData: {}", doPostData);
//    }


    @PostConstruct
    public void getMessage(){
        String url = "http://127.0.0.1:8155/getIdAndName";
        Map<String, String> params = new HashMap<>();
        params.put("id", "119");
        params.put("username", "朱夏宇");

        Map<String, String> headers = new HashMap<>();
        // 这里的真谛是，如果对接那边没有指定的数据格式类型，我可以任意使用 GBK 或者 utf-8
        // 所以，请求工具类是没有问题的，这里，我可以指定任意的数据格式
        headers.put("Content-Type", "application/json;charset=GBK");

        String doGetData = httpUtil.doGet(url, params);
        logger.warn("data: {}", doGetData);
        /**
         * 输出结果：
         * data: {"code":0,"message":"查询成功","object":{"id":119,"username":"朱夏宇","age":119}}
         */
    }

//    @PostConstruct
//    public void getMessage(){
//        String url = "http://127.0.0.1:8155/getUsername";
//        Map<String, String> params = new HashMap<>();
//        params.put("username", "朱夏宇");
//
//        String doGetData = httpUtil.doGet(url, params);
//        logger.warn("data: {}", doGetData);
//    }

//    @PostConstruct
//    public void getMessage(){
//        String url = "http://127.0.0.1:8155/chenxihua";
//        String doGetData = httpUtil.doGet(url);
//        logger.warn("data: {}", doGetData);
//    }




}
