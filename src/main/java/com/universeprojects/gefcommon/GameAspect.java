package com.universeprojects.gefcommon;

public interface GameAspect {
    // Standard field access..
    Object getProperty(String fieldName);

    void setProperty(String fieldName, Object value);

    String getName();

    GameObject getGameObject();


}
