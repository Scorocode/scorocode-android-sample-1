package com.peterstaranchuk.cleaningservice.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.peterstaranchuk.cleaningservice.R;

import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

public class CleanerInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_info);
    }

    public static void display(Context context, DocumentInfo documentInfo) {
        context.startActivity(new Intent(context, CleanerInfoActivity.class));
    }
}
