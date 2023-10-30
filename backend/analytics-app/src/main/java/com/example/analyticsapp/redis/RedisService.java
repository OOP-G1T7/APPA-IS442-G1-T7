package com.example.analyticsapp.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisService {
    JedisPoolConfig poolConfig = new JedisPoolConfig();
    JedisPool jedisPool = new JedisPool(poolConfig, "redis-11769.c292.ap-southeast-1-1.ec2.cloud.redislabs.com",
            11769);

    // username and password
    String username = "default";
    String password = "kkiwZNWzNfnH2OJH1xduyaGj1wbIt9K7";

    public String getCachedData(String key) {
        Jedis jedis = jedisPool.getResource();
        // Authenticate to the Redis Cloud instance using your username and password
        jedis.auth(username, password);
        return jedis.get(key);
    }

    public void cacheData(String key, String data) {
        Jedis jedis = jedisPool.getResource();
        // Authenticate to the Redis Cloud instance using your username and password
        jedis.auth(username, password);
        jedis.setex(key, 3600, data); // Cache it for future use
    }

}
