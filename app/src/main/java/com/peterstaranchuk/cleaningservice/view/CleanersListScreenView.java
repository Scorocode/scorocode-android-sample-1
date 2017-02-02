package com.peterstaranchuk.cleaningservice.view;

import java.util.List;

import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

/**
 * Created by Peter Staranchuk.
 */

public interface CleanersListScreenView {
    void refreshCleanersList(List<DocumentInfo> documentInfos);

    void showErrorToast(int errorStringId);
}
