/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.liudh.shiro.session;

import com.dubbo.utils.SerializeUtil;
import com.liudh.shiro.redis.JedisClusterClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 名称：redis管理类
 * <p>
 * 创建者
 * 创建时间
 * 创建描述
 */
public class RedisManager {

    /**
     * 定义全局日志
     */
    private static final Logger logger = LogManager.getLogger();
    /**
     * redis
     */
    @Autowired
    private JedisClusterClient jedisClient;

    /**
     * 0就是不失效
     */
    private int expire = 0;

    /**
     * 保存和更新
     *
     * @param key    键
     * @param value  值
     * @param expire 失效时间
     */
    public void set(byte[] key, byte[] value, Integer expire) {
        jedisClient.set(key, value,expire);
    }

    /**
     * 存储
     *
     * @param byteKey   key
     * @param serialize value
     */
    public void set(byte[] byteKey, byte[] serialize) {
        jedisClient.set(byteKey, serialize);
    }

    /**
     * 删除
     *
     * @param byteKey 键
     */
    public void del(byte[] byteKey) {
        jedisClient.del(byteKey);
    }

    /**
     * 根据条件获取对象使用的
     *
     * @param pattern 条件
     * @return set
     */
    public Set<byte[]> keys(String pattern) {
        logger.debug("redisCluster开始获取keys");
        Set<byte[]> result = new HashSet<>();
        Set<String> keys = new HashSet<>();
        Map<String, JedisPool> clusterNodes = jedisClient.getClusterNodes();
        for (String k : clusterNodes.keySet()) {
            logger.debug("得到keys从: {}", k);
            JedisPool jp = clusterNodes.get(k);
            Jedis connection = jp.getResource();
            try {
                //获取所有的key 的byte[]
                Set<byte[]> keys1 = connection.keys(SerializeUtil.serialize(pattern));
                for (byte[] b : keys1) {
                    //把从主从的redisCluster查出的重复数据去重
                    keys.add(new String(b, "UTF-8"));
                }
                //根据不重复的key用byte[]格式获取出byte[]格式的二进制数据
                for (String s : keys) {
                    result.add(get(s.getBytes()));
                }
            } catch (Exception e) {
                logger.error("获取keys错误: {}", e);
            } finally {
                logger.debug("获取redisCluster连接做keys操作之后关闭连接！");
                connection.close();//用完一定要close这个链接！！！
            }
        }
        logger.debug("redisCluster中keys获取完毕！");
        return result;
    }

    /**
     * 根据key获取二进制的值
     *
     * @param key key
     * @return 二进制
     */
    public byte[] get(byte[] key) {
        return jedisClient.get(key);
    }

    /**
     * 根据key获取值
     *
     * @param key key
     * @return 值
     */
    public String get(String key) {
        return jedisClient.get(key);
    }

    /**
     * 获取缓存数
     *
     * @return
     */
    public long dbSize() {
        logger.debug("shrio获取缓存数量开始！");
        Set<byte[]> sessionKeys = keys("shiro_redis_session:*");
        int sessionSize = sessionKeys.size();
        Set<byte[]> cacheKeys = keys("shiro_redis_cache:*");
        int cacheSize = cacheKeys.size();
        Integer size = sessionSize + cacheSize;
        logger.debug("shrio获取缓存数量结束！");
        return Long.valueOf(size.toString());
    }

    /**
     * 清空权限和认证缓存
     */
    public void flushDB() {
        logger.debug("shrio清除缓存开始！");
        //shiro_redis_session:
        /*Set<byte[]> sessionKeys = keys("shiro_redis_session:*");
        for (byte[] session : sessionKeys) {
            del(session);
        }*/
        //shiro_redis_cache:
        Set<byte[]> cacheKeys = keys("shiro_redis_cache:*");
        for (byte[] cache : cacheKeys) {
            del(cache);
        }
        logger.debug("shrio清除缓存结束！");
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }
}
