package com.parcial.config;

import com.parcial.DAO.IDataAccess;
import com.parcial.factory.factory.ConcreteFactoryMySQL;
import com.parcial.factory.factory.ConcreteFactoryOracle;
import com.parcial.factory.factory.DBFactory;

public class DatabaseConfig {

    private static String dataBaseType = "";

    public static void configureAdapterDB(String dbType) {
        dataBaseType = dbType;
    }

    public static <T> IDataAccess<T> getDAO(Class<T> entityType) {
        DBFactory factory;

        switch (dataBaseType.toLowerCase()) {
            case "mysql" ->
                factory = new ConcreteFactoryMySQL();
            case "oracle" ->
                factory = new ConcreteFactoryOracle();
            default ->
                throw new IllegalStateException("Unsupported database type: " + dataBaseType);
        }

        return factory.createDB(entityType);
    }
}
