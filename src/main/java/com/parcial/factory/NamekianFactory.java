package com.parcial.factory;

import java.util.ArrayList;
import java.util.List;

import com.parcial.model.Ability;
import com.parcial.model.Race;
import com.parcial.model.Technique;

public class NamekianFactory implements CharacterFactory {

    @Override
    public Race createRace() {
        return new Race(null, "Namek", "Namekiano", "#00ff00", "Big Namek", createAbilities());
    }

    @Override
    public List<Ability> createAbilities() {
        List<Ability> abilities = new ArrayList<>();
        abilities.add(new Ability(
                "Regenerate",
                "Deffensive",
                0,
                60,
                "Recover parts of your body instantly"));
        return abilities;
    }

    @Override
    public Technique createTechnique() {
        return new Technique(
                "Combat Meditation",
                "Physical Improvement",
                "Increases concentration and Ki recovery during battle."
        );
    }

}
