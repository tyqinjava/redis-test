package org.jtyq.jedis.spring.xml.dao;


import org.jtyq.jedis.spring.xml.db.DB;
import org.jtyq.jedis.spring.xml.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserDao {


    @Cacheable(value = "findUserById")
    public User findUserBydId(String userId) {
        return DB.findUser(userId);
    }

    @Cacheable("findAllUsers")
    public Map<String,User> findAllUsers(){
        return DB.findAllUser();
    }


}
