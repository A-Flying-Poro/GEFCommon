package com.universeprojects.gefcommon;

public interface Skill {
    Long getId();

    String getName();

    String getDescription();

    String getIcon();

    Long getBaseTimeToRunSecs();

    Double getBaseSuccessRate();
}
