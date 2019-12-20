package com.lingshi.https;

import com.lingshi.https.util.SendHttpUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @ClassName: HttpTest
 * @Create By: chenxihua
 * @Date: 2019/9/23 10:05
 */
public class HttpTest {

    @Autowired
    SendHttpUtil httpUtil;

    @Test
    public void testHttp(){
        Date date = new Date();
        System.out.println(date);
    }


}
