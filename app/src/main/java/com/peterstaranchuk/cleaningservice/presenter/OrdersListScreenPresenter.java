package com.peterstaranchuk.cleaningservice.presenter;

import com.peterstaranchuk.cleaningservice.R;
import com.peterstaranchuk.cleaningservice.model.OrdersListScreenModel;
import com.peterstaranchuk.cleaningservice.view.OrdersListScreenView;

import java.util.List;

import ru.profit_group.scorocode_sdk.Callbacks.CallbackFindDocument;
import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

/**
 * Created by Peter Staranchuk.
 */

public class OrdersListScreenPresenter {
    private OrdersListScreenView view;
    private OrdersListScreenModel model;

    public OrdersListScreenPresenter(OrdersListScreenView view, OrdersListScreenModel model) {
        this.view = view;
        this.model = model;
    }

    public void onCreate() {
        view.setActionBar();
        view.setSideMenu();
    }

    public void refreshOrdersList() {
        model.fetchOrders(new CallbackFindDocument() {
            @Override
            public void onDocumentFound(List<DocumentInfo> documentInfos) {
                view.setOrdersList(documentInfos);
            }

            @Override
            public void onDocumentNotFound(String errorCode, String errorMessage) {
                view.showError(R.string.errorDuringDocumentLoading);
            }
        });
    }
}
