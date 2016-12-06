package com.registry.DAO;

import org.mongodb.morphia.dao.DAO;

/**
 * Created by prasad on 11/8/16.
 */
public interface AbstractDao<T> extends DAO<T, String> {
    T get(String id);

    void put(T obj);

    T delete(String Id);
}
