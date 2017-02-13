package com.peterstaranchuk.cleaningservice.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.peterstaranchuk.cleaningservice.R;
import com.peterstaranchuk.cleaningservice.helpers.ActionBarHelper;
import com.peterstaranchuk.cleaningservice.helpers.FormatHelper;
import com.peterstaranchuk.cleaningservice.helpers.InputHelper;
import com.peterstaranchuk.cleaningservice.helpers.SideMenuHelper;
import com.peterstaranchuk.cleaningservice.model.OrdersDetailScreenModel;
import com.peterstaranchuk.cleaningservice.model.OrdersListScreenModel;
import com.peterstaranchuk.cleaningservice.presenter.OrdersDetailScreenPresenter;
import com.peterstaranchuk.cleaningservice.view.OrdersDetailScreenView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

public class OrdersDetailActivity extends AppCompatActivity implements OrdersDetailScreenView {
    @BindView(R.id.tvUserAddress) TextView tvUserAddress;
    @BindView(R.id.tvUserPhone) TextView tvUserPhone;
    @BindView(R.id.tvPlacedAt) TextView tvPlacedAt;
    @BindView(R.id.tvOrderPrice) TextView tvOrderPrice;
    @BindView(R.id.tvOrderStatus) TextView tvOrderStatus;
    @BindView(R.id.tvNumberOfBathrooms) TextView tvNumberOfBathrooms;
    @BindView(R.id.tvNumberOfBedrooms) TextView tvNumberOfBedrooms;
    @BindView(R.id.tvArea) TextView tvArea;
    @BindView(R.id.btnRemoveOrder) Button btnRemoveOrder;

    @BindString(R.string.fieldContactPhone) String keyPhone;
    @BindString(R.string.fieldAddress) String keyAddress;
    @BindString(R.string.fieldOrderPrice) String keyPrice;
    @BindString(R.string.fieldArea) String keyArea;
    @BindString(R.string.numberOfBathroomsField) String fieldBathrooms;
    @BindString(R.string.numberOfBedroomsField) String fieldBedrooms;
    @BindString(R.string.fieldOrderStatus) String keyStatus;

    private OrdersDetailScreenPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_detail);
        ButterKnife.bind(this);
        presenter = new OrdersDetailScreenPresenter(this, new OrdersDetailScreenModel(this));
        presenter.onCreate();
    }

    public static void display(Context context, DocumentInfo order) {
        Intent intent = new Intent(context, OrdersDetailActivity.class);
        intent.putExtra(OrdersDetailScreenModel.EXTRA_ORDER_INFO, order);

        context.startActivity(intent);
    }

    @Override
    public void setOrderDetails(DocumentInfo orderData) {
        String userPhone = orderData.getString(keyPhone);
        String userAddress = orderData.getString(keyAddress);
        String orderPrice = orderData.getString(keyPrice) + " " + getString(R.string.currencySign);
        String numberOfBathrooms = String.valueOf(orderData.getInteger(fieldBathrooms));
        String numberOfBedrooms = String.valueOf(orderData.getInteger(fieldBedrooms));
        String area = String.valueOf(orderData.getInteger(keyArea)) + " " + getString(R.string.sqft);
        int orderStatus = orderData.getInteger(keyStatus);

        tvUserPhone.setText(userPhone);
        tvUserAddress.setText(userAddress);
        tvOrderPrice.setText(orderPrice);
        tvPlacedAt.setText(getDateAndTimeStringFrom(orderData));
        tvNumberOfBathrooms.setText(numberOfBathrooms);
        tvNumberOfBedrooms.setText(numberOfBedrooms);
        tvArea.setText(area);
        tvOrderStatus.setText(OrdersListScreenModel.getOrderStatusFrom(this, orderStatus));
    }

    @Override
    public void showError(int error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setSideMenu() {
        NavigationView navigationView = ButterKnife.findById(this, R.id.navigation_view);
        SideMenuHelper.initSideMenu(navigationView);
    }

    @Override
    public void disableRemoveButton() {
        InputHelper.disableButton(btnRemoveOrder);
    }

    @Override
    public void setActionBar() {
        if(getSupportActionBar() != null) {
            ActionBarHelper.setHomeButton(getSupportActionBar());
            getSupportActionBar().setTitle(R.string.orderDetailsTitle);
        }
    }

    @NonNull
    private String getDateAndTimeStringFrom(DocumentInfo orderData) {
        String createdAtString = orderData.getString(getString(R.string.fieldCreatedAt));
        return FormatHelper.getFormattedDate(this, createdAtString) + " " + FormatHelper.getFormattedTime(this, createdAtString);
    }

    @OnClick(R.id.btnRemoveOrder)
    public void onBtnRemoveOrderClicked(View btnView) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.warrant)
                .setMessage(R.string.warrant_delete_order)
                .setPositiveButton(R.string.yes_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.onDeleteOrderButtonClicked();
                    }
                })
                .setNegativeButton(R.string.no_button, null)
                .show();
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
