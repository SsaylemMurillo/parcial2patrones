package com.parcial.factory;

import java.util.ArrayList;
import java.util.List;

import com.parcial.model.Ability;
import com.parcial.model.Race;
import com.parcial.model.Technique;

public class SaiyanFactory implements CharacterFactory {

    @Override
    public Race createRace() {
        return new Race("Saiyan", "#FFD6BB", "#000", "Super Saiyan", "Planet Vegeta", createAbilities2());
    }

    public List<Ability> createAbilities2() {
        List<Ability> abilities = new ArrayList<>();
        abilities.add(new Ability("Zenkai Boost", "Passive", 5000, 0, "Increases power after recovering from near-death experience."));
        abilities.add(new Ability("Ozaru", "Transformation", 10000, 3600, "Can transform into a great ape at the full moon (or a similar object) to increase their already formidable strength tremendously."));

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
