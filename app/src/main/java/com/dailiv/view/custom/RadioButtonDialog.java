package com.dailiv.view.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ListView;

import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.Difficulty;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by aldo on 4/21/18.
 */

public abstract class RadioButtonDialog extends BaseDialog implements IDialog{

    private RadioButtonAdapter radioButtonAdapter;

    public RadioButtonDialog(Context context, LayoutInflater layoutInflater, List<Difficulty> radioButtonItems) {
        super(context, layoutInflater, R.layout.dialog_radiobutton);

        radioButtonAdapter = new RadioButtonAdapter(context, R.layout.item_radiobutton, radioButtonItems);
    }

    @Override
    public void show() {

        ListView listView = mView.findViewById(R.id.lv_radiobutton);

        listView.setAdapter(radioButtonAdapter);

        btnApply.setOnClickListener(view -> {
            dialog.dismiss();
            submitAction().call(radioButtonAdapter.getSelectedItem());

        });

        dialog.show();
    }

    public void reset() {

        radioButtonAdapter.setSelectedItem(null);

        radioButtonAdapter.notifyDataSetChanged();
    }

    public abstract String title();

    public abstract Action1<Difficulty> submitAction();
}
