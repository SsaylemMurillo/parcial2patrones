package com.parcial.DAO.OracleDAO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.parcial.DAO.IDataAccess;
import com.parcial.model.Ability;
import com.parcial.utils.AbilityJSONUtil;

public class OracleAbilityDB implements IDataAccess<Ability> {

    private final Map<Integer, Ability> abilities;
    private final AbilityJSONUtil jsonUtil = new AbilityJSONUtil();

    public OracleAbilityDB() {
        // Cargar habilidades desde el archivo JSON al Map
        abilities = jsonUtil.readFromFile().stream()
                .collect(Collectors.toMap(Ability::getId, ability -> ability));
    }

    @Override
    public List<Ability> getAll() {
        return abilities.values().stream().collect(Collectors.toList());
    }

    @Override
    public Ability getById(int id) {
        return abilities.get(id);
    }

    @Override
    public Ability save(Ability ability) {
        abilities.put(ability.getId(), ability);
        jsonUtil.writeToFile(getAll());
        return ability;
    }

    @Override
    public void update(Ability ability) {
        if (abilities.containsKey(ability.getId())) {
            abilities.put(ability.getId(), ability);
            jsonUtil.writeToFile(getAll());
        } else {
            throw new IllegalArgumentException("Ability ID " + ability.getId() + " no existe para actualizar.");
        }
    }

    @Override
    public void delete(int id) {
        abilities.remove(id);
        jsonUtil.writeToFile(getAll());
    }
}
