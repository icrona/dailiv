package com.dailiv.view.custom;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.Difficulty;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by aldo on 4/21/18.
 */

public abstract class RadioButtonDialog {

    private Context context;

    private LayoutInflater layoutInflater;

    private RadioButtonAdapter radioButtonAdapter;

    public RadioButtonDialog(Context context, LayoutInflater layoutInflater, List<Difficulty> radioButtonItems) {
        this.context = context;
        this.layoutInflater = layoutInflater;

        radioButtonAdapter = new RadioButtonAdapter(context, R.layout.item_radiobutton, radioButtonItems);
    }

    public void show() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        View mView = layoutInflater.inflate(R.layout.dialog_radiobutton, null);

        TextView tvTitle = mView.findViewById(R.id.tv_dialog_title);

        Button btnApply = mView.findViewById(R.id.btn_apply);
        Button btnCancel = mView.findViewById(R.id.btn_cancel);

        tvTitle.setText(title());

        ListView listView = mView.findViewById(R.id.lv_radiobutton);

        listView.setAdapter(radioButtonAdapter);

        mBuilder.setView(mView);

        final AlertDialog dialog = mBuilder.create();
        dialog.setCanceledOnTouchOutside(false);

        btnApply.setOnClickListener(view -> {
            dialog.dismiss();
            submitAction().call(radioButtonAdapter.getSelectedItem());

        });

        btnCancel.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }

    public void reset() {

        radioButtonAdapter.setSelectedItem(null);

        radioButtonAdapter.notifyDataSetChanged();
    }

    public abstract String title();

    public abstract Action1<Difficulty> submitAction();
}
