package com.jk51;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:1111
 * 作者: gaojie
 * 创建日期: 2017-12-26
 * 修改记录:
 */

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = "com.jk51")
public class Application {

    public static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static final String ENV_KEY_PROFILE = "spring.profiles.active";

    private static final String DEFAULT_PROFILE = "dev";

    /*@Bean
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new GatewaySpringServlet(), "/common/gateway1");
    }*/

    public static void main(String[] args) throws Exception {
        SpringApplication application = new SpringApplication(Application.class);
        SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);
        if (!source.containsProperty(ENV_KEY_PROFILE) && !System.getenv().containsKey(ENV_KEY_PROFILE)
                && StringUtils.isEmpty(System.getProperty(ENV_KEY_PROFILE))) {
            logger.warn("未指定当前运行环境({}),使用默认环境[{}]", ENV_KEY_PROFILE, DEFAULT_PROFILE);
            application.setAdditionalProfiles(DEFAULT_PROFILE);
        }/*else{
            String targetProfile = source.getProperty(ENV_KEY_PROFILE);
            application.setAdditionalProfiles(ENV_KEY_PROFILE);
        }*/
        application.run(args);
    }
}
