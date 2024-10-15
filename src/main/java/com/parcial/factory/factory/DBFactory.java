package com.parcial.factory.factory;

import com.parcial.DAO.IDataAccess;

public abstract class DBFactory {

    public abstract <T> IDataAccess<T> createDB(Class<T> type);
}
