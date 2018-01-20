package com.jk51.module.aop.nonAop;

import com.jk51.module.aop.PerformanceMonitor;
import org.junit.Test;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-11
 * 修改记录:
 */
public class ForumServiceImpl implements ForumService {


    @Override
    public void removeTopic(int topic) {

        PerformanceMonitor.begin("ForumServiceImpl.removeTopic");

        System.out.println("模拟删除Topic记录： "+topic);

        try{
            Thread.currentThread().sleep(20);
        }catch (Exception e){

        }

        PerformanceMonitor.end();
    }

    @Override
    public void removeForum(int forumId) {
        PerformanceMonitor.begin("ForumServiceImpl.removeTopic");

        System.out.println("模拟删除Forum记录： "+forumId);

        try{
            Thread.currentThread().sleep(20);
        }catch (Exception e){

        }

        PerformanceMonitor.end();

    }


    @Test
    public void test(){

        ForumServiceImpl forumService = new ForumServiceImpl();
        forumService.removeForum(111);
        forumService.removeTopic(222);


    }

}
