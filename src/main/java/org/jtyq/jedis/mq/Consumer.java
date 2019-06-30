package org.jtyq.jedis.mq;

import org.jtyq.jedis.util.PerformanceTestUitl;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

public class Consumer implements Runnable{
    private String keyName;
    private JedisPool jedisPool;
    private volatile  boolean running = true;

    public Consumer(String keyName, JedisPool jedisPool) {
        this.keyName = keyName;
        this.jedisPool = jedisPool;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Jedis jedis = jedisPool.getResource();
            if (jedis != null && jedis.isConnected()) {
                List<String> result = new ArrayList<>();
                PerformanceTestUitl.test(()->{
                    List<String> t =jedis.blpop(0,keyName);
                    result.addAll(t);
                });
                System.out.print("消費者開始消費消息：");
                System.out.println(result);
                jedis.close();
            }
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
