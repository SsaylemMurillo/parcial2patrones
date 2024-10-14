package com.parcial.factory;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.parcial.DAO.IDataAccess;
import com.parcial.DAO.MySQLAbilityDB;
import com.parcial.DAO.MySQLRaceDB;
import com.parcial.model.Ability;
import com.parcial.model.Race;
import com.parcial.model.Technique;

public class SaiyanFactory2 implements CharacterFactory {

    private IDataAccess<Race> raceDAO;
    private IDataAccess<Ability> abilityDAO;

    public SaiyanFactory2() {
        this.raceDAO = new MySQLRaceDB();
        this.abilityDAO = new MySQLAbilityDB();
    }

    @Override
    public Race createRace() {
        this.raceDAO = new MySQLRaceDB();
        this.abilityDAO = new MySQLAbilityDB();

        return raceDAO.getAll().stream()
                .filter(race -> race.getName().equalsIgnoreCase("Saiyan"))
                .findFirst()
                .map(race -> new Race(
                race.getName(),
                race.getSkinColor(),
                race.getHairColor(),
                race.getTransformation(),
                race.getOriginPlanet(),
                race.getAbilities()
        ))
                .orElseThrow(() -> new IllegalArgumentException("Saiyan race not found"));
    }

    @Override
    public List<Ability> createAbilities(List<Ability> abilities) {
        return abilities; // No se necesita procesamiento adicional
    }

    @Override
    public Technique createTechnique(String name, String type, String description) {
        return new Technique(name, type, description);
    }

    @Override
    public Ability createAbility(Ability ability) {
        this.raceDAO = new MySQLRaceDB();
        this.abilityDAO = new MySQLAbilityDB();

        Ability abilitySaved = ((MySQLAbilityDB) abilityDAO).saveAbility(ability);

        this.abilityDAO = new MySQLAbilityDB();
        MySQLAbilityDB myAbilityDAO = (MySQLAbilityDB) abilityDAO;

        MySQLRaceDB myRaceDAO = (MySQLRaceDB) raceDAO;
        Race saiyanRace = myRaceDAO.getAll().stream()
                .filter(race -> race.getName().equalsIgnoreCase("Saiyan"))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Saiyan race not found"));

        List<Integer> abilitiesIds = saiyanRace.getAbilities().stream()
                .map(Ability::getId)
                .collect(Collectors.toList());
        abilitiesIds.add(abilitySaved.getId());

        saiyanRace.setAbilities(abilitiesIds.stream()
                .map(myAbilityDAO::getById)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));

        myRaceDAO.update(saiyanRace);

        return abilitySaved;
    }
}
