package com.peterstaranchuk.cleaningservice.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.peterstaranchuk.cleaningservice.R;
import com.peterstaranchuk.cleaningservice.custom_views.CounterView;
import com.peterstaranchuk.cleaningservice.dagger2components.OrderScreenActionsComponent;
import com.peterstaranchuk.cleaningservice.enums.PropertyType;
import com.peterstaranchuk.cleaningservice.helpers.ActionBarHelper;
import com.peterstaranchuk.cleaningservice.helpers.InputHelper;
import com.peterstaranchuk.cleaningservice.helpers.SideMenuHelper;
import com.peterstaranchuk.cleaningservice.model.OrderScreenModel;
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
import rx.subscriptions.CompositeSubscription;

import static com.peterstaranchuk.cleaningservice.dagger2modules.OrderScreenActionModule.ACTION_REFRESH_BUTTON_STATE;
import static com.peterstaranchuk.cleaningservice.dagger2modules.OrderScreenActionModule.ACTION_SET_APARTMENT_PROPERTY_TYPE;
import static com.peterstaranchuk.cleaningservice.dagger2modules.OrderScreenActionModule.ACTION_SET_HOUSE_PROPERTY_TYPE;
import static com.peterstaranchuk.cleaningservice.dagger2modules.OrderScreenActionModule.ACTION_STATE_CHANGED;

public class OrderActivity extends AppCompatActivity implements OrderScreenView {

    private static final String STATE_BEDROOMS_COUNTER = "state_bedroomsCounterState";
    private static final String STATE_BATHROOMS_COUNTER = "state_bathroomsCounterState";
    private static final String STATE_PROPERTY_TYPE = "state_property_type";

    @BindView(R.id.cvBedroomsCounter) CounterView cvBedroomsCounter;
    @BindView(R.id.cvBathroomsCounter) CounterView cvBathroomsCounter;
    @BindView(R.id.etSizeInSQF) EditText etSizeInSQF;
    @BindView(R.id.tvControlHouse) TextView tvControlHouse;
    @BindView(R.id.tvControlApartment) TextView tvControlApartment;
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.etAddress) EditText etAddress;
    @BindView(R.id.etContactPhone) EditText etContactPhone;
    @BindView(R.id.btnMakeAnOrder) Button btnMakeAnOrder;

    @BindString(R.string.price_for_cleaning) String textPriceForCleaning;
    @BindString(R.string.currency_sign) String textCurrencySign;
    @BindString(R.string.tell_us_about_your) String textTitle;

    @Inject OrderScreenPresenter presenter;
    @Inject @Named(ACTION_STATE_CHANGED) Action1<CharSequence> stateChangedAction;
    @Inject @Named(ACTION_SET_HOUSE_PROPERTY_TYPE) Action1<Void> actionSetHousePropertyType;
    @Inject @Named(ACTION_SET_APARTMENT_PROPERTY_TYPE) Action1<Void> actionSetApartmentPropertyType;
    @Inject @Named(ACTION_REFRESH_BUTTON_STATE) Action1<CharSequence> refreshButtonStateAction;
    private AlertDialog orderPlacingDialog;
    private CompositeSubscription compositeSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        OrderScreenActionsComponent.Injector.inject(this);

        presenter.onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        presenter.storeCounterState(outState);
        presenter.storePropertyType(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void setOrderInfoChangedListeners() {
        cvBedroomsCounter.setCounterClickAction(stateChangedAction);
        cvBathroomsCounter.setCounterClickAction(stateChangedAction);
        compositeSubscription = new CompositeSubscription();

        compositeSubscription.add(RxTextView.textChanges(etSizeInSQF).subscribe(stateChangedAction));
        compositeSubscription.add(RxTextView.textChanges(etAddress).subscribe(refreshButtonStateAction));
        compositeSubscription.add(RxTextView.textChanges(etContactPhone).subscribe(refreshButtonStateAction));
        compositeSubscription.add(RxView.clicks(tvControlHouse).subscribe(actionSetHousePropertyType));
        compositeSubscription.add(RxView.clicks(tvControlApartment).subscribe(actionSetApartmentPropertyType));
    }

    @Override
    protected void onDestroy() {
        if(compositeSubscription != null && !compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
        super.onDestroy();
    }

    @OnClick(R.id.btnMakeAnOrder)
    public void onBtnMakeAnOrderClicked(View btnView) {
        presenter.onMakeOrderButtonClicked();
    }

    @Override
    public void setDefaultState() {
        etSizeInSQF.setText("");
        presenter.setPropertyType(PropertyType.HOUSE);
    }

    @Override
    public void changeTitle() {
        tvTitle.setText(textTitle + " " + presenter.getPropertyType().getPropertyName(this) +":");
    }

    @Override
    public void highlightSelectedMode(int houseControlColorId, int apartmentControlColorId) {
        tvControlApartment.setBackgroundResource(houseControlColorId);
        tvControlHouse.setBackgroundResource(apartmentControlColorId);
    }

    @Override
    public void setControlItemsTextColors(int houseColorId, int apartmentColorId) {
        tvControlHouse.setTextColor(getResources().getColor(houseColorId));
        tvControlApartment.setTextColor(getResources().getColor(apartmentColorId));
    }

    @Override
    public void storeCounterState(Bundle outState) {
        outState.putLong(STATE_BEDROOMS_COUNTER, cvBedroomsCounter.getCurrentCount());
        outState.putLong(STATE_BATHROOMS_COUNTER, cvBathroomsCounter.getCurrentCount());
    }

    @Override
    public void restoreCounterState(Bundle savedInstanceState) {
        long bedroomsCount = savedInstanceState.getLong(STATE_BEDROOMS_COUNTER, 1);
        long bathroomsCount = savedInstanceState.getLong(STATE_BATHROOMS_COUNTER, 1);

        cvBedroomsCounter.setCurrentCount(bedroomsCount);
        cvBathroomsCounter.setCurrentCount(bathroomsCount);
    }

    @Override
    public void storePropertyType(Bundle outState) {
        outState.putSerializable(STATE_PROPERTY_TYPE, presenter.getPropertyType());
    }

    @Override
    public void restorePropertyType(Bundle savedInstanceState) {
        PropertyType propertyType = (PropertyType) savedInstanceState.getSerializable(STATE_PROPERTY_TYPE);
        presenter.setPropertyType(propertyType);
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
        try {
            String input = InputHelper.getStringFrom(etSizeInSQF);
            return input.isEmpty()? 0d : Long.valueOf(input);
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
            return 0d;
        }
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
    public String getPhone() {
        return InputHelper.getStringFrom(etContactPhone);
    }

    @Override
    public void orderSent() {
        orderPlacingDialog.cancel();

        new AlertDialog.Builder(this)
                .setTitle(R.string.orderSentTitle)
                .setMessage(R.string.orderSentMessage)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OrdersListActivity.display(getContext());
                    }
                })
                .show();
    }

    @Override
    public void errorDuringOrderSending() {
        orderPlacingDialog.cancel();

        new AlertDialog.Builder(this)
                .setTitle(R.string.errorWhileSendingTitle)
                .setMessage(R.string.errorWhileSendingMessage)
                .setPositiveButton(R.string.ok, null)
                .show();
    }

    @Override
    public void setPrice(Double price) {
        String priceText;

        if(price == OrderScreenModel.WRONG_PRICE) {
            priceText = getString(R.string.cant_calculate_price);
        } else {
            String priceString = new DecimalFormat("#.##").format(price);
            priceText = getString(R.string.make_an_order) + " (" + priceString  + textCurrencySign +")";
        }

        refreshMakeAnOrderButtonState();

        btnMakeAnOrder.setText(priceText);
    }

    @Override
    public void refreshMakeAnOrderButtonState() {
        if(!getAddress().isEmpty() && !getPhone().isEmpty() && presenter.getPrice() != OrderScreenModel.WRONG_PRICE) {
            InputHelper.enableButton(btnMakeAnOrder);
        } else {
            InputHelper.disableButton(btnMakeAnOrder);
        }
    }

    @Override
    public void showPlaceOrderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.orderSending))
                .setCancelable(false)
                .setView(R.layout.dialog_item_placing);

        orderPlacingDialog = builder.create();
        orderPlacingDialog.show();
    }

    @Override
    public void setActionBar() {
        if(getSupportActionBar() != null) {
            ActionBarHelper.setHomeButton(getSupportActionBar());
            getSupportActionBar().setTitle(getString(R.string.orderActivity));
        }
    }

    @Override
    public void setSideMenu() {
        NavigationView view = ButterKnife.findById(this, R.id.navigation_view);
        SideMenuHelper.initSideMenu(view);
    }

    public static void display(Context context) {
        context.startActivity(new Intent(context, OrderActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
