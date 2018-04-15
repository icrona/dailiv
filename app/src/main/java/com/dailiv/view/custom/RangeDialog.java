package com.dailiv.view.custom;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appyvet.materialrangebar.RangeBar;
import com.dailiv.R;

import lombok.AllArgsConstructor;
import rx.functions.Action2;

/**
 * Created by aldo on 4/14/18.
 */

@AllArgsConstructor
public abstract class RangeDialog{

    private Context context;

    private LayoutInflater layoutInflater;

    public void show() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        View mView = layoutInflater.inflate(R.layout.dialog_range, null);

        TextView tvTitle = mView.findViewById(R.id.tv_dialog_title);
        TextView tvPriceFrom = mView.findViewById(R.id.tv_price_from);
        TextView tvPriceTo = mView.findViewById(R.id.tv_price_to);
        TextView tvReset = mView.findViewById(R.id.tv_reset);

        Button btnApply = mView.findViewById(R.id.btn_apply);
        Button btnCancel = mView.findViewById(R.id.btn_cancel);

        tvTitle.setText(title());

        RangeBar rbPrice = mView.findViewById(R.id.rb_price);

        rbPrice.setPinTextFormatter(value -> value);

        rbPrice.setTickStart(tickStart());
        rbPrice.setTickEnd(tickEnd());
        rbPrice.setTickInterval(tickStart());

        rbPrice.setRangePinsByValue(fromValue(), toValue());

        tvPriceFrom.setText(String.format("%.0f",fromValue()));
        tvPriceTo.setText(String.format("%.0f",toValue()));

        rbPrice.setOnRangeBarChangeListener(((rangeBar, leftPinIndex, rightPinIndex, leftPinValue, rightPinValue) -> {

            tvPriceFrom.setText(leftPinValue);
            tvPriceTo.setText(rightPinValue);
        }));


        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.setCanceledOnTouchOutside(false);

        btnApply.setOnClickListener(view -> {
            dialog.dismiss();
            submitAction().call(Integer.valueOf(rbPrice.getLeftPinValue()), Integer.valueOf(rbPrice.getRightPinValue()));
        });

        btnCancel.setOnClickListener(view -> dialog.dismiss());

        tvReset.setOnClickListener(view -> {

            rbPrice.setRangePinsByValue(tickStart(), tickEnd());

            tvPriceFrom.setText(String.format("%.0f", tickStart()));
            tvPriceTo.setText(String.format("%.0f", tickEnd()));

        });


        dialog.show();
    }

    public abstract float tickStart();

    public abstract float tickEnd();

    public abstract float fromValue();

    public abstract float toValue();

    public abstract String title();

    public abstract Action2<Integer, Integer> submitAction();

}
