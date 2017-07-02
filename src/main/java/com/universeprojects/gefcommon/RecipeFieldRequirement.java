package com.universeprojects.gefcommon;

public interface RecipeFieldRequirement {
    String getAspect();

    String getField();

    RecipeFieldRequirementOperator getOperator();

    Object getValue();
}
