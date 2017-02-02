package com.peterstaranchuk.cleaningservice.helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.peterstaranchuk.cleaningservice.R;
import com.peterstaranchuk.cleaningservice.activities.CleanerListActivity;
import com.peterstaranchuk.cleaningservice.activities.LoginActivity;
import com.peterstaranchuk.cleaningservice.activities.OrdersListActivity;
import com.peterstaranchuk.cleaningservice.enums.MenuItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter Staranchuk.
 */

public class SideMenuHelper {

    public static void initSideMenu(ListView lvDrawerItems) {
        final Context context = lvDrawerItems.getContext();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, getListWithMenuItems(context));
        lvDrawerItems.setAdapter(arrayAdapter);

        lvDrawerItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MenuItems item = MenuItems.values()[position];

                switch (item) {
                    case ORDERS:
                        OrdersListActivity.display(context);
                        break;

                    case CLEANERS:
                        CleanerListActivity.display(context);
                        break;

                    case FEEDBACK:
                        showFeedbackSendDialog(context);
                        break;

                    case LOGOUT:
                        LoginActivity.display(context);
                        break;
                }
            }
        });
    }

    private static void showFeedbackSendDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.feedback)
                .setPositiveButton(R.string.send, null)
                .show();
    }

    @NonNull
    private static List<String> getListWithMenuItems(Context context) {
        List<String> list = new ArrayList<>();
        for(MenuItems item : MenuItems.values()) {
            list.add(item.getMenuName(context));
        }
        return list;
    }


}
