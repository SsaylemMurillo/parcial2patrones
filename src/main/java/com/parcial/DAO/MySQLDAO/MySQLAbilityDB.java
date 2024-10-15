package com.parcial.DAO.MySQLDAO;

import java.util.List;

import com.parcial.DAO.IDataAccess;
import com.parcial.model.Ability;
import com.parcial.utils.AbilityJSONUtil;

public class MySQLAbilityDB implements IDataAccess<Ability> {

    private final AbilityJSONUtil jsonUtil = new AbilityJSONUtil();

    @Override
    public List<Ability> getAll() {
        return jsonUtil.readFromFile();
    }

    @Override
    public Ability getById(int id) {
        return jsonUtil.readFromFile().stream()
                .filter(ability -> ability.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Ability save(Ability ability) {
        jsonUtil.addItem(ability);
        return ability;
    }

    @Override
    public void update(Ability ability) {
        List<Ability> abilities = jsonUtil.readFromFile();
        abilities.stream()
                .filter(a -> a.getId() == ability.getId())
                .forEach(a -> {
                    a.setName(ability.getName());
                    a.setType(ability.getType());
                    a.setPower(ability.getPower());
                    a.setCooldownTime(ability.getCooldownTime());
                    a.setDescription(ability.getDescription());
                });
        jsonUtil.writeToFile(abilities);
    }

    @Override
    public void delete(int id) {
        List<Ability> abilities = jsonUtil.readFromFile();
        abilities.removeIf(ability -> ability.getId() == id);
        jsonUtil.writeToFile(abilities);
    }
}
