package com.universeprojects.gefcommon;

import java.util.List;

public interface Recipe {
    String getName();

    String getDescription();

    List<? extends RecipeCategory> getCategories();
}
