package com.peterstaranchuk.cleaningservice.view;

import android.content.Context;

/**
 * Created by Peter Staranchuk.
 */

public interface LoginScreenView {

    String getEmail();

    String getPassword();

    Context getContext();

    void enableLoginButton();

    void disableLoginButton();

    void setDataListeners();

    void openRegisterScreen();

    void setItemsVisibility();

    void setActionBar();

    void setEmailAndPasswordAfterRegistration();
}
