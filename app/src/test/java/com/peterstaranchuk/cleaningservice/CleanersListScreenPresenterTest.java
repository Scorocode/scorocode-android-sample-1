package com.peterstaranchuk.cleaningservice;

import android.os.Bundle;

import com.peterstaranchuk.cleaningservice.model.CleanersListScreenModel;
import com.peterstaranchuk.cleaningservice.presenter.CleanersListScreenPresenter;
import com.peterstaranchuk.cleaningservice.view.CleanersListScreenView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ru.profit_group.scorocode_sdk.Callbacks.CallbackFindDocument;
import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Peter Staranchuk.
 */

@RunWith(MockitoJUnitRunner.class)
public class CleanersListScreenPresenterTest {
    @Mock CleanersListScreenView view;
    @Mock CleanersListScreenModel model;
    private CleanersListScreenPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new CleanersListScreenPresenter(view, model);
    }

    @Test
    public void shouldSetActionBarWhenScreenStarted() throws Exception {
        //when
        presenter.onCreate(new Bundle());

        //than
        verify(view).setActionBar();
    }

    @Test
    public void shouldRefreshCleanersListWhenActivityResumed() throws Exception {
        //when
        presenter.refreshList();

        //than
        verify(model).fetchCleanersList(any(CallbackFindDocument.class));

        //TODO add check with callback
//        verify(view).refreshCleanersList(new ArrayList<DocumentInfo>());
    }

    @Test
    public void shouldInitSideMenuWhenScreenCreated() throws Exception {
        //when
        presenter.onCreate(new Bundle());

        //than
        verify(view).setSideMenu();
    }


}