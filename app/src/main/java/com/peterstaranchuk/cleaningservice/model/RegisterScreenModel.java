package com.peterstaranchuk.cleaningservice.model;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.peterstaranchuk.cleaningservice.R;

import ru.profit_group.scorocode_sdk.Callbacks.CallbackRegisterUser;
import ru.profit_group.scorocode_sdk.scorocode_objects.User;

/**
 * Created by Peter Staranchuk.
 */

public class RegisterScreenModel {
    private Context context;

    public RegisterScreenModel(Context context) {
        this.context = context;
    }

    public boolean isDataValid(String userName, String email, String password, String repeatedPassword) {
        if(!userName.isEmpty() && !email.isEmpty() && !password.isEmpty() && !repeatedPassword.isEmpty() && isPasswordsMatch(password, repeatedPassword)) {
            return true;
        }

        return false;
    }

    public void registerNewUser(String userName, String email, String password) {
        User user = new User();

        CallbackRegisterUser callback = new CallbackRegisterUser() {
            @Override
            public void onRegisterSucceed() {
                //if all info's format correct and there is no any user with this
                //email in server (inside users collection)
                //new user with this data will be created
                ((Activity) context).finish();
            }

            @Override
            public void onRegisterFailed(String errorCode, String errorMessage) {
                //if user registration failed you can handle this case.
                // You can also see the reason why registration failed (code and message of error).
                Toast.makeText(context, R.string.errorDuringRegister, Toast.LENGTH_SHORT).show();
            }
        };
        user.register(userName, email, password, callback);
    }

    public boolean isPasswordsMatch(String password, String repeatedPassword) {
        if(password.equals(repeatedPassword)) {
            return true;
        }
        return false;
    }

    public void handleError() {

    }
}
