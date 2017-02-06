package com.peterstaranchuk.cleaningservice.view;

import android.content.Context;

/**
 * Created by Peter Staranchuk.
 */

public interface OrderScreenView {
    void setPrice(Double price);

    void setOrderInfoChangedListeners();

    void setDefaultState();

    void changeTitle();

    void highlightSelectedMode(int houseControlDrawableId, int apartmentControlDrawableId);

    long getBedroomsCount();

    long getBathroomsCount();

    double getSizeInSquareFoots();

    Context getContext();

    String getAddress();

    void orderSent();

    void errorDuringOrderSending();

    void refreshMakeAnOrderButtonState();

    void showPlaceOrderDialog();

    void setActionBar();

    void setSideMenu();

    void setControlItemsTextColors(int houseColorId, int apartmentColorId);
}
