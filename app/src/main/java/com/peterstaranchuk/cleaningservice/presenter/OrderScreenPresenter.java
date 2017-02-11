package com.peterstaranchuk.cleaningservice.presenter;

import android.os.Bundle;

import com.peterstaranchuk.cleaningservice.R;
import com.peterstaranchuk.cleaningservice.enums.PropertyType;
import com.peterstaranchuk.cleaningservice.model.OrderScreenModel;
import com.peterstaranchuk.cleaningservice.view.OrderScreenView;

import ru.profit_group.scorocode_sdk.Callbacks.CallbackDocumentSaved;
import rx.functions.Action1;

/**
 * Created by Peter Staranchuk.
 */

public class OrderScreenPresenter {
    private OrderScreenView view;
    private OrderScreenModel model;
    private PropertyType propertyType;
    private double price;

    public OrderScreenPresenter(OrderScreenView view, OrderScreenModel model) {
        this.view = view;
        this.model = model;
    }

    public void onCreate(Bundle savedInstanceState) {
        view.setDefaultState();
        view.setOrderInfoChangedListeners();
        view.setActionBar();
        view.setSideMenu();

        if(savedInstanceState != null) {
            view.restoreCounterState(savedInstanceState);
            view.restorePropertyType(savedInstanceState);
        }
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
        view.highlightSelectedMode(getHouseControlDrawable(), getApartmentControlDrawable());
        view.setControlItemsTextColors(getHouseControlTextColor(), getApartmentControlTextColor());
        view.changeTitle();

        recalculatePrice();
    }

    private int getApartmentControlTextColor() {
        return propertyType.equals(PropertyType.APARTMENT)? android.R.color.white : android.R.color.black;
    }

    private int getHouseControlTextColor() {
        return propertyType.equals(PropertyType.HOUSE)? android.R.color.white : android.R.color.black;
    }

    private void recalculatePrice() {
        view.setPrice(getPrice());
    }

    private int getApartmentControlDrawable() {
        return getPropertyType().equals(PropertyType.APARTMENT)? R.drawable.control_border : R.drawable.control_border_pressed;
    }

    private int getHouseControlDrawable() {
        return getPropertyType().equals(PropertyType.HOUSE)? R.drawable.control_border : R.drawable.control_border_pressed;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public Action1<CharSequence> getStateChangedAction() {
        return new Action1<CharSequence>() {
            @Override
            public void call(CharSequence s) {
                recalculatePrice();
            }
        };
    }

    public Action1<Void> getActionSetHousePropertyType() {
        return new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                setPropertyType(PropertyType.HOUSE);
            }
        };
    }

    public Action1<Void> getActionSetApartmentPropertyType() {
        return new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                setPropertyType(PropertyType.APARTMENT);
            }
        };
    }

    public void onMakeOrderButtonClicked() {
        CallbackDocumentSaved callbackDocumentSaved = new CallbackDocumentSaved() {
            @Override
            public void onDocumentSaved() {
                view.orderSent();
            }

            @Override
            public void onDocumentSaveFailed(String errorCode, String errorMessage) {
                view.errorDuringOrderSending();
            }
        };

        view.showPlaceOrderDialog();
        model.placeOrder(view.getAddress(), view.getPhone(), getPropertyType(), view.getSizeInSquareFoots(),
                view.getBathroomsCount(), view.getBedroomsCount(), callbackDocumentSaved);
    }

    public double getPrice() {
        this.price = model.getPrice(getPropertyType(), view.getSizeInSquareFoots(), view.getBedroomsCount(), view.getBathroomsCount());
        return this.price;
    }

    public void storeCounterState(Bundle outState) {
        view.storeCounterState(outState);
    }

    public void storePropertyType(Bundle bundle) {
        view.storePropertyType(bundle);
    }

    public Action1<? super CharSequence> getActionRefreshButtonState() {
        return new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                view.refreshMakeAnOrderButtonState();
            }
        };
    }
}
