package com.jk51.module.shiro.realm;

import com.jk51.module.shiro.mapper.UserMapper;
import com.jk51.module.shiro.model.Permission;
import com.jk51.module.shiro.model.Role;
import com.jk51.module.shiro.model.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-19
 * 修改记录:
 */

public class ShiroRealm extends AuthorizingRealm {


    @Autowired
    private UserMapper userMapper;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principals.getPrimaryPrincipal();
        for(Role role:user.getRoleList()){

            authorizationInfo.addRole(role.getRoleName());

            for(Permission p:role.getPermissionList()){
                authorizationInfo.addStringPermission(p.getPermissionname());
            }
        }

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        System.out.println("MyShiroRealm.doGetAuthenticationInfo()");

        //获取用户登入账户
        String username = (String) token.getPrincipal();
        System.out.println(token.getCredentials());

        User user = userMapper.findUserByName(username);
        System.out.println("----->>userInfo="+user);

        if(user==null){
            return null;
        }

       return new SimpleAuthenticationInfo(user,user.getPassword(),getName());


    }
}
