package com.parcial.DAO;

import java.util.ArrayList;
import java.util.List;

import com.parcial.model.Ability;
import com.parcial.utils.JSONUtil;

public class MySQLAbilityDB implements IDataAccess<Ability> {

    private List<Ability> abilities = new ArrayList<>();

    public MySQLAbilityDB() {
        this.abilities = JSONUtil.readAbilitiesFromFile();
    }

    @Override
    public List<Ability> getAll() {
        return abilities;
    }

    @Override
    public Ability getById(int id) {
        return abilities.stream().filter(a -> a.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void save(Ability ability) {
        abilities.add(ability);
        JSONUtil.writeAbilitiesToFile(abilities);
    }

    public Ability saveAbility(Ability ability) {
        return JSONUtil.addAbility(ability);
    }

    @Override
    public void delete(int id) {
        abilities.removeIf(a -> a.getId() == id);
        JSONUtil.writeAbilitiesToFile(abilities);
    }

    @Override
    public void update(Ability item) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
