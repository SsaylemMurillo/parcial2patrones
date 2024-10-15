package com.parcial.DAO.MySQLDAO;

import java.util.List;
import java.util.stream.Collectors;

import com.parcial.DAO.IDataAccess;
import com.parcial.model.Ability;
import com.parcial.model.Race;
import com.parcial.utils.RaceJSONUtil;

public class MySQLRaceDB implements IDataAccess<Race> {

    private final RaceJSONUtil jsonUtil = new RaceJSONUtil();

    @Override
    public List<Race> getAll() {
        List<Race> races = jsonUtil.readFromFile();
        List<Ability> abilities = new MySQLAbilityDB().getAll();
        for (Race race : races) {
            List<Ability> raceAbilities = race.getAbilities().stream()
                    .map(ref -> abilities.stream()
                    .filter(ability -> ability.getId() == ref.getId())
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Ability not found with id: " + ref.getId())))
                    .collect(Collectors.toList());
            race.setAbilities(raceAbilities);
        }

        return races;
    }

    @Override
    public Race getById(int id) {
        return jsonUtil.readFromFile().stream()
                .filter(race -> race.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Race save(Race race) {
        jsonUtil.addItem(race);
        return race;
    }

    @Override
    public void update(Race race) {
        List<Race> races = jsonUtil.readFromFile();
        races.stream()
                .filter(r -> r.getId() == race.getId())
                .forEach(r -> {
                    r.setName(race.getName());
                    r.setHairColor(race.getHairColor());
                    r.setTransformation(race.getTransformation());
                    r.setOriginPlanet(race.getOriginPlanet());
                    r.setAbilities(race.getAbilities());
                });
        jsonUtil.writeToFile(races);
    }

    @Override
    public void delete(int id) {
        List<Race> races = jsonUtil.readFromFile();
        races.removeIf(race -> race.getId() == id);
        jsonUtil.writeToFile(races);
    }
}
