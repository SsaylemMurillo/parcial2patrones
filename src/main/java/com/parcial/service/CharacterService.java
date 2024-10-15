package com.parcial.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.parcial.DAO.IDataAccess;
import com.parcial.builder.BuilderDirector;
import com.parcial.builder.CharacterBuilder;
import com.parcial.builder.TechniqueBuilder;
import com.parcial.config.DatabaseConfig;
import com.parcial.factory.abstractFactory.CharacterFactory;
import com.parcial.factory.abstractFactory.NamekianFactory;
import com.parcial.factory.abstractFactory.SaiyanFactory;
import com.parcial.model.Ability;
import com.parcial.model.Character;
import com.parcial.model.Race;
import com.parcial.model.Technique;

@Service
public class CharacterService {

    private final IDataAccess<Character> characterDB;
    private final IDataAccess<Ability> abilityDB;
    private final IDataAccess<Race> raceDB;
    private final IDataAccess<Technique> techniqueDB;
    private final Map<String, CharacterFactory> factoryMap = new HashMap<>();

    public CharacterService() {
        DatabaseConfig.configureAdapterDB("mysql");

        this.characterDB = DatabaseConfig.getDAO(Character.class);
        this.abilityDB = DatabaseConfig.getDAO(Ability.class);
        this.raceDB = DatabaseConfig.getDAO(Race.class);
        this.techniqueDB = DatabaseConfig.getDAO(Technique.class);

        this.factoryMap.put("saiyan", new SaiyanFactory(raceDB, abilityDB));
        this.factoryMap.put("namek", new NamekianFactory(raceDB, abilityDB));
    }

    public Character cloneCharacter(String characterName, String newName, int newPowerLevel) {
        Character characterToClone = characterDB.getAll().stream()
                .filter(c -> c.getName().equalsIgnoreCase(characterName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Personaje no encontrado"));

        Character clonedCharacter = characterToClone.clone();
        CharacterBuilder builder = new CharacterBuilder(new TechniqueBuilder());

        builder
                .id(clonedCharacter.getId())
                .name(newName)
                .powerLevel(newPowerLevel)
                .race(clonedCharacter.getRace())
                .age(clonedCharacter.getAge())
                .height(clonedCharacter.getHeight())
                .weight(clonedCharacter.getWeight())
                .techniques(clonedCharacter.getTechniques());

        Character updatedCharacter = builder.build();
        //characterDB.save(updatedCharacter);

        return updatedCharacter;
    }

    public Character assembleCharacter(String race) {
        CharacterFactory factory = factoryMap.get(race.toLowerCase());
        if (factory == null) {
            throw new IllegalArgumentException("Raza no válida: " + race);
        }

        CharacterBuilder myBuilder = new CharacterBuilder(new TechniqueBuilder());
        BuilderDirector director = new BuilderDirector(factory);
        return director.buildSaiyanCharacter(myBuilder);
    }

    public Race addAbilityToRace(String race, Ability newAbility) {
        CharacterFactory factory = factoryMap.get(race.toLowerCase());
        if (factory == null) {
            throw new IllegalArgumentException("Raza no válida: " + race);
        }

        factory.createAbility(newAbility);
        return factory.createRace();
    }

    public List<Character> getAllCharacters() {
        return characterDB.getAll();
    }

    public List<Ability> getAllAbilities() {
        return abilityDB.getAll();
    }

    public List<Race> getAllRaces() {
        return raceDB.getAll();
    }

    public List<Technique> getAllTechniques() {
        return techniqueDB.getAll();
    }

    public Character createCharacter(String name, int age, int raceId, double height, double weight, int powerLevel) {
        CharacterBuilder myBuilder = new CharacterBuilder(new TechniqueBuilder());
        myBuilder.name(name)
                .age(age)
                .race(new Race(raceId))
                .height(height)
                .weight(weight)
                .powerLevel(powerLevel);

        Character character = myBuilder.build();
        characterDB.save(character);

        return character;
    }
}
