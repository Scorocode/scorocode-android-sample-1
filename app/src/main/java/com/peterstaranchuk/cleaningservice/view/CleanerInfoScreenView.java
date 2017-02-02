package com.peterstaranchuk.cleaningservice.view;

import android.content.Intent;

import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

/**
 * Created by Peter Staranchuk.
 */

public interface CleanerInfoScreenView {
    void setCleanerInfo(DocumentInfo cleanerInfo);

    Intent getIntent();
}
