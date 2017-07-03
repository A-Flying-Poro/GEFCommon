package com.universeprojects.gefcommon;

import java.util.List;

public interface RecipeSlot {
    String getSlotId();

    String getName();

    List<? extends RecipeSlotOption> getOptions();
}
