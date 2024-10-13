package com.parcial.factory;

import java.util.List;

import com.parcial.model.Ability;
import com.parcial.model.Race;
import com.parcial.model.Technique;

public interface CharacterFactory {

    Race createRace();

    List<Ability> createAbilities();

    Technique createTechnique();
}
