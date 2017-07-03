package com.universeprojects.gefcommon.shared.elements;

public interface RecipeFieldRequirement {
    String getAspect();

    String getField();

    RecipeFieldRequirementOperator getOperator();

    Object getValue();
}
