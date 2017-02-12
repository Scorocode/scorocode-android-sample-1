package com.peterstaranchuk.cleaningservice.helpers;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.peterstaranchuk.cleaningservice.R;
import com.peterstaranchuk.cleaningservice.activities.CleanerListActivity;
import com.peterstaranchuk.cleaningservice.activities.LoginActivity;
import com.peterstaranchuk.cleaningservice.activities.OrdersListActivity;

import butterknife.ButterKnife;
import ru.profit_group.scorocode_sdk.Callbacks.CallbackDocumentSaved;
import ru.profit_group.scorocode_sdk.scorocode_objects.Document;

/**
 * Created by Peter Staranchuk.
 */

public class SideMenuHelper {

    public static void initSideMenu(NavigationView navigationView) {
        final Context context = navigationView.getContext();

        setHeader(navigationView, context);
        setOnItemClickListener(navigationView, context);
    }

    private static void setOnItemClickListener(NavigationView navigationView, final Context context) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_item_orders:
                        OrdersListActivity.display(context);
                        break;

                    case R.id.nav_item_cleaners:
                        CleanerListActivity.display(context);
                        break;

                    case R.id.nav_item_feedback:
                        showFeedbackSendDialog(context);
                        break;

                    case R.id.nav_item_logout:
                        LoginActivity.display(context);
                        break;
                }
                return false;
            }
        });
    }

    private static void setHeader(NavigationView navigationView, Context context) {
        View headerView = navigationView.getHeaderView(0);
        TextView name = ButterKnife.findById(headerView, R.id.tv_name);
        TextView email = ButterKnife.findById(headerView, R.id.tv_email);

        DataStoreHelper dataStoreHelper = new DataStoreHelper(context);

        String userName = dataStoreHelper.getUserName();
        userName = InputHelper.capitalizeFirstLetter(userName);
        name.setText(context.getString(R.string.hello) + ", " + userName);

        String emailAddress = context.getString(R.string.login_activity_email_label) + " " + dataStoreHelper.getUserEmail();
        email.setText(emailAddress);
    }

    private static void showFeedbackSendDialog(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_feedback, null);
        final EditText etFeedback = ButterKnife.findById(view, R.id.etFeedback);

        new AlertDialog.Builder(context)
                .setTitle(R.string.menu_item_feedback)
                .setView(view)
                .setCancelable(false)
                .setPositiveButton(R.string.send, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Document document = getFeedback(context, etFeedback);
                        sendFeedback(document, context);
                    }
                })
                .setNegativeButton(R.string.close_action, null)
                .show();
    }

    private static void sendFeedback(Document document, final Context context) {
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

    @NonNull
    private static Document getFeedback(Context context, EditText etFeedback) {
        FieldHelper fieldHelper = new FieldHelper(context);
        DataStoreHelper dataStoreHelper = new DataStoreHelper(context);
        String userId = dataStoreHelper.getUserId();
        String userName = dataStoreHelper.getUserName();

        Document document = new Document(context.getString(R.string.collectionNameFeedback));
        document.setField(fieldHelper.feedbackTextField(), InputHelper.getStringFrom(etFeedback));
        document.setField(fieldHelper.userIdField(), userId);
        document.setField(fieldHelper.userNameField(), userName);
        return document;
    }


}
