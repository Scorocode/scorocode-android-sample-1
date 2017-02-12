package com.peterstaranchuk.cleaningservice.helpers;

import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.peterstaranchuk.cleaningservice.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Peter Staranchuk.
 */

public class InputHelper {
    public static void setEmptyEnterListener(EditText viewForCheck, Action1<CharSequence> callbackAction) {
        RxTextView.textChanges(viewForCheck)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callbackAction);
    }

    public static boolean isNotEmpty(EditText editText) {
        if(editText != null && !editText.getText().toString().isEmpty()) {
            return true;
        }
        return false;
    }

    public static void enableButton(Button button) {
        //we extract this method for case if we will need to
        //change behaviour or appearance of all enabled buttons in app
        if(button != null) {
            button.setEnabled(true);
            button.setBackgroundColor(button.getResources().getColor(R.color.mainColor));
        }
    }

    public static void disableButton(Button button) {
        //we extract this method for case if we will need to
        //change behaviour or appearance of all disabled buttons in app
        if(button != null) {
            button.setEnabled(false);
            button.setBackgroundColor(button.getResources().getColor(R.color.disabledButtonColor));
        }
    }

    @NonNull
    public static String capitalizeFirstLetter(String s) {
        if(s == null) {
            return "";
        }

        if(s.length() > 0) {
            return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
        } else {
            return s.toUpperCase();
        }
    }

    @NonNull
    public static String getStringFrom(EditText editText) {
        if(editText != null) {
            return editText.getText().toString();
        } else {
            return "";
        }
    }

    public static void setFocusTo(EditText editText) {
        if(editText != null) {
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
