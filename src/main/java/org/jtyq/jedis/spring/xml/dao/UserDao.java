package org.jtyq.jedis.spring.xml.dao;


import org.jtyq.jedis.spring.xml.db.DB;
import org.jtyq.jedis.spring.xml.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {


    @Cacheable("findUserBydId")
    public User findUserBydId(String userId) {
        return DB.findUser(userId);
    }
}
