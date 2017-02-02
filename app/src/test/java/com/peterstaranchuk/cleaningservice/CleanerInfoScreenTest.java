package com.peterstaranchuk.cleaningservice;

import android.content.Intent;

import com.peterstaranchuk.cleaningservice.model.CleanerInfoScreenModel;
import com.peterstaranchuk.cleaningservice.presenter.CleanerInfoScreenPresenter;
import com.peterstaranchuk.cleaningservice.view.CleanerInfoScreenView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by Peter Staranchuk.
 */

@RunWith(MockitoJUnitRunner.class)
public class CleanerInfoScreenTest {
    @Mock CleanerInfoScreenView view;
    @Mock CleanerInfoScreenModel model;
    private CleanerInfoScreenPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new CleanerInfoScreenPresenter(view, model);
    }

    @Test
    public void shouldSetCleanersInfoWhenScreenStarted() throws Exception {
        presenter.onCreate();

        verify(view).getIntent();
        verify(model).getCleanerInfo(any(Intent.class));
        verify(view).setCleanerInfo(any(DocumentInfo.class));
    }

    @Test
    public void shouldInitActionBarWhenScreenStarted() throws Exception {
        presenter.onCreate();

        verify(view).setActionBar();
    }
}