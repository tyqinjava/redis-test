package org.jtyq.jedis.mq;

import org.jtyq.jedis.util.PerformanceTestUitl;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

public class Producer implements Runnable {

    private String keyName;
    private JedisPool jedisPool;
    private volatile  boolean running = true;

    public Producer(JedisPool jedisPool, String keyName){
        this.jedisPool  = jedisPool;
        this.keyName = keyName;
    }

    public void run() {
        while (running) {
            Jedis jedis =jedisPool.getResource();
            if(jedis!= null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                UUID uuid = UUID.randomUUID();
                System.out.println("生產者開始生產消息：Message内容："+ uuid.toString());
                PerformanceTestUitl.test(()->{
                    jedis.lpush(keyName, uuid.toString());
                });
                jedis.close();
            }
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }


}
