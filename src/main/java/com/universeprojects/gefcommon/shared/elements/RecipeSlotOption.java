package com.universeprojects.gefcommon.shared.elements;

import java.util.Collection;
import java.util.List;

public interface RecipeSlotOption {
    int getOptionIndex();

    String getName();

    Collection<String> getRequiredAspects();

    List<? extends RecipeFieldRequirement> getFieldRequirements();
}
