package com.jk51.module.aop.aspectJ;

import com.jk51.Application;
import com.jk51.model.User;
import com.jk51.module.aop.aspectJ.advisor.PreGreetinAspect;
import com.jk51.module.aop.aspectJ.service.TestAspectJService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-12
 * 修改记录:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes =Application.class)
public class AspectTest {

    @Autowired
    private TestAspectJService service;

    @Test
    public void test(){

        User user = new User();
        user.setName("tom");
        user.setCreateTime(new Date());

         System.out.println(service.test("java",111,user));

    }
}
