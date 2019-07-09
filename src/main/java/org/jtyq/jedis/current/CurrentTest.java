package org.jtyq.jedis.current;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class CurrentTest {

    public static void main(String[] args) throws FileNotFoundException {

        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\TYQ\\Desktop\\abc.txt");
        System.setOut(new PrintStream(fileOutputStream));
        int testThreadCount = 5000;

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(5000);
        config.setMaxIdle(5000);
        JedisPool jedisPool = new JedisPool(config,"192.168.0.108");
        Executor executor = Executors.newCachedThreadPool();
        while (true) {
            AtomicInteger errorCount = new AtomicInteger(0);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Semaphore semaphore = new Semaphore(testThreadCount);
            CountDownLatch countDownLatch = new CountDownLatch(testThreadCount);
            for (int i = 0; i < testThreadCount; i++) {
                executor.execute(new Task(semaphore,countDownLatch,errorCount,jedisPool));
            }
            try {
                countDownLatch.await();
                System.out.println("has "+ errorCount.get()+" is fail !");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("once test is over !");

        }
    }
}
