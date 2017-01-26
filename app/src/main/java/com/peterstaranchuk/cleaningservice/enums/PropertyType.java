package com.peterstaranchuk.cleaningservice.enums;

import android.content.Context;

import com.peterstaranchuk.cleaningservice.R;

/**
 * Created by Peter Staranchuk.
 */

public enum PropertyType {
    HOUSE(R.id.propertyNameHouse),
    APARTMENT(R.id.propertyNameApartment);

    private int propertyNameId;

    PropertyType(int propertyNameId) {
        this.propertyNameId = propertyNameId;
    }

    public String getPropertyName(Context context) {
        return context.getString(propertyNameId);
    }
}
