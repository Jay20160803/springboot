package com.jk51.configuration.shiroConfig;


import com.jk51.module.shiro.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-20
 * 修改记录:
 */
public class URLPermissionsFilter extends PermissionsAuthorizationFilter{

    @Autowired
    private UserService userService;

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws IOException {


        String curUri = getRequestUri(request);
        Subject subject = SecurityUtils.getSubject();

        if(subject.getPrincipal()==null || StringUtils.endsWithAny(curUri,".js",".css",".html")
                ||StringUtils.endsWithAny(curUri,".jpg",".png",".gif",".jpeg")
                ||StringUtils.equals(curUri,"/unauthor")
                ||StringUtils.equals(curUri,"/health")
                ||StringUtils.equals(curUri,"/autoconfig")
                ||StringUtils.equals(curUri,"/beans")
                ||StringUtils.equals(curUri,"/configprops")
                ||StringUtils.equals(curUri,"/evn")
                ||StringUtils.equals(curUri,"/mappings")
                ||StringUtils.equals(curUri,"/info")
                ||StringUtils.equals(curUri,"/login")
                ||StringUtils.equals(curUri,"/logout")){

            return true;
        }


        List<String> urls = userService.findPermissionUrl(subject.getPrincipal().toString());
        return urls.contains(curUri);

    }



    private String getRequestUri(ServletRequest request){

        HttpServletRequest req = (HttpServletRequest) request;
        return req.getRequestURI();
    }
}
