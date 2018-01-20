package com.jk51.module.aop.createAdvice.beforeAdvice;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-11
 * 修改记录:
 */
public class GreetBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable{

        String clientName = (String)args[0];
        System.out.println("How are you! Mr."+clientName+" .");
    }
}
