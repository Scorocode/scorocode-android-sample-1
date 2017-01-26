package com.peterstaranchuk.cleaningservice;

import com.peterstaranchuk.cleaningservice.model.OrderScreenModel;
import com.peterstaranchuk.cleaningservice.presenter.OrderScreenPresenter;
import com.peterstaranchuk.cleaningservice.view.OrderScreenView;

import org.junit.Before;
import org.mockito.Mock;

/**
 * Created by Peter Staranchuk.
 */
public class OrderScreenPresenterTest {
    @Mock OrderScreenView view;
    @Mock OrderScreenModel model;
    private OrderScreenPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new OrderScreenPresenter(view, model);
    }


}