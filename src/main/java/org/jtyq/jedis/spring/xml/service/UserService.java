package org.jtyq.jedis.spring.xml.service;

import org.jtyq.jedis.spring.xml.dao.UserDao;
import org.jtyq.jedis.spring.xml.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User findUserById(String userId){
        return  userDao.findUserBydId(userId);
    }

    public Map<String,User> findAllUsers(){
        return userDao.findAllUsers();
    }
}
