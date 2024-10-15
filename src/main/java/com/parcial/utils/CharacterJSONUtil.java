package com.parcial.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parcial.model.Character;

public class CharacterJSONUtil implements IJSONHandler<Character> {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String CHARACTERS_FILE_PATH = "resources/characters.json";

    @Override
    public List<Character> readFromFile() {
        try {
            return objectMapper.readValue(new File(CHARACTERS_FILE_PATH), new TypeReference<List<Character>>() {
            });
        } catch (IOException e) {
            System.err.println("Error leyendo characters.json:");
            return new ArrayList<>();
        }
    }

    @Override
    public void writeToFile(List<Character> items) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(CHARACTERS_FILE_PATH), items);
        } catch (IOException e) {
            System.err.println("Error escribiendo characters.json:");
        }
    }

    @Override
    public Character addItem(Character item) {
        List<Character> characters = readFromFile();
        int newId = characters.stream().mapToInt(Character::getId).max().orElse(0) + 1;
        item.setId(newId);
        System.out.println("Objeto a punto de ser almacenado: ");
        System.out.println(item);
        characters.add(item);
        writeToFile(characters);
        return item;
    }
}
