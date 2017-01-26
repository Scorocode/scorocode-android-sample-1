package com.peterstaranchuk.cleaningservice.model;

import android.content.Context;

import com.peterstaranchuk.cleaningservice.R;
import com.peterstaranchuk.cleaningservice.enums.PropertyType;
import com.peterstaranchuk.cleaningservice.helpers.FieldHelper;

import ru.profit_group.scorocode_sdk.Callbacks.CallbackDocumentSaved;
import ru.profit_group.scorocode_sdk.scorocode_objects.Document;

/**
 * Created by Peter Staranchuk.
 */

public class OrderScreenModel {
    public static final int PRICE_ADDITIONAL_ROOM = 12;
    public static final double PRICE_FOR_SQF = 0.15;

    private Context context;

    public OrderScreenModel(Context context) {
        this.context = context;
    }

    public Double getPrice(PropertyType propertyType, Double sizeInSquareFoots, long bedroomsCount, long bathroomsCount) {
        double price = PRICE_FOR_SQF * sizeInSquareFoots + PRICE_ADDITIONAL_ROOM * (bedroomsCount + bathroomsCount);
        if(propertyType.equals(PropertyType.HOUSE)) {
            price *= 1.15; //if it is house we increase price for 15%
        }

        return price;
    }

    public void placeOrder(String address, double sizeInSquareFoots, long bathroomsCount, long bedroomsCount, CallbackDocumentSaved callbackDocumentSaved) {
            //TODO getUserName;
        String userName = "add username logic";

        Document document = new Document(context.getString(R.string.collectionNameOrders));

        FieldHelper fieldHelper = new FieldHelper(context);
        document.setField(fieldHelper.userNameField(), userName);
        document.setField(fieldHelper.addressField(), address);
        document.setField(fieldHelper.sizeInSquareFootsField(), sizeInSquareFoots);
        document.setField(fieldHelper.bathroomsCountField(), bathroomsCount);
        document.setField(fieldHelper.bedroomsCountField(), bedroomsCount);

        document.saveDocument(callbackDocumentSaved);
    }
}
