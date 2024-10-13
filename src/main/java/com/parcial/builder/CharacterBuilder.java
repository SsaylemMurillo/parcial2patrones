package com.parcial.builder;

import java.util.ArrayList;
import java.util.List;

import com.parcial.model.Character;
import com.parcial.model.Race;
import com.parcial.model.Technique;

public class CharacterBuilder implements ICharacterBuilder {

    private String name;
    private Race race;
    private int powerLevel;
    private List<Technique> techniques;
    private int age;
    private double height;
    private double weight;
    private final ITechniqueBuilder techniqueBuilder;

    public CharacterBuilder(ITechniqueBuilder techniqueBuilder) {
        super();
        this.techniques = new ArrayList<>();
        this.techniqueBuilder = techniqueBuilder;
    }

    @Override
    public ICharacterBuilder name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ICharacterBuilder powerLevel(int powerLevel) {
        this.powerLevel = powerLevel;
        return this;
    }

    @Override
    public ICharacterBuilder techniques(List<Technique> techniques) {
        this.techniques = techniques;
        return this;
    }

    @Override
    public ICharacterBuilder age(int age) {
        this.age = age;
        return this;
    }

    @Override
    public ICharacterBuilder height(double height) {
        this.height = height;
        return this;
    }

    @Override
    public ICharacterBuilder weight(double weight) {
        this.weight = weight;
        return this;
    }

    @Override
    public ICharacterBuilder race(Race race) {
        this.race = race;
        return this;
    }

    @Override
    public Character build() {
        return new Character(name, race, powerLevel, techniques, age, height, weight);
    }

    @Override
    public Character reset() {
        this.name = null;
        this.race = null;
        this.powerLevel = 0;
        this.techniques = new ArrayList<>();
        this.age = 0;
        this.height = 0.0;
        this.weight = 0.0;
        return build();
    }

    @Override
    public ICharacterBuilder buildNewTechnique(String name, String style, String description) {
        this.techniqueBuilder.name(name);
        this.techniqueBuilder.style(style);
        this.techniqueBuilder.description(description);
        this.techniques.add(this.techniqueBuilder.build());
        return this;
    }
}
