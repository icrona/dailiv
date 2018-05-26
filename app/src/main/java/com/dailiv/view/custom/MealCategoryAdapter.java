package com.dailiv.view.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.PlanningCategory;

import java.util.List;

/**
 * Created by aldo on 4/22/18.
 */

public class MealCategoryAdapter extends ArrayAdapter<PlanningCategory>{

    private final LayoutInflater mInflater;
    private final Context mContext;
    private final int mResource;

    private int padding;

    private List<PlanningCategory> items;

    public MealCategoryAdapter(@NonNull Context context, int resource, @NonNull List<PlanningCategory> objects) {
        super(context, resource, 0, objects);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        items = objects;
        padding = mContext.getResources().getDimensionPixelOffset(R.dimen.s);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createDropDownView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    private View createView(int position, View convertView, ViewGroup parent) {
        final View view = mInflater.inflate(mResource, parent, false);

        TextView textView = view.findViewById(R.id.tv_spinner_text);

        textView.setText(items.get(position).getText());

        textView.setTextColor(ContextCompat.getColor(mContext, position == 0 ? R.color.grey_light : R.color.grey_dark));

        textView.setPadding(padding, padding, padding, padding);

        return view;
    }

    private View createDropDownView(int position, View convertView, ViewGroup parent) {

        final View view = mInflater.inflate(mResource, parent, false);

        TextView textView = view.findViewById(R.id.tv_spinner_text);

        if(position == 0) {
            textView.setVisibility(View.GONE);
        }
        else{
            textView.setVisibility(View.VISIBLE);
            textView.setText(items.get(position).getText());
        }

        textView.setPadding(padding, padding, padding, padding);

        return view;
    }
}
