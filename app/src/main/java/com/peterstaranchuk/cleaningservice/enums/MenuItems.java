package com.peterstaranchuk.cleaningservice.enums;

import android.content.Context;

import com.peterstaranchuk.cleaningservice.R;

/**
 * Created by Peter Staranchuk.
 */

public enum MenuItems {
    ORDERS(R.string.menu_item_orders),
    CLEANERS(R.string.menu_item_cleaners),
    FEEDBACK(R.string.feedback),
    LOGOUT(R.string.menu_item_logout);

    private int menuItemNameId;

    MenuItems(int menuItemNameId) {
        this.menuItemNameId = menuItemNameId;
    }

    public String getMenuName(Context context) {
        return context.getString(menuItemNameId);
    }
}
