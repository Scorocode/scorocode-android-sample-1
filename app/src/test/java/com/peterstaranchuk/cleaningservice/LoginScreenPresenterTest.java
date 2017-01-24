package com.peterstaranchuk.cleaningservice;

import com.peterstaranchuk.cleaningservice.model.LoginScreenModel;
import com.peterstaranchuk.cleaningservice.presenter.LoginScreenPresenter;
import com.peterstaranchuk.cleaningservice.view.LoginScreenView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by Peter Staranchuk.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginScreenPresenterTest {
    @Mock LoginScreenView view;
    @Mock LoginScreenModel model;
    private LoginScreenPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new LoginScreenPresenter(view, model);
    }

    @Test
    public void shouldRedirectIfDataEnteredAndValid() throws Exception {
        //when
        when(model.isDataValid(anyString(), anyString())).thenReturn(true);
        presenter.onLoginButtonClicked();

        //than
        verify(view).getEmail();
        verify(view).getPassword();
        verify(model).loginUser(anyString(), anyString());
    }

    @Test
    public void shouldShowErrorScreenIfDataNotValid() throws Exception {
        //when
        when(model.isDataValid(anyString(), anyString())).thenReturn(false);
        presenter.onLoginButtonClicked();

        //than
        verify(view).getEmail();
        verify(view).getPassword();
        verify(model).handleError();
    }

    @Test
    public void shouldInitScreenStateWhenItCreated() throws Exception {
        //when
        presenter.onCreateScreen();

        //than
        verify(view).disableLoginButton();
        verify(view).setDataListeners();
    }

    @Test
    public void shouldOpenRegisterScreenWhenRegisterButtonPressed() throws Exception {
        //when
        presenter.onRegisterButtonClicked();

        //than
        verify(view).openRegisterScreen();
    }
}