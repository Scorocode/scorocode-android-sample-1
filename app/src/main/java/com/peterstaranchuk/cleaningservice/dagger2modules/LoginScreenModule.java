package com.peterstaranchuk.cleaningservice.dagger2modules;

import com.peterstaranchuk.cleaningservice.activities.LoginActivity;
import com.peterstaranchuk.cleaningservice.model.LoginScreenModel;
import com.peterstaranchuk.cleaningservice.presenter.LoginScreenPresenter;
import com.peterstaranchuk.cleaningservice.view.LoginScreenView;

import dagger.Module;
import dagger.Provides;
import rx.functions.Action1;

/**
 * Created by Peter Staranchuk.
 */

@Module
public class LoginScreenModule {
    private LoginScreenView view;

    public LoginScreenModule(LoginActivity loginActivity) {
        this.view = loginActivity;
    }

    @Provides
    Action1<CharSequence> action1() {
        return new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                if(!view.getEmail().isEmpty() && !view.getPassword().isEmpty()) {
                    view.enableLoginButton();
                } else {
                    view.disableLoginButton();
                }
            }
        };
    }

    @Provides
    LoginScreenPresenter presenter() {
        return new LoginScreenPresenter(view, new LoginScreenModel(view.getContext()));
    }

}
