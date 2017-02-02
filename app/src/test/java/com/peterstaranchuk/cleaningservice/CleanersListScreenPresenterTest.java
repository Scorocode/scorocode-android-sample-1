package com.peterstaranchuk.cleaningservice;

import com.peterstaranchuk.cleaningservice.model.CleanersListScreenModel;
import com.peterstaranchuk.cleaningservice.presenter.CleanersListScreenPresenter;
import com.peterstaranchuk.cleaningservice.view.CleanersListScreenView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ru.profit_group.scorocode_sdk.Callbacks.CallbackFindDocument;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

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
        presenter.onCreate();

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
}