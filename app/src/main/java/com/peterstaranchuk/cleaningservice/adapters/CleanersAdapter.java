package com.peterstaranchuk.cleaningservice.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.peterstaranchuk.cleaningservice.R;
import com.peterstaranchuk.cleaningservice.helpers.FieldHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.profit_group.scorocode_sdk.scorocode_objects.Document;
import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

/**
 * Created by Peter Staranchuk.
 */

public class CleanersAdapter extends BaseAdapter {

    private Context context;
    private List<DocumentInfo> documents;
    private LayoutInflater inflater;
    private int layoutRes;

    public CleanersAdapter(Context context, List<DocumentInfo> documents, int layoutRes) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.documents = (documents == null? new ArrayList<DocumentInfo>() : documents);
        this.layoutRes = layoutRes;
    }

    @Override
    public int getCount() {
        return documents.size();
    }

    @Override
    public Object getItem(int position) {
        if(position < documents.size()) {
            return documents.get(position);
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

        DocumentInfo document = (DocumentInfo) getItem(position);

        FieldHelper fieldHelper = new FieldHelper(context);
        String photoUrl = fieldHelper.getCleanerPhotoUrlFrom(document);

        Picasso.with(context)
                .load(photoUrl)
                .placeholder(R.drawable.no_image)
                .into(holder.ivCleanerPhoto);

        holder.tvCleanerName.setText(fieldHelper.getCleanerNameFrom(document));

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tvName) TextView tvCleanerName;
        @BindView(R.id.ivCleanerPhoto) ImageView ivCleanerPhoto;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
