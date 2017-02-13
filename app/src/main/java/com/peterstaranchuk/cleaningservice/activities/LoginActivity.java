package com.peterstaranchuk.cleaningservice.activities;

import android.content.Context;
import android.content.Intent;
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
import ru.profit_group.scorocode_sdk.ScorocodeSdk;
import rx.functions.Action1;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity implements LoginScreenView {

    public static final String EXTRA_EMAIL = "EXTRA_EMAIL";
    public static final String EXTRA_PASSWORD = "EXTRA_PASSWORD";
    @BindView(R.id.etEmail) EditText etEmail;
    @BindView(R.id.etPassword) EditText etPassword;
    @BindView(R.id.btnLogin) Button btnLogin;
    @BindView(R.id.btnRegister) Button btnRegister;

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
                null,
                getString(R.string.fileKey), null, null, null
        );

        presenter.onCreate(savedInstanceState);
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
        InputHelper.setEmptyEnterListener(etEmail, action);
        InputHelper.setEmptyEnterListener(etPassword, action);
    }

    @Override
    public void openRegisterScreen() {
        RegisterActivity.display(getContext());
    }

    @Override
    public void setItemsVisibility() {
        ButterKnife.findById(this, R.id.tvTitle).setVisibility(View.VISIBLE);
    }

    @Override
    public void setActionBar() {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.loginScreenTitle));
        }
    }

    @Override
    public void setEmailAndPasswordAfterRegistration() {
        if(getIntent() != null) {
            String email = getIntent().getStringExtra(EXTRA_EMAIL);
            String password = getIntent().getStringExtra(EXTRA_PASSWORD);

            etEmail.setText(email);
            etPassword.setText(password);
        }
    }

    public static void display(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    public static void displayFilled(Context context, String email, String password) {
        //this method should be used only after successful registration process

        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(EXTRA_EMAIL, email);
        intent.putExtra(EXTRA_PASSWORD, password);
        context.startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
