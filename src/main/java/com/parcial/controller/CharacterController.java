package com.parcial.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parcial.builder.BuilderDirector;
import com.parcial.builder.CharacterBuilder;
import com.parcial.builder.TechniqueBuilder;
import com.parcial.factory.CharacterFactory;
import com.parcial.factory.NamekianFactory;
import com.parcial.factory.SaiyanFactory;
import com.parcial.model.Character;

@RestController
@RequestMapping("/api/character")
public class CharacterController {

    private final BuilderDirector myDirector;
    private final CharacterFactory saiyanFactory;
    private final CharacterFactory namekianFactory;

    public CharacterController() {
        this.saiyanFactory = new SaiyanFactory();
        this.namekianFactory = new NamekianFactory();
        this.myDirector = new BuilderDirector(saiyanFactory, namekianFactory);
    }

    @GetMapping("/assemble/default")
    public Character assembleDefaultCharacter(@RequestParam String race) {

        CharacterBuilder myBuilder = new CharacterBuilder(new TechniqueBuilder());
        switch (race.toLowerCase()) {
            case "saiyan" -> {
                return this.myDirector.buildSaiyanCharacter(myBuilder);
            }
            case "namek" -> {
                return this.myDirector.buildNamekCharacter(myBuilder);
            }
            default ->
                throw new IllegalArgumentException("Raza no válida: " + race);
        }
    }

    @PostMapping("/assemble/new_character")
    public Character assembleNewCharacter(@RequestParam String race,
            @RequestParam String name,
            @RequestParam int powerLevel,
            @RequestParam String techniqueName,
            @RequestParam String techniqueStyle,
            @RequestParam String techniqueDescription,
            @RequestParam int age,
            @RequestParam double height,
            @RequestParam double weight) {

        CharacterBuilder myBuilder = new CharacterBuilder(new TechniqueBuilder());

        CharacterFactory selectedFactory;
        switch (race.toLowerCase()) {
            case "saiyan" -> {
                selectedFactory = this.saiyanFactory;
            }
            case "namek" -> {
                selectedFactory = this.namekianFactory;
            }
            default -> {
                throw new IllegalArgumentException("Raza no válida: " + race);
            }
        }

        myBuilder.name(name)
                .age(age)
                .race(selectedFactory.createRace())
                .height(height)
                .weight(weight)
                .powerLevel(powerLevel);
        myBuilder.buildNewTechnique(techniqueName, techniqueStyle, techniqueDescription);

        return myBuilder.build();
    }

}
