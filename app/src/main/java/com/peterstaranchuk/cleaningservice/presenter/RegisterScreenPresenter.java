package com.peterstaranchuk.cleaningservice.presenter;

import com.peterstaranchuk.cleaningservice.model.RegisterScreenModel;
import com.peterstaranchuk.cleaningservice.view.RegisterScreenView;

import rx.functions.Action1;

/**
 * Created by Peter Staranchuk.
 */

public class RegisterScreenPresenter {
    private RegisterScreenView view;
    private RegisterScreenModel model;
    private Action1<CharSequence> action;

    public RegisterScreenPresenter(final RegisterScreenView view, final RegisterScreenModel model) {
        this.view = view;
        this.model = model;

        action = new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                if(model.isDataValid(view.getUserName(), view.getEmail(), view.getPassword(), view.getRepeatedPassword())) {
                    view.enableRegisterButton();
                } else {
                    if(!view.getPassword().isEmpty() && !view.getRepeatedPassword().isEmpty()
                            && !model.isPasswordsMatch(view.getPassword(), view.getRepeatedPassword())) {
                        view.showError();
                    }
                    view.disableRegisterButton();
                }
            }
        };
    }

    public void onCreateScreen() {
        view.setDataListeners();
        view.setInitialScreenState();
    }

    public void onRegisterButtonPressed() {
        String userName = view.getUserName();
        String email = view.getEmail();
        String password = view.getPassword();
        String repeatedPassword = view.getRepeatedPassword();

        if(model.isDataValid(userName, email, password, repeatedPassword)) {
            model.registerNewUser(userName, email, password);
        } else {
            model.handleError();
        }
    }

    public Action1<CharSequence> getInputCheckCallback() {
        return action;
    }
}
