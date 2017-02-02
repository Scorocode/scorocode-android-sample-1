package com.peterstaranchuk.cleaningservice.presenter;

import com.peterstaranchuk.cleaningservice.model.CleanerInfoScreenModel;
import com.peterstaranchuk.cleaningservice.view.CleanerInfoScreenView;

import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

/**
 * Created by Peter Staranchuk.
 */

public class CleanerInfoScreenPresenter {
    private CleanerInfoScreenView view;
    private CleanerInfoScreenModel model;

    public CleanerInfoScreenPresenter(CleanerInfoScreenView view, CleanerInfoScreenModel model) {
        this.view = view;
        this.model = model;
    }

    public void onCreate() {
        DocumentInfo cleanerInfo = model.getCleanerInfo(view.getIntent());
        view.setCleanerInfo(cleanerInfo);
    }
}
