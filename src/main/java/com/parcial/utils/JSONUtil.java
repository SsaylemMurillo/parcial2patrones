package com.parcial.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parcial.model.Ability;
import com.parcial.model.Technique;
import com.parcial.model.Race;
import com.parcial.model.Character;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class JSONUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String RESOURCES_PATH = "resources/";

    // --- Abilities ---
    public static List<Ability> readAbilitiesFromFile() {
        try {
            return objectMapper.readValue(new File(RESOURCES_PATH + "abilities.json"), new TypeReference<List<Ability>>() {
            });
        } catch (IOException e) {
            System.err.println("Error leyendo abilities.json:");
            //e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void writeAbilitiesToFile(List<Ability> abilities) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(RESOURCES_PATH + "abilities.json"), abilities);
        } catch (IOException e) {
            System.err.println("Error escribiendo abilities.json:");
            //e.printStackTrace();
        }
    }

    public static Ability addAbility(Ability ability) {
        List<Ability> abilities = readAbilitiesFromFile();
        int newId = abilities.stream().mapToInt(Ability::getId).max().orElse(0) + 1;
        ability.setId(newId);
        abilities.add(ability);
        writeAbilitiesToFile(abilities);
        return ability;
    }

    public static void updateRaceAbilities(int raceId, List<Integer> abilityIds) {
        List<Race> races = readRacesFromFile();

        // Buscar la raza que corresponde al ID
        Race raceToUpdate = races.stream()
                .filter(race -> race.getId() == raceId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Race ID " + raceId + " no existe."));

        // Actualizar la lista de habilidades
        List<Ability> abilities = readAbilitiesFromFile();

        // Comprobar que todas las habilidades existen antes de actualizar
        for (Integer abilityId : abilityIds) {
            boolean exists = abilities.stream().anyMatch(a -> a.getId() == abilityId);
            if (!exists) {
                throw new IllegalArgumentException("Ability ID " + abilityId + " no existe.");
            }
        }

        // Asignar las habilidades basadas en los IDs
        List<Ability> updatedAbilities = abilities.stream()
                .filter(a -> abilityIds.contains(a.getId()))
                .collect(Collectors.toList());

        raceToUpdate.setAbilities(updatedAbilities);

        // Escribir la lista actualizada de razas en el archivo JSON
        writeRacesToFile(races);
    }

    // --- Techniques ---
    public static List<Technique> readTechniquesFromFile() {
        try {
            return objectMapper.readValue(new File(RESOURCES_PATH + "techniques.json"), new TypeReference<List<Technique>>() {
            });
        } catch (IOException e) {
            System.err.println("Error leyendo techniques.json:");
            //e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void writeTechniquesToFile(List<Technique> techniques) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(RESOURCES_PATH + "techniques.json"), techniques);
        } catch (IOException e) {
            System.err.println("Error escribiendo techniques.json:");
            //e.printStackTrace();
        }
    }

    public static Technique addTechnique(Technique technique) {
        List<Technique> techniques = readTechniquesFromFile();
        int newId = techniques.stream().mapToInt(Technique::getId).max().orElse(0) + 1;
        technique.setId(newId);
        techniques.add(technique);
        writeTechniquesToFile(techniques);
        return technique;
    }

    public static List<Race> readRacesFromFile() {
        try {
            List<Ability> allAbilities = readAbilitiesFromFile();

            List<Race> races = objectMapper.readValue(new File(RESOURCES_PATH + "races.json"), new TypeReference<List<Race>>() {
            });

            for (Race race : races) {
                List<Ability> resolvedAbilities = new ArrayList<>();

                for (Ability raceAbility : race.getAbilities()) {
                    Ability resolvedAbility = allAbilities.stream()
                            .filter(ability -> ability.getId() == raceAbility.getId())
                            .findFirst()
                            .orElse(null);

                    if (resolvedAbility != null) {
                        resolvedAbilities.add(resolvedAbility);
                    }
                }
                race.setAbilities(resolvedAbilities);
            }

            return races;
        } catch (IOException e) {
            System.err.println("Error leyendo races.json:");
            //e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void writeRacesToFile(List<Race> races) {
        try {
            // Transformar las razas para que las habilidades sean listas de IDs
            List<Map<String, Object>> racesToWrite = races.stream().map(race -> {
                Map<String, Object> raceMap = new LinkedHashMap<>();
                raceMap.put("id", race.getId());
                raceMap.put("name", race.getName());
                raceMap.put("skinColor", race.getSkinColor());
                raceMap.put("hairColor", race.getHairColor());
                raceMap.put("transformation", race.getTransformation());
                raceMap.put("originPlanet", race.getOriginPlanet());
                // Convertir habilidades a lista de IDs
                List<Integer> abilityIds = race.getAbilities().stream().map(Ability::getId).collect(Collectors.toList());
                raceMap.put("abilities", abilityIds);
                return raceMap;
            }).collect(Collectors.toList());

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(RESOURCES_PATH + "races.json"), racesToWrite);
        } catch (IOException e) {
            System.err.println("Error escribiendo races.json:");
            //e.printStackTrace();
        }
    }

    public static Race addRace(Race race, List<Integer> abilityIds) {
        List<Race> races = readRacesFromFile();
        List<Ability> abilities = readAbilitiesFromFile();

        // Validar que todas las habilidades existan
        for (Integer abilityId : abilityIds) {
            boolean exists = abilities.stream().anyMatch(a -> a.getId() == abilityId);
            if (!exists) {
                throw new IllegalArgumentException("Ability ID " + abilityId + " no existe.");
            }
        }

        int newId = races.stream().mapToInt(Race::getId).max().orElse(0) + 1;
        race.setId(newId);

        // Asignar las habilidades basadas en los IDs
        List<Ability> raceAbilities = abilities.stream()
                .filter(a -> abilityIds.contains(a.getId()))
                .collect(Collectors.toList());
        race.setAbilities(raceAbilities);

        races.add(race);
        writeRacesToFile(races);
        return race;
    }

    public static List<Character> readCharactersFromFile() {
        try {
            // Leer todas las razas, técnicas y habilidades de una vez
            List<Race> races = readRacesFromFile(); // Este método ya une habilidades y razas
            List<Technique> techniques = readTechniquesFromFile();

            // Leer personajes
            List<Map<String, Object>> characterMaps = objectMapper.readValue(new File(RESOURCES_PATH + "characters.json"), new TypeReference<List<Map<String, Object>>>() {
            });

            List<Character> characters = new ArrayList<>();

            for (Map<String, Object> charMap : characterMaps) {
                Character character = new Character();
                character.setId((Integer) charMap.get("id"));
                character.setName((String) charMap.get("name"));
                character.setPowerLevel((Integer) charMap.get("powerLevel"));
                character.setAge((Integer) charMap.get("age"));
                character.setHeight(((Number) charMap.get("height")).doubleValue());
                character.setWeight(((Number) charMap.get("weight")).doubleValue());

                // Resolver la raza a partir de su ID sin volver a procesar habilidades
                Integer raceId = (Integer) charMap.get("race");
                Race race = races.stream()
                        .filter(r -> r.getId() == raceId)
                        .findFirst()
                        .orElse(null);
                character.setRace(race);

                // Verificación segura del tipo antes de hacer cast a List<Integer>
                Object techniquesObj = charMap.get("techniques");
                if (techniquesObj instanceof List<?> rawList) {
                    // Verificar que la lista contiene únicamente Integer
                    List<Integer> techniqueIds = rawList.stream()
                            .filter(item -> item instanceof Integer)
                            .map(item -> (Integer) item)
                            .collect(Collectors.toList());

                    // Resolver las técnicas
                    List<Technique> characterTechniques = techniques.stream()
                            .filter(t -> techniqueIds.contains(t.getId()))
                            .collect(Collectors.toList());
                    character.setTechniques(characterTechniques);
                } else {
                    System.err.println("Error: El campo 'techniques' no es una lista válida para el personaje con ID " + character.getId());
                }

                characters.add(character);
            }

            return characters;
        } catch (IOException e) {
            System.err.println("Error leyendo characters.json:");
            //e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void writeCharactersToFile(List<Character> characters) {
        try {
            // Transformar los personajes para que race sea ID y techniques sean listas de IDs
            List<Map<String, Object>> charactersToWrite = characters.stream().map(character -> {
                Map<String, Object> charMap = new LinkedHashMap<>();
                charMap.put("id", character.getId());
                charMap.put("name", character.getName());
                charMap.put("powerLevel", character.getPowerLevel());
                charMap.put("age", character.getAge());
                charMap.put("height", character.getHeight());
                charMap.put("weight", character.getWeight());
                // Asignar race como ID
                if (character.getRace() != null) {
                    charMap.put("race", character.getRace().getId());
                } else {
                    charMap.put("race", null);
                }
                // Asignar techniques como lista de IDs
                List<Integer> techniqueIds = character.getTechniques().stream().map(Technique::getId).collect(Collectors.toList());
                charMap.put("techniques", techniqueIds);
                return charMap;
            }).collect(Collectors.toList());

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(RESOURCES_PATH + "characters.json"), charactersToWrite);
        } catch (IOException e) {
            System.err.println("Error escribiendo characters.json:");
            //e.printStackTrace();
        }
    }

    public static Character addCharacter(Character character) {
        // Leer los datos actuales de personajes, razas y técnicas del archivo
        List<Character> characters = readCharactersFromFile();
        List<Race> races = readRacesFromFile();
        List<Technique> techniques = readTechniquesFromFile();

        // Validar que la raza exista (basado en el ID de la raza del personaje)
        Race race = races.stream().filter(r -> r.getId() == character.getRace().getId()).findFirst().orElse(null);
        if (race == null) {
            throw new IllegalArgumentException("Race ID " + character.getRace().getId() + " no existe.");
        }

        // Validar que las técnicas existan (basado en las técnicas del personaje)
        List<Technique> characterTechniques = new ArrayList<>();
        for (Technique tech : character.getTechniques()) {
            Technique existingTech = techniques.stream().filter(t -> t.getId() == tech.getId()).findFirst().orElse(null);
            if (existingTech == null) {
                throw new IllegalArgumentException("Technique ID " + tech.getId() + " no existe.");
            }
            characterTechniques.add(existingTech);
        }

        // Obtener el siguiente ID basado en el último personaje en la lista
        int newId = characters.stream().mapToInt(Character::getId).max().orElse(0) + 1;
        character.setId(newId);  // Asignar el nuevo ID al personaje

        // Asignar la raza validada y las técnicas validadas al personaje
        character.setRace(race);
        character.setTechniques(characterTechniques);

        // Agregar el personaje a la lista y escribirla nuevamente en el archivo
        characters.add(character);
        writeCharactersToFile(characters);

        return character;
    }

}
