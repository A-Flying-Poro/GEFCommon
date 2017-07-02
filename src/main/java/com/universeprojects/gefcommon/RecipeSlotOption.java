package com.universeprojects.gefcommon;

import java.util.List;

public interface RecipeSlotOption {
    String getOptionId();

    String getName();

    List<String> getRequiredAspects();

    List<RecipeFieldRequirement> getFieldRequirements();
}
