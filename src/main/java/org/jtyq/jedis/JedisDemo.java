package org.jtyq.jedis;

import org.jtyq.jedis.util.PerformanceTestUitl;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author TYQ
 * @create 2018-07-22 21:36
 * @desc
 * Jedis和JedisPool优缺点
 * jedis优点：简单方便，适用于少量长期连接的场景
 * jedispool优点：预先生成，降低开销使用连接池的形式保护
 * 和控制资源的使用
 * jedis缺点： 存在每次新建/关闭tcp开销，资源无法控制，存在连接泄漏的可能
 * jedispool缺点： 使用相对麻烦，在资源管理上需要很多参数保证
 * 一旦规划不合理也会出现问题
 *
 *
 *
 *
 **/
public class JedisDemo {

    private  static final String url = "localhost";
    private  static final  int port =6379;

    @Test
    public void test0(){
        Jedis jedis = new Jedis(url,port);
        jedis.set("name","imooc");
        jedis.close();
    }

    @Test
    public void test1(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(30);
        config.setMaxIdle(10);

        JedisPool jedisPool = new JedisPool(config,url,port);
        Jedis jedis = null;
        try{
           jedis = jedisPool.getResource();
           //string 类型操作
           jedis.set("name","jtyq");
           //设置过期时间
           jedis.expire("name",10);
           //如果不存在key，则设置
           jedis.setnx("key","value");
           //设置key value 并且设置过期时间
           jedis.setex("hello",10,"world");
           //自增一
           jedis.incr("count");
           //自增10
           jedis.incrBy("count", 10);


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis!= null){
                jedis.close();
            }
        }
    }

    @Test
    public void highCurrency(){
        int threadCount = 10000;
        CountDownLatch latch = new CountDownLatch(threadCount);
        Semaphore semaphore = new Semaphore(threadCount);
        String key = "count";
        AtomicInteger failCount = new AtomicInteger();
        for (int i = 0; i < threadCount; i++) {
            new Thread(()->{
                try{
                    semaphore.acquire();
                    PerformanceTestUitl.test(()->{
                        Jedis jedis = new Jedis("127.0.0.1");
                        long result =jedis.incr(key);
                        jedis.close();
                        if(result == 0) {
                            System.out.println("jedis连接失败");
                            failCount.getAndIncrement();
                        }
                    });
                }catch (Exception e){
                    failCount.getAndIncrement();
                }finally {
                    semaphore.release();
                    latch.countDown();
                }
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("执行完毕");
        System.out.println("jedis连接失败的有：" + failCount.get());

    }

}



//
