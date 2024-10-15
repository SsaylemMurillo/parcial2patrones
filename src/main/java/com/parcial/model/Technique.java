package com.parcial.model;

public class Technique implements Cloneable {

    private int id;
    private String name;
    private String style;
    private String description;

    public Technique() {

    }

    public Technique(int id) {
        this.id = id;
    }

    public Technique(String name, String style, String description) {
        this.name = name;
        this.style = style;
        this.description = description;
    }

    public Technique(int id, String name, String style, String description) {
        this.id = id;
        this.name = name;
        this.style = style;
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

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Technique: " + name + " [Style: " + style + ", Description: " + description + "]";
    }

    @Override
    @SuppressWarnings("CloneDeclaresCloneNotSupported")
    public Technique clone() {
        try {
            return (Technique) super.clone();
        } catch (CloneNotSupportedException e) {
            //e.printStackTrace();
            return null;
        }
    }
}
