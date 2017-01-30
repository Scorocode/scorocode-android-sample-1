package com.peterstaranchuk.cleaningservice;

import com.peterstaranchuk.cleaningservice.enums.PropertyType;
import com.peterstaranchuk.cleaningservice.model.OrderScreenModel;
import com.peterstaranchuk.cleaningservice.presenter.OrderScreenPresenter;
import com.peterstaranchuk.cleaningservice.view.OrderScreenView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ru.profit_group.scorocode_sdk.Callbacks.CallbackDocumentSaved;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

/**
 * Created by Peter Staranchuk.
 */

@RunWith(MockitoJUnitRunner.class)
public class OrderScreenPresenterTest {
    @Mock OrderScreenView view;
    @Mock OrderScreenModel model;
    private OrderScreenPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new OrderScreenPresenter(view, model);
    }

    @Test
    public void shouldInitDefaultStatesWhenActivityCreated() throws Exception {
        //when
        presenter.onCreate();

        //than
        verify(view).setDefaultState();
    }

    @Test
    public void shouldSetListenersWhenActivityCreated() throws Exception {
        //when
        presenter.onCreate();

        //than
        verify(view).setOrderInfoChangedListeners();
    }

    @Test
    public void shouldSetActionBarWhenActivityCreated() throws Exception {
        //when
        presenter.onCreate();

        //than
        verify(view).setActionBar();
    }

    @Test
    public void shouldSetPropertyTypeAndRecalculatePriceWhenPropertyTypeChangedToHouse() throws Exception {
        //when
        PropertyType propertyType = PropertyType.HOUSE;
        presenter.setPropertyType(propertyType);

        //than
        verify(view).highlightSelectedMode(anyInt(), anyInt());
        verify(view).changeTitle();
        verify(model).getPrice(propertyType, 0d, 0L, 0L);
        verify(view).setPrice(anyDouble());
    }

    @Test
    public void shouldSetPropertyTypeAndRecalculatePriceWhenPropertyTypeChangedToAppartment() throws Exception {
        //when
        PropertyType propertyType = PropertyType.APARTMENT;
        presenter.setPropertyType(propertyType);

        //than
        verify(view).highlightSelectedMode(anyInt(), anyInt());
        verify(view).changeTitle();
        verify(model).getPrice(propertyType, 0d, 0L, 0L);
        verify(view).setPrice(anyDouble());

    }

    @Test
    public void shouldPlaceNewOrderWhenMakeOrderButtonPressed() throws Exception {
        presenter.onMakeOrderButtonClicked();
        verify(view).showPlaceOrderDialog();
        verify(view).getBathroomsCount();
        verify(view).getBedroomsCount();
        verify(view).getSizeInSquareFoots();
        verify(view).getAddress();

        verify(model).placeOrder(anyString(), anyDouble(), anyLong(), anyLong(), any(CallbackDocumentSaved.class));
    }
}