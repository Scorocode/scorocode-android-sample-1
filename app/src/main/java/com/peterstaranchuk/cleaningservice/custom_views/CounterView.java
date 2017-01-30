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
import rx.functions.Action1;

/**
 * Created by Peter Staranchuk.
 */

public class CounterView extends LinearLayout {
    @BindView(R.id.tvCounter) TextView tvCounter;
    private Action1<CharSequence> clickAction;

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
        tvCounter.setText(String.valueOf(1));
    }

    @OnClick(R.id.llLeft)
    public void onCountDownButtonClicked() {
        Long newAmount = getCurrentCount() - 1L;
        if(newAmount < 1) {
            newAmount = 1L;
        }

        String newAmountAsString = String.valueOf(newAmount);
        tvCounter.setText(newAmountAsString);

        if(clickAction != null) {
            clickAction.call(newAmountAsString);
        }
    }

    @OnClick(R.id.llRight)
    public void onCountUpButtonClicked() {
        String newAmount = String.valueOf(getCurrentCount() + 1L);
        tvCounter.setText(newAmount);

        if(clickAction != null) {
            clickAction.call(newAmount);
        }
    }

    public long getCurrentCount() {
        return Long.valueOf(tvCounter.getText().toString());
    }

    public void setCounterClickAction(Action1<CharSequence> clickAction) {
        this.clickAction = clickAction;
    }
}
