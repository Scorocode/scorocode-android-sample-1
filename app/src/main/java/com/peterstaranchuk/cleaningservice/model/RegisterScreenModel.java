package com.peterstaranchuk.cleaningservice.model;

import android.content.Context;

import com.peterstaranchuk.cleaningservice.helpers.FieldHelper;

import ru.profit_group.scorocode_sdk.Callbacks.CallbackRegisterUser;
import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;
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

    public void registerNewUser(String userName, String email, String password, CallbackRegisterUser callback) {
        User user = new User();

        DocumentInfo documentInfo = new DocumentInfo();
        documentInfo.put(new FieldHelper(context).isEmployeeField(), false);

        user.register(userName, email, password, documentInfo, callback);
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
