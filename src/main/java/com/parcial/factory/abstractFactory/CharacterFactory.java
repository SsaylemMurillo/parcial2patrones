package com.parcial.factory.abstractFactory;

import com.parcial.model.Ability;
import com.parcial.model.Race;
import com.parcial.model.Technique;

public interface CharacterFactory {

    Race createRace();

    Ability createAbility(Ability ability);

    Technique createTechnique(String name, String type, String description);
}
