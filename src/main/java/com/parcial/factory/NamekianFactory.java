package com.parcial.factory;

import java.util.ArrayList;
import java.util.List;

import com.parcial.model.Ability;
import com.parcial.model.Race;
import com.parcial.model.Technique;

public class NamekianFactory implements CharacterFactory {

    @Override
    public Race createRace() {
        return new Race(null, "Namek", "Namekian", "#00ff00", "Big Namek", createAbilities());
    }

    @Override
    public List<Ability> createAbilities() {
        List<Ability> abilities = new ArrayList<>();
        abilities.add(new Ability("Regenerate", "Defensive", 500, 60, 
            "Recover parts of your body instantly."));
        return abilities;
    }

  

    // Implementación del nuevo método para crear una lista de técnicas
    @Override
    public List<Technique> createTechniques() {
        List<Technique> techniques = new ArrayList<>();
        techniques.add(new Technique("Meditation", "Enhancement", 
            "Increases concentration and ki recovery during battle."));
        techniques.add(new Technique("Namekian Fusion", "Support", 
            "Fuse with another Namekian to greatly increase power level."));
        return techniques;
    }
}
