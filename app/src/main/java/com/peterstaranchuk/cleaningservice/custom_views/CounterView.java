package com.peterstaranchuk.cleaningservice.custom_views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peterstaranchuk.cleaningservice.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Peter Staranchuk.
 */

public class CounterView extends LinearLayout {
    @BindView(R.id.tvCounter) TextView tvCounter;

    public CounterView(Context context) {
        super(context);
        init(context);
    }

    public CounterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CounterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.custom_view_counter, this);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.llLeft)
    public void onCountDownButtonClicked() {
        Long newAmount = getCurrentCount() - 1L;
        if(newAmount < 0) {
            newAmount = 0L;
        }
        tvCounter.setText(String.valueOf(newAmount));
    }

    @OnClick(R.id.llRight)
    public void onCountUpButtonClicked() {
        String newAmount = String.valueOf(getCurrentCount() + 1L);
        tvCounter.setText(newAmount);
    }

    public long getCurrentCount() {
        return Long.valueOf(tvCounter.getText().toString());
    }

}
