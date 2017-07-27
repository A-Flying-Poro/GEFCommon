package com.universeprojects.gefcommon.shared.elements;

import java.util.Collection;

public interface GameAspect<K> {
    // Standard field access..
    Object getProperty(String fieldName);

    void setProperty(String fieldName, Object value);

    String getName();

    GameObject<K> getGameObject();

    Collection<String> getPropertyNames();
}
