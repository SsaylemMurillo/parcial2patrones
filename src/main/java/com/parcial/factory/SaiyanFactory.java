package com.parcial.factory;

import java.util.ArrayList;
import java.util.List;

import com.parcial.model.Ability;
import com.parcial.model.Race;
import com.parcial.model.Technique;

public class SaiyanFactory implements CharacterFactory {

    @Override
    public Race createRace() {
        return new Race("#000", "Vegeta", "Saiyan", "#FFD6BB", "Base", createAbilities());
    }

    @Override
    public List<Ability> createAbilities() {
        List<Ability> abilities = new ArrayList<>();
        abilities.add(new Ability("Ozaru", "Offensive", 10000, 3600, "You turn into a huge primate when exposed to the light of the full moon."));
        return abilities;

    }

    @Override
    public Technique createTechnique() {
        return new Technique("Ki Wave", "Ki-based", "You concentrate vital energy (ki) in a point of the body, and then it is released in the form of a powerful ray or sphere of energy that moves quickly towards the target.");
    }
}
