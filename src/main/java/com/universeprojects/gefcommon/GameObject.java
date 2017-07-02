package com.universeprojects.gefcommon;

import java.util.Collection;

public interface GameObject<K> {
    K getKey();

    // Special field access..
    String getName();

    Long getQuantity();

    void setQuantity(Long value);

    Long getDurability();

    void setDurability(Long value);

    // Standard field access..
    Object getProperty(String fieldName);

    void setProperty(String fieldName, Object value);


    // Aspect related access..
    Collection<String> getAspectNames();

    GameAspect getAspect(String aspectName);

    boolean hasAspect(String aspectName);
}
