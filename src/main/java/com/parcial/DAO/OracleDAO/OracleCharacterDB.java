package com.parcial.DAO.OracleDAO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.parcial.DAO.IDataAccess;
import com.parcial.model.Character;
import com.parcial.model.Race;
import com.parcial.model.Technique;
import com.parcial.utils.CharacterJSONUtil;

public class OracleCharacterDB implements IDataAccess<Character> {

    private final CharacterJSONUtil jsonUtil = new CharacterJSONUtil();
    private Map<Integer, Character> characters;

    public OracleCharacterDB() {
        // Cargar personajes en el Map
        characters = jsonUtil.readFromFile().stream()
                .collect(Collectors.toMap(Character::getId, character -> character));
    }

    @Override
    public List<Character> getAll() {
        characters = jsonUtil.readFromFile().stream()
                .collect(Collectors.toMap(Character::getId, character -> character));
        List<Race> races = new OracleRaceDB().getAll();
        List<Technique> techniques = new OracleTechniqueDB().getAll();

        for (Character character : characters.values()) {
            Race raceWithAbilities = races.stream()
                    .filter(race -> race.getId() == character.getRace().getId())
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Race not found: " + character.getRace().getId()));

            character.setRace(raceWithAbilities);

            List<Technique> characterTechniques = character.getTechniques().stream()
                    .map(techniqueId -> techniques.stream()
                    .filter(technique -> technique.getId() == techniqueId.getId())
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Technique not found: " + techniqueId.getId())))
                    .collect(Collectors.toList());

            character.setTechniques(characterTechniques);  // Asignar técnicas completas al personaje
        }

        return characters.values().stream().collect(Collectors.toList());
    }

    @Override
    public Character getById(int id) {
        return characters.get(id);
    }

    @Override
    public Character save(Character character) {
        characters.put(character.getId(), character);
        jsonUtil.writeToFile(getAll());
        return character;
    }

    @Override
    public void update(Character character) {
        if (characters.containsKey(character.getId())) {
            characters.put(character.getId(), character);
            jsonUtil.writeToFile(getAll());
        } else {
            throw new IllegalArgumentException("Character ID " + character.getId() + " no existe para actualizar.");
        }
    }

    @Override
    public void delete(int id) {
        characters.remove(id);
        jsonUtil.writeToFile(getAll());
    }
}
