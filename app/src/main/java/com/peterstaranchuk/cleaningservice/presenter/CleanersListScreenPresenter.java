package com.peterstaranchuk.cleaningservice.presenter;

import android.os.Bundle;
import android.os.Parcelable;

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
    public static final String EXTRA_LIST_VIEW_STATE = "EXTRA_LIST_VIEW_STATE";

    private CleanersListScreenView view;
    private CleanersListScreenModel model;
    private Parcelable listViewState;

    public CleanersListScreenPresenter(CleanersListScreenView view, CleanersListScreenModel model) {
        this.view = view;
        this.model = model;
    }

    public void refreshList() {
        model.fetchCleanersList(new CallbackFindDocument() {
            @Override
            public void onDocumentFound(List<DocumentInfo> documentInfos) {
                view.refreshCleanersList(documentInfos, listViewState);
            }

            @Override
            public void onDocumentNotFound(String errorCode, String errorMessage) {
                view.showErrorToast(R.string.errorDuringDocumentLoading);
            }
        });

    }

    public void onCreate(Bundle savedInstanceState) {
        view.setActionBar();
        view.setSideMenu();

        if(savedInstanceState != null) {
            listViewState = savedInstanceState.getParcelable(EXTRA_LIST_VIEW_STATE);
        }
    }

    public void saveListState(Parcelable listState) {
        this.listViewState = listState;
    }

    public Parcelable getListState() {
        if(listViewState != null) {
            return listViewState;
        }

        return view.getCleanersListState();
    }

}
