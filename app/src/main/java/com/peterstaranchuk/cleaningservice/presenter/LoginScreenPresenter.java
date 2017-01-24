package com.peterstaranchuk.cleaningservice.presenter;

import com.peterstaranchuk.cleaningservice.model.LoginScreenModel;
import com.peterstaranchuk.cleaningservice.view.LoginScreenView;

/**
 * Created by Peter Staranchuk.
 */

public class LoginScreenPresenter {
    private LoginScreenView view;
    private LoginScreenModel model;

    public LoginScreenPresenter(LoginScreenView view, LoginScreenModel model) {
        this.view = view;
        this.model = model;
    }

    public void onLoginButtonClicked() {
        String email = view.getEmail();
        String password = view.getPassword();

        if(model.isDataValid(email, password)) {
            model.loginUser(email, password);
        } else {
            model.handleError();
        }
    }

    public void onCreateScreen() {
        view.disableLoginButton();
        view.setDataListeners();
    }

    public void onRegisterButtonClicked() {
        view.openRegisterScreen();
    }
}
