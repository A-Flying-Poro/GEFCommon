package com.universeprojects.gefcommon;

import java.util.List;

public interface RecipeCategory {
    String getName();

    boolean isRequired();

    List<? extends RecipeSlot> getSlots();
}
