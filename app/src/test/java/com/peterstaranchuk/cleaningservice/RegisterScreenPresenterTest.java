package com.peterstaranchuk.cleaningservice;

import com.peterstaranchuk.cleaningservice.model.RegisterScreenModel;
import com.peterstaranchuk.cleaningservice.presenter.RegisterScreenPresenter;
import com.peterstaranchuk.cleaningservice.view.RegisterScreenView;

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
public class RegisterScreenPresenterTest {
    @Mock RegisterScreenView view;
    @Mock RegisterScreenModel model;
    private RegisterScreenPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new RegisterScreenPresenter(view, model);
    }

    @Test
    public void shouldSetDataListenersWhenActivityCreated() throws Exception {
        //when
        presenter.onCreateScreen();

        //than
        verify(view).setDataListeners();
        verify(view).setInitialScreenState();
    }

    @Test
    public void shouldRegisterNewUserIfDataIsValid() throws Exception {
        //when
        when(model.isDataValid(anyString(), anyString(), anyString(), anyString())).thenReturn(true);
        when(view.getUserName()).thenReturn("testUser");
        when(view.getEmail()).thenReturn("testEmail@gmail.com");
        when(view.getPassword()).thenReturn("anyPassword");
        when(view.getRepeatedPassword()).thenReturn("anyPassword");
        presenter.onRegisterButtonPressed();

        //than
        verify(view).getUserName();
        verify(view).getEmail();
        verify(view).getPassword();
        verify(view).getRepeatedPassword();

        verify(model).registerNewUser(anyString(), anyString(), anyString());
    }

    @Test
    public void shouldShowErrorIfDataIsNotValid() throws Exception {
        //when
        when(model.isDataValid(anyString(), anyString(), anyString(), anyString())).thenReturn(false);
        when(view.getUserName()).thenReturn("");
        when(view.getEmail()).thenReturn("testEmail@gmail.com");
        when(view.getPassword()).thenReturn("anyPassword");
        when(view.getRepeatedPassword()).thenReturn("anyPassword");
        presenter.onRegisterButtonPressed();

        //than
        verify(view).getUserName();
        verify(view).getEmail();
        verify(view).getPassword();
        verify(view).getRepeatedPassword();

        verify(model).handleError();
    }

    @Test
    public void shouldShowErrorIfPasswordsNotMatch() throws Exception {
        //when
        when(model.isDataValid(anyString(), anyString(), anyString(), anyString())).thenReturn(false);
        when(view.getUserName()).thenReturn("anyUserName");
        when(view.getEmail()).thenReturn("testEmail@gmail.com");
        when(view.getPassword()).thenReturn("anyPassword");
        when(view.getRepeatedPassword()).thenReturn("anyAnotherPassword");
        presenter.onRegisterButtonPressed();

        //than
        verify(view).getUserName();
        verify(view).getEmail();
        verify(view).getPassword();
        verify(view).getRepeatedPassword();

        verify(model).handleError();
    }
}