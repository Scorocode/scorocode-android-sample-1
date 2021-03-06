package com.peterstaranchuk.cleaningservice.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.peterstaranchuk.cleaningservice.R;
import com.peterstaranchuk.cleaningservice.adapters.CleanersAdapter;
import com.peterstaranchuk.cleaningservice.helpers.ActionBarHelper;
import com.peterstaranchuk.cleaningservice.helpers.SideMenuHelper;
import com.peterstaranchuk.cleaningservice.model.CleanersListScreenModel;
import com.peterstaranchuk.cleaningservice.presenter.CleanersListScreenPresenter;
import com.peterstaranchuk.cleaningservice.view.CleanersListScreenView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

import static com.peterstaranchuk.cleaningservice.presenter.CleanersListScreenPresenter.EXTRA_LIST_VIEW_STATE;

public class CleanerListActivity extends AppCompatActivity implements CleanersListScreenView {

    @BindView(R.id.lvCleaners) ListView lvCleaners;
    private CleanersListScreenPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cleaners_list);
        ButterKnife.bind(this);

        presenter = new CleanersListScreenPresenter(this, new CleanersListScreenModel(this));
        presenter.onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(EXTRA_LIST_VIEW_STATE, presenter.getListState());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        presenter.saveListState(lvCleaners.onSaveInstanceState());
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.refreshList();
    }

    @Override
    public void refreshCleanersList(final List<DocumentInfo> documentInfos, Parcelable listViewState) {
        CleanersAdapter adapter = new CleanersAdapter(CleanerListActivity.this, documentInfos, R.layout.item_cleaner);
        lvCleaners.setAdapter(adapter);
        lvCleaners.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CleanerInfoActivity.display(CleanerListActivity.this, documentInfos.get(position));
            }
        });

        if(listViewState != null) {
            lvCleaners.onRestoreInstanceState(listViewState);
        }
    }

    @Override
    public void showErrorToast(int errorStringId) {
        Toast.makeText(this, errorStringId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setActionBar() {
        if(getSupportActionBar() != null) {
            ActionBarHelper.setHomeButton(getSupportActionBar());
            getSupportActionBar().setTitle(R.string.cleanerListActivityTitle);
        }
    }

    @Override
    public void setSideMenu() {
        NavigationView view = ButterKnife.findById(this, R.id.navigation_view);
        SideMenuHelper.initSideMenu(view);
    }

    @Override
    public Parcelable getCleanersListState() {
        return lvCleaners.onSaveInstanceState();
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
                presenter.refreshList();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void display(Context context) {
        context.startActivity(new Intent(context, CleanerListActivity.class));
    }
}
