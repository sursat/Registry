package com.registry.services;

import com.google.inject.Inject;
import com.registry.DAO.UserDao;
import com.registry.common.utils.InputParametersChecker;
import com.registry.entities.User;

import java.util.List;

/**
 * Created by prasad on 12/4/16.
 */
public class UserService {

    @Inject
    private UserDao userDao;

    public User putUser(User user){
        InputParametersChecker.ifNullThrowNullPointerException(user);
        return userDao.putUser(user);
    }

    public User getUser(String userId){
        InputParametersChecker.ifNullThrowNullPointerException(userId);
        return userDao.getUser(userId);
    }

    public List<User> getUserList(){
        return userDao.getUserList();
    }
}
