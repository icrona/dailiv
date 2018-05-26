package com.dailiv.view.custom;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dailiv.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.dailiv.App.getContext;

/**
 * Created by aldo on 5/5/18.
 */

@Setter
@NoArgsConstructor
public class ExpandableListAdapter extends BaseExpandableListAdapter{

    protected List<String> headerList = new ArrayList<>();
    protected Map<String, List<String>> itemListMap = new LinkedHashMap<>();

    @Override
    public int getGroupCount() {
        return itemListMap.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return itemListMap.get(headerList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return headerList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return itemListMap.get(headerList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expandable_header, parent, false);
        }

        final TextView textView = ButterKnife.findById(view, R.id.tv_header);
        textView.setText(headerList.get(groupPosition));

        final LinearLayout linearLayout = ButterKnife.findById(view, R.id.ll_header);

        linearLayout.setBackgroundColor(ContextCompat.getColor(getContext(), isExpanded ? R.color.grey_light : R.color.white ));
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_expandable_child, parent, false);
        }

        final TextView textView = ButterKnife.findById(view, R.id.tv_child);
//        final LinearLayout linearLayout = ButterKnife.findById(view, R.id.ll_expandable_child);
//        if(isLastChild){
//            linearLayout.setBackgroundResource(R.drawable.bg_border_bottom);
//        }
//        else{
//            linearLayout.setBackgroundResource(0);
//        }
        textView.setText(itemListMap.get(headerList.get(groupPosition)).get(childPosition));
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
