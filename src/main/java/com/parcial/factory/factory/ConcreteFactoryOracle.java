package com.parcial.factory.factory;

import com.parcial.DAO.IDataAccess;
import com.parcial.DAO.OracleDAO.OracleAbilityDB;
import com.parcial.DAO.OracleDAO.OracleCharacterDB;
import com.parcial.DAO.OracleDAO.OracleRaceDB;
import com.parcial.DAO.OracleDAO.OracleTechniqueDB;
import com.parcial.model.Ability;
import com.parcial.model.Character;
import com.parcial.model.Race;
import com.parcial.model.Technique;

public class ConcreteFactoryOracle extends DBFactory {

    @SuppressWarnings("unchecked")
    @Override
    public <T> IDataAccess<T> createDB(Class<T> type) {
        if (type.equals(Ability.class)) {
            return (IDataAccess<T>) new OracleAbilityDB();
        } else if (type.equals(Character.class)) {
            return (IDataAccess<T>) new OracleCharacterDB();
        } else if (type.equals(Race.class)) {
            return (IDataAccess<T>) new OracleRaceDB();
        } else if (type.equals(Technique.class)) {
            return (IDataAccess<T>) new OracleTechniqueDB();
        }
        throw new IllegalArgumentException("Unsupported type: " + type);
    }
}
