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

public class NamekianFactory2 implements CharacterFactory {

    private IDataAccess<Race> raceDAO;
    private IDataAccess<Ability> abilityDAO;

    public NamekianFactory2() {
        this.raceDAO = new MySQLRaceDB();
        this.abilityDAO = new MySQLAbilityDB();
    }

    @Override
    public Race createRace() {
        this.raceDAO = new MySQLRaceDB();
        this.abilityDAO = new MySQLAbilityDB();

        return raceDAO.getAll().stream()
                .filter(race -> race.getName().equalsIgnoreCase("Namek"))
                .findFirst()
                .map(race -> new Race(
                race.getName(),
                race.getSkinColor(),
                race.getHairColor(),
                race.getTransformation(),
                race.getOriginPlanet(),
                race.getAbilities()
        ))
                .orElseThrow(() -> new IllegalArgumentException("Namekian race not found"));
    }

    @Override
    public List<Ability> createAbilities(List<Ability> abilities) {
        this.raceDAO = new MySQLRaceDB();
        this.abilityDAO = new MySQLAbilityDB();

        return abilities;
    }

    @Override
    public Technique createTechnique(String name, String type, String description) {
        return new Technique(name, type, description);
    }

    @Override
    public Ability createAbility(Ability ability) {
        this.raceDAO = new MySQLRaceDB();
        this.abilityDAO = new MySQLAbilityDB();
        // Guardar la habilidad
        Ability abilitySaved = ((MySQLAbilityDB) abilityDAO).saveAbility(ability);

        // Actualizar el DAO para reflejar los cambios
        this.abilityDAO = new MySQLAbilityDB();
        MySQLAbilityDB myAbilityDAO = (MySQLAbilityDB) abilityDAO; // Reinstanciamos el DAO

        // Obtener la raza Namekian
        MySQLRaceDB myRaceDAO = (MySQLRaceDB) raceDAO;
        Race namekianRace = myRaceDAO.getAll().stream()
                .filter(race -> race.getName().equalsIgnoreCase("Namek"))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Namekian race not found"));

        List<Integer> abilitiesIds = namekianRace.getAbilities().stream()
                .map(Ability::getId)
                .collect(Collectors.toList());
        abilitiesIds.add(abilitySaved.getId());

        namekianRace.setAbilities(abilitiesIds.stream()
                .map(myAbilityDAO::getById)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));

        myRaceDAO.update(namekianRace);

        return abilitySaved;
    }
}
