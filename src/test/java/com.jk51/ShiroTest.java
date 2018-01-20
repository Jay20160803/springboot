package com.jk51;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-20
 * 修改记录:
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes =  Application.class)
public class ShiroTest {

    @Autowired
    private SecurityManager securityManager;

    @Test
    public void test(){

        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("tom","123456");

        try{

            subject.login(token);

        }catch (Exception e){
            System.out.println(e);
        }

        Assert.assertEquals(true,subject.isAuthenticated());

        subject.logout();
    }

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testRedis(){

        redisTemplate.opsForValue().set("testRedis","123");

        String aa = redisTemplate.opsForValue().get("testRedis");

        Assert.assertEquals(aa,"123");
    }
}
