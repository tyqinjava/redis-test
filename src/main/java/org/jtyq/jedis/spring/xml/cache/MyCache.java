package org.jtyq.jedis.spring.xml.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;

public class MyCache extends ConcurrentMapCache implements Cache {

    public MyCache(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return this.getNativeCache().toString();
    }
}
