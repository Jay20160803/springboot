package com.jk51.module.aop.aspectJ.advisor;

import com.jk51.module.annotaion.NeedTest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-12
 * 修改记录:
 */
@Aspect
@Component
public class PreGreetinAspect {

    /*@Before("execution(* greetTo(..))&&args(name,..)&&@annotation(nt)")
    public void beforeGreeting(JoinPoint joinPoint,String name,NeedTest nt){

        System.out.println(name);
        System.out.println(nt.value());



    }*/



    //通过目标类和方法定义注解
    @Pointcut("execution(* com.jk51.module.aop.aspectJ.service.TestAspectJService.*(..))")
    public void pointCutForExecution(){}

    //通过注解定义切点
    @Before("@annotation(bt)")
    public void before(JoinPoint point, NeedTest bt){
        System.out.println("how are you before! value:"+bt.value());




        System.out.println("@Before：目标方法为：" +

                point.getSignature().getDeclaringTypeName() +

                "." + point.getSignature().getName());

        System.out.println("@Before：参数为：" +getParamMap(point));

        System.out.println("@Before：被织入的目标对象为：" + point.getTarget());

    }


    @AfterReturning("pointCutForExecution()")
    public void afterReturning(){
        System.out.println("how are you afterReturning!");
    }

    private Map<String,Object> getParamMap(JoinPoint joinPoint){

        Map<String,Object> result = new HashMap();

        Object[] paramValues = joinPoint.getArgs();
        String[] paramNames = ((CodeSignature) joinPoint
                .getSignature()).getParameterNames();

        for(int i=0;i<paramNames.length;i++){
            result.put(paramNames[i],paramValues[i]);
        }

        return result;
    }
}
