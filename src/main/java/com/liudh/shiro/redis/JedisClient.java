package com.liudh.shiro.redis;

public interface JedisClient {


	public String get(String key);

	public byte[] get(byte[] key);
	
	public String set(String key, String value);

	public String set(byte[] key, byte[] value);

	public String set(byte[] key, byte[] value, Integer expire);

	public String hget(String hkey, String key);

	public byte[] hget(byte[] hkey, byte[] key);

	public long hset(String hkey, String key, String value);

	public long hset(byte[] hkey, byte[] key, byte[] value);
	/**
	 * 自增
	 * @param key
	 * @return
	 */
	public long incr(String key);
	/**
	 * 设置有效时间
	 * @param key
	 * @param seconds
	 * @return
	 */
	public long expire(String key, int seconds);
	/**
	 * 查看是否存在
	 * @param key
	 * @return
	 */
	public long ttl(String key);

	public long del(String key);

	public long del(byte[] key);

	public long hdel(String hkey, String key);

	public long hdel(byte[] hkey, byte[] key);

}
