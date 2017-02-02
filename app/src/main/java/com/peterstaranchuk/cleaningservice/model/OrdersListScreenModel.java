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
}
