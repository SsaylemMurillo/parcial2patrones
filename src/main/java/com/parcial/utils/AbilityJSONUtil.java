package com.parcial.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parcial.model.Ability;

public class AbilityJSONUtil implements IJSONHandler<Ability> {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String ABILITIES_FILE_PATH = "resources/abilities.json";

    @Override
    public List<Ability> readFromFile() {
        try {
            return objectMapper.readValue(new File(ABILITIES_FILE_PATH), new TypeReference<List<Ability>>() {
            });
        } catch (IOException e) {
            System.err.println("Error leyendo abilities.json:");
            return new ArrayList<>();
        }
    }

    @Override
    public void writeToFile(List<Ability> items) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(ABILITIES_FILE_PATH), items);
        } catch (IOException e) {
            System.err.println("Error escribiendo abilities.json:");
        }
    }

    @Override
    public Ability addItem(Ability item) {
        List<Ability> abilities = readFromFile();
        int newId = abilities.stream().mapToInt(Ability::getId).max().orElse(0) + 1;
        item.setId(newId);
        abilities.add(item);
        writeToFile(abilities);
        return item;
    }
}
