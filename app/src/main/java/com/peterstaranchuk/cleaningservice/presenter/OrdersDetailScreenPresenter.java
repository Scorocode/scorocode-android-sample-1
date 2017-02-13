package com.peterstaranchuk.cleaningservice.presenter;

import com.peterstaranchuk.cleaningservice.R;
import com.peterstaranchuk.cleaningservice.model.OrdersDetailScreenModel;
import com.peterstaranchuk.cleaningservice.model.OrdersListScreenModel;
import com.peterstaranchuk.cleaningservice.view.OrdersDetailScreenView;

import ru.profit_group.scorocode_sdk.Callbacks.CallbackRemoveDocument;
import ru.profit_group.scorocode_sdk.Responses.data.ResponseRemove;
import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

/**
 * Created by Peter Staranchuk.
 */

public class OrdersDetailScreenPresenter {
    private OrdersDetailScreenView view;
    private OrdersDetailScreenModel model;

    public OrdersDetailScreenPresenter(OrdersDetailScreenView view, OrdersDetailScreenModel model) {
        this.view = view;
        this.model = model;
    }

    public void onCreate() {
        DocumentInfo order = model.extractOrderInfo(view.getIntent());
        view.setOrderDetails(order);
        view.setSideMenu();
        view.setActionBar();

        if(model.getOrderStatus(order) != OrdersListScreenModel.STATUS_PLACED) {
            view.disableRemoveButton();
        }
    }

    public void onDeleteOrderButtonClicked() {
        final DocumentInfo order = model.extractOrderInfo(view.getIntent());

        OrdersDetailScreenModel.OnStatusChecked callback = new OrdersDetailScreenModel.OnStatusChecked() {
            @Override
            public void onAvailable() {
                model.removeOrder(order, new CallbackRemoveDocument() {
                    @Override
                    public void onRemoveSucceed(ResponseRemove responseRemove) {
                        view.finish();
                    }

                    @Override
                    public void onRemoveFailed(String errorCode, String errorMessage) {
                        view.showError(R.string.errorDuringOrderDeletion);
                    }
                });
            }

            @Override
            public void onNotAvailable() {
                view.showError(R.string.errorDuringOrderDeletion);
            }
        };

        model.checkIfNotAccepted(order, callback);
    }


}
