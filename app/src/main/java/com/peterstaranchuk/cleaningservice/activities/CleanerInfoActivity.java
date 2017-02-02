package com.peterstaranchuk.cleaningservice.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.peterstaranchuk.cleaningservice.R;
import com.peterstaranchuk.cleaningservice.model.CleanerInfoScreenModel;
import com.peterstaranchuk.cleaningservice.view.CleanerInfoScreenView;

import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

public class CleanerInfoActivity extends AppCompatActivity implements CleanerInfoScreenView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_info);
    }

    public static void display(Context context, DocumentInfo cleanerInfo) {
        Intent intent = new Intent(context, CleanerInfoActivity.class);
        intent.putExtra(CleanerInfoScreenModel.EXTRA_CLEANER_INFO, cleanerInfo);
        context.startActivity(intent);
    }


    @Override
    public void setCleanerInfo(DocumentInfo cleanerInfo) {

    }
}
