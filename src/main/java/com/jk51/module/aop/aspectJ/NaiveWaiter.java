package com.jk51.module.aop.aspectJ;

import com.jk51.module.annotaion.NeedTest;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-11
 * 修改记录:
 */
public class NaiveWaiter implements Waiter {


    @NeedTest(false)
    @Override
    public void greetTo(String name){

        System.out.println("greet to "+name );
    }

    @Override
    public void serveTo(String name){

        System.out.println("serve to "+name);
    }
}
