package org.jtyq.jedis.spring.xml.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;

public class MyCacheManager implements CacheManager {

    @Override
    public Cache getCache(String s) {
        return null;
    }

    @Override
    public Collection<String> getCacheNames() {
        return null;
    }
}
