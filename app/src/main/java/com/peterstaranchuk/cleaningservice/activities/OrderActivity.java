package com.peterstaranchuk.cleaningservice.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.peterstaranchuk.cleaningservice.R;
import com.peterstaranchuk.cleaningservice.custom_views.CounterView;
import com.peterstaranchuk.cleaningservice.view.OrderScreenView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderActivity extends AppCompatActivity implements OrderScreenView{
    @BindView(R.id.cvBedroomsCounter) CounterView cvBedroomsCounter;
    @BindView(R.id.cvBathroomsCounter) CounterView cvBathroomsCounter;
    @BindView(R.id.etSizeInSQF) EditText etSizeInSQF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    public static void display(Context context) {
        context.startActivity(new Intent(context, OrderActivity.class));
    }


}
