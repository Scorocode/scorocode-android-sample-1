package com.peterstaranchuk.cleaningservice.model;

import android.content.Context;

import com.peterstaranchuk.cleaningservice.R;
import com.peterstaranchuk.cleaningservice.dagger2components.DaggerLoginModelComponent;
import com.peterstaranchuk.cleaningservice.dagger2modules.LoginCallbackModule;
import com.peterstaranchuk.cleaningservice.helpers.DataStoreHelper;

import javax.inject.Inject;

import ru.profit_group.scorocode_sdk.Callbacks.CallbackLoginUser;
import ru.profit_group.scorocode_sdk.scorocode_objects.User;

/**
 * Created by Peter Staranchuk.
 */

public class LoginScreenModel {
    @Inject CallbackLoginUser callbackLoginUser;

    private Context context;

    public LoginScreenModel(Context context) {
        this.context = context;

        DaggerLoginModelComponent.builder()
                .loginCallbackModule(new LoginCallbackModule(context))
                .build()
                .inject(this);
    }

    public boolean isDataValid(String email, String password) {
        if (!email.isEmpty() && !password.isEmpty()) {
            return true;
        }

        return false;
    }

    public void loginUser(String email, String password) {
        User user = new User();
        user.login(email, password, callbackLoginUser);
    }

    public void handleError() {
        callbackLoginUser.onLoginFailed("", context.getString(R.string.wrong_data_error));
    }

    public void clearUserData() {
        new DataStoreHelper(context).clearUserData();
    }
}
