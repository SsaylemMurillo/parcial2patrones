package com.parcial.DAO;

import java.util.ArrayList;
import java.util.List;

import com.parcial.model.Character;
import com.parcial.utils.JSONUtil;

public class MySQLCharacterDB implements IDataAccess<Character> {

    private List<Character> characters = new ArrayList<>();

    public MySQLCharacterDB() {
        this.characters = JSONUtil.readCharactersFromFile();
    }

    @Override
    public List<Character> getAll() {
        return characters;
    }

    @Override
    public Character getById(int id) {
        return characters.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public Character getByName(String name) {
        return characters.stream().filter(c -> c.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    @Override
    public void save(Character character) {
        characters.add(character);
        JSONUtil.writeCharactersToFile(characters);
    }

    public Character saveCharacter(Character character) {
        return JSONUtil.addCharacter(character);
    }

    @Override
    public void delete(int id) {
        characters.removeIf(c -> c.getId() == id);
        JSONUtil.writeCharactersToFile(characters);
    }

    @Override
    public void update(Character item) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
