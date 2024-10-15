package com.parcial.model;

public class Ability implements Cloneable {

    private int id;
    private String name;
    private String type;
    private int power;
    private int cooldownTime;
    private String description;

    public Ability() {
    }

    public Ability(int id) {
        this.id = id;
    }

    public Ability(String name, String type, int power, int cooldownTime, String description) {
        this.name = name;
        this.type = type;
        this.power = power;
        this.cooldownTime = cooldownTime;
        this.description = description;
    }

    public Ability(int id, String name, String type, int power, int cooldownTime, String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.power = power;
        this.cooldownTime = cooldownTime;
        this.description = description;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getCooldownTime() {
        return cooldownTime;
    }

    public void setCooldownTime(int cooldownTime) {
        this.cooldownTime = cooldownTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Ability: " + "Id: " + id + ", " + name + " [Type: " + type + ", Power: " + power + ", Cooldown Time: "
                + cooldownTime + " seconds, Description: " + description + "]";
    }

    @Override
    @SuppressWarnings("CloneDeclaresCloneNotSupported")
    public Ability clone() {
        try {
            return (Ability) super.clone();
        } catch (CloneNotSupportedException e) {
            //e.printStackTrace();
            return null;
        }
    }
}
