package com.peterstaranchuk.cleaningservice.presenter;

import com.peterstaranchuk.cleaningservice.model.OrderScreenModel;
import com.peterstaranchuk.cleaningservice.view.OrderScreenView;

/**
 * Created by Peter Staranchuk.
 */

public class OrderScreenPresenter {
    private OrderScreenView view;
    private OrderScreenModel model;

    public OrderScreenPresenter(OrderScreenView view, OrderScreenModel model) {
        this.view = view;
        this.model = model;
    }
}
