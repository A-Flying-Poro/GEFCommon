package com.universeprojects.gefcommon.shared.elements;

import java.util.List;

public interface RecipeCategory {
    String getName();

    boolean isRequired();

    List<? extends RecipeSlot> getSlots();
}
