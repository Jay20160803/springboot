package com.jk51;

import com.jk51.module.shiro.mapper.UserMapper;
import com.jk51.module.shiro.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2017-12-27
 * 修改记录:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class LogTest {

    private Logger logger = LoggerFactory.getLogger(LogTest.class);

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test(){

       User user = userMapper.findUserByName("tom");
        user.getId();
    }



}
