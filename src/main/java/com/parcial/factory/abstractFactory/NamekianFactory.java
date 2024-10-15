package com.parcial.factory.abstractFactory;

import java.util.List;

import com.parcial.DAO.IDataAccess;
import com.parcial.model.Ability;
import com.parcial.model.Race;
import com.parcial.model.Technique;

public class NamekianFactory implements CharacterFactory {

    private final IDataAccess<Race> raceDAO;
    private final IDataAccess<Ability> abilityDAO;

    public NamekianFactory(IDataAccess<Race> raceDAO, IDataAccess<Ability> abilityDAO) {
        this.raceDAO = raceDAO;
        this.abilityDAO = abilityDAO;
    }

    @Override
    public Race createRace() {
        return raceDAO.getAll().stream()
                .filter(race -> race.getName().equalsIgnoreCase("Namek"))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Namekian race not found"));
    }

    @Override
    public Technique createTechnique(String name, String type, String description) {
        return new Technique(name, type, description);
    }

    @Override
    public Ability createAbility(Ability ability) {
        Ability abilitySaved = abilityDAO.save(ability);

        Race namekianRace = raceDAO.getAll().stream()
                .filter(race -> race.getName().equalsIgnoreCase("Namek"))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Namekian race not found"));

        List<Ability> abilities = namekianRace.getAbilities();
        abilities.add(abilitySaved);

        namekianRace.setAbilities(abilities);
        raceDAO.update(namekianRace);

        return abilitySaved;
    }
}
