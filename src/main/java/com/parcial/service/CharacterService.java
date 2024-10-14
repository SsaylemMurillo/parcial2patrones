package com.parcial.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.parcial.DAO.MySQLAbilityDB;
import com.parcial.DAO.MySQLCharacterDB;
import com.parcial.DAO.MySQLRaceDB;
import com.parcial.builder.BuilderDirector;
import com.parcial.builder.CharacterBuilder;
import com.parcial.builder.TechniqueBuilder;
import com.parcial.factory.CharacterFactory;
import com.parcial.factory.NamekianFactory2;
import com.parcial.factory.SaiyanFactory2;
import com.parcial.model.Ability;
import com.parcial.model.Character;
import com.parcial.model.Race;

@Service
public class CharacterService {

    private final MySQLCharacterDB characterDB;
    private final MySQLAbilityDB abilityDB;
    private final MySQLRaceDB raceDB;
    private final Map<String, CharacterFactory> factoryMap = new HashMap<>();

    ;

    public CharacterService() {
        this.characterDB = new MySQLCharacterDB();
        this.abilityDB = new MySQLAbilityDB();
        this.raceDB = new MySQLRaceDB();
        this.factoryMap.put("saiyan", new SaiyanFactory2()
        );
        this.factoryMap.put("namek", new NamekianFactory2());
    }

    public Character assembleCharacter(String race) {
        CharacterFactory factory = factoryMap.get(race.toLowerCase());
        if (factory == null) {
            throw new IllegalArgumentException("Raza no válida: " + race);
        }

        CharacterBuilder myBuilder = new CharacterBuilder(new TechniqueBuilder());
        BuilderDirector director = new BuilderDirector(factory);
        return director.buildSaiyanCharacter(myBuilder); // Usar un método genérico si es posible
    }

    public Race addAbilityToRace(String race, Ability newAbility) {
        CharacterFactory factory = factoryMap.get(race.toLowerCase());
        if (factory == null) {
            throw new IllegalArgumentException("Raza no válida: " + race);
        }

        factory.createAbility(newAbility);  // Agrega la habilidad a la raza
        return factory.createRace();  // Retorna la raza actualizada
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

    public Character createCharacter(String name, int age, int race, double height, double weight, int powerLevel) {
        CharacterBuilder myBuilder = new CharacterBuilder(new TechniqueBuilder());
        myBuilder.name(name)
                .age(age)
                .race(new Race(race))
                .height(height)
                .weight(weight)
                .powerLevel(powerLevel);

        Character character = myBuilder.build();
        return characterDB.saveCharacter(character);
    }
}
