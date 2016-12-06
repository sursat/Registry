package com.registry.DAO;

import com.google.inject.Inject;
import com.registry.common.utils.InputParametersChecker;
import com.registry.entities.User;
import org.mongodb.morphia.Datastore;

import java.util.List;

/**
 * Created by prasad on 12/4/16.
 */
public class UserDaoImpl extends AbstractObjectDAO<User> implements UserDao{

    @Inject
    public UserDaoImpl(Datastore datastore){super(User.class,datastore);}

    @Override
    public User putUser(User user) {
        put(user);
        return user;
    }

    @Override
    public User getUser(String userId) {
        InputParametersChecker.ifNullThrowNullPointerException(userId);
        return get(userId);
    }

    @Override
    public List<User> getUserList() {
        return createQuery().asList();
    }
}
