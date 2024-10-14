package com.parcial.DAO;

import java.util.List;

public interface IDataAccess<T> {

    List<T> getAll();

    T getById(int id);

    void save(T item);

    void update(T item);  // Nuevo método para actualizar el objeto

    void delete(int id);
}
