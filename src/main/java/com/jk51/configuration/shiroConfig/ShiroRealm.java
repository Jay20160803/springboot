package com.jk51.configuration.shiroConfig;


import com.jk51.module.shiro.model.UserInfo;
import com.jk51.module.shiro.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
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
    private UserService userService;


    public ShiroRealm(){
        setName("ShiroRealm");
        setCredentialsMatcher(new HashedCredentialsMatcher("md5"));
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");

        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(userService.findPermissions(username));
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        System.out.println("MyShiroRealm.doGetAuthenticationInfo()");

        //获取用户登入账户
        String username = (String) token.getPrincipal();
        System.out.println(token.getCredentials());

        UserInfo userInfo = userService.findByAccount(username);
        System.out.println("----->>userInfo="+ userInfo);

        if(userInfo ==null){
            throw  new UnknownAccountException();
        }

       return new SimpleAuthenticationInfo(username, userInfo.getPassword(),getName());


    }
}
