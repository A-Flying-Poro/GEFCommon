package com.universeprojects.gefcommon.shared.elements;

import java.util.Map;

public interface StoreOrder {
    enum StoreOrderState {
        Active,
        Inactive,
        Completed
    }

    String getStoreOrderKey();

    String getCharacterKey ();

    Long getMaxTransactions ();

    Long getTransactions ();

    /**
     * Returns a map of the StoreOrderRequirements used in the store order's offer
     * @return Map of Strings to StoreOrderRequirements of the offer
     */
    Map<String, StoreOrderRequirement> getOfferRequirements ();

    /**
     * Returns a map of the StoreOrderRequirements used in the store order's payment
     * @return Map of Strings to StoreOrderRequirements of the payment
     */
    Map<String, StoreOrderRequirement> getPaymentRequirements ();

    StoreOrderState getStoreOrderState ();
}
