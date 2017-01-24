package com.peterstaranchuk.cleaningservice.dagger2modules;

import android.widget.Toast;

import com.peterstaranchuk.cleaningservice.R;
import com.peterstaranchuk.cleaningservice.activities.LoginActivity;
import com.peterstaranchuk.cleaningservice.activities.MainActivity;
import com.peterstaranchuk.cleaningservice.model.LoginScreenModel;
import com.peterstaranchuk.cleaningservice.presenter.LoginScreenPresenter;
import com.peterstaranchuk.cleaningservice.view.LoginScreenView;

import dagger.Module;
import dagger.Provides;
import ru.profit_group.scorocode_sdk.Callbacks.CallbackLoginUser;
import ru.profit_group.scorocode_sdk.Responses.user.ResponseLogin;
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
    CallbackLoginUser callbackLoginUser() {
        return new CallbackLoginUser() {
            @Override
            public void onLoginSucceed(ResponseLogin responseLogin) {
                //if user account exist in server (inside users collection)
                //when login will be successful
                MainActivity.display(view.getContext());
            }

            @Override
            public void onLoginFailed(String errorCode, String errorMessage) {
                //if login failed you can handle this situation. You can also see the reason
                //why login operation was failed
                Toast.makeText(view.getContext(), view.getContext().getString(R.string.cant_login) + "\n" + errorMessage, Toast.LENGTH_SHORT).show();
            }
        };
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
