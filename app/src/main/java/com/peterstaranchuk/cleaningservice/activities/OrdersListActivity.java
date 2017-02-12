package com.peterstaranchuk.cleaningservice.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.peterstaranchuk.cleaningservice.R;
import com.peterstaranchuk.cleaningservice.adapters.OrdersAdapter;
import com.peterstaranchuk.cleaningservice.helpers.ActionBarHelper;
import com.peterstaranchuk.cleaningservice.helpers.SideMenuHelper;
import com.peterstaranchuk.cleaningservice.model.OrdersListScreenModel;
import com.peterstaranchuk.cleaningservice.presenter.OrdersListScreenPresenter;
import com.peterstaranchuk.cleaningservice.view.OrdersListScreenView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

public class OrdersListActivity extends AppCompatActivity implements OrdersListScreenView {

    @BindView(R.id.lvOrders) ListView lvOrders;
//    @BindView(R.id.lvMenuItems) ListView lvMenuItems;
    private OrdersListScreenPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_list);
        ButterKnife.bind(this);

        presenter = new OrdersListScreenPresenter(this, new OrdersListScreenModel(this));
        presenter.onCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.refreshOrdersList();
    }

    public static void display(Context context) {
        context.startActivity(new Intent(context, OrdersListActivity.class));
    }

    @Override
    public void setActionBar() {
        if(getSupportActionBar() != null) {
            ActionBarHelper.setHomeButton(getSupportActionBar());
            getSupportActionBar().setTitle(getString(R.string.ordersList));
        }
    }

    @Override
    public void setOrdersList(List<DocumentInfo> ordersList) {
        OrdersAdapter adapter = new OrdersAdapter(this, ordersList, R.layout.item_order);
        lvOrders.setAdapter(adapter);
    }

    @Override
    public void showError(int error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setSideMenu() {
        NavigationView view = ButterKnife.findById(this, R.id.navigation_view);
        SideMenuHelper.initSideMenu(view);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_with_refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.refreshList:
                presenter.refreshOrdersList();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
