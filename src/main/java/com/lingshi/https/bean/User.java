package com.lingshi.https.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName: User
 * @Create By: chenxihua
 * @Author: Administrator
 * @Date: 2019/12/20 10:45
 *
 *      @JSONField(name = "Id") 中的 @JSONField 字段，能在将对象转换成JSON字符串的时候，将其首字母转为大写字母
 *
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {


    @JSONField(name = "Id")
    private Integer id;

    @JSONField(name = "Username")
    private String username;

    @JSONField(name = "Password")
    private String password;

    @JSONField(name = "CreateTime", format = "yyyy-MM-dd HH:mm:ss:SSS")
    private Date createTime;

    @JSONField(name = "Execed")
    private Object execed;


}
