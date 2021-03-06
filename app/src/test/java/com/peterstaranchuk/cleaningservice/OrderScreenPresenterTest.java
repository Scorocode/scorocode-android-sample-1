package com.peterstaranchuk.cleaningservice;

import android.os.Bundle;

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
import static org.mockito.Mockito.never;
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
    public void should_Init_Default_States_When_Activity_Created() throws Exception {
        //when
        presenter.onCreate(null);

        //than
        verify(view).setDefaultState();
    }

    @Test
    public void should_Set_Listeners_When_Activity_Created() throws Exception {
        //when
        presenter.onCreate(null);

        //than
        verify(view).setOrderInfoChangedListeners();
    }

    @Test
    public void should_Set_Action_Bar_When_Activity_Created() throws Exception {
        //when
        presenter.onCreate(null);

        //than
        verify(view).setActionBar();
    }

    @Test
    public void should_Set_Side_Menu_When_Activity_Created() throws Exception {
        //when
        presenter.onCreate(null);

        //than
        verify(view).setSideMenu();
    }

    @Test
    public void should_Set_Property_Type_And_Recalculate_Price_When_Property_Type_Changed_To_House() throws Exception {
        //when
        PropertyType propertyType = PropertyType.HOUSE;
        presenter.setPropertyType(propertyType);

        //than
        verify(view).highlightSelectedMode(anyInt(), anyInt());
        verify(view).setControlItemsTextColors(anyInt(), anyInt());
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

        verify(model).placeOrder(anyString(), view.getPhone(), any(PropertyType.class),  anyDouble(), anyLong(), anyLong(), any(CallbackDocumentSaved.class));
    }

    @Test
    public void should_Store_Counter_State_When_Activity_Paused() throws Exception {
        //when
        presenter.storeCounterState(any(Bundle.class));

        //than
        verify(view).storeCounterState(any(Bundle.class));
    }

    @Test
    public void should_Restore_Counter_State_When_Activity_Resumed() throws Exception {
        //given
        Bundle bundle = new Bundle();

        //when
        presenter.onCreate(bundle);

        //than
        verify(view).restoreCounterState(any(Bundle.class));
    }

    @Test
    public void should_Not_Restore_Counter_State_When_Activity_Started() throws Exception {
        //when

        presenter.onCreate(null);

        //than
        verify(view, never()).restoreCounterState(any(Bundle.class));
    }

    @Test
    public void shouldStorePropertyTypeWhenActivityPaused() throws Exception {
        //when
        presenter.storePropertyType(any(Bundle.class));

        //than
        verify(view).storePropertyType(any(Bundle.class));
    }

    @Test
    public void shouldRestorePropertyTypeIfActivityResumed() throws Exception {
        //given
        Bundle bundle = new Bundle();

        //when
        presenter.onCreate(bundle);

        //than
        verify(view).restorePropertyType(any(Bundle.class));
    }

    @Test
    public void shouldNotRestorePropertyTypeIfActivityCreated() throws Exception {
        //when
        presenter.onCreate(null);

        //than
        verify(view, never()).restorePropertyType(any(Bundle.class));
    }

}