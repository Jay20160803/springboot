package com.jk51.module.aop.jdkProxy;

import com.jk51.module.aop.PerformanceMonitor;
import com.jk51.module.aop.nonAop.ForumService;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-11
 * 修改记录:
 */
public class PerformanceHandle implements InvocationHandler {

    private Object target;

    public PerformanceHandle(Object target){
        this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        PerformanceMonitor.begin(target.getClass().getName()+"."+method.getName());
        Object obj = method.invoke(target,args);
        PerformanceMonitor.end();
        return obj;
    }



}
