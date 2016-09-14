package com.hx.util;

import java.util.ResourceBundle;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis工具类,用于获取RedisPool. 操作redis
 */
public class JedisUtil {

	private static JedisPool pool;

	static {
		ResourceBundle bundle = ResourceBundle.getBundle("redis");
		if (bundle == null) {
			throw new IllegalArgumentException("[redis.properties] is not found!");
		}
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(Integer.valueOf(bundle.getString("redis.pool.maxActive")));
		config.setMaxIdle(Integer.valueOf(bundle.getString("redis.pool.maxIdle")));
		config.setMaxWaitMillis(Long.valueOf(bundle.getString("redis.pool.maxWait")));
		config.setTestOnBorrow(Boolean.valueOf(bundle.getString("redis.pool.testOnBorrow")));
		config.setTestOnReturn(Boolean.valueOf(bundle.getString("redis.pool.testOnReturn")));
		pool = new JedisPool(config, bundle.getString("redis.ip"), Integer.valueOf(bundle.getString("redis.port")));
	}

	public String getValue(String keys) {

		Jedis jedis = pool.getResource();

		// 取数据
		String value = jedis.get(keys);

		jedis.close();

		return value;
	}

	public void deleteValue(String keys) {

		Jedis jedis = pool.getResource();

		// 删数据
		jedis.del(keys);

		jedis.close();

	}

	public void setValue(String keys, String values) {

		Jedis jedis = pool.getResource();

		// 存数据
		jedis.set(keys, values);

		jedis.close();

	}

}
