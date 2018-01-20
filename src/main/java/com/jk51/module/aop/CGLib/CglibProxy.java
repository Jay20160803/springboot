package com.jk51.module.aop.CGLib;

import com.jk51.module.aop.PerformanceMonitor;
import com.jk51.module.aop.jdkProxy.ForumServiceImpl;
import com.jk51.module.aop.jdkProxy.PerformanceHandle;
import com.jk51.module.aop.nonAop.ForumService;
import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;


import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-11
 * 修改记录:
 */
public class CglibProxy implements MethodInterceptor {

    private Enhancer enhancer = new Enhancer();

    public Object getProxy(Class clazz){
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return  enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        PerformanceMonitor.begin(o.getClass().getName()+"."+method.getName());
        Object result = methodProxy.invokeSuper(o,objects);
        PerformanceMonitor.end();
        return result;
    }


    @Test
    public void proxyTest(){

        CglibProxy proxy = new CglibProxy();

       ForumService forumService = (ForumService) proxy.getProxy(ForumServiceImpl.class);

       forumService.removeForum(11);
       forumService.removeTopic(22);
    }
}
