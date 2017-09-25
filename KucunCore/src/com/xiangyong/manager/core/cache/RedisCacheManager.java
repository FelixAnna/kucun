package com.xiangyong.manager.core.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;
import org.springframework.cache.transaction.TransactionAwareCacheDecorator;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.cache.DefaultRedisCachePrefix;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCachePrefix;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@SuppressWarnings("unchecked")
public class RedisCacheManager extends AbstractTransactionSupportingCacheManager {

    private final Logger logger = LoggerFactory.getLogger(RedisCacheManager.class);
    private final RedisOperations redisOperations;
    private boolean usePrefix = false;
    private RedisCachePrefix cachePrefix = new DefaultRedisCachePrefix();
    private boolean loadRemoteCachesOnStartup = false;
    private boolean dynamic = true;

    private long defaultExpiration = 0L;
    private Map<String, Long> expires = null;
    private Set<String> configuredCacheNames;

    public RedisCacheManager(RedisOperations redisOperations) {
        this(redisOperations, Collections.emptyList());
        this.expires = new ConcurrentHashMap<>();
    }

    public RedisCacheManager(RedisOperations redisOperations, Collection<String> cacheNames) {
        this.redisOperations = redisOperations;
        setCacheNames(cacheNames);
        this.expires = new ConcurrentHashMap<>();
    }

    public Cache getCache(String name) {
        Cache cache = super.getCache(name);
        if ((cache == null) && (this.dynamic)) {
            return createAndAddCache(name);
        }
        return cache;
    }

    public void setCacheNames(Collection<String> cacheNames) {
        Set<String> newCacheNames = CollectionUtils.isEmpty(cacheNames) ? Collections.emptySet() : new HashSet<>(cacheNames);

        this.configuredCacheNames = newCacheNames;
        this.dynamic = newCacheNames.isEmpty();
    }

    public void setUsePrefix(boolean usePrefix) {
        this.usePrefix = usePrefix;
    }

    public void setCachePrefix(RedisCachePrefix cachePrefix) {
        this.cachePrefix = cachePrefix;
    }

    public void setDefaultExpiration(long defaultExpireTime) {
        this.defaultExpiration = defaultExpireTime;
    }

    public void setExpires(Map<String, Long> expires) {
        this.expires = (expires != null ? new ConcurrentHashMap<>(expires) : null);
    }

    public void setLoadRemoteCachesOnStartup(boolean loadRemoteCachesOnStartup) {
        this.loadRemoteCachesOnStartup = loadRemoteCachesOnStartup;
    }

    protected Collection<? extends Cache> loadCaches() {
        Assert.notNull(this.redisOperations, "A redis template is required in order to interact with data store");
        return addConfiguredCachesIfNecessary(this.loadRemoteCachesOnStartup ? loadAndInitRemoteCaches() : Collections.emptyList());
    }

    protected Collection<? extends Cache> addConfiguredCachesIfNecessary(Collection<? extends Cache> caches) {
        Assert.notNull(caches, "Caches must not be null!");

        Collection<Cache> result = new ArrayList<>(caches);

        for (String cacheName : getCacheNames()) {
            boolean configuredCacheAlreadyPresent = false;

            for (Cache cache : caches) {
                if (cache.getName().equals(cacheName)) {
                    configuredCacheAlreadyPresent = true;
                    break;
                }
            }

            if (!configuredCacheAlreadyPresent) {
                result.add(getCache(cacheName));
            }
        }

        return result;
    }

    protected Cache createAndAddCache(String cacheName) {
        addCache(createCache(cacheName));
        return super.getCache(cacheName);
    }

    protected RedisCache createCache(String cacheName) {
        long expiration = computeExpiration(cacheName);
        return new RedisCache(cacheName, this.usePrefix ? this.cachePrefix.prefix(cacheName) : null, this.redisOperations, expiration);
    }

    protected long computeExpiration(String name) {
        if (this.expires.containsKey(name)) {
            return ((Long) this.expires.get(name)).longValue();
        }
        if (name.contains(":")) {
            String[] nameExpire = name.split(":");
            this.expires.put(name, Long.valueOf(Long.parseLong(nameExpire[(nameExpire.length - 1)])));
            return ((Long) this.expires.get(name)).longValue();
        }
        return this.defaultExpiration;
    }

    protected List<Cache> loadAndInitRemoteCaches() {
        List caches = new ArrayList();
        try {
            Set<String> cacheNames = loadRemoteCacheKeys();
            if (!CollectionUtils.isEmpty(cacheNames)) {
                for (String cacheName : cacheNames)
                    if (null == super.getCache(cacheName))
                        caches.add(createCache(cacheName));
            }
        } catch (Exception e) {
            if (this.logger.isWarnEnabled()) {
                this.logger.warn("Failed to initialize cache with remote cache keys.", e);
            }
        }

        return caches;
    }

    protected Set<String> loadRemoteCacheKeys() {
        return (Set) this.redisOperations.execute(new RedisCallback() {
            public Set<String> doInRedis(RedisConnection connection)
                    throws DataAccessException {
                Set<byte[]> keys = connection.keys(RedisCacheManager.this.redisOperations.getKeySerializer().serialize("*~keys"));
                Set<String> cacheKeys = new LinkedHashSet<>();

                if (!CollectionUtils.isEmpty(keys)) {
                    for (byte[] key : keys) {
                        cacheKeys.add(RedisCacheManager.this.redisOperations.getKeySerializer().deserialize(key).toString().replace("~keys", ""));
                    }
                }

                return cacheKeys;
            }
        });
    }

    protected RedisOperations getRedisOperations() {
        return this.redisOperations;
    }

    protected RedisCachePrefix getCachePrefix() {
        return this.cachePrefix;
    }

    protected boolean isUsePrefix() {
        return this.usePrefix;
    }

    public void afterPropertiesSet() {
        if (!CollectionUtils.isEmpty(this.configuredCacheNames)) {
            for (String cacheName : this.configuredCacheNames) {
                createAndAddCache(cacheName);
            }

            this.configuredCacheNames.clear();
        }

        super.afterPropertiesSet();
    }

    protected Cache decorateCache(Cache cache) {
        if (isCacheAlreadyDecorated(cache)) {
            return cache;
        }

        return super.decorateCache(cache);
    }

    protected boolean isCacheAlreadyDecorated(Cache cache) {
        return (isTransactionAware()) && ((cache instanceof TransactionAwareCacheDecorator));
    }
}