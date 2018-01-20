package com.jk51.configuration.cacheConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-20
 * 修改记录:
 */

@ConfigurationProperties(prefix = "spring.redis")
@Component
public class RedisConfig {


    /**
     * redis sentinel master 名称，通常配置在sentinel.conf中
     */
    @Value("${spring.redis.sentinel.master}")
    private String master;

    /**
     * sentinel节点，逗号分隔，格式如：172.20.10.192:26379,172.20.10.193:26379
     */
    @Value("${spring.redis.sentinel.nodes}")
    private String nodes;

    /**
     * 连接超时时间，单位：毫秒
     */
    @Value("${spring.redis.timeout}")
    private int timeout;

    /**
     * 最小空闲连接数
     */
    @Value("${spring.redis.pool.min-idle}")
    private int minIdle;

    /**
     * 最大空闲连接数
     */
    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;

    /**
     * 最大激活连接数
     */
    @Value("${spring.redis.pool.max-active}")
    private int maxActive;

    /**
     * 最大等待毫秒数
     */
    @Value("${spring.redis.pool.max-wait}")
    private long maxWaitMillis;

    /**
     * redis密码
     */
    @Value("${spring.redis.host}")
    private String host;

    /**
     * redis密码
     */
    @Value("${spring.redis.port}")
    private int port;

    /**
     * redis密码
     */
    @Value("${spring.redis.password}")
    private String password;

    /**
     * 通过@Cacheable代理的缓存默认失效时间(单位：秒)
     */
    @Value("${spring.redis.cacheableDefaultExpSec}")
    private int cacheableDefaultExpSec;

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public long getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(long maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCacheableDefaultExpSec() {
        return cacheableDefaultExpSec;
    }

    public void setCacheableDefaultExpSec(int cacheableDefaultExpSec) {
        this.cacheableDefaultExpSec = cacheableDefaultExpSec;
    }
}
