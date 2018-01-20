package com.jk51.module.annotaion;

import org.junit.Test;

import java.lang.reflect.Method;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-12
 * 修改记录:
 */
public class ForumService {


    @NeedTest
    public void deleteForum(int forumId){

        System.out.println("删除论坛模块："+forumId);
    }

    @NeedTest(false)
    public void deleteTopic(int postId){

        System.out.println("删除论坛主题："+postId);
    }


    @Test
    public void test(){

        Class clazz = ForumService.class;

        Method[] methods =  clazz.getDeclaredMethods();

        for(Method m:methods){

            NeedTest nt = m.getAnnotation(NeedTest.class);

            if(nt !=null){
                if(nt.value()){
                    System.out.println(m.getName()+"()需要测试");
                }else {
                    System.out.println(m.getName()+"()不需要测试");
                }
            }

        }
    }
}
