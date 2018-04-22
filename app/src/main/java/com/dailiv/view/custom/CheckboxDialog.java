package com.dailiv.view.custom;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.CheckboxItem;

import java.util.List;

import rx.functions.Action1;

import static com.annimon.stream.Collectors.toList;

/**
 * Created by aldo on 4/15/18.
 */
public abstract class CheckboxDialog {

    private Context context;

    private LayoutInflater layoutInflater;

    private CheckboxAdapter checkboxAdapter;

    private List<CheckboxItem> checkboxItems;

    public CheckboxDialog(Context context, LayoutInflater layoutInflater, List<CheckboxItem> checkboxItems) {
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.checkboxItems = checkboxItems;

        checkboxAdapter = new CheckboxAdapter(context, R.layout.item_checkbox, this.checkboxItems);

    }

    public void show() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        View mView = layoutInflater.inflate(R.layout.dialog_checkbox, null);

        TextView tvTitle = mView.findViewById(R.id.tv_dialog_title);

        Button btnApply = mView.findViewById(R.id.btn_apply);
        Button btnCancel = mView.findViewById(R.id.btn_cancel);

        tvTitle.setText(title());

        ListView listView = mView.findViewById(R.id.lv_checkboxes);

        listView.setAdapter(checkboxAdapter);

        mBuilder.setView(mView);

        final AlertDialog dialog = mBuilder.create();
        dialog.setCanceledOnTouchOutside(false);

        btnApply.setOnClickListener(view -> {
            dialog.dismiss();
            submitAction().call(checkboxAdapter.getCheckboxItems());

        });

        btnCancel.setOnClickListener(view -> dialog.dismiss());

        dialog.show();


    }

    public void reset() {

        List<CheckboxItem> resettedCheckbox = Stream.of(checkboxItems)
                .map(CheckboxItem::reset)
                .collect(toList());

        checkboxAdapter.setCheckboxItems(resettedCheckbox);

        checkboxAdapter.notifyDataSetChanged();

    }

    public abstract String title();

    public abstract Action1<List<CheckboxItem>> submitAction();


}
