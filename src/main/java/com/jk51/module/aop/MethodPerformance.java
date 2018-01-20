package com.jk51.module.aop;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-11
 * 修改记录:
 */
public class MethodPerformance {

    private long begin;
    private long end;
    private String serviceMethod;

    public MethodPerformance(String serviceMethod){

        this.serviceMethod = serviceMethod;
        this.begin = System.currentTimeMillis();
    }

    public void printPerformance(){

        end = System.currentTimeMillis();
        long elapse = end - begin;
        System.out.println(serviceMethod+ " 花费 "+elapse+" 毫秒");
    }
}
