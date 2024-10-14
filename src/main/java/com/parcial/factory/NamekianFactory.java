package com.parcial.factory;

import java.util.ArrayList;
import java.util.List;

import com.parcial.model.Ability;
import com.parcial.model.Race;
import com.parcial.model.Technique;

public class NamekianFactory implements CharacterFactory {

    @Override
    public Race createRace() {
        return new Race("Namek", "#00ff00", null, "Big Namek", "Planet Namek", createAbilities2());
    }

    public List<Ability> createAbilities2() {
        List<Ability> abilities = new ArrayList<>();
        abilities.add(new Ability("Regenerate", "Deffensive", 0, 60, "Recover parts of your body instantly"));

        return abilities;
    }

    @Override
    public Technique createTechnique(String name, String type, String description) {
        return new Technique(name, type, description);
    }

    @Override
    public List<Ability> createAbilities(List<Ability> abilities) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Ability createAbility(Ability ability) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
