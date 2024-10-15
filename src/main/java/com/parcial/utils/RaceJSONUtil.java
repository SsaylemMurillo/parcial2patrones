package com.parcial.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parcial.model.Race;

public class RaceJSONUtil implements IJSONHandler<Race> {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String RACES_FILE_PATH = "resources/races.json";

    @Override
    public List<Race> readFromFile() {
        try {
            return objectMapper.readValue(new File(RACES_FILE_PATH), new TypeReference<List<Race>>() {
            });
        } catch (IOException e) {
            System.err.println("Error leyendo races.json:");
            return new ArrayList<>();
        }
    }

    @Override
    public void writeToFile(List<Race> items) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(RACES_FILE_PATH), items);
        } catch (IOException e) {
            System.err.println("Error escribiendo races.json:");
        }
    }

    @Override
    public Race addItem(Race item) {
        List<Race> races = readFromFile();
        int newId = races.stream().mapToInt(Race::getId).max().orElse(0) + 1;
        item.setId(newId);
        races.add(item);
        writeToFile(races);
        return item;
    }
}
