package com.parcial.builder;

import com.parcial.model.Technique;

public interface ITechniqueBuilder {

    ITechniqueBuilder name(String name);

    ITechniqueBuilder style(String style);

    ITechniqueBuilder description(String description);

    Technique reset();

    Technique build();
}
