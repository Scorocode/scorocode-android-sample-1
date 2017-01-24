package com.peterstaranchuk.cleaningservice.view;

import android.content.Context;

/**
 * Created by Peter Staranchuk.
 */

public interface LoginScreenView {

    String getEmail();

    String getPassword();

    void loginUser();

    void showError();

    Context getContext();

    void enableLoginButton();

    void disableLoginButton();

    void setDataListeners();

    void openRegisterScreen();
}
