package com.peterstaranchuk.cleaningservice.model;

import android.content.Context;

/**
 * Created by Peter Staranchuk.
 */

public class LoginScreenModel {
    private Context context;

    public LoginScreenModel(Context context) {
        this.context = context;
    }

    public boolean isDataValid(String email, String password) {
        if(!email.isEmpty() && !password.isEmpty()) {
            return true;
        }

        return false;
    }

}
