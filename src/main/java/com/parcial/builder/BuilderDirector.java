package com.parcial.builder;

import java.util.ArrayList;
import java.util.List;

import com.parcial.factory.CharacterFactory;
import com.parcial.model.Character;
import com.parcial.model.Technique;

public class BuilderDirector {

    private final CharacterFactory saiyanFactory;
    private final CharacterFactory namekianFactory;

    public BuilderDirector(CharacterFactory saiyanFactory, CharacterFactory namekianFactory) {
        this.saiyanFactory = saiyanFactory;
        this.namekianFactory = namekianFactory;
    }

    private void buildCommonAttributes(ICharacterBuilder builder, CharacterFactory factory, String name, int age, double height, double weight, int powerLevel) {
        List<Technique> techniqueList = new ArrayList<>();
        techniqueList.add(factory.createTechnique());
        builder.techniques(techniqueList)
                .race(factory.createRace())
                .name(name)
                .age(age)
                .height(height)
                .weight(weight)
                .powerLevel(powerLevel);
    }

    public Character buildSaiyanCharacter(ICharacterBuilder builder) {
        buildCommonAttributes(builder, saiyanFactory, "SSAYKO", 30, 120, 80, 3000000);
        return builder.build();
    }

    public Character buildNamekCharacter(ICharacterBuilder builder) {
        buildCommonAttributes(builder, namekianFactory, "Lele", 20, 240, 30, 100);
        return builder.build();
    }
}
