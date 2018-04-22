package com.dailiv.view.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.appyvet.materialrangebar.RangeBar;
import com.dailiv.R;

import rx.functions.Action2;

/**
 * Created by aldo on 4/14/18.
 */

public abstract class RangeDialog extends BaseDialog implements IDialog{

    public RangeDialog(Context context, LayoutInflater layoutInflater) {
        super(context, layoutInflater, R.layout.dialog_range);
    }

    @Override
    public void show() {

        TextView tvPriceFrom = mView.findViewById(R.id.tv_price_from);
        TextView tvPriceTo = mView.findViewById(R.id.tv_price_to);
        TextView tvReset = mView.findViewById(R.id.tv_reset);

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


        btnApply.setOnClickListener(view -> {
            dialog.dismiss();
            submitAction().call(Integer.valueOf(rbPrice.getLeftPinValue()), Integer.valueOf(rbPrice.getRightPinValue()));
        });

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

    public abstract Action2<Integer, Integer> submitAction();

}
