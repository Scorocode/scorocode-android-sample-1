package com.peterstaranchuk.cleaningservice.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.peterstaranchuk.cleaningservice.R;
import com.peterstaranchuk.cleaningservice.dagger2components.LoginScreenComponent;
import com.peterstaranchuk.cleaningservice.helpers.InputHelper;
import com.peterstaranchuk.cleaningservice.presenter.LoginScreenPresenter;
import com.peterstaranchuk.cleaningservice.view.LoginScreenView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.profit_group.scorocode_sdk.Callbacks.CallbackLoginUser;
import ru.profit_group.scorocode_sdk.ScorocodeSdk;
import ru.profit_group.scorocode_sdk.scorocode_objects.User;
import rx.functions.Action1;

public class LoginActivity extends AppCompatActivity implements LoginScreenView {

    @BindView(R.id.etEmail) EditText etEmail;
    @BindView(R.id.etPassword) EditText etPassword;
    @BindView(R.id.btnLogin) Button btnLogin;
    @BindView(R.id.btnRegister) Button btnRegister;

    @Inject CallbackLoginUser callbackLoginUser;
    @Inject Action1<CharSequence> action;
    @Inject LoginScreenPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginScreenComponent.Injector.inject(this);
        ButterKnife.bind(this);
        ScorocodeSdk.initWith(
                getString(R.string.appKey),
                getString(R.string.clientKey),
                getString(R.string.masterKey),
                null, null, null, null
        );

        presenter.onCreateScreen();
    }

    @OnClick(R.id.btnLogin)
    public void onLoginButtonClicked(View loginButton) {
        presenter.onLoginButtonClicked();
    }

    @OnClick(R.id.btnRegister)
    public void onRegisterButtonClicked(View loginButton) {
        presenter.onRegisterButtonClicked();
    }

    @Override
    public String getEmail() {
        return InputHelper.getStringFrom(etEmail);
    }

    @Override
    public String getPassword() {
        return InputHelper.getStringFrom(etPassword);
    }

    @Override
    public void loginUser() {
        User user = new User();
        user.login(getEmail(), getPassword(), callbackLoginUser);
    }

    @Override
    public void showError() {
        callbackLoginUser.onLoginFailed("", getString(R.string.wrong_data_error));
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void enableLoginButton() {
        InputHelper.enableButton(btnLogin);
    }

    @Override
    public void disableLoginButton() {
        InputHelper.disableButton(btnLogin);
    }

    @Override
    public void setDataListeners() {
        InputHelper.checkForEmptyEnter(etEmail, action);
        InputHelper.checkForEmptyEnter(etPassword, action);
    }

    @Override
    public void openRegisterScreen() {
        RegisterActivity.display(getContext());
    }
}
