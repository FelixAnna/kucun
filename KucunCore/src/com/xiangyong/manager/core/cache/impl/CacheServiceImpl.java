package com.xiangyong.manager.core.cache.impl;

import com.alibaba.fastjson.JSON;
import com.xiangyong.manager.common.util.JsonUtils;
import com.xiangyong.manager.core.cache.CacheService;
import com.xiangyong.manager.core.cache.util.SerializeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class CacheServiceImpl implements CacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheServiceImpl.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static String redisEncoding = "utf-8";

    public void delete(String key) {
        this.redisTemplate.delete(key);
    }

    public void delete(List<String> keys) {
        this.redisTemplate.delete(keys);
    }

    public void delPattern(String pattern) {
        Set<String> keys = this.redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            this.redisTemplate.delete(keys);
        }
    }

    public void set(String key, String value) {
        this.redisTemplate.execute((RedisConnection connection) -> {
            connection.set(key.getBytes(), value.getBytes());
            return 1;
        });
    }

    public void set(String key, String value, long timeout) {
        this.redisTemplate.execute((RedisConnection connection) -> {
            connection.setEx(key.getBytes(), timeout, value.getBytes());
            return 1;
        });
    }

    public String get(String key) {
        return this.redisTemplate.execute((RedisConnection connection) -> {
            String result = null;
            byte[] resultByte = connection.get(key.getBytes());
            if (resultByte != null) {
                try {
                    result = new String(resultByte, CacheServiceImpl.redisEncoding);
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("根据key获取数据时异常", e);
                }
            }
            return result;
        });
    }

    public List<String> multiGet(Set<String> keys) {
        return this.redisTemplate.opsForValue().multiGet(keys);
    }

    public Set<String> getKeys(String regular) {
        return this.redisTemplate.keys(regular);
    }

    public boolean exists(String key) {
        return this.redisTemplate.execute((RedisConnection connection) -> {
            return connection.exists(key.getBytes());
        });
    }

    public String flushDB() {
        return null;
    }

    public long dbSize() {
        return this.redisTemplate.execute((RedisConnection connection) -> {
            return connection.dbSize();
        });
    }

    public String ping() {
        return this.redisTemplate.execute((RedisConnection connection) -> {
            return connection.ping();
        });
    }

    public void setSet(String key, String[] values) {
        this.redisTemplate.execute((RedisConnection connection) -> {
            byte[][] byteValue = new byte[values.length][];
            for (int i = 0; i < values.length; i++) {
                byteValue[i] = values[i].getBytes();
            }
            connection.sAdd(key.getBytes(), byteValue);
            return 1;
        });
    }

    public void setSet(String key, long timeout, String[] values) {
        setSet(key, values);
        expire(key, timeout);
    }

    public List<String> getSetMember(String key) {
        return this.redisTemplate.execute((RedisConnection connection) -> {
            Set<byte[]> sets = connection.sMembers(key.getBytes());
            List<String> returnList = new ArrayList<>();
            if ((sets != null) && (sets.size() > 0)) {
                for (byte[] byteOne : sets) {
                    try {
                        returnList.add(new String(byteOne, CacheServiceImpl.redisEncoding));
                    } catch (UnsupportedEncodingException e) {
                        LOGGER.error("redis key={}的数据byte转化为string时异常", key, e);
                    }
                }
            }
            return returnList;
        });
    }

    public long removeSetMemeber(String key, String[] values) {
        return this.redisTemplate.execute((RedisConnection connection) -> {
            byte[][] byteValue = new byte[values.length][];
            for (int i = 0; i < values.length; i++) {
                byteValue[i] = values[i].getBytes();
            }
            return connection.sRem(key.getBytes(), byteValue);
        });
    }

    public void setHash(String key, String hashKey, String hashValue) {
        this.redisTemplate.execute((RedisConnection connection) -> {
            connection.hSetNX(key.getBytes(), hashKey.getBytes(), hashValue.getBytes());
            return 1;
        });
    }

    public Map<String, String> getHash(String key) {
        return this.redisTemplate.execute((RedisConnection connection) -> {
            Map<byte[], byte[]> mapByte = connection.hGetAll(key.getBytes());
            Map<String, String> returnMap = new HashMap<>();
            if ((mapByte != null) && (mapByte.size() > 0)) {
                try {
                    for (Map.Entry entry : mapByte.entrySet())
                        returnMap.put(new String((byte[]) entry.getKey(), CacheServiceImpl.redisEncoding), new String((byte[]) entry.getValue(), CacheServiceImpl.redisEncoding));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("获取redis中的hash值时失败", e);
                }
            }
            return returnMap;
        });
    }

    public void setHash(String key, String hashKey, String hashValue, long timeout) {
        setHash(key, hashKey, hashValue);
        expire(key, timeout);
    }

    public void putHash(String key, String hashKey, String value, long timeout) {
        putHash(key, hashKey, value);
        expire(key, timeout);
    }

    public void delHash(String key, String hashKey) {
//        this.redisTemplate.opsForHash().delete(key,new Object[]{hashKey});
        this.redisTemplate.opsForHash().delete(key, hashKey);
    }

    public void putHash(String key, String hashKey, String value) {
        this.redisTemplate.execute((RedisConnection connection) -> {
            connection.hSet(key.getBytes(), hashKey.getBytes(), value.getBytes());
            return 1;
        });
    }

    public void setObject(String key, Object value) {
        this.redisTemplate.execute((RedisConnection connection) -> {
            connection.set(key.getBytes(), SerializeUtils.serialize(value));
            return 1;
        });
    }

    public void setObjectWithJsonFormat(String key, Object value) {
        this.redisTemplate.execute((RedisConnection connection) -> {
            String json = value == null ? "{}" : JsonUtils.toJSONString(value);
            connection.set(key.getBytes(), json.getBytes());
            return 1;
        });
    }

    public void setObjectWithJsonFormat(String key, Object value, long timeout){
        String json = value == null ? "{}" : JsonUtils.toJSONString(value);
        this.redisTemplate.boundValueOps(key).set(json, timeout, TimeUnit.SECONDS);
    }

    @SuppressWarnings("unchecked")
    public <T> T getObject(String key) {
        return (T) this.redisTemplate.execute((RedisConnection connection) -> {
            return SerializeUtils.deserialize(connection.get(key.getBytes()));
        });
    }

    public <T> T getObjectFromJsonFormat(String key,Class<T> clazz) {
        return this.redisTemplate.execute((RedisConnection connection) -> {
            byte[] arr = connection.get(key.getBytes());
            if (arr == null) {
                return null;
            } else {
                return JSON.parseObject(arr, clazz);
            }
        });
    }

    public void setObject(String key, Object value, long timeout) {
        this.redisTemplate.execute((RedisConnection connection) -> {
            connection.setEx(key.getBytes(), timeout, SerializeUtils.serialize(value));
            return 1;
        });
    }

    public Boolean expire(String key, long timeout) {
        return this.redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    public Long incr(String key, long incrValue) {
        return this.redisTemplate.execute((RedisConnection connection) -> {
            return connection.incrBy(key.getBytes(), incrValue);
        });
    }

    public Long incr(String key, long incrValue, long ttl) {
        long result = this.redisTemplate.execute((RedisConnection connection) -> {
            return connection.incrBy(key.getBytes(), incrValue);
        });
        if (result == incrValue) {
            expire(key, ttl);
        }
        return result;
    }

    public Long incr(String key, long defaultValue, long incrValue, long timeout) {
        Assert.notNull(key, "key不能为空");
        boolean exists = exists(key);
        if (!exists) {
            synchronized (this) {
                if (!exists(key)) {
                    set(key, defaultValue + "");
                    expire(key, timeout);
                    return Long.parseLong(get(key));
                }
            }
        }
        return this.redisTemplate.execute((RedisConnection connection) -> {
            return connection.incrBy(key.getBytes(), incrValue);
        });
    }
}