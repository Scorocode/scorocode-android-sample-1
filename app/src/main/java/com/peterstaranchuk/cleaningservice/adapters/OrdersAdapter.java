package com.peterstaranchuk.cleaningservice.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.peterstaranchuk.cleaningservice.R;
import com.peterstaranchuk.cleaningservice.helpers.FieldHelper;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.profit_group.scorocode_sdk.scorocode_objects.Document;
import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

/**
 * Created by Peter Staranchuk.
 */

public class OrdersAdapter extends BaseAdapter {

    private Context context;
    private List<DocumentInfo> orders;
    private LayoutInflater inflater;
    private int layoutRes;

    private String textOrderNumber;
    private String textOrderStatus;
    private String textOrderPlacedAt;

    public OrdersAdapter(Context context, List<DocumentInfo> orders, int layoutRes) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.orders = (orders == null? new ArrayList<DocumentInfo>() : orders);
        this.layoutRes = layoutRes;

        this.textOrderNumber = context.getString(R.string.order_item_number);
        this.textOrderPlacedAt = context.getString(R.string.order_item_placed_at);
        this.textOrderStatus = context.getString(R.string.order_item_status);
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        if(position < orders.size()) {
            return orders.get(position);
        } else {
            return new Document("");
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = inflater.inflate(layoutRes, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        DocumentInfo order = (DocumentInfo) getItem(position);

        FieldHelper fieldHelper = new FieldHelper(context);
        String placedAt = fieldHelper.getPlacedAt(order);
        String orderStatus = fieldHelper.getOrderStatus(order);

        DateFormat dateFormat = android.text.format.DateFormat.getLongDateFormat(context.getApplicationContext());
        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(context.getApplicationContext());
        Date date = new Date(placedAt);

        holder.tvOrderNumber.setText(textOrderNumber + " " + (position + 1));
        holder.tvOrderPlaceTime.setText(textOrderPlacedAt + " " + dateFormat.format(date) + timeFormat.format(date));
        holder.tvOrderStatus.setText(textOrderStatus + " " + orderStatus);

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tvOrderNumber) TextView tvOrderNumber;
        @BindView(R.id.tvOrderPlaceTime) TextView tvOrderPlaceTime;
        @BindView(R.id.tvOrderStatus) TextView tvOrderStatus;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
