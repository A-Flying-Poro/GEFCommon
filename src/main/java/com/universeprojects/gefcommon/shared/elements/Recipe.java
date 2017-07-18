package com.universeprojects.gefcommon.shared.elements;

import java.util.List;

public interface Recipe {
    String getName();

    String getDescription();

    List<? extends RecipeCategory> getCategories();
}
