package com.peterstaranchuk.cleaningservice;

import com.peterstaranchuk.cleaningservice.model.OrdersListScreenModel;
import com.peterstaranchuk.cleaningservice.presenter.OrdersListScreenPresenter;
import com.peterstaranchuk.cleaningservice.view.OrdersListScreenView;

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
public class OrdersListScreenTest {
    @Mock OrdersListScreenView view;
    @Mock OrdersListScreenModel model;
    private OrdersListScreenPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new OrdersListScreenPresenter(view, model);
    }

    @Test
    public void shouldSetActionBarWhenScreenStarted() throws Exception {
        //when
        presenter.onCreate();

        //than
        verify(view).setActionBar();
    }

    @Test
    public void shouldFetchAndSetOrderInfo() throws Exception {
        //when
        presenter.refreshOrdersList();

        //than
        verify(model).fetchOrders(any(CallbackFindDocument.class));
//        verify(view).setOrdersList(any(List.class)); //cant be used because of callback
    }
}