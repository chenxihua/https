package com.lingshi.https.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: Execed
 * @Create By: chenxihua
 * @Author: Administrator
 * @Date: 2019/12/20 10:45
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Execed {

    @JSONField(name = "Id")
    private Integer id;

    @JSONField(name = "DeptName")
    private String deptName;

    @JSONField(name = "Peoples")
    private Integer peoples;


}
