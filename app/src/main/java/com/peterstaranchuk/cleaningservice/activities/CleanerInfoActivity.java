package com.peterstaranchuk.cleaningservice.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.peterstaranchuk.cleaningservice.R;
import com.peterstaranchuk.cleaningservice.helpers.ActionBarHelper;
import com.peterstaranchuk.cleaningservice.helpers.FieldHelper;
import com.peterstaranchuk.cleaningservice.helpers.SideMenuHelper;
import com.peterstaranchuk.cleaningservice.model.CleanerInfoScreenModel;
import com.peterstaranchuk.cleaningservice.presenter.CleanerInfoScreenPresenter;
import com.peterstaranchuk.cleaningservice.view.CleanerInfoScreenView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

public class CleanerInfoActivity extends AppCompatActivity implements CleanerInfoScreenView {

    @BindView(R.id.ivCleanerPhoto) ImageView ivCleanerPhoto;
    @BindView(R.id.tvCleanerName) TextView tvCleanerName;
    @BindView(R.id.tvCleanerDescription) TextView tvCleanerDescription;

    private CleanerInfoScreenPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_info);
        ButterKnife.bind(this);

        presenter = new CleanerInfoScreenPresenter(this, new CleanerInfoScreenModel(this));
        presenter.onCreate();
    }

    @Override
    public void setCleanerInfo(DocumentInfo cleanerInfo) {
        FieldHelper fieldHelper = new FieldHelper(this);

        String imageUrl = fieldHelper.getCleanerPhotoUrlFrom(cleanerInfo);
        String cleanerName = fieldHelper.getCleanerNameFrom(cleanerInfo);
        String cleanerDescription = fieldHelper.getCleanerDescriptionFrom(cleanerInfo);

        int avatarSize = Math.round(getResources().getDimension(R.dimen.cleaner_avatar_size));

        Picasso.with(this)
                .load(imageUrl)
                .resize(avatarSize, avatarSize)
                .centerCrop()
                .placeholder(R.drawable.no_image)
                .into(ivCleanerPhoto);

        tvCleanerName.setText(cleanerName);
        tvCleanerDescription.setText(cleanerDescription.replace("\\\\n", "\n"));
    }

    @Override
    public void setActionBar() {
        if(getSupportActionBar() != null) {
            ActionBarHelper.setHomeButton(getSupportActionBar());
            getSupportActionBar().setTitle(R.string.cleanersInfoActivityTitle);
        }
    }

    @Override
    public void setSideMenu() {
        NavigationView view = ButterKnife.findById(this, R.id.navigation_view);
        SideMenuHelper.initSideMenu(view);
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

    public static void display(Context context, DocumentInfo cleanerInfo) {
        Intent intent = new Intent(context, CleanerInfoActivity.class);
        intent.putExtra(CleanerInfoScreenModel.EXTRA_CLEANER_INFO, cleanerInfo);
        context.startActivity(intent);
    }
}
