package org.jtyq.jedis.current;

import org.jtyq.jedis.util.PerformanceTestUitl;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Task implements Runnable {

    private Semaphore semaphore;
    private CountDownLatch countDownLatch;
    private AtomicInteger errorCount;
    private JedisPool pool;

    public Task(Semaphore semaphore, CountDownLatch countDownLatch, AtomicInteger errorCount, JedisPool pool) {
        this.semaphore = semaphore;
        this.countDownLatch = countDownLatch;
        this.errorCount = errorCount;
        this.pool = pool;
    }


    @Override
    public void run() {
        try {
            semaphore.acquire();
            System.out.println("all thread is ready ! ");
            doRun();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            semaphore.release();
            countDownLatch.countDown();
        }
    }

    private void doRun(){
        PerformanceTestUitl.test(()->{
            try {
                Jedis  jedis = pool.getResource();
                jedis.incr("count");
                jedis.close();
            }catch (Exception e){
                errorCount.getAndIncrement();
            }

        });
        System.out.println("jedis set value success !");
    }
}
