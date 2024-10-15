package com.parcial.DAO.MySQLDAO;

import java.util.List;
import java.util.stream.Collectors;

import com.parcial.DAO.IDataAccess;
import com.parcial.model.Character;
import com.parcial.model.Race;
import com.parcial.model.Technique;
import com.parcial.utils.CharacterJSONUtil;

public class MySQLCharacterDB implements IDataAccess<Character> {

    private final CharacterJSONUtil jsonUtil = new CharacterJSONUtil();

    @Override
    public List<Character> getAll() {
        List<Character> characters = jsonUtil.readFromFile();
        List<Race> races = new MySQLRaceDB().getAll();
        List<Technique> techniques = new MySQLTechniqueDB().getAll();

        for (Character character : characters) {
            Race raceWithAbilities = races.stream()
                    .filter(race -> race.getId() == character.getRace().getId())
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Race not found: " + character.getRace().getId()));

            character.setRace(raceWithAbilities);

            List<Integer> techniqueIds = character.getTechniques().stream()
                    .map(Technique::getId)
                    .collect(Collectors.toList());

            List<Technique> characterTechniques = techniqueIds.stream()
                    .map(techniqueId -> techniques.stream()
                    .filter(technique -> technique.getId() == techniqueId)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Technique not found: " + techniqueId)))
                    .collect(Collectors.toList());

            character.setTechniques(characterTechniques);  // Asignar técnicas completas al personaje
        }

        return characters;
    }

    @Override
    public Character getById(int id) {
        return jsonUtil.readFromFile().stream()
                .filter(character -> character.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Character save(Character character) {
        jsonUtil.addItem(character);
        return character;
    }

    @Override
    public void update(Character character) {
        List<Character> characters = jsonUtil.readFromFile();
        characters.stream()
                .filter(c -> c.getId() == character.getId())
                .forEach(c -> {
                    c.setName(character.getName());
                    c.setPowerLevel(character.getPowerLevel());
                    c.setRace(character.getRace());
                    c.setTechniques(character.getTechniques());
                    c.setAge(character.getAge());
                    c.setHeight(character.getHeight());
                    c.setWeight(character.getWeight());
                });
        jsonUtil.writeToFile(characters);
    }

    @Override
    public void delete(int id) {
        List<Character> characters = jsonUtil.readFromFile();
        characters.removeIf(character -> character.getId() == id);
        jsonUtil.writeToFile(characters);
    }
}
