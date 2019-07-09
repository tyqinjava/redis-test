package org.jtyq.jedis.spring.xml.db;

import org.jtyq.jedis.spring.xml.model.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DB {

    public static final Map<String, User> dbs;

    static {
        Map<String, User> t = new HashMap<>();
        User user = new User();
        user.setId("1");
        user.setUsername("abc");
        user.setPassword("abc123");
        User user1 = new User();
        user1.setId("2");
        user1.setUsername("def");
        user1.setPassword("abc123");
        User user2 = new User();
        user2.setId("3");
        user2.setUsername("cfg");
        user2.setPassword("abc123");

        User user3 = new User();
        user3.setId("4");
        user3.setUsername("peg");
        user3.setPassword("abc123");

        User user4 = new User();
        user4.setId("5");
        user4.setUsername("cpaa");
        user4.setPassword("abc123");

        t.put(user.getId(),user);
        t.put(user1.getId(),user1);
        t.put(user2.getId(),user2);
        t.put(user3.getId(),user3);
        t.put(user4.getId(),user4);
        dbs = Collections.unmodifiableMap(t);

    }

    public static User findUser(String id) {
        return dbs.get(id);
    }
}
