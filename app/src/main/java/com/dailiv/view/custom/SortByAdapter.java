package com.dailiv.view.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.SortBy;

import java.util.List;

/**
 * Created by aldo on 4/22/18.
 */

public class SortByAdapter extends ArrayAdapter<SortBy>{

    private final LayoutInflater mInflater;
    private final Context mContext;
    private List<SortBy> items;
    private final int mResource;

    public SortByAdapter(@NonNull Context context, int resource, @NonNull List<SortBy> objects) {
        super(context, resource, 0, objects);

        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        items = objects;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final View view = mInflater.inflate(mResource, parent, false);

        TextView textView = view.findViewById(R.id.tv_spinner_text);

        textView.setText(items.get(position).getText());
        textView.setTextColor(mContext.getResources().getColor(R.color.white));

        return view;
    }

    private View createView(int position, View convertView, ViewGroup parent) {
        final View view = mInflater.inflate(mResource, parent, false);

        RelativeLayout relativeLayout = view.findViewById(R.id.rl_spinner);

        int padding = mContext.getResources().getDimensionPixelOffset(R.dimen.s);
        relativeLayout.setPadding(padding, padding, padding, padding);

        TextView textView = view.findViewById(R.id.tv_spinner_text);

        textView.setText(items.get(position).getText());

        return view;
    }

}
