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
import com.parcial.model.Technique;

import com.parcial.model.Character;
import com.parcial.service.CharacterService;

import java.util.List;
@RestController
@RequestMapping("/api/character")
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping("/assemble/default")
    public Character assembleDefaultCharacter(@RequestParam String race) {
        return characterService.assembleDefaultCharacter(race);
    }

    @PostMapping("/assemble/new_character")
    public Character assembleNewCharacter(@RequestParam String race,
                                          @RequestParam String name,
                                          @RequestParam int powerLevel,
                                          @RequestParam List<String> techniqueNames,
                                          @RequestParam List<String> techniqueStyles,
                                          @RequestParam List<String> techniqueDescriptions,
                                          @RequestParam int age,
                                          @RequestParam double height,
                                          @RequestParam double weight) {
        return characterService.assembleNewCharacter(race, name, powerLevel, techniqueNames, techniqueStyles, techniqueDescriptions, age, height, weight);
    }
}