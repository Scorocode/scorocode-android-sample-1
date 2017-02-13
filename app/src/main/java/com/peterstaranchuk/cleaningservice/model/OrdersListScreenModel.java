package com.peterstaranchuk.cleaningservice.model;

import android.content.Context;

import com.peterstaranchuk.cleaningservice.R;
import com.peterstaranchuk.cleaningservice.helpers.DataStoreHelper;
import com.peterstaranchuk.cleaningservice.helpers.FieldHelper;

import ru.profit_group.scorocode_sdk.Callbacks.CallbackFindDocument;
import ru.profit_group.scorocode_sdk.scorocode_objects.Query;

/**
 * Created by Peter Staranchuk.
 */

public class OrdersListScreenModel {
    public static final int STATUS_PLACED = 0;
    public static final int STATUS_ACCEPTED = 1;
    public static final int STATUS_IN_PROGRESS = 2;
    public static final int STATUS_COMPLETE = 3;
    private Context context;

    public OrdersListScreenModel(Context context) {
        this.context = context;
    }

    public void fetchOrders(CallbackFindDocument callback) {
        FieldHelper fieldHelper = new FieldHelper(context);
        DataStoreHelper dataStoreHelper = new DataStoreHelper(context);

        Query query = new Query(context.getString(R.string.collectionNameOrders));
        query.equalTo(fieldHelper.userIdField(), dataStoreHelper.getUserId());
        query.descending(new FieldHelper(context).createdAtField());

        query.findDocuments(callback);
    }

    public static String getOrderStatusFrom(Context context, Integer orderStatusCode) {
        switch (orderStatusCode) {
            case STATUS_PLACED:
                return context.getString(R.string.status_placed);

            case STATUS_ACCEPTED:
                return context.getString(R.string.status_accepted);

            case STATUS_IN_PROGRESS:
                return context.getString(R.string.status_inprogress);

            case STATUS_COMPLETE:
                return context.getString(R.string.status_completed);
        }

        return context.getString(R.string.status_error);
    }
}
