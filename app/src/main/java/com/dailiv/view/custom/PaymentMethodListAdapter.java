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

        if (getChildrenCount( groupPosition ) == 0 ) {
            indicator.setVisibility( View.INVISIBLE );
        }
        else {
            indicator.setVisibility( View.VISIBLE );
            indicator.setImageResource( isExpanded ? R.drawable.ic_arrow_down : R.drawable.ic_arrow_down );
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
