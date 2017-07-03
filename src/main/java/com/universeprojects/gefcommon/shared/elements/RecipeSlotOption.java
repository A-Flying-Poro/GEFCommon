package com.universeprojects.gefcommon.shared.elements;

import java.util.List;

public interface RecipeSlotOption {
    int getOptionIndex();

    String getName();

    List<String> getRequiredAspects();

    List<? extends RecipeFieldRequirement> getFieldRequirements();
}
