package com.peterstaranchuk.cleaningservice.helpers;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.peterstaranchuk.cleaningservice.R;
import com.peterstaranchuk.cleaningservice.activities.CleanerListActivity;
import com.peterstaranchuk.cleaningservice.activities.LoginActivity;
import com.peterstaranchuk.cleaningservice.activities.OrdersListActivity;
import com.peterstaranchuk.cleaningservice.enums.MenuItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import ru.profit_group.scorocode_sdk.Callbacks.CallbackDocumentSaved;
import ru.profit_group.scorocode_sdk.scorocode_objects.Document;

/**
 * Created by Peter Staranchuk.
 */

public class SideMenuHelper {

    public static void initSideMenuItems(ListView lvDrawerItems) {
        final Context context = lvDrawerItems.getContext();

        lvDrawerItems.setAdapter(getSimpleAdapter(context));

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

    @NonNull
    private static SimpleAdapter getSimpleAdapter(Context context) {
        String ATTRIBUTE_TEXT = "TEXT";
        String ATTRIBUTE_IMAGE = "IMAGE";

        List<Map<String, Object>> data = new ArrayList<>();

        Map<String, Object> map;
        for(MenuItems item : MenuItems.values()) {
            map = new HashMap<>();
            map.put(ATTRIBUTE_TEXT, item.getMenuItemName(context));
            map.put(ATTRIBUTE_IMAGE, item.getMenuItemIcon());
            data.add(map);
        }

        String[] from = {ATTRIBUTE_TEXT, ATTRIBUTE_IMAGE};

        int[] to = {R.id.tvMenuItemName, R.id.ivMenuItemIcon};

        return new SimpleAdapter(context, data, R.layout.menu_item, from, to);
    }

    private static void showFeedbackSendDialog(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_feedback, null);
        final EditText etFeedback = ButterKnife.findById(view, R.id.etFeedback);

        new AlertDialog.Builder(context)
                .setTitle(R.string.feedback)
                .setView(view)
                .setCancelable(false)
                .setPositiveButton(R.string.send, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FieldHelper fieldHelper = new FieldHelper(context);
                        DataStoreHelper dataStoreHelper = new DataStoreHelper(context);
                        String userId = dataStoreHelper.getUserId();
                        String userName = dataStoreHelper.getUserName();

                        Document document = new Document(context.getString(R.string.collectionNameFeedback));
                        document.setField(fieldHelper.feedbackTextField(), InputHelper.getStringFrom(etFeedback));
                        document.setField(fieldHelper.userIdField(), userId);
                        document.setField(fieldHelper.userNameField(), userName);

                        document.saveDocument(new CallbackDocumentSaved() {
                            @Override
                            public void onDocumentSaved() {
                                Toast.makeText(context, R.string.feedback_sent, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onDocumentSaveFailed(String errorCode, String errorMessage) {
                                Toast.makeText(context, R.string.error_during_feedback_sending, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton(R.string.close_action, null)
                .show();
    }

    @NonNull
    private static List<String> getListWithMenuItems(Context context) {
        List<String> list = new ArrayList<>();
        for(MenuItems item : MenuItems.values()) {
            list.add(item.getMenuItemName(context));
        }
        return list;
    }


}
