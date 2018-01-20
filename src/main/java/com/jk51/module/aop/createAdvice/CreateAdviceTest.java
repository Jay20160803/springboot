package com.jk51.module.aop.createAdvice;

import com.jk51.module.aop.createAdvice.beforeAdvice.GreetBeforeAdvice;
import org.junit.Test;
import org.springframework.aop.BeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-11
 * 修改记录:
 */
public class CreateAdviceTest {

    @Test
    public void beforeAdviceTest(){


        Waiter waiter = new NaiveWaiter();
        BeforeAdvice advice = new GreetBeforeAdvice();

        ProxyFactory pf = new ProxyFactory();

        pf.setTarget(waiter);
        pf.addAdvice(advice);

        Waiter proxy = (Waiter)pf.getProxy();
        proxy.greetTo("John");
        proxy.serveTo("Tom");
    }
}
