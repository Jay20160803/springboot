package com.jk51.module.aop.aspectJ.service;

import com.jk51.model.User;
import com.jk51.module.annotaion.NeedTest;
import org.springframework.stereotype.Service;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-24
 * 修改记录:
 */
@Service
public class TestAspectJService {


    @NeedTest(false)
    public String test(String name,int storeAdminId,User user){

        StringBuilder builder = new StringBuilder();
        builder.append(name);
        builder.append(":");
        builder.append(storeAdminId);
        builder.append(":");
        builder.append(user);

        return builder.toString();
    }
}
