package com.jk51.module.quartz.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: wangzhengfei
 * 创建日期: 2017-02-22
 * 修改记录:
 */
@Component
@Configuration
@ConfigurationProperties(prefix="schedule")
public class ScheduleProperties {

    private boolean enabled = true;

    private boolean overwrite = true;

    private int startupDelay = 30;

    private String prefix;

    private int threadCount = 100;

    private String defaultServerAddr;

    private String agentUrl;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isOverwrite() {
        return overwrite;
    }

    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
    }

    public int getStartupDelay() {
        return startupDelay;
    }

    public void setStartupDelay(int startupDelay) {
        this.startupDelay = startupDelay;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }



    public String getDefaultServerAddr() {
        return defaultServerAddr;
    }

    public void setDefaultServerAddr(String defaultServerAddr) {
        this.defaultServerAddr = defaultServerAddr;
    }

    public String getAgentUrl() {
        return agentUrl;
    }

    public void setAgentUrl(String agentUrl) {
        this.agentUrl = agentUrl;
    }


}
