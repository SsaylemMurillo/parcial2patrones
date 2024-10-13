package com.parcial.builder;

import com.parcial.model.Technique;

public class TechniqueBuilder implements ITechniqueBuilder {

    private String name;
    private String style;
    private String description;

    @Override
    public ITechniqueBuilder name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ITechniqueBuilder style(String style) {
        this.style = style;
        return this;
    }

    @Override
    public ITechniqueBuilder description(String description) {
        this.description = description;
        return this;
    }

    @Override
    public Technique reset() {
        this.name = "";
        this.style = "";
        this.description = "";
        return build();
    }

    @Override
    public Technique build() {
        return new Technique(name, style, description);
    }

}
