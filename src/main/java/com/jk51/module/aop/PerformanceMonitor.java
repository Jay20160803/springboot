package com.jk51.module.aop;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-11
 * 修改记录:
 */
public class PerformanceMonitor {

    private static ThreadLocal<MethodPerformance> performanceRecord = new ThreadLocal<>();

    public static void begin(String method){

        System.out.println("begin monitor");
        MethodPerformance mp = new MethodPerformance(method);
        performanceRecord.set(mp);
    }


    public static void end(){
        System.out.println("end monitor");
        MethodPerformance mp = performanceRecord.get();
        mp.printPerformance();
    }
}
