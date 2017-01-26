package com.peterstaranchuk.cleaningservice.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.peterstaranchuk.cleaningservice.R;
import com.peterstaranchuk.cleaningservice.custom_views.CounterView;
import com.peterstaranchuk.cleaningservice.dagger2components.OrderScreenActionsComponent;
import com.peterstaranchuk.cleaningservice.dagger2modules.OrderScreenActionModule;
import com.peterstaranchuk.cleaningservice.enums.PropertyType;
import com.peterstaranchuk.cleaningservice.helpers.InputHelper;
import com.peterstaranchuk.cleaningservice.presenter.OrderScreenPresenter;
import com.peterstaranchuk.cleaningservice.view.OrderScreenView;

import java.text.DecimalFormat;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class OrderActivity extends AppCompatActivity implements OrderScreenView {

    @BindView(R.id.cvBedroomsCounter) CounterView cvBedroomsCounter;
    @BindView(R.id.cvBathroomsCounter) CounterView cvBathroomsCounter;
    @BindView(R.id.etSizeInSQF) EditText etSizeInSQF;
    @BindView(R.id.tvPrice) TextView tvPrice;
    @BindView(R.id.tvControlHouse) TextView tvControlHouse;
    @BindView(R.id.tvControlApartment) TextView tvControlApartment;
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.etAddress) EditText etAddress;

    @BindString(R.string.price_for_cleaning) String textPriceForCleaning;
    @BindString(R.string.currency_sign) String textCurrencySign;
    @BindString(R.string.tell_us_about_your) String textTitle;

    @Inject OrderScreenPresenter presenter;
    @Inject @Named(OrderScreenActionModule.ACTION_STATE_CHANGED) Action1<CharSequence> stateChangedAction;
    @Inject @Named(OrderScreenActionModule.ACTION_SET_HOUSE_PROPERTY_TYPE) Action1<Void> actionSetHousePropertyType;
    @Inject @Named(OrderScreenActionModule.ACTION_SET_APARTMENT_PROPERTY_TYPE) Action1<Void> actionSetApartmentPropertyType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        OrderScreenActionsComponent.Injector.inject(this);
        presenter.onCreate();
    }

    @Override
    public void setOrderInfoChangedListeners() {
        cvBedroomsCounter.setCounterClickAction(stateChangedAction);
        cvBathroomsCounter.setCounterClickAction(stateChangedAction);

        RxTextView.textChanges(etSizeInSQF).subscribe(stateChangedAction);
        RxView.clicks(tvControlHouse).subscribe(actionSetHousePropertyType);
        RxView.clicks(tvControlApartment).subscribe(actionSetApartmentPropertyType);
    }

    @OnClick(R.id.btnMakeAnOrder)
    public void onBtnMakeAnOrderClicked(View btnView) {
        presenter.onMakeOrderButtonClicked();
    }

    @Override
    public void setDefaultState() {
        presenter.setPropertyType(PropertyType.HOUSE);
        etSizeInSQF.setText(String.valueOf(1000));
    }

    @Override
    public void changeTitle() {
        tvTitle.setText(textTitle + " " + presenter.getPropertyType().getPropertyName(this));
    }

    @Override
    public void highlightSelectedMode(int houseControlColorId, int apartmentControlColorId) {
        tvControlApartment.setBackgroundColor(getResources().getColor(houseControlColorId));
        tvControlHouse.setBackgroundColor(getResources().getColor(apartmentControlColorId));
    }

    @Override
    public long getBedroomsCount() {
        return cvBedroomsCounter.getCurrentCount();
    }

    @Override
    public long getBathroomsCount() {
        return cvBathroomsCounter.getCurrentCount();
    }

    @Override
    public double getSizeInSquareFoots() {
        String input = InputHelper.getStringFrom(etSizeInSQF);
        return input.isEmpty()? 0d : Long.valueOf(input);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public String getAddress() {
        return InputHelper.getStringFrom(etAddress);
    }

    @Override
    public void orderSent() {
        //TODO add logic
    }

    @Override
    public void errorDuringOrderSending() {
        //TODO add logic
    }

    @Override
    public void setPrice(Double price) {
        tvPrice.setText(textPriceForCleaning +": " + new DecimalFormat("#.##").format(price) + " " + textCurrencySign);
    }

    public static void display(Context context) {
        context.startActivity(new Intent(context, OrderActivity.class));
    }
}
