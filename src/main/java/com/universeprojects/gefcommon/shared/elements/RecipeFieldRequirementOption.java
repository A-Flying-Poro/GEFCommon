package com.universeprojects.gefcommon.shared.elements;

public interface RecipeFieldRequirementOption {
    String getAspect();

    String getField();

    RecipeFieldRequirementOperator getOperator();

    Object getValue();
}
