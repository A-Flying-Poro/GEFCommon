package com.universeprojects.gefcommon.shared.elements;

import java.util.List;

public interface RecipeSlot {
    String getSlotId();

    String getName();

    List<? extends RecipeSlotOption> getOptions();
}
