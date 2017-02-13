package com.peterstaranchuk.cleaningservice.model;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.peterstaranchuk.cleaningservice.R;

import ru.profit_group.scorocode_sdk.Callbacks.CallbackGetDocumentById;
import ru.profit_group.scorocode_sdk.Callbacks.CallbackRemoveDocument;
import ru.profit_group.scorocode_sdk.scorocode_objects.Document;
import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;
import ru.profit_group.scorocode_sdk.scorocode_objects.Query;

/**
 * Created by Peter Staranchuk.
 */

public class OrdersDetailScreenModel {
    public static final String EXTRA_ORDER_INFO = "EXTRA_ORDER_INFO";
    private Context context;

    public OrdersDetailScreenModel(Context context) {
        this.context = context;
    }

    @NonNull
    public DocumentInfo extractOrderInfo(Intent intent) {
        if(intent != null) {
            return (DocumentInfo) intent.getSerializableExtra(EXTRA_ORDER_INFO);
        }
        return new DocumentInfo();
    }

    public void checkIfNotAccepted(DocumentInfo order, final OnStatusChecked callback) {
        Document document = new Document(context.getString(R.string.collectionNameOrders));
        document.getDocumentById(order.getId(), new CallbackGetDocumentById() {
            @Override
            public void onDocumentFound(DocumentInfo documentInfo) {
                int status = documentInfo.getInteger(context.getString(R.string.fieldOrderStatus));
                if(status == OrdersListScreenModel.STATUS_PLACED) {
                    callback.onAvailable();
                } else {
                    callback.onNotAvailable();
                }
            }

            @Override
            public void onDocumentNotFound(String errorCode, String errorMessage) {
                callback.onNotAvailable();
            }
        });
    }

    public int getOrderStatus(DocumentInfo order) {
        return order.getInteger(context.getString(R.string.fieldOrderStatus));
    }

    public interface OnStatusChecked {
        void onAvailable();
        void onNotAvailable();
    }

    public void removeOrder(DocumentInfo order, CallbackRemoveDocument callback) {
        Query query = new Query(context.getString(R.string.collectionNameOrders));
        query.equalTo("_id", order.getId());
        query.removeDocument(callback);
    }
}
