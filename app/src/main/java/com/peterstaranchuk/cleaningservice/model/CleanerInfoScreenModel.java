package com.peterstaranchuk.cleaningservice.model;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

/**
 * Created by Peter Staranchuk.
 */

public class CleanerInfoScreenModel {
    public static final String EXTRA_CLEANER_INFO = "EXTRA_CLEANER_INFO";

    private Context context;

    public CleanerInfoScreenModel(Context context) {
        this.context = context;
    }

    @NonNull
    public DocumentInfo getCleanerInfo(Intent intent) {
        if(intent != null) {
            DocumentInfo documentInfo = (DocumentInfo) intent.getSerializableExtra(EXTRA_CLEANER_INFO);
            return documentInfo != null? documentInfo : new DocumentInfo();
        } else {
            return new DocumentInfo();
        }
    }
}
