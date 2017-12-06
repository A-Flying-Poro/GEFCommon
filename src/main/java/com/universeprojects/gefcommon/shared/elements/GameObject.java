package com.universeprojects.gefcommon.shared.elements;

import java.util.Collection;

public interface GameObject<K> {
    String getSchemaKind();
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

    Collection<String> getPropertyNames();

    // Aspect related access..
    Collection<String> getAspectNames();

    GameAspect<K> getAspect(String aspectName);

    boolean hasAspect(String aspectName);
}
