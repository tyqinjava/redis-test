package org.jtyq.jedis.spring;

import org.jtyq.jedis.spring.xml.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

public class XmlSpringRedis extends Thread {

    private ApplicationContext context;

    public XmlSpringRedis(String xmlPath){
        init(xmlPath);
    }

    private void  init(String xmlPath) {
        this.context = new ClassPathXmlApplicationContext(xmlPath);
        start();
    }
    public ApplicationContext getContext() {
        return context;
    }
    public <T> T getBeanByType(Class<T> type){
        return context.getBean(type);
    }

    public Object getBeanById(String id) {
        return context.getBean(id);
    }

    @Override
    public void run() {
            while (true) {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

    }

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        XmlSpringRedis xmlSpringRedis = new XmlSpringRedis("applicationContext.xml");
        UserService userService = xmlSpringRedis.getBeanByType(UserService.class);
        System.out.println(userService.findUserById("1"));


    }
}
