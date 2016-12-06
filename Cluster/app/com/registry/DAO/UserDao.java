package com.registry.DAO;

import com.google.inject.ImplementedBy;
import com.registry.entities.User;

import java.util.List;

/**
 * Created by prasad on 12/4/16.
 */
@ImplementedBy(UserDaoImpl.class)
public interface UserDao {

    User putUser(User user);

    User getUser(String userId);

    List<User> getUserList();
}
