package com.jk51.module.aop.aspectJ.advisor;

import com.jk51.module.annotaion.NeedTest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Bean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-12
 * 修改记录:
 */
@Aspect
public class PreGreetinAspect {

    @Before("execution(* greetTo(..))&&args(name,..)&&@annotation(nt)")
    public void beforeGreeting(JoinPoint joinPoint,String name,NeedTest nt){

        System.out.println(name);
        System.out.println(nt.value());



    }
}
