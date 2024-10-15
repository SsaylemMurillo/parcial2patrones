package com.parcial.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.parcial.model.Technique;

public class TechniqueJSONUtil implements IJSONHandler<Technique> {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String TECHNIQUES_FILE_PATH = "resources/techniques.json";

    @Override
    public List<Technique> readFromFile() {
        try {
            return objectMapper.readValue(new File(TECHNIQUES_FILE_PATH), new TypeReference<List<Technique>>() {
            });
        } catch (IOException e) {

            System.err.println("Error leyendo techniques.json:");
            return new ArrayList<>();
        }
    }

    @Override
    public void writeToFile(List<Technique> items) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(TECHNIQUES_FILE_PATH), items);
        } catch (IOException e) {
            System.err.println("Error escribiendo techniques.json:");
        }
    }

    @Override
    public Technique addItem(Technique item) {
        List<Technique> techniques = readFromFile();
        int newId = techniques.stream().mapToInt(Technique::getId).max().orElse(0) + 1;
        item.setId(newId);
        techniques.add(item);
        writeToFile(techniques);
        return item;
    }

}
