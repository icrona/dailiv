package com.dailiv.view.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.Difficulty;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by aldo on 4/21/18.
 */

public class RadioButtonAdapter extends ArrayAdapter<Difficulty>{

    private LayoutInflater mInflater;

    private List<Difficulty> radioButtonItems;
    private int mViewResourceId;

    private AppCompatRadioButton selectedRb;

    private int selectedPosition = -1;

    @Getter
    @Setter
    private Difficulty selectedItem;

    public RadioButtonAdapter(@NonNull Context context, int resource, @NonNull List<Difficulty> objects) {
        super(context, resource, objects);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        radioButtonItems = objects;
        mViewResourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView == null) {
            convertView = mInflater.inflate(mViewResourceId, null);
            viewHolder = new RadioButtonAdapter.ViewHolder();
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.radioButton = convertView.findViewById(R.id.rb_dialog);

        viewHolder.radioButton.setOnClickListener(v -> {

            selectedItem = radioButtonItems.get(position);

            if(position != selectedPosition && selectedRb != null){
                selectedRb.setChecked(false);
            }

            selectedPosition = position;
            selectedRb = (AppCompatRadioButton)v;

        });

        if(selectedPosition != position || selectedItem == null){
            viewHolder.radioButton.setChecked(false);
        }
        else{
            viewHolder.radioButton.setChecked(true);
            if(selectedRb != null && viewHolder.radioButton != selectedRb){
                selectedRb = viewHolder.radioButton;
            }
        }

        viewHolder.radioButton.setText(radioButtonItems.get(position).getText());

        return convertView;

    }


    private static class ViewHolder{

        private AppCompatRadioButton radioButton;
    }
}
