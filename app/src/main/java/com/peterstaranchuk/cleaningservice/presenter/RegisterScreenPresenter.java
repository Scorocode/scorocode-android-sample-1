package com.peterstaranchuk.cleaningservice.presenter;

import com.peterstaranchuk.cleaningservice.R;
import com.peterstaranchuk.cleaningservice.model.RegisterScreenModel;
import com.peterstaranchuk.cleaningservice.view.RegisterScreenView;

import ru.profit_group.scorocode_sdk.Callbacks.CallbackRegisterUser;
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
                        view.showToast(R.string.notMatchPasswords);
                    }
                    view.disableRegisterButton();
                }
            }
        };


    }

    public void onCreateScreen() {
        view.setDataListeners();
        view.setInitialScreenState();
        view.setActionBar();
    }

    public void onRegisterButtonPressed() {
        final String userName = view.getUserName();
        final String email = view.getEmail();
        final String password = view.getPassword();
        final String repeatedPassword = view.getRepeatedPassword();

        if(model.isDataValid(userName, email, password, repeatedPassword)) {

            CallbackRegisterUser callback = new CallbackRegisterUser() {
                @Override
                public void onRegisterSucceed() {
                    view.showToast(R.string.user_registered);
                    view.displayFilledLoginActivity(email, password);
                }

                @Override
                public void onRegisterFailed(String errorCode, String errorMessage) {
                    view.showToast(R.string.errorDuringRegister);
                }
            };

            model.registerNewUser(userName, email, password, callback);
        } else {
            model.handleError();
        }
    }

    public Action1<CharSequence> getInputCheckCallback() {
        return action;
    }
}
