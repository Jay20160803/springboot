package com.jk51.configuration.schedulerConfig;

import com.jk51.module.quartz.util.ScheduleProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-18
 * 修改记录:
 */
@Configuration
@Component
public class SchedulerConfig {

    @Autowired
    private ScheduleProperties props;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setOverwriteExistingJobs(true);
        factoryBean.setStartupDelay(props.getStartupDelay());
        factoryBean.setTaskExecutor(createTaskExecutor());
        return factoryBean;
    }

    @Bean
    public Executor createTaskExecutor(){
        SimpleThreadPoolTaskExecutor executor = new SimpleThreadPoolTaskExecutor();
        executor.setThreadNamePrefix(props.getPrefix());
        executor.setThreadCount(props.getThreadCount());
        executor.setMakeThreadsDaemons(false);
        return executor;
    }
}
