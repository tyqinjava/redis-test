package org.jtyq.jedis.mq;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class JedisImplMq {

    private BlockingQueue<Runnable> workQueue;

    private ThreadPoolExecutor executor;

    public static final int DEFAULT_THREAD_COUNT = 10;

    public static final int DEFAULT_WORK_QUEUE_COUNT = 100;

    public static final int DEFAULT_MAX_THREAD_COUNT = 100;

    public static final long DEFAULT_THEAD_KEEP_LIVE = 10;

    private int threadCount = DEFAULT_THREAD_COUNT;

    private int maxThreadCount =DEFAULT_MAX_THREAD_COUNT;

    private int workQueueCount = DEFAULT_WORK_QUEUE_COUNT;

    private long threadKeepLive = DEFAULT_THEAD_KEEP_LIVE;

    public JedisImplMq(){
        this.workQueue = new ArrayBlockingQueue<Runnable>(workQueueCount);
        this.executor = new ThreadPoolExecutor(
                threadCount, maxThreadCount, threadKeepLive, TimeUnit.MINUTES, workQueue,(r)->{
            Thread thread = new Thread(r);
            thread.setDaemon(false);
            return thread;
        },(r,executor)-> {});
    }

    public ThreadPoolExecutor getExecutor() {
        return executor;
    }

    public static void main(String[] args) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(30);
        config.setMaxIdle(10);
        JedisPool jedisPool = new JedisPool(config,"localhost",6379);
        JedisImplMq jedisImplMq = new JedisImplMq();
        String key = "list123" ;
        Producer producer = new Producer(jedisPool,key);
        jedisImplMq.getExecutor().execute(producer);
        Consumer consumer = new Consumer(key,jedisPool);
        jedisImplMq.getExecutor().execute(consumer);

    }


}
