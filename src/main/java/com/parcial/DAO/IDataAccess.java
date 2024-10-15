package com.parcial.DAO;

import java.util.List;

public interface IDataAccess<T> {

    List<T> getAll();

    T getById(int id);

    T save(T item);

    void update(T item);

    void delete(int id);
}
