package com.universeprojects.gefcommon.shared.elements;

import java.util.List;

public interface StoreOrderRequirement {
     String getName ();

     String getIconName();

     String getKey ();

     Long getQuantity();

     List getFieldFilters ();
}
