package com.peterstaranchuk.cleaningservice.enums;

import android.content.Context;

import com.peterstaranchuk.cleaningservice.R;

/**
 * Created by Peter Staranchuk.
 */

public enum MenuItems {
    ORDERS(R.string.menu_item_orders, R.drawable.ic_assignment_black_24dp),
    CLEANERS(R.string.menu_item_cleaners, R.drawable.ic_perm_identity_black_24dp),
    FEEDBACK(R.string.feedback, R.drawable.ic_thumbs_up_down_black_24dp),
    LOGOUT(R.string.menu_item_logout, R.drawable.ic_reply_black_24dp);

    private int menuItemNameId;
    private int iconId;

    MenuItems(int menuItemNameId, int iconId) {
        this.menuItemNameId = menuItemNameId;
        this.iconId = iconId;
    }

    public String getMenuItemName(Context context) {
        return context.getString(menuItemNameId);
    }

    public int getMenuItemIcon() {
        return iconId;
    }
}
