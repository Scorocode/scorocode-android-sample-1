package com.peterstaranchuk.cleaningservice.view;

import android.os.Parcelable;

import java.util.List;

import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

/**
 * Created by Peter Staranchuk.
 */

public interface CleanersListScreenView {

    void refreshCleanersList(List<DocumentInfo> documentInfos, Parcelable listViewState);

    void showErrorToast(int errorStringId);

    void setActionBar();

    void setSideMenu();

    Parcelable getCleanersListState();
}
