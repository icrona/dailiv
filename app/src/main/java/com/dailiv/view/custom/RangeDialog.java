package com.dailiv.view.custom;

import android.content.Context;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.annimon.stream.function.Function;
import com.appyvet.materialrangebar.RangeBar;
import com.dailiv.R;

import rx.functions.Action2;

/**
 * Created by aldo on 4/14/18.
 */

public abstract class RangeDialog extends BaseDialog implements IDialog{

    private TextView tvPriceFrom;
    private TextView tvPriceTo;

    public RangeDialog(Context context, LayoutInflater layoutInflater) {
        super(context, layoutInflater, R.layout.dialog_range);

        tvPriceFrom = mView.findViewById(R.id.tv_price_from);
        tvPriceTo = mView.findViewById(R.id.tv_price_to);
    }

    @Override
    public void show() {

        TextView tvReset = mView.findViewById(R.id.tv_reset);

        RangeBar rbPrice = mView.findViewById(R.id.rb_price);

        rbPrice.setPinTextFormatter(value -> value);

        rbPrice.setTickStart(tickStart());
        rbPrice.setTickEnd(tickEnd());
        rbPrice.setTickInterval(tickStart());

        rbPrice.setRangePinsByValue(fromValue(), toValue());

        setFromAndTo(fromValue(), toValue());

        rbPrice.setOnRangeBarChangeListener(((rangeBar, leftPinIndex, rightPinIndex, leftPinValue, rightPinValue) -> {

            setFromAndTo(Float.valueOf(leftPinValue), Float.valueOf(rightPinValue));
        }));


        btnApply.setOnClickListener(view -> {
            dialog.dismiss();
            submitAction().call(Integer.valueOf(rbPrice.getLeftPinValue()), Integer.valueOf(rbPrice.getRightPinValue()));
        });

        tvReset.setOnClickListener(view -> {

            rbPrice.setRangePinsByValue(tickStart(), tickEnd());

            setFromAndTo(tickStart(), tickEnd());

        });

        dialog.show();
    }

    private void setFromAndTo(float from, float to) {

        tvPriceFrom.setText(spannableTextView().apply(textFormat().apply(from)));
        tvPriceTo.setText(spannableTextView().apply(textFormat().apply(to)));
    }

    public abstract float tickStart();

    public abstract float tickEnd();

    public abstract float fromValue();

    public abstract float toValue();

    public abstract Function<String, SpannableString> spannableTextView();

    public abstract Function<Float, String> textFormat();

    public abstract Action2<Integer, Integer> submitAction();

}
