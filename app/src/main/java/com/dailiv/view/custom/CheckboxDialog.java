package com.dailiv.view.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ListView;

import com.annimon.stream.Stream;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.CheckboxItem;

import java.util.List;

import rx.functions.Action1;

import static com.annimon.stream.Collectors.toList;

/**
 * Created by aldo on 4/15/18.
 */
public abstract class CheckboxDialog extends BaseDialog implements IDialog{

    private CheckboxAdapter checkboxAdapter;

    private List<CheckboxItem> checkboxItems;

    public CheckboxDialog(Context context, LayoutInflater layoutInflater, List<CheckboxItem> checkboxItems) {
        super(context, layoutInflater, R.layout.dialog_checkbox);
        this.checkboxItems = checkboxItems;

        checkboxAdapter = new CheckboxAdapter(context, R.layout.item_checkbox, this.checkboxItems);

    }

    @Override
    public void show() {

        ListView listView = mView.findViewById(R.id.lv_checkboxes);

        listView.setAdapter(checkboxAdapter);

        btnApply.setOnClickListener(view -> {
            dialog.dismiss();
            submitAction().call(checkboxAdapter.getCheckboxItems());

        });

        dialog.show();

    }

    public void reset() {

        List<CheckboxItem> resettedCheckbox = Stream.of(checkboxItems)
                .map(CheckboxItem::reset)
                .collect(toList());

        checkboxAdapter.setCheckboxItems(resettedCheckbox);

        checkboxAdapter.notifyDataSetChanged();

    }

    public abstract Action1<List<CheckboxItem>> submitAction();

}
