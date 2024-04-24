package com.github.StilverGP.model.dao;

import java.io.Closeable;

public interface DAO<T, K> extends Closeable {
    T add(T entity);
    T update(T entity);
    T findById(K id);
    T delete(T entity);
}
