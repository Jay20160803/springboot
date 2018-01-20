package com.jk51.module.aop.aspectJ;

import com.jk51.module.aop.aspectJ.advisor.PreGreetinAspect;
import org.junit.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-12
 * 修改记录:
 */
public class AspectTest {

    @Test
    public void test(){

        Waiter waiter = new NaiveWaiter();

        AspectJProxyFactory factory = new AspectJProxyFactory();

        factory.setTarget(waiter);
        factory.addAspect(PreGreetinAspect.class);

        Waiter proxy = factory.getProxy();

        proxy.greetTo("John");
        proxy.serveTo("Tom");

    }
}
