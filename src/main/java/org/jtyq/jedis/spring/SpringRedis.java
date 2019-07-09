package org.jtyq.jedis.spring;

import org.jtyq.jedis.spring.config.AppConfig_JedisPool;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.nio.charset.Charset;

public class SpringRedis {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig_JedisPool.class);
        applicationContext.start();
        System.out.println(Charset.defaultCharset());
        System.out.println(System.getProperty("file.encoding"));
    }
}
