package com.peterstaranchuk.cleaningservice.model;

import android.content.Context;

import com.peterstaranchuk.cleaningservice.R;
import com.peterstaranchuk.cleaningservice.enums.PropertyType;
import com.peterstaranchuk.cleaningservice.helpers.DataStoreHelper;
import com.peterstaranchuk.cleaningservice.helpers.FieldHelper;

import ru.profit_group.scorocode_sdk.Callbacks.CallbackDocumentSaved;
import ru.profit_group.scorocode_sdk.scorocode_objects.Document;

/**
 * Created by Peter Staranchuk.
 */

public class OrderScreenModel {
    public static final int PRICE_ADDITIONAL_ROOM = 12;
    public static final double PRICE_FOR_SQF = 0.15;
    public static final double WRONG_PRICE = -1d;

    private Context context;

    public OrderScreenModel(Context context) {
        this.context = context;
    }

    public double getPrice(PropertyType propertyType, Double sizeInSquareFoots, long bedroomsCount, long bathroomsCount) {
        if(sizeInSquareFoots == 0 || bedroomsCount == 0 || bathroomsCount == 0) {
            return WRONG_PRICE;
        }

        double price = PRICE_FOR_SQF * sizeInSquareFoots + PRICE_ADDITIONAL_ROOM * (bedroomsCount + bathroomsCount);
        if(propertyType.equals(PropertyType.HOUSE)) {
            price *= 1.15; //if it is house we increase price for 15%
        }

        return price;
    }

    public void placeOrder(String address, double sizeInSquareFoots, long bathroomsCount, long bedroomsCount, CallbackDocumentSaved callbackDocumentSaved) {
        DataStoreHelper dataStoreHelper = new DataStoreHelper(context);

        String userId = dataStoreHelper.getUserId();
        String userName = dataStoreHelper.getUserName();

        Document document = new Document(context.getString(R.string.collectionNameOrders));

        FieldHelper fieldHelper = new FieldHelper(context);
        document.setField(fieldHelper.userIdField(), userId);
        document.setField(fieldHelper.userNameField(), userName);
        document.setField(fieldHelper.addressField(), address);
        document.setField(fieldHelper.sizeInSquareFootsField(), sizeInSquareFoots);
        document.setField(fieldHelper.bathroomsCountField(), bathroomsCount);
        document.setField(fieldHelper.bedroomsCountField(), bedroomsCount);

        document.saveDocument(callbackDocumentSaved);
    }
}
