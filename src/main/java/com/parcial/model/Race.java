package com.parcial.model;

import java.util.ArrayList;
import java.util.List;

public class Race implements Cloneable {

    private int id;
    private String name;
    private String skinColor;
    private String hairColor;
    private String transformation;
    private String originPlanet;
    private List<Ability> abilities;

    public Race() {

    }

    public Race(int id) {
        this.id = id;
    }

    public Race(String name, String skinColor, String hairColor, String transformation, String originPlanet, List<Ability> abilities) {
        this.name = name;
        this.skinColor = skinColor;
        this.hairColor = hairColor;
        this.transformation = transformation;
        this.originPlanet = originPlanet;
        this.abilities = abilities;
    }

    public Race(int id, String hairColor, String originPlanet, String name, String skinColor, String transformation, Ability abilityDefault) {
        this.id = id;
        this.hairColor = hairColor;
        this.originPlanet = originPlanet;
        this.name = name;
        this.skinColor = skinColor;
        this.transformation = transformation;
        this.abilities = new ArrayList<>();
        this.abilities.add(abilityDefault);
    }

    public Race(int id, String hairColor, String originPlanet, String name, String skinColor, String transformation, List<Ability> abilities) {
        this.id = id;
        this.hairColor = hairColor;
        this.originPlanet = originPlanet;
        this.name = name;
        this.skinColor = skinColor;
        this.transformation = transformation;
        this.abilities = abilities;

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

    public String getSkinColor() {
        return skinColor;
    }

    public void setSkinColor(String skinColor) {
        this.skinColor = skinColor;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getTransformation() {
        return transformation;
    }

    public void setTransformation(String transformation) {
        this.transformation = transformation;
    }

    public String getOriginPlanet() {
        return originPlanet;
    }

    public void setOriginPlanet(String originPlanet) {
        this.originPlanet = originPlanet;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Race{");
        sb.append("race=").append(name);
        sb.append(", skinColor=").append(skinColor);
        sb.append(", hairColor=").append(hairColor);
        sb.append(", transformation=").append(transformation);
        sb.append(", originPlanet=").append(originPlanet);
        sb.append(", Abilities=").append(abilities);
        sb.append('}');
        return sb.toString();
    }

    @Override
    @SuppressWarnings("CloneDeclaresCloneNotSupported")
    public Race clone() {
        try {
            Race cloned = (Race) super.clone();

            // Clonamos la lista de habilidades
            if (this.abilities != null) {
                cloned.abilities = new ArrayList<>(this.abilities.size());
                for (Ability ability : this.abilities) {
                    cloned.abilities.add(ability.clone());  // Asumiendo que Ability también es Cloneable
                }
            }

            return cloned;
        } catch (CloneNotSupportedException e) {
            //e.printStackTrace();
            return null;
        }
    }
}
