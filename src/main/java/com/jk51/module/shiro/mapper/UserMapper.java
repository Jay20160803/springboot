package com.jk51.module.shiro.mapper;

import com.jk51.module.shiro.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-19
 * 修改记录:
 */
@Mapper
public interface UserMapper {

    @Select("select id,account,password,name,create_time as createTime from t_user where account=#{account}")
    UserInfo findByAccount(String account);
}
