package com.peterstaranchuk.cleaningservice.model;

import android.content.Context;

import com.peterstaranchuk.cleaningservice.R;

import ru.profit_group.scorocode_sdk.Callbacks.CallbackFindDocument;
import ru.profit_group.scorocode_sdk.scorocode_objects.Query;

/**
 * Created by Peter Staranchuk.
 */

public class CleanersListScreenModel {
    private Context context;

    public CleanersListScreenModel(Context context) {
        this.context = context;
    }

    public void fetchCleanersList(CallbackFindDocument callback) {
        //To get all documents from collection you should:
        //1.Create new object of Query class
        Query query = new Query(context.getString(R.string.collectionNameCleaners));

        //2.Don't specify any criteria of search (so it will searching for all documents).
        //3.Use findDocument() method of Query class
        query.findDocuments(callback);
    }
}
