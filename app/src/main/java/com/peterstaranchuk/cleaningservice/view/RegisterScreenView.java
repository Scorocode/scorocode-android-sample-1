package com.peterstaranchuk.cleaningservice.view;

import android.content.Context;

/**
 * Created by Peter Staranchuk.
 */

public interface RegisterScreenView {

    void setDataListeners();

    String getUserName();

    String getEmail();

    String getPassword();

    String getRepeatedPassword();

    void enableRegisterButton();

    void showToast(int errorId);

    void disableRegisterButton();

    void setInitialScreenState();

    Context getContext();

    void setActionBar();

    void finishActivity();

    void displayFilledLoginActivity(String email, String password);
}
