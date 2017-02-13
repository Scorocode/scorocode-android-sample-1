package com.peterstaranchuk.cleaningservice.view;

import android.content.Intent;

import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

/**
 * Created by Peter Staranchuk.
 */

public interface OrdersDetailScreenView {

    void setOrderDetails(DocumentInfo orderDetails);

    void showError(int error);

    Intent getIntent();

    void finish();

    void setSideMenu();

    void disableRemoveButton();

    void setActionBar();
}
