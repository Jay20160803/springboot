package com.jk51.configuration.shiroConfig;

import com.jk51.module.util.SerializeUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.assertj.core.util.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.*;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-22
 * 修改记录:
 */
public class ShiroRedisCache<K,V> implements Cache<K,V> {

    private static final Logger logger = LoggerFactory.getLogger(ShiroRedisCache.class);


    private RedisTemplate<byte[],V> redisTemplate;
    private String prefix = "shiro_redis:";

    public ShiroRedisCache(RedisTemplate<byte[], V> shiroRedisTemplate) {
        this.redisTemplate = shiroRedisTemplate;
    }

    public ShiroRedisCache(RedisTemplate<byte[], V> shiroRedisTemplate, String prefix) {
        this(shiroRedisTemplate);
        this.prefix = prefix;
    }

    @Override
    public V get(K key) throws CacheException {

        if(logger.isDebugEnabled()){
            logger.debug("key：{}",key);
        }
        if(key==null){
            return null;
        }

        byte[] bkey = getByteKey(key);

        return redisTemplate.opsForValue().get(bkey);
    }

    @Override
    public V put(K key, V value) throws CacheException {

        if(logger.isDebugEnabled()){
            logger.debug("key: {},value: {}",key,value);
        }

        if(key==null || value==null){
            return null;
        }

        byte[] bkey = getByteKey(key);
        redisTemplate.opsForValue().set(bkey,value);
        return value;
    }

    @Override
    public V remove(K key) throws CacheException {

        if(key==null){
            return null;
        }

        byte[] bkey = getByteKey(key);
        ValueOperations<byte[],V> vo = redisTemplate.opsForValue();
        V value = vo.get(bkey);
        redisTemplate.delete(bkey);
        return value;
    }

    @Override
    public void clear() throws CacheException {
        redisTemplate.getConnectionFactory().getConnection().flushDb();
    }

    @Override
    public int size() {
        Long len = redisTemplate.getConnectionFactory().getConnection().dbSize();
        return len.intValue();
    }

    @Override
    public Set<K> keys() {

        byte[] bkey = (prefix+"*").getBytes();
        Set<byte[]> set = redisTemplate.keys(bkey);

        Set<K> result = Sets.newHashSet();

        if(CollectionUtils.isEmpty(set)){
            return Collections.emptySet();
        }

        for(byte[] key:set){
             result.add((K)key);
        }
        return result;
    }

    @Override
    public Collection<V> values() {
        Set<K> keys = keys();
        List<V> values = new ArrayList<>(keys.size());
        for (K k : keys) {
            byte[] bkey = getByteKey(k);
            values.add(redisTemplate.opsForValue().get(bkey));
        }
        return values;
    }

    private byte[] getByteKey(K key){

        if(key instanceof String){
            String prekey = prefix + key;
            return prekey.getBytes();
        }else {
            return SerializeUtils.serialize(key);
        }
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
