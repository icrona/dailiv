package com.dailiv.view.custom;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.CheckboxItem;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by aldo on 4/22/18.
 */

public abstract class BaseDialog {

    protected Context context;

    protected LayoutInflater layoutInflater;

    protected final AlertDialog dialog;

    protected View mView;

    protected Button btnApply;
    protected Button btnCancel;

    public BaseDialog(Context context, LayoutInflater layoutInflater, int resourceId) {
        this.context = context;
        this.layoutInflater = layoutInflater;

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        mView = layoutInflater.inflate(resourceId, null);

        TextView tvTitle = mView.findViewById(R.id.tv_dialog_title);

        btnApply = mView.findViewById(R.id.btn_apply);
        btnCancel = mView.findViewById(R.id.btn_cancel);

        if(title() != null){
            tvTitle.setText(title());
        }

        mBuilder.setView(mView);

        dialog = mBuilder.create();
        dialog.setCanceledOnTouchOutside(false);

        if(btnCancel != null) {
            btnCancel.setOnClickListener(onCancelClickListener());
        }

    }

    protected View.OnClickListener onCancelClickListener() {
        return view -> dialog.dismiss();
    }


    public abstract String title();

}
