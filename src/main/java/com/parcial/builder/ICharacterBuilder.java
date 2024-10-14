package com.parcial.builder;

import java.util.List;

import com.parcial.model.Technique;
import com.parcial.model.Character;
import com.parcial.model.Race;

public interface ICharacterBuilder {

    ICharacterBuilder id(int id);

    ICharacterBuilder name(String name);

    ICharacterBuilder race(Race race);

    ICharacterBuilder powerLevel(int powerLevel);

    ICharacterBuilder techniques(List<Technique> techniques);

    ICharacterBuilder buildNewTechnique(int id, String name, String style, String description);

    ICharacterBuilder age(int age);

    ICharacterBuilder height(double height);

    ICharacterBuilder weight(double weight);

    Character reset();

    Character build();
}
