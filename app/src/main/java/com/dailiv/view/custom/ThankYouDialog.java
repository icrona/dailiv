package com.dailiv.view.custom;

import android.content.Context;
import android.view.LayoutInflater;

import com.dailiv.R;

import rx.functions.Action0;

/**
 * Created by aldo on 5/27/18.
 */

public abstract class ThankYouDialog extends BaseDialog{

    public ThankYouDialog(Context context, LayoutInflater layoutInflater) {
        super(context, layoutInflater, R.layout.dialog_thank_you);
    }

    public void show() {

        btnApply.setOnClickListener(view -> submitAction().call());
        dialog.setCancelable(false);

        dialog.show();
    }

    public abstract Action0 submitAction();
}
