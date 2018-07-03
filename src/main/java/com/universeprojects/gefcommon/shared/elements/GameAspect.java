package com.universeprojects.gefcommon.shared.elements;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface GameAspect<K> {
    // Standard field access..
    Object getProperty(String fieldName);

    void setProperty(String fieldName, Object value);

    String getName();

    GameObject<K> getGameObject();

    Collection<String> getPropertyNames();

    void setSubObjectSingle(String name, GameObject<K> object);

    void setSubObjectList(String name, List<GameObject<K>> object);

    void setSubObjectMap(String name, Map<?, GameObject<K>> object);

    Map<String, GameObject<K>> getSubObjectsSingle();

    Map<String, List<GameObject<K>>> getSubObjectsList();

    Map<String, Map<?, GameObject<K>>> getSubObjectsMap();
}
