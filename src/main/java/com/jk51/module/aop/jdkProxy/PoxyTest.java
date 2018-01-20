package com.jk51.module.aop.jdkProxy;

import com.jk51.module.aop.nonAop.ForumService;
import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-11
 * 修改记录:
 */
public class PoxyTest {

    @Test
    public void proxyTest(){


        ForumService target = new ForumServiceImpl();

        PerformanceHandle handle = new PerformanceHandle(target);

        ForumService proxy = (ForumService) Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),handle);

        proxy.removeTopic(111);
        proxy.removeForum(222);
    }
}
