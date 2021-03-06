package com.peterstaranchuk.cleaningservice.dagger2modules;

import com.peterstaranchuk.cleaningservice.dagger2_scopes.SingletonScope;
import com.peterstaranchuk.cleaningservice.model.OrderScreenModel;
import com.peterstaranchuk.cleaningservice.presenter.OrderScreenPresenter;
import com.peterstaranchuk.cleaningservice.view.OrderScreenView;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.functions.Action1;

/**
 * Created by Peter Staranchuk.
 */

@Module
public class OrderScreenActionModule {
    public static final String ACTION_STATE_CHANGED = "stateChangedAction";
    public static final String ACTION_SET_HOUSE_PROPERTY_TYPE = "setHousePropertyTypeAction";
    public static final String ACTION_SET_APARTMENT_PROPERTY_TYPE = "setApartmentPropertyTypeAction";
    public static final String ACTION_REFRESH_BUTTON_STATE = "refreshButtonStateAction";
    private final OrderScreenView orderScreenView;

    public OrderScreenActionModule(OrderScreenView orderScreenView) {
        this.orderScreenView = orderScreenView;
    }

    @Provides
    @SingletonScope
    public OrderScreenPresenter presenter() {
        return new OrderScreenPresenter(orderScreenView, new OrderScreenModel(orderScreenView.getContext()));
    }

    @Named(ACTION_STATE_CHANGED)
    @Provides
    @SingletonScope
    public Action1<CharSequence> getStateChangedAction(OrderScreenPresenter presenter) {
        return presenter.getStateChangedAction();
    }

    @Named(ACTION_SET_HOUSE_PROPERTY_TYPE)
    @Provides
    @SingletonScope
    public Action1<Void> getActionSetHousePropertyType(OrderScreenPresenter presenter){
        return presenter.getActionSetHousePropertyType();
    }

    @Named(ACTION_SET_APARTMENT_PROPERTY_TYPE)
    @Provides
    @SingletonScope
    public Action1<Void> getActionSetApartmentPropertyType(OrderScreenPresenter presenter) {
        return presenter.getActionSetApartmentPropertyType();
    }

    @Named(ACTION_REFRESH_BUTTON_STATE)
    @Provides
    @SingletonScope
    public Action1<CharSequence> getActionRefreshButtonState(OrderScreenPresenter presenter) {
        return presenter.getActionRefreshButtonState();
    }
}
