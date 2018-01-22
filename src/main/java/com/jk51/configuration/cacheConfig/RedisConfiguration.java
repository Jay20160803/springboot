package com.jk51.configuration.cacheConfig;

import com.sun.tracing.ProbeName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Objects;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-19
 * 修改记录:
 */

@Configuration
@EnableCaching
@EnableRedisHttpSession
public class RedisConfiguration {

    private Logger logger = LoggerFactory.getLogger(RedisConfiguration.class);

    @Autowired
    private RedisConfig redisProps;

    @Bean
    public JedisConnectionFactory redisConnectionFactory(){
        JedisConnectionFactory factory = null;
        if(!StringUtils.isEmpty(redisProps.getNodes())){
            factory = new JedisConnectionFactory(buildSentinelConfig());
        }else{
            factory = new JedisConnectionFactory();
            factory.setHostName(redisProps.getHost());
            factory.setPort(redisProps.getPort());
        }

        factory.setPoolConfig(buildJedisPoolConfig());
        factory.setTimeout(redisProps.getTimeout());
        factory.setPassword(redisProps.getPassword());
        return factory;
    }

    @Bean
    public RedisTemplate redisTemplate(){

        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory());
        setSerializer(template);
        return template;

    }

    @Bean
    public StringRedisTemplate StringRedisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        setSerializer(template);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public CacheManager cacheManager(@Qualifier("redisTemplate") RedisTemplate template){

        RedisCacheManager cacheManager = new RedisCacheManager(template);
        cacheManager.setDefaultExpiration(redisProps.getCacheableDefaultExpSec());
        return cacheManager;
    }

    private JedisPoolConfig buildJedisPoolConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(redisProps.getMaxActive());
        config.setMaxWaitMillis(redisProps.getMaxWaitMillis());
        config.setMaxIdle(redisProps.getMaxIdle());
        config.setMinIdle(redisProps.getMinIdle());
        return config;
    }

    private RedisSentinelConfiguration buildSentinelConfig(){

        RedisSentinelConfiguration sentinels = new RedisSentinelConfiguration();
        sentinels.setMaster(redisProps.getMaster());
        Objects.requireNonNull(redisProps.getNodes());
        String[] hostAndPorts = redisProps.getNodes().split(",");

        for(String hp:hostAndPorts){
            String[] parts = hp.split(":");
            if(parts.length != 2){
                logger.error("redis sentinels nodes config error,please check again.");
            }
            sentinels.addSentinel(new RedisNode(parts[0],Integer.parseInt(parts[1])));
        }

        return sentinels;
    }


    private void setSerializer(RedisTemplate template) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        JdkSerializationRedisSerializer jdkSerializer = new JdkSerializationRedisSerializer();
        template.setKeySerializer(jdkSerializer);
        template.setValueSerializer(jdkSerializer);
        template.setHashKeySerializer(jdkSerializer);
        template.setHashValueSerializer(jdkSerializer);
        template.afterPropertiesSet();
    }
}
