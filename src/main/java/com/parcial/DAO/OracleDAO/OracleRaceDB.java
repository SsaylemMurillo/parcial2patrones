package com.parcial.DAO.OracleDAO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.parcial.DAO.IDataAccess;
import com.parcial.model.Ability;
import com.parcial.model.Race;
import com.parcial.utils.RaceJSONUtil;

public class OracleRaceDB implements IDataAccess<Race> {

    private final RaceJSONUtil jsonUtil = new RaceJSONUtil();
    private final Map<Integer, Race> races;

    public OracleRaceDB() {
        // Cargar razas en el Map
        races = jsonUtil.readFromFile().stream()
                .collect(Collectors.toMap(Race::getId, race -> race));
    }

    @Override
    public List<Race> getAll() {
        List<Ability> abilities = new OracleAbilityDB().getAll();

        for (Race race : races.values()) {
            List<Ability> raceAbilities = race.getAbilities().stream()
                    .map(ref -> abilities.stream()
                            .filter(ability -> ability.getId() == ref.getId())
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("Ability not found with id: " + ref.getId())))
                    .collect(Collectors.toList());
            race.setAbilities(raceAbilities);
        }

        return races.values().stream().collect(Collectors.toList());
    }

    @Override
    public Race getById(int id) {
        return races.get(id);
    }

    @Override
    public Race save(Race race) {
        races.put(race.getId(), race);
        jsonUtil.writeToFile(getAll());
        return race;
    }

    @Override
    public void update(Race race) {
        if (races.containsKey(race.getId())) {
            races.put(race.getId(), race);
            jsonUtil.writeToFile(getAll());
        } else {
            throw new IllegalArgumentException("Race ID " + race.getId() + " no existe para actualizar.");
        }
    }

    @Override
    public void delete(int id) {
        races.remove(id);
        jsonUtil.writeToFile(getAll());
    }
}
