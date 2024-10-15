package com.parcial.builder;

import java.util.ArrayList;
import java.util.List;

import com.parcial.factory.abstractFactory.CharacterFactory;
import com.parcial.model.Character;
import com.parcial.model.Technique;

public class BuilderDirector {

    private final CharacterFactory myFactory;

    public BuilderDirector(CharacterFactory myFactory) {
        this.myFactory = myFactory;
    }

    private void buildCommonAttributes(ICharacterBuilder builder, CharacterFactory factory, String name, int age, double height, double weight, int powerLevel, String techniqueName, String techniqueType, String techniqueDescription) {
        List<Technique> techniqueList = new ArrayList<>();
        // Crear técnica con parámetros personalizados
        techniqueList.add(factory.createTechnique(techniqueName, techniqueType, techniqueDescription));
        builder.techniques(techniqueList)
                .race(factory.createRace())
                .name(name)
                .age(age)
                .height(height)
                .weight(weight)
                .powerLevel(powerLevel);
    }

    public Character buildSaiyanCharacter(ICharacterBuilder builder) {
        buildCommonAttributes(builder, myFactory, "Unknown", 20, 1.75, 80, 3000000, "Final Flash", "Ki-based", "A powerful blast of concentrated energy fired with both hands.");
        return builder.build();
    }

    public Character buildNamekCharacter(ICharacterBuilder builder) {
        buildCommonAttributes(builder, myFactory, "Unknown", 20, 2.40, 90, 10000, "Combat Meditation", "Physical Improvement", "Increases concentration and Ki recovery during battle.");
        return builder.build();
    }
}
