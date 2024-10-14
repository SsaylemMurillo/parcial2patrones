package com.parcial.model;

import java.util.List;

public class Character implements Cloneable {

    private int id;
    private String name;
    private Race race;
    private int powerLevel;
    private List<Technique> techniques;
    private int age;
    private double height;
    private double weight;

    public Character() {

    }

    public Character(int id) {
        this.id = id;
    }

    public Character(int id, String name, Race race, int powerLevel, List<Technique> techniques,
            int age, double height, double weight) {
        this.id = id;
        this.name = name;
        this.race = race;
        this.techniques = techniques;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPowerLevel() {
        return powerLevel;
    }

    public void setPowerLevel(int powerLevel) {
        this.powerLevel = powerLevel;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public List<Technique> getTechniques() {
        return techniques;
    }

    public void setTechniques(List<Technique> techniques) {
        this.techniques = techniques;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Character: " + name + " , Race: " + race + ", Power Level: " + powerLevel
                + ", Techniques: " + techniques
                + ", Age: " + age
                + ", Height: " + height + "m, Weight: " + weight + "kg";
    }

}
