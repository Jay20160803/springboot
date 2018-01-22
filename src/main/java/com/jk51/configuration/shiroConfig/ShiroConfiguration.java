package com.jk51.configuration.shiroConfig;

import com.jk51.configuration.cacheConfig.RedisConfiguration;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-19
 * 修改记录:
 */
@Configuration
@Import(RedisConfiguration.class)
public class ShiroConfiguration implements ApplicationContextAware {


    private static final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);
    private ApplicationContext applicationContext;

    /**
     * FilterRegistrationBean
     *
     * */
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){

        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);
        return filterRegistration;
    }



    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){

        System.out.println("ShiroConfiguration.shirFilter()");

        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        bean.setLoginUrl("/login");

        //设置没有权限时显示的页面
        bean.setUnauthorizedUrl("/unauthor");

        //拦截器.
        Map<String,Filter> filters = new LinkedHashMap();
        filters.put("perms",urlPermissionsFilter());
        filters.put("anon", new AnonymousFilter());
        bean.setFilters(filters);


        Map<String,String> chains = new LinkedHashMap();
        chains.put("/druid/**", "anon");
        chains.put("/login", "anon");
        chains.put("/unauthor", "anon");
        chains.put("/logout", "logout");
        chains.put("/base/**", "anon");
        chains.put("/css/**", "anon");
        chains.put("/layer/**", "anon");
        chains.put("/**", "authc,perms");
        bean.setFilterChainDefinitionMap(chains);

        return bean;

    }





    @Bean
    public SecurityManager securityManager(ShiroRealm shiroRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 数据库认证的实现
        securityManager.setRealm(shiroRealm);
        // session 管理器
        securityManager.setSessionManager(sessionManager());
        // 缓存管理器
        securityManager.setCacheManager(redisCacheManager());
        return securityManager;
    }


    /**
     * DefaultWebSessionManager
     *
     * @return
     */
    @Bean
    public ServletContainerSessionManager sessionManager(){
        ServletContainerSessionManager sessionManager = new ServletContainerSessionManager();
        return sessionManager;
    }

    /**
     * @see ShiroRealm--->AuthorizingRealm
     * @return
     */
    @Bean
    public ShiroRealm shiroRealm(){
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCacheManager(redisCacheManager());
        shiroRealm.setCachingEnabled(true);
        shiroRealm.setAuthenticationCachingEnabled(false); //禁止认证缓存
        shiroRealm.setAuthorizationCachingEnabled(true);
        return shiroRealm;

    }

    @Bean
    public URLPermissionsFilter urlPermissionsFilter() {
        return new URLPermissionsFilter();
    }



    @Bean(name = "shrioRedisCacheManager")
    public ShiroRedisCacheManager redisCacheManager() {

        RedisTemplate redisTemplate = (RedisTemplate) applicationContext.getBean("redisTemplate");
        ShiroRedisCacheManager cacheManager = new ShiroRedisCacheManager(redisTemplate);
        cacheManager.createCache("shiro_redis:");
        return cacheManager;
    }



    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
