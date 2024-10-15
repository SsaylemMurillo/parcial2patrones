package com.parcial.factory.abstractFactory;

import java.util.List;
import java.util.stream.Collectors;

import com.parcial.DAO.IDataAccess;
import com.parcial.model.Ability;
import com.parcial.model.Race;
import com.parcial.model.Technique;

public class SaiyanFactory implements CharacterFactory {

    private final IDataAccess<Race> raceDAO;
    private final IDataAccess<Ability> abilityDAO;

    public SaiyanFactory(IDataAccess<Race> raceDAO, IDataAccess<Ability> abilityDAO) {
        this.raceDAO = raceDAO;
        this.abilityDAO = abilityDAO;
    }

    @Override
    public Race createRace() {
        return raceDAO.getAll().stream()
                .filter(race -> race.getName().equalsIgnoreCase("Saiyan"))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Saiyan race not found"));
    }

    @Override
    public Technique createTechnique(String name, String type, String description) {
        return new Technique(name, type, description);
    }

    @Override
    public Ability createAbility(Ability ability) {
        Ability abilitySaved = abilityDAO.save(ability);

        Race saiyanRace = raceDAO.getAll().stream()
                .filter(race -> race.getName().equalsIgnoreCase("Saiyan"))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Saiyan race not found"));

        List<Integer> abilityIds = saiyanRace.getAbilities().stream()
                .map(Ability::getId)
                .collect(Collectors.toList());

        abilityIds.add(abilitySaved.getId());

        saiyanRace.setAbilities(abilityIds.stream()
                .map(id -> {
                    Ability refAbility = new Ability();
                    refAbility.setId(id);
                    return refAbility;
                })
                .collect(Collectors.toList()));

        raceDAO.update(saiyanRace);

        return abilitySaved;
    }

}
