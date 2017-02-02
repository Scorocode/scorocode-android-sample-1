package com.peterstaranchuk.cleaningservice.view;

import java.util.List;

import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

/**
 * Created by Peter Staranchuk.
 */

public interface OrdersListScreenView {

    void setActionBar();

    void setOrdersList(List<DocumentInfo> documentInfos);

    void showError(int error);

}
