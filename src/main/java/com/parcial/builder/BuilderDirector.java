package com.parcial.builder;

import java.util.ArrayList;
import java.util.List;

import com.parcial.factory.CharacterFactory;
import com.parcial.factory.NamekianFactory;
import com.parcial.factory.SaiyanFactory;
import com.parcial.model.Character;
import com.parcial.model.Technique;

public class BuilderDirector {

    private final CharacterFactory saiyanFactory;
    private final CharacterFactory namekianFactory;


    public BuilderDirector(CharacterFactory saiyanFactory, CharacterFactory namekianFactory) {
        this.saiyanFactory = saiyanFactory;
        this.namekianFactory = namekianFactory;
    }


    private void buildCommonAttributes(ICharacterBuilder builder, CharacterFactory factory, String name, int age,
                                       double height, double weight, int powerLevel) {
        List<Technique> techniqueList = new ArrayList<>();
        techniqueList.addAll(factory.createTechniques()); // Se acepta múltiples técnicas
        builder.techniques(techniqueList)
               .race(factory.createRace())
               .name(name)
               .age(age)
               .height(height)
               .weight(weight)
               .powerLevel(powerLevel);
    }

   
    public Character buildSaiyanCharacter(ICharacterBuilder builder, List<Technique> techniques) {
        CharacterFactory factory = new SaiyanFactory();
        builder.techniques(techniques); //Lista tecnicas
        builder.race(factory.createRace());
        builder.name("SSAYKO");
        builder.age(30);
        builder.height(120);
        builder.weight(80);
        builder.powerLevel(3000000);
        return builder.build();
    }


    public Character buildNamekCharacter(ICharacterBuilder builder, List<Technique> techniques) {
        CharacterFactory factory = new NamekianFactory();
        builder.techniques(techniques); // *
        builder.race(factory.createRace());
        builder.name("Lele");
        builder.age(20);
        builder.height(240);
        builder.weight(30);
        builder.powerLevel(100);
        return builder.build();
    }
}
