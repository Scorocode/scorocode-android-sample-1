package com.peterstaranchuk.cleaningservice.enums;

import android.content.Context;

import com.peterstaranchuk.cleaningservice.R;

/**
 * Created by Peter Staranchuk.
 */

public enum MenuItems {
    ORDERS(R.string.orders),
    CLEANERS(R.string.cleaners),
    FEEDBACK(R.string.feedback),
    LOGOUT(R.string.logout);

    private int menuItemNameId;

    MenuItems(int menuItemNameId) {
        this.menuItemNameId = menuItemNameId;
    }

    public String getMenuName(Context context) {
        return context.getString(menuItemNameId);
    }
}
