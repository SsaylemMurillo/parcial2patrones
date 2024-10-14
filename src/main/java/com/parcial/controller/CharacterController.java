package com.parcial.controller;

import com.parcial.model.Ability;
import com.parcial.model.Character;
import com.parcial.model.Race;
import com.parcial.service.CharacterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
