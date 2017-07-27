package com.universeprojects.gefcommon.shared.elements;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class GameObjectMapWrapper implements Map<String, Object> {

    private final GameObject<?> gameObject;

    public GameObjectMapWrapper(GameObject<?> gameObject) {
        this.gameObject = gameObject;
    }

    @Override
    public int size() {
        int size = 0;
        for(String aspectName : gameObject.getAspectNames()) {
            GameAspect<?> aspect = gameObject.getAspect(aspectName);
            size += aspect.getPropertyNames().size();
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        for(String aspectName : gameObject.getAspectNames()) {
            GameAspect<?> aspect = gameObject.getAspect(aspectName);
            if(!aspect.getPropertyNames().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    String[] splitKey(String key) {
        String[] parts = key.split(":");
        if(parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new IllegalArgumentException("Bad key: "+key);
        }
        return parts;
    }

    @Override
    public boolean containsKey(Object key) {
        if(!(key instanceof String)) return false;
        String[] keyParts = splitKey((String) key);
        String aspectName = keyParts[0];
        GameAspect<?> aspect = gameObject.getAspect(aspectName);
        if(aspect == null) return false;
        String propertyName = keyParts[1];
        return aspect.getPropertyNames().contains(propertyName);
    }

    @Override
    public boolean containsValue(Object value) {
        for(String aspectName : gameObject.getAspectNames()) {
            GameAspect<?> aspect = gameObject.getAspect(aspectName);
            for(String propertyName : aspect.getPropertyNames()) {
                Object propertyValue = aspect.getProperty(propertyName);
                if(Objects.equals(propertyValue, value)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Object get(Object key) {
        if(!(key instanceof String)) return null;
        String[] keyParts = splitKey((String) key);
        String aspectName = keyParts[0];
        GameAspect<?> aspect = gameObject.getAspect(aspectName);
        if(aspect == null) return false;
        String propertyName = keyParts[1];
        return aspect.getProperty(propertyName);
    }

    @Override
    public Object put(String key, Object value) {
        String[] keyParts = splitKey(key);
        String aspectName = keyParts[0];
        GameAspect<?> aspect = gameObject.getAspect(aspectName);
        if(aspect == null) return null;
        String propertyName = keyParts[1];
        Object oldValue = aspect.getProperty(propertyName);
        aspect.setProperty(propertyName, value);
        return oldValue;
    }

    @Override
    public Object remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends String, ?> map) {
        for(Entry<? extends String, ?> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    private Map<String, Object> createHelperMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        for(String aspectName : gameObject.getAspectNames()) {
            GameAspect<?> aspect = gameObject.getAspect(aspectName);
            for(String propertyName : aspect.getPropertyNames()) {
                Object propertyValue = aspect.getProperty(propertyName);
                map.put(aspectName+":"+propertyName, propertyValue);
            }
        }
        return Collections.unmodifiableMap(map);
    }

    @Override
    public Set<String> keySet() {
        return createHelperMap().keySet();
    }

    @Override
    public Collection<Object> values() {
        return createHelperMap().values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return createHelperMap().entrySet();
    }
}
