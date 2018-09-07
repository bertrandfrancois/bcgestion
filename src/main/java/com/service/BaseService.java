package com.service;

import java.util.List;

public interface BaseService<T> {

    List<T> findAll();

//    void save(T param);

    T save(T param);

    T find(long id);

    void delete(long id);
}
