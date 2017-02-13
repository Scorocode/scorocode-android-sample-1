package com.peterstaranchuk.cleaningservice.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.peterstaranchuk.cleaningservice.R;
import com.peterstaranchuk.cleaningservice.dagger2components.RegisterScreenMVPComponent;
import com.peterstaranchuk.cleaningservice.helpers.ActionBarHelper;
import com.peterstaranchuk.cleaningservice.helpers.InputHelper;
import com.peterstaranchuk.cleaningservice.presenter.RegisterScreenPresenter;
import com.peterstaranchuk.cleaningservice.view.RegisterScreenView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class RegisterActivity extends AppCompatActivity implements RegisterScreenView {

    @BindView(R.id.etUserName) EditText etUserName;
    @BindView(R.id.etEmail) EditText etEmail;
    @BindView(R.id.etPassword) EditText etPassword;
    @BindView(R.id.etRepeatPassword) EditText etRepeatPassword;
    @BindView(R.id.btnRegister) Button btnRegister;
    @BindView(R.id.btnLogin) Button btnLogin;
    @BindView(R.id.llRepeatPassword) LinearLayout llRepeatPassword;
    @BindView(R.id.llUserName) LinearLayout llUserName;
    @Inject RegisterScreenPresenter presenter;
    @Inject Action1<CharSequence> inputCheckCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        RegisterScreenMVPComponent.Injector.inject(this);
        presenter.onCreateScreen();
    }

    @Override
    public void setInitialScreenState() {
        disableRegisterButton();
        btnLogin.setVisibility(View.GONE);
        llRepeatPassword.setVisibility(View.VISIBLE);
        llUserName.setVisibility(View.VISIBLE);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setActionBar() {
        if(getSupportActionBar() != null) {
            ActionBarHelper.setHomeButton(getSupportActionBar());
            getSupportActionBar().setTitle(R.string.registerActivityTitle);
        }
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void displayFilledLoginActivity(String email, String password) {
        LoginActivity.displayFilled(getContext(), email, password);
    }

    @OnClick(R.id.btnRegister)
    public void onBtnRegisterClicked(View registerButton) {
        presenter.onRegisterButtonPressed();
    }

    @Override
    public void setDataListeners() {
        InputHelper.setEmptyEnterListener(etUserName, inputCheckCallback);
        InputHelper.setEmptyEnterListener(etEmail, inputCheckCallback);
        InputHelper.setEmptyEnterListener(etPassword, inputCheckCallback);
        InputHelper.setEmptyEnterListener(etRepeatPassword, inputCheckCallback);
    }

    @Override
    public String getUserName() {
        return InputHelper.getStringFrom(etUserName);
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
    public String getRepeatedPassword() {
        return InputHelper.getStringFrom(etRepeatPassword);
    }

    @Override
    public void enableRegisterButton() {
        InputHelper.enableButton(btnRegister);
    }

    @Override
    public void showToast(int errorId) {
        Toast.makeText(this, errorId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void disableRegisterButton() {
        InputHelper.disableButton(btnRegister);
    }

    public static void display(Context context) {
        context.startActivity(new Intent(context, RegisterActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
