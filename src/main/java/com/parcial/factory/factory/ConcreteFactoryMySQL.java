package com.parcial.factory.factory;

import com.parcial.DAO.IDataAccess;
import com.parcial.DAO.MySQLDAO.MySQLAbilityDB;
import com.parcial.DAO.MySQLDAO.MySQLCharacterDB;
import com.parcial.DAO.MySQLDAO.MySQLRaceDB;
import com.parcial.DAO.MySQLDAO.MySQLTechniqueDB;
import com.parcial.model.Ability;
import com.parcial.model.Character;
import com.parcial.model.Race;
import com.parcial.model.Technique;

public class ConcreteFactoryMySQL extends DBFactory {

    @SuppressWarnings("unchecked")
    @Override
    public <T> IDataAccess<T> createDB(Class<T> type) {
        if (type.equals(Ability.class)) {
            return (IDataAccess<T>) new MySQLAbilityDB();
        } else if (type.equals(Character.class)) {
            return (IDataAccess<T>) new MySQLCharacterDB();
        } else if (type.equals(Race.class)) {
            return (IDataAccess<T>) new MySQLRaceDB();
        } else if (type.equals(Technique.class)) {
            return (IDataAccess<T>) new MySQLTechniqueDB();
        }
        throw new IllegalArgumentException("Unsupported type: " + type);
    }
}
