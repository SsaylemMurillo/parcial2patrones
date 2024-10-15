package com.parcial.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parcial.model.Ability;
import com.parcial.model.Character;
import com.parcial.model.Race;
import com.parcial.model.Technique;
import com.parcial.service.CharacterService;

@RestController
@RequestMapping("/api/character")
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController() {
        this.characterService = new CharacterService();
    }

    @GetMapping("/assemble/default")
    public Character assembleDefaultCharacter(@RequestParam String race) {
        return characterService.assembleCharacter(race);
    }

    @GetMapping("/clone")
    public Character cloneCharacter(@RequestParam String characterName, @RequestParam String newName, @RequestParam int powerLevel) {
        return characterService.cloneCharacter(characterName, newName, powerLevel);
    }

    @PostMapping("/add_ability")
    public ResponseEntity<Race> addAbilityToRace(
            @RequestParam String race,
            @RequestParam String name,
            @RequestParam String type,
            @RequestParam int power,
            @RequestParam int cooldownTime,
            @RequestParam String description) {

        Ability newAbility = new Ability(name, type, power, cooldownTime, description);
        Race updatedRace = characterService.addAbilityToRace(race, newAbility);
        return ResponseEntity.ok(updatedRace);
    }

    @GetMapping("/query/all_characters")
    public List<Character> getAllCharacters() {
        return characterService.getAllCharacters();
    }

    @GetMapping("/query/all_abilities")
    public List<Ability> getAllAbilities() {
        return characterService.getAllAbilities();
    }

    @GetMapping("/query/all_races")
    public List<Race> getAllRaces() {
        return characterService.getAllRaces();
    }

    @GetMapping("/query/all_techniques")
    public List<Technique> getAllTechniques() {
        return characterService.getAllTechniques();
    }

    @PostMapping("/assemble/new_character")
    public Character assembleNewCharacter(
            @RequestParam int race,
            @RequestParam String name,
            @RequestParam int powerLevel,
            @RequestParam int age,
            @RequestParam double height,
            @RequestParam double weight) {

        return characterService.createCharacter(name, age, race, height, weight, powerLevel);
    }
}
