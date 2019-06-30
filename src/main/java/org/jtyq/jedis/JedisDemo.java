package org.jtyq.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Arrays;

/**
 * @author TYQ
 * @create 2018-07-22 21:36
 * @desc
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
           jedis.set("name","jtyq");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis!= null){
                jedis.close();
            }
        }
    }


    @Test
    public void test2(){

        byte[][] tab = new byte[10][10];
        for(int i=0;i<tab.length;i++){
            System.out.println(Arrays.toString(tab[i]));
        }



    }




}
