package com.dailiv.view.custom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dailiv.R;

import butterknife.ButterKnife;
import lombok.NoArgsConstructor;

/**
 * Created by aldo on 5/10/18.
 */

@NoArgsConstructor
public class PaymentMethodListAdapter extends ExpandableListAdapter{

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment_method_header, parent, false);
        }

        final ImageView indicator = ButterKnife.findById(view, R.id.iv_indicator);

        final TextView comingSoon = ButterKnife.findById(view, R.id.tv_coming_soon);

        if (getChildrenCount( groupPosition ) == 0 ) {
            indicator.setVisibility(View.GONE);
            comingSoon.setVisibility(View.VISIBLE);
        }
        else {
            indicator.setVisibility(View.VISIBLE);
            comingSoon.setVisibility(View.GONE);
            indicator.setImageResource( isExpanded ? R.drawable.ic_arrow_up : R.drawable.ic_arrow_down );
        }

        final TextView textView = ButterKnife.findById(view, R.id.tv_header);
        textView.setText(headerList.get(groupPosition));
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_expandable_child, parent, false);
        }

        final TextView textView = ButterKnife.findById(view, R.id.tv_child);
        textView.setText(itemListMap.get(headerList.get(groupPosition)).get(childPosition));
        return view;
    }


}
