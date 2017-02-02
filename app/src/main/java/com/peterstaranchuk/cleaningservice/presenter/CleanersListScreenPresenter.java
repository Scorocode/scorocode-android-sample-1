package com.peterstaranchuk.cleaningservice.presenter;

import com.peterstaranchuk.cleaningservice.R;
import com.peterstaranchuk.cleaningservice.model.CleanersListScreenModel;
import com.peterstaranchuk.cleaningservice.view.CleanersListScreenView;

import java.util.List;

import ru.profit_group.scorocode_sdk.Callbacks.CallbackFindDocument;
import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

/**
 * Created by Peter Staranchuk.
 */

public class CleanersListScreenPresenter {
    private CleanersListScreenView view;
    private CleanersListScreenModel model;

    public CleanersListScreenPresenter(CleanersListScreenView view, CleanersListScreenModel model) {
        this.view = view;
        this.model = model;
    }

    public void refreshList() {
        model.fetchCleanersList(new CallbackFindDocument() {
            @Override
            public void onDocumentFound(List<DocumentInfo> documentInfos) {
                view.refreshCleanersList(documentInfos);
            }

            @Override
            public void onDocumentNotFound(String errorCode, String errorMessage) {
                view.showErrorToast(R.string.errorDuringDocumentLoading);
            }
        });

    }

    public void onCreate() {
        view.setActionBar();
        view.setSideMenu();
    }
}
