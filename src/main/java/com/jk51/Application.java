package com.jk51;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2017-12-26
 * 修改记录:
 */


@SpringBootApplication
/*@ComponentScan(basePackages = "com.jk51")*/
public class Application {


    public static void main(String[] args){

        SpringApplication.run(Application.class,args);
    }
}
