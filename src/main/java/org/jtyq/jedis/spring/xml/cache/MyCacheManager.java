package org.jtyq.jedis.spring.xml.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.AbstractCacheManager;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class MyCacheManager extends AbstractCacheManager implements CacheManager {

    @Override
    protected Collection<? extends Cache> loadCaches() {
       return Collections.emptySet();
    }

    @Override
    protected Cache getMissingCache(String name) {
        return createCache(name);
    }

    private Cache createCache(String name) {
        System.out.println("从缓存中读取数据... "+ name);
        MyCache myCache= new MyCache(name);
        return myCache;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CacheManager : { ");
        Collection cacheNames = getCacheNames();
        Iterator iterator = cacheNames.iterator();
        while (iterator.hasNext()) {
            String name = (String) iterator.next();
            sb.append(name).append(":");
            sb.append(getCache(name)).append(",");
        }
        int len =sb.length();
        sb.delete(len-1,len);
        sb.append(" }");
        return sb.toString();
    }
}
