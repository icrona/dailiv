package com.dailiv.view.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.CheckboxItem;

import java.util.List;

import lombok.Getter;

/**
 * Created by aldo on 4/15/18.
 */

public class CheckboxAdapter extends ArrayAdapter<CheckboxItem>{

    private LayoutInflater mInflater;

    @Getter
    private List<CheckboxItem> checkboxItems;
    private int mViewResourceId;

    public CheckboxAdapter(@NonNull Context context, int resource, @NonNull List<CheckboxItem> objects) {
        super(context, resource, objects);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        checkboxItems = objects;
        mViewResourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView == null) {
            convertView = mInflater.inflate(mViewResourceId, null);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.checkBox = convertView.findViewById(R.id.cb_dialog);

        viewHolder.checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            System.out.println(position + " "+ b);
            CheckboxItem checkboxItem = checkboxItems.get(position);
            checkboxItem.setSelected(b);
            checkboxItems.set(position, checkboxItem);
        });

        viewHolder.checkBox.setText(checkboxItems.get(position).getName());
        viewHolder.checkBox.setChecked(checkboxItems.get(position).isSelected());
        return convertView;
    }

    private static class ViewHolder{

        private AppCompatCheckBox checkBox;
    }

}
