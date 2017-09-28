/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.liudh.shiro.realm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 名称：redis缓存管理
 * <p>
 * 创建者
 * 创建时间
 * 创建描述
 *
 * 审核者：
 * 审核时间：
 * 审核描述：
 *
 * 修改者：
 * 修改时间：
 * 修改内容：
 */
public class RedisCacheManager implements CacheManager {

    /**
     * 定义全局日志
     */
    private static final Logger logger = LogManager.getLogger();

    private RedisManager redisManager;

    // fast lookup by name map
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<>();

    /**
     * redis的key前缀
     */
    private String keyPrefix = "shiro_redis_cache:";

    /**
     * 构造器
     * @param redisManager  redisManager
     */
    public RedisCacheManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    /**
     * Returns the Redis session keys
     * prefix.
     * @return The prefix
     */
    public String getKeyPrefix() {
        return keyPrefix;
    }

    /**
     * Sets the Redis sessions key
     * prefix.
     * @param keyPrefix The prefix
     */
    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    /**
     * 根据缓存名字获取一个Cache
     * @param name      名字
     * @param <K>       key
     * @param <V>       value
     * @return
     * @throws CacheException
     */
    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        logger.debug("获取名称为: {}的RedisCache实例" + name);
        Cache c = caches.get(name);
        if (c == null) {
            // 创建缓存实例
            c = new RedisCache<K, V>(redisManager, keyPrefix);
            // 添加缓存集合中
            caches.put(name, c);
        }
        return c;
    }

    public RedisManager getRedisManager() {
        return redisManager;
    }

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }
}