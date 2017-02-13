package com.peterstaranchuk.cleaningservice;

import android.content.Intent;

import com.peterstaranchuk.cleaningservice.model.OrdersDetailScreenModel;
import com.peterstaranchuk.cleaningservice.presenter.OrdersDetailScreenPresenter;
import com.peterstaranchuk.cleaningservice.view.OrdersDetailScreenView;

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
public class OrderDetailScreenPresenterTest {
    @Mock OrdersDetailScreenView view;
    @Mock OrdersDetailScreenModel model;
    private OrdersDetailScreenPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new OrdersDetailScreenPresenter(view, model);
    }

    @Test
    public void shouldSetOrderInfoWhenActivityStarted() throws Exception {
        //when
        presenter.onCreate();

        //than
        verify(model).extractOrderInfo(any(Intent.class));
        verify(view).setOrderDetails(any(DocumentInfo.class));
    }

    @Test
    public void shouldAskIfOrderNotAcceptedAndRemoveOrder() throws Exception {
        //when
        presenter.onDeleteOrderButtonClicked();

        //than
        verify(model).checkIfNotAccepted(any(DocumentInfo.class), any(OrdersDetailScreenModel.OnStatusChecked.class));
//        verify(model).removeOrder(any(DocumentInfo.class), any(CallbackRemoveDocument.class));
    }

    @Test
    public void shouldSetSideMenuWhenActivityStarted() throws Exception {
        //when
        presenter.onCreate();

        //than
        verify(view).setSideMenu();
    }

    @Test
    public void shouldSetActionBarWhenActivityStarted() throws Exception {
        //when
        presenter.onCreate();

        //than
        verify(view).setActionBar();
    }
}