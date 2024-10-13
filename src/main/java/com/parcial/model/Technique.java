package com.parcial.model;

public class Technique {

    private String name;
    private String style;
    private String description;

    public Technique(String name, String style, String description) {
        this.name = name;
        this.style = style;
        this.description = description;
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
}
