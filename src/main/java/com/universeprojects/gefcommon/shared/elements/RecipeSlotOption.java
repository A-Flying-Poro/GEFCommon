package com.universeprojects.gefcommon.shared.elements;

import java.util.Collection;

public interface RecipeSlotOption {
    String getName();

    Collection<String> getRequiredAspects();

    Collection<? extends RecipeFieldRequirement> getFieldRequirements();
}
