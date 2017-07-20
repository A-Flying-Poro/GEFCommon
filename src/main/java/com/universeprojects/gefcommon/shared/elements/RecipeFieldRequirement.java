package com.universeprojects.gefcommon.shared.elements;

import java.util.Collection;

public interface RecipeFieldRequirement {
    Collection<? extends RecipeFieldRequirementOption> getFieldOptions();
}
