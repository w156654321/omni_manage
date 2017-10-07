package com.liudh.shiro.redis.impl;

import com.liudh.shiro.redis.JedisClusterClient;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * redis集群
 * @author liudh  20160730
 *
 */
public class JedisClientCluster implements JedisClusterClient {

	@Autowired
	private JedisCluster jedisCluster;
	
	@Override
	public String get(String key) {
		return jedisCluster.get(key);
	}

	@Override
	public byte[] get(byte[] key) {
		return jedisCluster.get(key);
	}

	@Override
	public String set(String key, String value) {
		return jedisCluster.set(key, value);
	}

	@Override
	public String set(byte[] key, byte[] value) {
		return jedisCluster.set(key,value);
	}

	@Override
	public String set(byte[] key, byte[] value, Integer expire) {
		return jedisCluster.setex(key,expire,value);
	}

	@Override
	public String hget(String hkey, String key) {
		return jedisCluster.hget(hkey, key);
	}

	@Override
	public byte[] hget(byte[] hkey, byte[] key) {
		return jedisCluster.hget(hkey,key);
	}

	@Override
	public long hset(String hkey, String key, String value) {
		return jedisCluster.hset(hkey, key, value);
	}

	@Override
	public long hset(byte[] hkey, byte[] key, byte[] value) {
		return jedisCluster.hset(hkey,key,value);
	}

	@Override
	public long incr(String key) {
		return jedisCluster.incr(key);
	}

	@Override
	public long expire(String key, int seconds) {
		return jedisCluster.expire(key, seconds);
	}

	@Override
	public long ttl(String key) {
		return jedisCluster.ttl(key);
	}

	@Override
	public long del(String key) {
		return jedisCluster.del(key);
	}

	@Override
	public long del(byte[] key) {
		return jedisCluster.del(key);
	}

	@Override
	public long hdel(String hkey,String key) {
		return jedisCluster.hdel(hkey,key);
	}

	@Override
	public long hdel(byte[] hkey, byte[] key) {
		return jedisCluster.hdel(hkey,key);
	}

	@Override
	public Map<String, JedisPool> getClusterNodes(){
		return jedisCluster.getClusterNodes();
	}
}
