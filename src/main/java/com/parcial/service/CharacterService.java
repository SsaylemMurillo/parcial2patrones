package com.parcial.service;

import com.parcial.builder.BuilderDirector;
import com.parcial.builder.CharacterBuilder;
import com.parcial.builder.TechniqueBuilder;
import com.parcial.factory.CharacterFactory;
import com.parcial.factory.NamekianFactory;
import com.parcial.factory.SaiyanFactory;
import com.parcial.model.Character;
import com.parcial.model.Technique;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CharacterService {

    private final BuilderDirector myDirector;
    private final CharacterFactory saiyanFactory;
    private final CharacterFactory namekianFactory;

    public CharacterService() {
        this.saiyanFactory = new SaiyanFactory();
        this.namekianFactory = new NamekianFactory();
        this.myDirector = new BuilderDirector(saiyanFactory, namekianFactory);
    }

    public Character assembleDefaultCharacter(String race) {
        CharacterBuilder myBuilder = new CharacterBuilder(new TechniqueBuilder());
        List<Technique> defaultTechniques = new ArrayList<>();

        switch (race.toLowerCase()) {
            case "saiyan" -> {
                defaultTechniques.add(new Technique("Kamehameha", "Ki-based", "A powerful energy blast."));
                defaultTechniques.add(new Technique("Galick Gun", "Ki-based", 
                "A purple energy blast-----------."));
                return this.myDirector.buildSaiyanCharacter(myBuilder, defaultTechniques);
            }
            case "namek" -> {
                defaultTechniques.add(new Technique("Meditación de Combate", "Mejora física", "Aumenta la concentración y la recuperación de Ki."));
                return this.myDirector.buildNamekCharacter(myBuilder, defaultTechniques);
            }
            default -> throw new IllegalArgumentException("Raza no válida: " + race);
        }
    }

    public Character assembleNewCharacter(String race, String name, int powerLevel, List<String> techniqueNames, 
                                           List<String> techniqueStyles, List<String> techniqueDescriptions, 
                                           int age, double height, double weight) {
        if (techniqueNames.size() != techniqueStyles.size() || techniqueStyles.size() != techniqueDescriptions.size()) {
            throw new IllegalArgumentException("La cantidad de nombres, estilos y descripciones de técnicas deben coincidir.");
        }

        CharacterBuilder myBuilder = new CharacterBuilder(new TechniqueBuilder());
        CharacterFactory selectedFactory;

        switch (race.toLowerCase()) {
            case "saiyan" -> selectedFactory = this.saiyanFactory;
            case "namek" -> selectedFactory = this.namekianFactory;
            default -> throw new IllegalArgumentException("Raza no válida: " + race);
        }

        myBuilder.name(name)
                .age(age)
                .race(selectedFactory.createRace())
                .height(height)
                .weight(weight)
                .powerLevel(powerLevel);

        for (int i = 0; i < techniqueNames.size(); i++) {
            myBuilder.buildNewTechnique(techniqueNames.get(i), techniqueStyles.get(i), techniqueDescriptions.get(i));
        }

        return myBuilder.build();
    }
}
