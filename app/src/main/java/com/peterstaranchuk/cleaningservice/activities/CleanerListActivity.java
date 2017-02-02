package com.peterstaranchuk.cleaningservice.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.peterstaranchuk.cleaningservice.model.CleanersListScreenModel;
import com.peterstaranchuk.cleaningservice.presenter.CleanersListScreenPresenter;
import com.peterstaranchuk.cleaningservice.view.CleanersListScreenView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

public class CleanerListActivity extends AppCompatActivity implements CleanersListScreenView {

    @BindView(R.id.lvDocuments) ListView lvDocuments;
    private CleanersListScreenPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        presenter = new CleanersListScreenPresenter(this, new CleanersListScreenModel(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.refreshList();
    }

    @Override
    public void refreshCleanersList(final List<DocumentInfo> documentInfos) {
        CleanersAdapter adapter = new CleanersAdapter(CleanerListActivity.this, documentInfos, R.layout.item_document);
        lvDocuments.setAdapter(adapter);
        lvDocuments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CleanerInfoActivity.display(CleanerListActivity.this, documentInfos.get(position));
            }
        });
    }

    @Override
    public void showErrorToast(int errorStringId) {
        Toast.makeText(this, errorStringId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

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
