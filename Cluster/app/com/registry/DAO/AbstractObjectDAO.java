package com.registry.DAO;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Created by prasad on 11/8/16.
 */
public abstract class AbstractObjectDAO<T> extends BasicDAO<T, String> implements AbstractDao<T> {

    public AbstractObjectDAO(Class<T> entityClass, Datastore ds) {
        super(entityClass, ds);
    }

    public T get(String id) {

        if (id == null || id.isEmpty()) {
            return null;
        }

        T savedObj = get(id);

        if (savedObj == null) {
            return null;
        }
        return savedObj;
    }

    public void put(T obj) {
        if (obj == null) {
            return;
        }
        getDatastore().save(obj);
    }

    public T delete(String Id) {

        if (Id == null || Id.isEmpty()) {
            return null;
        }

        T savedObj = get(Id);

        if (savedObj == null) {
            return null;
        }
        getDatastore().delete(Id);
        return savedObj;
    }
}
