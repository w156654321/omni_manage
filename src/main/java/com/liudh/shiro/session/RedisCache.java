/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.liudh.shiro.session;

import com.dubbo.utils.SerializeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


/**
 * 名称：shrio的redis缓存类
 * <p>
 * 创建者
 * 创建时间
 * 创建描述
 *
 */
public class RedisCache<K, V> implements Cache<K, V> {

    /**
     * 定义全局日志
     */
    private static final Logger logger = LogManager.getLogger();

    /**
     * 包装的jedisCluster实例
     */
    @Autowired
    private RedisManager cache;

    /**
     * sessions的Redis key 前缀
     */
    private String keyPrefix = "shiro_redis_session:";

    /**
     * 通过一个JedisManager实例构造RedisCache
     *
     * @param cache redisManager
     */
    public RedisCache(RedisManager cache) {
        if (cache == null) {
            throw new IllegalArgumentException("Cache argument cannot be null.");
        }
        this.cache = cache;
    }

    /**
     * 根据规定构造一个缓存实例
     * Redis manager 和自定义的key前缀
     *
     * @param cache  缓存实例
     * @param prefix 前缀
     */
    public RedisCache(RedisManager cache, String prefix) {
        this(cache);
        // 设置前缀
        this.keyPrefix = prefix;
    }

    /**
     * 根据Key获取缓存中的值
     * @param key       key
     * @return
     * @throws CacheException
     */
    @Override
    public V get(K key) throws CacheException {
        logger.debug("根据key从Redis中获取对象 key [{}]", key);
        try {
            if (key == null) {
                return null;
            } else {
                byte[] rawValue = cache.get(getByteKey(key));
                @SuppressWarnings("unchecked")
                V value = (V) SerializeUtil.deserialize(rawValue);
                return value;
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    /**
     * 往缓存中放入key-value，返回缓存中之前的值
     * @param key           key
     * @param value         value
     * @return
     * @throws CacheException
     */
    @Override
    public V put(K key, V value) throws CacheException {
        logger.debug("根据key从存储 key [{}]", key);
        try {
            cache.set(getByteKey(key), SerializeUtil.serialize(value));
            return value;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    /**
     * 移除缓存中key对应的值，返回该值
     * @param key       key
     * @return
     * @throws CacheException
     */
    @Override
    public V remove(K key) throws CacheException {
        logger.debug("从redis中删除 key [" + key + "]");
        try {
            V previous = get(key);
            cache.del(getByteKey(key));
            return previous;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    /**
     * 清空整个缓存
     * @throws CacheException
     */
    @Override
    public void clear() throws CacheException {
        logger.debug("从redis中删除所有元素");
        try {
            cache.flushDB();
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    /**
     * 返回缓存大小
     * @return
     */
    @Override
    public int size() {
        try {
            Long longSize = new Long(cache.dbSize());
            return longSize.intValue();
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    /**
     * 获取缓存中所有的key
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public Set<K> keys() {
        try {
            Set<byte[]> keys = cache.keys(this.keyPrefix + "*");
            if (CollectionUtils.isEmpty(keys)) {
                return Collections.emptySet();
            } else {
                Set<K> newKeys = new HashSet<>();
                for (byte[] key : keys) {
                    newKeys.add((K) key);
                }
                return newKeys;
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    /**
     * 获取缓存中所有的value
     * @return
     */
    @Override
    public Collection<V> values() {
        try {
            Set<byte[]> keys = cache.keys(this.keyPrefix + "*");
            if (!CollectionUtils.isEmpty(keys)) {
                List<V> values = new ArrayList<>(keys.size());
                for (byte[] key : keys) {
                    @SuppressWarnings("unchecked")
                    V value = get((K) key);
                    if (value != null) {
                        values.add(value);
                    }
                }
                return Collections.unmodifiableList(values);
            } else {
                return Collections.emptyList();
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    /**
     * 返回Redis session keys前缀
     *
     * @return 前缀
     */
    public String getKeyPrefix() {
        return keyPrefix;
    }


    /**
     * 设置Redis sessions key前缀
     *
     * @param keyPrefix 前缀
     */
    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    /**
     * 获得byte[]型的key
     *
     * @param key key
     * @return 值
     */
    private byte[] getByteKey(K key) {
        if (key instanceof String) {
            String preKey = this.keyPrefix + key;
            return preKey.getBytes();
        } else {
            return SerializeUtil.serialize(key);
        }
    }
}